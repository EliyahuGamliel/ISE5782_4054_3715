package renderer;

import primitives.*;
import static primitives.Util.*;

public class Camera {
    private Point location;
    private Vector vTo, vUp, vRight;
    private double width, height;
    private double distance;

    public Camera(Point location, Vector vTo, Vector vUp) {
        if (! isZero(vTo.dotProduct(vUp))) {
            throw new IllegalArgumentException("vUp and vTo are not orthogonal");
        }

        this.location = location;
        this.vTo = vTo.normalize();
        this.vUp = vUp.normalize();
        this.vRight = vTo.crossProduct(vUp).normalize();
    }

    public Point getLocation() {
        return location;
    }
    public Vector getvUp() {
        return vUp;
    }
    public Vector getvTo() {
        return vTo;
    }
    public Vector getvRight() {
        return vRight;
    }
    public double getWidth() {
        return width;
    }
    public double getHeight() {
        return height;
    }
    public double getDistance() {
        return distance;
    }
    
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }
    
    public Ray constructRay(int nX, int nY, int j, int i) {
        return null;
    }
}
