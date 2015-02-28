/**
 * Vector
 * @author Joshua Boone
 * 4/27/2014
 * MiraCosta Programming Competition
 * 
 * Holds information for Vector objects.
 */
public class Vector {
	
	private double x;
	private double y;
	
	/**
	 * Initializes Object.
	 */
	public Vector()
	{
		x = 0;
		y = 0;
	}
	
	/**
	 * Initializes object with the given variables.
	 * 
	 * @param x		- Object's x position.
	 * @param y		- Object's y position.
	 */ 
	public Vector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Copies the given object's information. 
	 * 
	 * @param v	- Object to be copied.
	 */
	public void copy(Vector v)
	{
		x = v.getX();
		y = v.getY();
	}
	
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Calculates the magnitude of this vector.
	 * 
	 * @return	- The magnitude of this vector.
	 */
	public double magnitude()
	{
		return Math.sqrt((x*x) + (y*y));
	}
	
	/**
	 * Converts this vector into a unit vector.
	 */
	public void makeUnitVector()
	{
		x = x/magnitude();
		y = y/magnitude();
	}
	
	public String toString()
	{
		return "<" + x + "," + y + ">";
	}
	
	/**
	 * Calculates the magnitude of a vector.
	 * 
	 * @param x	- The x component of a vector.
	 * @param y	- The y component of a vector.
	 * @return	- The magnitude of the given vector.
	 */
	public static double magnitude(double x, double y)
	{
		return Math.sqrt((x*x) + (y*y));
	}
	
	/**
	 * Proforms the DOT Product on two vectors.
	 * 
	 * @param v - A vector.
	 * @param u	- Another vector.
	 * @return 	- The DOT Product of the given vectors.
	 */
	public static double dot(Vector v, Vector u)
	{
		return (v.getX()*u.getX()) + (v.getY()*u.getY());
	}
	
	/**
	 * Performs scalar multiplication on a vector.
	 * 
	 * @param scalar	- A scalar.
	 * @param v			- A vector.
	 * @return			- The vector multiplied by the scalar.
	 */
	public static Vector scalarMult(double scalar, Vector v)
	{
		return new Vector(scalar*v.getX(), scalar*v.getY());
	}
	
	/**
	 * Performs vector subtraction.
	 * 
	 * @param v	- A vector.
	 * @param u	- Another vector.
	 * @return	- The difference of the two given vectors.
	 */
	public static Vector sub(Vector v, Vector u)
	{
		return new Vector(v.getX() - u.getX(), v.getY() - u.getY());
	}
	
}
