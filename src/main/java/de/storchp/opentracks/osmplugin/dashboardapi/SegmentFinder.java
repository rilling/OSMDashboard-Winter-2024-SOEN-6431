package de.storchp.opentracks.osmplugin.dashboardapi;

import org.oscim.core.GeoPoint;

public class SegmentFinder {
    private static double euclideanDistance(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double distanceToSegment(GeoPoint start, GeoPoint end, GeoPoint point) {
        double dx = end.getLongitude() - start.getLongitude();
        double dy = end.getLatitude() - start.getLatitude();
        double segmentLengthSquared = dx * dx + dy * dy;

        if (segmentLengthSquared == 0) {
            // The segment is a point, calculate direct distance
            return euclideanDistance(start.getLongitude(), start.getLatitude(), point.getLongitude(), point.getLatitude());
        }

        // Project point onto the line segment
        double t = ((point.getLongitude() - start.getLongitude()) * dx + (point.getLatitude() - start.getLatitude()) * dy) / segmentLengthSquared;
        t = Math.max(0, Math.min(1, t));

        // Calculate the coordinates of the projection point
        double projectionLongitude = start.getLongitude() + t * dx;
        double projectionLatitude = start.getLatitude() + t * dy;

        // Return the Euclidean distance between the original point and its projection onto the segment
        return euclideanDistance(point.getLongitude(), point.getLatitude(), projectionLongitude, projectionLatitude);
    }
}
