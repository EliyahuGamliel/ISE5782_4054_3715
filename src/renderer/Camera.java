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

    public Camera setRayTracer(RayTracerBase rayTracerBase) {
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
     * create a ray from the camera through a specific pixel in the View Plane
     * @param nX how many pixels are in the X dim
     * @param nY how many pixels are in the Y dim
     * @param j the pixel to go through X dim
     * @param i the pixel to go through Y dim
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

    /**
     * construct a ray from the camera throu a specific pixel in the View Plane
     * and get the color of the pixel
     * @param nX how many pixels are in the X dim
     * @param nY how many pixels are in the Y dim
     * @param j the pixel to go through X dim
     * @param i the pixel to go through Y dim
     * @return the color of the pixel
     */
    private Color castRay(int nX, int nY, int j, int i) {
        Ray ray = this.constructRay(nX, nY, j, i);
        return rayTracerBase.traceRay(ray);
    }

    public Camera renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "Image Writer");

        if (rayTracerBase == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "Ray Tracer Base");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int j = 0; j < nX; j++) {
            for (int i = 0; i < nY; i++) {
                Color color = this.castRay(nX, nY, j, i);
                imageWriter.writePixel(j, i, color);
            }
        }
        return this;
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

    /**
     * spin the camera 'angle' degrees clockwise around the To vector
     * @param angle the angle we want to spin the camera
     * @return this instance of camera
     */
    public Camera spin(double angle) {
        if (angle == 90 || angle == -270) {
            vUp = vRight;
            vRight = vTo.crossProduct(vUp).normalize();
            return this;
        } else if (angle == 270 || angle == -90) {
            vUp = vRight.scale(-1);
            vRight = vTo.crossProduct(vUp).normalize();
            return this;
        }
        angle = Math.toRadians(angle);
        //rotate around the To vector using Rodrigues' rotation formula
        vUp = vUp.scale(Math.cos(angle))
                .add(vTo.crossProduct(vUp).scale(Math.sin(angle)));
        vRight = vTo.crossProduct(vUp).normalize();
        return this;
    }

    /**
     * spin the camera 'angle' degrees clockwise around the Up vector
     * @param angle  the angle we want to spin the camera
     * @return this instance of camera
     */
    public Camera spinRightLeft(double angle) {
        if (angle == 90 || angle == -270) {
            vTo = vRight;
            vRight = vTo.crossProduct(vUp).normalize();
            return this;
        } else if (angle == 270 || angle == -90) {
            vTo = vRight.scale(-1);
            vRight = vTo.crossProduct(vUp).normalize();
            return this;
        }
        angle = Math.toRadians(angle);
        //rotate around the Up vector using Rodrigues' rotation formula
        vTo = vTo.scale(Math.cos(angle))
                .add(vUp.crossProduct(vTo).scale(Math.sin(angle)));
        vRight = vTo.crossProduct(vUp).normalize();
        return this;
    }

    /**
     * spin the camera 'angle' degrees clockwise around the Right vector
     * @param angle the angle we want to spin the camera 
     * @return this instance of camera
     */
    public Camera spinUpDown(double angle) {
        if (angle == 90 || angle == -270) {
            vRight = vUp;
            vUp = vTo.crossProduct(vRight).scale(-1).normalize();
            return this;
        } else if (angle == 270 || angle == -90) {
            vRight = vUp.scale(-1);
            vUp = vTo.crossProduct(vRight).scale(-1).normalize();
            return this;
        }
        angle = Math.toRadians(angle);
        //rotate around the Right vector using Rodrigues' rotation formula
        vTo = vTo.scale(Math.cos(angle))
                .add(vRight.crossProduct(vTo).scale(Math.sin(angle)));
        vUp = vTo.crossProduct(vRight).scale(-1).normalize();
        return this;
    }

    /**
     * move the camera 'distance' units along the To vector
     * @param distance the distance we want to move the camera
     * @return this instance of camera
     */
    public Camera moveForward(double distance) {
        this.location = this.location.add(this.vTo.scale(distance));
        return this;
    }

    /**
     * move the camera 'distance' units along the Right vector
     * @param distance the distance we want to move the camera
     * @return this instance of camera
     */
    public Camera moveRightLeft(double distance) {
        this.location = this.location.add(this.vRight.scale(distance));
        return this;
    }

    /**
     * move the camera 'distance' units along the Up vector
     * @param distance the distance we want to move the camera
     * @return this instance of camera
     */
    public Camera moveUpDown(double distance) {
        this.location = this.location.add(this.vUp.scale(distance));
        return this;
    }
}
