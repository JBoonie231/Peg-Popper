/**
 * Peg
 * @author Joshua Boone
 * 4/27/2014
 * MiraCosta Programming Competition
 * 
 * Holds information for peg objects.
 */
public class Peg extends GamePiece {
	
	private double radius;
	private int lifespan;

	/**
	 * Initializes Object.
	 */
	public Peg() 
	{
		radius = 0;
	}

	/**
	 * Initializes object with the given variables.
	 * 
	 * @param x			- Object's x position.
	 * @param y			- Object's y position.
	 * @param type		- The type of peg. 0-normal, 1-primary, 2-bonus, 3-power
	 * @param radius	- Object's radius.
	 */
	public Peg(double x, double y, int type, double radius) 
	{
		super(x, y, type);
		this.radius = radius;
		
		setImgY(13);
		switch(type)
		{
		case 0:
			setImgX(126);
			break;
		case 1:
			setImgX(84);
			break;
		case 2:
			setImgX(42);
			break;
		case 3:
			setImgX(0);
			break;
		default:
			break;
		}
		
		setImgH(21);
		setImgW(21);
		
		radius = 10.5;
	}
	
	/**
	 * Copies the given object's information. 
	 * 
	 * @param peg	- Object to be copied.
	 */
	public void copy(Peg peg)
	{
		super.copy(peg);
		radius = peg.getRadius();
	}

	/**
	 * @return the radius
	 */
	public double getRadius() 
	{
		return radius;
	}

	/**
	 * @param radius the radius to set
	 */
	public void setRadius(double radius) 
	{
		this.radius = radius;
	}
	
	/**
	 * @return the lifespan
	 */
	public int getLifespan() {
		return lifespan;
	}

	/**
	 * @param lifespan the lifespan to set
	 */
	public void setLifespan(int lifespan) {
		this.lifespan = lifespan;
	}

	public void setType(int type)
	{
		super.setType(type);
		
		switch(type)
		{
		case 0:
			setImgX(126);
			break;
		case 1:
			setImgX(84);
			break;
		case 2:
			setImgX(42);
			break;
		case 3:
			setImgX(0);
			break;
		default:
			break;
		}
	}
	
	public String toString()
	{
		return super.toString() + " | Radius: " + radius;
	}

}
