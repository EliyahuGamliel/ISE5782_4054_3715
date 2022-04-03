package lighting;

import primitives.Color;

/**
 * a light source
 */
abstract class Light {
    private Color intensity;

    /**
     * create a light source with a color
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    public Color getIntensity() {
        return intensity;
    }
}
