package app.lisboa.lisboapp.model;

/**
 * Created by Rajat Anantharam on 01/11/16.
 */

public class Event {

    public String eventName;
    public String hostName;
    public Double latitude;
    public String locationName;
    public Double longitude;
    public String timeStamp;

    public Event(){

    }

    public Event(String eventName, String hostName, Double lat, String locationName, Double lon, String timeStamp) {
        this.eventName = eventName;
        this.hostName = hostName;
        this.latitude = lat;
        this.locationName = locationName;
        this.longitude = lon;
        this.timeStamp = timeStamp;
    }
}
