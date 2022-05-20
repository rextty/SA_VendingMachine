package Model.Sensor;

public class ProductSensor {

    private String productId;

    public ProductSensor() {
        productId = "";
    }

    public void addProductId(String productId) {
        this.productId += productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }
}
