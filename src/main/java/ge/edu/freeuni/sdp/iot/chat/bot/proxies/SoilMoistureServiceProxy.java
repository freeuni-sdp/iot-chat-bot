package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.SoilMoisture;
import org.json.JSONObject;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class SoilMoistureServiceProxy extends ServiceProxy {
    public SoilMoistureServiceProxy(String uri) {
        super(uri);
    }

    public SoilMoisture getSoilMoisture() {
        String str = client.
                target(uri)
                .request()
                .get(String.class);
        JSONObject object = new JSONObject(str);
        return SoilMoisture.fromJson(object);
    }
}
