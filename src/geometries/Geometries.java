package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries extends Intersectable {
    private List<Intersectable> geometries;

    public Geometries() {
        geometries = new ArrayList<>();
    }

    public Geometries(Intersectable... geometries) {
        this.geometries = new ArrayList<>(Arrays.asList(geometries));
    }

    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> res = null;
        for (Intersectable geometry : this.geometries) {
            List<GeoPoint> resi = geometry.findGeoIntersections(ray, maxDistance);
            if (resi != null) {
                if (res == null) {
                    res = new LinkedList<GeoPoint>();
                }
                res.addAll(resi);
            }
        }
        return res;
    }
}
