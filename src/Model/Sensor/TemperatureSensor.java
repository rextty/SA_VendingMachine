package Model.Sensor;

public class TemperatureSensor {
    private double temperature;

    private boolean isError;

    public TemperatureSensor() {
        isError = false;
    }


    public void setError(boolean error) {
        isError = error;
    }

    public double getTemperature() {
        return temperature;
    }

    public boolean getError() {
        return isError;
    }


}
