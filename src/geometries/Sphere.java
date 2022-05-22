package geometries;

import primitives.*;
import static primitives.Util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * sort of a ball
 */
public class Sphere extends Geometry implements BoxAble {
    
    final private Point center;
    final private double radius;

    /**
     * the box that bounds this sphere
     */
    private Box _boundingBox=null;

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
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
        if (t1 > 0 && alignZero(t1 - maxDistance) <= 0) {
            Point p = ray.getPoint(t1);
            points.add(new GeoPoint(this, p));
        }
        if (t2 > 0 && alignZero(t2 - maxDistance) <= 0) {
            Point p = ray.getPoint(t2);
            points.add(new GeoPoint(this, p));
        }
        return points;
    }

    @Override
    public Box getBox() {
        if(_boundingBox==null){
            _boundingBox=BuildBox();
        }
        return _boundingBox;
    }

    private Box BuildBox() {
        // init the bounding box. the first point of this box is the most low point of the sphere, and the second is the most high point of the sphere.
        double x = center.getX(),y= center.getY(),z=center.getZ();
        Point min = new Point(x-radius,y-radius,z-radius); // calculates the most low point of the sphere
        Point max = new Point(x+radius,y+radius,z+radius); // calculates the most high point of the sphere
        return new Box(min,max);
    }
}
