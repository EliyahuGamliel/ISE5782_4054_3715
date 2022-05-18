package lighting;

import java.util.ArrayList;
import java.util.List;

import primitives.*;

/**
 * a class that represents a lightsource with radius
 */
public class CirclesLight extends PointLight {
    private final Vector direction;
    private final double radius;
    private int numOfShadowRays = 72;

    
    public CirclesLight(Color intensity, Point position, Vector direction, double radius) {
        super(intensity, position);
        this.direction = direction;
        this.radius = radius;
    }

    public CirclesLight(Color intensity, Point position,Vector direction, double radius, double kC, double kL, double kQ) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction;
        this.radius = radius;
    }

    @Override
    public List<VectorDistance> getLsDistances(Point p) {
        List<VectorDistance> res = new ArrayList<>(numOfShadowRays);
        Vector l = getL(p);
        Vector vRight = l.crossProduct(direction).normalize();
        for (double i = 0; i < 360; i+=(360/numOfShadowRays)) {
            Point pl = position.add(vRight.Roatate(i, direction).scale(radius));
            res.add(new VectorDistance(p.subtract(pl).normalize(), pl.distance(p)));
        }
        return res;
    }

    public CirclesLight setNumOfShadowRays(int numOfShadowRays) {
        this.numOfShadowRays = numOfShadowRays;
        return this;
    }
}