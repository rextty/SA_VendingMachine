package View;

import javax.swing.*;

public class MachineGUI extends JFrame {
    private JPanel mainPanel;
    private JPanel RightPanel;
    private JButton put1NTDButton;
    private JButton put5NTDButton;
    private JButton put10NTDButton;
    private JButton put50NTDButton;
    private JButton put100NTDButton;
    private JButton put500NTDButton;
    private JButton put1000NTDButton;
    private JLabel amountLabel;

    public MachineGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
    }

    public JLabel getAmountLabel() {
        return amountLabel;
    }

    public JButton getPut1NTDButton() {
        return put1NTDButton;
    }

    public JButton getPut5NTDButton() {
        return put5NTDButton;
    }

    public JButton getPut10NTDButton() {
        return put10NTDButton;
    }

    public JButton getPut50NTDButton() {
        return put50NTDButton;
    }

    public JButton getPut100NTDButton() {
        return put100NTDButton;
    }

    public JButton getPut500NTDButton() {
        return put500NTDButton;
    }

    public JButton getPut1000NTDButton() {
        return put1000NTDButton;
    }
}
