package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.BathLight;
import org.json.JSONObject;

import javax.ws.rs.core.Response;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class BathLightServiceProxy extends ServiceProxy {
    public BathLightServiceProxy(String uri) {
        super(uri);
    }

    public BathLight getBathLight(String houseId) {
        Response response = client.
                target(uri + "/house/" + houseId)
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
        return BathLight.fromJson(object);
    }
}
