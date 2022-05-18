package Controller;

import Model.Sensor.MoneyInterface;
import Model.Sensor.MoneySensor;
import Model.Sensor.ProductSensor;
import Model.Sensor.TemperatureSensor;
import View.MachineGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VendingMachine implements ActionListener, MoneyInterface {

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
        this.machineGUI.getPut1NTDButton().setActionCommand(ONE_NTD);
        this.machineGUI.getPut5NTDButton().setActionCommand(FIVE_NTD);
        this.machineGUI.getPut10NTDButton().setActionCommand(TEN_NTD);
        this.machineGUI.getPut50NTDButton().setActionCommand(FIF_NTD);
        this.machineGUI.getPut100NTDButton().setActionCommand(ONE_HUNDRED_NTD);
        this.machineGUI.getPut500NTDButton().setActionCommand(FIVE_HUNDRED_NTD);
        this.machineGUI.getPut1000NTDButton().setActionCommand(ONE_THOUSAND_NTD);

        this.machineGUI.getPut1NTDButton().addActionListener(this);
        this.machineGUI.getPut5NTDButton().addActionListener(this);
        this.machineGUI.getPut10NTDButton().addActionListener(this);
        this.machineGUI.getPut50NTDButton().addActionListener(this);
        this.machineGUI.getPut100NTDButton().addActionListener(this);
        this.machineGUI.getPut500NTDButton().addActionListener(this);
        this.machineGUI.getPut1000NTDButton().addActionListener(this);
    }

    public void startView() {
        machineGUI.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case ONE_NTD -> moneySensor.addAmount(1);
            case FIVE_NTD -> moneySensor.addAmount(5);
            case TEN_NTD -> moneySensor.addAmount(10);
            case FIF_NTD -> moneySensor.addAmount(50);
            case ONE_HUNDRED_NTD -> moneySensor.addAmount(100);
            case FIVE_HUNDRED_NTD -> moneySensor.addAmount(500);
            case ONE_THOUSAND_NTD -> moneySensor.addAmount(1000);
        }

        this.machineGUI.getAmountLabel().setText(Integer.toString(moneySensor.getAmount()));
    }
}
