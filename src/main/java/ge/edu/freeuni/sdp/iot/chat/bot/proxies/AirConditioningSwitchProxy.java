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
        String str = client.
                target(uri)
                .request()
                .get(String.class);
        JSONObject object = new JSONObject(str);
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
        if (is404(response)) {
            return false;
        }
        return true;
    }
}
