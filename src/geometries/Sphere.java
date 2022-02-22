package geometries;

import primitives.*;

/**
 *
 */
public class Sphere implements Geometry {
    
    protected Point center;
    protected double radius;

    /**
     *
     * @param center
     * @param radius
     */
    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     *
     * @return
     */
    public Point getCenter() {
        return center;
    }

    /**
     *
     * @return
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point point) {
        // TODO calculate normal at a specific point
        return null;
    }

    @Override
    public String toString() {
        return "Sphere [center=" + center + ", radius=" + radius + "]";
    }
}
