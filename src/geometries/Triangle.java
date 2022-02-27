package geometries;

import primitives.Point;

/**
 * 2d triangle in a 3d space
 */
public class Triangle extends Polygon {

    /**
     * create a triangle with 3 points
     * @param q0 first point
     * @param q1 second point
     * @param q2 third point
     */
    public Triangle(Point q0, Point q1, Point q2) {
        super(q0, q1, q2);
    }

    @Override
    public String toString() {
        return "Triangle [" + super.toString() + "]";
    }
}
