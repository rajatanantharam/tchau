package app.lisboa.lisboapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Rajat Anantharam on 01/11/16.
 */

public class Event  implements Serializable{

    public String hostName;
    public String eventName;
    public String locationName;
    public Double latitude;
    public Double longitude;
    public long startTime;
    public int durationInMinutes;
    public String hostId;
    public List<String> attendees;

    public Event(){

    }

    public Event(String host, String eventDesc, String locationName, Double latitude, Double longitude, long startTime, int durationInMinutes, String hostId) {
        this.hostName = host;
        this.eventName = eventDesc;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startTime  = startTime;
        this.durationInMinutes = durationInMinutes;
        this.hostId = hostId;
    }

}
