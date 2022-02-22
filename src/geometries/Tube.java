package geometries;

import primitives.*;

/**
 *
 */
public class Tube implements Geometry {
    
    protected Ray exisRay;
    protected double radius;

    /**
     *
     * @param exisRay
     * @param radius
     */
    public Tube(Ray exisRay, double radius) {
        this.exisRay = exisRay;
        this.radius = radius;
    }

    /**
     *
     * @return
     */
    public Ray getExisRay() {
        return exisRay;
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
        return "Tube [exisRay=" + exisRay + ", radius=" + radius + "]";
    }
}
