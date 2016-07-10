package ge.edu.freeuni.sdp.iot.chat.bot;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;


public class PingURLs {

    public void printPings() {

        String[] hostList = { "https://iot-bath-vent-switch.herokuapp.com/ping","https://bath-climate.herokuapp.com/webapi/ping",
                "https://iot-bath-light-sensor.herokuapp.com/webapi/ping","https://iot-bath-humidity-sensor.herokuapp.com/webapi/ping",
                "https://iot-router.herokuapp.com/webapi/ping","https://iot-air-conditioning-switch.herokuapp.com/webapi/ping",
                "https://iot-room-thermometer.herokuapp.com/webapi/ping","https://iot-heating-switch.herokuapp.com/ping",
                "https://iot-temperature-scheduler.herokuapp.com/ping","https://iot-room-climate-regulator.herokuapp.com/webapi/ping",
                "https://iot-weather.herokuapp.com/webapi/ping","https://iot-camera-object-recognizer.herokuapp.com/webapi/ping",
                "https://iot-soil-moisture-sensor.herokuapp.com/ping","https://iot-sprinkler-switch.herokuapp.com/webapi/ping",
                "https://iot-sprinkler-scheduler.herokuapp.com/webapi/ping","https://iot-sprinkler.herokuapp.com/webapi/ping"
        };
        ClientConfig config = new ClientConfig().register(JacksonFeature.class);
        Client client = ClientBuilder.newClient(config);
        for (int i = 0; i < hostList.length; i++) {

            String url = hostList[i];
            String status = getStatus(url, client);

            System.out.println(url + "\t\tStatus:" + status);
        }

    }

    public String getStatus(String url, Client client) {
        long start = 0;
        long end = 0;
        String b = "Error occured while pinging. ";
        String result = "";
        try {

            start = System.currentTimeMillis();
            Response response = client.target(url).request().get();

            int code = response.getStatus();
            end = System.currentTimeMillis();
            b = Long.toString(end - start);
            result = "Response Code: " + code + "; Duration: " + b + " ms;";
        } catch (Exception e) {
            result = b;
        }

        return result;
    }

}