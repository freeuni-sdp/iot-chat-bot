package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.Router;
import org.json.JSONArray;
import org.json.JSONObject;

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
        String str = client.
                target(uri + "/addresses")
                .request()
                .get(String.class);
        JSONArray array = new JSONArray(str);
        List<Router> routers = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            routers.add(Router.fromJson(array.getJSONObject(i)));
        }
        return routers;
    }

    public boolean isAnyoneAtHome() {
        String str = client.
                target(uri + "/available")
                .request()
                .get(String.class);
        JSONObject object = new JSONObject(str);
        return object.getBoolean("atHome");
    }
}
