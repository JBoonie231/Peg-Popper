/**
 * Screen
 * @author Joshua Boone
 * 4/27/2014
 * MiraCosta Programming Competition
 * 
 * Initializes all class variables and starts the thread.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable{
	
	private Thread animator;
	private Image openingScreen, border, expositionScreen, background1, background2, background3, background4, background5, elements, endScreen;
	private List<Peg> pegs;
	private List<GameBall> gameBalls;
	private List<RoundButton> buttons;
	private List<RectButton> menuButtons;
	private List<Integer> highScores;
	private List<String> highScoreNames;
	private GameBall tempGameBall;
	private RectButton continueButton;
	private GameElement goal;
	private Font small, medium, large;
	private int numOfBallsInPlay;
	private int numOfBonusPegs;
	private int numOfPrimaryPegs;
	private int numOfPowerPegs;
	private int level;
	private int power = 0;
	private int powerInfo = 0;
	private int powerDuration = 0;
	private int transition = 0;
	private int angle = 90;
	private int score;
	private int count;
	private double goalAcceleration;
	private long beforeTime;
	private boolean menu;
	private boolean exposition;
	private boolean title;
	private boolean end;
	private boolean inPlay;
	private boolean instructions;
	private boolean powerUps;
	private boolean highScoreScreen;
	private boolean power1Unlocked;
	private boolean power2Unlocked;
	private boolean power3Unlocked;
	private boolean power4Unlocked;
	private boolean newRound;
	private boolean firstTime = true;
	private boolean up1,up2,down1,down2,left1,left2,right1,right2,a,b;
	private String playerName, continueLabel, returnLabel, startLabel, instructionsLabel, powerUpsLabel, highScoresLabel, quitLabel, lockedLabel, instructionsExposition, rulesExposition, powerUpsExposition, scoreBonusExposition, power0Exposition, power1Exposition, power2Exposition, power3Exposition, power4Exposition, powerLockedExposition;
	
	private final int DELAY = 2;
	
	/**
	 * Initializes key variables, listeners and starts the thread.
	 */
	public Screen()
	{
		Mouse mouse = new Mouse();
		
		addKeyListener(new Controller());
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
        setFocusable(true);
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        
        ImageIcon ii = new ImageIcon(this.getClass().getResource("Images/Opening Screen.png"));
        openingScreen = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("Images/Border Screen.png"));
        border = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("Images/Exposition Screen.png"));
        expositionScreen = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("Images/Background1.png"));
        background1 = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("Images/Background2.png"));
        background2 = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("Images/Background3.png"));
        background3 = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("Images/Background4.png"));
        background4 = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("Images/Background5.png"));
        background5 = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("Images/Elements.png"));
        elements = ii.getImage();
        ii = new ImageIcon(this.getClass().getResource("Images/End Screen.png"));
        endScreen = ii.getImage();
        
        continueButton = new RectButton(292,432,0,131,315,66);// Continue Button
        buttons = new ArrayList<RoundButton>();
        menuButtons = new ArrayList<RectButton>();
        buttons.add(new RoundButton(9,522,0,263,62,62,31));// menu button
        buttons.add(new RoundButton(825,73,0,325,71,71,35));// Power1 Button
        buttons.add(new RoundButton(825,168,71,325,71,71,35));// Power2 Button
        buttons.add(new RoundButton(825,260,142,325,71,71,35));// Power3 Button
        buttons.add(new RoundButton(825,352,213,325,71,71,35));// Power4 Button
        buttons.add(new RoundButton(830,522,124,263,62,62,31));// restart button
        menuButtons.add(new RectButton(292,128,0,131,315,66));// Instructions Button
        menuButtons.add(new RectButton(292,204,0,131,315,66));// Power Ups Button
        menuButtons.add(new RectButton(292,280,0,131,315,66));// High Scores Button
        menuButtons.add(new RectButton(292,356,0,131,315,66));// Quit Button
        
        continueLabel = "Continue";
        startLabel = "Start";
        returnLabel = "Return";
        instructionsLabel = "Instructions";
        powerUpsLabel = "Power Ups";
        highScoresLabel = "High Scores";
        quitLabel = "Quit";
        lockedLabel = "Locked";
        instructionsExposition = "Using a limited number of game balls, the player must hit primary pegs located throughout the screen. Once all primary pegs are gone, the user wins the round.";
        rulesExposition = "The player has a limited number of game balls to use each round.  When they run out the round is over. If the player runs out of game balls, but hasn't yet eliminated all primary pegs, the player loses the round. A goal occilates at the bottom of the screen. If a game ball lands in the goal, the player is awarded a free ball!";
        powerUpsExposition = "Power ups can be obtained by winning consecutive rounds, and are activated by hitting power pegs. Power ups are activated when hit.";
        scoreBonusExposition = "A score bonus of 1000 points can be obtained by hitting score bonus pegs. The score bonus peg changes position each round";
        power0Exposition = "Hover over one of the Power-Up icons to see a description.";
        power1Exposition = "Multi-Ball duplicates the game ball that struck the power peg, potentially doubling the player's chances!";
        power2Exposition = "Goal Extension doubles the size of the free ball goal for three rounds, doubling the player's chances of winning a free ball!";
        power3Exposition = "Ghost Ball brings the player's game ball back to the top of the screen when it reaches the bottom without entering the goal. The effect lasts until the game ball passes through the bottom at least once.";
        power4Exposition = "Fire-Ball increases the size of the game ball and is not stopped by any peg, it just barrels right on through!";
        powerLockedExposition = "This power must be unlocked to view its description";
        
        goal = new GameElement(80, 576, 0, 107, 157, 24);
        
        pegs = new ArrayList<Peg>();       
        
        gameBalls = new ArrayList<GameBall>();
        gameBalls.add(new GameBall());
        
        highScores = new ArrayList<Integer>();
        highScoreNames = new ArrayList<String>();
        
        //Load High Scores from file
        try 
        {
			loadScores();
		} 
        catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
        
        small = new Font("Helvetica", Font.BOLD, 15);
        medium = new Font("Helvetica", Font.BOLD, 20);
        large = new Font("Helvetica", Font.BOLD, 40);
        
        title = true;
        
        level = 1;
        newRound = true;
        
        animator = new Thread(this);
        
        animator.start();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * Runs the Program
	 */
	public void run()
	{
		beforeTime = System.currentTimeMillis();
		
		while(true)
		{
			//Title Screen
			if(title)
			{
				if(transition != 0)
					transition++;
				if(transition >= 100)
				{
					transition = 0;
					title = false;
					exposition = true;
					instructions = true;
					numOfPrimaryPegs = 1;
					
					//Retrieve player name from the user.
					playerName = JOptionPane.showInputDialog("Please enter your name:");
					JOptionPane.showMessageDialog(null, "Hello " + playerName + ", here is some information about the game. \nWhen you're ready, click 'Continue' then click \nwithin the game screen to begin.");
				}
				
				repaint();
			}
			
			//Display Information
			else if(exposition)
			{
				if(transition != 0)
					transition++;
				if(transition >= 100)
				{
					transition = 0;
					exposition = false;
				}
				
				repaint();
			}
	        
			//Display Menu
			else if(menu)
			{
				if(transition != 0)
					transition++;
				if(transition >= 100)
				{
					transition = 0;
					menu = false;
				}
				
				repaint();
			}
			
			//Exit Game
			else if(end)
			{
				if(transition != 0)
					transition++;
				if(transition >= 1000)
				{
					System.exit(0);
				}
				
				repaint();
			}
			
			//In Game
			else
			{
				//Check win/lose
				if(numOfPrimaryPegs == 0)
				{
					if(level == 1)
					{
						JOptionPane.showMessageDialog(null, "Congratulations! You beat level 1!\nHit 'OK' to continue to level 2.");
						if(!power1Unlocked)
						{
							power1Unlocked = true;
							JOptionPane.showMessageDialog(null, "You unlocked Multi-Ball!\nActivate it by clicking its icon on the right.");
						}
						level++;
					}
					else if(level == 2)
					{
						JOptionPane.showMessageDialog(null, "Congratulations! You beat level 2!\nHit 'OK' to continue to level 3.");
						if(!power4Unlocked)
						{
							power4Unlocked = true;
							JOptionPane.showMessageDialog(null, "You unlocked Fireball!\nActivate it by clicking its icon on the right.");
						}
						level++;
					}
					else if(level == 3)
					{
						JOptionPane.showMessageDialog(null, "Congratulations! You beat level 3!\nHit 'OK' to continue to level 4.");
						if(!power3Unlocked)
						{
							power3Unlocked = true;
							JOptionPane.showMessageDialog(null, "You unlocked Ghost Ball!\nActivate it by clicking its icon on the right.");
						}
						level++;
					}
					else if(level == 4)
					{
						JOptionPane.showMessageDialog(null, "Congratulations! You beat level 4!\nHit 'OK' to move on to the FINAL level.");
						if(!power2Unlocked)
						{
							power2Unlocked = true;
							JOptionPane.showMessageDialog(null, "You unlocked Goal Extender!\nActivate it by clicking its icon on the right.");
						}
						level = 0;
					}
					else if(level == 0 && firstTime)
					{
						firstTime = false;
						JOptionPane.showMessageDialog(null, "Amazing! You beat every level!\nBut can you beat the High Score?!\nHit 'OK' for continuous random level generation.");
					}
					
					score += 1000*numOfBallsInPlay;
					
					if(score > highScores.get(0))
						JOptionPane.showMessageDialog(null, "You just set the High Score!\nYou are literally the best at this game!\nHow high can you go? Hit 'OK' to find out.");
					
					//Check level score placement
					if(score > highScores.get(9))
					{
						for(int i = 0; i < highScores.size(); i++)
						{
							if(score > highScores.get(i))
							{
								highScores.add(i, score);
								highScoreNames.add(i, playerName);
								highScores.remove(10);
								highScoreNames.remove(10);
								try 
								{
									saveScores();
								} 
								catch (FileNotFoundException e) 
								{
									e.printStackTrace();
								}
								break;
							}
						}
					}
					
					newRound = true;
				}
				else if(numOfBallsInPlay == -1)
				{
					score = 0;
					level = 1;
					newRound = true;
					JOptionPane.showMessageDialog(null, "Aww, you lost...\nGuess it's back to level 1.");
				}
				
				if(newRound)
				{
					initiateRound();
					newRound = false;
				}
				
				if(inPlay)
				{
					moveBall();
					checkCollision();
				}
				
				else if(power == 4 && powerDuration > 0)
				{
					gameBalls.get(0).setRadius(21);
					gameBalls.get(0).setImgX(90);
					gameBalls.get(0).setImgY(55);
					gameBalls.get(0).setImgW(42);
					gameBalls.get(0).setImgH(42);
				}
				
				if(power == 2 && powerDuration > 0)
				{
					if(goal.getX() < 294)
						goalAcceleration += .0011;
					else
						goalAcceleration -= .0011;
				}
				else
				{
					if(goal.getX() < 372)
						goalAcceleration += .0011;
					else
						goalAcceleration -= .0011;
				}
				
				goal.setX(goal.getX() + goalAcceleration);
				
				repaint();
			}
			
			buttonCheck();
	        
	        sleep();
		}
        
	}// end of run

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 * 
	 * Draws the game to the screen.
	 */
	public void paint(Graphics g) 
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        
        //Title Screen
        if(title)
        {
        	g2d.drawImage(openingScreen, 0, 0, getWidth(), getHeight(), this);
        	
        	//Start Button
        	if(!continueButton.getHover())
    		{
    			g2d.drawImage(elements, 
                        (int)continueButton.getX(), (int)continueButton.getY(), 
                        (int)(continueButton.getX() + continueButton.getImgW()), (int)(continueButton.getY() + continueButton.getImgH()),
                        continueButton.getImgX(), continueButton.getImgY(), 
                        continueButton.getImgX() + continueButton.getImgW(), continueButton.getImgY() + continueButton.getImgH(), 
                        this); 
    		}
    		else
    		{
    			g2d.drawImage(elements, 
    					(int)continueButton.getX(), (int)continueButton.getY(), 
                        (int)(continueButton.getX() + continueButton.getImgW()), (int)(continueButton.getY() + continueButton.getImgH()),
                        continueButton.getImgX(), continueButton.getImgY() + continueButton.getImgH(), 
                        continueButton.getImgX() + continueButton.getImgW(), continueButton.getImgY() + 2*continueButton.getImgH(), 
                        this); 
    		}
        	
        	g2d.setFont(large);
        	g2d.drawString(startLabel, (int)continueButton.getX() + 110, (int)continueButton.getY() + 45);
        }
        
        //Information Screen
        else if(exposition)
        {
        	g2d.drawImage(border, 0, 0, getWidth(), getHeight(), this);
        	g2d.drawImage(expositionScreen, 80, 50, 741, 541, this);
        	
        	// Menu, Restart Buttons and Power UPs 
        	for(RoundButton button : buttons)
        	{
        		
        		if(!button.getHover())
        		{
        			g2d.drawImage(elements, 
                            (int)button.getX(), (int)button.getY(), 
                            (int)(button.getX() + button.getImgW()), (int)(button.getY() + button.getImgH()),
                            button.getImgX(), button.getImgY(), 
                            button.getImgX() + button.getImgW(), button.getImgY() + button.getImgH(), 
                            this); 
        		}
        		else if(button.getRadius() != 35)
        		{
        			g2d.drawImage(elements, 
        					(int)button.getX(), (int)button.getY(), 
                            (int)(button.getX() + button.getImgW()), (int)(button.getY() + button.getImgH()),
                            button.getImgX() + button.getImgW(), button.getImgY(), 
                            button.getImgX() + 2*button.getImgW(), button.getImgY() + button.getImgH(), 
                            this); 
        		}
        	}
        	
        	//Instructions Description
        	if(instructions)
        	{
        		g2d.setFont(small);
            	g2d.drawString(instructionsExposition.substring(0, 79), 150, 140);
            	g2d.drawString(instructionsExposition.substring(79), 150, 160);

            	g2d.drawImage(elements, 
                        740, 135, 
                        761, 156,
                        84, 13, 
                        105, 34, 
                        this); 
            	
            	g2d.drawString(rulesExposition.substring(0, 79), 200, 200);
            	g2d.drawString(rulesExposition.substring(80, 156), 200, 220);
            	g2d.drawString(rulesExposition.substring(156, 213), 200, 240);
            	
            	g2d.drawImage(elements, 
                        150, 203, 
                        177, 230,
                        258, 55, 
                        285, 82, 
                        this); 

            	g2d.drawString(rulesExposition.substring(213, 293), 150, 280);
            	g2d.drawString(rulesExposition.substring(293), 150, 300);
            	
            	g2d.drawImage(elements, 
                        700, 285, 
                        700 + goal.getImgW()/2, 285 + goal.getImgH()/2,
                        goal.getImgX(), goal.getImgY(), 
                        goal.getImgX() + goal.getImgW(), goal.getImgY() + goal.getImgH(), 
                        this);
            	
            	g2d.drawString(powerUpsExposition.substring(0, 77), 200, 340);
            	g2d.drawString(powerUpsExposition.substring(78), 200, 360);
            	
            	g2d.drawImage(elements, 
                        150, 335, 
                        171, 356,
                        0, 13, 
                        21, 34, 
                        this); 
            	
            	g2d.drawString(scoreBonusExposition.substring(0, 77), 150, 400);
            	g2d.drawString(scoreBonusExposition.substring(78), 150, 420);

            	g2d.drawImage(elements, 
                        740, 395, 
                        761, 416,
                        42, 13, 
                        63, 34, 
                        this); 
        	}
        	
        	//Power Ups Description
        	else if(powerUps)
        	{
        		g2d.setFont(small);

        		if(power1Unlocked)
        			g2d.drawString(power1Exposition.substring(0, 10), 730, 115);
        		else
        			g2d.drawString(lockedLabel, 740, 115);
        		
        		if(power2Unlocked)
        			g2d.drawString(power2Exposition.substring(0, 14), 700, 207);
        		else
        			g2d.drawString(lockedLabel, 740, 207);
        		
        		if(power3Unlocked)
        			g2d.drawString(power3Exposition.substring(0, 10), 725, 299);
        		else
        			g2d.drawString(lockedLabel, 740, 299);
        		
        		if(power4Unlocked)
        			g2d.drawString(power4Exposition.substring(0, 9), 730, 391);
        		else
        			g2d.drawString(lockedLabel, 740, 391);
        		
        		//Descriptions
        		if(powerInfo == 0)
        		{
        			g2d.drawString(power0Exposition, 250, 250);
        		}
        		else if(powerInfo == 1)
        		{
        			if(power1Unlocked)
        			{
        				g2d.drawImage(elements, 
                                430, 150, 
                                468, 185,
                                0, 396, 
                                38, 431, 
                                this); 
        				
        				g2d.setFont(medium);
        				g2d.drawString(power1Exposition.substring(0, 10), 400, 210);
        				
        				g2d.setFont(small);
            			g2d.drawString(power1Exposition.substring(0, 57), 260, 250);
            			g2d.drawString(power1Exposition.substring(58), 290, 270);
        			}
            		else
            			g2d.drawString(powerLockedExposition, 270, 250);
        		}
        		else if(powerInfo == 2)
        		{
        			if(power2Unlocked)
        			{
        				g2d.drawImage(elements, 
                                410, 150,
                                410 + goal.getImgW()/2, 150 + goal.getImgH()/2,
                                goal.getImgX(), goal.getImgY(), 
                                goal.getImgX() + goal.getImgW(), goal.getImgY() + goal.getImgH(), 
                                this);
        				
        				g2d.setFont(medium);
        				g2d.drawString(power2Exposition.substring(0, 14), 380, 210);
        				
        				g2d.setFont(small);
            			g2d.drawString(power2Exposition.substring(0, 57), 260, 250);
            			g2d.drawString(power2Exposition.substring(58), 230, 270);
        			}
            		else
            			g2d.drawString(powerLockedExposition, 270, 250);
        		}
        		else if(powerInfo == 3)
        		{
        			if(power3Unlocked)
        			{
        				g2d.drawImage(elements, 
                                425, 130, 
                                476, 184,
                                38, 396, 
                                89, 450, 
                                this); 
        				
        				g2d.setFont(medium);
        				g2d.drawString(power3Exposition.substring(0, 10), 400, 210);
        				
        				g2d.setFont(small);
            			g2d.drawString(power3Exposition.substring(0, 70), 220, 250);
            			g2d.drawString(power3Exposition.substring(71, 141), 220, 270);
            			g2d.drawString(power3Exposition.substring(142), 240, 290);
        			}
            		else
            			g2d.drawString(powerLockedExposition, 270, 250);
        		}
        		else if(powerInfo == 4)
        		{
        			if(power4Unlocked)
        			{
        				g2d.drawImage(elements, 
                                429, 140, 
                                471, 182,
                                90, 55, 
                                132, 97, 
                                this); 
        				
        				g2d.setFont(medium);
        				g2d.drawString(power4Exposition.substring(0, 9), 410, 210);
        				
        				g2d.setFont(small);
            			g2d.drawString(power4Exposition.substring(0, 56), 260, 250);
            			g2d.drawString(power4Exposition.substring(57), 270, 270);
        			}
            		else
            			g2d.drawString(powerLockedExposition, 270, 250);
        		}
        		
        		//Power Ups
            	if(power1Unlocked)
            	{
            		g2d.drawImage(elements, 
                            840, 93, 
                            878, 128,
                            0, 396, 
                            38, 431, 
                            this); 
            	}
            	else
            	{
            		g2d.drawImage(elements, 
                            839, 79, 
                            884, 131,
                            0, 55, 
                            45, 107, 
                            this); 
            	}
            	
            	if(power2Unlocked)
            	{
            		g2d.drawImage(elements, 
                            835, 200,
                            835 + goal.getImgW()/3, 200 + goal.getImgH()/3,
                            goal.getImgX(), goal.getImgY(), 
                            goal.getImgX() + goal.getImgW(), goal.getImgY() + goal.getImgH(), 
                            this); 
            	}
            	else
            	{
            		g2d.drawImage(elements, 
            				839, 174, 
                            884, 226,
                            0, 55, 
                            45, 107, 
                            this); 
            	}
            	
            	if(power3Unlocked)
            	{
            		g2d.drawImage(elements, 
                            835, 265, 
                            886, 319,
                            38, 396, 
                            89, 450, 
                            this); 
            	}
            	else
            	{
            		g2d.drawImage(elements, 
                            839, 266, 
                            884, 318,
                            0, 55, 
                            45, 107, 
                            this); 
            	}
            	
            	if(power4Unlocked)
            	{
            		g2d.drawImage(elements, 
                            839, 366, 
                            881, 408,
                            90, 55, 
                            132, 97, 
                            this); 
            	}
            	else
            	{
            		g2d.drawImage(elements, 
                            839, 358, 
                            884, 410,
                            0, 55, 
                            45, 107, 
                            this); 
            	}
        	}
        	
        	//High Scores
        	else if(highScoreScreen)
        	{
        		g2d.setFont(large);
				g2d.drawString(highScoresLabel, 330, 170);
				
				g2d.setFont(medium);
				for(int i = 0; i < 10; i++)
				{
					g2d.drawString(highScoreNames.get(i), 430 - highScoreNames.get(i).length()*10, 210 + i*20);
					g2d.drawString(String.valueOf(highScores.get(i)), 470, 210 + i*20);
				}
				
				
        	}
        	
        	//Continue Button
        	if(!continueButton.getHover())
    		{
    			g2d.drawImage(elements, 
                        (int)continueButton.getX(), (int)continueButton.getY(), 
                        (int)(continueButton.getX() + continueButton.getImgW()), (int)(continueButton.getY() + continueButton.getImgH()),
                        continueButton.getImgX(), continueButton.getImgY(), 
                        continueButton.getImgX() + continueButton.getImgW(), continueButton.getImgY() + continueButton.getImgH(), 
                        this); 
    		}
    		else
    		{
    			g2d.drawImage(elements, 
    					(int)continueButton.getX(), (int)continueButton.getY(), 
                        (int)(continueButton.getX() + continueButton.getImgW()), (int)(continueButton.getY() + continueButton.getImgH()),
                        continueButton.getImgX(), continueButton.getImgY() + continueButton.getImgH(), 
                        continueButton.getImgX() + continueButton.getImgW(), continueButton.getImgY() + 2*continueButton.getImgH(), 
                        this); 
    		}
        	
        	g2d.setFont(large);
        	g2d.drawString(continueLabel, (int)continueButton.getX() + 80, (int)continueButton.getY() + 45);
        }
        
        //Menu Screen
        else if(menu)
        {   
        	g2d.drawImage(border, 0, 0, getWidth(), getHeight(), this);
        	g2d.drawImage(expositionScreen, 80, 50, 741, 541, this);
        	
        	g2d.setFont(large);
        	
        	// Menu, Restart Buttons and Power UPs 
        	for(RoundButton button : buttons)
        	{
        		
        		if(!button.getHover())
        		{
        			g2d.drawImage(elements, 
                            (int)button.getX(), (int)button.getY(), 
                            (int)(button.getX() + button.getImgW()), (int)(button.getY() + button.getImgH()),
                            button.getImgX(), button.getImgY(), 
                            button.getImgX() + button.getImgW(), button.getImgY() + button.getImgH(), 
                            this); 
        		}
        		else if(button.getRadius() != 35)
        		{
        			g2d.drawImage(elements, 
        					(int)button.getX(), (int)button.getY(), 
                            (int)(button.getX() + button.getImgW()), (int)(button.getY() + button.getImgH()),
                            button.getImgX() + button.getImgW(), button.getImgY(), 
                            button.getImgX() + 2*button.getImgW(), button.getImgY() + button.getImgH(), 
                            this); 
        		}
        	}
        	
        	count = 0;
        	// Menu Buttons
        	for(RectButton button : menuButtons)
        	{
        		
        		if(!button.getHover())
        		{
        			g2d.drawImage(elements, 
                            (int)button.getX(), (int)button.getY(), 
                            (int)(button.getX() + button.getImgW()), (int)(button.getY() + button.getImgH()),
                            button.getImgX(), button.getImgY(), 
                            button.getImgX() + button.getImgW(), button.getImgY() + button.getImgH(), 
                            this); 
        		}
        		else
        		{
        			g2d.drawImage(elements, 
        					(int)button.getX(), (int)button.getY(), 
                            (int)(button.getX() + button.getImgW()), (int)(button.getY() + button.getImgH()),
                            button.getImgX(), button.getImgY() + button.getImgH(), 
                            button.getImgX() + button.getImgW(), button.getImgY() + 2*button.getImgH(), 
                            this); 
        		}
            	            	
            	if(count == 0)
            		g2d.drawString(instructionsLabel, (int)button.getX() + 50, (int)button.getY() + 45);
            	
            	else if(count == 1)
            		g2d.drawString(powerUpsLabel, (int)button.getX() + 60, (int)button.getY() + 45);
            	
            	else if(count == 2)
            		g2d.drawString(highScoresLabel, (int)button.getX() + 50, (int)button.getY() + 45);
            	
            	else if(count == 3)
            		g2d.drawString(quitLabel, (int)button.getX() + 115, (int)button.getY() + 45);
            	
            	count++;
        	}
        	
        	// Return Button
        	if(!continueButton.getHover())
    		{
    			g2d.drawImage(elements, 
                        (int)continueButton.getX(), (int)continueButton.getY(), 
                        (int)(continueButton.getX() + continueButton.getImgW()), (int)(continueButton.getY() + continueButton.getImgH()),
                        continueButton.getImgX(), continueButton.getImgY(), 
                        continueButton.getImgX() + continueButton.getImgW(), continueButton.getImgY() + continueButton.getImgH(), 
                        this); 
    		}
    		else
    		{
    			g2d.drawImage(elements, 
    					(int)continueButton.getX(), (int)continueButton.getY(), 
                        (int)(continueButton.getX() + continueButton.getImgW()), (int)(continueButton.getY() + continueButton.getImgH()),
                        continueButton.getImgX(), continueButton.getImgY() + continueButton.getImgH(), 
                        continueButton.getImgX() + continueButton.getImgW(), continueButton.getImgY() + 2*continueButton.getImgH(), 
                        this); 
    		}
        	
        	g2d.drawString(returnLabel, (int)continueButton.getX() + 90, (int)continueButton.getY() + 45);
        }
        
        //End Screen
        else if(end)
        {
        	g2d.drawImage(endScreen, 0, 0, 900, 600, this);
        }
        
        //Game Screen
        else
        {	
        	//Draw background
        	if(level == 1)
        		g2d.drawImage(background1, 80, 50, 741, 541, this);
        	
        	else if(level == 2)
        		g2d.drawImage(background2, 80, 50, 741, 541, this);
        	
        	else if(level == 3)
        		g2d.drawImage(background3, 80, 50, 741, 541, this);
        	
        	else if(level == 4)
        		g2d.drawImage(background4, 80, 50, 741, 541, this);
        	
        	else if(level == 0)
        		g2d.drawImage(background5, 80, 50, 741, 541, this);
        	
        	//Draw pegs
        	for(Peg peg : pegs)
        	{
        		if(peg.getStatus())
        		{
        			g2d.drawImage(elements, 
                            (int)(peg.getX() - peg.getRadius()), (int)(peg.getY() - peg.getRadius()), 
                            (int)(peg.getX() + peg.getRadius()), (int)(peg.getY() + peg.getRadius()),
                            peg.getImgX() + 21, peg.getImgY(), 
                            peg.getImgX() + 21 + peg.getImgW(), peg.getImgY() + peg.getImgH(), 
                            this); 
        		}
        		else
        		{
        			g2d.drawImage(elements, 
                            (int)(peg.getX() - peg.getRadius()), (int)(peg.getY() - peg.getRadius()), 
                            (int)(peg.getX() + peg.getRadius()), (int)(peg.getY() + peg.getRadius()),
                            peg.getImgX(), peg.getImgY(), 
                            peg.getImgX() + peg.getImgW(), peg.getImgY() + peg.getImgH(), 
                            this);
        		}
        	}
        	
        	//Draw goal
        	if(power == 2 && powerDuration > 0)
        	{
	        	g2d.drawImage(elements, 
	                    (int)goal.getX(), (int)goal.getY(), 
	                    (int)(goal.getX() + goal.getImgW()*2), (int)(goal.getY() + goal.getImgH()),
	                    goal.getImgX(), goal.getImgY(), 
	                    goal.getImgX() + goal.getImgW(), goal.getImgY() + goal.getImgH(), 
	                    this);
        	}
        	else
        	{
        		g2d.drawImage(elements, 
	                    (int)goal.getX(), (int)goal.getY(), 
	                    (int)(goal.getX() + goal.getImgW()), (int)(goal.getY() + goal.getImgH()),
	                    goal.getImgX(), goal.getImgY(), 
	                    goal.getImgX() + goal.getImgW(), goal.getImgY() + goal.getImgH(), 
	                    this);
        	}
        	
        	//Draw game balls
        	if(inPlay)
        	{
	        	for(GameBall ball : gameBalls)
	        	{
	        		g2d.drawImage(elements, 
	                        (int)(ball.getX() - ball.getRadius()), (int)(ball.getY() - ball.getRadius()), 
	                        (int)(ball.getX() + ball.getRadius()), (int)(ball.getY() + ball.getRadius()),
	                        ball.getImgX(), ball.getImgY(), 
	                        ball.getImgX() + ball.getImgW(), ball.getImgY() + ball.getImgH(), 
	                        this);
	        	}
        	}
        	else
        	{
        		gameBalls.get(0).setX(450 + gameBalls.get(0).getxVelocity()*62);
        		gameBalls.get(0).setY(59 + gameBalls.get(0).getyVelocity()*62);
        		
        		//Draw Chambered Game Ball
        		g2d.drawImage(elements, 
                        (int)(gameBalls.get(0).getX() - gameBalls.get(0).getRadius()), (int)(gameBalls.get(0).getY() - gameBalls.get(0).getRadius()), 
                        (int)(gameBalls.get(0).getX() + gameBalls.get(0).getRadius()), (int)(gameBalls.get(0).getY() + gameBalls.get(0).getRadius()),
                        gameBalls.get(0).getImgX(), gameBalls.get(0).getImgY(), 
                        gameBalls.get(0).getImgX() + gameBalls.get(0).getImgW(), gameBalls.get(0).getImgY() + gameBalls.get(0).getImgH(), 
                        this);
        		
        		//Draw Trajectory
        		double tempX = gameBalls.get(0).getX();
        		double tempY = gameBalls.get(0).getY();
        		double tempYVelocity = gameBalls.get(0).getDirection().getY();
        		double tempXVelocity = gameBalls.get(0).getDirection().getX();
        		for(int i = 0; i < 300; i++)
        		{
        			if(tempYVelocity < 1.0)
        				tempYVelocity += .004;
        			if(tempYVelocity > 1.0)
        				tempYVelocity = 1.0;
        				
        			tempX += tempXVelocity; 
        			tempY += tempYVelocity;
        			
        			if(i%30 == 0 && i != 0)
        			{
        				g2d.drawImage(elements, 
                                (int)(tempX - gameBalls.get(0).getRadius()/2), (int)(tempY - gameBalls.get(0).getRadius()/2), 
                                (int)(tempX + gameBalls.get(0).getRadius()/2), (int)(tempY + gameBalls.get(0).getRadius()/2),
                                gameBalls.get(0).getImgX(), gameBalls.get(0).getImgY(), 
                                gameBalls.get(0).getImgX() + gameBalls.get(0).getImgW(), gameBalls.get(0).getImgY() + gameBalls.get(0).getImgH(), 
                                this);
        			}
        		}
        	}
        	
        	//Draw game border
        	g2d.drawImage(border, 0, 0, 900, 600, this);
        	
        	//Draw goal foreground
        	if(power == 2 && powerDuration > 0)
        	{
        		g2d.drawImage(elements, 
	                    (int)goal.getX(), (int)goal.getY(), 
	                    (int)(goal.getX() + goal.getImgW()*2), (int)(goal.getY() + goal.getImgH()),
	                    goal.getImgX() + 157, goal.getImgY(), 
	                    goal.getImgX() + 157 + goal.getImgW(), goal.getImgY() + goal.getImgH(), 
	                    this);
        	}
        	else
        	{
	        	g2d.drawImage(elements, 
	                    (int)goal.getX(), (int)goal.getY(), 
	                    (int)(goal.getX() + goal.getImgW()), (int)(goal.getY() + goal.getImgH()),
	                    goal.getImgX() + 157, goal.getImgY(), 
	                    goal.getImgX() + 157 + goal.getImgW(), goal.getImgY() + goal.getImgH(), 
	                    this);
        	}
        	
        	//Draw Extra Game Balls
        	for(int i = 0; i < numOfBallsInPlay; i++)
        	{
        		g2d.drawImage(elements, 
                        28, 466 - i*30, 
                        55, 493 - i*30,
                        258, 55, 
                        285, 82, 
                        this); 
        	}
        	
        	g2d.setFont(large);
        	g2d.drawString(String.valueOf(numOfBallsInPlay), 41 - String.valueOf(numOfBallsInPlay).length()*11, 102);
        	
        	// Menu, Restart Buttons and Power UPs 
        	for(RoundButton button : buttons)
        	{
        		
        		if(!button.getHover())
        		{
        			g2d.drawImage(elements, 
                            (int)button.getX(), (int)button.getY(), 
                            (int)(button.getX() + button.getImgW()), (int)(button.getY() + button.getImgH()),
                            button.getImgX(), button.getImgY(), 
                            button.getImgX() + button.getImgW(), button.getImgY() + button.getImgH(), 
                            this); 
        		}
        		else if(button.getRadius() != 35)
        		{
        			g2d.drawImage(elements, 
        					(int)button.getX(), (int)button.getY(), 
                            (int)(button.getX() + button.getImgW()), (int)(button.getY() + button.getImgH()),
                            button.getImgX() + button.getImgW(), button.getImgY(), 
                            button.getImgX() + 2*button.getImgW(), button.getImgY() + button.getImgH(), 
                            this); 
        		}
        	}
        	
        	//Draw Power Ups
        	if(power1Unlocked)
        	{
        		g2d.drawImage(elements, 
                        840, 93, 
                        878, 128,
                        0, 396, 
                        38, 431, 
                        this); 
        	}
        	else
        	{
        		g2d.drawImage(elements, 
                        839, 79, 
                        884, 131,
                        0, 55, 
                        45, 107, 
                        this); 
        	}
        	
        	if(power2Unlocked)
        	{
        		g2d.drawImage(elements, 
                        835, 200,
                        835 + goal.getImgW()/3, 200 + goal.getImgH()/3,
                        goal.getImgX(), goal.getImgY(), 
                        goal.getImgX() + goal.getImgW(), goal.getImgY() + goal.getImgH(), 
                        this); 
        	}
        	else
        	{
        		g2d.drawImage(elements, 
        				839, 174, 
                        884, 226,
                        0, 55, 
                        45, 107, 
                        this); 
        	}
        	
        	if(power3Unlocked)
        	{
        		g2d.drawImage(elements, 
                        835, 265, 
                        886, 319,
                        38, 396, 
                        89, 450, 
                        this); 
        	}
        	else
        	{
        		g2d.drawImage(elements, 
                        839, 266, 
                        884, 318,
                        0, 55, 
                        45, 107, 
                        this); 
        	}
        	
        	if(power4Unlocked)
        	{
        		g2d.drawImage(elements, 
                        839, 366, 
                        881, 408,
                        90, 55, 
                        132, 97, 
                        this); 
        	}
        	else
        	{
        		g2d.drawImage(elements, 
                        839, 358, 
                        884, 410,
                        0, 55, 
                        45, 107, 
                        this); 
        	}
        	
        	if(power == 1)
        	{
        		g2d.drawImage(elements, 
                        430, 43, 
                        468, 78,
                        0, 396, 
                        38, 431, 
                        this);
        	}
        	else if(power == 2)
        	{
        		g2d.drawImage(elements, 
                        410, 53,
                        410 + goal.getImgW()/2, 53 + goal.getImgH()/2,
                        goal.getImgX(), goal.getImgY(), 
                        goal.getImgX() + goal.getImgW(), goal.getImgY() + goal.getImgH(), 
                        this);
        	}
        	else if(power == 3)
        	{
        		g2d.drawImage(elements, 
                        425, 28, 
                        476, 82,
                        38, 396, 
                        89, 450, 
                        this);
        	}
        	else if(power == 4)
        	{
        		g2d.drawImage(elements, 
                        429, 38, 
                        471, 80,
                        90, 55, 
                        132, 97, 
                        this);
        	}
        	
        	//Draw Score
        	g2d.setFont(medium);
        	g2d.drawString("Score: " + String.valueOf(score), 133, 31);
        	g2d.drawString("Top Score: " + String.valueOf(highScores.get(0)), 533, 31);
        }
        
        g.dispose();
    }// end of Paint
	
//-----Listeners-----
	
	/**
	 * Mouse
	 * @author Joshua Boone
	 * 4/27/2014
	 * MiraCosta Programming Competition
	 * 
	 * Listens for Mouse commands and movements.
	 */
	public class Mouse extends MouseAdapter implements MouseMotionListener
    {
        /* (non-Javadoc)
         * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
         * 
         * Listens for mouseEvent.
         */
        public void mousePressed(MouseEvent e)
        {
        	//Title Screen
        	if(title && transition == 0)
            {
        		if(e.getX() > continueButton.getX() && e.getX() < continueButton.getX() + continueButton.getImgW() && 
                   e.getY() > continueButton.getY() && e.getY() < continueButton.getY() + continueButton.getImgH())
                {
             		continueButton.setClicked(true);
             	}
            }
        	
        	//Exposition Screen
            else if(exposition && transition == 0)
            {
            	if(e.getX() > continueButton.getX() && e.getX() < continueButton.getX() + continueButton.getImgW() && 
            	   e.getY() > continueButton.getY() && e.getY() < continueButton.getY() + continueButton.getImgH())
            	{
            		continueButton.setClicked(true);
            	}
            	
            	if(Vector.magnitude(e.getX() - (buttons.get(0).getX() + buttons.get(0).getRadius()), e.getY() - (buttons.get(0).getY() + buttons.get(0).getRadius())) <= buttons.get(0).getRadius())
            	{
            		buttons.get(0).setClicked(true);
            	}
            }
        	
        	//Menu Screen
            else if(menu && transition == 0)
            {
            	if(e.getX() > continueButton.getX() && e.getX() < continueButton.getX() + continueButton.getImgW() && 
                   e.getY() > continueButton.getY() && e.getY() < continueButton.getY() + continueButton.getImgH())
                {
              		continueButton.setClicked(true);
              	}
            	
            	for(RectButton button : menuButtons)
            	{
	            	if(e.getX() > button.getX() && e.getX() < button.getX() + button.getImgW() && 
	                   e.getY() > button.getY() && e.getY() < button.getY() + button.getImgH())
	            	{
	            		button.setClicked(true);
	            	}
            	}
            	
            	if(Vector.magnitude(e.getX() - (buttons.get(0).getX() + buttons.get(0).getRadius()), e.getY() - (buttons.get(0).getY() + buttons.get(0).getRadius())) <= buttons.get(0).getRadius())
            	{
            		buttons.get(0).setClicked(true);
            	}
            }
        	
        	//Exit Screen
            else if(end)
            {
            	
            }
        	
        	//In Game
            else
            {
            	for(RoundButton button : buttons)
            	{
	            	if(Vector.magnitude(e.getX() - (button.getX() + button.getRadius()), e.getY() - (button.getY() + button.getRadius())) <= button.getRadius())
	            	{
	            		button.setClicked(true);
	            	}
            	}
            	
            	if(!inPlay)
            	{
            		if(e.getX() > 80 && e.getX() < 820 && 
                       e.getY() > 50 && e.getY() < 590)
                 	{
            			inPlay = true;
                 	}
            	}
            }
        }
        
        /* (non-Javadoc)
         * @see java.awt.event.MouseAdapter#mouseMoved(java.awt.event.MouseEvent)
         * 
         * Listens for mouse movement
         */
        public void mouseMoved(MouseEvent e)
        {
        	if((title || exposition || menu) && transition == 0)
            {
        		if(e.getX() > continueButton.getX() && e.getX() < continueButton.getX() + continueButton.getImgW() && 
                 	   e.getY() > continueButton.getY() && e.getY() < continueButton.getY() + continueButton.getImgH())
                 	{
                 		continueButton.setHover(true);
                 	}
        		else
        			continueButton.setHover(false);
            }
        	
        	if(menu && transition == 0)
        	{
        		for(RectButton button : menuButtons)
            	{
        			if(e.getX() > button.getX() && e.getX() < button.getX() + button.getImgW() && 
                      	   e.getY() > button.getY() && e.getY() < button.getY() + button.getImgH())
                      	{
                      		button.setHover(true);
                      	}
             		else
             			button.setHover(false);
            	}
        		
        		if(e.getX() > continueButton.getX() && e.getX() < continueButton.getX() + continueButton.getImgW() && 
                  	   e.getY() > continueButton.getY() && e.getY() < continueButton.getY() + continueButton.getImgH())
                  	{
                  		continueButton.setHover(true);
                  	}
         		else
         			continueButton.setHover(false);
        	}
        	
        	if((!title && !end) && transition == 0)
        	{
        		for(RoundButton button : buttons)
            	{
        			if(e.getX() > button.getX() && e.getX() < button.getX() + button.getImgW() && 
                       e.getY() > button.getY() && e.getY() < button.getY() + button.getImgH())
                      	{
                      		button.setHover(true);
                      	}
             		else
             			button.setHover(false);
            	}
        	}
        	
        	if(!title && !exposition && !menu && !end && !inPlay && transition == 0)
        	{
        		if(e.getX() > 80 && e.getX() < 820 && 
                   e.getY() > 50 && e.getY() < 590)
        		{
        			angle = (int)(Math.atan((double)((e.getY() - 50.0) / (e.getX() - 450.0)))*(180.0/Math.PI));
        			
        			if(angle < 0)
        				angle += 180;
        			
        			gameBalls.get(0).calculateSlope(angle);
        		}
        	}
        }
        /* (non-Javadoc)
         * @see java.awt.event.MouseAdapter#mouseDragged(java.awt.event.MouseEvent)
         * 
         * Need this to keep mouseMotionListener happy.
         */
        public void mouseDragged(MouseEvent e)
        {
        	
        }

    } // end of Mouse Class

	
	/**
	 * Controller
	 * @author Joshua Boone
	 * 4/27/2014
	 * MiraCosta Programming Competition
	 * 
	 * Listens for Keyboard commands.
	 */
	public class Controller extends KeyAdapter
    {
        /* (non-Javadoc)
         * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
         * 
         * Listens for KeyEvents
         */
        public void keyPressed(KeyEvent e)
        {
        	int key = e.getKeyCode();
        	
        	//Title Screen
            if(title && transition == 0)
            {
            	if(key == KeyEvent.VK_SPACE)
            		transition = 1;
            }
            
            //Exposition Screen
            else if(exposition && transition == 0)
            {
            	if(key == KeyEvent.VK_SPACE)
            		transition = 1;
            }
            
            //Menu Screen
            else if(menu && transition == 0)
            {
            	if(key == KeyEvent.VK_SPACE)
            		transition = 1;
            }
            
            //Exit Screen
            else if(end)
            {
            	
            }
            
            //In Game
            else
            {
            	if(!inPlay)
            	{
            		if(key == KeyEvent.VK_LEFT)
            		{
            			if(angle < 180)
            			{
            				angle += 1;
            				gameBalls.get(0).calculateSlope(angle);
            			}
            			else
            				angle = 180;
            		}
            		if(key == KeyEvent.VK_RIGHT)
            		{
            			if(angle > 1)
            			{
            				angle -= 1;
            				gameBalls.get(0).calculateSlope(angle);
            			}
            			else
            				angle = 1;
            		}
            	}
            	
            	if(key == KeyEvent.VK_ENTER)
            		inPlay = true;
            }
            
            //konami code
            if(up1)
            {
            	if(up2)
            	{
            		if(down1)
            		{
            			if(down2)
                		{
            				if(left1)
                    		{
            					if(right1)
                        		{
            						if(left2)
                            		{
            							if(right2)
                                		{
            								if(b)
                                    		{
            									if(a)
                                        		{
                                        			power1Unlocked = power2Unlocked = power3Unlocked = power4Unlocked = true;
                                        		}
                                        		else if(key == KeyEvent.VK_A)
                                        			a = true;
                                        		else 
                                        			up1 = up2 = down1 = down2 = left1 = right1 = left2 = right2 = b = false;
                                    		}
                                    		else if(key == KeyEvent.VK_B)
                                    			b = true;
                                    		else 
                                    			up1 = up2 = down1 = down2 = left1 = right1 = left2 = right2 = false;
                                		}
                                		else if(key == KeyEvent.VK_RIGHT)
                                			right2 = true;
                                		else 
                                			up1 = up2 = down1 = down2 = left1 = right1 = left2 = false;
                            		}
                            		else if(key == KeyEvent.VK_LEFT)
                            			left2 = true;
                            		else 
                            			up1 = up2 = down1 = down2 = left1 = right1 = false;
                        		}
                        		else if(key == KeyEvent.VK_RIGHT)
                        			right1 = true;
                        		else 
                        			up1 = up2 = down1 = down2 = left1 = false;
                    		}
                    		else if(key == KeyEvent.VK_LEFT)
                    			left1 = true;
                    		else 
                    			up1 = up2 = down1 = down2 = false;
                		}
                		else if(key == KeyEvent.VK_DOWN)
                			down2 = true;
                		else 
                			up1 = up2 = down1 = false;
            		}
            		else if(key == KeyEvent.VK_DOWN)
            			down1 = true;
            		else 
            			up1 = up2 = false;
            	}
            	else if(key == KeyEvent.VK_UP)
                	up2 = true;
            	else 
            		up1 = false;
            }
            else if(key == KeyEvent.VK_UP)
            	up1 = true;    
        }
    } // end of Controller Class

	
//-----Helper Methods-----
	
	/**
	 * Initiates a new round, setting the level and special peg locations.
	 */
	private void initiateRound()
	{
		numOfBallsInPlay = 10;
		
		if(level == 1)
		{
			pegs.clear();       
	        pegs.add(new Peg(192, 128, 0, 10.5));
	        pegs.add(new Peg(294, 168, 0, 10.5));
	        pegs.add(new Peg(389, 148, 0, 10.5));
	        pegs.add(new Peg(509, 151, 0, 10.5));
	        pegs.add(new Peg(586, 99, 0, 10.5));
	        pegs.add(new Peg(676, 123, 0, 10.5));
	        pegs.add(new Peg(716, 153, 0, 10.5));
	        pegs.add(new Peg(761, 231, 0, 10.5));
	        pegs.add(new Peg(674, 219, 0, 10.5));
	        pegs.add(new Peg(601, 186, 0, 10.5));
	        pegs.add(new Peg(534, 247, 0, 10.5));
	        pegs.add(new Peg(531, 277, 0, 10.5));
	        pegs.add(new Peg(268, 210, 0, 10.5));
	        pegs.add(new Peg(346, 229, 0, 10.5));
	        pegs.add(new Peg(222, 268, 0, 10.5));
	        pegs.add(new Peg(325, 283, 0, 10.5));
	        pegs.add(new Peg(110, 300, 0, 10.5));
	        pegs.add(new Peg(155, 315, 0, 10.5));
	        pegs.add(new Peg(205, 246, 0, 10.5));
	        pegs.add(new Peg(232, 369, 0, 10.5));
	        pegs.add(new Peg(289, 373, 0, 10.5));
	        pegs.add(new Peg(382, 417, 0, 10.5));
	        pegs.add(new Peg(481, 447, 0, 10.5));
	        pegs.add(new Peg(570, 416, 0, 10.5));
	        pegs.add(new Peg(589, 380, 0, 10.5));
	        pegs.add(new Peg(615, 351, 0, 10.5));
	        pegs.add(new Peg(663, 329, 0, 10.5));
	        pegs.add(new Peg(709, 320, 0, 10.5));
	        pegs.add(new Peg(747, 280, 0, 10.5));
	        pegs.add(new Peg(791, 261, 0, 10.5));
	        pegs.add(new Peg(218, 497, 0, 10.5));
	        pegs.add(new Peg(264, 497, 0, 10.5));
	        pegs.add(new Peg(310, 496, 0, 10.5));
	        pegs.add(new Peg(218, 544, 0, 10.5));
	        pegs.add(new Peg(264, 544, 0, 10.5));
	        pegs.add(new Peg(311, 544, 0, 10.5));
	        pegs.add(new Peg(358, 544, 0, 10.5));
	        pegs.add(new Peg(178, 544, 0, 10.5));
	        pegs.add(new Peg(222, 453, 0, 10.5));
	        pegs.add(new Peg(264, 453, 0, 10.5));
	        pegs.add(new Peg(310, 453, 0, 10.5));
	        pegs.add(new Peg(671, 502, 0, 10.5));
	        
		}
		
		else if(level == 2)
		{
			pegs.clear();       
			pegs.add(new Peg(350, 74, 0, 10.5));
			pegs.add(new Peg(360, 101, 0, 10.5));
			pegs.add(new Peg(374, 139, 0, 10.5));
			pegs.add(new Peg(386, 177, 0, 10.5));
			pegs.add(new Peg(397, 210, 0, 10.5));
			pegs.add(new Peg(408, 246, 0, 10.5));
			pegs.add(new Peg(420, 291, 0, 10.5));
			pegs.add(new Peg(152, 140, 0, 10.5));
			pegs.add(new Peg(152, 180, 0, 10.5));
			pegs.add(new Peg(152, 220, 0, 10.5));
			pegs.add(new Peg(152, 260, 0, 10.5));
			pegs.add(new Peg(152, 300, 0, 10.5));
			pegs.add(new Peg(152, 340, 0, 10.5));
			pegs.add(new Peg(152, 380, 0, 10.5));
			pegs.add(new Peg(152, 420, 0, 10.5));
			pegs.add(new Peg(152, 460, 0, 10.5));
			pegs.add(new Peg(152, 500, 0, 10.5));
			pegs.add(new Peg(751, 140, 0, 10.5));
			pegs.add(new Peg(751, 180, 0, 10.5));
			pegs.add(new Peg(751, 220, 0, 10.5));
			pegs.add(new Peg(751, 260, 0, 10.5));
			pegs.add(new Peg(751, 300, 0, 10.5));
			pegs.add(new Peg(751, 340, 0, 10.5));
			pegs.add(new Peg(751, 380, 0, 10.5));
			pegs.add(new Peg(751, 420, 0, 10.5));
			pegs.add(new Peg(751, 460, 0, 10.5));
			pegs.add(new Peg(751, 500, 0, 10.5));
			pegs.add(new Peg(173, 543, 0, 10.5));
			pegs.add(new Peg(197, 516, 0, 10.5));
			pegs.add(new Peg(222, 478, 0, 10.5));
			pegs.add(new Peg(251, 443, 0, 10.5));
			pegs.add(new Peg(312, 439, 0, 10.5));
			pegs.add(new Peg(361, 461, 0, 10.5));
			pegs.add(new Peg(378, 506, 0, 10.5));
			pegs.add(new Peg(363, 551, 0, 10.5));
			pegs.add(new Peg(340, 526, 0, 10.5));
			pegs.add(new Peg(301, 528, 0, 10.5));
			pegs.add(new Peg(312, 485, 0, 10.5));
			pegs.add(new Peg(266, 487, 0, 10.5));
			pegs.add(new Peg(720, 448, 0, 10.5));
			pegs.add(new Peg(663, 404, 0, 10.5));
			pegs.add(new Peg(597, 346, 0, 10.5));
			pegs.add(new Peg(558, 294, 0, 10.5));
			pegs.add(new Peg(209, 414, 0, 10.5));
			pegs.add(new Peg(352, 402, 0, 10.5));
			pegs.add(new Peg(427, 371, 0, 10.5));
			pegs.add(new Peg(572, 189, 0, 10.5));
			pegs.add(new Peg(532, 241, 0, 10.5));
			pegs.add(new Peg(478, 262, 0, 10.5));
		}
		
		if(level == 3)
		{
			pegs.clear();       
			pegs.add(new Peg(150, 300, 0, 10.5));
			pegs.add(new Peg(190, 300, 0, 10.5));
			pegs.add(new Peg(230, 300, 0, 10.5));
			pegs.add(new Peg(270, 300, 0, 10.5));
			pegs.add(new Peg(310, 300, 0, 10.5));
			pegs.add(new Peg(350, 300, 0, 10.5));
			pegs.add(new Peg(390, 300, 0, 10.5));
			pegs.add(new Peg(430, 300, 0, 10.5));
			pegs.add(new Peg(470, 300, 0, 10.5));
			pegs.add(new Peg(510, 300, 0, 10.5));
			pegs.add(new Peg(550, 300, 0, 10.5));
			pegs.add(new Peg(590, 300, 0, 10.5));
			pegs.add(new Peg(630, 300, 0, 10.5));
			pegs.add(new Peg(670, 300, 0, 10.5));
			pegs.add(new Peg(710, 300, 0, 10.5));
			pegs.add(new Peg(750, 300, 0, 10.5));
			pegs.add(new Peg(110, 380, 0, 10.5));
			pegs.add(new Peg(150, 380, 0, 10.5));
			pegs.add(new Peg(190, 380, 0, 10.5));
			pegs.add(new Peg(230, 380, 0, 10.5));
			pegs.add(new Peg(270, 380, 0, 10.5));
			pegs.add(new Peg(310, 380, 0, 10.5));
			pegs.add(new Peg(350, 380, 0, 10.5));
			pegs.add(new Peg(390, 380, 0, 10.5));
			pegs.add(new Peg(430, 380, 0, 10.5));
			pegs.add(new Peg(470, 380, 0, 10.5));
			pegs.add(new Peg(510, 380, 0, 10.5));
			pegs.add(new Peg(550, 380, 0, 10.5));
			pegs.add(new Peg(590, 380, 0, 10.5));
			pegs.add(new Peg(630, 380, 0, 10.5));
			pegs.add(new Peg(670, 380, 0, 10.5));
			pegs.add(new Peg(710, 380, 0, 10.5));
			pegs.add(new Peg(750, 380, 0, 10.5));
			pegs.add(new Peg(790, 380, 0, 10.5));
			pegs.add(new Peg(188, 276, 0, 10.5));
			pegs.add(new Peg(278, 276, 0, 10.5));
			pegs.add(new Peg(518, 276, 0, 10.5));
			pegs.add(new Peg(448, 276, 0, 10.5));
			pegs.add(new Peg(369, 349, 0, 10.5));
			pegs.add(new Peg(449, 349, 0, 10.5));
			pegs.add(new Peg(719, 349, 0, 10.5));
		}
		
		if(level == 4)
		{
			pegs.clear();       
			pegs.add(new Peg(190, 230, 0, 10.5));
			pegs.add(new Peg(230, 230, 0, 10.5));
			pegs.add(new Peg(270, 230, 0, 10.5));
			pegs.add(new Peg(310, 230, 0, 10.5));
			pegs.add(new Peg(350, 230, 0, 10.5));
			pegs.add(new Peg(390, 230, 0, 10.5));
			pegs.add(new Peg(430, 230, 0, 10.5));
			pegs.add(new Peg(470, 230, 0, 10.5));
			pegs.add(new Peg(510, 230, 0, 10.5));
			pegs.add(new Peg(550, 230, 0, 10.5));
			pegs.add(new Peg(590, 230, 0, 10.5));
			pegs.add(new Peg(630, 230, 0, 10.5));
			pegs.add(new Peg(670, 230, 0, 10.5));
			pegs.add(new Peg(710, 230, 0, 10.5));
			pegs.add(new Peg(150, 340, 0, 10.5));
			pegs.add(new Peg(190, 340, 0, 10.5));
			pegs.add(new Peg(230, 340, 0, 10.5));
			pegs.add(new Peg(270, 340, 0, 10.5));
			pegs.add(new Peg(310, 340, 0, 10.5));
			pegs.add(new Peg(350, 340, 0, 10.5));
			pegs.add(new Peg(390, 340, 0, 10.5));
			pegs.add(new Peg(430, 340, 0, 10.5));
			pegs.add(new Peg(470, 340, 0, 10.5));
			pegs.add(new Peg(510, 340, 0, 10.5));
			pegs.add(new Peg(550, 340, 0, 10.5));
			pegs.add(new Peg(590, 340, 0, 10.5));
			pegs.add(new Peg(630, 340, 0, 10.5));
			pegs.add(new Peg(670, 340, 0, 10.5));
			pegs.add(new Peg(710, 340, 0, 10.5));
			pegs.add(new Peg(750, 340, 0, 10.5));
			pegs.add(new Peg(150, 448, 0, 10.5));
			pegs.add(new Peg(190, 448, 0, 10.5));
			pegs.add(new Peg(230, 448, 0, 10.5));
			pegs.add(new Peg(270, 448, 0, 10.5));
			pegs.add(new Peg(310, 448, 0, 10.5));
			pegs.add(new Peg(350, 448, 0, 10.5));
			pegs.add(new Peg(390, 448, 0, 10.5));
			pegs.add(new Peg(430, 448, 0, 10.5));
			pegs.add(new Peg(470, 448, 0, 10.5));
			pegs.add(new Peg(510, 448, 0, 10.5));
			pegs.add(new Peg(550, 448, 0, 10.5));
			pegs.add(new Peg(590, 448, 0, 10.5));
			pegs.add(new Peg(630, 448, 0, 10.5));
			pegs.add(new Peg(670, 448, 0, 10.5));
			pegs.add(new Peg(710, 448, 0, 10.5));
			pegs.add(new Peg(750, 448, 0, 10.5));
			pegs.add(new Peg(201, 208, 0, 10.5));
			pegs.add(new Peg(382, 209, 0, 10.5));
			pegs.add(new Peg(611, 209, 0, 10.5));
			pegs.add(new Peg(303, 317, 0, 10.5));
			pegs.add(new Peg(570, 426, 0, 10.5));
		}
		
		else if(level == 0)
		{
			pegs.clear();  
			
			int tempX;
			int tempY;
			boolean tempBoolean;
			
			//Generate Randon Level
			while(pegs.size() < 50)
			{
				tempX = 110 + 40*(int)Math.round(Math.random()*17.0);
				tempY = 150 + 40*(int)Math.round(Math.random()*10.0);
				tempBoolean = false;
				
				for(Peg peg : pegs)
				{
					if(peg.getX() == tempX && peg.getY() == tempY)
					{
						tempBoolean = true;
						break;
					}
				}
				
				if(!tempBoolean)
					pegs.add(new Peg(tempX, tempY, 0, 10.5));
			}
		}
		
		//Assign Primary Pegs
		numOfPrimaryPegs = 0;
		while(numOfPrimaryPegs < 10)
		{
			for(Peg peg : pegs)
			{
				if(Math.random() < 10.0/pegs.size() && peg.getType() == 0)
				{
					peg.setType(1);
					numOfPrimaryPegs++;
					if(numOfPrimaryPegs == 10)
						break;
				}
			}
		}
		
		//Assign Power Pegs
		numOfPowerPegs = 0;
		while(numOfPowerPegs < 2)
		{
			for(Peg peg : pegs)
			{
				if(Math.random() < 2.0/pegs.size() && peg.getType() == 0)
				{
					peg.setType(3);
					numOfPowerPegs++;
					if(numOfPowerPegs == 2)
						break;
				}
			}
		}
		
		assignBonusPeg();
	}
	
	/**
	 * Assigns the bonus peg in-between turns.
	 */
	private void assignBonusPeg()
	{
		for(Peg peg : pegs)
		{
			if(peg.getType() == 2)
			{
				peg.setType(0);
				break;
			}
		}

		numOfBonusPegs = 0;
		while(numOfPrimaryPegs + numOfPowerPegs < pegs.size() && numOfBonusPegs == 0)
		{
			for(Peg peg : pegs)
			{
				if(peg.getType() == 0 && Math.random() < 1.0/pegs.size())
				{
					peg.setType(2);
					numOfBonusPegs++;
					break;
				}
			}
		}
	}
	
	/**
	 * Moves the Game Balls across the screen.
	 */
	private void moveBall()
	{
		for(GameBall ball : gameBalls)
		{
			ball.calculateVelocity();
			ball.setX(ball.getX() + ball.getxVelocity());
			ball.setY(ball.getY() + ball.getyVelocity());
		}
	}
	
	/**
	 * Checks for Game Ball collisions
	 */
	private void checkCollision()
	{
		for(GameBall ball : gameBalls)
		{
			//Check for wall collisions
			if(ball.getX() < ball.getRadius() + 80)
			{
				ball.setX(ball.getRadius() + 80);
				ball.setxVelocity(-ball.getxVelocity());
				ball.getDirection().setX(-ball.getDirection().getX());
			}
			if(ball.getX() > getWidth() - ball.getRadius() - 80)
			{
				ball.setX(getWidth() - ball.getRadius() - 80);
				ball.setxVelocity(-ball.getxVelocity());
				ball.getDirection().setX(-ball.getDirection().getX());
			}
			if(ball.getY() < ball.getRadius() + 50)
			{
				ball.setY(ball.getRadius() + 50);
				ball.setyVelocity(-ball.getyVelocity());
				ball.getDirection().setY(-ball.getDirection().getY());
			}
			if(ball.getY() > getHeight() - ball.getRadius())
			{
				ball.setY(getHeight() - ball.getRadius());
				ball.setyVelocity(-ball.getyVelocity());
				ball.getDirection().setY(-ball.getDirection().getY());
				if((power != 2 && ball.getX() > goal.getX() + 10 && ball.getX() < goal.getX() + goal.getImgW() - 10) ||
				   (power == 2 && ball.getX() > goal.getX() + 10 && ball.getX() < goal.getX() + goal.getImgW()*2 - 10))
				{
					numOfBallsInPlay++;
					
					if(power != 3 && powerDuration > 0)
						powerDuration--;
					ball.setStatus(false);
				}
				else
				{
					if(power == 3 && powerDuration > 0)
						ball.setY(50 + ball.getRadius());
					
					else
						ball.setStatus(false);
					
					if(powerDuration > 0)
						powerDuration--;
				}
				
			}
			
			//Check goal collision - left
			if(ball.getStatus() && Vector.magnitude(ball.getX() - (goal.getX() + 10.5), ball.getY() - (goal.getY() + 13.5)) <= ball.getRadius() + 10.5)
			{
				// Use the normal vector between the ball and peg, and the direction vector of the ball to find the bounce vector.
				Vector tempD = new Vector();
				tempD.copy(ball.getDirection());
				Vector tempN = new Vector(ball.getX() - (goal.getX() + 10.5), ball.getY() - (goal.getY() + 13.5));
				tempN.makeUnitVector();
				
				if(Vector.magnitude(ball.getX() - (goal.getX() + 10.5), ball.getY() - (goal.getY() + 13.5)) < ball.getRadius() + 10.5)
				{
					ball.setX(tempN.getX()*(ball.getRadius() + 10.5) + goal.getX() + 10.5);
					ball.setY(tempN.getY()*(ball.getRadius() + 10.5) + goal.getY() + 13.5);
				}
				
				//(d DOT n / n DOT n)n
				Vector tempV = Vector.scalarMult(Vector.dot(tempD, tempN), tempN);// / Vector.dot(tempN, tempN)

				//d-2v
				ball.setDirection(Vector.sub(tempD, Vector.scalarMult(2, tempV)));
			}
			
			//Check goal collision - right
			if(ball.getStatus() && ((power == 2 && powerDuration > 0 && Vector.magnitude(ball.getX() - (goal.getX() + 146.5*2), ball.getY() - (goal.getY() + 13.5)) <= ball.getRadius() + 10.5) ||
									(((power == 2 && powerDuration <= 0) || power != 2) && Vector.magnitude(ball.getX() - (goal.getX() + 146.5), ball.getY() - (goal.getY() + 13.5)) <= ball.getRadius() + 10.5)))
			{
				// Use the normal vector between the ball and peg, and the direction vector of the ball to find the bounce vector.
				Vector tempD = new Vector();
				tempD.copy(ball.getDirection());
				Vector tempN;
				if(power == 2 && powerDuration > 0)
					tempN = new Vector(ball.getX() - (goal.getX() + 146.5*2), ball.getY() - (goal.getY() + 13.5));
				else
					tempN = new Vector(ball.getX() - (goal.getX() + 146.5), ball.getY() - (goal.getY() + 13.5));
				
				tempN.makeUnitVector();
				
				if(((power == 2 && powerDuration <= 0) || power != 2) && Vector.magnitude(ball.getX() - (goal.getX() + 146.5), ball.getY() - (goal.getY() + 13.5)) < ball.getRadius() + 10.5)
				{
					ball.setX(tempN.getX()*(ball.getRadius() + 10.5) + goal.getX() + 146.5);
					ball.setY(tempN.getY()*(ball.getRadius() + 10.5) + goal.getY() + 13.5);
				}
				else if(power == 2 && powerDuration > 0 && Vector.magnitude(ball.getX() - (goal.getX() + 146.5*2), ball.getY() - (goal.getY() + 13.5)) < ball.getRadius() + 10.5)
				{
					ball.setX(tempN.getX()*(ball.getRadius() + 10.5) + goal.getX() + 146.5*2);
					ball.setY(tempN.getY()*(ball.getRadius() + 10.5) + goal.getY() + 13.5);
				}
				
				//(d DOT n / n DOT n)n
				Vector tempV = Vector.scalarMult(Vector.dot(tempD, tempN), tempN);// / Vector.dot(tempN, tempN)

				//d-2v
				ball.setDirection(Vector.sub(tempD, Vector.scalarMult(2, tempV)));
			}
				
			//Check for peg collisions
			for(Peg peg : pegs)
			{
				if(ball.getStatus() && Vector.magnitude(ball.getX() - peg.getX(), ball.getY() - peg.getY()) <= ball.getRadius() + peg.getRadius())
				{	
					// Use the normal vector between the ball and peg, and the direction vector of the ball to find the bounce vector.
					Vector tempD = new Vector();
					tempD.copy(ball.getDirection());
					Vector tempN = new Vector(ball.getX() - peg.getX(), ball.getY() - peg.getY());
					tempN.makeUnitVector();
					
					if(!(power == 4 && powerDuration > 0 && ball.getImgW() == 42))
					{
						if(Vector.magnitude(ball.getX() - peg.getX(), ball.getY() - peg.getY()) < ball.getRadius() + peg.getRadius())
						{
							ball.setX(tempN.getX()*(ball.getRadius() + peg.getRadius()) + peg.getX());
							ball.setY(tempN.getY()*(ball.getRadius() + peg.getRadius()) + peg.getY());
						}
						
						//(d DOT n / n DOT n)n
						Vector tempV = Vector.scalarMult(Vector.dot(tempD, tempN), tempN);
						
						//d-2v
						ball.setDirection(Vector.sub(tempD, Vector.scalarMult(2, tempV)));
					}
					
					// Update Peg.
					if(peg.getType() == 0 && !peg.getStatus())
						score += 10*(10 - numOfPrimaryPegs);
					
					else if(peg.getType() == 1 && !peg.getStatus())
					{
						numOfPrimaryPegs--;
						score += 100*(10 - numOfPrimaryPegs);
					}
					else if(peg.getType() == 2 && !peg.getStatus())
						score += 1000;
					
					else if(peg.getType() == 3 && !peg.getStatus())
					{
						if(power == 1)
						{
							tempGameBall = new GameBall();
							tempGameBall.copy(ball);
							tempGameBall.getDirection().setX(-tempGameBall.getDirection().getX());
						}
						else if(power == 2)
						{
							if(powerDuration <= 0)
							{
								goal.setX(80);
								goalAcceleration = 0;
							}
							powerDuration += 3;
						}
						
						else if(power == 3)
							if(powerDuration <= 0)
								powerDuration = 1;
							
							else
								powerDuration += 1;
						
						else if(power == 4)
							powerDuration += 2;

						numOfPowerPegs--;
						score += 50*(10 - numOfPrimaryPegs);
					}
					
					if(peg.getStatus())
						peg.setLifespan(peg.getLifespan() - 1);
					
					else
					{
						peg.setStatus(true);
						peg.setLifespan(5);
					}
				}
			}
		}
		
		//Remove dead elements
		for(int i= gameBalls.size() - 1; i >= 0; i--)
		{
			if(!gameBalls.get(i).getStatus())
				gameBalls.remove(i);
		}
		for(int i= pegs.size() - 1; i >= 0; i--)
		{
			if(pegs.get(i).getStatus() && pegs.get(i).getLifespan() <= 0)
				pegs.remove(i);
		}
		
		//Add new elements
		if(tempGameBall != null)
		{
			gameBalls.add(tempGameBall);
			tempGameBall = null;
		}
		
		if(gameBalls.isEmpty())
		{
			//Remove hit pegs
			for(int i= pegs.size() - 1; i >= 0; i--)
			{
				if(pegs.get(i).getStatus())
					pegs.remove(i);
			}
			
			inPlay = false;
			numOfBallsInPlay--;
			gameBalls.add(new GameBall());
			assignBonusPeg();
		}
		
		
	}
	
	/**
	 * Checks for button clicks and hovers.
	 */
	private void buttonCheck()
	{
		if(powerUps)
		{
			//Power Button 1
			if(buttons.get(1).getHover())
				powerInfo = 1;
			
			//Power Button 2
			else if(buttons.get(2).getHover())
				powerInfo = 2;
					
			//Power Button 3
			else if(buttons.get(3).getHover())
				powerInfo = 3;
					
			//Power Button 4
			else if(buttons.get(4).getHover())
				powerInfo = 4;
			
			else
				powerInfo = 0;
		}
		
		//Continue Button
		if(continueButton.getClicked())
		{
			if(exposition)
				exposition = instructions = powerUps = highScoreScreen = false;
			else if(title || menu)
				transition = 1;
		
			continueButton.setClicked(false);
		}
		
		//Menu Button
		else if(buttons.get(0).getClicked())
		{
			if(menu || exposition)
				menu = exposition = instructions = powerUps = highScoreScreen = false;
			
			else
				menu = true;
			transition = -100;
			buttons.get(0).setClicked(false);
		}
		
		//Power Button 1
		else if(buttons.get(1).getClicked())
		{
			if(powerUps)
				powerInfo = 1;
			
			else if(power1Unlocked)
				power = 1;
			buttons.get(1).setClicked(false);
		}
		
		//Power Button 2
		else if(buttons.get(2).getClicked())
		{
			if(powerUps)
				powerInfo = 2;
			
			else if(power2Unlocked)
				power = 2;
			buttons.get(2).setClicked(false);
		}
				
		//Power Button 3
		else if(buttons.get(3).getClicked())
		{
			if(powerUps)
				powerInfo = 3;
			
			else if(power3Unlocked)
				power = 3;
			buttons.get(3).setClicked(false);
		}
				
		//Power Button 4
		else if(buttons.get(4).getClicked())
		{
			if(powerUps)
				powerInfo = 4;
			
			else if(power4Unlocked)
				power = 4;
			buttons.get(4).setClicked(false);
		}

		//Restart Button
		else if(buttons.get(5).getClicked())
		{
			inPlay = false;
			newRound = true;
			buttons.get(5).setClicked(false);
		}
		
		//Instructions Button
		else if(menuButtons.get(0).getClicked())
		{
			exposition = true;
			instructions = true;
			menuButtons.get(0).setClicked(false);
		}
		
		//Power Ups Button
		else if(menuButtons.get(1).getClicked())
		{
			exposition = true;
			powerUps = true;
			powerInfo = 0;
			menuButtons.get(1).setClicked(false);
		}
		
		//High Scores Button
		else if(menuButtons.get(2).getClicked())
		{
			exposition = true;
			highScoreScreen = true;
			menuButtons.get(2).setClicked(false);
		}
		
		//Quit Button
		else if(menuButtons.get(3).getClicked())
		{
			end = true;
			transition = 1;
			menu = exposition = false;
			menuButtons.get(3).setClicked(false);
		}
				
	}
	
	/**
	 * Adjusts for lag.
	 */
	private void sleep()
	{
		long timeDiff, sleep;
		timeDiff = System.currentTimeMillis() - beforeTime;
        sleep = DELAY - timeDiff;

        if (sleep < 0)
            sleep = 2;
        try 
        {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            System.out.println("interrupted");
        }

        beforeTime = System.currentTimeMillis();
	}
	
	/**
	 * Loads saved high scores from an external file.
	 * 
	 * @throws FileNotFoundException
	 */
	private void loadScores() throws FileNotFoundException
	{
		try
		{
			String tempLine;
			BufferedReader inputFile = new BufferedReader(new FileReader(new File("PegPopperSave.txt")));
			
			while(inputFile.ready())
			{
				tempLine = inputFile.readLine();
				highScoreNames.add(tempLine.substring(0, tempLine.indexOf("|")));
				highScores.add(Integer.parseInt(tempLine.substring(tempLine.indexOf("|") + 1)));
			}
			
			inputFile.close();
		} 
		catch (FileNotFoundException e) 
		{
			System.out.println("The file \"PegPopperSave.txt\" could not be found.");
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			System.out.println("The Peg Popper encountered an error.");
			e.printStackTrace();
		} 
	}
	
	/**
	 * Saves high scores to an external file.
	 * 
	 * @throws FileNotFoundException
	 */
	private void saveScores() throws FileNotFoundException
	{
		PrintWriter outputFile = new PrintWriter("PegPopperSave.txt");
				
		for(int i = 0; i < 10; i++)
		{
			outputFile.println(highScoreNames.get(i) + "|" + highScores.get(i));
		}
		outputFile.close();
	}
}
