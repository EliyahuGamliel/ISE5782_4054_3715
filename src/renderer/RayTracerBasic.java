package renderer;

import java.util.List;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import static primitives.Util.*;

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
        return calcColor(colsesPoint, ray);
    }
    /**
     * calculate the color in a specific Point
     * @param intersection 
     * @return the Color
     */
    private Color calcColor(GeoPoint intersection, Ray ray) {
        return scene.ambientLight.getIntensity()
            .add(intersection.geometry.getEmission())
            .add(calcLocalEffects(intersection, ray));
    }

    private Color calcLocalEffects(GeoPoint intersection, Ray ray) {
        Vector v = ray.getDir ();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }
        int nShininess = intersection.geometry.getMaterial().getnShininess();
        double kd = intersection.geometry.getMaterial().getkD(), ks = intersection.geometry.getMaterial().getkS();
        Color color = Color.BLACK;
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(intersection.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                                    calcSpecular(ks, l, n, nShininess, lightIntensity));
            } 
        }
        return color; }

    private Color calcSpecular(double ks, Vector l, Vector n, int nShininess, Color lightIntensity) {
        return null;
    }

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {
        return null;
    }
}
