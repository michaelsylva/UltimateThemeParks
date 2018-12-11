package me.toaster.ultimatethemeparks.utils;

import java.io.Serializable;

public class Matrix3 {
	  public double a11, a12, a13;
	  public double a21, a22, a23;
	  public double a31, a32, a33;
	  
	  public Matrix3() {
	      a11=0; a12=0; a13=0;
	      a21=0; a22=0; a23=0;
	      a31=0; a32=0; a33=0;
	  }

	   /**
	    * Assign the zero matrix to this matrix
	    * @return <code>this</code>
	    */
	  public final Matrix3 assignZero() {
	    a11 = 0; a12 = 0; a13 = 0;
	    a21 = 0; a22 = 0; a23 = 0;
	    a31 = 0; a32 = 0; a33 = 0;
	          return this;
	  }
	  
	  /**
	   * Construct a 3x3 matrix using specified fields
	   * @param a11
	   * @param a12
	   * @param a13
	   * @param a21
	   * @param a22
	   * @param a23
	   * @param a31
	   * @param a32
	   * @param a33
	   */
	  public Matrix3(double a11, double a12, double a13, double a21, double a22,
	    double a23, double a31, double a32, double a33) {  
	  this.a11 = a11;
	  this.a12 = a12;
	  this.a13 = a13;
	  this.a21 = a21;
	  this.a22 = a22;
	  this.a23 = a23;
	  this.a31 = a31;
	  this.a32 = a32;
	  this.a33 = a33;
	}

	 /**
	  * create a 3x3 matrix using a set of basis vectors
	  * @param e1
	  * @param e2
	  * @param e3
	  */
	  public Matrix3( Vector3 e1, Vector3 e2, Vector3 e3) {
	    a11 = e1.x;
	    a21 = e1.y;
	    a31 = e1.z;
	    
	    a12 = e2.x;
	    a22 = e2.y;
	    a32 = e2.z;
	    
	    a13 = e3.x;
	    a23 = e3.y;
	    a33 = e3.z;
	  }
	  
	  /**
	   * Construct a new 3x3 matrix as a copy of the given matrix B
	   * @param B
	   * @throws NullPointerException
	   */
	  public Matrix3( Matrix3 B) {
	   assign(B);
	  }

	   /**
	   * assign the value of B to this Matrix3
	   * @param B
	   */
	  public final Matrix3 assign(Matrix3 B) {
	    a11 = B.a11; a12 = B.a12; a13 = B.a13;
	    a21 = B.a21; a22 = B.a22; a23 = B.a23;
	    a31 = B.a31; a32 = B.a32; a33 = B.a33;
	          return this;
	  }

	  /**
	   * Assign the scale matrix given by s, to this matrix
	   */
	  public final Matrix3 assignScale(final double s) {
	    a11 = s; a12 = 0; a13 = 0;
	    a21 = 0; a22 = s; a23 = 0;
	    a31 = 0; a32 = 0; a33 = s;
	          return this;
	  }

	  /**
	   * Assign the non-uniform scale matrix given by s1, s2 and s3, to this matrix
	   */
	  public final Matrix3 assignScale(double sx, double sy, double sz) {
	    a11 = sx; a12 = 0.; a13 = 0.;
	    a21 = 0.; a22 = sy; a23 = 0.;
	    a31 = 0.; a32 = 0.; a33 = sz;
	    return this;
	  }

	  
	  /**
	   * Assign the identity matrix to this matrix
	   */
	  public final Matrix3 assignIdentity() {
	    a11 = 1; a12 = 0; a13 = 0;
	    a21 = 0; a22 = 1; a23 = 0;
	    a31 = 0; a32 = 0; a33 = 1;
	          return this;
	  }

	    public Matrix3 assign(
	            double a11, double a12, double a13,
	            double a21, double a22, double a23,
	            double a31, double a32, double a33) {
	        this.a11 = a11;  this.a12 = a12;  this.a13 = a13;
	  this.a21 = a21;  this.a22 = a22;  this.a23 = a23;
	  this.a31 = a31;  this.a32 = a32;  this.a33 = a33;
	        return this;
	    }
	  /**
	   * Get the n'th column vector of this matrix
	   * @param n
	   * @return
	   * @throws IllegalArgumentException
	   */
	  public final Vector3 column(int n) {
	    switch (n) {
	    case 0:
	      return new Vector3(a11,a21,a31);
	    case 1:
	      return new Vector3(a12,a22,a32);
	    case 2:
	      return new Vector3(a13,a23,a33);
	              default:
	                  throw new IllegalArgumentException();
	    }  
	  }
	  
	  /**
	   * Get the n'th row vector of this matrix
	   * @param n
	   * @return
	   */
	  public Vector3 row(int n) {
	    switch (n) {
	    case 0:
	      return new Vector3(a11,a12,a13);
	    case 1:
	      return new Vector3(a21,a22,a23);
	    case 2:
	      return new Vector3(a31,a32,a33);
	              default:
	                  throw new IllegalArgumentException();
	    }  
	  }

	  
	  /**
	   * Get all column vectors of this matrix
	   * @param c1
	   * @param c2
	   * @param c3
	   */
	  public void getColumnVectors( Vector3 c1, Vector3 c2, Vector3 c3) {
	    c1.x = a11;
	    c1.y = a21;
	    c1.z = a31;

	    c2.x = a12;
	    c2.y = a22;
	    c2.z = a32;
	    
	    c3.x = a13;
	    c3.y = a23;
	    c3.z = a33;
	  }
	  
	  /**
	   * Get all row vectors of this matrix
	   * @param r1
	   * @param R3
	   * @param r3
	   */
	  public void getRowVectors( Vector3 r1, Vector3 R3, Vector3 r3) {
	    r1.x = a11;
	    r1.y = a12;
	    r1.z = a13;

	    R3.x = a21;
	    R3.y = a22;
	    R3.z = a23;
	    
	    r3.x = a31;
	    r3.y = a32;
	    r3.z = a33;
	  }
	    
	  /**
	   * Return a new identity Matrix3 instance
	   * @return
	   */
	  public static Matrix3 identity() {
	    return new Matrix3().assignIdentity();
	  }
	  
	  /**
	   * Multiply this matrix by a scalar, return the resulting matrix
	   * @param s
	   * @return
	   */
	  public final Matrix3 multiply( double s) {
	    Matrix3 A = new Matrix3();
	    A.a11 = a11*s; A.a12 = a12*s; A.a13 = a13*s;
	    A.a21 = a21*s; A.a22 = a22*s; A.a23 = a23*s;
	    A.a31 = a31*s; A.a32 = a32*s; A.a33 = a33*s;    
	    return A;
	  }
	  
	  /**
	   * Right-multiply by a scaling matrix given by s, so M.scale(s) = M S(s)
	   * @param s
	   * @return
	   */
	  public final Matrix3 scale( Vector3 s ) {
	    Matrix3 A = new Matrix3();
	    A.a11 = a11*s.x; A.a12 = a12*s.y; A.a13 = a13*s.z;
	    A.a21 = a21*s.x; A.a22 = a22*s.y; A.a23 = a23*s.z;
	    A.a31 = a31*s.x; A.a32 = a32*s.y; A.a33 = a33*s.z;    
	    return A;
	  }
	  
	  /**
	   * Multiply this matrix by the matrix A and return the result
	   * @param A
	   * @return
	   */
	  public Matrix3 multiply(Matrix3 A) {
	    return multiply(this,A,new Matrix3());
	  }

	    /**
	   * Multiply this matrix by the matrix A and return the result
	   * @param A
	   * @return
	   */
	  public Matrix3 assignMultiply(Matrix3 A) {
	    return multiply(this,A,this);
	  }
	  
	  //C = AxB 
	  public static Matrix3 multiply( final Matrix3 A, final Matrix3 B, final Matrix3 C ) {
	    //               B | b11 b12 b13
	    //                 | b21 b22 b23
	    //                 | b31 b32 b33
	    //     -------------------------
	    //  A  a11 a12 a13 | c11 c12 c13
	    //     a21 a22 a23 | c21 c22 c23
	    //     a31 a32 a33 | c31 c32 c33  C
	    
	    double t11 = A.a11*B.a11 + A.a12*B.a21 + A.a13*B.a31;
	    double t12 = A.a11*B.a12 + A.a12*B.a22 + A.a13*B.a32;
	    double t13 = A.a11*B.a13 + A.a12*B.a23 + A.a13*B.a33;
	    
	    double t21 = A.a21*B.a11 + A.a22*B.a21 + A.a23*B.a31;
	    double t22 = A.a21*B.a12 + A.a22*B.a22 + A.a23*B.a32;
	    double t23 = A.a21*B.a13 + A.a22*B.a23 + A.a23*B.a33;
	    
	    double t31 = A.a31*B.a11 + A.a32*B.a21 + A.a33*B.a31;
	    double t32 = A.a31*B.a12 + A.a32*B.a22 + A.a33*B.a32;
	    double t33 = A.a31*B.a13 + A.a32*B.a23 + A.a33*B.a33;

	    //copy to C
	    C.a11 = t11;
	    C.a12 = t12;
	    C.a13 = t13;

	    C.a21 = t21;
	    C.a22 = t22;
	    C.a23 = t23;

	    C.a31 = t31;
	    C.a32 = t32;
	    C.a33 = t33;

	    return C;
	  }
	  
	  //functional
	  /**
	   * Multiply a vector by this matrix, return the resulting vector
	   */
	  public final Vector3 multiply( final Vector3 v) {
	    Vector3 r = new Vector3();
	    Matrix3.multiply(this, v, r);
	    return r;
	  }
	  
	  
	  //A = A^T 
	  public Matrix3 assignTranspose() {
	    double t;
	  t=a12; a12=a21; a21=t;
	  t=a13; a13=a31; a31=t;
	  t=a23; a23=a32; a32=t;
	    return this;
	  }
	  
	  /**
	   * Functional method. Transpose this matrix and return the result
	   * @return
	   */
	  public final Matrix3 transpose() {
	   return new Matrix3(this).assignTranspose();
	  }


	  //C = A-B
	  public static Matrix3 subtract( final Matrix3 A, final Matrix3 B, final Matrix3 C ) {
	    C.a11 = A.a11-B.a11; C.a12 = A.a12-B.a12; C.a13 = A.a13-B.a13;
	    C.a21 = A.a21-B.a21; C.a22 = A.a22-B.a22; C.a23 = A.a23-B.a23;
	    C.a31 = A.a31-B.a31; C.a32 = A.a32-B.a32; C.a33 = A.a33-B.a33;
	    return C;
	  }
	 /**
	   * Substract to this matrix the matrix B, return result in a new matrix instance
	   * @param B
	   * @return
	   */
	  public Matrix3 subtract( Matrix3 B ) {
	    return subtract(this,B,new Matrix3());
	  }
	  /**
	   * Substract to this matrix the matrix B, return result in a new matrix instance
	   * @param B
	   * @return
	   */
	  public Matrix3 assignSubtract( Matrix3 B ) {
	    return subtract(this,B,this);
	  }
	  /**
	   * Add to this matrix the matrix B, return result in a new matrix instance
	   * @param B
	   * @return
	   */
	  public Matrix3 add( Matrix3 B ) {
	    return add(this,B,new Matrix3());
	  }
	  /**
	   * Add to this matrix the matrix B, return result in a new matrix instance
	   * @param B
	   * @return
	   */
	  public Matrix3 assignAdd( Matrix3 B ) {
	    return add(this,B,this);
	  }
	  
	  //C = A+B
	  public static Matrix3 add( final Matrix3 A, final Matrix3 B, final Matrix3 C ) {
	    C.a11 = A.a11+B.a11; C.a12 = A.a12+B.a12; C.a13 = A.a13+B.a13;
	    C.a21 = A.a21+B.a21; C.a22 = A.a22+B.a22; C.a23 = A.a23+B.a23;
	    C.a31 = A.a31+B.a31; C.a32 = A.a32+B.a32; C.a33 = A.a33+B.a33;
	    return C;
	  }
	  
	  // rT = (vT)A   NOTE that the result of this is actually a transposed vector!! 
	  public static Vector3 transposeVectorAndMultiply( final Vector3 v, final Matrix3 A, final Vector3 r ){
	    //            A  | a11 a12 a13
	    //               | a21 a22 a23
	    //               | a31 a32 a33
	    //      ----------------------
	    // vT   v1 v2 v3 |  c1  c2  c3
	    
	    double t1 = v.x*A.a11+v.y*A.a21+v.z*A.a31;
	    double t2 = v.x*A.a12+v.y*A.a22+v.z*A.a32;
	    double t3 = v.x*A.a13+v.y*A.a23+v.z*A.a33;
	    
	    r.x = t1;
	    r.y = t2;
	    r.z = t3;

	    return r;
	  }

	  /**
	   * Multiply v by A, and place result in r, so r = Av
	   * @param A 3 by 3 matrix
	   * @param v Vector to be multiplied
	   * @param r Vector to hold result of multiplication
	   * @return Reference to the given Vector3 r instance
	   */
	  public static Vector3 multiply( final Matrix3 A, final Vector3 v, final Vector3 r ) {
	    //                   
	    //               V | v1
	    //                 | v2
	    //                 | v3                     
	    //     -----------------
	    //  A  a11 a12 a13 | c1
	    //     a21 a22 a23 | c2
	    //     a31 a32 a33 | c3   
	    
	    double t1 = v.x*A.a11+v.y*A.a12+v.z*A.a13;
	    double t2 = v.x*A.a21+v.y*A.a22+v.z*A.a23;
	    double t3 = v.x*A.a31+v.y*A.a32+v.z*A.a33;
	    
	    r.x = t1;
	    r.y = t2;
	    r.z = t3;
	    
	    return r;
	  }  

	  /**
	   * Compute the determinant of Matrix3 A
	   * @param A
	   * @return 
	   */
	  public double determinant() {
	    return a11*a22*a33- a11*a23*a32 + a21*a32*a13 - a21*a12*a33 + a31*a12*a23-a31*a22*a13;
	  }
	  
	/**
	 * Compute the inverse of the matrix A, place the result in C
	 */
	  public static Matrix3 inverse( final Matrix3 A, final Matrix3 C ) {
	    double d = (A.a31*A.a12*A.a23-A.a31*A.a13*A.a22-A.a21*A.a12*A.a33+A.a21*A.a13*A.a32+A.a11*A.a22*A.a33-A.a11*A.a23*A.a32);
	    double t11 =  (A.a22*A.a33-A.a23*A.a32)/d;
	    double t12 = -(A.a12*A.a33-A.a13*A.a32)/d;
	    double t13 =  (A.a12*A.a23-A.a13*A.a22)/d;
	    double t21 = -(-A.a31*A.a23+A.a21*A.a33)/d;
	    double t22 =  (-A.a31*A.a13+A.a11*A.a33)/d;
	    double t23 = -(-A.a21*A.a13+A.a11*A.a23)/d;
	    double t31 =  (-A.a31*A.a22+A.a21*A.a32)/d;
	    double t32 = -(-A.a31*A.a12+A.a11*A.a32)/d;
	    double t33 =  (-A.a21*A.a12+A.a11*A.a22)/d;

	    C.a11 = t11; C.a12 = t12; C.a13 = t13;
	    C.a21 = t21; C.a22 = t22; C.a23 = t23;
	    C.a31 = t31; C.a32 = t32; C.a33 = t33;
	    return C;
	  }

	  public final Matrix3 assignInverse() {
	      return inverse(this,this);
	  }
	  public final Matrix3 inverse() {
	        return inverse(this,new Matrix3());
	  }

	  public static Matrix3 scaleMatrix( double xs, double ys, double zs) {
	      return new Matrix3().assignScale(xs,ys,zs);
	  }
	  
	  public static Matrix3 scaleMatrix( double s ) {
	      return new Matrix3().assignScale(s);      
	  }
	     
	  @Override
	  public String toString() {
	    return "["+a11+", " + a12 + ", " + a13 + "]\n"
	         + "["+a21+", " + a22 + ", " + a23 + "]\n"
	         + "["+a31+", " + a32 + ", " + a33 + "]" ;
	  }
	  
	  /**
	   * Check matrix for NaN values 
	   */
	  public final boolean isNaN() {
	    return Double.isNaN(a11)||Double.isNaN(a12)||Double.isNaN(a13)
	    || Double.isNaN(a21)||Double.isNaN(a22)||Double.isNaN(a23)
	    || Double.isNaN(a31)||Double.isNaN(a32)||Double.isNaN(a33);
	  }
	  
	  public double[] toArray() {
	       return new double[]{
	                  a11, a21, a31,
	                  a12, a22, a32,
	                  a13, a23, a33};
	  }

	  /**
	   * Return the Frobenius norm of this Matrix3
	   * @return
	   */
	  public final double fnorm() {
	    return Math.sqrt(  a11*a11 + a12*a12 + a13*a13  + a21*a21 + a22*a22  + a23*a23  + a31*a31 + a32*a32 + a33*a33 ); 
	  }
	    /**
	     *
	     * @param v
	     * @return
	     * @throws NullPointerException
	     */
	    public static Matrix3 crossProductMatrix(Vector3 v) {
	        return new Matrix3(
	                0., -v.z, v.y,
	                v.z, 0., -v.x,
	                -v.y, v.x, 0.);
	    }
	}

	/**
	 * <code>Vector3d</code> defines a Vector for a three double value tuple.
	 * <code>Vector3d</code> can represent any three dimensional value, such as a
	 * vertex or normal.
	 *
	 * The functional methods like add, sub, multiply that returns new instances, and
	 * left <code>this</code> unchanged.
	 *
	 * Static methods store the resulting vector on a existing reference, which avoid
	 * allowcation an can improove performances around 20% (profiling performend on vector
	 * addition).
	 *
	 * Deprecated methods will be removed on October 2010
	 *
	 * @author Morten Silcowitz
	 * @author Pierre Labatut 
	 */
	 final class Vector3 implements Serializable {
	  private static final long serialVersionUID = 1L;

	        /**
	         * The x coordinate.
	         */
	  public double x;
	        /**
	         * The y coordinate.
	         */
	        public double y;
	        /**
	         * The z coordinate.
	         */
	        public double z;

	        
	  public transient final static double e = 1e-9f;

	  /**
	         * Constructs and initializes a <code>Vector3</code> to [0., 0., 0.]
	         */
	  public Vector3 () {
	    x=0; y=0; z=0;
	  }
	        /**
	         * Constructs and initializes a <code>Vector3</code> from the specified
	         * xyz coordinates.
	         * @param x the x coordinate
	         * @param y the y coordinate
	         * @param z the z coordinate
	         */
	  public Vector3( double x, double y, double z) {
	    this.x=x; this.y=y; this.z=z;
	  }

	        /**
	         * Constructs and initializes a <code>Vector3</code> with the coordinates
	         * of the given <code>Vector3</code>.
	         * @param v the <code>Vector3</code> containing the initialization x y z data
	         * @throws NullPointerException when v is null
	         */
	  public Vector3( Vector3 v ) {
	    x=v.x; y=v.y; z = v.z;
	  }

	    /**
	     * Create a new unit vector heading positive x
	     * @return a new unit vector heading positive x
	     */
	    public static Vector3 i() {
	        return new Vector3(1., 0., 0.);
	    }
	    /**
	     * Create a new unit vector heading positive y
	     * @return a new unit vector heading positive y
	     */
	    public static Vector3 j() {
	        return new Vector3(0., 1., 0.);
	    }
	    /**
	     * Create a new unit vector heading positive z
	     * @return a new unit vector heading positive z
	     */
	    public static Vector3 k() {
	        return new Vector3(0., 0., 1.);
	    }

	        /**
	         * Adds a provided vector to this vector creating a resultant
	         * vector which is returned.
	         * Neither <code>this</code> nor <code>v</code> is modified.
	         *
	         * @param v the vector to add to this.
	         * @return resultant vector
	         * @throws NullPointerException if v is null
	         */
	  public final Vector3 add( Vector3 v) {
	    return new Vector3( x+v.x, y+v.y, z+v.z );
	  }
	        /**
	         * Multiply the vector coordinates by -1. creating a resultant vector
	         * which is returned.
	         * <code>this</code> vector is not modified.
	         *
	         * @return resultant vector
	         * @throws NullPointerException if v is null
	         */
	  public final Vector3 negate() {
	    return new Vector3(-x,-y,-z);
	  }
	  /**
	         * Returns true if one of the coordinated is not a number
	         * <code>this</code> vector is not modified.
	         * @return true if one of the coordinated is not a number
	         */
	  public final boolean isNaN() {
	    return Double.isNaN(x)||Double.isNaN(y)||Double.isNaN(z);
	  }
	        /**
	         * Get a coordinate from a dimention ordinal.
	         * @param i the dimention ordinal number. 1 is x, 2 is y 3 is z.
	         * @return <ul>
	         *<li>         x coordiante when i is 0</li>
	         *<li>         y coordiante when i is 1</li>
	         *<li>         z coordiante when i is 2</li>
	         * </ul>
	         */
	  public double get( int i ) {
	    return i>0?(i>1?z:y):x; 
	  }
	        /**
	         * Set a coordinate from a dimention ordinal.
	         * @param i the dimention ordinal number. 1 is x, 2 is y 3 is z.
	         * @param v new coordinate value
	         */
	  public void set( int i, double v ) {
	    if (i == 0) {
	      x = v;
	    } else {
	      if ( i==1) {
	        y=v;
	      }else {
	        z=v;
	      }
	    }
	  }
	  
	        /**
	         * Add two vectors and place the result in v1.
	         * <code>v2</code> is not modified.
	         * @param v1 a not null reference, store the sum
	         * @param v2 a not null reference
	         * @throws NullPointerException if v1 or v2 is null
	         */
	  public static void add( final Vector3 v1, final Vector3 v2 ) {
	    v1.x += v2.x;
	    v1.y += v2.y;
	    v1.z += v2.z;
	  }

	        /**
	         * Substract two vectors and place the result in v1.
	         * <code>v2</code> is not modified.
	         * @param v1 a not null reference, store the difference
	         * @param v2 a not null reference
	         * @throws NullPointerException if v1 or v2 is null
	         */
	  public static void sub( final Vector3 v1, final Vector3 v2 ) {
	    v1.x -= v2.x;
	    v1.y -= v2.y;
	    v1.z -= v2.z;
	  }

	        /**
	         * Substracts a provided vector to this vector creating a resultant
	         * vector which is returned.
	         * Neither <code>this</code> nor <code>v</code> is modified.
	         *
	         * @param v the vector to add to this.
	         * @return resultant vector
	         */
	        public final Vector3 sub( Vector3 v ) {
	    return new Vector3( x-v.x, y-v.y, z-v.z );
	  }

	        /**
	         * Multiply this vector by a provided scalar creating a resultant
	         * vector which is returned.
	         * <code>this</code> vector is not modified.
	         *
	         * @param s multiplication coeficient
	         * @return resultant vector
	         */
	  public final Vector3 multiply( double s ) {
	    return new Vector3( x*s, y*s, z*s);
	  }
	  
	  /**
	   * Scale vector by the scale matrix given by s.
	         * <code>this</code> vector is not modified.
	   * @param s scale direction and factor
	   * @return an new vector
	   */
	  public final Vector3 scale( Vector3 s) {
	    return new Vector3(x*s.x, y*s.y, z*s.z);
	  }

	        /**
	         * Multiply a given vector by a scalar and place the result in v
	         * @param v vector multipled
	         * @param s scalar used to scale the vector
	         * @throws NullPointerException if v is null
	         */
	  public static void multiply( Vector3 v, double s) {
	    v.x*=s; v.y*=s; v.z*=s;
	  }

	        /**
	         *
	         * @param v
	         * @param s
	         * @param result
	         * @throws NullPointerException if v ot result is null
	         */
	  public static void multiplyAndAdd( Vector3 v, double s, Vector3 result) {
	    result.x += v.x*s; 
	    result.y += v.y*s; 
	    result.z += v.z*s;
	  }

	  /**
	   * Multiply v by s, and store result in v. Add v to result and store in result
	   * @param v
	   * @param s
	   * @param result
	         * @throws NullPointerException if v ot result is null
	   */
	  public static void  multiplyStoreAndAdd( Vector3 v, double s, Vector3 result) {
	    v.x *= s;
	    v.y *= s;
	    v.z *= s;    
	    result.x += v.x; 
	    result.y += v.y; 
	    result.z += v.z;
	  }

	        /**
	         * Returns the dot product of this vector and vector v.
	         * Neither <code>this</code> nor <code>v</code> is modified.
	         * @param v the other vector
	         * @return the dot product of this and v1
	         * @throws NullPointerException if v is null
	         */
	  public final double dot( Vector3 v ) {
	    return this.x*v.x+this.y*v.y+this.z*v.z;
	  }
	   /**
	         * Returns the dot product of this vector and vector v.
	         * Neither <code>this</code> nor <code>v</code> is modified.
	         * z coordinated if trucated
	         * @param v the other vector
	         * @return the dot product of this and v1
	         * @throws NullPointerException
	         */  
	  public final double xydot( Vector3 v ) {
	    return this.x*v.x+this.y*v.y;
	  }

	  /**
	         * Return a new new set to the cross product of this vectors and v
	         * Neither <code>this</code> nor <code>v</code> is modified.
	         * @param v a not null vector
	         * @return the cross product
	         * @throws NullPointerException when v is null
	         */
	  public final Vector3 cross( final Vector3 v ) {
	    return new Vector3( y*v.z-z*v.y, z*v.x-x*v.z, x*v.y-y*v.x ); 
	  }
	       /**
	        * Sets result vector to the vector cross product of vectors v1 and v2.
	        * Neither <code>v1</code> nor <code>v2</code> is modified.
	        * @param v1 the first vector
	        * @param v2 the second vector
	        * @param result
	        */
	  public static void crossProduct( final Vector3 v1, final Vector3 v2, final Vector3 result ) {
	    final double tempa1 = v1.y*v2.z-v1.z*v2.y;
	    final double tempa2 = v1.z*v2.x-v1.x*v2.z;
	    final double tempa3 = v1.x*v2.y-v1.y*v2.x;

	    result.x = tempa1;
	    result.y = tempa2;
	    result.z = tempa3;
	  }

	        /**
	         * Return a new vector set to the normalization of vector v1.
	         * <code>this</code> vector is not modified.
	         * @return the normalized vector
	         */
	  public final Vector3 normalize() {
	    double l = Math.sqrt(x*x+y*y+z*z);
	    if ( l == 0.0 ) {return new Vector3(1,0,0); }
	                l=1./l;
	    return new Vector3( x*l, y*l, z*l);
	  }
	        /**
	         * Sets the value of this <code>Vector3</code> to the specified x, y and  coordinates.
	         * @param x the x coordinate
	         * @param y the y coordinate
	         * @param z the z coordinate
	         * @return return this
	         */
	        public final Vector3 assign( double x, double y, double z ) {
	    this.x = x;
	    this.y = y;
	    this.z = z;
	    return this;
	  }
	        /**
	         * A this vector to the provided coordinates creating a new resultant vector.
	         * <code>this</code> vector is not modified
	         * @param x the x coordinate
	         * @param y the y coordinate
	         * @param z the z coordinate
	         * @return the result vector
	         */
	  public final Vector3 add( double x, double y, double z ) {
	    return new Vector3( this.x+x, this.y+y, this.z+z);
	  }

	  /**
	         * Sets the value of this vector to the value of the xyz coordinates of the
	         * given vector.
	         * <code>v</code> is not modified
	         * @param v the vector to be copied
	         * @return <code>this</code>
	         * @throws NullPointerException
	         */
	  public final Vector3 assign( Vector3 v ) {
	    double t1 =v.x;
	    double t2 =v.y;
	    double t3 =v.z;
	    x = t1;
	    y = t2;
	    z = t3;
	    return this;
	  }
	        /**
	         *
	         * @return
	         */
	  public final Vector3 assignZero() {
	    x = 0;
	    y = 0;
	    z = 0;
	    return this;
	  }
	 
	        /**
	         * Returns the length of this vector.
	         * <code>this</code> vector is not modified.
	         * @return Returns the length of this vector.
	         */
	  public final double norm() {
	    return Math.sqrt( x*x + y*y + z*z );
	  }
	  /**
	         * Returns the length of this vector.
	         * z coordinate is truncated.
	         * <code>this</code> vector is not modified.
	         * @return Double.NaN when Double.isNaN(x) || Double.isNaN(y)
	         */
	  public final double xynorm() {
	    return Math.sqrt( x*x + y*y );
	  }

	       
	        /**
	         * Returns the length of this vector.
	         * <code>this</code> vector is not modified.
	         * @return the length of this vector
	         */
	  public final double squaredNorm() {
	    return x*x+y*y+z*z;
	  }

	       
	    /**
	     * Returns <tt>true</tt> if the absolute value of the three coordinates are
	     * smaller or equal to epsilon.
	     *
	     * @param epsilon positive tolerance around zero
	     * @return true when the coordinates are next to zero
	     *         false in the other cases
	     */
	    public final boolean isEpsilon(double epsilon) {
	        if (epsilon < 0.) {
	            throw new IllegalArgumentException("epsilon must be positive");
	        }
	        return -epsilon <= x && x <= epsilon
	                && -epsilon <= y && y <= epsilon
	                && -epsilon <= z && z <= epsilon;
	    }
	        /**
	         * Pack the three coorindates into a new double array
	         * <code>this</code> vector is not modified.
	         * @return a array set with x, y and z
	         */
	  public final double[] toArray() {
	    return new double[]{x,y,z};
	  }

	        /**
	         * Returns a string representation of this vector.  The string
	         * representation consists of the three dimentions in the order x, y, z,
	         * enclosed in square brackets (<tt>"[]"</tt>). Adjacent elements are
	         * separated by the characters <tt>", "</tt> (comma and space).
	         * Elements are converted to strings as by {@link Double#toString(double)}.
	         *
	         * @return a string representation of this vector
	         */
	  @Override
	  public final String toString() {
	    return  "[" + x + ", " +y+ ", " +z + "]";
	  }
	}

