package de.storchp.opentracks.osmplugin.dashboardapi;

import java.util.ArrayList;
import java.util.List;

class Bounds {
    public double minlat;
    public double minlon;
    public double maxlat;
    public double maxlon;

    // Constructor
    public Bounds(double minlat, double minlon, double maxlat, double maxlon) {
        this.minlat = minlat;
        this.minlon = minlon;
        this.maxlat = maxlat;
        this.maxlon = maxlon;
    }

    public Bounds() {

    }
}

public class ChairLift {

    private List<ChairLiftElements> chairLifts = new ArrayList<>();

    private static ChairLift instance = null;

    private ChairLift() {
    }

    // Static method to create instance of Singleton class
    public static synchronized ChairLift getInstance() {
        if (instance == null)
            instance = new ChairLift();

        return instance;
    }

    public List<ChairLiftElements> getChairLifts() {
        return chairLifts;
    }

    public void addChairLiftData(ChairLiftElements chair) {
        chairLifts.add(chairLift);
    }

    public void clearData() {
        chairs.clear();
    }

}

class Geometry {
    public double lat;
    public double lon;

    // Constructor
    public Geometry(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Geometry() {

    }
}

class Tags {
    public String name;
    public String aerialway;
    public String aerialway_occupancy;
    public String aerialway_bubble;
    public String aerialway_heating;
    public String aerialway_duration;

    public Tags(String name, String aerialway, String aerialway_occupancy, String aerialway_bubble,
            String aerialway_heating, String aerialway_duration) {
        this.name = name;
        this.aerialway = aerialway;
        this.aerialway_occupancy = aerialway_occupancy;
        this.aerialway_bubble = aerialway_bubble;
        this.aerialway_heating = aerialway_heating;
        this.aerialway_duration = aerialway_duration;
    }

    public Tags() {

    }
}
