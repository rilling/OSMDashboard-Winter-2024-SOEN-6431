package de.storchp.opentracks.osmplugin.maps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import org.oscim.android.MapView;
import org.oscim.core.GeoPoint;
import org.oscim.map.Viewport;

import de.storchp.opentracks.osmplugin.MapsActivity;
import de.storchp.opentracks.osmplugin.dashboardapi.APIConstants;

public class TrailSelectionMapView extends MapView {

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
        Log.d("CustomMapView", "Map coordinates: " + geoPoint.getLatitude() + ", " + geoPoint.getLongitude());
//        String intentAction = ((Activity)getContext()).getIntent().getAction();
//        if (intentAction != null && intentAction.equals(APIConstants.ACTION_DASHBOARD)) {
//            MapsActivity activity = new MapsActivity();
//            activity.displaySelectedTrailTable();
//        }
        // Notify listener when there's a touch event
        if (event.getAction() == MotionEvent.ACTION_UP && onMapTouchListener != null) {
            onMapTouchListener.onMapTouch(geoPoint);
        }

        // It's important to call the superclass implementation of onTouchEvent
        // to ensure that map controls like zoom and pan work correctly.
        return super.onTouchEvent(event);
    }
}
