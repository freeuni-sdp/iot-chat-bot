package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.SoilMoisture;
import org.json.JSONObject;

import javax.ws.rs.core.Response;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class SoilMoistureServiceProxy extends ServiceProxy {
    public SoilMoistureServiceProxy(String uri) {
        super(uri);
    }

    public SoilMoisture getSoilMoisture() {
        Response response = client.
                target(uri)
                .request()
                .get();
        if (isSuccess(response))
            return null;
        JSONObject object;
        try {
            object = new JSONObject(response.readEntity(String.class));
        } catch (Exception e) {
            return null;
        }
        return SoilMoisture.fromJson(object);
    }
}
