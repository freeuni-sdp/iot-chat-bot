package ge.edu.freeuni.sdp.iot.chat.bot.model;

import org.json.JSONObject;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class SoilMoisture {
    private double sensorValueMessage;
    private String houseId;
    private boolean available;

    public SoilMoisture(double sensorValueMessage, String houseId, boolean available) {
        this.sensorValueMessage = sensorValueMessage;
        this.houseId = houseId;
        this.available = available;
    }

    public double getSensorValueMessage() {
        return sensorValueMessage;
    }

    public void setSensorValueMessage(double sensorValueMessage) {
        this.sensorValueMessage = sensorValueMessage;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        String available = this.available ? "Yes" : "No";
        return "Sensor Value: " + sensorValueMessage + ", Available: " + available;
    }

    public static SoilMoisture fromJson(JSONObject jsonObject) {
        double sensorValueMessage = jsonObject.getDouble("sensorValueMessage");
        String houseId = jsonObject.getString("houseId");
        boolean available = jsonObject.getBoolean("available");
        return new SoilMoisture(sensorValueMessage, houseId, available);
    }
}
