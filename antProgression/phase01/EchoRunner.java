import java.util.Scanner;

/** A class that interacts with the user, reading lines from an input
 * source and echoing them to an output source. Initially, read from
 * System.in, write to System.out. */
public class EchoRunner {
  /** version string; useful for identifying what version
      of a program has a given bug */
  public final static String VERSION = "0.01a";
  /** Scanner connected to the keyboard (or input file) */
  private Scanner keyboard;
  /** prompt to show the user to let them know they can type */
  private String prompt;

  /** Default constructor setting default prompt and reading from
   * standard input. */
  public EchoRunner() {
    keyboard = new Scanner(System.in);
    prompt = "EchoRunner> ";
  }

  /** Actually do the work: print out a prompt and read a line. Note
   * that the println on the prompt is required because Ant wraps the
   * running program and only echos finished lines to the screen. */
  public void run() {
    if (!prompt.isEmpty())
      System.out.println(prompt);
    while (keyboard.hasNextLine()) {
      String aLine = keyboard.nextLine();
      
      System.out.println("\"" + aLine + "\"");
      if (!prompt.isEmpty())
        System.out.println(prompt);
    }
  }
}
