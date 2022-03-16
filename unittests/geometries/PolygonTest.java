package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Polygon class
 */
public class PolygonTest {

    /**
     * Test method for {@link Polygon#Polygon(Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link Polygon#getNormal(Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        double sqrt3 = Math.sqrt(1d / 3);
        assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)), "Bad normal to trinagle");
    }

    @Test
    void findIntersections() {
        Polygon p1 = new Polygon(new Point(1, 2, 3), new Point(-1, -2, 4), new Point(4, 2, 1));

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersect inside Polygon
        Ray r1 = new Ray(new Point(-1, -1, 1), new Vector(1, 1, 1));
        assertEquals(1, p1.findIntersections(r1).size(),
                        "findIntersections() wrong result");

        //TC02: Ray intersect outside Polygon against edge
        Ray r2 = new Ray(new Point(-1, -1, 1), new Vector(1, 1, 4));
        assertNull(p1.findIntersections(r2),
                        "findIntersections() wrong result");

        //TC03: Ray intersect outside Polygon against vertex
        Ray r3 = new Ray(new Point(-1, -1, 1), new Vector(7, 4, -1));
        assertNull(p1.findIntersections(r3),
                        "findIntersections() wrong result");

        // =============== Boundary Values Tests ==================
        //TC11: Ray intersect on edge
        Ray r4 = new Ray(new Point(-1, -1, 1), new Vector(2.6, 3, 1.6));
        assertEquals(1, p1.findIntersections(r4).size(),
                        "findIntersections() Ray intersect on edge wrong result");

        //TC12: Ray intersect in vertex
        Ray r5 = new Ray(new Point(-1, -1, 1), new Vector(0, -1, 3));
        assertEquals(1, p1.findIntersections(r5).size(),
                        "findIntersections() Ray intersect in vertex wrong result");

        //TC13: Ray intersect on edge's continuation
        Ray r6 = new Ray(new Point(-1, -1, 1), new Vector(3, 5, 1.5));
        assertNull(p1.findIntersections(r6),
                        "findIntersections() Ray intersect on edge's continuation wrong result");
    }
}
