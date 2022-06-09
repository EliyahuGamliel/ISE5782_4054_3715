package lighting;

import java.util.ArrayList;
import java.util.List;

import primitives.*;

import static primitives.Util.*;
/**
 * a class that represents a lightsource with radius
 */
public class CirclesLight extends PointLight {
    private final Vector direction;
    private final double radius;
    private int numOfShadowRays = 72;
    private double n;
    
    public CirclesLight(Color intensity, Point position, Vector direction, double radius) {
        super(intensity, position);
        this.direction = direction;
        this.radius = radius;
        n = Math.sqrt(numOfShadowRays);
    }

    public CirclesLight(Color intensity, Point position,Vector direction, double radius, double kC, double kL, double kQ) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction;
        this.radius = radius;
        n = Math.sqrt(numOfShadowRays);
    }

    @Override
    public List<VectorDistance> getLsDistances(Point p) {
        List<VectorDistance> res = new ArrayList<>(numOfShadowRays);
        Vector l = getL(p);
        Vector vRight = l.crossProduct(direction).normalize();
        Vector vUp = vRight.crossProduct(direction);
        for (double j = -radius / 2; isZero(j - (radius/2)) || j <= radius / 2; j += radius / (n - 1)) {
            for (double i = -radius / 2; isZero(i - (radius/2)) || i <= radius / 2; i += radius / (n - 1)) {
                Point ijP = p;
                if (!isZero(j)) ijP = ijP.add(vRight.scale(j));
                if (!isZero(i)) ijP = ijP.add(vUp.scale(i));
                res.add(new VectorDistance(ijP.subtract(position).normalize(), ijP.distance(position)));
            }
        }
        return res;
    }

    public CirclesLight setNumOfShadowRays(int numOfShadowRays) {
        this.numOfShadowRays = numOfShadowRays;
        n = Math.sqrt(numOfShadowRays);
        return this;
    }
}