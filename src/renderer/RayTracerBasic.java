package renderer;

import java.util.List;
import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import lighting.VectorDistance;
import primitives.*;
import scene.Scene;
import static primitives.Util.*;

public class RayTracerBasic extends RayTracerBase {
    private static final int MAX_CALC_COLOR_LEVEL = 3;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final Double3 INITIAL_K = new Double3(1.0);

    public RayTracerBasic (Scene scene) {
        super(scene);
    }

    /**
     * calculate the local effect on the color
     * this function calculate the illumination factor and diffuse and specular for every light source
     * @param intersection the GeoPoint
     * @param ray the entrence ray
     * @param k a product of all impacted materials
     * @return the color of the local effect
     */
    private Color calcLocalEffects(GeoPoint intersection, Ray ray, Double3 k) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return Color.BLACK;

        Material material = intersection.geometry.getMaterial();
        int nShininess = material.getnShininess();
        Double3 kd = material.getkD(), ks = material.getkS();
        Color color = intersection.geometry.getEmission();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(intersection.point);
            Double3 ktr = calcIllumination(intersection, n, nv, lightSource);
            if (!(ktr.product(k).lowerThan(MIN_CALC_COLOR_K))) {
                Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
            }
        }
        return color;
    }

    /**
     * calculate the illumination factor relative to a point
     * @param intersection the point
     * @param n the normal
     * @param nv the normal * entrence vector
     * @param lightSource the lightsource
     * @return the illumination factor
     */
    private Double3 calcIllumination(GeoPoint intersection, Vector n, double nv, LightSource lightSource) {
        //calculate and average ktr for each light source
        Double3 ktr = Double3.ZERO;
        List<VectorDistance> lsDis = lightSource.getLsDistances(intersection.point);
        for (VectorDistance ld : lsDis) {
            double nl = alignZero(n.dotProduct(ld.vector));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                ktr = ktr.add(transparency(intersection, ld.distance, ld.vector, n));
            }
        }
        ktr = ktr.reduce(lsDis.size());
        return ktr;
    }

    /**
     * calculate the specular effect of a lightsource
     * @param kS the Ks factor
     * @param l the vector to light
     * @param n the normal
     * @param v the etrence vector
     * @param nShininess the Shininess factor
     * @param intensity the light color
     * @return the specular effect
     */
    private Color calcSpecular(Double3 kS, Vector l, Vector n, Vector v, int nShininess, Color intensity) {
        Vector r = l.subtract(n.scale(2 * (l.dotProduct(n))));
        return intensity.scale(kS.scale(Math.pow(Math.max(v.scale(-1).dotProduct(r), 0), nShininess)));
    }

    /**
     * calculate the diffuse effect of a lightsource
     * @param kd the Kd factor
     * @param l the vector to light
     * @param n the normal
     * @param lightIntensity the light color
     * @return the diffuse effect
     */
    private Color calcDiffusive(Double3 kd, Vector l, Vector n, Color lightIntensity) {
        return lightIntensity.scale(kd.scale(Math.abs(l.dotProduct(n))));
    }

    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }

    /**
     * this is helper function that calls the recursive {@link #calcColor(GeoPoint, Ray, int, Double3)} function
     * and also adds the ambient light
     * @param gp the geoPoint
     * @param ray the ray
     * @return
     */
    private Color calcColor(GeoPoint gp, Ray ray) {
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(scene.ambientLight.getIntensity());
    }

    /**
     * calc the global effect on the color using reflected and refracted rays
     * depending on the geometry material
     * this function calls {@link #calcGlobalEffect(Ray, int, Double3, Double3)}
     * @param gp the GeoPoint
     * @param v the entrence vector
     * @param level the level of recursion
     * @param k a product of all impacted materials
     * @return the global effect color
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, Double3 k) {
        Color color = Color.BLACK; Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        Double3 kkr = k.product(material.kR);
        if (!(kkr.lowerThan(MIN_CALC_COLOR_K)))
            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.kR, kkr);
        Double3 kkt = k.product(material.kT);
        if (!(kkt.lowerThan(MIN_CALC_COLOR_K)))
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.kT, kkt));
        return color;
    }

    /**
     * an helper function for {@link #calcGlobalEffects(GeoPoint, Vector, int, Double3)}
     * this function simply calls {@link #calcColor(GeoPoint, Ray, int, Double3)}
     * @param ray
     * @param level
     * @param kx
     * @param kkx
     * @return
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level-1, kkx)).scale(kx);
    }

    /**
     * the recursive calcColor which calls {@link #calcLocalEffects(GeoPoint, Ray, Double3)}
     * and also calls {@link #calcGlobalEffects(GeoPoint, Vector, int, Double3)} 
     * which could then call this function
     * @param intersection the GeoPoint
     * @param ray the ray
     * @param level the recursion level
     * @param k a product of all impacted materials
     * @return
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Color color = calcLocalEffects(intersection, ray, k);
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray.getDir(), level, k));
    }
    /**
     * finds the closest intersection of a ray in the scene
     * @param ray the ray
     * @return the closest GeoPoint intersection
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(ray);
        if (intersections == null) return null;
        return ray.findClosestGeoPoint(intersections);
    }

    /**
     * cunstruct a reflected ray using the normal and entrence vector
     * @param p the starting point of the ray
     * @param v the entrence vector
     * @param n the normal
     * @return the reflected ray
     */
    private Ray constructReflectedRay(Point p, Vector v, Vector n) {
        Vector r = v.subtract(n.scale(2 * v.dotProduct(n)));
        return new Ray(p, r, n);
    }

    /**
     * cunstruct a refracted ray using the normal and entrence vector
     * @param p the starting point of the ray
     * @param v the entrence vector
     * @param n the normal
     * @return the refracted ray
     */
    private Ray constructRefractedRay(Point p, Vector v, Vector n) {
        return new Ray(p, v, n);
    }
    /**
     * calculate the transparency factor of a ray going throu the scene from a light source
     * @param geoPoint the starting point of the ray
     * @param lightDistance the distance from the relevent light
     * @param l the vector to the light
     * @param n the normal
     * @return the transparency factor
     */
    private Double3 transparency(GeoPoint geoPoint, double lightDistance, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        var intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return new Double3(1.0);
        Double3 ktr = Double3.ONE;
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0) {
                ktr = ktr.product(gp.geometry.getMaterial().kT);
                if (ktr.lowerThan(MIN_CALC_COLOR_K)) return new Double3(0.0);
            }
        }
        return ktr;
    }
}
