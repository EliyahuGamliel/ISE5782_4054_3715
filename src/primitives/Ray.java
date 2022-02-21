package primitives;

import java.util.Objects;

public class Ray {
    private Point p0;
    private Vector dir;

    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        if (dir.length() != 1)
            dir.normalize();
        this.dir = dir;
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
                "point: ({},{},{})".formatted(this.p0.xyz.d1,this.p0.xyz.d2,this.p0.xyz.d3) +
                "dir: ({},{},{})".formatted(this.dir.xyz.d1,this.dir.xyz.d2,this.dir.xyz.d3);
    }
}
