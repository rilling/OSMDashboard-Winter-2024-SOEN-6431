package de.storchp.opentracks.osmplugin.maps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import org.oscim.android.MapView;
import org.oscim.core.GeoPoint;
import org.oscim.map.Viewport;

public class TrailSelectionMapView extends MapView {

    private static final String TAG = TrailSelectionMapView.class.getSimpleName();

    // Interface for handling map touch events
    public interface OnMapTouchListener {
        void onMapTouch(GeoPoint geoPoint);
    }

    private OnMapTouchListener onMapTouchListener;

    public TrailSelectionMapView(Context context) {
        super(context);
    }

    public TrailSelectionMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // Set the listener for map touch events
    public void setOnMapTouchListener(OnMapTouchListener listener) {
        this.onMapTouchListener = listener;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        Viewport viewport = this.map().viewport();
        GeoPoint geoPoint = viewport.fromScreenPoint(x, y);

        // Log the touch event for debugging
        Log.d(TAG, "Map coordinates: " + geoPoint.getLatitude() + ", " + geoPoint.getLongitude());
        // Notify listener when there's a touch event
        if (event.getAction() == MotionEvent.ACTION_UP && onMapTouchListener != null) {
            onMapTouchListener.onMapTouch(geoPoint);
        }

        // It's important to call the superclass implementation of onTouchEvent
        // to ensure that map controls like zoom and pan work correctly.
        return super.onTouchEvent(event);
    }
}
