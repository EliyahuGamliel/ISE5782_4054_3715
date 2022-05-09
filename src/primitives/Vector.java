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
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("The ZERO Vector");
    }

    /**
     * create a vector using a Double3 object (used for inner purposes)
     * @param xyz the Double3
     * @throws IllegalArgumentException when the xyz parameter creates the ZERO vector
     */
    Vector(Double3 xyz) throws IllegalArgumentException {
        super(xyz);
        if (xyz.equals(Double3.ZERO))
            throw new IllegalArgumentException("The ZERO Vector");
    }

    @Override
    public String toString() {
        return String.format("Vector: (%.2f,%.2f,%.2f)",xyz.d1,xyz.d2,xyz.d3);
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
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * scale a vector by a number
     * @param number for the scaling
     * @return a new scaled vector
     */
    public Vector scale(double number) {
        try {
            return new Vector(xyz.scale(number));
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * calculates the squared length of the vector
     * @return the squared length of the vector
     */
    public double lengthSquared() {
        return xyz.d1*xyz.d1 + xyz.d2*xyz.d2 + xyz.d3*xyz.d3;
    }

    /**
     * calculates the length of the vector
     * @return the length of the vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * normalize the vector (vector with the length of 1)
     * @return a new normalized vector
     */
    public Vector normalize() {
        return new Vector(xyz.reduce(length()));
    }

    /**
     * calculated the dot product of two vector
     * @param vector the other vector for the procedure
     * @return the dot product of the two vectors
     */
    public double dotProduct(Vector vector) {
        Double3 product = xyz.product(vector.xyz);
        return product.d1 + product.d2 + product.d3;
    }

    /**
     * calculated the cross product of two vector
     * @param vector the other vector for the procedure
     * @return the cross product of the two vectors
     */
    public Vector crossProduct(Vector vector) {
        return new Vector(xyz.d2*vector.xyz.d3-xyz.d3*vector.xyz.d2,
                xyz.d3*vector.xyz.d1-xyz.d1*vector.xyz.d3,
                xyz.d1*vector.xyz.d2-xyz.d2*vector.xyz.d1);
    }

    public   Vector   Roatate ( double   angle  ,  Vector   axis  ){ 
        angle  =  angle  /  180  *  Math . PI  ;  
        double   cosa  =  Math . cos ( angle  ) ,  sina  =  Math . sin ( angle );  
        double    x  =  axis . xyz . d1  ,  y  =  axis . xyz . d2  ,  z = axis . xyz . d3  , x2  =  x * x  , y2  = y * y ,  z2  =  z * z ;  
        double   tx  =  this . xyz . d1  ,  ty  =  this . xyz . d2  , tz  = this . xyz . d3  ;  
        return   new   Vector ( 
          ( x2 *( 1 - cosa )+  cosa  )* tx  +  ( x * y *( 1 - cosa )- sina )* ty  + ( x * z *( 1 - cosa )+ y * sina )* tz  ,  
          ( x * y *( 1 - cosa )+ z * sina )* tx  + ( y2 *( 1 - cosa )+ cosa )* ty  + ( y * z *( 1 - cosa )- x * sina )* tz  ,  
          ( x * z *( 1 - cosa )- y * sina )* tx  + ( y * z *( 1 - cosa )+ x * sina )* ty  + ( z2 *( 1 - cosa )+ cosa )* tz   
       ); 

   }
}
