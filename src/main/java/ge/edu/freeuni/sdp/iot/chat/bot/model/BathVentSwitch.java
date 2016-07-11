package ge.edu.freeuni.sdp.iot.chat.bot.model;

import org.json.JSONObject;

/**
 * Created by Koko on 11.07.2016.
 */
public class BathVentSwitch {
    String house_id;
    String status;
    boolean successed;

    public BathVentSwitch(String house_id, String status, boolean successed) {
        this.house_id = house_id;
        this.status = status;
        this.successed = successed;
    }

    public String getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return "Status: " + status;
    }

    public static BathVentSwitch fromJson(JSONObject jsonObject) {
        String house_id = jsonObject.getString("houseid");
        String status = jsonObject.getString("status");
        boolean successed = jsonObject.getBoolean("successed");
        return new BathVentSwitch(house_id, status, successed);
    }
}
