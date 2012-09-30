package GUI_test;

import static GUI.FirstWindow.BUTTON_TEXT;
import static GUI.FirstWindow.INITIAL_MESSAGE;
import static GUI.FirstWindow.LABEL_NAME;
import static GUI.FirstWindow.MAIN_WINDOW_TITLE;
import static com.objogate.wl.swing.probe.ComponentIdentity.selectorFor;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static com.objogate.wl.swing.matcher.ComponentMatchers.withButtonText;


import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GUI.Client;

import com.objogate.wl.swing.AWTEventQueueProber;
import com.objogate.wl.swing.driver.ComponentDriver;
import com.objogate.wl.swing.driver.JButtonDriver;
import com.objogate.wl.swing.driver.JFrameDriver;
import com.objogate.wl.swing.driver.JLabelDriver;
import com.objogate.wl.swing.driver.JMenuBarDriver;
import com.objogate.wl.swing.driver.JMenuDriver;
import com.objogate.wl.swing.driver.JMenuItemDriver;
import com.objogate.wl.swing.driver.JOptionPaneDriver;
import com.objogate.wl.swing.gesture.GesturePerformer;
import com.objogate.wl.swing.matcher.JMenuTextMatcher;
import com.objogate.wl.swing.matcher.ButtonTextMatcher;
import com.objogate.wl.swing.ComponentSelector;


public class FirstWindowTest {
	JFrameDriver driver;
	JMenuBarDriver menuBarDriver;
	AWTEventQueueProber prober;
	/**
	 * @wbp.parser.entryPoint
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		Client.main(new String[0]);
		prober = new AWTEventQueueProber();
		driver = new JFrameDriver(new GesturePerformer(), prober, JFrameDriver.named(MAIN_WINDOW_TITLE), JFrameDriver.showingOnScreen());
		menuBarDriver = new JMenuBarDriver(driver, JMenuBarDriver.showingOnScreen());

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
		driver = new JFrameDriver(new GesturePerformer(), new AWTEventQueueProber(), JFrameDriver.named("Quoridor"), JFrameDriver.showingOnScreen());
		driver.hasTitle("Quoridor");
	}
	
	@Test
	public void canFindMenuBar(){
		menuBarDriver.is(ComponentDriver.showingOnScreen());
	}
	
	@Test
    public void hasMenu() {
        menuBarDriver.has(new JMenuTextMatcher("File"));
    }
	
	 @Test
	    public void canFindAMenuByTitle() {
	        String menuTexts[] = {"File", "Help"};
	        for (String menuText : menuTexts) {
	            JMenuDriver menu = menuBarDriver.menu(new JMenuTextMatcher(menuText));
	            ComponentSelector<JMenu> selector = menu.component();
	            prober.check(selector);
	            assertNotNull("Can find component", selector.component());
	            menu.hasText(equalTo(menuText));
	        }
	    }
	 
	 @Test
	    public void canClickOnANamedMenu() {
	        JMenuDriver menu = menuBarDriver.menu(new JMenuTextMatcher("Help"));
	        ComponentSelector<JMenu> selector = menu.component();
	        prober.check(selector);
	        menu.leftClickOnComponent();
	        menu.isShowing();
	    }

	 @Test
	    public void canClickOnAMenuItemWithGivenText() throws Exception {
		 JMenuDriver menuDriver = menuBarDriver.menu(new JMenuTextMatcher("Help"));
	        menuDriver.leftClickOnComponent();
	        menuDriver.leftClickOn(withButtonText("Quoridor Wiki"));

	    }


}
