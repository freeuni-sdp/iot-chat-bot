package ge.edu.freeuni.sdp.iot.chat.bot.model;

import org.json.JSONObject;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class BathLight {
    String houseId;
    String time;
    String status;

    public BathLight(String houseId, String time, String status) {
        this.houseId = houseId;
        this.time = time;
        this.status = status;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        String onOff = status.equals("true") ? "On" : "Off";
        return "Light: " + onOff + ", Light Switch Time: " + time;
    }

    public static BathLight fromJson(JSONObject jsonObject) {
        String houseId = jsonObject.getString("houseId");
        String time = jsonObject.getString("time");
        String status = jsonObject.getString("status");
        return new BathLight(houseId, time, status);
    }
}
