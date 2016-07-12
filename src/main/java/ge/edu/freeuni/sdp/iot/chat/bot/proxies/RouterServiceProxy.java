package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.Router;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class RouterServiceProxy extends ServiceProxy{

    public RouterServiceProxy(String uri) {
        super(uri);
    }

    public List<Router> getAll() {
        Response response = client.
                target(uri + "/addresses")
                .request()
                .get();
        if (!isSuccess(response))
            return null;
        JSONArray array;
        try {
            array = new JSONArray(response.readEntity(String.class));
        } catch (Exception e) {
            return null;
        }
        List<Router> routers = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            routers.add(Router.fromJson(array.getJSONObject(i)));
        }
        return routers;
    }

    public boolean isAnyoneAtHome() {
        Response response = client.
                target(uri + "/available")
                .request()
                .get();
        if (!isSuccess(response))
            return false;
        JSONObject object;
        try {
            object = new JSONObject(response.readEntity(String.class));
        } catch (Exception e) {
            return false;
        }
        return object.getBoolean("atHome");
    }
}
