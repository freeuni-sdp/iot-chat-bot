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
        String str = client.
                target(uri + "/floor/" + floor)
                .request()
                .get(String.class);
        JSONObject object = new JSONObject(str);
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
        if (is404(response)) {
            return false;
        }
        return true;
    }

    public boolean turnOffSwitch(String floor) {
        Response response = client
                .target(uri + "/floor/" + floor)
                .request()
                .delete();
        if (is404(response)) {
            return false;
        }
        return true;
    }
}
