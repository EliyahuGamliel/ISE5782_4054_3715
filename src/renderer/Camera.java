package renderer;

import static primitives.Util.getSign;
import static primitives.Util.isZero;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

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
    private Scatterer scatterer;
    private boolean adaptiveSampling = false;

    private long pixelCounter = 0;
    Timer timer = new Timer();

    private int adaptiveSamplingDepth = 2;

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
    public Camera setAdaptiveSampling(boolean adaptiveSampling) {
        this.adaptiveSampling = adaptiveSampling;
        return this;
    }
    public Camera setAdaptiveSamplingDepth(int adaptiveSamplingDepth) {
        this.adaptiveSamplingDepth = adaptiveSamplingDepth;
        return this;
    }
    public Camera setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }
    public Camera setScatterer(Scatterer scatterer) {
        this.scatterer = scatterer;
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
     * starts timer which will print the progress every 15 seconds
     */
    private void startTimer() {
        this.timer.schedule(new TimerTask() {
                private long total = imageWriter.getNx() * imageWriter.getNy();
                @Override
                public void run() {
                        System.out.format("%.1f%% : %,d pixels done from %,d\n",
                                        ((double)pixelCounter/total) * 100,
                                        pixelCounter,
                                        total);
                        // writeToImage();
                }
        }, 1000, 15000);
    }

    /**
     * stops the timer that startTimer() started
     */
    private void stopTimer() {
        this.timer.cancel();
    }

    /**
     * create a ray from the camera through a specific pixel in the View Plane
     * @param nX how many pixels are in the X dim
     * @param nY how many pixels are in the Y dim
     * @param j the pixel to go through X dim
     * @param i the pixel to go through Y dim
     * @return the constructed Ray
     */
    private Ray constructRay(int nX, int nY, int j, int i) {
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
     * create a ray from the camera through a specific pixel in the View Plane
     * @param nX how many pixels are in the X dim
     * @param nY how many pixels are in the Y dim
     * @param j the pixel to go through X dim
     * @param i the pixel to go through Y dim
     * @return the constructed Ray
     */
    private List<Ray> constructBeamOfRay(int nX, int nY, int j, int i) {
        Point imgCenter = location.add(vTo.scale(distance));
        double rY = height / nY, rX = width / nX;
        double iY = -(i - (nY - 1d) / 2) * rY, jX = (j - (nX - 1d) / 2) * rX;
        Point ijP = imgCenter;
        if (jX != 0) ijP = ijP.add(vRight.scale(jX));
        if (iY != 0) ijP = ijP.add(vUp.scale(iY));
        List<Point> ijPs = scatterer.createPointsAround(ijP, rX, rY, vRight, vUp);
        List<Ray> rays = new ArrayList<>();
        for (Point p : ijPs) {
            Vector ijV = p.subtract(location);
            rays.add(new Ray(location, ijV));
        }
        return rays;
    }

    /**
     * calculate the color of a pixel with adaptive super sampling technic
     * this helper function calls the recursive {@link #adaptiveSampling(Point, double, double, Color, Color, Color, Color, int)} and give the entire pixel as the area
     * @param nX how many pixels are in the X dim
     * @param nY how many pixels are in the Y dim
     * @param j the pixel to go through X dim
     * @param i the pixel to go through Y dim
     * @return the final color of this pixel
     */
    private Color adaptiveSamplingHelper(int nX, int nY, int j, int i) {
        Point imgCenter = location.add(vTo.scale(distance));
        double rY = height / nY, rX = width / nX;
        double iY = -(i - (nY - 1d) / 2) * rY, jX = (j - (nX - 1d) / 2) * rX;
        Point ijP = imgCenter;
        if (jX != 0) ijP = ijP.add(vRight.scale(jX));
        if (iY != 0) ijP = ijP.add(vUp.scale(iY));

        Point leftUp = ijP.add(vRight.scale(-rX/2)).add(vUp.scale(rY/2));
        Point rightUp = ijP.add(vRight.scale(rX/2)).add(vUp.scale(rY/2));
        Point leftDown = ijP.add(vRight.scale(-rX/2)).add(vUp.scale(-rY/2));
        Point rightDown = ijP.add(vRight.scale(rX/2)).add(vUp.scale(-rY/2));

        Color leftUpColor = rayTracerBase.traceRay(new Ray(location, leftUp.subtract(location)));
        Color rightUpColor = rayTracerBase.traceRay(new Ray(location, rightUp.subtract(location)));
        Color leftDownColor = rayTracerBase.traceRay(new Ray(location, leftDown.subtract(location)));
        Color rightDownColor = rayTracerBase.traceRay(new Ray(location, rightDown.subtract(location)));

        return adaptiveSampling(ijP, rX, rY,
                        leftUpColor, rightUpColor,
                        leftDownColor, rightDownColor,
                        adaptiveSamplingDepth);
    }

    /**
     * the recursive function
     * calculate the color of an area given this parameters
     * calls itself on every quarter if necessery
     * @param center center point of this area
     * @param rX width of the area
     * @param rY height of the area
     * @param leftUpColor color of the upper left cornor
     * @param rightUpColor color of the upper right cornor
     * @param leftDownColor color of the lower left cornor
     * @param rightDownColor color of the lower right cornor
     * @param depth recursion max depth stops when <= 0
     * @return
     */
    private Color adaptiveSampling(Point center, double rX, double rY,
                                Color leftUpColor, Color rightUpColor,
                                Color leftDownColor, Color rightDownColor,
                                int depth) {
        if (depth <= 0) {
            Color color = Color.BLACK;
            color = color.add(leftUpColor, rightUpColor, leftDownColor, rightDownColor);
            return color.reduce(4);
        }
        if (leftUpColor.equals(rightUpColor) && leftUpColor.equals(leftDownColor) && leftUpColor.equals(rightDownColor)) {
            return leftUpColor;
        }
        else {
            Point up = center.add(vUp.scale(rY/2));
            Point down = center.add(vUp.scale(-rY/2));
            Point left = center.add(vRight.scale(-rY/2));
            Point right = center.add(vRight.scale(rY/2));

            Color upColor = rayTracerBase.traceRay(new Ray(location, up.subtract(location)));
            Color downColor = rayTracerBase.traceRay(new Ray(location, down.subtract(location)));
            Color leftColor = rayTracerBase.traceRay(new Ray(location, left.subtract(location)));
            Color rightColor = rayTracerBase.traceRay(new Ray(location, right.subtract(location)));
            Color centerColor = rayTracerBase.traceRay(new Ray(location, center.subtract(location)));

            leftUpColor = adaptiveSampling(center.add(vRight.scale(-rX/4)).add(vUp.scale(rY/4)), rX/2, rY/2, 
                                leftUpColor, upColor, leftColor, centerColor, depth-1);
            rightUpColor = adaptiveSampling(center.add(vRight.scale(rX/4)).add(vUp.scale(rY/4)), rX/2, rY/2, 
                                upColor, rightUpColor, centerColor, rightColor, depth-1);
            leftDownColor = adaptiveSampling(center.add(vRight.scale(-rX/4)).add(vUp.scale(-rY/4)), rX/2, rY/2, 
                                leftColor, centerColor, leftDownColor, downColor, depth-1);
            rightDownColor = adaptiveSampling(center.add(vRight.scale(rX/4)).add(vUp.scale(-rY/4)), rX/2, rY/2, 
                                centerColor, rightColor, downColor, rightDownColor, depth-1);

            Color color = Color.BLACK;
            color = color.add(leftUpColor, rightUpColor, leftDownColor, rightDownColor);
            return color.reduce(4);
        }
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
        if (adaptiveSampling) {
            return adaptiveSamplingHelper(nX, nY, j, i);
        }
        if (scatterer != null) {
            List<Ray> rays = constructBeamOfRay(nX, nY, j, i);
            List<Color> colors = rays.stream()
                            .map(ray -> rayTracerBase.traceRay(ray))
                            .collect(Collectors.toList());
            return Util.calcColorAverage(colors);
        }
        else {
            Ray ray = constructRay(nX, nY, j, i);
            return rayTracerBase.traceRay(ray);
        }
    }
    
    /**
     * render the image to buffer
     * multithreaded
     * @return builder design
     */
    public Camera renderImage() {
        if (imageWriter == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "Image Writer");

        if (rayTracerBase == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "Ray Tracer Base");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        startTimer();

        IntStream.range(0, nY).parallel().forEach(i -> {
            IntStream.range(0, nX).parallel().forEach(j -> { 
                Color color = this.castRay(nX, nY, j, i);
                imageWriter.writePixel(j, i, color);
                pixelCounter++;
            });
        });

        System.out.println("100% :)");

        stopTimer();

        return this;
    }
    /**
     * print grid oer the buffer
     * @param interval how offten the pattern should repeate
     * @param color the color of the grid
     * @return builder design
     */
    public Camera printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("Camera resource not set", "Camera", "Image writer");

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nX; i += interval)
            for (int j = 0; j < nY; ++j) {
                imageWriter.writePixel(j, i, color);
                imageWriter.writePixel(i, j, color);
            }
        return this;
    }

    /**
     * write the buffer to file
     */
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
        double angleRad = Math.toRadians(angle);
        double cos0 = Math.cos(angleRad);
        double sin0 = Math.sin(angleRad);
        if (isZero(cos0)) {
            vUp = vRight.scale(getSign(angle));
        } else if (isZero(sin0)) {
            vUp = vUp.scale(cos0);
        }
        else {//rotate around the To vector using Rodrigues' rotation formula
            vUp = vUp.scale(cos0)
                    .add(vTo.crossProduct(vUp).scale(sin0));
        }
        vRight = vTo.crossProduct(vUp).normalize();
        return this;
    }

    /**
     * spin the camera 'angle' degrees clockwise around the Up vector
     * @param angle  the angle we want to spin the camera
     * @return this instance of camera
     */
    public Camera spinRightLeft(double angle) {
        double angleRad = Math.toRadians(angle);
        double cos0 = Math.cos(angleRad);
        double sin0 = Math.sin(angleRad);
        if (isZero(cos0)) {
            vTo = vRight.scale(getSign(angle));
        } else if (isZero(sin0)) {
            vTo = vTo.scale(cos0);
        }
        else {//rotate around the To vector using Rodrigues' rotation formula
            vTo = vTo.scale(cos0)
                .add(vUp.crossProduct(vTo).scale(Math.sin(angle)));
        }
        vRight = vTo.crossProduct(vUp).normalize();
        return this;
    }

    /**
     * spin the camera 'angle' degrees clockwise around the Right vector
     * @param angle the angle we want to spin the camera 
     * @return this instance of camera
     */
    public Camera spinUpDown(double angle) {
        double angleRad = Math.toRadians(angle);
        double cos0 = Math.cos(angleRad);
        double sin0 = Math.sin(angleRad);
        if (isZero(cos0)) {
            vTo = vUp.scale(getSign(angle));
        } else if (isZero(sin0)) {
            vTo = vUp.scale(cos0);
        }
        else {//rotate around the To vector using Rodrigues' rotation formula
            vTo = vTo.scale(cos0)
                .add(vRight.crossProduct(vTo).scale(sin0));
        }
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
