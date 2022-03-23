package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {

    @Test
    void findClosestPoint() {
        Ray r = new Ray(new Point(1,2,3), new Vector(2,-4,6));

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersect inside Triangle
        //TC13: The point is the last point in the list
        List<Point> l1 = List.of(new Point(3.66,-3.32,10.99), new Point(0,0,0), new Point(-20,0,0));
        Point p1 = r.findClosestPoint(l1);
        assertEquals(l1.get(1), p1, "Point in the middle");

        // =============== Boundary Values Tests ==================
        //TC11: List empty
        List<Point> l2 = List.of();
        assertNull(r.findClosestPoint(l2), "There is not points");

        //TC12: The point is the first point in the list
        List<Point> l3 = List.of(new Point(0,0,0), new Point(3.66,-3.32,10.99), new Point(-20,0,0));
        p1 = r.findClosestPoint(l3);
        assertEquals(l3.get(0), p1, "Point in the start");

        //TC13: The point is the last point in the list
        List<Point> l4 = List.of(new Point(3.66,-3.32,10.99), new Point(-20,0,0), new Point(0,0,0));
        p1 = r.findClosestPoint(l4);
        assertEquals(l4.get(2), p1, "Point in the end");
    }
}