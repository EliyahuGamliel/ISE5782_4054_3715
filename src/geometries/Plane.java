package geometries;

import primitives.*;

public class Plane implements Geometry {
    
    protected Point q0;
    protected Vector normal;
    
    
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    public Plane(Point q0, Point q1, Point q3) {
        this.q0 = q0;
        // TODO: needs to calculate the normal from the 3 points
        this.normal = null;
    }


    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane [normal=" + normal + ", q0=" + q0 + "]";
    }
}
