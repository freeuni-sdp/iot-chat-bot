package ge.edu.freeuni.sdp.iot.chat.bot.model;

import org.json.JSONObject;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class RoomThermometer {
    private String house_id;
    private String floor_id;
    private double temperature;

    public RoomThermometer(String house_id, String floor_id, double temperature) {
        this.house_id = house_id;
        this.floor_id = floor_id;
        this.temperature = temperature;
    }

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getFloor_id() {
        return floor_id;
    }

    public void setFloor_id(String floor_id) {
        this.floor_id = floor_id;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Floor: " + floor_id + ", Temperature: " + temperature;
    }

    public static RoomThermometer fromJson(JSONObject jsonObject) {
        String house_id = jsonObject.getString("house_id");
        String floor_id = jsonObject.getString("floor_id");
        double temperature = jsonObject.getDouble("temperature");
        return new RoomThermometer(house_id, floor_id, temperature);
    }
}
