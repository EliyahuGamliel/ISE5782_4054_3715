package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void findIntersections() {

        // ============ Equivalence Partitions Tests ==============
        //TC01: Some shapes are cut (but not all)
        Geometries geometries = new Geometries(new Sphere(new Point(1,1,1), 1),
                new Plane(new Point(-4, 4, 1), new Vector(-2, -2, -1)));
        Ray r1 = new Ray(new Point(0,0,0), new Vector(1.5,1,1));
        List<Point> result = geometries.findIntersections(r1);
        assertEquals(result.size(), 2, "Wrong number of points");

        // =============== Boundary Values Tests ==================
        //TC11: There are no shapes
        Geometries g4 = new Geometries();
        Ray r4 = new Ray(new Point(-1, -1, 1), new Vector(2.6, 3, 1.6));
        assertNull(g4.findIntersections(r4), "There are no shapes");

        //TC12: All shapes are cut
        Ray r5 = new Ray(new Point(-1, -1, 1), new Vector(0, -1, 3));

        //TC13: All shapes are not cut
        Ray r6 = new Ray(new Point(-1, -1, 1), new Vector(3, 5, 1.5));

        //TC14: Only one shape is cut

    }
}