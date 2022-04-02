package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight extends Light {

    public AmbientLight() {
        super(Color.BLACK);
    }

    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }
}
