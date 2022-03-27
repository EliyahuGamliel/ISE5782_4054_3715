package renderer;

import primitives.*;

import java.util.MissingResourceException;

import static primitives.Util.*;

/**
 * Class to represent a Camera in 3d space
 * camera has a View Plane and can make rays throu that View Plane
 */
public class Camera {
    private Point location;
    private Vector vTo, vUp, vRight;
    private double width, height;
    private double distance;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracerBase;

    /**
     * create a camera specifying the location and the To and Up vectors
     * @param location the camera location
     * @param vTo the towords direction
     * @param vUp the up direction
     */
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
    public ImageWriter getImageWriter() {
        return imageWriter;
    }
    public RayTracerBase getRayTracerBase() {
        return rayTracerBase;
    }

    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracerBase(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * set the View Plane size
     * @param width the width of the VP
     * @param height the height of the VP
     * @return self
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }
    /**
     * set the View Plane distance from the camera
     * @param distance the VP distance from the camera
     * @return self
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }
    /**
     * create a ray from the camera throu a specific pixel in the View Plane
     * @param nX how many pixels are in the X dim
     * @param nY how many pixels are in the Y dim
     * @param j the pixel to go throu X dim 
     * @param i the pixel to go throu Y dim 
     * @return the constructed Ray
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        Point imgCenter = location.add(vTo.scale(distance));
        double rY = height / nY, rX = width / nX;
        double iY = -(i - (nY - 1d) / 2) * rY, jX = (j - (nX - 1d) / 2) * rX;
        Point ijP = imgCenter;
        if (jX != 0) ijP = ijP.add(vRight.scale(jX));
        if (iY != 0) ijP = ijP.add(vUp.scale(iY));
        Vector ijV = ijP.subtract(location);
        return new Ray(location, ijV);
    }

    public void renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "Image Writer");

        if (rayTracerBase == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "Ray Tracer Base");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int j = 0; j < nX; j++) {
            for (int i = 0; i < nY; i++) {
                Ray ray = this.constructRay(nX, nY, j, i);
                Color color = rayTracerBase.traceRay(ray);
                imageWriter.writePixel(j, i, color);
            }
        }

    }

    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "Image writer");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nX; i += interval)
            for (int j = 0; j < nY; ++j) {
                imageWriter.writePixel(j, i, color);
                imageWriter.writePixel(i, j, color);
            }
    }

    public void writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "Image writer");

        imageWriter.writeToImage();
    }
}
