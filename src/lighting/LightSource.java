package lighting;

import java.util.Arrays;
import java.util.List;

import primitives.*;

/**
 * a non ambient light source
 */
public interface LightSource {
    double getDistance(Point point);
    Color getIntensity(Point p);
    Vector getL(Point p);

    /**
     * this method returns information neede to calculate illumination
     * (for soft shadows)
     * @param p
     * @return the list of vectors and distances to the light source
     */
    default List<VectorDistance> getLsDistances(Point p) {
        return Arrays.asList(new VectorDistance(getL(p), getDistance(p)));
    }
}