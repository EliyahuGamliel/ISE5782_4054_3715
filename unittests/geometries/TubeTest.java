package geometries;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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

    @Test
    void findIntersections() {
        Tube tube = new Tube(new Ray(new Point(0,0,-1), new Vector(-1,0,1)), 3d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the tube (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(-10, 0, 0), new Vector(-20,4,0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the tube (2 points)

        // TC03: Ray starts inside the tube (1 point)

        // TC04: Ray starts after the tube (0 points)
    }
}