package de.storchp.opentracks.osmplugin.dashboardapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ChairLiftElements {
    public String type;
    public long id;
    public Bounds bounds;
    public ArrayList<Object> nodes;
    public ArrayList<Geometry> geometry;
    public Tags tags;

    public ChairLiftElements(String type, long id, Bounds bounds, ArrayList<Object> nodes,
                       ArrayList<Geometry> geometry, Tags tags) {
        this.type = type;
        this.id = id;
        this.bounds = bounds;
        this.nodes = nodes;
        this.geometry = geometry;
        this.tags = tags;
    }

    public ChairLiftElements() {

    }

    public static ChairLiftElements parseJsonElement(JSONObject jsonObject) throws JSONException {
        ChairLiftElements chairLiftElements = new ChairLiftElements();
        chairLiftElements.type = jsonObject.optString("type", "");
        chairLiftElements.id = jsonObject.optInt("id", 0);

        // Parsing bounds
        JSONObject boundsObj = jsonObject.optJSONObject("bounds");
        if (boundsObj != null) {
            chairLiftElements.bounds = new Bounds();
            chairLiftElements.bounds.minlat = boundsObj.optDouble("minlat", 0.0);
            chairLiftElements.bounds.minlon = boundsObj.optDouble("minlon", 0.0);
            chairLiftElements.bounds.maxlat = boundsObj.optDouble("maxlat", 0.0);
            chairLiftElements.bounds.maxlon = boundsObj.optDouble("maxlon", 0.0);
        }

        // Parsing nodes
        JSONArray nodesArray = jsonObject.optJSONArray("nodes");
        if (nodesArray != null) {
            chairLiftElements.nodes = new ArrayList<>();
            for (int i = 0; i < nodesArray.length(); i++) {
                chairLiftElements.nodes.add(nodesArray.optLong(i, 0L));
            }
        }

        // Parsing geometry
        JSONArray geometryArray = jsonObject.optJSONArray("geometry");
        if (geometryArray != null) {
            for (int i = 0; i < geometryArray.length(); i++) {
                JSONObject geometryObj = geometryArray.optJSONObject(i);
                chairLiftElements.geometry = new ArrayList<>();
                if (geometryObj != null) {
                    Geometry geometry = new Geometry();
                    geometry.lat = geometryObj.optDouble("lat", 0.0);
                    geometry.lon = geometryObj.optDouble("lon", 0.0);
                    chairLiftElements.geometry.add(geometry);
                }
            }
        }

        // Parsing tags
        JSONObject tagsObj = jsonObject.optJSONObject("tags");
        if (tagsObj != null) {
            Tags tags = new Tags();
            tags.name = tagsObj.optString("name", "");
            tags.aerialway = tagsObj.optString("aerialway", "");
            tags.aerialway_occupancy = tagsObj.optString("aerialway:occupancy", "");
            tags.aerialway_bubble = tagsObj.optString("aerialway:bubble", "");
            tags.aerialway_heating = tagsObj.optString("aerialway:heating", "");
            tags.aerialway_duration = tagsObj.optString("aerialway:duration", "");
            chairLiftElements.tags = tags;
        }

        return chairLiftElements;
    }

}

