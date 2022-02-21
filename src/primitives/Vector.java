package primitives;

public class Vector extends Point {
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (super.equals(Double3.ZERO))
            throw new IllegalArgumentException("The ZERO Vector");
    }

    @Override
    public String toString() {
        return "Vector: ({},{},{})".formatted(this.xyz.d1,this.xyz.d2,this.xyz.d3);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    public Vector add(Vector vector) {
        return new Vector(this.xyz.d1*vector.xyz.d1, this.xyz.d2*vector.xyz.d2, this.xyz.d3*vector.xyz.d3);
    }

    public Vector scale(double number) {
        return new Vector(this.xyz.d1*number, this.xyz.d2*number, this.xyz.d3*number);
    }

    public double lengthSquared() {
        return (this.xyz.d1)*(this.xyz.d1)+(this.xyz.d2)*(this.xyz.d2)+(this.xyz.d3)*(this.xyz.d3);
    }

    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    public Vector normalize() {
        return new Vector(this.xyz.d1/this.length(), this.xyz.d2/this.length(), this.xyz.d3/this.length());
    }

    public double dotProduct(Vector vector) {
        return this.xyz.d1*vector.xyz.d1 + this.xyz.d2*vector.xyz.d2 + this.xyz.d3*vector.xyz.d3;
    }

    public Vector crossProduct(Vector vector) {
        return new Vector(this.xyz.d2*vector.xyz.d3-this.xyz.d3*vector.xyz.d2,
                this.xyz.d3*vector.xyz.d1-this.xyz.d1*vector.xyz.d3,
                this.xyz.d1*vector.xyz.d2-this.xyz.d2*vector.xyz.d1);
    }
}
