package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    private Color intensity;

    public AmbientLight() {
        this.intensity = Color.BLACK;
    }

    public AmbientLight(Color Ia, Double3 Ka) {
        this.intensity = Ia.scale(Ka);
    }

    public Color getIntensity() {
        return intensity;
    }
}
