package GUI_test;

import static GUI.FirstWindow.MAIN_WINDOW_TITLE;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GUI.Client;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.ComponentSelector;
import com.objogate.wl.swing.driver.ComponentDriver;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JCheckBoxDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.driver.JMenuBarDriver;
import com.objogate.wl.swing.driver.JRadioButtonDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;

public class GameBoardTest {

	JFrameDriver driver;
	JMenuBarDriver menuBarDriver;
	AWTEventQueueProber prober;
	
	@Before
	public void setUp() throws Exception {
		Client.main(new String[0]);
		prober = new AWTEventQueueProber();
		driver = new JFrameDriver(new GesturePerformer(), prober, JFrameDriver.named(MAIN_WINDOW_TITLE), JFrameDriver.showingOnScreen());
	}
	
	@SuppressWarnings("unchecked")
    private JButtonDriver button(String name) {
        return new JButtonDriver(driver, JButton.class, ComponentDriver.named(name));
    }

    @SuppressWarnings("unchecked")
    private JLabelDriver label(String name) {
        return new JLabelDriver(driver, ComponentDriver.named(name));
    }
    
    private JCheckBoxDriver checkBox(String name){
		return new JCheckBoxDriver(driver, JCheckBox.class, ComponentDriver.named(name));

    }
    
    private JRadioButtonDriver radioButton(String name){
    	return new JRadioButtonDriver(driver, JRadioButton.class, ComponentDriver.named(name));
    }

	@After
	public void tearDown() throws Exception {
		driver.dispose();
	}
	
	@Test
	public void bringUpGameBoard(){
		JButtonDriver button = button("btnNewGame");
		button.click();
		driver = new JFrameDriver(new GesturePerformer(), new AWTEventQueueProber(), JFrameDriver.named("Quoridor"), JFrameDriver.showingOnScreen());
		driver.hasTitle("Quoridor");
	}
	
	@Test
	public void startAIGame(){
		JCheckBoxDriver checkbox = checkBox("aiCheckBox");
		checkbox.click();
		bringUpGameBoard();
		
	}
	
	@Test
	public void start4PlayerGame(){
		JRadioButtonDriver radiobtn = radioButton("4Player");
		radiobtn.isChecked();
		bringUpGameBoard();
	}
	
	@Test
	public void start2PlayerGame(){
		JRadioButtonDriver radiobtn = radioButton("2Player");
		radiobtn.click();
		radiobtn.isChecked();
		bringUpGameBoard();
	}
	
	
	
}
