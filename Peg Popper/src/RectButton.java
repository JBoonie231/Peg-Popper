/**
 * RectButton
 * @author Joshua Boone
 * 4/27/2014
 * MiraCosta Programming Competition
 * 
 * Holds information for rectangle button objects.
 */
public class RectButton extends GameElement {

	private boolean hover;
	private boolean clicked;
	
	/**
	 * Initializes Object.
	 */
	public RectButton() 
	{
		super();
	}

	/**
	 * Initializes object with the given variables.
	 * 
	 * @param x		- Object's x position.
	 * @param y		- Object's y position.
	 * @param imgX	- Object's image x position.
	 * @param imgY	- Object's image y position.
	 * @param imgW	- Object's image width.
	 * @param imgH	- Object's image height.
	 */
	public RectButton(double x, double y, int imgX, int imgY, int imgW, int imgH) 
	{
		super(x, y, imgX, imgY, imgW, imgH);
	}
	
	/**
	 * Copies the given object's information. 
	 * 
	 * @param rectButton	- Object to be copied.
	 */
	public void copy(RectButton rectButton)
	{
		super.copy(rectButton);
		hover = rectButton.getHover();
		clicked = rectButton.getClicked();
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
		return super.toString() + " | Hover: " + hover + " | Clicked: " + clicked;
	}

}
