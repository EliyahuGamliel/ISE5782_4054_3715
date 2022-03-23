package renderer;

import java.util.List;

import primitives.*;
import scene.Scene;

public class RayTracerBasic extends RayTracerBase {
    public RayTracerBasic (Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections = scene.geometries.findIntersections(ray);
        if (intersections == null) {
            return scene.background;
        }
        Point colsesPoint = ray.findClosestPoint(intersections);
        return calcColor(colsesPoint);
    }
    /**
     * calculate the color in a specific Point
     * @param point 
     * @return the Color
     */
    private Color calcColor(Point point) {
        return scene.ambientLight.getIntensity();
    }
}
