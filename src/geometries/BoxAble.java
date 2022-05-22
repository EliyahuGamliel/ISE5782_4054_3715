package geometries;

/**
 * interface for geometries that can be bordered in a box (finite geometries)
 */
public interface BoxAble {
    /**
     * gets the box that bounding the geometry, so all the points of the geometry is in the box,
     * and the box axis are orthogonal to the axis.
     * @return the bounding box
     */
    Box getBox();
}
