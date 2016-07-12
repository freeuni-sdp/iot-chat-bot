package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.AirConditioningSwitch;
import ge.edu.freeuni.sdp.iot.chat.bot.model.BathVentSwitch;
import org.json.JSONObject;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * Created by Koko on 11.07.2016.
 */
public class AirConditioningSwitchProxy extends ServiceProxy {
    public AirConditioningSwitchProxy(String uri) {
        super(uri);
    }

    public AirConditioningSwitch getAirConditioningStatus() {
        Response response = client.
                target(uri)
                .request()
                .get();
        if (!isSuccess(response)) {
            return null;
        }
        JSONObject object;
        try {
            object = new JSONObject(response.readEntity(String.class));
        } catch (Exception e) {
            return null;
        }
        return AirConditioningSwitch.fromJson(object);
    }

    public boolean changeAirConditioningStatus(String status) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);
        Response response = client
                .target(uri)
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(jsonObject.toString()));
        return isSuccess(response);
    }
}
