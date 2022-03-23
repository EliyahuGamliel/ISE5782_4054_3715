package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

public abstract class RayTracerBase {
    protected Scene scene;

    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * return the color of the first hit by a ray or black for no hit
     * @param ray the ray
     * @return the color that go back
     */
    public abstract Color traceRay(Ray ray);
}
