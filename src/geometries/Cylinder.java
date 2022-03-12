package geometries;

import primitives.*;

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
        Vector OtherBase = exisRay.getDir().scale(height);
        Point p0OtherBase = exisRay.getP0().add(OtherBase);
        double crossProductP01 = exisRay.getDir().dotProduct(exisRay.getP0().subtract(point));
        double crossProductP02 = exisRay.getDir().dotProduct(p0OtherBase.subtract(point));
        double r = radius*radius;
        if (point.distanceSquared(exisRay.getP0()) <= r && crossProductP01 == 0 || point.distanceSquared(p0OtherBase) <= r && crossProductP02 == 0)
            return exisRay.getDir();
        else
            return super.getNormal(point);
    }

    @Override
    public String toString() {
        return "Cylinder [" + super.toString() + "]";
    }
}
