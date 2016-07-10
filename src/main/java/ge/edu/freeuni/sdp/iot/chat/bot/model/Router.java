package ge.edu.freeuni.sdp.iot.chat.bot.model;

import org.json.JSONObject;

/**
 * Created by Nikoloz on 07/10/16.
 */
public class Router {
    private String deviceName;
    private String deviceMacAddress;
    private String mac_id;

    public Router(String deviceName, String deviceMacAddress, String mac_id) {
        this.deviceName = deviceName;
        this.deviceMacAddress = deviceMacAddress;
        this.mac_id = mac_id;
    }

    public String getDeviceMacAddress() {
        return deviceMacAddress;
    }

    public void setDeviceMacAddress(String deviceMacAddress) {
        this.deviceMacAddress = deviceMacAddress;
    }

    public String getMac_id() {
        return mac_id;
    }

    public void setMac_id(String mac_id) {
        this.mac_id = mac_id;
    }

    public String getDeviceName() {

        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    @Override
    public String toString() {
        return "DeviceName: " + deviceName + ", DeviceMacAddress " + deviceMacAddress;
    }

    public static Router fromJson(JSONObject jsonObject) {
        String deviceName = jsonObject.getString("deviceName");
        String deviceMacAddress = jsonObject.getString("deviceMacAddress");
        String mac_id = jsonObject.getString("mac_id");
        return new Router(deviceName, deviceMacAddress, mac_id);
    }
}
