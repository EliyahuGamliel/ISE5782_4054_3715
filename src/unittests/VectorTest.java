package unittests;

import org.junit.jupiter.api.Test;
import primitives.*;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 */
public class VectorTest {
    /**
     * Test method for {@link Vector#add(Vector)}
     */
    @Test
    void testAdd() {
        Vector v1 = new Vector(1,2,3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(-1, -2, -3);
        assertEquals(new Point(0, 0, 0), v1.add(v2),
                "add() wrong result Vector");
    }

    /**
     *
     */
    @Test
    void testScale() {
        Vector v1 = new Vector(1,2,3);

        // ============ Equivalence Partitions Tests ==============
        assertEquals(new Point(3, 6, 9), v1.scale(3),
                "scale() wrong result Vector");


    }
}
