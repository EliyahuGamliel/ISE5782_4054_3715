package lighting;

import primitives.*;

/**
 * a non ambient light source
 */
public interface LightSource {
    public Color getIntensity(Point p);
    public Vector getL(Point p);
}