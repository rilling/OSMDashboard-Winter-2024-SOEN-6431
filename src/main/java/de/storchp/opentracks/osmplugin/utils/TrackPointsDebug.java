package de.storchp.opentracks.osmplugin.utils;

public class TrackPointsDebug {
    private int trackpointsReceived = 0;
    public int trackpointsInvalid = 0;
    public int trackpointsDrawn = 0;
    public int trackpointsPause = 0;
    public int segments = 0;

    public int getTrackpointsReceived() {
        return trackpointsReceived;
    }

    public void setTrackpointsReceived(int trackpointsReceived) {
        this.trackpointsReceived = trackpointsReceived;
    }

    public void add(final TrackPointsDebug other) {
        this.trackpointsReceived.setTrackpointsReceived(this.trackpointsReceived + other.trackpointsReceived());
        this.trackpointsInvalid += other.trackpointsInvalid;
        this.trackpointsDrawn += other.trackpointsDrawn;
        this.trackpointsPause += other.trackpointsPause;
        this.segments += other.segments;
    }
}
