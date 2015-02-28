/**
 * RoundButton
 * @author Joshua Boone
 * 4/27/2014
 * MiraCosta Programming Competition
 * 
 * Holds information for round button objects.
 */
public class RoundButton extends GameElement {

	private double radius;
	private boolean hover;
	private boolean clicked;
	
	/**
	 * Initializes Object.
	 */
	public RoundButton() 
	{
		radius = 0;
	}

	/**
	 * Initializes object with the given variables.
	 * 
	 * @param x			- Object's x position.
	 * @param y			- Object's y position.
	 * @param imgX		- Object's image x position.
	 * @param imgY		- Object's image y position.
	 * @param imgW		- Object's image width.
	 * @param imgH		- Object's image height.
	 * @param radius	- Object's radius.
	 */
	public RoundButton(double x, double y, int imgX, int imgY, int imgW, int imgH, double radius) 
	{
		super(x, y, imgX, imgY, imgW, imgH);
		this.radius = radius;
	}
	
	/**
	 * Copies the given object's information. 
	 * 
	 * @param roundButton	- Object to be copied.
	 */
	public void copy(RoundButton roundButton)
	{
		super.copy(roundButton);
		radius = roundButton.getRadius();
		hover = roundButton.getHover();
		clicked = roundButton.getClicked();
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
	 * @return the hover
	 */
	public boolean getHover() {
		return hover;
	}

	/**
	 * @param hover the hover to set
	 */
	public void setHover(boolean hover) {
		this.hover = hover;
	}

	/**
	 * @return the clicked
	 */
	public boolean getClicked() {
		return clicked;
	}

	/**
	 * @param clicked the clicked to set
	 */
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public String toString()
	{
		return super.toString() + " | Radius: " + radius + " | Hover: " + hover + " | Clicked: " + clicked;
	}

}
