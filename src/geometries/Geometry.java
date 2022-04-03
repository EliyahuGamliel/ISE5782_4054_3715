package geometries;

import primitives.*;

/**
 * a body in a 3d space
 */
public abstract class Geometry extends Intersectable {

    protected Color emission = Color.BLACK;
    protected Material material = new Material();

    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * returns the normal of the surface at a specific point
     * @param point the point
     * @return Vector of the normal
     */
    public abstract Vector getNormal(Point point);
}
