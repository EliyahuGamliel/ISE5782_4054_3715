package geometries;

import java.util.List;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.*;

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

    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> _points = plane.findIntersections(ray);
		if (_points == null) {
			return null;
		}
		Point p = _points.get(0);
		Point viMinus1 = vertices.get(vertices.size()-1);
		for (Point vi : vertices) {
			Vector edge = vi.subtract(viMinus1);
			Vector pviMinus1 = p.subtract(viMinus1);
			Vector C;
			try {
				C = edge.crossProduct(pviMinus1);
			} catch (Exception e) {
				return null;
			}
			if (alignZero(plane.getNormal().dotProduct(C)) <= 0) {
				return null;
			}
			viMinus1 = vi;
		}
		return _points;
    }
}
