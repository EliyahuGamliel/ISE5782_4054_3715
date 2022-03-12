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
     * @param q2 third point
     */
    public Plane(Point q0, Point q1, Point q2) {
        if (q0.equals(q1) || q1.equals(q2) || q2.equals(q0))
            throw new IllegalArgumentException("Two or more equal points");
        // save one point as the point of reference
        this.q0 = q0;
        Vector v1 = q1.subtract(q0);
        Vector v2 = q2.subtract(q0);
        if (v1.dotProduct(v2)/(v1.length()*v2.length()) == 1 || v1.dotProduct(v2)/(v1.length()*v2.length()) == -1)
            throw new IllegalArgumentException("The points on are the same line");
        normal = v1.crossProduct(v2).normalize();
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
