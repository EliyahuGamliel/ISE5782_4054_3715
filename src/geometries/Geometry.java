package geometries;

import primitives.*;

/**
 * a body in a 3d space
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * returns the normal of the surface at a specific point
     * @param point the point
     * @return Vector of the normal
     */
    public abstract Vector getNormal(Point point);
}
