package geometries;

import primitives.*;

import java.util.List;

import static primitives.Util.*;

/**
 *  a finite tube
 */
public class Cylinder extends Tube {
    final private double height;

    /**
     * create a cylinder with a ray and a radius
     * @param exisRay the ray on which the cylinder is based
     * @param radius the radius of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(Ray exisRay, double radius, double height) {
        super(exisRay, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        double t = exisRay.getDir().dotProduct(point.subtract(exisRay.getP0()));
        if (isZero(t) || isZero(t - height))
            return exisRay.getDir();
        else
            return super.getNormal(point);
    }

    @Override
    public String toString() {
        return "Cylinder [" + super.toString() + "]";
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
