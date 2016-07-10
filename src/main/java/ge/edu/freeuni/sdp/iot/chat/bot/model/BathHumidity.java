package ge.edu.freeuni.sdp.iot.chat.bot.model;

import org.json.JSONObject;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class BathHumidity {
    private double humidity;
    private String measurement_time;

    public BathHumidity(double humidity, String measurement_time) {
        this.humidity = humidity;
        this.measurement_time = measurement_time;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public String getMeasurement_time() {
        return measurement_time;
    }

    public void setMeasurement_time(String measurement_time) {
        this.measurement_time = measurement_time;
    }

    @Override
    public String toString() {
        return "Humidity: " + humidity + ", Measurement Time: " + measurement_time;
    }

    public static BathHumidity fromJson(JSONObject jsonObject) {
        double humidity = jsonObject.getDouble("humidity");
        String measurement_time = jsonObject.getString("measurement_time");
        return new BathHumidity(humidity, measurement_time);
    }
}
