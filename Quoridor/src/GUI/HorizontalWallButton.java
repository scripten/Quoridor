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
public class HorizontalWallButton extends JButton {
	private int row;
	private int column;
	private boolean isInUse;
	
	public HorizontalWallButton(){
		super();
		row = -1;
		column = -1;
	}
	
	public HorizontalWallButton(String text, int columns, int rows){
		super(text);
		row = rows;
		column = columns;
	}
	public void setUsed (boolean used){
		isInUse = used;
	}
	public boolean getUsed(){
		return isInUse;
	}

}
