package controller;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import cs2410.assn8.view.GameFrame;
import cs2410.assn8.view.GameBoard.Cell;
import cs2410.assn8.view.GameBoard.CellState;

/**
 * @author Jack Thomas
 * @version 8.0
 *	The Game class which creates the minesweeper game and handles all of the game logic
 */
public class Game implements MouseListener{
	/**
	 * An instance of the GameFrame Class, which is a JFrame and contains the GameBoard and ScorePanel
	 */
	private GameFrame gameFrame;
	/**
	 * An image icon of the bomb
	 */
	private ImageIcon bombIcon = new ImageIcon("./images/bomb.png", null);
	/**
	 * An image icon of the flag
	 */
	private ImageIcon flagIcon = new ImageIcon("./images/flag2.png", null);
	/**
	 * An image of the question mark
	 */
	private ImageIcon questionIcon = new ImageIcon("./images/questionMark2.gif", null);
	/**
	 * A random number generator
	 */
	private Random rand = new Random();
	/**
	 * A boolean that is set to true if the player loses
	 */
	private boolean playerLoses = false;
	/**
	 * A boolean that is set to true if the player wins
	 */
	private boolean playerWins = false;
	/**
	 * A boolean to determine if it is the players first click of the game
	 */
	private boolean firstClick = true;
	/**
	 * An integer to store the number of clicked cells to check if the player has won the game
	 */
	private int numberOfClickedCells = 0;
	/**
	 * An integer to store the total number of bombs that will be on the gameboard
	 */
	private int totalBombs = 100;
	/**
	 * An integer to store the length of the row and col for the game
	 */
	private int rowAndColLength = 24;
	/**
	 * An array list of bomb locations on the gameboard
	 */
	ArrayList<BombLocation> bombLocations = new ArrayList<BombLocation>();
	/**
	 * A class that has creates an instance of a bomb location with the row and column
	 * @author jack
	 *
	 */
	private class BombLocation{
		public int row;
		public int col;
		BombLocation(int row, int col){
			this.row = row;
			this.col = col;
		}
	}
	/**
	 * The Game class constructor initializes all variables
	 */
	public Game(){
		gameFrame = new GameFrame(rowAndColLength, this);
		placeBombs();
		findBombs();
		gameFrame.pack();
	}
	/**
	 * Method that places 100 bombs randomly throughout the 24x24 Cell array
	 */
	private void placeBombs(){
		Cell[][] tempCell = gameFrame.getGameBoardCell();
		while(totalBombs > 0){
			int row = rand.nextInt(24);
			int col = rand.nextInt(24);
			while(tempCell[row][col].getHasBomb()){
				row = rand.nextInt(24);
				col = rand.nextInt(24);
			}
			tempCell[row][col].setHasBomb(true);
			BombLocation newBombLoc = new BombLocation(row, col);
			bombLocations.add(newBombLoc);
			totalBombs--;
		}
	}
	/**
	 * Method that locates all of the neighboring bombs after they have been placed throughout the board and
	 * inits the cell's neighborbombcount
	 */
	private void findBombs(){
		Cell[][] tempCell = gameFrame.getGameBoardCell();
		for(int i = 0; i < tempCell.length; i++){
			for(int j = 0; j < tempCell[i].length; j++){
				int neighborBombs = checkForBombs(tempCell[i][j]);
				tempCell[i][j].setNeighboringBombCount(neighborBombs);
			}
		}
	}
	/**
	 * Method that is called when a cell without any neighboring bombs is clicked
	 * Displays the number of neighboring bombs for each neighbor
	 * @param temp
	 */
	private void showNeighbors(Cell temp){
		if(temp.getState() == CellState.CLICKED)return;
		if(temp.getState() == CellState.FLAGGED)return; //If a cell is flagged, don't show its contents
		temp.setColor(Color.GRAY);
		temp.setState(CellState.CLICKED);
		numberOfClickedCells++;

		ArrayList<Cell> tempCells = temp.getNeighbors();
		for(int i = 0; i < tempCells.size(); i++){
			if(tempCells.get(i).getNeighborBombCount() == 0)showNeighbors(tempCells.get(i));
			else {
				if(tempCells.get(i).getState() == CellState.CLICKED)continue;
				if(tempCells.get(i).getState() == CellState.FLAGGED)continue;//skips the cell if it is flagged and moves onto the next in the neighbor array
				tempCells.get(i).setColor(Color.GRAY);
				tempCells.get(i).setState(CellState.CLICKED);
				numberOfClickedCells++;
				tempCells.get(i).showNeighborBombCount();
			}
		}
	}
	/**
	 * Method that checks the neighboring Cells, including diagonals of the given cell
	 * @param temp
	 * @return
	 */
	private int checkForBombs(Cell temp){
		int bombCount = 0;
		ArrayList<Cell> tempCells = temp.getNeighbors();
		for(int i = 0; i < tempCells.size(); i++){
			if(tempCells.get(i).getHasBomb())bombCount++;
		}
		return bombCount;
	}
	/**
	 * Method that processes when the player wins or loses the game
	 */
	private void gameOver(Cell temp){
		gameFrame.stopTimer();
		Cell[][] tempCell = gameFrame.getGameBoardCell();
		String msg = "";
		if(playerLoses){
			for(int i = 0; i < bombLocations.size(); i++){
				int tempRow = bombLocations.get(i).row;
				int tempCol = bombLocations.get(i).col;
				tempCell[tempRow][tempCol].setImageLabel(bombIcon);
				if(tempCell[tempRow][tempCol].getState() == CellState.FLAGGED || tempCell[tempRow][tempCol].getState() == CellState.POSSIBLE)
					tempCell[tempRow][tempCol].setColor(Color.GREEN);
				else if(tempCell[tempRow][tempCol].getState() == CellState.UNMARKED)
					tempCell[tempRow][tempCol].setColor(Color.RED);
			}
			for(int i = 0; i < tempCell.length; i++){
				for(int j = 0; j < tempCell[i].length; j++){
					if(tempCell[i][j].getState() == CellState.FLAGGED || tempCell[i][j].getState() == CellState.POSSIBLE)
						if(!tempCell[i][j].getHasBomb())
							tempCell[i][j].setColor(Color.YELLOW);
				}
			}
			msg = "You Lose!\n";
		}
		else if(playerWins){
			for(int i = 0; i < tempCell.length; i++){
				for(int j = 0; j < tempCell[i].length; j++){
					if(tempCell[i][j].getHasBomb()){
						tempCell[i][j].setImageLabel(bombIcon);
						tempCell[i][j].setColor(Color.GREEN);
					}
				}
			}
			msg = "You Win!\n";
		}
		JOptionPane.showMessageDialog(gameFrame, msg + "It took you " + gameFrame.getTime() + " seconds\n"
				+ "Click the Smiley Face to restart the game", "Game Over", JOptionPane.INFORMATION_MESSAGE);
	}
	/**
	 * Method to process when the user left clicks a cell
	 * @param temp
	 */
	private void leftClicked(Cell temp){
		if(playerLoses || playerWins)return; //Making sure the player can't click any cells once the game has ended until they click the reset button
		if(temp.getState() == CellState.FLAGGED)return; //Making sure the player can't click any cells that are currently flagged

		if(firstClick){
			firstClick = false;
			gameFrame.startCountUp();
		}
		if(temp.getState() == CellState.POSSIBLE){
			temp.removeEverything();
		}
		if(temp.getHasBomb()){
			//Game Over
			//stop the timer
			playerLoses = true;
			gameOver(temp);
			return;
		}
		if(temp.getNeighborBombCount() == 0){
			//Open up the neighbors
			showNeighbors(temp);
		}
		else{
			//Display the number of neighboring bombs
			temp.setColor(Color.GRAY);
			temp.setState(CellState.CLICKED);
			numberOfClickedCells++;
			temp.showNeighborBombCount();
		}
		if(numberOfClickedCells == (Math.pow(rowAndColLength, 2)) - totalBombs){
			//stop the timer
			playerWins = true;
			gameOver(temp);
		}
	}
	/**
	 * Method to process when the user right clicks a cell
	 * @param temp
	 */
	public void rightClicked(Cell temp){
		if(playerLoses || playerWins)return;
		if(temp.getState() == CellState.UNMARKED){
			temp.setState(CellState.FLAGGED);
			temp.setImageLabel(flagIcon);
			gameFrame.decrBombsLeft();
		}
		else if(temp.getState() == CellState.FLAGGED){
			temp.setState(CellState.POSSIBLE);
			temp.setImageLabel(questionIcon);
			gameFrame.incrBombsLeft();
		}
		else if(temp.getState() == CellState.POSSIBLE){
			temp.setState(CellState.UNMARKED);
			temp.removeEverything();
		}
		else{
			return;
		}
	}
	/**
	 * The main method, creates an instance of the Game class to run the game
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				new Game();
			}});
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	/**
	 * When the mouse is left pressed, change the reset button icon to a surprised face
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() instanceof Cell){
			if(SwingUtilities.isLeftMouseButton(e)){
				gameFrame.setButtonSurprised();
			}
		}
	}

	/**
	 * Process the click after it has been released
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() instanceof Cell){
			Cell temp = (Cell)e.getSource();
			if(SwingUtilities.isLeftMouseButton(e)){
				leftClicked(temp);
				gameFrame.setButtonSmiley();
			}
			if(SwingUtilities.isRightMouseButton(e)){
				rightClicked(temp);
			}
		}
		else if(e.getSource() instanceof JButton){
			/* Reset The Game
			 * Reset Bomb Placement
			 * Reset Timer
			 * Clear Bomb Location ArrayList
			 */
			gameFrame.resetGameBoard(this); //Passing it the MouseListener
			gameFrame.resetScorePanel();
			bombLocations.clear();
			totalBombs = 100;
			placeBombs();
			findBombs();
			firstClick = true;
			playerLoses = false;
			playerWins = false;
			numberOfClickedCells = 0;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
