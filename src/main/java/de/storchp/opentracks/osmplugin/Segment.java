package de.storchp.opentracks.osmplugin;
import org.oscim.core.GeoPoint;
public class Segment {
    public GeoPoint start;
    public GeoPoint end;

    public Segment(GeoPoint start, GeoPoint end) {
        this.start = start;
        this.end = end;
    }
}