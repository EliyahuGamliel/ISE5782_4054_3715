package geometries;

import primitives.Point;
import primitives.Ray;

import java.io.Console;
import java.util.*;

public class Geometries extends Intersectable {
    /**
     * A list of geometries. (According to the composite design pattern)
     */
    private List<Intersectable> geometries;

    /**
     * If true, then the geometries class will use AABB in the calculations, and vice versa.
     */
    private boolean AABB = true;

    /**
     * Constructs a new instance with empty list of geometries.
     */
    public Geometries() {
        this.geometries = new ArrayList<>(); // I used array list because of the constant access time.
    }

    /**
     * Constructs a new instance with a collections of geometries.
     *
     * @param geometries The geometries to insert to the new instance.
     */
    public Geometries(Intersectable... geometries) {
        if (AABB) {
            this.geometries = new ArrayList<>(Arrays.asList(geometries));

            //create a list of all the geometries in the scene
            List<Intersectable> geos = Arrays.asList(geometries);

            //a list of all the boundable geometries in the scene
            List<Boundable> boundables = new ArrayList<>();

            //move all the boundables from geos to boundables list
            for (Intersectable g : geometries) {
                if (g instanceof Boundable) {
                    geos.remove(g);
                    boundables.add((Boundable) g);
                }
            }

            // create a aabb tree for the boundable geometries and add the tree to the geometry list
            if (AxisAlignedBoundingBox.createTree(boundables) != null)
                geos.add(AxisAlignedBoundingBox.createTree(boundables));

            this.geometries = geos;
        } else
            this.geometries = Arrays.asList(geometries);
    }

    /**
     * Add new geometries into the list
     *
     * @param geometries the new geometries to add
     */
    public void add(Intersectable... geometries) {
        if (AABB) {
            //create a list of all the geometries already existing in the scene
            List<Intersectable> geos = new ArrayList<>();

            //add all the un-boundable ones to the ones that are bounded in boxes
            for (Intersectable item : this.geometries) {
                if (item instanceof Geometry)
                    geos.add(item);
                else if (item instanceof Geometries)
                        geos.add(item);
                else
                    geos.addAll(((AxisAlignedBoundingBox) item).getAllGeometries());
            }

            // Add all new geometries to the existing ones
            geos.addAll(Arrays.asList(geometries));

            //a list of all the boundable geometries in the scene
            List<Boundable> boundables = new ArrayList<>();

            //move all the boundables from geos to boundables list
            for (Intersectable g : geometries) {
                if (g instanceof Boundable) {
                    geos.remove(g);
                    boundables.add((Boundable) g);
                }
            }
            // create a aabb tree for the boundable geometries and add the tree to the geometry list

            AxisAlignedBoundingBox aabb = AxisAlignedBoundingBox.createTree(boundables);
            if (aabb != null)
                geos.add(aabb);

            this.geometries = geos;
        } else
            this.geometries.addAll(Arrays.asList(geometries));
    }


    /**
     * Finds all the intersection points with geometries in our list
     *
     * @param ray The ray to check intersection points with.
     * @return List of the geometric intersection points
     */
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        ArrayList<GeoPoint> lst = new ArrayList<>();
        for (Intersectable geometry : geometries) {
            var points = geometry.findGeoIntersections(ray, maxDistance);
            if (points != null) {
                lst.addAll(points);
            }
        }

        if (lst.size() == 0) return null;

        return lst;
    }

    /**
     * @return The list of geometries.
     */
    public List<Intersectable> getGeometries() {
        return geometries;
    }

    /**
     * @param AABB The boolean value to set if use AABB.
     * @return The current instance (Builder pattern).
     */
    public Geometries setAABB(boolean AABB) {
        this.AABB = AABB;
        return this;
    }
}