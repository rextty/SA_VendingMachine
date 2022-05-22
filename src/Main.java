import Controller.VendingMachine;
import View.MachineGUI;


public class Main {

    public static void main(String[] args) {

        MachineGUI gui = new MachineGUI("Smart VendingMachine");

        VendingMachine controller = new VendingMachine(gui);
        controller.startView();
    }
}