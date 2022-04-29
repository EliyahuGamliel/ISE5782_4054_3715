package lighting;

import primitives.*;

public class SpotLight extends PointLight {
    private Vector direction;
    private double narrowBeam;

    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
        this.narrowBeam = 0.5;
    }

    public SpotLight(Color intensity, Point position, double kC, double kL, double kQ, Vector direction) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction.normalize();
        this.narrowBeam = 0.5;
    }

    public Color getIntensity(Point p) {
        double l;
        double m = direction.dotProduct(getL(p));

        if (narrowBeam == 0.5)
            l = m;
        else {
            l = Math.cos(Math.acos(m) * (0.5 / narrowBeam));
            if (Math.cos(narrowBeam * Math.PI) > m)
                l = 0;
        }

        if (l<=0)
            return Color.BLACK;
        return super.getIntensity(p).scale(l);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }
}
