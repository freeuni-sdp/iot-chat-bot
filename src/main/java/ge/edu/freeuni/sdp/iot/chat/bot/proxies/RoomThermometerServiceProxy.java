package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.RoomThermometer;
import org.json.JSONArray;
import org.json.JSONObject;

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
        String str = client.
                target(uri)
                .request()
                .get(String.class);
        JSONArray array = new JSONArray(str);
        List<RoomThermometer> roomThermometers = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            roomThermometers.add(RoomThermometer.fromJson(array.getJSONObject(i)));
        }
        return roomThermometers;
    }

    public RoomThermometer getFromFloor(int floor) {
        String str = client.
                target(uri + "/" + floor)
                .request()
                .get(String.class);
        JSONObject jsonObject = new JSONObject(str);
        return RoomThermometer.fromJson(jsonObject);
    }
}
