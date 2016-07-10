package ge.edu.freeuni.sdp.iot.chat.bot;

import java.net.HttpURLConnection;
import java.net.URL;


public class PingURLs {

    public void printPings() {

        String[] hostList = { "https://iot-bath-vent-switch.herokuapp.com/ping","https://bath-climate.herokuapp.com/webapi/ping",
                "https://iot-bath-light-sensor.herokuapp.com/webapi/ping","https://iot-bath-humidity-sensor.herokuapp.com/webapi/ping",
                "https://iot-router.herokuapp.com/webapi/ping","https://iot-air-conditioning-switch.herokuapp.com/webapi/ping",
                "https://iot-room-thermometer.herokuapp.com/webapi/ping","https://iot-heating-switch.herokuapp.com/ping",
                "https://iot-temperature-scheduler.herokuapp.com/ping","https://iot-room-climate-regulator.herokuapp.com/webapi/ping",
                "https://iot-weather.herokuapp.com/webapi/ping","https://iot-camera-object-recognizer.herokuapp.com/webapi/ping",
                "https://iot-soil-moisture-sensor.herokuapp.com/ping","https://iot-sprinkler-switch.herokuapp.com/webapi/ping",
                "https://iot-sprinkler-scheduler.herokuapp.com/webapi/ping","\thttps://iot-sprinkler.herokuapp.com/webapi/ping"
        };

        for (int i = 0; i < hostList.length; i++) {

            String url = hostList[i];
            String status = getStatus(url);

            System.out.println(url + "\t\tStatus:" + status);
        }

    }

    public String getStatus(String url) {
        long start = 0;
        long end = 0;
        String b = "Error occured while pinging. ";
        String result = "";
        try {
            URL siteURL = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) siteURL
                    .openConnection();
            connection.setRequestMethod("GET");
            start = System.currentTimeMillis();
            connection.connect();

            int code = connection.getResponseCode();
            end = System.currentTimeMillis();
            b = Long.toString(end - start);
            result = "Response Code: " + code + "; Duration: " + b + " ms;";
        } catch (Exception e) {
            result = b;
        }

        return result;
    }

}