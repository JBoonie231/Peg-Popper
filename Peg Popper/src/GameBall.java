/**
 * GameBall
 * @author Joshua Boone
 * 4/27/2014
 * MiraCosta Programming Competition
 * 
 * Holds information for GameBall objects.
 */
public class GameBall extends GameElement {
	
	private Vector direction;
	private double xVelocity;
	private double yVelocity;
	private double radius;
	private boolean status;
	private static final double ACCELERATION = .004;

	/**
	 * Initializes Object.
	 */
	public GameBall() 
	{
		direction = new Vector();
		xVelocity = 0;
		yVelocity = 0;
		radius = 0;
		
		setImgY(0);
		setImgX(0);
		
		setImgH(13);
		setImgW(13);
		
		radius = 6.5;
		
		status = true;
	}

	/**
	 * Initializes object with the given variables.
	 * 
	 * @param x			- Object's x position.
	 * @param y			- Object's y position.
	 * @param direction	- Object's direction.
	 * @param xVelocity	- Object's x velocity.
	 * @param yVelocity	- Object's y velocity.
	 * @param radius	- Object's radius.
	 */
	public GameBall(double x, double y, Vector direction, double xVelocity, double yVelocity, double radius) 
	{
		super(x, y);
		this.direction = direction;
		this.xVelocity = xVelocity;
		this.yVelocity = yVelocity;
		this.radius = radius;
		
		setImgY(0);
		setImgX(0);
		
		setImgH(13);
		setImgW(13);
		
		radius = 6.5;
	}
	
	/**
	 * Copies the given object's information. 
	 * 
	 * @param gameBall	- Object to be copied.
	 */
	public void copy(GameBall gameBall)
	{
		super.copy(gameBall);
		direction.copy(gameBall.getDirection());
		xVelocity = gameBall.getxVelocity();
		yVelocity = gameBall.getyVelocity();
		radius = gameBall.getRadius();
	}
	
	/**
	 * Calculates the game-ball's velocity.
	 */
	public void calculateVelocity()
	{
		direction.setY(direction.getY() + ACCELERATION);
		if(direction.getY() > 1.0)
			direction.setY(1.0);
		if(direction.getX() > 1.0)
			direction.setX(1.0);
		if(direction.getY() < -1.0)
			direction.setY(-1.0);
		if(direction.getX() < -1.0)
			direction.setX(-1.0);
		xVelocity = direction.getX();
		yVelocity = direction.getY();
	}
	
	/**
	 * Calculates the game-ball's direction slope.
	 * 
	 * @param angle	- Angle to find the slope of.
	 */
	public void calculateSlope(int angle)
	{
		xVelocity = Math.cos((angle*Math.PI)/180.0);
		yVelocity = Math.sin((angle*Math.PI)/180.0);
		direction.setX(xVelocity);
		direction.setY(yVelocity);
	}

	/**
	 * @return the direction
	 */
	public Vector getDirection() {
		return direction;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(Vector direction) {
		this.direction = direction;
	}

	/**
	 * @return the xVelocity
	 */
	public double getxVelocity() {
		return xVelocity;
	}

	/**
	 * @param xVelocity the xVelocity to set
	 */
	public void setxVelocity(double xVelocity) {
		this.xVelocity = xVelocity;
	}

	/**
	 * @return the yVelocity
	 */
	public double getyVelocity() {
		return yVelocity;
	}

	/**
	 * @param yVelocity the yVelocity to set
	 */
	public void setyVelocity(double yVelocity) {
		this.yVelocity = yVelocity;
	}

	/**
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) {
		this.radius = radius;
	}

	/**
	 * @return the status
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	public String toString()
	{
		return super.toString() + " | Direction Vector: " + direction.toString() + " | x Velocity: " + xVelocity + " | y Velocity: " + yVelocity + " | Radius: " + radius; 
	}
}
