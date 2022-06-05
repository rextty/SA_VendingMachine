package Controller;

import Model.Entity.*;
import Model.JDBC_Connect;
import Model.Sensor.MoneySensor;
import Model.Sensor.ProductSensor;
import Model.Sensor.TemperatureSensor;
import Model.Service.PreOrderService;
import Model.Service.ProductService;
import Model.Service.ReplenishmentService;
import Model.Service.SalesRecordService;
import View.MachineGUI;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.objdetect.QRCodeDetector;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class VendingMachine{

    private final MachineGUI machineGUI;

    private MoneySensor moneySensor;
    private ProductSensor productSensor;
    private TemperatureSensor temperatureSensor;

    private ProductService productService;
    private ReplenishmentService replenishmentService;
    private SalesRecordService salesRecordService;
    private PreOrderService preOrderService;

    private int currentPage;
    private int maxPage;
    private int startPage;
    private int endPage;

    private JLabel pageLabel;
    private JLabel amountLabel;
    private JLabel productIdLabel;

    private Webcam webcam;

    // opencv
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public VendingMachine(MachineGUI _gui) {
        //TODO: 故障提醒
        // TODO: 悠遊卡

        this.machineGUI = _gui;
        init();
//        saveImgToDB();
    }

    private void init() {
        moneySensor = new MoneySensor();
        productSensor = new ProductSensor();
        temperatureSensor = new TemperatureSensor();

        productService = new ProductService();
        replenishmentService = new ReplenishmentService();
        salesRecordService = new SalesRecordService();
        preOrderService = new PreOrderService();

        pageLabel = machineGUI.getPageLabel();
        amountLabel = machineGUI.getAmountLabel();
        productIdLabel = machineGUI.getProductIdLabel();

        currentPage = 1;

        startPage = 0;
        endPage = 9;

        // 綁定投幣按鈕事件
        bingPutCoinListener();

        // 綁定商品按鈕事件
        bindProductSelectorListener();

        // 綁定切頁按鈕事件
        bindPageSwitcherListener();

        // 初始化商品頁面
        initProductPanel();

        // 初始化 QR Code 掃描
        initScanner();
    }

    // 初始化 QR Code 掃描
    private void initScanner() {
        JTabbedPane cameraTabbedPane = machineGUI.getCameraTabbedPane();
        JPanel jPanelCamera = new JPanel();

        Dimension camSize = new Dimension(100, 100);
        Dimension panelSize = new Dimension(150, 150);
        cameraTabbedPane.setPreferredSize(panelSize);

        cameraTabbedPane.remove(0);
        cameraTabbedPane.addTab("Scanner", jPanelCamera);

        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());

        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setPreferredSize(camSize);
//        webcamPanel.setFPSDisplayed(true);
//        webcamPanel.setDisplayDebugInfo(true);
//        webcamPanel.setImageSizeDisplayed(true);
//        webcamPanel.setMirrored(true);

        jPanelCamera.add(webcamPanel);
        jPanelCamera.getParent().revalidate();

        new Thread(receiveQRCode).start();
    }

    // 接收QRCode
    private Runnable receiveQRCode = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(3000);

                while (true) {
                    BufferedImage snapShot = webcam.getImage();

                    String qrCode = readQRCode(snapShot);

                    if (qrCode != null) {
                        getOrderInfo(Integer.parseInt(qrCode));
                    }

                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    };

    private void getOrderInfo(int qrCode){
        PreOrder preOrder = preOrderService.getPreOrder(qrCode);
        String dateTime = preOrder.getExpireDate();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String currentTime = dtf.format(LocalDateTime.now());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        Date date2 = null;
        try {
            date1 = format.parse(currentTime);
            date2 = format.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date1 != null;
        assert date2 != null;

        long difference = date2.getTime() - date1.getTime();

        if (difference > 0) {
            showMsg("Product have been shipped.");
            preOrder.setTake(true);
            preOrderService.updatePreOrder(preOrder);
        }else {
            showMsg("QRCode is expired.");
        }
    }

    private void pickUpPreOrder(int  qrCode) {
        PreOrder preOrder = preOrderService.getPreOrder(qrCode);

        // 判斷取貨時間...
    }

    private String readQRCode(BufferedImage bufferedImage) {
        Mat img = bufferedImageToMat(bufferedImage);
        QRCodeDetector detector = new QRCodeDetector();
        Mat points = new Mat();
        Mat straight_qrcode = new Mat();

        String data = detector.detectAndDecode(img, points, straight_qrcode);

        if (data.length() > 0) {
            return data;
        }
        return null;
    }

    public static Mat bufferedImageToMat(BufferedImage bufferedImage) {
        Mat mat = new Mat(bufferedImage.getHeight(), bufferedImage.getWidth(), CvType.CV_8UC3);
        byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        mat.put(0, 0, data);
        return mat;
    }

    private void deliverProduct() {
        productSensor.replenishment();
    }

    // 初始化商品頁面
    private void initProductPanel() {
        List<Product> productList = productService.getAllProduct();

        maxPage = (int) Math.ceil(productList.size() / 9.0);
        updatePageSwitcher();

        updateProductPanel(startPage, endPage);
    }

    // 投幣事件
    private void bingPutCoinListener() {
        machineGUI.getPut1NTDButton().addActionListener(e -> {
            moneySensor.addAmount(1);
            updateAmount();
        });

        machineGUI.getPut5NTDButton().addActionListener(e -> {
            moneySensor.addAmount(5);
            updateAmount();
        });

        machineGUI.getPut10NTDButton().addActionListener(e -> {
            moneySensor.addAmount(10);
            updateAmount();
        });

        machineGUI.getPut50NTDButton().addActionListener(e -> {
            moneySensor.addAmount(50);
            updateAmount();
        });

        machineGUI.getPut100NTDButton().addActionListener(e -> {
            moneySensor.addAmount(100);
            updateAmount();
        });

        machineGUI.getPut500NTDButton().addActionListener(e -> {
            moneySensor.addAmount(500);
            updateAmount();
        });

        machineGUI.getPut1000NTDButton().addActionListener(e -> {
            moneySensor.addAmount(1000);
            updateAmount();
        });

        machineGUI.getRefundButton().addActionListener(e -> {
            moneySensor.setAmount(0);
            updateAmount();
        });

    }

    // 商品換頁事件
    private void bindPageSwitcherListener() {
        machineGUI.getPrePageButton().addActionListener(e -> {
            currentPage--;
            updatePageSwitcher();

            if (currentPage == 1)
                machineGUI.getPrePageButton().setEnabled(false);

            machineGUI.getNextPageButton().setEnabled(true);

            startPage -= 9;
            endPage -= 9;
            updateProductPanel(startPage, endPage);

        });

        machineGUI.getNextPageButton().addActionListener(e -> {
            currentPage ++;
            updatePageSwitcher();

            if (currentPage == maxPage)
                machineGUI.getNextPageButton().setEnabled(false);

            machineGUI.getPrePageButton().setEnabled(true);

            startPage += 9;
            endPage += 9;
            updateProductPanel(startPage, endPage);
        });
    }

    // 選擇商品事件
    private void bindProductSelectorListener() {
        machineGUI.getA1Button().addActionListener(e -> {
            productSensor.addProductId("1");
            updateProductId();
        });

        machineGUI.getA2Button().addActionListener(e -> {
            productSensor.addProductId("2");
            updateProductId();
        });

        machineGUI.getA3Button().addActionListener(e -> {
            productSensor.addProductId("3");
            updateProductId();
        });

        machineGUI.getA4Button().addActionListener(e -> {
            productSensor.addProductId("4");
            updateProductId();
        });

        machineGUI.getA5Button().addActionListener(e -> {
            productSensor.addProductId("5");
            updateProductId();
        });

        machineGUI.getA6Button().addActionListener(e -> {
            productSensor.addProductId("6");
            updateProductId();
        });

        machineGUI.getA7Button().addActionListener(e -> {
            productSensor.addProductId("7");
            updateProductId();
        });

        machineGUI.getA8Button().addActionListener(e -> {
            productSensor.addProductId("8");
            updateProductId();
        });

        machineGUI.getA9Button().addActionListener(e -> {
            productSensor.addProductId("9");
            updateProductId();
        });

        machineGUI.getA0Button().addActionListener(e -> {
            productSensor.addProductId("0");
            updateProductId();
        });

        machineGUI.getDeleteButton().addActionListener(e -> {
            String productId = productSensor.getProductId();

            if (productId.length() == 0)
                return;

            productSensor.deleteProductId();
            updateProductId();
        });

        machineGUI.getConfirmButton().addActionListener(e -> {
            String productId = productSensor.getProductId();

            if (productId.isEmpty()) {
                showMsg("Please enter item number.");
                return;
            }

            Product product = productService.getProductById(Integer.parseInt(productId));

            if (product == null) {
                showMsg("Please enter the correct item number.");
                return;
            }

            if (product.getQuantity() < 1) {
                showMsg("Item is out of stock.");
                return;
            }

            if (moneySensor.getAmount() < product.getPrice()) {
                showMsg("Insufficient amount");
                return;
            }

            buyProduct(product);
        });
    }

    // 購買商品
    private void buyProduct(Product product) {
        setPanelEnabled(machineGUI.getRightPanel(), false);

        moneySensor.setAmount(moneySensor.getAmount() - product.getPrice());
        updateAmount();

        productSensor.setProductId("");
        updateProductId();

        deliverProduct();

        int newProductQuantity = product.getQuantity() - 1;
        productService.updateProductQuantityBytId(product.getId(), newProductQuantity);

        setPanelEnabled(machineGUI.getRightPanel(), true);

        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        SalesRecord salesRecord = new SalesRecord();
        salesRecord.setProductId(product.getId());
        salesRecord.setDate(simpleDateFormat.format(new java.util.Date()));

        salesRecordService.addSalesRecord(salesRecord);

        checkProductStock(product);
    }

    // 更新頁面狀態
    private void setPanelEnabled(JPanel panel, Boolean isEnabled) {
        panel.setEnabled(isEnabled);

        Component[] components = panel.getComponents();

        for (Component component : components) {
            if (component instanceof JPanel) {
                setPanelEnabled((JPanel) component, isEnabled);
            }
            component.setEnabled(isEnabled);
        }
    }

    // 檢查補貨
    private void checkProductStock(Product product) {
        //TODO: 是否該寫入Sensor
        int productQuantity = product.getQuantity();

        if (productQuantity < 30) {
            //TODO: message maybe should be json type.

            Message message = new Message();

            message.setMessage(String.format(
                    "Product of %s quantity is less than 30.", product.getName()
            ));

            message.setMsgType(MessageTypeEnum.OUT_OF_STOCK.getValue());

            if (!replenishmentService.checkExistMessageByMsgAndMsgType(message))
                replenishmentService.addMessage(message);
        }
    }

    // 更新商品頁面
    private void updateProductPanel(int start, int end) {
        try {
            // 判斷商品該放在哪一層, 一層只能放三個商品
            int temp = 0;

            JPanel topProductPanel = machineGUI.getTopProductPanel();
            JPanel middleProductPanel = machineGUI.getMiddleProductPanel();
            JPanel bottomProductPanel = machineGUI.getBottomProductPanel();

            // 清除商品
            topProductPanel.removeAll();
            middleProductPanel.removeAll();
            bottomProductPanel.removeAll();

            List<Product> productList = productService.getAllProduct();

            // 一頁放9個商品, 呼叫function所帶入的 start 跟 end 會自動計算
            for (int i = start; i <= end; i++) {
                // 避免取超出資料庫大小的資料
                if (i >= productList.size()) {
                    updateView();
                    return;
                }

                JPanel itemPanel = new JPanel();
                itemPanel.setLayout(new BoxLayout(itemPanel, BoxLayout.Y_AXIS));

                JLabel productItem = new JLabel();
                JLabel productName = new JLabel();
                JLabel productPrice = new JLabel();
                JLabel productQuantity = new JLabel();

                productItem.setIcon(new ImageIcon(ImageIO.read(productList.get(i).getImage().getBinaryStream())));
                productName.setText(productList.get(i).getName());
                productPrice.setText("$ " + productList.get(i).getPrice() + " NTD");
                productQuantity.setText("Number: " + productList.get(i).getId());

                itemPanel.add(productItem);
                itemPanel.add(productName);
                itemPanel.add(productPrice);
                itemPanel.add(productQuantity);

                // 每三筆資料放一層
                if (temp < 3) {
                    topProductPanel.add(itemPanel);
                }else if (temp < 6) {
                    middleProductPanel.add(itemPanel);
                }else if (temp < 9) {
                    bottomProductPanel.add(itemPanel);
                }

                temp++;
            }

            updateView();
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveImgToDB() {
        String base = "C:\\Users\\ian98\\OneDrive\\圖片\\sa\\";
        String[] imgs = {
                "",
                "",
                "3050268.png",
                "287062.png",
                "3081162.png",
                "food-32-512.png",
                "5303997.png",
                "3054889.png",
                "2258281.png",
                "18-512.png",
                "603884.png",
                "2619512.png",
                "3075627.png",
        };
        for (int i = 2; i <= 12; i++) {
            JDBC_Connect jdbc_connect = new JDBC_Connect();
            String file = base + imgs[i];
            String sql = String.format(
                    "UPDATE vending_machine.product SET image=(?) WHERE id = %d;", i
            );

            try {
                InputStream in = new FileInputStream(file);
                PreparedStatement ps = jdbc_connect.getConnection().prepareStatement(sql);
                ps.setBinaryStream(1, in);
                ps.executeUpdate();
            } catch (FileNotFoundException | SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 更新商品頁數
    private void updatePageSwitcher() {
        pageLabel.setText(currentPage + "/" + maxPage);
    }

    // 更新投入金額
    public void updateAmount() {
        amountLabel.setText(Integer.toString(moneySensor.getAmount()));
    }

    // 更新選擇商品編號
    public void updateProductId() {
        productIdLabel.setText(productSensor.getProductId());
    }

    private void showMsg(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }

    // 更新畫面
    public void updateView() {
        machineGUI.validate();
        machineGUI.repaint();
    }

    // 顯示販賣機畫面
    public void startView() {
        machineGUI.setSize(550, 540);
        machineGUI.setVisible(true);
    }
}
