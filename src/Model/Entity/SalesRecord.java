package Model.Entity;

public class SalesRecord {

    private int id;
    private int productId;

    private int machineSerialNumber;

    private String date;

    private int userId;

    public SalesRecord() {}

    public void setId(int id) {
        this.id = id;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void setMachineSerialNumber(int machineSerialNumber) {
        this.machineSerialNumber = machineSerialNumber;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getProductId() {
        return productId;
    }

    public int getMachineSerialNumber() {
        return machineSerialNumber;
    }

    public int getUserId() {
        return userId;
    }

    public String getDate() {
        return date;
    }
}
