package cs2410.assn8.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Jack Thomas
 * @version 8.1
 *
 */
public class ScorePanel extends JPanel{
	/**
	 * A JLabel to store the timer values
	 */
	private JLabel timerLabel = new JLabel();
	/**
	 * A timer to show the user how it has been since they started
	 */
	private Timer timer = new Timer();
	/**
	 * A JButton to reset the game
	 */
	private JButton resetButton = new JButton();
	/**
	 * A JLabel to store how many bombs are left
	 */
	private JLabel bombsLeftLabel = new JLabel();
	/**
	 * An int to store the original number of bombs
	 */
	private int origBombCount;
	/**
	 * An int to store the number that the user sees of how many bombs are left
	 */
	private int bombsLeftCount;
	/**
	 * An image icon of a smiley face that is displayed on the reset button
	 */
	private ImageIcon smileyFace = new ImageIcon("./images/smileyFace.png");
	/**
	 * An image icon of a surprised face that is displayed on the reset button when a cell is clicked
	 */
	private ImageIcon surprisedFace = new ImageIcon("./images/surprisedFace.jpg");
	/**
	 * An int to store the current time that the user sees and is changed by the timer
	 */
	private int currentTime = 0;
	/**
	 * A font, sets the font to bold, size 15, and "Impact"
	 */
	private Font myFont = new Font("Impact", Font.BOLD, 15);
	
	/**
	 * The constructor for the ScorePanel object, sets fonts, layouts, texts, and adds them to the panel
	 * @param ml
	 */
	public ScorePanel(MouseListener ml){
		timerLabel.setFont(myFont);
		bombsLeftLabel.setFont(myFont);
		timerLabel.setForeground(Color.RED);
		bombsLeftLabel.setForeground(Color.RED);
		this.setLayout(new GridLayout(1,3));
		bombsLeftCount = 100;
		origBombCount = bombsLeftCount;
		bombsLeftLabel.setText(Integer.toString(bombsLeftCount));
		bombsLeftLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(bombsLeftLabel);
		resetButton.addMouseListener(ml);
		this.add(resetButton);
		timerLabel.setText(Integer.toString(currentTime));
		timerLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(timerLabel);
	}
	/**
	 * Sets the image on the button to the Smiley Face
	 */
	public void setSmileyFace(){
		resetButton.setText("");
		smileyFace = new ImageIcon(smileyFace.getImage().getScaledInstance(20, 15, Image.SCALE_SMOOTH));
		resetButton.setIcon(smileyFace);
		this.update(this.getGraphics());
	}
	/**
	 * Sets the image on the reset button to the surprised face
	 */
	public void setSurprisedFace(){
		resetButton.removeAll();
		surprisedFace = new ImageIcon(surprisedFace.getImage().getScaledInstance(20, 15, Image.SCALE_SMOOTH));
		resetButton.setIcon(surprisedFace);
		this.update(this.getGraphics());
	}
	/**
	 * Decrements the number of bombs left
	 */
	public void decrBombsLeft(){
		bombsLeftCount--;
		bombsLeftLabel.setText(Integer.toString(bombsLeftCount));
		this.update(this.getGraphics());
	}
	/**
	 * Increments the number of bombs left
	 */
	public void incrBombsLeft(){
		if(bombsLeftCount == origBombCount)return;
		bombsLeftCount++;
		bombsLeftLabel.setText(Integer.toString(bombsLeftCount));
		this.update(this.getGraphics());
	}
	/**
	 * Resets the number of bombs left to the original amount
	 */
	public void resetBombsLeft(){
		bombsLeftCount = origBombCount;
		bombsLeftLabel.setText(Integer.toString(bombsLeftCount));
		this.update(this.getGraphics());
	}
	/**
	 * Resets the timer to start at 0
	 */
	public void resetTimer(){
		timer.cancel();
		timer = new Timer();
		currentTime = 0;
		timerLabel.setText(Integer.toString(currentTime));
		this.update(this.getGraphics());
	}
	/**
	 * Starts the counter to count up
	 * @param temp
	 */
	public void startCountUp(JPanel temp){
		timer.scheduleAtFixedRate(new TimerTask(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				currentTime++;
				timerLabel.setText(Integer.toString(currentTime));
				temp.update(temp.getGraphics());
			}
			
		}, 0, 1000); 
	}
	/**
	 * Method to stop the timer from counting up
	 */
	public void stopTimer(){
		timer.cancel();
		timer = new Timer();
	}
	/**
	 * Method that returns the current time
	 * @return
	 */
	public int getTime(){
		return currentTime;
	}
}
