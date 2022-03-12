package primitives;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Point class
 */
class PointTest {

    /**
     * Test method for {@link primitives.Point#subtract(Point)}
     */
    @Test
    void subtract() {
        Point p1 = new Point(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Point p2 = new Point(2, 3, 4);
        Vector vr1 = p2.subtract(p1);

        // TC01: Test that Point subtraction creates the desired Vector
        assertEquals(new Vector(1,1,1), vr1,
                "subtract() wrong result Vector");

        // =============== Boundary Values Tests ==================
        Point p3 = new Point(1,2,3);

        // TC11: Test Point subtraction that create the Vector ZERO
        assertThrows(IllegalArgumentException.class, () -> p3.subtract(p1),
                "subtract() that create the Vector ZERO does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Point#add(Vector)}
     */
    @Test
    void add() {
        Point p1 = new Point(1,2,3);

        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(-1, -2, -3);
        assertEquals(new Point(0, 0, 0), p1.add(v1),
                "add() wrong result Point");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)}
     */
    @Test
    void distanceSquared() {
        Point p1 = new Point(1,1,1);

        // ============ Equivalence Partitions Tests ==============
        Point p2 = new Point(2,3,4);
        Point p3 = new Point(-1,-2,-3);

        // TC01: Test the calculation of the distance squared between two Points
        assertTrue(isZero(p1.distanceSquared(p2) - 14.0),
                "distanceSquared() wrong result distanceSquared");
        assertTrue(isZero(p1.distanceSquared(p3) - 29.0),
                "distanceSquared() wrong result distanceSquared");

        // =============== Boundary Values Tests ==================
        Point p4 = new Point(1,1,1);

        //TC11: Test for distance zero
        assertTrue(isZero(p1.distanceSquared(p4)));
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared(Point)}
     */
    @Test
    void distance() {

        Point p1 = new Point(1,1,1);

        // ============ Equivalence Partitions Tests ==============
        Point p2 = new Point(2,3,4);
        Point p3 = new Point(-1,-2,-3);

        // TC01: Test the calculation of the distance squared between two Points
        assertTrue(isZero(p1.distance(p2) - 3.7416573867739413),
                "distanceSquared() wrong result distance");
        assertTrue(isZero(p1.distance(p3) - 5.385164807134504),
                "distanceSquared() wrong result distance");

        // =============== Boundary Values Tests ==================
        Point p4 = new Point(1,1,1);

        //TC11: Test for distance zero
        assertTrue(isZero(p1.distance(p4)));
    }
}