package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

public class Geometries extends Intersectable implements BoxAble{
    private List<Intersectable> geometries;

    /**
     * to use bounding box while finding intersections or not
     */
    public static boolean DoBoundingBox = false;

    /**
     * the min size of objects per BB.
     */
    private static final int MIN_SIZE = 3;

    public Geometries() {
        geometries = new ArrayList<>();
    }

    public Geometries(Intersectable... geometries) {
        this.geometries = new ArrayList<>(Arrays.asList(geometries));
    }

    /**
     * the bounding box that bounds all of the boxables that we only have to calculate once
     */
    private Box _boundingBox = null;

    /**
     * get if we want to use bounding box
     * @return true if we want to use bounding box , otherwise false.
     */
    public boolean isDoBoundingBox() {
        return DoBoundingBox;
    }

    /**
     * set if we want to use bounding box
     * @param doBoundingBox the new state if we want to do bounding box
     * @return this, for builder pattern
     */
    public Geometries setDoBoundingBox(boolean doBoundingBox) {
        DoBoundingBox = doBoundingBox;
        return this;
    }

    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> res = null;
        for (Intersectable geometry : this.geometries) {
            // if we have to use BB, and this geometry can insert into a box, use bounding box acceleration
            if(DoBoundingBox &&  geometry instanceof BoxAble){
                Box box = ((BoxAble) geometry).getBox();
                if (box!=null && !box.HasIntersection(ray)) //if the ray not intersect the box, so it don't intersect the geometry at all.
                    continue; // the ray hasn't intersections with the box, so it hasn't intersection with this geometry at all, so continue to the next geometry.
            }
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

    /**
     * part 3 of the bounding box upgrade that builds a hierarchy of boxes
     */
    public void ReArrange(){

        if(geometries.size() < MIN_SIZE || !DoBoundingBox) //to stop the recursion
            return;

        List<Intersectable> newL = new ArrayList<>();

        for (Intersectable i:geometries) { //remove all of the non boxables
            if(!(i instanceof BoxAble)) {
                newL.add(i);
                geometries.remove(i);
            }

        }

        Box bb = getBox(); //big bounding box

        //find the length of each axis to find the longest one:
        double zAxis = bb.getMax().getZ()-bb.getMin().getZ();
        double yAxis = bb.getMax().getY()-bb.getMin().getY();
        double xAxis = bb.getMax().getX()-bb.getMin().getY();
        double maxAxis = Math.max(Math.max(Math.abs(zAxis),Math.abs(yAxis)),Math.abs(xAxis));

        Geometries left = new Geometries(); //all of the geometries that are left of the middle of the longest axis
        Geometries right = new Geometries(); //all of the geometries that are right of the middle of the longest axis

        for(Intersectable i:geometries){ //check each geometry in the list to see which side it's on
            Box b = ((BoxAble) i).getBox();//get the personal box of the geometry

            boolean isRight;
            double centerOfGeometry; //the center of the geometry on the longest axis = axis/2 + (minimum of the bounding box on the wanted axis)
            double middleOfAxis; //the middle of the longest axis = the average of the minimum and maximum on that axis

            if (maxAxis == Math.abs(zAxis)) {
                middleOfAxis= zAxis/2+bb.getMin().getZ();
                centerOfGeometry= (b.getMax().getZ()+b.getMin().getZ())/2;
                isRight = centerOfGeometry < middleOfAxis;
            }

            else if (maxAxis == Math.abs(yAxis)) {
                middleOfAxis = yAxis/2+bb.getMin().getY();
                centerOfGeometry = (b.getMax().getY()+b.getMin().getY())/2;
                isRight = centerOfGeometry < middleOfAxis;
            }

            else {
                middleOfAxis = xAxis/2+bb.getMin().getX();
                centerOfGeometry = (b.getMax().getX()+b.getMin().getX())/2;
                isRight = centerOfGeometry < middleOfAxis;
            }

            if(isRight)
                right.add(i);

            else
                left.add(i);
        }

        geometries = newL;

        //if one side is empty, that means that the other side has all of the geometries
        // and rearanging will put us in an infinite loop
        //now rearrange each side separately
        if(!right.geometries.isEmpty() && !left.geometries.isEmpty()){
            right.ReArrange();
            left.ReArrange();
        }

        if(!right.geometries.isEmpty())
            this.add(right);

        if(!left.geometries.isEmpty())
            this.add(left);

    }

    @Override
    public Box getBox() {
        if(_boundingBox!=null)
            return _boundingBox;
        // gets the BB of this geometry collection. the BB is a box of all the boxes of this collection.
        // if there are geometries that don't boxable(like planes)- there is no BB, so return null.

        List<Point> points = new ArrayList<>(); // a list of the min & max points of all the boxes.
        for(Intersectable o : geometries){
            if (o instanceof BoxAble){
                Box b = ((BoxAble) o).getBox();
                if(b==null) return null;//b is a geometry collection that have non-boxable geometries.
                points.add(b.getMax());
                points.add(b.getMin());
            }
            else
                return null; //there is non-boxable geometry, so we cant make a big box of all the boxes.
        }
        // now we have a list of all the points. lets find the minimum point, and the maximum point, and build from it the new box.
        _boundingBox =  getBoxFromPoints(points);
        return _boundingBox;
    }

    private Box getBoxFromPoints(List<Point> points){
        Comparator<Point> x = Comparator.comparing(Point::getX); // comparator that finds the min and max x from all the points
        Comparator<Point> y = Comparator.comparing(Point::getY); //comparator that finds the min and max y from all the points
        Comparator<Point> z = Comparator.comparing(Point::getZ); //comparator that finds the min and max z from all the points

        double maxX = Collections.max(points, x).getX();
        double minX = Collections.min(points,x).getX();

        double maxY = Collections.max(points, y).getY();
        double minY = Collections.min(points,y).getY();

        double maxZ = Collections.max(points, z).getZ();
        double minZ = Collections.min(points,z).getZ();

        return new Box(new Point(minX,minY,minZ),new Point(maxX,maxY,maxZ));
    }
}
