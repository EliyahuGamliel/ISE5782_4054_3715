package renderer;

import java.util.List;

import primitives.Point;
import primitives.Vector;

public interface Scatterer {
    /**
     * scatter points in a given space
     * @param p the point
     * @param rX length of pixel in x dimension
     * @param rY length of pixel in y dimension
     * @param vRught the vector to the right
     * @param vUp the vector to the up
     * @return list of scattered points
     */
    List<Point> createPointsAround(Point p, double rX, double rY, Vector vRight, Vector vUp);
}
