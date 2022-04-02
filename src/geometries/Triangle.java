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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        int len = vertices.size();
		Point p0 = ray.getP0();
		Vector v = ray.getDir();
		List<Vector> vectors = new ArrayList<Vector>(len);

		//all the vectors
		for (Point vertex : vertices) {
			vectors.add(vertex.subtract(p0));
		}

		int sign = 0;
		for (int i = 0; i < len; i++) {
			// calculate the normal using the formula in the course slides
			Vector N = vectors.get(i).crossProduct(vectors.get((i+1)%len)).normalize();
			double dotProd = v.dotProduct(N);

			if (i == 0)
				sign = dotProd > 0 ? 1 : -1;

			if (!checkSign(sign,dotProd) || isZero(dotProd))
				return null;
		}
		List<GeoPoint> geoPoints = plane.findGeoIntersectionsHelper(ray);
		List<GeoPoint> newGeoPoints = new ArrayList<>();
		for (GeoPoint geo : geoPoints) {
			newGeoPoints.add(new GeoPoint(this, geo.point));
		}
		return newGeoPoints;
    }

	public List<Point> findIntersections(Ray ray) {
		int len = vertices.size();
		Point p0 = ray.getP0();
		Vector v = ray.getDir();
		List<Vector> vectors = new ArrayList<Vector>(len);

		//all the vectors
		for (Point vertex : vertices) {
			vectors.add(vertex.subtract(p0));
		}

		int sign = 0;
		for (int i = 0; i < len; i++) {
			// calculate the normal using the formula in the course slides
			Vector N = vectors.get(i).crossProduct(vectors.get((i+1)%len)).normalize();
			double dotProd = v.dotProduct(N);

			if (i == 0)
				sign = dotProd > 0 ? 1 : -1;

			if (!checkSign(sign,dotProd) || isZero(dotProd))
				return null;
		}
		return plane.findIntersections(ray);
	}
}
