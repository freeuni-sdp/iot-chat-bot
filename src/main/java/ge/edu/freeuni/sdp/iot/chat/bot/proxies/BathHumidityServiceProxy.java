package ge.edu.freeuni.sdp.iot.chat.bot.proxies;

import ge.edu.freeuni.sdp.iot.chat.bot.model.BathHumidity;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class BathHumidityServiceProxy extends ServiceProxy {
    public BathHumidityServiceProxy(String uri) {
        super(uri);
    }

    public List<BathHumidity> getBathHumidities(int nMeasurements) {
        String str = client.
                target(uri + "/num_measurements/" + nMeasurements)
                .request()
                .get(String.class);
        JSONArray array = new JSONArray(str);
        List<BathHumidity> humidities = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            humidities.add(BathHumidity.fromJson(array.getJSONObject(i)));
        }
        return humidities;
    }
}
