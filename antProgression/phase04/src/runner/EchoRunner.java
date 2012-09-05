/* runner.EchoRunner is the full name of this class; it is in the
 * runner package. That means the code is below the src directory in
 * runner/EchoRunner.java */
package runner;

/* an import statement takes the fully-qualified name of the class to
 * import; the classpath must include the proper folder to start
 * searching for the named class */
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * A class that interacts with the user, reading lines from an input source and
 * echoing them to an output source. Initially, read from System.in, write to
 * System.out.
 */
public class EchoRunner {
  /**
   * version string; useful for identifying what version of a program has a
   * given bug
   */
  public final static String VERSION = "0.01d";

  /** Scanner connected to the keyboard (or input file) */
  private Scanner keyboard;

  /** prompt to show the user to let them know they can type */
  private String prompt;

  /** PrintStream for the output (nominally the screen) */
  PrintStream screen;

  /**
   * Default constructor setting default prompt, writing to standard output and
   * reading from standard input.
   */
  public EchoRunner() {
    this(System.in);
    prompt = "EchoRunner[" + VERSION + "]> ";
  }

  /**
   * Convenience constructor for redirecting just the input. No prompt is set.
   * 
   * @param keys where the input comes from
   */
  public EchoRunner(InputStream keys) {
    this(System.out, keys);
  }

  /**
   * Attach screen and keyboard to the given "files". The streams can be
   * standard output/input or, alternatively, can be attached to files. Exposing
   * the streams makes this class easier to test: we can inject streams of our
   * choosing such as in-memory streams so that
   * 
   * @param screen
   *          stream to which output is printed
   * @param keys
   *          where input is read from
   */
  public EchoRunner(PrintStream screen, InputStream keys) {
    this.screen = screen;
    this.keyboard = new Scanner(keys);
    this.prompt = "";
  }

  /**
   * Determine if the prompt is to be hidden (if it is empty) or not.
   * 
   * @return true if prompt should be shown, false otherwise
   */
  private boolean shouldShowPrompt() {
    return !prompt.isEmpty();
  }

  /**
   * Display the prompt (on the "screen") if it is to be shown.
   */
  private void showPromptIfNecessary() {
    if (shouldShowPrompt())
      screen.println(prompt);
  }

  /**
   * Actually do the work: print out a prompt and read a line. Note that the
   * println on the prompt is required because Ant wraps the running program and
   * only echos finished lines to the screen.
   */
  public void run() {
    showPromptIfNecessary();
    while (keyboard.hasNextLine()) {
      String aLine = keyboard.nextLine();

      screen.println("\"" + aLine + "\"");
      showPromptIfNecessary();
    }
  }
}
