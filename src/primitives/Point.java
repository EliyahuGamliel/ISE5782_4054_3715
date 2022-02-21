package primitives;

import com.ise.Main;

import java.util.Objects;

public class Point {
    final Double3 xyz;

    public Point(double x, double y, double z) {
        this.xyz = new Double3(x, y, z);
    }

    public Vector subtract(Point point) {
        return new Vector(this.xyz.d1 - point.xyz.d1, this.xyz.d2 - point.xyz.d2, this.xyz.d3 - point.xyz.d3);
    }

    public Point add(Vector vector) {
        return new Point(this.xyz.d1 + vector.xyz.d1, this.xyz.d2 + vector.xyz.d2, this.xyz.d3 + vector.xyz.d3);
    }

    public double distanceSquared(Point point2) {
        return (point2.xyz.d1-this.xyz.d1)*(point2.xyz.d1-this.xyz.d1)+
                (point2.xyz.d2-this.xyz.d2)*(point2.xyz.d2-this.xyz.d2)+
                (point2.xyz.d3-this.xyz.d3)*(point2.xyz.d3-this.xyz.d3);
    }

    public double distance(Point point2) {
        return Math.sqrt(distanceSquared(point2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Objects.equals(xyz, point.xyz);
    }

    @Override
    public String toString() {
        return "Point: ({},{},{})".formatted(this.xyz.d1,this.xyz.d2,this.xyz.d3);
    }
}
