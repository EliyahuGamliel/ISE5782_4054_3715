package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource {
    private final Vector direction;
    
    /**
     * create a directional light source with a color
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction;
    }

    @Override
    public Color getIntensity(Point p) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
