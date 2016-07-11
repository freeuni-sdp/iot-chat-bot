package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.BathVentSwitch;
import org.json.JSONObject;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

/**
 * Created by Koko on 11.07.2016.
 */
public class BathVentSwitchProxy extends ServiceProxy {
    public BathVentSwitchProxy(String uri) {
        super(uri);
    }

    public BathVentSwitch getBathVentStatus() {
        String str = client.
                target(uri)
                .request()
                .get(String.class);
        JSONObject object = new JSONObject(str);
        return BathVentSwitch.fromJson(object);
    }

    public BathVentSwitch changeBathVentStatus(String status, int timeout) {
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
        String output = response.readEntity(String.class);
        return  BathVentSwitch.fromJson(new JSONObject(output));
    }
}
