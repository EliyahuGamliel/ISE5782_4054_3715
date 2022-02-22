package geometries;

import primitives.*;

public class Cylinder extends Tube {

    /**
     *
     * @param exisRay
     * @param radius
     */
    public Cylinder(Ray exisRay, double radius) {
        super(exisRay, radius);
    }

    @Override
    public String toString() {
        return "Cylinder [" + super.toString() + "]";
    }
}
