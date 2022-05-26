package geometries;

import java.util.ArrayList;
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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
		Point p0 = ray.getP0();
		Vector v = ray.getDir();

		Vector v0 = vertices.get(0).subtract(p0);
		Vector v1 = vertices.get(1).subtract(p0);
		Vector v2 = vertices.get(2).subtract(p0);

		// calculate the normal using the formula in the course slides
		Vector N = v0.crossProduct(v1).normalize();
		double dotProd = v.dotProduct(N);

		int sign = dotProd > 0 ? 1 : -1;

		if (!checkSign(sign,dotProd) || isZero(dotProd))
				return null;

		// calculate the normal using the formula in the course slides
		N = v1.crossProduct(v2).normalize();
		dotProd = v.dotProduct(N);

		if (!checkSign(sign,dotProd) || isZero(dotProd))
				return null;

		// calculate the normal using the formula in the course slides
		N = v2.crossProduct(v0).normalize();
		dotProd = v.dotProduct(N);

		if (!checkSign(sign,dotProd) || isZero(dotProd))
				return null;
				
		List<GeoPoint> geoPoints = plane.findGeoIntersectionsHelper(ray, maxDistance);
		if (geoPoints == null)
			return null;
		return geoPoints.stream().map(geo -> new GeoPoint(this, geo.point)).toList();
    }
}
