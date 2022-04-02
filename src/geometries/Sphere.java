package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * sort of a ball
 */
public class Sphere extends Geometry {
    
    final private Point center;
    final private double radius;

    /**
     * create a sphere with point and a radius
     * @param center the center point of the sphere
     * @param radius the radius of the sphere
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     *
     * @return Point
     */
    public Point getCenter() {
        return center;
    }

    /**
     *
     * @return double
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    @Override
    public String toString() {
        return "Sphere [center=" + center + ", radius=" + radius + "]";
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        if (ray.getP0().equals(center)) {
            List<GeoPoint> points = new ArrayList<>(1);
            Point p = center.add(ray.getDir().scale(radius));
            points.add(new GeoPoint(this, p));
            return points;
        }

        Vector u = center.subtract(ray.getP0());
        double Tm = ray.getDir().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared()-Tm*Tm);
        if (d >= radius)
            return null;
        double Th = Math.sqrt(radius*radius-d*d);
        double t1 = alignZero(Tm + Th);
        double t2 = alignZero(Tm - Th);
        if (t1 <= 0 && t2 <= 0)
            return null;
        int size = 0;
        if(t1 > 0)
            size += 1;
        if(t2 > 0)
            size += 1;
        List<GeoPoint> points = new ArrayList<>(size);
        if (t1 > 0) {
            Point p = ray.getPoint(t1);
            points.add(new GeoPoint(this, p));
        }
        if (t2 > 0) {
            Point p = ray.getPoint(t2);
            points.add(new GeoPoint(this, p));
        }
        return points;
    }
}
