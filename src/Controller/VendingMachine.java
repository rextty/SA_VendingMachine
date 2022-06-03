package Controller;

import Model.Entity.Message;
import Model.Entity.MessageTypeEnum;
import Model.Entity.Product;
import Model.Entity.SalesRecord;
import Model.Sensor.MoneySensor;
import Model.Sensor.ProductSensor;
import Model.Sensor.TemperatureSensor;
import Model.Service.ProductService;
import Model.Service.ReplenishmentService;
import Model.Service.SalesRecordService;
import View.MachineGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class VendingMachine{

    private final MachineGUI machineGUI;

    private MoneySensor moneySensor;

    private ProductSensor productSensor;

    private TemperatureSensor temperatureSensor;

    private ProductService productService;

    private ReplenishmentService replenishmentService;

    private SalesRecordService salesRecordService;

    private int currentPage;

    private int maxPage;

    private int startPage;

    private int endPage;

    private JLabel pageLabel;

    private JLabel amountLabel;

    private JLabel productIdLabel;

    public VendingMachine(MachineGUI _gui) {
        //TODO: 故障提醒
        //TODO: 確認MVC開發模式
        //TODO: 圖片問題
        //TODO: 悠遊卡

        this.machineGUI = _gui;
        init();
    }

    private void init() {
        moneySensor = new MoneySensor();
        productSensor = new ProductSensor();
        temperatureSensor = new TemperatureSensor();

        productService = new ProductService();
        replenishmentService = new ReplenishmentService();
        salesRecordService = new SalesRecordService();

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
        machineGUI.getScanQRCodeButton().addActionListener(e -> {
            receiveQR();
        });
    }

    // 掃描 QR Code
    private void receiveQR() {

    }

    private void accessQR() {

    }

    private void getPreOrderByQRCode() {

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
                JOptionPane.showMessageDialog(null, "Please enter item number.");
                return;
            }

            Product product = productService.getProductByProductId(productId);

            if (product == null) {
                JOptionPane.showMessageDialog(null, "Please enter the correct item number.");
                return;
            }

            if (product.getQuantity() < 1) {
                JOptionPane.showMessageDialog(null, "Item is out of stock.");
                return;
            }

            if (moneySensor.getAmount() < product.getPrice()) {
                JOptionPane.showMessageDialog(null, "Insufficient amount");
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
        productService.updateProductQuantityByProductId(product.getProductId(), newProductQuantity);

        setPanelEnabled(machineGUI.getRightPanel(), true);

        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);

        SalesRecord salesRecord = new SalesRecord();
        salesRecord.setProductId(product.getProductId());
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

            message.setProductId(product.getProductId());

            message.setMessage(String.format(
                    "Product of %s quantity is less than 30.", product.getName()
            ));

            message.setMsgType(MessageTypeEnum.OUT_OF_STOCK.getValue());

            if (!replenishmentService.checkExistMessageByProductIdAndMsgType(message))
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

                BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\ian98\\IdeaProjects\\SA_VendingMachine\\Resource\\DiscordIcon.png"));

                JLabel productItem = new JLabel(new ImageIcon(bufferedImage));
                JLabel productName = new JLabel();
                JLabel productPrice = new JLabel();
                JLabel productQuantity = new JLabel();

                productName.setText("名稱:" + productList.get(i).getName());
                productPrice.setText("價格: " + productList.get(i).getPrice());
                productQuantity.setText("編號: " + productList.get(i).getProductId());

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
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    // 更新畫面
    public void updateView() {
        machineGUI.validate();
        machineGUI.repaint();
    }

    // 顯示販賣機畫面
    public void startView() {
        machineGUI.setSize(550, 400);
        machineGUI.setVisible(true);
    }
}
