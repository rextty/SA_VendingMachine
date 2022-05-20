package Controller;

import Model.Entity.Product;
import Model.Sensor.MoneySensor;
import Model.Sensor.ProductSensor;
import Model.Sensor.TemperatureSensor;
import Model.Service.ProductService;
import View.MachineGUI;
import com.jgoodies.forms.layout.FormLayout;

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
        productService = new ProductService();

        // 綁定投幣按鈕事件
        bingPutCoinListener();

        // 綁定商品按鈕事件
        bindProductSelectorListener();

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

    private void initProductPanel() {
        try {
            JPanel productPanel = machineGUI.getProductPanel();

            JPanel columnPanel = new JPanel();
            columnPanel.setLayout(new GridBagLayout());

            JPanel rowPanel = new JPanel();
            rowPanel.setLayout(new GridBagLayout());

            BufferedImage bufferedImage = ImageIO.read(new File("C:\\Users\\ian98\\IdeaProjects\\SA_VendingMachine\\Resource\\DiscordIcon.png"));

//            JLabel productItem = new JLabel(new ImageIcon(bufferedImage));
            JLabel productItem = new JLabel();
            JLabel productName = new JLabel();
            JLabel productPrice = new JLabel();
            JLabel productQuantity = new JLabel();

            List<Product> productList = productService.getAllProduct();

            for (Product product : productList) {
                productName.setText(product.getName());
                productPrice.setText(Integer.toString(product.getPrice()));
                productQuantity.setText(Integer.toString(product.getQuantity()));
            }

            rowPanel.add(productItem);
            rowPanel.add(productName);
            rowPanel.add(productPrice);
            rowPanel.add(productQuantity);

            columnPanel.add(rowPanel);

            productPanel.add(columnPanel);

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

    // 更新畫面金額
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
        machineGUI.setSize(1000, 500);
        machineGUI.setVisible(true);
    }
}
