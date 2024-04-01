package de.storchp.opentracks.osmplugin.dashboardapi;

import static de.storchp.opentracks.osmplugin.dashboardapi.APIConstants.LAT_LON_FACTOR;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import org.oscim.core.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;


import de.storchp.opentracks.osmplugin.utils.MapUtils;
import de.storchp.opentracks.osmplugin.utils.TrackPointsDebug;

public class TrackPoint {

    public static final String _ID = "_id";
    public static final String TRACKID = "trackid";
    public static final String LONGITUDE = "longitude";
    public static final String LATITUDE = "latitude";
    public static final String TIME = "time";
    public static final String TYPE = "type";
    public static final String SPEED = "speed";
    public static final double PAUSE_LATITUDE = 100.0;

    public static final String[] PROJECTION_V1 = {
            _ID,
            TRACKID,
            LATITUDE,
            LONGITUDE,
            TIME,
            SPEED
    };

    public static final String[] PROJECTION_V2 = {
            _ID,
            TRACKID,
            LATITUDE,
            LONGITUDE,
            TIME,
            TYPE,
            SPEED
    };

    private final long trackPointId;
    private final long trackId;
    private final GeoPoint latLong;
    private final boolean pause;
    private final double speed;
    private final String time; // Assuming time is stored as a long timestamp


    public TrackPoint(long trackId, long trackPointId, double latitude, double longitude, Integer type, double speed, String time) {
        this.trackId = trackId;
        this.trackPointId = trackPointId;
        if (MapUtils.isValid(latitude, longitude)) {
            this.latLong = new GeoPoint(latitude, longitude);
        } else {
            latLong = null;
        }
        this.pause = type != null ? type == 3 : latitude == PAUSE_LATITUDE;
        this.speed = speed;
        this.time = time;
    }

    public boolean hasValidLocation() {
        return latLong != null;
    }

    public boolean isPause() {
        return pause;
    }

    @Override
    public String toString() {
        return "TrackPoint{" +
                "trackPointId=" + trackPointId +
                ", trackId=" + trackId +
                ", latLong=" + latLong +
                ", pause=" + pause +
                ", speed=" + speed +
                ",time=" + time+
                '}';
    }

    /**
     * Reads the TrackPoints from the Content Uri and split by segments.
     * Pause TrackPoints and different Track IDs split the segments.
     */
    public static TrackPointsBySegments readTrackPointsBySegments(ContentResolver resolver, Uri data, long lastTrackPointId, int protocolVersion) {
        var debug = new TrackPointsDebug();
        var segments = new ArrayList<List<TrackPoint>>();
        var projection = PROJECTION_V2;
        var typeQuery = " AND " + TrackPoint.TYPE + " IN (-2, -1, 0, 1, 3)";
        if (protocolVersion < 2) { // fallback to old Dashboard API
            projection = PROJECTION_V1;
            typeQuery = "";
        }
        try (Cursor cursor = resolver.query(data, projection, TrackPoint._ID + "> ?" + typeQuery, new String[]{Long.toString(lastTrackPointId)}, null)) {
            TrackPoint lastTrackPoint = null;
           // lastTrackPoint.getTime(); // Direct call for testing
            List<TrackPoint> segment = null;
            while (cursor.moveToNext()) {
                debug.trackpointsReceived++;
                var trackPointId = cursor.getLong(cursor.getColumnIndexOrThrow(TrackPoint._ID));
                var trackId = cursor.getLong(cursor.getColumnIndexOrThrow(TrackPoint.TRACKID));
                var latitude = cursor.getInt(cursor.getColumnIndexOrThrow(TrackPoint.LATITUDE)) / LAT_LON_FACTOR;
                var longitude = cursor.getInt(cursor.getColumnIndexOrThrow(TrackPoint.LONGITUDE)) / LAT_LON_FACTOR;
                var typeIndex = cursor.getColumnIndex(TrackPoint.TYPE);
                var speed = cursor.getDouble(cursor.getColumnIndexOrThrow(TrackPoint.SPEED));
                var time = cursor.getString(cursor.getColumnIndexOrThrow(TrackPoint.TIME)); // Fetch the time value for the current track point
                Log.d("ttt", "Fetched time from cursor: " + time);
                String formattedTime;
                try {
                    long timeInMillis = Long.parseLong(time);
                    Date date = new Date(timeInMillis);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    formattedTime = dateFormat.format(date);
                } catch (NumberFormatException e) {
                    Log.e("TrackPointTime", "Error parsing timestamp: " + time, e);
                    formattedTime = "Invalid timestamp"; // Fallback in case of parsing error
                }


                Integer type = null;
                if (typeIndex > -1) {
                    type = cursor.getInt(typeIndex);
                }

                if (lastTrackPoint == null || lastTrackPoint.trackId != trackId) {
                    segment = new ArrayList<>();
                    segments.add(segment);
                }

                lastTrackPoint = new TrackPoint(trackId, trackPointId, latitude, longitude, type, speed , formattedTime);
                Log.d("lastTrackPoint", String.valueOf(lastTrackPoint));
                if (lastTrackPoint.hasValidLocation()) {
                    segment.add(lastTrackPoint);
                } else if (!lastTrackPoint.isPause()) {
                    debug.trackpointsInvalid++;
                }
                if (lastTrackPoint.isPause()) {
                    debug.trackpointsPause++;
                    if (!lastTrackPoint.hasValidLocation()) {
                       if (segment.size() > 0) {
                           var previousTrackpoint = segment.get(segment.size() - 1);
                           if (previousTrackpoint.hasValidLocation()) {
                               segment.add(new TrackPoint(trackId, trackPointId, previousTrackpoint.getLatLong().getLatitude(), previousTrackpoint.getLatLong().getLongitude(), type, speed, formattedTime));
                           }
                       }
                    }
                    lastTrackPoint = null;
                }
            }
        }
        debug.segments = segments.size();

        return new TrackPointsBySegments(segments, debug);
    }

    public long getTrackPointId() {
//        Log.d("time", String.valueOf(trackPointId));
        return trackPointId;
    }

    public long getTrackId() {
        return trackId;
    }

    public GeoPoint getLatLong() {
//        Log.d("time", String.valueOf(latLong));

        return latLong;
    }

    public double getSpeed() {
//        Log.d("time", String.valueOf(speed));

        return speed;
    }
   public String getTime() {
//        Log.d("TrackPointTime", "Entering getTime method");
//        try {
//            OffsetDateTime parsedTime = OffsetDateTime.parse(time, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
//            String formattedTime = parsedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            Log.d("FormattedTime", formattedTime); // Correct logging to see the formatted time
//            return formattedTime; // Return the formatted time instead of the original string
//        } catch (Exception e) {
//            Log.e("TrackPointTime", "Error parsing time: " + time, e);
            return time; // Fallback to original time string in case of parsing error
        }
//    }


}
