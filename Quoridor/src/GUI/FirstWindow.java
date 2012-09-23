package GUI;

import java.awt.Dimension;

import javax.swing.*;




public class FirstWindow extends JFrame{
	public final static String MAIN_WINDOW_TITLE = "FirstWindow";


	private JMenuBar menbar;

	public FirstWindow() {
		super();
		setName(MAIN_WINDOW_TITLE);
		setTitle(MAIN_WINDOW_TITLE);
		setSize(640, 480);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setVisible(true);
	}
	

}
