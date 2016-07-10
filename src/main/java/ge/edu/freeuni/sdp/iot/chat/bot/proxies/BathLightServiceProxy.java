package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.BathLight;
import org.json.JSONObject;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class BathLightServiceProxy extends ServiceProxy {
    public BathLightServiceProxy(String uri) {
        super(uri);
    }

    public BathLight getBathLight(String houseId) {
        String str = client.
                target(uri + "/house/" + houseId)
                .request()
                .get(String.class);
        JSONObject object = new JSONObject(str);
        return BathLight.fromJson(object);
    }
}
