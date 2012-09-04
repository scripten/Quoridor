/**
 * Author: James Dubee
 * Date: 9/4/2012
 * Purpose: Displays the contents of files passed by the command-line.
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class CMDLine {

	/**
	 * @param args Filenames
	 */
	public static void main(String[] args) {
		String err = "";
		
		// Determine if command-line arguments exist
		if (args.length > 0) {
			
			// Iterate through each argument
			for (Integer i = 0; i < args.length; i++)
			{
				// Attempt to display the contents of the file
				try {
					displayFile(args[i]);
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
	
	/**
	 * Pre: A filename most be passed to the method.
	 * Post: The contents of a file will be displayed via console.
	 * @param filename name of the file to be displayed.
	 * @throws FileNotFoundException thrown if a file does not exist
	 */
	public static void displayFile(String filename) throws FileNotFoundException {
		FileReader reader;		// Reads file input
		Scanner input;			// Parsed file input
		
		// Attempt to open the file
		try {
			reader = new FileReader(filename);
			input = new Scanner(reader);
			
			System.out.format("Contents of \"%s\":\n", filename);
			
			// Read each line from the file
			while (input.hasNextLine()) 
				System.out.println(input.nextLine());
			
			input.close();
			
			System.out.println();
			
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(String.format("\"%s\" was not found.\n", filename));
		}
	}
}
