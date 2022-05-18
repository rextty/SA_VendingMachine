package Model.Sensor;

public class MoneySensor {

    private int amount;

    public MoneySensor() {

    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public int getAmount() {
        return amount;
    }
}
