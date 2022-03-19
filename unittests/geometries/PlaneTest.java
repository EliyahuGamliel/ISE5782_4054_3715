package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class PlaneTest {

    @Test
    void PlaneCtor() {
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 0, 1),
                                                                     new Point(0, 0, 2),
                                                                     new Point(0, 0, 3)),
                "PlaneCtor() for same line point does not throw an exception");

        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 0, 1),
                                                                     new Point(0, 0, 1),
                                                                     new Point(1, 2, 3)),
                "PlaneCtor() for same points does not throw an exception");
    }


    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Plane p1 = new Plane(new Point(1, 2, 3), new Point(2, 1.2, 4), new Point(3, 4, 5));
        Plane p2 = new Plane(new Point(1, 2, 3), new Vector(3, 4, 5));

        //TC01: Test that getNormal of Plane made of 3 points works
        assertEquals(new Vector(-0.7071067811865476, 0.0, 0.7071067811865476),
                p1.getNormal(new Point(1, 2, 3)),
                "getNormal() wrong result");

        //TC02: Test that getNormal of Plane made of point and a normal works
        assertEquals(new Vector(3, 4, 5).normalize(), p2.getNormal(new Point(1, 2, 3)),
                "getNormal() wrong result");
    }

    @Test
    void findIntersections() {
        Plane p1 = new Plane(new Point(-4, 4, 1), new Vector(-2, -2, -1));

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the plane
        Ray r1 = new Ray(new Point(5, 3, 0), new Vector(-4, -2.5, 4));
        assertEquals(List.of(new Point(-5d/3d, -7d/6d, 20d/3d)), p1.findIntersections(r1),
                "findIntersections() wrong result");

        //TC02: Ray does not intersects the plane
        Ray r2 = new Ray(new Point(5, 3, 0), new Vector(4, 2.5, 4));
        assertNull(p1.findIntersections(r2),
                "findIntersections() wrong result");

        // =============== Boundary Values Tests ==================
        //TC11: Ray is parallel to the plane and included in the plane
        Ray r3 = new Ray(new Point(-4, 4, 1), new Vector(7f/3f, -31f/6f, 17f/3f));
        assertNull(p1.findIntersections(r3),
                "findIntersections() for parallel and included Ray is wrong");

        //TC12: Ray is parallel to the plane and not included in the plane
        Ray r4 = new Ray(new Point(1, 2, 3), new Vector(4, 2.5, 4));
        assertNull(p1.findIntersections(r4),
                "findIntersections() for parallel and not included Ray is wrong");

        //TC13: Ray is orthogonal to the plane and p0 before the plane
        Ray r5 = new Ray(new Point(5, 3, 0), new Vector(-2, -2, -1));
        assertEquals(List.of(new Point(5d/3d, -1d/3d, -5d/3d)), p1.findIntersections(r5),
                "findIntersections() for orthogonal Ray before the plane is wrong");

        //TC14: Ray is orthogonal to the plane and p0 in the plane
        Ray r6 = new Ray(new Point(3, -3, 1), new Vector(-2, -2, -1));
        assertNull(p1.findIntersections(r6),
                "findIntersections() for orthogonal Ray in the plane is wrong");

        //TC15: Ray is orthogonal to the plane and p0 after the plane
        Ray r7 = new Ray(new Point(-4, -3, 0), new Vector(-4, -3, 0));
        assertNull(p1.findIntersections(r7),
                "findIntersections() for orthogonal Ray after the plane is wrong");

        //TC16: Ray is neither orthogonal nor parallel to and begins at the plane (p0 is in the plane, but not the ray)
        Ray r8 = new Ray(new Point(3, 3, 1), new Vector(1, 2, 3));
        assertNull(p1.findIntersections(r8),
                "findIntersections() for neither orthogonal nor parallel Ray in the plane is wrong");

        //TC17: Ray is neither orthogonal nor parallel to the plane and begins in the same point (p0 equals q)
        Ray r9 = new Ray(new Point(-4, 4, 1), new Vector(1, 2, 3));
        assertNull(p1.findIntersections(r9),
                "findIntersections() for neither orthogonal nor parallel Ray in the plane is wrong");
    }
}