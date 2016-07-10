package ge.edu.freeuni.sdp.iot.chat.bot.model;

import org.json.JSONObject;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class House {
    private String rowKey;
    private String partitionKey;
    private String timestamp;
    private String name;
    private String geoLocation;
    private String sprinklerIp;
    private String heatIp;
    private String ventIp;

    public House(String rowKey, String partitionKey, String timestamp, String name, String geoLocation, String sprinklerIp, String heatIp, String ventIp) {
        this.rowKey = rowKey;
        this.partitionKey = partitionKey;
        this.timestamp = timestamp;
        this.name = name;
        this.geoLocation = geoLocation;
        this.sprinklerIp = sprinklerIp;
        this.heatIp = heatIp;
        this.ventIp = ventIp;
    }

    public String getRowKey() {
        return rowKey;
    }

    public String getPartitionKey() {
        return partitionKey;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getName() {
        return name;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public String getSprinklerIp() {
        return sprinklerIp;
    }

    public String getHeatIp() {
        return heatIp;
    }

    public String getVentIp() {
        return ventIp;
    }

    public static House fromJson(JSONObject jsonObject) {
        String rowKey = jsonObject.getJSONObject("RowKey").getString("_");
        String partitionKey = jsonObject.getJSONObject("PartitionKey").getString("_");
        String timestamp = jsonObject.getJSONObject("Timestamp").getString("_");
        String name = jsonObject.getJSONObject("name").getString("_");
        String geoLocation = jsonObject.getJSONObject("geo_location").getString("_");
        String sprinklerIp = jsonObject.getJSONObject("sprinkler_ip").getString("_");
        String heatIp = jsonObject.getJSONObject("heat_ip").getString("_");
        String ventIp = jsonObject.getJSONObject("vent_ip").getString("_");
        return new House(rowKey, partitionKey, timestamp, name, geoLocation, sprinklerIp, heatIp, ventIp);
    }

    @Override
    public String toString() {
        return name;
    }
}
