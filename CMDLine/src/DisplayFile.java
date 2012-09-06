/*
 * Author: James Dubee
 * Date: 9/6/2012
 * Purpose: To display the contents of a file via console.
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;


public class DisplayFile {

	/**
	 * Pre: A filename most be passed to the method.
	 * Post: The contents of a file will be displayed via console.
	 * @param filename name of the file to be displayed.
	 * @throws FileNotFoundException thrown if a file does not exist
	 */
	public void displayFile(String filename) throws FileNotFoundException {
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
