package renderer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;

public class IntegretionTest {

    /**
     * an helper method to sum intersection
     * @param camera
     * @param body
     * @param nX
     * @param nY
     * @return sum of intersections
     */
    int sumIntersection(Camera camera, Intersectable body, int nX, int nY) {
        int sum = 0;
        // sum the intersections for each ray from each pixel
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                Ray ray = camera.constructRay(nX, nY, j, i);
                List<Point> res = body.findIntersections(ray);
                if (res != null) {
                    sum += res.size();
                }
            }
        }
        return sum;
    }

    @Test
    void shpereTest() {
        Camera camera1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0))
            .setVPSize(3, 3)
            .setVPDistance(1);
        Sphere s1 = new Sphere(new Point(0, 0, -3), 1);
        int sum1 = sumIntersection(camera1, s1, 3, 3);

        //TC01: test shpere with radius 1
        assertEquals(2, sum1,
            "constructRay() and findIntersections() wrong result");

        
        Camera camera2 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, 1), new Vector(0, -1, 0))
            .setVPSize(3, 3)
            .setVPDistance(1);
        Sphere s2 = new Sphere(new Point(0, 0, -2.5), 2.5);
        int sum2 = sumIntersection(camera2, s2, 3, 3);

        //TC02: test shpere with radius 2.5
        assertEquals(18, sum2,
            "constructRay() and findIntersections() wrong result");
        

        Camera camera3 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, 1), new Vector(0, -1, 0))
            .setVPSize(3, 3)
            .setVPDistance(1);
        Sphere s3 = new Sphere(new Point(0, 0, -2), 2);
        int sum3 = sumIntersection(camera3, s3, 3, 3);

        //TC03: test shpere with radius 2
        assertEquals(10, sum3,
            "constructRay() and findIntersections() wrong result");


        Camera camera4 = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, 1), new Vector(0, -1, 0))
            .setVPSize(3, 3)
            .setVPDistance(1);
        Sphere s4 = new Sphere(new Point(0, 0, -2), 4);
        int sum4 = sumIntersection(camera4, s4, 3, 3);

        //TC04: test shpere with radius 4
        assertEquals(9, sum4,
            "constructRay() and findIntersections() wrong result");


        Camera camera5 = new Camera(new Point(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0))
            .setVPSize(3, 3)
            .setVPDistance(1);
        Sphere s5 = new Sphere(new Point(0, 0, 1), 0.5);
        int sum5 = sumIntersection(camera5, s5, 3, 3);

        //TC04: test shpere with radius 5
        assertEquals(0, sum5,
            "constructRay() and findIntersections() wrong result");
    }

    @Test
    void planeTest() {
        Camera camera1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0))
            .setVPSize(3, 3)
            .setVPDistance(1);
        Plane p1 = new Plane(new Point(1, 1, -3), new Vector(0, 0, 1));
        int sum1 = sumIntersection(camera1, p1, 3, 3);

        //TC01: test plane parallar to view plane
        assertEquals(9, sum1,
            "constructRay() and findIntersections() wrong result");


        Camera camera2 = new Camera(new Point(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0))
            .setVPSize(3, 3)
            .setVPDistance(1);
        Plane p2 = new Plane(new Point(0, 0, -3), new Vector(0, -0.5, 1));
        int sum2 = sumIntersection(camera2, p2, 3, 3);

        //TC02: test plane not parallar to view plane
        assertEquals(9, sum2,
            "constructRay() and findIntersections() wrong result");

        
        Camera camera3 = new Camera(new Point(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0))
            .setVPSize(3, 3)
            .setVPDistance(1);
        Plane p3 = new Plane(new Point(0, 0, -3), new Vector(0, -1, 1));
        int sum3 = sumIntersection(camera3, p3, 3, 3);

        //TC03: test plane not parallar to view plane
        assertEquals(6, sum3,
            "constructRay() and findIntersections() wrong result");

        
    }

    @Test
    void TriangleTest() {
        Camera camera1 = new Camera(new Point(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0))
            .setVPSize(3, 3)
            .setVPDistance(1);
        Triangle t1 = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        int sum1 = sumIntersection(camera1, t1, 3, 3);

        //TC01: test plane not parallar to view plane
        assertEquals(1, sum1,
            "constructRay() and findIntersections() wrong result");


        Camera camera2 = new Camera(new Point(0, 0, 0), new Vector(0, 0, 1), new Vector(0, -1, 0))
            .setVPSize(3, 3)
            .setVPDistance(1);
        Triangle t2 = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        int sum2 = sumIntersection(camera2, t2, 3, 3);

        //TC01: test plane not parallar to view plane
        assertEquals(2, sum2,
            "constructRay() and findIntersections() wrong result");
    }
}
