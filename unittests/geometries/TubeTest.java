package geometries;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void getNormal() {
        Tube t1 = new Tube(new Ray(new Point(0, 0, 0), new Vector(1, 1, 0)), 1.0);
        // ============ Equivalence Partitions Tests ==============

        //TC01: Test that getNormal of Tube works
        assertEquals(new Vector(-0.7071067811865476, 0.7071067811865476, 0.0), t1.getNormal(new Point(0, 1.414213562, 0)),
                "getNormal() wrong result");
        
        // =============== Boundary Values Tests ==================

        //TC11: Test that getNormal in the same point as the center of Tube works
        assertEquals(new Vector(0, 0, 1), t1.getNormal(new Point(0, 0, 1)),
                "getNormal() wrong result");
    }
}