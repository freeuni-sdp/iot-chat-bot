package ge.edu.freeuni.sdp.iot.chat.bot.model;

import org.json.JSONObject;

/**
 * Created by Nikoloz on 07/11/16.
 */
public class SprinklerSwitch {
    String house_id;
    String status;
    int seconds_left;

    public SprinklerSwitch(String house_id, String status, int seconds_left) {
        this.house_id = house_id;
        this.status = status;
        this.seconds_left = seconds_left;
    }

    public String getHouse_id() {
        return house_id;
    }

    public void setHouse_id(String house_id) {
        this.house_id = house_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSeconds_left() {
        return seconds_left;
    }

    public void setSeconds_left(int seconds_left) {
        this.seconds_left = seconds_left;
    }

    public boolean isOn() {
        return status.equals("on");
    }

    @Override
    public String toString() {
        return "Status: " + status + ", Seconds Left: " + seconds_left;
    }

    public static SprinklerSwitch fromJson(JSONObject jsonObject) {
        String house_id = jsonObject.getString("house_id");
        String status = jsonObject.getString("status");
        int seconds_left = jsonObject.optInt("seconds_left", 0);
        return new SprinklerSwitch(house_id, status, seconds_left);
    }
}
