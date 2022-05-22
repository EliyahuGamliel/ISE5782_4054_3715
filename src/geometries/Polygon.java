package geometries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 * 
 * @author Dan
 */
public class Polygon extends Geometry implements BoxAble{
	/**
	 * List of polygon's vertices
	 */
	protected List<Point> vertices;
	/**
	 * Associated plane in which the polygon lays
	 */
	protected Plane plane;
	private int size;
	/**
	 * the box that bounds this polygon
	 */
	protected Box _boundingBox =null;

	/**
	 * Polygon constructor based on vertices list. The list must be ordered by edge
	 * path. The polygon must be convex.
	 * 
	 * @param vertices list of vertices according to their order by edge path
	 * @throws IllegalArgumentException in any case of illegal combination of
	 *                                  vertices:
	 *                                  <ul>
	 *                                  <li>Less than 3 vertices</li>
	 *                                  <li>Consequent vertices are in the same
	 *                                  point
	 *                                  <li>The vertices are not in the same
	 *                                  plane</li>
	 *                                  <li>The order of vertices is not according
	 *                                  to edge path</li>
	 *                                  <li>Three consequent vertices lay in the
	 *                                  same line (180&#176; angle between two
	 *                                  consequent edges)
	 *                                  <li>The polygon is concave (not convex)</li>
	 *                                  </ul>
	 */
	public Polygon(Point... vertices) {
		if (vertices.length < 3)
			throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
		this.vertices = List.of(vertices);
		// Generate the plane according to the first three vertices and associate the
		// polygon with this plane.
		// The plane holds the invariant normal (orthogonal unit) vector to the polygon
		plane = new Plane(vertices[0], vertices[1], vertices[2]);
		if (vertices.length == 3)
			return; // no need for more tests for a Triangle

		Vector n = plane.getNormal();

		// Subtracting any subsequent points will throw an IllegalArgumentException
		// because of Zero Vector if they are in the same point
		Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
		Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

		// Cross Product of any subsequent edges will throw an IllegalArgumentException
		// because of Zero Vector if they connect three vertices that lay in the same
		// line.
		// Generate the direction of the polygon according to the angle between last and
		// first edge being less than 180 deg. It is hold by the sign of its dot product
		// with
		// the normal. If all the rest consequent edges will generate the same sign -
		// the
		// polygon is convex ("kamur" in Hebrew).
		boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
		for (var i = 1; i < vertices.length; ++i) {
			// Test that the point is in the same plane as calculated originally
			if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
				throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
			// Test the consequent edges have
			edge1 = edge2;
			edge2 = vertices[i].subtract(vertices[i - 1]);
			if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
				throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
		}
		size = vertices.length;
	}

	@Override
	public Vector getNormal(Point point) {
		return plane.getNormal();
	}

	@Override
	public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {

		int len = vertices.size();
		Point p0 = ray.getP0();
		Vector v = ray.getDir();
		List<Vector> vectors = new ArrayList<>(len);

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
		List<GeoPoint> geoPoints = plane.findGeoIntersectionsHelper(ray, maxDistance);
		List<GeoPoint> newGeoPoints = new ArrayList<>();
		if (geoPoints == null)
			return null;
		for (GeoPoint geo : geoPoints) {
			newGeoPoints.add(new GeoPoint(this, geo.point));
		}
		return newGeoPoints;
	}

	@Override
	public Box getBox() {

		if(_boundingBox==null){
			_boundingBox = BuildBox();
		}

		return _boundingBox;
	}


	private Box BuildBox(){
		// calc the bounding box. the BB minimum point is the point with the minimal coords from all the vertices.
		// in order to cover all the points, we should find the maximum x,y,z coord, and the box is Box(Point(x_min,y_min,z_min),Point(x_max,y_max,z_max))
		Comparator<Point> x = Comparator.comparing(Point::getX); // comparator that finds the min and max x from all the points
		Comparator<Point> y = Comparator.comparing(Point::getY); //comparator that finds the min and max y from all the points
		Comparator<Point> z = Comparator.comparing(Point::getZ); //comparator that finds the min and max z from all the points

		double maxX = Collections.max(vertices, x).getX();
		double minX = Collections.min(vertices,x).getX();

		double maxY = Collections.max(vertices, y).getY();
		double minY = Collections.min(vertices,y).getY();

		double maxZ = Collections.max(vertices, z).getZ();
		double minZ = Collections.min(vertices,z).getZ();

		return new Box(new Point(minX,minY,minZ),new Point(maxX,maxY,maxZ));
	}
}
