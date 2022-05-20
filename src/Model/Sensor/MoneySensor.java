package Model.Sensor;

public class MoneySensor {

    private int amount;

    public MoneySensor() {
        amount = 0;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
