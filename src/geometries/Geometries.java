package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometries implements Intersectable {
    private List<Intersectable> geometries;

    public Geometries() {
        geometries = new ArrayList<>(); //why? or LinkedList
    }

    public Geometries(Intersectable... geometries) {
        this();
    }

    public void add(Intersectable... geometries) {
        for (var geometry:geometries) {
            this.geometries.add(geometry);
        }
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
