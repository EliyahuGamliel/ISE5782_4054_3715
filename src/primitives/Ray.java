package primitives;

import java.util.Objects;

/**
 *
 */
public class Ray {
    final private Point p0;
    final private Vector dir;

    /**
     *
     * @param p0
     * @param dir
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     *
     * @return
     */
    public Point getP0() {
        return new Point(p0.xyz.d1,p0.xyz.d2,p0.xyz.d3);
    }

    /**
     *
     * @return
     */
    public Vector getDir() {
        return new Vector(dir.xyz.d1,dir.xyz.d2,dir.xyz.d3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    @Override
    public String toString() {
        return "Ray: " +
                String.format("point: (%.2f,%.2f,%.2f)",this.p0.xyz.d1,this.p0.xyz.d2,this.p0.xyz.d3) +
                String.format("dir: (%.2f,%.2f,%.2f)",this.dir.xyz.d1,this.dir.xyz.d2,this.dir.xyz.d3);
    }
}
