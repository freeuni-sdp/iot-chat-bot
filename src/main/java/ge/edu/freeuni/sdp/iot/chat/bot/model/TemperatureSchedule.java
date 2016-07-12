package ge.edu.freeuni.sdp.iot.chat.bot.model;

import org.json.JSONObject;

/**
 * Created by Nikoloz on 07/12/16.
 */
public class TemperatureSchedule {
    String dateFrom;
    String dateTo;
    int temperature;

    public TemperatureSchedule(String dateFrom, String dateTo, int temperature) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.temperature = temperature;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    @Override
    public String toString() {
        return "Date From: " + dateFrom + ", Date To: " + dateTo + ", Temperature: " + temperature;
    }

    public static TemperatureSchedule fromJson(JSONObject object) {
        String dateFrom = object.getString("dateFrom");
        String dateTo = object.getString("dateTo");
        int temperature = object.getInt("temperature");
        return new TemperatureSchedule(dateFrom, dateTo, temperature);
    }
}
