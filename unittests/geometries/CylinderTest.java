package geometries;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class CylinderTest {

    @Test
    void getNormal() {
        Cylinder c1 = new Cylinder(new Ray(new Point(0, 0, 0), new Vector(1, 1, 0)), 1.0, 5.0);
        // ============ Equivalence Partitions Tests ==============

        //TC01: Test that getNormal of Cylinder works
        assertEquals(new Vector(-0.7071067811865476, 0.7071067811865476, 0.0), c1.getNormal(new Point(0, 1.414213562, 0)),
                "getNormal() wrong result");

        //TC02: Test that getNormal of Cylinder works
        assertEquals(new Vector(1, 1, 0).normalize(), c1.getNormal(new Point(3.5355339059327373, 3.5355339059327373, -0.5)),
                "getNormal() wrong result");
        
        //TC03: Test that getNormal of Cylinder works
        assertEquals(new Vector(1, 1, 0).normalize(), c1.getNormal(new Point(0.0, 0.0, 0.53)),
                "getNormal() wrong result");
        
        // =============== Boundary Values Tests ==================

        //TC11: Test that getNormal in the same point as the center of Cylinder works
        assertEquals(new Vector(0.7071067811865476, 0.7071067811865476, 0.0), c1.getNormal(new Point(0, 0, 1)),
                "getNormal() wrong result");

    }
}