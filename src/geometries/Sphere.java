package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

/**
 * sort of a ball
 */
public class Sphere implements Geometry {
    
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
    public List<Point> findIntersections(Ray ray) {
        List<Point> points = new ArrayList<>();
        Vector u = center.subtract(ray.getP0());
        double Tm = ray.getDir().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared()-Tm*Tm);
        if (d >= radius)
            return null;
        double Th = Math.sqrt(radius*radius-d*d);
        double t1 = Tm + Th;
        double t2 = Tm - Th;
        if (t1 > 0) {
            Point p = ray.getP0().add(ray.getDir().scale(t1));
            points.add(p);
        }
        if (t2 > 0) {
            Point p = ray.getP0().add(ray.getDir().scale(t2));
            points.add(p);
        }
        return points;
    }
}
