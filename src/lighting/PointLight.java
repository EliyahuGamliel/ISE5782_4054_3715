package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class PointLight extends Light implements LightSource {
    private final Point position;
    private final double kC, kL, kQ;
    
    
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
        this.kC = 1;
        this.kL = 0;
        this.kQ = 0;
    }


    @Override
    public Color getIntensity(Point p) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public Vector getL(Point p) {
        // TODO Auto-generated method stub
        return null;
    }


}
