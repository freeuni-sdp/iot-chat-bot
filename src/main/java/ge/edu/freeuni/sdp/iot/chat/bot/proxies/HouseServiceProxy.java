package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import com.fasterxml.jackson.core.JsonParseException;
import ge.edu.freeuni.sdp.iot.chat.bot.model.House;
import org.json.JSONArray;
import org.json.JSONObject;

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
        Response response = client.
                target(uri)
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
        List<House> houses = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            houses.add(House.fromJson(object));
        }
        return houses;
    }


}
