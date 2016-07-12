package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.TemperatureSchedule;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikoloz on 07/12/16.
 */
public class TemperatureScheduleProxy extends ServiceProxy {
    public TemperatureScheduleProxy(String uri) {
        super(uri);
    }

    public List<TemperatureSchedule> getTemperatureSchedules(int floor) {
        Response response = client.
                target(uri + "/floors/" + floor + "/schedule?start=4&end=10")
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
        List<TemperatureSchedule> temperatureSchedules = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.getJSONObject(i);
            temperatureSchedules.add(TemperatureSchedule.fromJson(object));
        }
        return temperatureSchedules;
    }
}
