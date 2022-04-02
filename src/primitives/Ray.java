package primitives;

import java.util.List;
import java.util.Objects;
import geometries.Intersectable.GeoPoint;

/**
 * 3d ray with direction
 */
public class Ray {
    final private Point p0;
    final private Vector dir;

    /**
     * create a ray using a point and a vector
     * @param p0 the reference point
     * @param dir the direction of the ray
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     *
     * @return Point
     */
    public Point getP0() {
        return new Point(p0.xyz.d1,p0.xyz.d2,p0.xyz.d3);
    }

    /**
     *
     * @return Vector
     */
    public Vector getDir() {
        return new Vector(dir.xyz.d1,dir.xyz.d2,dir.xyz.d3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    @Override
    public String toString() {
        return "Ray: " +
                String.format("point: (%.2f,%.2f,%.2f)",p0.xyz.d1,p0.xyz.d2,p0.xyz.d3) +
                String.format("dir: (%.2f,%.2f,%.2f)",dir.xyz.d1,dir.xyz.d2,dir.xyz.d3);
    }

    public Point getPoint(double t) {
        return p0.add(dir.scale(t));
    }

    public Point findClosestPoint(List<Point> points) {
        if (points.size() == 0)
            return null;
        double close = p0.distanceSquared(points.get(0));
        int index = 0;
        for (int i = 1; i < points.size(); i++) {
            if (p0.distanceSquared(points.get(i)) < close) {
                close = p0.distanceSquared(points.get(i));
                index = i;
            }
        }
        return points.get(index);
    }

    public GeoPoint findClosestGeoPoint(List<GeoPoint> points) {
        if (points.size() == 0)
            return null;
        double close = p0.distanceSquared(points.get(0).point);
        int index = 0;
        for (int i = 1; i < points.size(); i++) {
            if (p0.distanceSquared(points.get(i).point) < close) {
                close = p0.distanceSquared(points.get(i).point);
                index = i;
            }
        }
        return points.get(index);
    }
}
