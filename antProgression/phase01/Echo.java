/** The main program for creating and running an EchoRunner
 * object. The EchoRunner handles all the interaction (though this
 * program handles command-line parameters). */
public class Echo {
  public static void main(String [] args) {
    if (args.length == 0) {
      EchoRunner er = new EchoRunner();
      er.run();
    }
  }
}
