package geometries;

import org.junit.jupiter.api.Test;

import primitives.Point;

import static org.junit.jupiter.api.Assertions.*;

import primitives.Vector;

class SphereTest {

    @Test
    void getNormal() {
        Sphere s1 = new Sphere(new Point(1,1,1), 1);

        // ============ Equivalence Partitions Tests ==============

        //TC01: Test that getNormal of Sphere works
        assertEquals(new Vector(-1, -1, -1).normalize(), s1.getNormal(new Point(0,0,0)),
                "getNormal() wrong result");
    }
}