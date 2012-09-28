/**
 * 
 */
package GUI;

import javax.swing.JButton;

/**
 * A class for the squares that extends JButton, this allows us to keep track of
 * the buttons row and column positions.
 * 
 * @author Nick
 *
 */
public class VerticalWallButton extends JButton {
	private int row;
	private int column;
	private boolean isInUse;
	
	public VerticalWallButton(){
		super();
		row = -1;
		column = -1;
	}
	
	public VerticalWallButton(String text, int columns, int rows){
		super(text);
		row = rows;
		column = columns;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getColumn(){
		return column;
	}
	
	public void setUsed (boolean used){
		isInUse = used;
	}
	
	public boolean getUsed(){
		return isInUse;
	}

}
