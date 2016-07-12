package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.RoomThermometer;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class RoomThermometerServiceProxy extends ServiceProxy {
    public RoomThermometerServiceProxy(String uri) {
        super(uri);
    }

    public List<RoomThermometer> getAll() {
        Response response = client.
                target(uri)
                .request()
                .get();
        if (!isSuccess(response))
            return null;
        JSONArray array;
        try {
            array = new JSONArray(response.readEntity(String.class));
        } catch (Exception e){
            return null;
        }
        List<RoomThermometer> roomThermometers = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            roomThermometers.add(RoomThermometer.fromJson(array.getJSONObject(i)));
        }
        return roomThermometers;
    }

    public RoomThermometer getFromFloor(int floor) {
        Response response = client.
                target(uri + "/" + floor)
                .request()
                .get();
        if (!isSuccess(response))
            return null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(response.readEntity(String.class));
        } catch (Exception e) {
            return null;
        }
        return RoomThermometer.fromJson(jsonObject);
    }
}
