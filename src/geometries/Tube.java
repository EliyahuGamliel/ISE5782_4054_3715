package geometries;

import primitives.*;

public class Tube implements Geometry {
    
    protected Ray exisRay;
    protected double radius;
    
    
    public Tube(Ray exisRay, double radius) {
        this.exisRay = exisRay;
        this.radius = radius;
    }


    public Ray getExisRay() {
        return exisRay;
    }

    public double getRadius() {
        return radius;
    }


    @Override
    public Vector getNormal(Point point) {
        // TODO calculate normal at a specific point
        return null;
    }


    @Override
    public String toString() {
        return "Tube [exisRay=" + exisRay + ", radius=" + radius + "]";
    }
}
