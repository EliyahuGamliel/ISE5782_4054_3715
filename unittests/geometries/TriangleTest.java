package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Triangle class
 */
public class TriangleTest {
    /**
     * Test method for {@link Triangle#findIntersections(Ray)}
     */
    @Test
    void findIntersections() {
        Triangle p1 = new Triangle(new Point(1, 2, 3), new Point(-1, -2, 4), new Point(4, 2, 1));

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersect inside Triangle
        Ray r1 = new Ray(new Point(-1, -1, 1), new Vector(1, 1, 1));
        assertEquals(1, p1.findIntersections(r1).size(),
                        "findIntersections() wrong result");

        //TC02: Ray intersect outside Triangle against edge
        Ray r2 = new Ray(new Point(-1, -1, 1), new Vector(1, 1, 4));
        assertNull(p1.findIntersections(r2),
                        "findIntersections() wrong result");

        //TC03: Ray intersect outside Triangle against vertex
        Ray r3 = new Ray(new Point(-1, -1, 1), new Vector(7, 4, -1));
        assertNull(p1.findIntersections(r3),
                        "findIntersections() wrong result");

        // =============== Boundary Values Tests ==================
        //TC11: Ray intersect on edge
        Ray r4 = new Ray(new Point(-1, -1, 1), new Vector(2.6, 3, 1.6));
        assertNull(p1.findIntersections(r4),
                        "findIntersections() Ray intersect on edge wrong result");

        //TC12: Ray intersect in vertex
        Ray r5 = new Ray(new Point(-1, -1, 1), new Vector(0, -1, 3));
        assertNull(p1.findIntersections(r5),
                        "findIntersections() Ray intersect in vertex wrong result");

        //TC13: Ray intersect on edge's continuation
        Ray r6 = new Ray(new Point(-1, -1, 1), new Vector(3, 5, 1.5));
        assertNull(p1.findIntersections(r6),
                        "findIntersections() Ray intersect on edge's continuation wrong result");
    }
}
