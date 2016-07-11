package ge.edu.freeuni.sdp.iot.chat.bot.model;

import org.json.JSONObject;

/**
 * Created by Koko on 11.07.2016.
 */
public class AirConditioningSwitch {
    String status;

    public AirConditioningSwitch(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return "Status: " + status;
    }

    public static AirConditioningSwitch fromJson(JSONObject jsonObject) {
        String status = jsonObject.getString("status");
        return new AirConditioningSwitch(status);
    }
}
