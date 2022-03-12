package geometries;

import primitives.*;

/**
 * an infinite pipe
 */
public class Tube implements Geometry {
    final protected Ray exisRay;
    final protected double radius;

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
        double t = exisRay.getDir().dotProduct(point.subtract(exisRay.getP0()));
        Point center;
        if (t != 0)
            center = exisRay.getP0().add(exisRay.getDir().scale(t));
        else
            center = exisRay.getP0();
        return point.subtract(center).normalize();
    }

    @Override
    public String toString() {
        return "Tube [exisRay=" + exisRay + ", radius=" + radius + "]";
    }
}
