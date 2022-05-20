import Controller.VendingMachine;
import Model.Sensor.MoneySensor;
import Model.Sensor.ProductSensor;
import Model.Sensor.TemperatureSensor;
import View.MachineGUI;

public class Main {
    public static void main(String[] args) {

        MachineGUI gui = new MachineGUI("Smart VendingMachine");
        MoneySensor moneySensor = new MoneySensor();
        ProductSensor productSensor = new ProductSensor();
        TemperatureSensor temperatureSensor = new TemperatureSensor();

        VendingMachine controller = new VendingMachine(gui, moneySensor, productSensor, temperatureSensor);

        controller.startView();
        controller.init();
    }
}