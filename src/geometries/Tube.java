package geometries;

import primitives.*;

import java.util.ArrayList;
import static primitives.Util.*;
import java.util.List;

/**
 * an infinite pipe
 */
public class Tube implements Geometry {
    final protected Ray exisRay;
    final protected double radius;

    /**
     * create a tube with a ray and a radius
     *
     * @param exisRay the ray on which the tube is based
     * @param radius  the radius of the tube
     */
    public Tube(Ray exisRay, double radius) {
        this.exisRay = exisRay;
        this.radius = radius;
    }

    /**
     * @return Ray
     */
    public Ray getExisRay() {
        return exisRay;
    }

    /**
     * @return double
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(Point point) {
        double t = exisRay.getDir().dotProduct(point.subtract(exisRay.getP0()));
        Point center;
        if (t != 0)
            center = exisRay.getP0().add(exisRay.getDir().scale(t));
        else
            center = exisRay.getP0();
        return point.subtract(center).normalize();
    }

    @Override
    public String toString() {
        return "Tube [exisRay=" + exisRay + ", radius=" + radius + "]";
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        Vector dir = ray.getDir();
        Vector v = exisRay.getDir();
        double dirV = dir.dotProduct(v);

        if (ray.getP0().equals(exisRay.getP0())) { // In case the ray starts on the p0.
            if (isZero(dirV))
                return List.of(ray.getPoint(radius));

            if (dir.equals(v.scale(dir.dotProduct(v))))
                return null;

            return List.of(ray.getPoint(
                    Math.sqrt(radius * radius / dir.subtract(v.scale(dir.dotProduct(v)))
                            .lengthSquared())));
        }

        Vector deltaP = ray.getP0().subtract(exisRay.getP0());
        double dpV = deltaP.dotProduct(v);

        double a = 1 - dirV * dirV;
        double b = 2 * (dir.dotProduct(deltaP) - dirV * dpV);
        double c = deltaP.lengthSquared() - dpV * dpV - radius * radius;

        if (isZero(a)) {
            if (isZero(b)) { // If a constant equation.
                return null;
            }
            return List.of(ray.getPoint(-c / b)); // if it's linear, there's a solution.
        }

        double discriminant = alignZero(b * b - 4 * a * c);

        if (discriminant < 0) // No real solutions.
            return null;

        double t1 = alignZero(-(b + Math.sqrt(discriminant)) / (2 * a)); // Positive solution.
        double t2 = alignZero(-(b - Math.sqrt(discriminant)) / (2 * a)); // Negative solution.

        if (discriminant <= 0) // No real solutions.
            return null;

        if (t1 > 0 && t2 > 0) {
            List<Point> _points = new ArrayList<>(2);
            _points.add(ray.getPoint(t1));
            _points.add(ray.getPoint(t2));
            return _points;
        }
        else if (t1 > 0) {
            List<Point> _points = new ArrayList<>(1);
            _points.add(ray.getPoint(t1));
            return  _points;
        }
        else if (t2 > 0) {
            List<Point> _points = new ArrayList<>(1);
            _points.add(ray.getPoint(t2));
            return _points;
        }
        return null;
    }
}