package Controller;

import Model.Entity.Product;
import Model.Sensor.MoneySensor;
import Model.Sensor.ProductSensor;
import Model.Sensor.TemperatureSensor;
import Model.Service.ProductService;
import View.MachineGUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class VendingMachine{

    private final MachineGUI machineGUI;

    private final MoneySensor moneySensor;

    private final ProductSensor productSensor;

    private final TemperatureSensor temperatureSensor;

    private ProductService productService;

    private int currentPage;

    private int maxPage;

    private int startPage;

    private int endPage;

    public VendingMachine(
            MachineGUI _gui,
            MoneySensor moneySensor,
            ProductSensor productSensor,
            TemperatureSensor temperatureSensor
    ) {
        this.machineGUI = _gui;
        this.moneySensor = moneySensor;
        this.productSensor = productSensor;
        this.temperatureSensor = temperatureSensor;
    }

    public void init() {
        currentPage = 1;

        startPage = 0;
        endPage = 9;

        productService = new ProductService();

        // 綁定投幣按鈕事件
        bingPutCoinListener();

        // 綁定商品按鈕事件
        bindProductSelectorListener();

        // 綁定切頁按鈕事件
        bindPageSwitcherListener();

        // 初始化商品頁面
        initProductPanel();
    }

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

            if (productId.length() == 0) {
                return;
            }

            productSensor.setProductId(productId.substring(0, productId.length() - 1));
            updateProductId();
        });

        machineGUI.getConfirmButton().addActionListener(e -> {
            setPanelEnabled(machineGUI.getRightPanel(), false);
        });
    }

    private void bindPageSwitcherListener() {
        machineGUI.getPrePageButton().addActionListener(e -> {
            currentPage--;
            updatePageSwitcher();

            if (currentPage == 1) {
                machineGUI.getPrePageButton().setEnabled(false);
            }

            startPage -= 9;
            endPage -= 9;

            machineGUI.getNextPageButton().setEnabled(true);
            updateProductPanel(startPage, endPage);
        });

        machineGUI.getNextPageButton().addActionListener(e -> {
            currentPage ++;
            updatePageSwitcher();

            if (currentPage == maxPage) {
                machineGUI.getNextPageButton().setEnabled(false);
            }

            startPage += 9;
            endPage += 9;

            machineGUI.getPrePageButton().setEnabled(true);
            updateProductPanel(startPage, endPage);
        });
    }

    private void initProductPanel() {
        List<Product> productList = productService.getAllProduct();

        maxPage = (int) Math.ceil(productList.size() / 9.0);

        updatePageSwitcher();
        updateProductPanel(startPage, endPage);
    }

    private void updateProductPanel(int start, int end) {
        try {
            int temp = 0;

            JPanel topProductPanel = machineGUI.getTopProductPanel();
            JPanel middleProductPanel = machineGUI.getMiddleProductPanel();
            JPanel bottomProductPanel = machineGUI.getBottomProductPanel();

            topProductPanel.removeAll();
            middleProductPanel.removeAll();
            bottomProductPanel.removeAll();

            List<Product> productList = productService.getAllProduct();

            for (int i = start; i <= end; i++) {
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

    private void updatePageSwitcher() {
        JLabel pageLabel = machineGUI.getPageLabel();
        pageLabel.setText(currentPage + "/"+ maxPage);
    }

    public void updateAmount() {
        machineGUI.getAmountLabel().setText(Integer.toString(moneySensor.getAmount()));
    }

    public void updateProductId() {
        machineGUI.getProductIdLabel().setText(productSensor.getProductId());
    }

    public void updateView() {
        machineGUI.validate();
        machineGUI.repaint();
    }

    public void startView() {
        machineGUI.setSize(550, 400);
        machineGUI.setVisible(true);
    }
}
