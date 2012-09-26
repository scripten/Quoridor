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
public class SquareButton extends JButton {
	private int row;
	private int column;
	
	public SquareButton(){
		super();
		row = -1;
		column = -1;
	}
	
	public SquareButton(String text, int columns, int rows){
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

}
