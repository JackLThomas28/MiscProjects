package cs2410.assn8.view;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import cs2410.assn8.view.GameBoard.Cell;

/**
 * @author Jack Thomas
 * @version 8.0
 *
 */
public class GameFrame extends JFrame{
	/**
	 * An instance of the GameBoard Class, which contains all of the cells for the game
	 */
	private GameBoard gameBoard;
	/**
	 * An instance of the ScorePanel Class, which contains the timer, reset button, and current score, and high score
	 */
	private ScorePanel scorePanel;
	/**
	 * The JPanel that contains the GameBoard Panel and ScorePanel Panel
	 */
	private JPanel pane;
	
	public GameFrame(int size, MouseListener ml){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Minesweeper-ish");
		pane = (JPanel) this.getContentPane();
		pane.setLayout(new BorderLayout());
		gameBoard = new GameBoard(size, ml);
		scorePanel = new ScorePanel(ml);
		pane.add(gameBoard, BorderLayout.CENTER);
		pane.add(scorePanel, BorderLayout.NORTH);
		scorePanel.setSmileyFace();
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	/**
	 * Method to set the scorePanel button to a smiley face
	 */
	public void setButtonSmiley(){
		scorePanel.setSmileyFace();
	}
	/**
	 * Method to set the scorePanel button to a surprised face
	 */
	public void setButtonSurprised(){
		scorePanel.setSurprisedFace();
	}
	/**
	 * Method that calls the gameBoard reset method
	 * @param ml
	 */
	public void resetGameBoard(MouseListener ml){
		//Reset Timer
		//Reset Score
		gameBoard.resetGameBoard(ml);
	}
	/**
	 * Method that returns the game board, the 2D array of cells
	 * @return
	 */
	public Cell[][] getGameBoardCell(){
		return gameBoard.getCells();
	}
	/**
	 * Method that increments the number of bombs left on the score panel
	 */
	public void incrBombsLeft(){
		scorePanel.incrBombsLeft();
	}
	/**
	 * Method that decrements the number of bombs left on the score panel
	 */
	public void decrBombsLeft(){
		scorePanel.decrBombsLeft();
	}
	/**
	 * Method that resets the values on the score panel
	 */
	public void resetScorePanel(){
		scorePanel.resetBombsLeft();
		scorePanel.resetTimer();
	}
	/**
	 * Method that starts the timer to count up on the score panel
	 */
	public void startCountUp(){
		scorePanel.startCountUp(scorePanel);
	}
	/**
	 * Method that will stop the timer on the score panel
	 */
	public void stopTimer(){
		scorePanel.stopTimer();
	}
	/**
	 * Method that returns the current time of the score panel
	 * @return
	 */
	public int getTime(){
		return scorePanel.getTime();
	}
}
