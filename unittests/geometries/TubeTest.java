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
        Point p12 = new Vector(0,0,-1);
        Point p22 = new Vector(1,0,0);
        Tube tube = new Tube(new Ray(new Point(0,0,-1), p12.subtract(p22)), 3d);

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)

        assertNull(tube.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3,1,0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Point> result = tube.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(p1, p2), result, "Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        p1 = new Point(1.1937129433613967, 0.6937129433613968, 0.6937129433613968);
        result = tube.findIntersections(new Ray(new Point(0.5,0,0), new Vector(1,1,1)));
        assertEquals(result.size(), 1, "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses sphere");

        // TC04: Ray starts after the sphere (0 points)
        assertNull(tube.findIntersections(new Ray(new Point(4, 0, 0), new Vector(1, 0, 0))),
                "Ray starts after the sphere");
    }
}