/**
 * PegPopper
 * @author Joshua Boone
 * 4/27/2014
 * MiraCosta Programming Competition
 * 
 * This is a Peggle-esque game.
 *
 * -----Objective-----
 * Using a limited number of game balls, the user must hit primary pegs located throughout the screen.
 * Primary pegs are denoted by red pegs.  Once all primary pegs are gone, the user wins the round.
 * The player increases their score with every peg hit.
 *
 * -----Rules-----
 * The player has a limited number of game balls to use each round.  When they run out the round is over.
 * If the player runs out of game balls, but hasn't yet eliminated all primary pegs, the player loses the round.
 *
 * -----Power Ups-----
 * Power ups can be obtained by winning consecutive rounds, and are activated by hitting power pegs.
 * Power pegs are denoted by green pegs.
 * Power ups are activated when hit.
 *
 * The following are possible power ups:
 * Double Ball		- Creates another game ball that plays simultaneously with the ball in play. (Does not extend through player's next turn.)
 * Goal Extender	- Doubles the size of the extra ball goal at the bottom of the screen. (Lasts for three turns.)
 * Ghost Ball		- When a ball reaches the bottom of the screen, it moves to the top of the screen. (Does not extend through player's next turn.)
 * Fire Ball		- The ball's radius increases and does not bounce off pegs. (This power is activated on the player's next turn.)
 *
 * -----Controls-----
 * Mouse	- Used to select buttons, and to aim and fire Game Balls.
 */
import javax.swing.JFrame;

public class PegPopper extends JFrame{
	
	protected double width;
	protected double height;

	/**
	 * Sets up the JFrame and calls Screen.
	 */
	public PegPopper()
	{
		add(new Screen());

		width = 900;
		height = 622;
				
		setSize((int)width,(int)height);
		setResizable(false);
		
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("PegPopper");
        setVisible(true);
	}
	
	/**
	 * Initializes the game.
	 * @param args
	 */
	public static void main(String[] args) {
		new PegPopper();

	}

}
