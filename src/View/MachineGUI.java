package View;

import javax.swing.*;

public class MachineGUI extends JFrame {
    private JPanel mainPanel;
    private JButton put1NTDButton;
    private JButton put5NTDButton;
    private JButton put10NTDButton;
    private JButton put50NTDButton;
    private JButton put100NTDButton;
    private JButton put500NTDButton;
    private JButton put1000NTDButton;
    private JLabel amountLabel;
    private JPanel RightPanel;
    private JButton refundButton;
    private JButton a1Button;
    private JButton a4Button;
    private JButton a7Button;
    private JButton deleteButton;
    private JLabel productIdLabel;
    private JButton a2Button;
    private JButton a3Button;
    private JButton a5Button;
    private JButton a8Button;
    private JButton a6Button;
    private JButton a9Button;
    private JButton a0Button;
    private JButton confirmButton;
    private JPanel LeftPanel;
    private JPanel productPanel;
    private JPanel topProductPanel;
    private JPanel middleProductPanel;
    private JPanel bottomProductPanel;
    private JButton prePageButton;
    private JButton nextPageButton;
    private JLabel pageLabel;
    private JTabbedPane cameraTabbedPane;

    public MachineGUI(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
    }

    public JLabel getAmountLabel() {
        return amountLabel;
    }

    public JPanel getRightPanel() {
        return RightPanel;
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

    public JButton getRefundButton() {
        return refundButton;
    }

    public JLabel getProductIdLabel() {
        return productIdLabel;
    }

    public JButton getA1Button() {
        return a1Button;
    }

    public JButton getA2Button() {
        return a2Button;
    }

    public JButton getA3Button() {
        return a3Button;
    }

    public JButton getA4Button() {
        return a4Button;
    }

    public JButton getA5Button() {
        return a5Button;
    }

    public JButton getA6Button() {
        return a6Button;
    }

    public JButton getA7Button() {
        return a7Button;
    }

    public JButton getA8Button() {
        return a8Button;
    }

    public JButton getA9Button() {
        return a9Button;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JButton getA0Button() {
        return a0Button;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public JPanel getProductPanel() {
        return productPanel;
    }

    public JPanel getTopProductPanel() {
        return topProductPanel;
    }

    public JPanel getMiddleProductPanel() {
        return middleProductPanel;
    }

    public JPanel getBottomProductPanel() {
        return bottomProductPanel;
    }

    public JButton getPrePageButton() {
        return prePageButton;
    }

    public JLabel getPageLabel() {
        return pageLabel;
    }

    public JButton getNextPageButton() {
        return nextPageButton;
    }

    public JTabbedPane getCameraTabbedPane() {
        return cameraTabbedPane;
    }
}
