/**
 * GameElement
 * @author Joshua Boone
 * 4/27/2014
 * MiraCosta Programming Competition
 * 
 * Holds information for GameElement objects.
 */
public class GameElement {

	private double x;
	private double y;
	private int imgX;
	private int imgY;
	private int imgW;
	private int imgH;
	
	/**
	 * Initializes Object.
	 */
	public GameElement()
	{
		x = 0;
		y = 0;
	}
	
	/**
	 * Initializes object with the given variables.
	 * 
	 * @param x			- Object's x position.
	 * @param y			- Object's y position.
	 */
	public GameElement(double x, double y)
	{
		this.x = x;
		this.y = y;
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
	public GameElement(double x, double y, int imgX, int imgY, int imgW, int imgH)
	{
		this.x = x;
		this.y = y;
		this.imgX = imgX;
		this.imgY = imgY;
		this.imgW = imgW;
		this.imgH = imgH;
	}
	
	/**
	 * Copies the given object's information. 
	 * 
	 * @param gameElement	- Object to be copied.
	 */
	public void copy(GameElement gameElement)
	{
		x = gameElement.getX();
		y = gameElement.getY();
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
	 * @return the imgX
	 */
	public int getImgX() {
		return imgX;
	}

	/**
	 * @param imgX the imgX to set
	 */
	public void setImgX(int imgX) {
		this.imgX = imgX;
	}

	/**
	 * @return the imgY
	 */
	public int getImgY() {
		return imgY;
	}

	/**
	 * @param imgY the imgY to set
	 */
	public void setImgY(int imgY) {
		this.imgY = imgY;
	}

	/**
	 * @return the imgW
	 */
	public int getImgW() {
		return imgW;
	}

	/**
	 * @param imgW the imgW to set
	 */
	public void setImgW(int imgW) {
		this.imgW = imgW;
	}

	/**
	 * @return the imgH
	 */
	public int getImgH() {
		return imgH;
	}

	/**
	 * @param imgH the imgH to set
	 */
	public void setImgH(int imgH) {
		this.imgH = imgH;
	}

	public String toString()
	{
		return "Position: (" + x + "," + y + ") | Img Position: (" + imgX + "," + imgY + ") | Img width: " + imgW + " | Img Height: " + imgH;
	}
}
