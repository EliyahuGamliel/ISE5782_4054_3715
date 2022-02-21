package geometries;

import primitives.*;

public class Cylinder extends Tube {

    public Cylinder(Ray exisRay, double radius) {
        super(exisRay, radius);
    }

    @Override
    public String toString() {
        return "Cylinder [" + super.toString() + "]";
    }
}
