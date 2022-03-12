package geometries;

import org.junit.jupiter.api.Test;

import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;


class PlaneTest {

    @Test
    void PlaneCtor() {
        // =============== Boundary Values Tests ==================
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 0, 1), new Point(0, 0, 2), new Point(0, 0, 3)),
                "PlaneCtor() for same line point does not throw an exception");
        
        assertThrows(IllegalArgumentException.class, () -> new Plane(new Point(0, 0, 1), new Point(0, 0, 1), new Point(1, 2, 3)),
                "PlaneCtor() for same points does not throw an exception");
    }


    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Plane p1 = new Plane(new Point(1, 2, 3), new Point(2, 1.2, 4), new Point(3, 4, 5));
        Plane p2 = new Plane(new Point(1, 2, 3), new Vector(3, 4, 5));
        
        //TC01: Test that getNormal of Plane made of 3 points works
        assertEquals(new Vector(-0.7071067811865476, 0.0, 0.7071067811865476), p1.getNormal(new Point(1, 2, 3)),
        "getNormal() wrong result");
        
        //TC02: Test that getNormal of Plane made of point and a normal works
        assertEquals(new Vector(3,4,5).normalize(), p2.getNormal(new Point(1, 2, 3)),
                "getNormal() wrong result");
        }
}