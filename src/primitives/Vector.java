package primitives;

/**
 *
 */
public class Vector extends Point {

    /**
     *
     * @param x
     * @param y
     * @param z
     * @throws IllegalArgumentException
     */
    public Vector(double x, double y, double z) throws IllegalArgumentException {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("The ZERO Vector");
    }

    Vector(Double3 xyz) throws IllegalArgumentException {
        super(xyz);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("The ZERO Vector");
    }

    @Override
    public String toString() {
        return String.format("Vector: (%.2f,%.2f,%.2f)",this.xyz.d1,this.xyz.d2,this.xyz.d3);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    /**
     *
     * @param vector
     * @return
     */
    public Vector add(Vector vector) {
        return new Vector(this.xyz.add(vector.xyz));
    }

    /**
     *
     * @param number
     * @return
     */
    public Vector scale(double number) {
        return new Vector(this.xyz.scale(number));
    }

    /**
     *
     * @return
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     *
     * @return
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     *
     * @return
     */
    public Vector normalize() {
        return new Vector(this.xyz.d1/this.length(), this.xyz.d2/this.length(), this.xyz.d3/this.length());
    }

    /**
     *
     * @param vector
     * @return
     */
    public double dotProduct(Vector vector) {
        Double3 product = this.xyz.product(vector.xyz);
        return product.d1 + product.d2 + product.d3;
    }

    /**
     *
     * @param vector
     * @return
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(this.xyz.d2*vector.xyz.d3-this.xyz.d3*vector.xyz.d2,
                this.xyz.d3*vector.xyz.d1-this.xyz.d1*vector.xyz.d3,
                this.xyz.d1*vector.xyz.d2-this.xyz.d2*vector.xyz.d1);
    }


}
