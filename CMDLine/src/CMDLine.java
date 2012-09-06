/**
 * Author: James Dubee
 * Date: 9/4/2012
 * Purpose: Displays the contents of files passed to the command-line.
 */

import java.io.FileNotFoundException;


public class CMDLine {

	/**
	 * @param args Filenames
	 */
	public static void main(String[] args) {
		String err = "";				// Error message
		DisplayFile display;			// Displays the contents of a file
		
		// Determine if command-line arguments exist
		if (args.length > 0) {
			display = new DisplayFile();
			
			// Iterate through each argument
			for (Integer i = 0; i < args.length; i++)
			{
				// Attempt to display the contents of the file
				try {
					display.displayFile(args[i]);
				} catch (FileNotFoundException e) {
					err = err + e.getMessage();
				}
			}
			
			// Display errors if they occurred
			if (!err.equals(""))
				System.out.format("Errors: \n%s", err);
		} else 
			System.out.println("Please provide command-line arguemnt(s).");
	}
}
