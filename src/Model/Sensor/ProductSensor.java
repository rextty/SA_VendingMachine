package Model.Sensor;

import javax.swing.*;

public class ProductSensor {

    private String productId;

    private boolean isError;

    public ProductSensor() {
        productId = "";
        isError = false;
    }

    public void addProductId(String productId) {
        this.productId += productId;
    }

    public void deleteProductId() {
        productId = productId.substring(0, productId.length() - 1);
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setError(boolean error) {
        this.isError = error;
    }

    public String getProductId() {
        return productId;
    }

    public boolean getError() {
        return isError;
    }

    public void replenishment() {
        JOptionPane.showMessageDialog(null, "Product have been shipped.");
    }
}
