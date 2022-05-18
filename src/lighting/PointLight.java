package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    protected final Point position;
    protected double kC = 1, kL = 0, kQ = 0;
    
    
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    public PointLight(Color intensity, Point position, double kC, double kL, double kQ) {
        super(intensity);
        this.position = position;
        this.kC = kC;
        this.kL = kL;
        this.kQ = kQ;
    }

    @Override
    public double getDistance(Point point) {
        return position.distance(point);
    }

    @Override
    public Color getIntensity(Point p) {
        double d = p.distance(position);
        return getIntensity().scale(1/(kC + kL * d + kQ * d * d));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }

    public PointLight setkC(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setkL(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setkQ(double kQ) {
        this.kQ = kQ;
        return this;
    }
}
