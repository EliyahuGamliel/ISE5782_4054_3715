package lighting;

import primitives.Color;
import primitives.Double3;

/**
* an ambient light source
*/
public class AmbientLight extends Light {

    /**
     * create an ambient light source with a default color (black)
     */
    public AmbientLight() {
        super(Color.BLACK);
    }
    
    /**
     * create an ambient light source with a color and intensity
     */
    public AmbientLight(Color Ia, Double3 Ka) {
        super(Ia.scale(Ka));
    }
}
