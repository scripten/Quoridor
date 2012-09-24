package GUI_test;

import static org.hamcrest.text.IsEqualIgnoringWhiteSpace.equalToIgnoringWhiteSpace;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static GUI.FirstWindow.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import GUI.Client;
import GUI.FirstWindow;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.ComponentSelector;
import com.objogate.wl.swing.driver.ComponentDriver;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.driver.JOptionPaneDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class FirstWindowTest {
	JFrameDriver driver;
	/**
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		Client.main(new String[0]);
		driver = new JFrameDriver(new GesturePerformer(), new AWTEventQueueProber(), JFrameDriver.named(MAIN_WINDOW_TITLE), JFrameDriver.showingOnScreen());
	}
	
	
	@SuppressWarnings("unchecked")
    private JButtonDriver button(String name) {
        return new JButtonDriver(driver, JButton.class, ComponentDriver.named(name));
    }

    @SuppressWarnings("unchecked")
    private JLabelDriver label(String name) {
        return new JLabelDriver(driver, ComponentDriver.named(name));
    }

	@After
	public void tearDown() throws Exception {
		driver.dispose();
	}

	@Test
	public void windowUpWithTitleAndLabel() {
		driver.hasTitle(MAIN_WINDOW_TITLE);
	}
	
	@Test
	public void windowContainsButtons() {
		
			String buttonName = "btnNewGame";
			JButtonDriver button = button(buttonName);
			button.hasText(equalTo(BUTTON_TEXT));	
	}
	
	@Test
	public void windowContainsLabel(){
		JLabelDriver label = label(LABEL_NAME);
		label.hasText(equalTo(INITIAL_MESSAGE));
	}
	
	@Test
	public void popupOnButtonPress(){
		JButtonDriver button = button("btnNewGame");
		button.click();
		 JOptionPaneDriver optionPane = new JOptionPaneDriver(driver, JOptionPane.class, JOptionPaneDriver.showingOnScreen());
		 optionPane.clickOK();
	}
	
	

}
