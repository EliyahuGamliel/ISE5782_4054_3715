package geometries;

import java.util.ArrayList;
import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Circle extends Geometry {
    Plane plane;
    Point center;
    double radius;

    public Circle(Point center, Vector normal, double radius) {
        this.center = center;
        this.radius = radius;
        this.plane = new Plane(center, normal);
    }

    @Override
    public Vector getNormal(Point point) {
        return plane.getNormal(point);
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> geoPoints = plane.findGeoIntersectionsHelper(ray, maxDistance);
        if (geoPoints == null)
            return null;
        GeoPoint geoPoint = geoPoints.get(0);
        if (geoPoint.point.distance(center) > radius)
            return null;
		List<GeoPoint> newGeoPoints = new ArrayList<>();
		for (GeoPoint geo : geoPoints) {
			newGeoPoints.add(new GeoPoint(this, geo.point));
		}
		return newGeoPoints;
    }
}