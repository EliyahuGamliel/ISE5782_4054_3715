package geometries;

import primitives.*;

/**
 *  a finite tube
 */
public class Cylinder extends Tube {

    /**
     * create a cylinder with a ray and a radius
     * @param exisRay the ray on which the cylinder is based
     * @param radius the radius of the cylinder
     */
    public Cylinder(Ray exisRay, double radius) {
        super(exisRay, radius);
    }


    @Override
    public Vector getNormal(Point point) {
        // TODO calculate normal at a specific point
        return null;
    }

    @Override
    public String toString() {
        return "Cylinder [" + super.toString() + "]";
    }
}
