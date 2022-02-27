package geometries;

import primitives.*;

/**
 * an infinite pipe
 */
public class Tube implements Geometry {
    
    protected Ray exisRay;
    protected double radius;

    /**
     * create a tube with a ray and a radius
     * @param exisRay the ray on which the tube is based
     * @param radius the radius of the tube
     */
    public Tube(Ray exisRay, double radius) {
        this.exisRay = exisRay;
        this.radius = radius;
    }

    /**
     *
     * @return Ray
     */
    public Ray getExisRay() {
        return exisRay;
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
        // TODO calculate normal at a specific point
        return null;
    }

    @Override
    public String toString() {
        return "Tube [exisRay=" + exisRay + ", radius=" + radius + "]";
    }
}
