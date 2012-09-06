/* The first executable line of a Java source file must be the name of
 * the package containing the class UNLESS the class is in the
 * (nameless) default package. A package is typically a collection of
 * Java classes that somehow go together. Along with Java's naming
 * convention that a public class name must match the file name, it is
 * required that each level of package match the name of a directory
 * (or folder).
 *
 * Echo.java is in the main folder; the full name of the class is
 * main.Echo and the package name, provided in the package line, must
 * be main. */

package main;

/* EchoRunner is no longer in the same package as Echo; any class not
 * in the same package as this class must be imported before it can be
 * used. EchoRunner's full name is runner.EchoRunner (so what folder
 * is EchoRunner.java in?). That is the class name that must be
 * imported. */

import runner.EchoRunner;

/** The main program for creating and running an EchoRunner
 * object. The EchoRunner handles all the interaction (though this
 * program handles command-line parameters). */
public class Echo {
  public static void main(String [] args) {
    EchoRunner er = null;
    if (args.length == 0) {
      er = new EchoRunner();
    } else {
      er = new EchoRunner(args[0]);
    }
    er.run();
  }
}
