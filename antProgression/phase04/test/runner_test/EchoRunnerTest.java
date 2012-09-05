package runner_test;

// The class under test
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import runner.EchoRunner;

public class EchoRunnerTest {
  private EchoRunner echoRunner;

  @Test
  public void properVersionOfEchoRunnerBuilt() {
    echoRunner = new EchoRunner();
    assertThat(echoRunner.VERSION, is(equalTo("0.01d")));
  }

  @Test
  public void handlesSeveralLines() {
    String inputString = "this is a test of the \n" +
        "emergency broadcast system\n";
    
    String outputString = "\"this is a test of the \"\n" +
        "\"emergency broadcast system\"\n";
    
    ByteArrayInputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
    
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    echoRunner = new EchoRunner(printStream, inputStream);
    
    echoRunner.run();
    
    assertThat(outputStream.toString(), is(equalTo(outputString)));
  }
  
  @Test
  public void defaultConstructorSettings() {
    String inputString = "this is a test of the \n" +
        "emergency broadcast system\n";
    
    String prompt = "EchoRunner[0.01d]> \n"; 
    String outputString =
        prompt +
        "\"this is a test of the \"\n" +
        prompt +
        "\"emergency broadcast system\"\n" +
        prompt;
    
    ByteArrayInputStream inputStream = new ByteArrayInputStream(inputString.getBytes());
    
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outputStream);
    System.setIn(inputStream);
    System.setOut(printStream);
    echoRunner = new EchoRunner();
    
    echoRunner.run();
    
    assertThat(outputStream.toString(), is(equalTo(outputString)));
  }
}
