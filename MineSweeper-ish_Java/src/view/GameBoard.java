package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author Jack Thomas
 * @version 8.0
 *
 */
public class GameBoard extends JPanel{
	/**
	 * An enum to declare what state the cell is in, Flagged, Possible Bomb, Unmarked, or clicked
	 * @author jack
	 *
	 */
	public enum CellState{
		FLAGGED,
		POSSIBLE,
		UNMARKED,
		CLICKED;
	}
	/**
	 * The Cell class is what comprises the game board object
	 * Each cell has a state, neighboring bomb count, image, and array of neighbor cells
	 * @author jack
	 *
	 */
	public class Cell extends JPanel{
		/**
		 * A State variable storing the current state of the cell
		 */
		private CellState cellState;
		/**
		 * An int storing the number of neighboring bombs
		 */
		private int neighboringBombCount;
		/**
		 * A boolean storing whether or not this cell has a bomb or not
		 */
		private boolean hasBomb;
		/**
		 * A JLabel that will hold the bomb
		 */
		private JLabel imageLabel;
		/**
		 * A JLabel that holds the int for the neighboring bomb count
		 */
		private JLabel neighboringBombsLabel;
		/**
		 * An int containing the cell's row position on the game board
		 */
		private int rowPosition;
		/**
		 * An int containing the cell's col position on the game board
		 */
		private int colPosition;
		/**
		 * An array list of cells that contains this cell's neighbor cells
		 */
		private ArrayList<Cell> neighbors = new ArrayList<Cell>();

		/**
		 * The constructor for the Inner class of Cell
		 */
		public Cell(){
			this.setBackground((Color.GRAY).brighter());
			cellState = CellState.UNMARKED;
			hasBomb = false;
		}
		/**
		 * Method that returns the number of neighboring Bombs
		 * @return neighboringBombCount
		 */
		public int getNeighborBombCount(){
			return neighboringBombCount;
		}
		/**
		 * Method that sets whether or not the cell has a bomb
		 * @param hasBomb
		 */
		public void setHasBomb(boolean hasBomb){
			this.hasBomb = hasBomb;
		}
		/**
		 * Method that returns the bomb status of the cell
		 * @return hasBomb
		 */
		public boolean getHasBomb(){
			return hasBomb;
		}
		/**
		 * Method that returns the state of the cell
		 * @return
		 */
		public CellState getState(){
			return cellState;
		}
		/**
		 * Method that sets the current JLabel Image
		 * @param icon
		 */
		public void setImageLabel(ImageIcon icon){
			this.removeAll();
			icon = new ImageIcon(icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
			imageLabel = new JLabel(icon);
			this.add(imageLabel);
			this.repaint();
			this.validate();
		}
		/**
		 * Method that removes everything from the current Cell
		 */
		public void removeEverything(){
			this.removeAll();
			this.repaint();
			this.validate();
		}
		/**
		 * Method that sets the state of the cell
		 * @param cellState
		 */
		public void setState(CellState cellState){
			this.cellState = cellState;
		}
		/**
		 * Method that sets the background color of the cell
		 * @param c
		 */
		public void setColor(Color c){
			this.setBackground(c);
			this.update(this.getGraphics());
		}
		/**
		 * Method that will display the neighboring bomb Count
		 */
		public void showNeighborBombCount(){
			this.removeAll();
			this.add(neighboringBombsLabel);
			this.repaint();
			this.validate();
		}
		/**
		 * Method that sets the number for the neighboring Bomb Count
		 * @param neighborBombCount
		 */
		public void setNeighboringBombCount(int neighborBombCount){
			this.neighboringBombCount = neighborBombCount;
			neighboringBombsLabel = new JLabel(Integer.toString(neighboringBombCount));
			Font myFont = new Font(neighboringBombsLabel.getFont().getFontName(), Font.BOLD, this.getHeight());
			neighboringBombsLabel.setFont(myFont);
			neighboringBombsLabel.setHorizontalAlignment(JLabel.CENTER);
			if(neighboringBombCount == 1)
				neighboringBombsLabel.setForeground(Color.BLUE);
			else if(neighboringBombCount == 2)
				neighboringBombsLabel.setForeground(Color.GREEN);
			else if(neighboringBombCount == 3)
				neighboringBombsLabel.setForeground(Color.RED);
			else if(neighboringBombCount == 4)
				neighboringBombsLabel.setForeground(Color.PINK);
			else if(neighboringBombCount == 5)
				neighboringBombsLabel.setForeground(Color.ORANGE);
			else if(neighboringBombCount == 6)
				neighboringBombsLabel.setForeground(Color.CYAN);
			else if(neighboringBombCount == 7)
				neighboringBombsLabel.setForeground(Color.BLACK);
			else if(neighboringBombCount == 8)
				neighboringBombsLabel.setForeground(Color.MAGENTA);
		}
		/**
		 * Method that returns the cell's row position
		 * @return
		 */
		public int getRow(){
			return rowPosition;
		}
		/**
		 * Method that returns the cell's col position
		 * @return
		 */
		public int getCol(){
			return colPosition;
		}
		/**
		 * Method that adds a new cell to the neighbor array list
		 * @param newNeighbor
		 */
		public void addNeighbor(Cell newNeighbor){
			neighbors.add(newNeighbor);
		}
		/**
		 * Method that returns the cell's array list of  neighbors
		 * @return
		 */
		public ArrayList<Cell> getNeighbors(){
			return neighbors;
		}
	}


	/**
	 * A 2D array of Cell class instances
	 */
	private Cell[][] cell;

	/**
	 * GameBoard class constructor, initializes GameBoard variables
	 * @param size
	 */
	public GameBoard(int size, MouseListener ml){
		this.setBackground(Color.BLACK);
		this.setPreferredSize(new Dimension(500,500));
		this.setLayout(new GridLayout(size, size, 2, 2));
		cell = new Cell[size][size];

		//Adding each cell of the 2D array to the GameBoard JPanel
		for(int row = 0; row < cell.length; row++){
			for(int col = 0; col < cell[row].length; col++){
				cell[row][col] = new Cell();
				cell[row][col].addMouseListener(ml);
				cell[row][col].rowPosition = row;
				cell[row][col].colPosition = col;
				cell[row][col].setLayout(new BorderLayout());
				this.add(cell[row][col]);
			}
		}
		setCellNeighbors();
	}
	/**
	 * A method that removes all of the cells and re-adds new ones to reset the game
	 * @param ml
	 */
	public void resetGameBoard(MouseListener ml){
		this.removeAll();
		for(int row = 0; row < cell.length; row++){
			for(int col = 0; col < cell[row].length; col++){
				cell[row][col] = new Cell();
				cell[row][col].addMouseListener(ml);
				cell[row][col].rowPosition = row;
				cell[row][col].colPosition = col;
				cell[row][col].setLayout(new BorderLayout());
				this.add(cell[row][col]);
			}
		}
		setCellNeighbors();
		this.paint(this.getGraphics());
		this.validate();
		this.update(this.getGraphics());
	}
	/**
	 * Method that returns the 2D cell Array
	 * @return
	 */
	public Cell[][] getCells(){
		return cell;
	}
	/**
	 * Method that adds the 8 neighboring cells to the cell's neighbor array list
	 */
	private void setCellNeighbors(){
		for(int i = 0; i < cell.length; i++){
			for(int j = 0; j < cell[i].length; j++){
				try{
					cell[i][j].addNeighbor(cell[i - 1][j - 1]);
				}
				catch(ArrayIndexOutOfBoundsException e){

				}
				try{
					cell[i][j].addNeighbor(cell[i - 1][j]);
				}
				catch(ArrayIndexOutOfBoundsException e){

				}
				try{
					cell[i][j].addNeighbor(cell[i - 1][j + 1]);
				}
				catch(ArrayIndexOutOfBoundsException e){

				}
				try{
					cell[i][j].addNeighbor(cell[i][j - 1]);
				}
				catch(ArrayIndexOutOfBoundsException e){

				}
				try{
					cell[i][j].addNeighbor(cell[i][j + 1]);
				}
				catch(ArrayIndexOutOfBoundsException e){

				}
				try{
					cell[i][j].addNeighbor(cell[i + 1][j - 1]);
				}
				catch(ArrayIndexOutOfBoundsException e){

				}
				try{
					cell[i][j].addNeighbor(cell[i + 1][j]);
				}
				catch(ArrayIndexOutOfBoundsException e){

				}
				try{
					cell[i][j].addNeighbor(cell[i + 1][j + 1]);
				}
				catch(ArrayIndexOutOfBoundsException e){

				}
			}
		}
	}
}
