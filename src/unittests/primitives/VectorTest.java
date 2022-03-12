package unittests.primitives;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 */
class VectorTest {

    /**
     * Test method for {@link primitives.Vector#add(Vector)}
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1,2,3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, -2, -3);
        assertEquals(new Point(1, 0, 0), v1.add(v2),
                "add() wrong result Vector");


        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(-1, -2, -3);
        assertThrows(IllegalArgumentException.class, () -> v1.add(v3),
                "add() that create the Vector ZERO does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}
     */
    @Test
    void testScale() {
        Vector v1 = new Vector(1,2,3);

        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Point(3, 6, 9), v1.scale(3),
                "scale() wrong result Vector");
    }

    /**
     * Test method for {@link Vector#lengthSquared()}
     */
    @Test
    void testLengthSquared() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============

        assertTrue(isZero(v1.lengthSquared() - 14),
                "lengthSquared() wrong value");
    }

    /**
     * Test method for {@link Vector#length()}
     */
    @Test
    void testLength() {
        Vector v1 = new Vector(0, 3, 4);

        // ============ Equivalence Partitions Tests ==============

        assertTrue(isZero(v1.length() - 5),
                "length() wrong value");
    }

    /**
     * Test method for {@link Vector#normalize()}
     */
    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 3);


        // =============== Boundary Values Tests ==================
        Vector u = v.normalize();
        assertTrue(isZero(u.length() - 1),
                "the normalized vector is not a unit vector");

        assertThrows(IllegalArgumentException.class, () -> v.crossProduct(u),
                "the normalized vector is not parallel to the original one");

        assertFalse(v.dotProduct(u) < 0,
                "the normalized vector is opposite to the original one");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}
     */
    @Test
    void testDotProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(-2, -4, -6);

        assertTrue(isZero(v1.dotProduct(v2) + 28),
                "dotProduct() wrong value");

        // =============== Boundary Values Tests ==================
        Vector v3 = new Vector(0, 3, -2);

        assertTrue(isZero(v1.dotProduct(v3)),
                "dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}
     */
    @Test
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v3 = new Vector(0, 3, -2);

        Vector vr = v1.crossProduct(v3);
        assertTrue(isZero(vr.length() - v1.length() * v3.length()),
                "crossProduct() wrong result length");

        assertTrue(isZero(vr.dotProduct(v1)) || !isZero(vr.dotProduct(v3)),
                "crossProduct() result is not orthogonal to its operands");

        // =============== Boundary Values Tests ==================
        Vector v2 = new Vector(-2, -4, -6);

        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2),
                "crossProduct() for parallel vectors does not throw an exception");
    }
}