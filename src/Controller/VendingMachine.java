package Controller;

import Model.Sensor.MoneySensor;
import Model.Sensor.ProductSensor;
import Model.Sensor.TemperatureSensor;
import View.MachineGUI;

public class VendingMachine{

    private MachineGUI machineGUI;

    private MoneySensor moneySensor;

    private ProductSensor productSensor;

    private TemperatureSensor temperatureSensor;

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

        this.machineGUI.getPut1NTDButton().addActionListener(e -> {
            moneySensor.addAmount(1);
            this.machineGUI.getAmountLabel().setText(Integer.toString(moneySensor.getAmount()));
        });

        this.machineGUI.getPut5NTDButton().addActionListener(e -> {
            moneySensor.addAmount(5);
            this.machineGUI.getAmountLabel().setText(Integer.toString(moneySensor.getAmount()));
        });

        this.machineGUI.getPut10NTDButton().addActionListener(e -> {
            moneySensor.addAmount(10);
            this.machineGUI.getAmountLabel().setText(Integer.toString(moneySensor.getAmount()));
        });

        this.machineGUI.getPut50NTDButton().addActionListener(e -> {
            moneySensor.addAmount(50);
            this.machineGUI.getAmountLabel().setText(Integer.toString(moneySensor.getAmount()));
        });

        this.machineGUI.getPut100NTDButton().addActionListener(e -> {
            moneySensor.addAmount(100);
            this.machineGUI.getAmountLabel().setText(Integer.toString(moneySensor.getAmount()));
        });

        this.machineGUI.getPut500NTDButton().addActionListener(e -> {
            moneySensor.addAmount(500);
            this.machineGUI.getAmountLabel().setText(Integer.toString(moneySensor.getAmount()));
        });

        this.machineGUI.getPut1000NTDButton().addActionListener(e -> {
            moneySensor.addAmount(1000);
            this.machineGUI.getAmountLabel().setText(Integer.toString(moneySensor.getAmount()));
        });
    }

    public void startView() {
        machineGUI.setVisible(true);
    }
}
