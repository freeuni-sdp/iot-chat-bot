package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.House;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class HouseServiceProxy extends ServiceProxy{

    public HouseServiceProxy(String uri) {
        super(uri);
    }

    public List<House> getAll() {
        String str = client.
                target(uri)
                .request()
                .get(String.class);
        JSONArray array = new JSONArray(str);
        List<House> houses = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            houses.add(House.fromJson(object));
        }
        return houses;
    }


}
