package ge.edu.freeuni.sdp.iot.chat.bot.model;
import org.json.JSONObject;

/**
 * Created by Koko on 12.07.2016.
 */
public class HeatingSwitch {
    String id;
    boolean status;
    boolean available;

    public HeatingSwitch(String id, boolean status, boolean available) {
        this.id = id;
        this.status = status;
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "status = " + status +
                ", available = " + available;
    }

    public static HeatingSwitch fromJson(JSONObject object) {
        String id = object.getString("id");
        boolean status = object.getBoolean("status");
        boolean available = object.getBoolean("available");
        return new HeatingSwitch(id, status, available);
    }
}

