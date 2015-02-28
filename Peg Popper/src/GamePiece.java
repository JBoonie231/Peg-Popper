/**
 * GamePiece
 * @author Joshua Boone
 * 4/27/2014
 * MiraCosta Programming Competition
 * 
 * Holds information for GamePiece objects.
 */
public class GamePiece extends GameElement {

	private int type;
	private boolean status;
	
	/**
	 * Initializes Object.
	 */
	public GamePiece() 
	{
		type = 0;
		status = true;
	}

	/**
	 * Initializes object with the given variables.
	 * 
	 * @param x		- Object's x position.
	 * @param y		- Object's y position.
	 * @param type	- Object's type.
	 */
	public GamePiece(double x, double y, int type) 
	{
		super(x, y);
		this.type = type;
		status = false;
	}
	
	/**
	 * Copies the given object's information. 
	 * 
	 * @param gamePiece	- Object to be copied.
	 */
	public void copy(GamePiece gamePiece)
	{
		super.copy(gamePiece);
		type = gamePiece.getType();
		status = gamePiece.getStatus();
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
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
		return super.toString() + " | Type: " + type + " | Status: " + status;
	}

}
