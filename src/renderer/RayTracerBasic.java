package renderer;

import java.util.List;
import geometries.Intersectable.GeoPoint;
import primitives.*;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {
    public RayTracerBasic (Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        GeoPoint colsesPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(colsesPoint);
    }
    /**
     * calculate the color in a specific Point
     * @param point 
     * @return the Color
     */
    private Color calcColor(GeoPoint point) {
        return scene.ambientLight.getIntensity().add(point.geometry.getEmission());
    }
}
