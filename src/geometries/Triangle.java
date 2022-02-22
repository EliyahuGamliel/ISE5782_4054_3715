package geometries;

import primitives.Point;

/**
 *
 */
public class Triangle extends Polygon {

    /**
     *
     * @param q0
     * @param q1
     * @param q2
     */
    public Triangle(Point q0, Point q1, Point q2) {
        super(q0, q1, q2);
    }

    @Override
    public String toString() {
        return "Triangle [" + super.toString() + "]";
    }
}
