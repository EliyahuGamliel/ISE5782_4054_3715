package geometries;

import primitives.*;
import java.util.List;

public interface Intersectable {
    public List<Point> findIntersections(Ray ray);
}
