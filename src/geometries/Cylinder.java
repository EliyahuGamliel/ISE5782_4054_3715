package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.*;

/**
 *  a finite tube
 */
public class Cylinder extends Tube implements Boundable {
    final private double height;

    /**
     * create a cylinder with a ray and a radius
     * @param exisRay the ray on which the cylinder is based
     * @param radius the radius of the cylinder
     * @param height the height of the cylinder
     */
    public Cylinder(Ray exisRay, double radius, double height) {
        super(exisRay, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        double t = exisRay.getDir().dotProduct(point.subtract(exisRay.getP0()));
        if (isZero(t) || isZero(t - height))
            return exisRay.getDir();
        else
            return super.getNormal(point);
    }

    @Override
    public String toString() {
        return "Cylinder [" + super.toString() + "]";
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> res = new ArrayList<>();
        List<GeoPoint> lst = super.findGeoIntersectionsHelper(ray, maxDistance);
        if (lst != null)
            for (GeoPoint geoPoint : lst) {
                double distance = alignZero(geoPoint.point.subtract(exisRay.getP0()).dotProduct(exisRay.getDir()));
                if (distance > 0 && distance <= height)
                    res.add(geoPoint);
            }

        if (res.size() == 0)
            return null;
        return res;
    }

    @Override
    public AxisAlignedBoundingBox getBoundingBox() {
        double minX, minY, minZ, maxX, maxY, maxZ;

        Point o1 = exisRay.getP0();//middle of first end
        Point o2 = o1.add(exisRay.getDir().scale(height));//middle of second end


        //middle point of side circles plus a radius offset is a good approximation for the bounding box
        if (o1.getX() > o2.getX()) {
            maxX = o1.getX() + radius;
            minX = o2.getX() - radius;
        }

        else {
            maxX = o2.getX() + radius;
            minX = o1.getX() - radius;
        }

        if (o1.getY() > o2.getY()) {
            maxY = o1.getY() + radius;
            minY = o2.getY() - radius;
        }

        else {
            maxY = o2.getY() + radius;
            minY = o1.getY() - radius;
        }

        if (o1.getZ() > o2.getZ()) {
            maxZ = o1.getZ() + radius;
            minZ = o2.getZ() - radius;
        }

        else {
            maxZ = o2.getZ() + radius;
            minZ = o1.getZ() - radius;
        }

        AxisAlignedBoundingBox res = new AxisAlignedBoundingBox(minX,minY,minZ,maxX,maxY,maxZ);
        res.addToContains(this);

        return res;
    }
}
