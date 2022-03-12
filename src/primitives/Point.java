package primitives;

import com.ise.Main;

import java.util.Objects;

/**
 * 3d point
 */
public class Point {
    final protected Double3 xyz;

    /**
     * create a point using 3 doubles
     * @param x position in the x dimension
     * @param y position in the y dimension
     * @param z position in the z dimension
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * create a point using the Double3 class (for inner purposes)
     * @param xyz the Double3
     */
    Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * create a vector from the subtraction of two points
     * @param point the second point
     * @return new Vector from this point and another point
     */
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }

    /**
     * add or "move" a point by a vector
     * @param vector vector to move the point by
     * @return new Point that "moved" by the vector
     */
    public Point add(Vector vector) {
        return new Point(xyz.add(vector.xyz));
    }

    /**
     * calculate the squared distance between two points
     * @param point2 the second point
     * @return the squared distance
     */
    public double distanceSquared(Point point2) {
        return (point2.xyz.d1-xyz.d1)*(point2.xyz.d1-xyz.d1)+
                (point2.xyz.d2-xyz.d2)*(point2.xyz.d2-xyz.d2)+
                (point2.xyz.d3-xyz.d3)*(point2.xyz.d3-xyz.d3);
    }

    /**
     * calculate the distance between two points
     * @param point2 the second point
     * @return the distance
     */
    public double distance(Point point2) {
        return Math.sqrt(distanceSquared(point2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof Point point)) return false;
        return Objects.equals(xyz, point.xyz);
    }

    @Override
    public String toString() {
        return String.format("Point: (%.2f,%.2f,%.2f)",xyz.d1,xyz.d2,xyz.d3);
    }
}
