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
        Point P = ray.getP0();
        Vector V = ray.getDir(),
                Va = exisRay.getDir(),
                DeltaP = P.subtract(exisRay.getP0()),
                temp_for_use1, temp_for_use2;

        double V_dot_Va = V.dotProduct(Va),
                DeltaP_dot_Va = DeltaP.dotProduct(Va);

        temp_for_use1 = V.subtract(Va.scale(V_dot_Va));
        temp_for_use2 = DeltaP.subtract(Va.scale(DeltaP_dot_Va));

        double A = temp_for_use1.dotProduct(temp_for_use1);
        double B = 2*V.subtract(Va.scale(V_dot_Va)).dotProduct(DeltaP.subtract(Va.scale(DeltaP_dot_Va)));
        double C = temp_for_use2.dotProduct(temp_for_use2) - radius * radius;
        double desc = B*B - 4*A*C;

        if (desc < 0) {//No solution
            return null;
        }

        double t1 = (-B+Math.sqrt(desc))/(2*A),
                t2 = (-B-Math.sqrt(desc))/(2*A);

        if (desc == 0) {//One solution
            if (-B/(2*A) < 0)
                return null;
            List<Point> _points = new ArrayList<Point>(1);
            _points.add(P.add(V.scale(-B/(2*A))));
            return _points;
        }
        else if (t1 < 0 && t2 < 0){
            return null;
        }
        else if (t1 < 0 && t2 > 0) {
            List<Point> _points = new ArrayList<Point>(1);
            _points.add(P.add(V.scale(t2)));
            return _points;
        }
        else if (t1 > 0 && t2 < 0) {
            List<Point> _points = new ArrayList<Point>(1);
            _points.add(P.add(V.scale(t1)));
            return _points;
        }
        else {
            List<Point> _points = new ArrayList<Point>(2);
            _points.add(P.add(V.scale(t1)));
            _points.add(P.add(V.scale(t2)));
            return _points;
        }
    }
}