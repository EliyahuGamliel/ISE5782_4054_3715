package geometries;

import primitives.*;

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
}
