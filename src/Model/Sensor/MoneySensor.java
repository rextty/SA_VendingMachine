package Model.Sensor;

//TODO: 功能內聚
public class MoneySensor {

    private int amount;

    private boolean isError;

    public MoneySensor() {
        amount = 0;
        isError = false;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setError(boolean error) {
        isError = error;
    }

    public int getAmount() {
        return amount;
    }

    public boolean getError() {
        return isError;
    }

}
