package primitives;

/**
 * 3d vector
 */
public class Vector extends Point {

    /**
     * create a vector with 3 doubles
     * @param x length in the x dimension
     * @param y length in the y dimension
     * @param z length in the z dimension
     * @throws IllegalArgumentException when all three parameters are 0 which creates the ZERO vector
     */
    public Vector(double x, double y, double z) throws IllegalArgumentException {
        super(x, y, z);
        if (this.xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("The ZERO Vector");
    }

    /**
     * create a vector using a Double3 object (used for inner purposes)
     * @param xyz the Double3
     * @throws IllegalArgumentException when the xyz parameter creates the ZERO vector
     */
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
     * adds two vectors
     * @param vector the other vector for the procedure
     * @return a new vector result of the addition
     */
    public Vector add(Vector vector) {
        return new Vector(this.xyz.add(vector.xyz));
    }

    /**
     * scale a vector by a number
     * @param number for the scaling
     * @return a new scaled vector
     */
    public Vector scale(double number) {
        return new Vector(this.xyz.scale(number));
    }

    /**
     * calculates the squared length of the vector
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        return this.dotProduct(this);
    }

    /**
     * calculates the length of the vector
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(this.lengthSquared());
    }

    /**
     * normalize the vector (vector with the length of 1)
     * @return a new normalized vector
     */
    public Vector normalize() {
        return new Vector(this.xyz.d1/this.length(), this.xyz.d2/this.length(), this.xyz.d3/this.length());
    }

    /**
     * calculated the dot product of two vector
     * @param vector the other vector for the procedure
     * @return the dot product of the two vectors
     */
    public double dotProduct(Vector vector) {
        Double3 product = this.xyz.product(vector.xyz);
        return product.d1 + product.d2 + product.d3;
    }

    /**
     * calculated the cross product of two vector
     * @param vector the other vector for the procedure
     * @return the cross product of the two vectors
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(this.xyz.d2*vector.xyz.d3-this.xyz.d3*vector.xyz.d2,
                this.xyz.d3*vector.xyz.d1-this.xyz.d1*vector.xyz.d3,
                this.xyz.d1*vector.xyz.d2-this.xyz.d2*vector.xyz.d1);
    }


}
