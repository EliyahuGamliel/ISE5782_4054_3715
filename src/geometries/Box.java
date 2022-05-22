package geometries;
import primitives.Point;
import primitives.Ray;

/**
 * class that represents a bounding box using two points that create vertices that are orthogonal to the axis
 */
public class Box{
    /**
     * the point with the smallest coordinates
     */
    private Point _min;

    /**
     * the point with the biggest coordinates
     */
    private Point _max;

    /**
     * constructor that builds a box using the two boundary points
     * @param min the minimum point
     * @param max the maximum point
     */
    public Box(Point min, Point max) {
        _min = min;
        _max = max;
    }

    /**
     * gets the minimum point
     * @return the point with the smallest coordinates
     */
    public Point getMin() {
        return _min;
    }

    /**
     * sets the minimum point (builder pattern)
     * @param min the point with the smallest coordinates
     * @return this instance of Box
     */
    public Box setMin(Point min) {
        _min = min;
        return this;
    }

    /**
     * gets the maximum point
     * @return the point with the biggest coordinates
     */
    public Point getMax() {
        return _max;
    }

    /**
     * sets the maximum point (builder pattern)
     * @param max the point with the largest coordinates
     * @return this instance of Box
     */
    public Box setMax(Point max) {
        _max = max;
        return this;
    }

    /**
     * checks if a ray intersects this box
     * the code based on the code from the presentation.
     * @param r the ray we want to know if it intersects
     * @return true if it intersects, otherwise false.
     */
    public boolean HasIntersection(Ray r){
        // for each coordinate we will check what is the t  that we have to mult the ray in order
        // to get the minimum/maximum point of the box for that coordinate.
        double tminX, tmaxX, tminY, tmaxY, tminZ, tmaxZ;
        Point dirHead = r.getDir();
        Point p0 = r.getP0();

        // checks the direction of the ray on the x axis, to know which size of the box is closer.
        if (dirHead.getX() >= 0) { // the min point is closer to the ray than the max point
            // find the limits of the min x.
            // according to the equations:
            // 1) p0.x+ tmin*head.x = min.x
            // 2) p0.x+ tmax*head.x = max.x
            tminX = (_min.getX() - p0.getX()) / dirHead.getX(); // rearrange 1) to get tminX
            tmaxX = (_max.getX() - p0.getX()) / dirHead.getX(); // rearrange 2) to get tmaxX
        }
        else { // the max point is closer to the ray than the min point
            tminX = (_max.getX() - p0.getX()) / dirHead.getX();
            tmaxX = (_min.getX() - p0.getX()) / dirHead.getX();
        }
        // exactly the same like the x axis, but in the y axis.
        if (dirHead.getY() >= 0) {
            tminY = (_min.getY() - p0.getY()) / dirHead.getY();
            tmaxY = (_max.getY() - p0.getY()) / dirHead.getY();
        }
        else {
            tminY = (_max.getY() - p0.getY()) / dirHead.getY();
            tmaxY = (_min.getY() - p0.getY()) / dirHead.getY();
        }
        // to minimize calculations, we will calc the z only if the x and the y are possible.
        // the ranges of x and y should have an intersection. if all the y are big from all the x, there is no value of t that t*v+po = point on the box.
        if ( (tminX > tmaxY) || (tminY > tmaxX) )
            return false;

        /*
         * the x are possible only if xmin<x<xmax. the y only possible if ymin<y<ymax. but xmin,xmax,ymin,ymax both values of t!
         * x in a specific t is not possible if the y of this t is not possible!
         * so we have to do intersection of the ranges
         * x: xmin---------xmax
         * y:      ymin--------ymax
         *             ^^^^
         *             this is the range that x and y possible at the same time.
         * the new edges of the range are the maximum min, and the minimum max.
         */
        double totalMin, totalMax;
        totalMin = Math.max(tminY, tminX);
        totalMax = Math.min(tmaxX,tmaxY);

        // calc the zmin and zmax, like the x axis.
        if (dirHead.getZ() >= 0) {
            tminZ = (_min.getZ() - p0.getZ()) / dirHead.getZ();
            tmaxZ = (_max.getZ() - p0.getZ()) / dirHead.getZ();
        }
        else {
            tminZ = (_max.getZ() - p0.getZ()) / dirHead.getZ();
            tmaxZ = (_min.getZ() - p0.getZ()) / dirHead.getZ();
        }

        // the ranges of the intersection of (x and y) should have an intersection with the range of z. if all the (x and y) are big from all the z, there is no value of t that t*v+po = point on the box.
        if ( (totalMin > tmaxZ) || (tminZ > totalMax) )
            return false;
        return true;
    }
}
