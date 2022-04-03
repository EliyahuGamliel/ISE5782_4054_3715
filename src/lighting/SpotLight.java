package lighting;

import primitives.*;

public class SpotLight extends PointLight {
    private Vector direction;

    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction;
    }

    public SpotLight(Color intensity, Point position, double kC, double kL, double kQ, Vector direction) {
        super(intensity, position, kC, kL, kQ);
        this.direction = direction;
    }
}
