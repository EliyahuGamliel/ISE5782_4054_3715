package lighting;

import primitives.*;

public class SpotLight extends PointLight {
    private Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    public SpotLight(Color intensity, Point position, double kC, double kL, double kQ, Vector direction) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction.normalize();
    }

    public Color getIntensity(Point p) {
        double l = direction.dotProduct(getL(p));
        if(l<=0) // for time saving. do not compute intensity if l<=0.
            return Color.BLACK;
        return super.getIntensity(p).scale(l);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
