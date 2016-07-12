package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.SprinklerSwitch;
import org.json.JSONObject;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * Created by Nikoloz on 07/11/16.
 */
public class SprinklerSwitchServiceProxy extends ServiceProxy {
    public SprinklerSwitchServiceProxy(String uri) {
        super(uri);
    }

    public SprinklerSwitch getSprinklerStatus() {
        Response response = client.
                target(uri)
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
        return SprinklerSwitch.fromJson(object);
    }

    public SprinklerSwitch changeSprinklerStatus(String status, int timeout) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("set_status", status);
        jsonObject.put("timeout", timeout);
        Response response = client
                .target(uri)
                .request()
                .header("Content-Type", "application/json")
                .put(Entity.json(jsonObject.toString()));

        if (is404(response)) {
            return null;
        }
        JSONObject object;
        try {
            object = new JSONObject(response.readEntity(String.class));
        } catch (Exception e) {
            return null;
        }
        return SprinklerSwitch.fromJson(object);
    }
}
