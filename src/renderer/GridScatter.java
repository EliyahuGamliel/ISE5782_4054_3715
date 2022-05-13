package renderer;

import java.util.ArrayList;
import java.util.List;

import primitives.*;
import static primitives.Util.*;

public class GridScatter implements Scatterer {
    private int nX;
    private int nY;

    public GridScatter(int nX, int nY) {
        this.nX = nX;
        this.nY = nY;
    }

    @Override
    public List<Point> createPointsAround(Point p, double rX, double rY, Vector vRight, Vector vUp) {
        List<Point> points = new ArrayList<>(nX * nY);
        for (double j = -rY / 2; j < rY / 2; j += rY / nY) {
            for (double i = -rX / 2; i < rX / 2; i += rX / nX) {
                Point ijP = p;
                if (!isZero(j)) ijP = ijP.add(vRight.scale(j));
                if (!isZero(i)) ijP = ijP.add(vUp.scale(i));
                points.add(ijP);
            }
        }
        return points;
    }
}
