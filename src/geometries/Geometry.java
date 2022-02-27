package geometries;

import primitives.*;

/**
 * a body in a 3d space
 */
public interface Geometry {

    /**
     * returns the normal of the surface at a specific point
     * @param point the point
     * @return Vector of the normal
     */
    public Vector getNormal(Point point);
}
