package geometries;

import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import primitives.Point;
import primitives.Ray;

/**
 *
 */
public class AxisAlignedBoundingBox extends Intersectable implements Boundable {
    /**
     * The minimum values of the box on the axis
     */
    private double minX, minY, minZ;


    /**
     * The maximum values of the box on the axis
     */
    private double maxX, maxY, maxZ;


    /**
     * The middle of the box on the axis
     */
    private double midX, midY, midZ;


    /**
     * A list of the contained boundable objects
     */
    private List<Boundable> contains;
    //Boundable left, right = null;


    /**
     * Create an AABB given furthest axis values
     *
     * @param minX minimum x value
     * @param minY minimum y value
     * @param minZ minimum z value
     * @param maxX maximum x value
     * @param maxY maximum y value
     * @param maxZ maximum z value
     */
    public AxisAlignedBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;

        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

        this.midX = (minX + maxX) / 2;
        this.midY = (minY + maxY) / 2;
        this.midZ = (minZ + maxZ) / 2;

        contains = new ArrayList<>();
    }


    /**
     * Create an AABB that encapsulates a list of Boxes
     *
     * @param boxes the list of boxes to bound
     */
    public AxisAlignedBoundingBox(List<AxisAlignedBoundingBox> boxes) {
        this.maxX = boxes.get(0).getMaxX();
        this.maxY = boxes.get(0).getMaxY();
        this.maxZ = boxes.get(0).getMaxZ();
        this.minX = boxes.get(0).getMinX();
        this.minY = boxes.get(0).getMinY();
        this.minZ = boxes.get(0).getMinZ();


        for (int i = 1; i < boxes.size(); i++) {
            if (boxes.get(i).getMaxX() > maxX) {
                this.maxX = boxes.get(i).getMaxX();
            }
            if (boxes.get(i).getMaxY() > maxY) {
                this.maxY = boxes.get(i).getMaxY();
            }
            if (boxes.get(i).getMaxZ() > maxZ) {
                this.maxZ = boxes.get(i).getMaxZ();
            }
            if (boxes.get(i).getMinX() < minX) {
                this.minX = boxes.get(i).getMinX();
            }
            if (boxes.get(i).getMinY() < minY) {
                this.minY = boxes.get(i).getMinY();
            }
            if (boxes.get(i).getMinZ() < minZ) {
                this.minZ = boxes.get(i).getMinZ();
            }

        }

        this.midX = (minX + maxX) / 2;
        this.midY = (minY + maxY) / 2;
        this.midZ = (minZ + maxZ) / 2;

        contains = new ArrayList<>();
    }

    /**
     * @return The value of minimum X.
     */
    public double getMinX() {
        return minX;
    }

    /**
     * @return The value of minimum Y.
     */
    public double getMinY() {
        return minY;
    }

    /**
     * @return The value of minimum Z.
     */
    public double getMinZ() {
        return minZ;
    }

    /**
     * @return The value of maximum X.
     */
    public double getMaxX() {
        return maxX;
    }

    /**
     * @return The value of maximum Y.
     */
    public double getMaxY() {
        return maxY;
    }

    /**
     * @return The value of maximum Z.
     */
    public double getMaxZ() {
        return maxZ;
    }

    /**
     * @return The list of all the contained boundables.
     */
    public List<Boundable> getContains() {
        return contains;
    }

    /**
     * Add an object to contains
     *
     * @param boundable object to add
     */
    public void addToContains(Boundable boundable) {
        this.contains.add(boundable);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        //the ray's head and direction points
        Point dir = ray.getDir();
        Point point = ray.getP0();

        double xMax, yMax, zMax, xMin, yMin, zMin;

        //if the vector's x coordinate is zero
        if (isZero(dir.getZ())) {
            //if the point's x value is in the box,
            if (maxX >= point.getX() && minX <= point.getX()) {
                xMax = Double.MAX_VALUE;
                xMin = Double.MIN_VALUE;
            } else
                return null;
        }

        //if the vector's x coordinate is not zero, we need to check if we have values
        //where (MaxX - pointX) / dirX > (MinX - pointX) / dirX
        else {
            double t1 = (maxX - point.getX()) / dir.getX();
            double t2 = (minX - point.getX()) / dir.getX();
            xMin = Math.min(t1, t2);
            xMax = Math.max(t1, t2);

        }

        //if the vector's y coordinate is zero
        if (isZero(dir.getY())) {
            //if the point's y value is in the box,
            if (maxX >= point.getY() && minY <= point.getY()) {
                yMax = Double.MAX_VALUE;
                yMin = Double.MIN_VALUE;
            } else
                return null;
        }

        //if the vector's y coordinate is not zero, we need to check if we have values
        //where (MaxY - pointY) / dirY > (MinY - pointY) / dirY
        else {
            double t1 = (maxY - point.getY()) / dir.getY();
            double t2 = (minY - point.getY()) / dir.getY();
            yMin = Math.min(t1, t2);
            yMax = Math.max(t1, t2);
        }

        //if the vector's z coordinate is zero
        if (isZero(dir.getZ())) {
            //if the point's z value is in the box,
            if (maxZ >= point.getZ() && minZ <= point.getZ()) {
                zMax = Double.MAX_VALUE;
                zMin = Double.MIN_VALUE;
            } else
                return null;

        }

        //if the vector's z coordinate is not zero, we need to check if we have values
        //where (MaxZ - pointZ) / dirZ > (MinZ - pointZ) / dirZ
        else {
            double t1 = (maxZ - point.getZ()) / dir.getZ();
            double t2 = (minZ - point.getZ()) / dir.getZ();
            zMin = Math.min(t1, t2);
            zMax = Math.max(t1, t2);
        }

        //check if such a point exists.
        if (xMin > yMax || xMin > zMax ||
                yMin > xMax || yMin > zMax ||
                zMin > yMax || zMin > xMax)
            return null;//if not return null

            //if they do, return all the intersection points of the contents of the box
        else {
            List<GeoPoint> lst = new ArrayList<>();
            for (Boundable geo : contains) {
                List<GeoPoint> pointLst = ((Intersectable) geo).findGeoIntersections(ray, maxDistance);
                if (pointLst != null)
                    lst.addAll(pointLst);
            }

            return lst;
        }
    }

    @Override
    public AxisAlignedBoundingBox getBoundingBox() {
        return this;//a bounding box bounds itself
    }

    /**
     * Creates an AABB tree given a list of boundable objects.
     * Used to create an AABB tree for a scene with geometries.
     *
     * @param boundables a list of boundable objects
     * @return AABB tree if size > 0, else null
     */
    public static AxisAlignedBoundingBox createTree(List<Boundable> boundables) {

        //if we got 0 boundables to bound
        if (boundables.size() == 0)
            return null;


        else {
            //turn the list of boundables into a list of boxes that encapsulate the boundables
            ArrayList<AxisAlignedBoundingBox> boxes = new ArrayList<>(boundables.stream().map(Boundable::getBoundingBox).collect(Collectors.toList()));
            return createTreeRec(boxes);
        }
    }


    /**
     * Create a tree of boxes given a list of boxes to turn into a tree
     *
     * @param boxes list of boxes
     * @return AABB tree
     */
    private static AxisAlignedBoundingBox createTreeRec(ArrayList<AxisAlignedBoundingBox> boxes) {
        //create a box that encapsulates all the other ones
        AxisAlignedBoundingBox node = new AxisAlignedBoundingBox(boxes);

        //find the longest edge of the box
        double x = node.maxX - node.minX;
        double y = node.maxY - node.minY;
        double z = node.maxZ - node.minZ;
        int edge = x > y && x > z ? 0 : (y > x && y > z ? 1 : 2);

        //base of the recursion, if the list has 1 box, return it
        if (boxes.size() == 1) {
            return boxes.get(0);
        }

        //base of the recursion, if the list has 2 boxes
        if (boxes.size() <= 2) {
            for (AxisAlignedBoundingBox box : boxes) {
                node.addToContains(box);//add them to this box
            }
        }

        //recursion step, if we have more boxes left
        else {
            ArrayList<AxisAlignedBoundingBox> left, right;

            //sort the boxes according to how they align on that edge
            sortBoxesByAxis(boxes, edge);

            //split the list into 2 even pieces
            left = new ArrayList<>(boxes.subList(0, boxes.size() / 2));
            right = new ArrayList<>(boxes.subList(boxes.size() / 2, boxes.size()));


            //go down the recursion
            node.addToContains(createTreeRec(left));
            node.addToContains(createTreeRec(right));

        }
        return node;
    }

    /**
     * Sorts a bounding box list according to the axis given
     *
     * @param boxes List of boxes to sort
     * @param axis  Axis to sort by
     */
    public static void sortBoxesByAxis(ArrayList<AxisAlignedBoundingBox> boxes, int axis) {
        switch (axis) {
            case 0:
                boxes.sort((AxisAlignedBoundingBox x, AxisAlignedBoundingBox y) -> Double.compare(y.midX, x.midX));
            case 1:
                boxes.sort((AxisAlignedBoundingBox x, AxisAlignedBoundingBox y) -> Double.compare(y.midY, x.midY));
            case 2:
                boxes.sort((AxisAlignedBoundingBox x, AxisAlignedBoundingBox y) -> Double.compare(y.midZ, x.midZ));
        }
    }

    /**
     * A function that returns a list of all the geometries kept
     * inside an AABB tree
     *
     * @return List of geometries
     */
    public List<Intersectable> getAllGeometries() {
        List<Intersectable> res = new ArrayList<>();

        for (Boundable item : contains) {
            //Base of the recursion, we found geometry
            if (item instanceof Geometry) {
                res.add((Intersectable) item);
            }

            //Recursion step, return all the geometries in the inside box
            else {
                res.addAll(((AxisAlignedBoundingBox) item).getAllGeometries());
            }
        }
        return res;
    }
}