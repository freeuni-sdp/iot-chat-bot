package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.HeatingSwitch;
import org.json.JSONObject;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * Created by Koko on 12.07.2016.
 */
public class HeatingSwitchServiceProxy extends ServiceProxy {

    public HeatingSwitchServiceProxy(String uri) {
        super(uri);
    }

    public HeatingSwitch getSwitchStatusByFloor(String floor) {
        Response response = client.
                target(uri + "/floor/" + floor)
                .request()
                .get();
        if (!isSuccess(response))
            return null;
        JSONObject object;
        try {
            object = new JSONObject(response.readEntity(String.class));
        } catch (Exception e) {
            return null;
        }
        return HeatingSwitch.fromJson(object);
    }

    public boolean turnOnSwitch(String floor, int period) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("period", period);
        Response response = client
                .target(uri + "/floor/" + floor)
                .request()
                .header("Content-Type", "application/json")
                .put(Entity.json(jsonObject.toString()));
        return isSuccess(response);
    }

    public boolean turnOffSwitch(String floor) {
        Response response = client
                .target(uri + "/floor/" + floor)
                .request()
                .delete();
        return isSuccess(response);
    }
}
