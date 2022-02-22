package geometries;

import primitives.*;

/**
 *
 */
public class Plane implements Geometry {
    
    protected Point q0;
    protected Vector normal;

    /**
     *
     * @param q0
     * @param normal
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     *
     * @param q0
     * @param q1
     * @param q3
     */
    public Plane(Point q0, Point q1, Point q3) {
        this.q0 = q0;
        // TODO: needs to calculate the normal from the 3 points
        this.normal = null;
    }

    /**
     *
     * @return
     */
    public Point getQ0() {
        return q0;
    }

    /**
     *
     * @return
     */
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
