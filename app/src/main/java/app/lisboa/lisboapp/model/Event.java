package app.lisboa.lisboapp.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajat Anantharam on 01/11/16.
 */

public class Event {

    public String eventName;
    public String hostName;
    public List<String> attendees;
    public String locationName;
    public Double latitude;
    public Double longitude;
    public String timeStamp;


    public Event getDummyEvent(){

        eventName = "Let's grab a cup of coffee";
        hostName = "Rajat";
        attendees = new ArrayList<>();
        attendees.add("Noortje");
        attendees.add("Roel");
        attendees.add("Joris");
        attendees.add("Anjali");
        locationName = "Main hall";
        latitude = 0.0;
        longitude = 0.0;
        timeStamp = "In an hour";
        return this;
    }
}
