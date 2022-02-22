package primitives;

import com.ise.Main;

import java.util.Objects;

/**
 *
 */
public class Point {
    final Double3 xyz;

    /**
     *
     * @param x
     * @param y
     * @param z
     */
    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    /**
     *
     * @param point
     * @return new Vector from this point and another point
     */
    public Vector subtract(Point point) {
        return new Vector(this.xyz.d1 - point.xyz.d1, this.xyz.d2 - point.xyz.d2, this.xyz.d3 - point.xyz.d3);
    }

    /**
     *
     * @param vector
     * @return new Point that
     */
    public Point add(Vector vector) {
        return new Point(this.xyz.d1 + vector.xyz.d1, this.xyz.d2 + vector.xyz.d2, this.xyz.d3 + vector.xyz.d3);
    }

    /**
     *
     * @param point2
     * @return
     */
    public double distanceSquared(Point point2) {
        return (point2.xyz.d1-this.xyz.d1)*(point2.xyz.d1-this.xyz.d1)+
                (point2.xyz.d2-this.xyz.d2)*(point2.xyz.d2-this.xyz.d2)+
                (point2.xyz.d3-this.xyz.d3)*(point2.xyz.d3-this.xyz.d3);
    }

    /**
     *
     * @param point2
     * @return
     */
    public double distance(Point point2) {
        return Math.sqrt(distanceSquared(point2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (this.getClass() == o.getClass()) {
            Point point = (Point) o;
            return Objects.equals(xyz, point.xyz);
        }
        else if(o.getClass() == Double3.class) {
            Double3 double3 = (Double3) o;
            return Objects.equals(xyz, o);
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Point: (%.2f,%.2f,%.2f)",this.xyz.d1,this.xyz.d2,this.xyz.d3);
    }
}
