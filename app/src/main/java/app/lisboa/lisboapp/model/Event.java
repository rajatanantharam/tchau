package app.lisboa.lisboapp.model;

/**
 * Created by Rajat Anantharam on 01/11/16.
 */

public class Event {

    public String hostName;
    public String eventName;
    public String locationName;
    public Double latitude;
    public Double longitude;
    public long startTime;
    public int durationInMinutes;

    public Event(){

    }

    public Event(String host, String eventDesc, String locationName, Double latitude, Double longitude, long startTime, int durationInMinutes) {
        this.hostName = host;
        this.eventName = eventDesc;
        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startTime  = startTime;
        this.durationInMinutes = durationInMinutes;
    }
}
