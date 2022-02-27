package geometries;

import primitives.*;

/**
 * an infinite surface in a 3d space
 */
public class Plane implements Geometry {
    
    final private Point q0;
    final private Vector normal;

    /**
     * create a plane using point and a vector
     * @param q0 point of reference
     * @param normal vector of the normal of the plane
     */
    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal.normalize();
    }

    /**
     * create a plane using 3 points
     * @param q0 first point
     * @param q1 second point
     * @param q3 third point
     */
    public Plane(Point q0, Point q1, Point q3) {
        // save one point as the point of reference
        this.q0 = q0;
        // TODO: needs to calculate the normal from the 3 points
        this.normal = null;
    }

    /**
     *
     * @return Point
     */
    public Point getQ0() {
        return q0;
    }

    /**
     *
     * @return Vector
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
