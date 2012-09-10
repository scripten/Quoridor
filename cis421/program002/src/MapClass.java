/*
 * Author: James Dubee
 * Filename: MapClass.java
 * Date: 8/31/12
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;


public class MapClass {
	final int MAX_CHARS_PER_LINE = 70;	// Maximum characters per output line
	TreeClass cities = null;			// 
	
	/**
	 * Pre: None.
	 * Post: Object will be initialized.
	 */
	public MapClass() {
		cities = new TreeClass();
	}
	
	/**
	 * Pre: A state and goal state must be passed.
	 * @param start	Start state
	 * @param goal	Goal state
	 * @return	Path information
	 * @throws NoSuchElementException	Thrown if element does not exist in tree
	 */
	public PathInfo breadthFirstSearch(String start, String goal) throws NoSuchElementException {
		Search search = new Search();	// For searching
		Node node = new Node();			// Start state

		node.setName(goal);
		node.setCost(0);
		
		// Ensure goal state exists
		if (cities.findVertex(node)) {
			node.setName(start);
			node.setCost(0);
		
			// Ensure start state exists
			if (cities.findVertex(node)) {
				
				// Attempt to search
				try {
					return search.breadthFirst(cities, node, new GoalClass(goal), new PathInfo());
				} catch (NoSuchElementException e){
					throw new NoSuchElementException(e.getMessage());
				}
			} else
				throw new NoSuchElementException(String.format("Start state \"%s\" was not found.\n", start));
		} else
			throw new NoSuchElementException(String.format("Goal state \"%s\" was not found.\n", goal));
	}
	
	/**
	 * Pre: A state and goal state must be passed.
	 * @param start	Start state
	 * @param goal	Goal state
	 * @return	Path information
	 * @throws NoSuchElementException	Thrown if element does not exist in tree
	 */
	public PathInfo depthFirstSearch(String start, String goal) throws NoSuchElementException {
		Search search = new Search();	// For searching
		Node node = new Node();			// Start state
		
		node.setName(goal);
		node.setCost(0);
		
		// Ensure goal state exists
		if (cities.findVertex(node)) {
			node.setName(start);
			node.setCost(0);
			
			// Ensure start state exists
			if (cities.findVertex(node)) {
				
				// Attempt to search
				try {
					return search.depthFirst(cities, node, new GoalClass(goal), new PathInfo());
				} catch (NoSuchElementException e){
					throw new NoSuchElementException(e.getMessage());
				}
			} else
				throw new NoSuchElementException(String.format("Start state \"%s\" was not found.\n", start));
		} else
			throw new NoSuchElementException(String.format("Goal state \"%s\" was not found.\n", goal));
	}
	
	/**
	 * Pre: A state and goal state must be passed.
	 * @param start	Start state
	 * @param goal	Goal state
	 * @return	Path information
	 * @throws NoSuchElementException	Thrown if element does not exist in tree
	 */
	public PathInfo bestFirstSearch(String start, String goal) throws NoSuchElementException {
		Search search = new Search();	// For searching
		Node node = new Node();			// Start state

		node.setName(goal);
		node.setCost(0);
		
		// Ensure goal state exists
		if (cities.findVertex(node)) {
			node.setName(start);
			node.setCost(0);
			
			// Ensure start state exists
			if (cities.findVertex(node)) {
				
				// Attempt to search
				try {
					return search.bestFirst(cities, node, new GoalClass(goal), new PathInfo());
				} catch (NoSuchElementException e){
					throw new NoSuchElementException(e.getMessage());
				}
			} else
				throw new NoSuchElementException(String.format("Start state \"%s\" was not found.\n", start));
		} else
			throw new NoSuchElementException(String.format("Goal state \"%s\" was not found.\n", goal));
		}
	
	/**
	 * Pre: None.
	 * Post: The number of connections is returned.
	 * @return	The number of connections.
	 */
	public int GetNumConnections() {
		return cities.GetNumEdges();
	}
	
	/**
	 * Pre: None.
	 * Post: The number of nodes will be returned.
	 * @return	The number of nodes.
	 */
	public int GetNumNodes() {
		return cities.GetNumNodes();
	}
	
	/**
	 * Pre: A filename must be passed to the method.
	 * Post: Cities read from the file will be stored in the cities list, and
	 * connections will be stored in the connections array.
	 * @param filename File name cities are read from.
	 * @throws IOException is thrown if a duplicate city is found. 
	 * 	FileNotFoundException is thrown if the input file is not found. 
	 */
	public void LoadCities(String filename) throws IOException {
		File fileExists;					// Determines if file exists
		FileReader reader;					// Reads file input
		Scanner fileInput = null;			// Parses file input
		Scanner parseConnections = null;	// Parses connection data
		boolean readCities = false;			// Flag denoting if all cities were read
		String input = "";					// A line of file input
		String city1;						// First city in connection
		String city2;						// Second city in connection
		Integer dist;						// Distance between connections
		Integer numLines = 0;				// Current line		

		fileExists = new File(filename);
		
		// Determine if the input file exists
		if (fileExists.exists()) {
			reader = new FileReader(filename);
			fileInput = new Scanner(reader);
			
			
			// Read each like from the input file
			while (fileInput.hasNextLine()) {
				input = fileInput.nextLine();
				numLines++;
				
				// Determine if all cities have been read
				if (!readCities) {
					// Determine if input is a blank line
					if (!input.equals("")) 	
					{
						// Determine if the city is a duplicate
						if (cities.indexOfVertex(input) > -1)
						{
							fileInput.close();
							throw new IOException(String.format("Duplicate city \"%s\" detected on line %d.", input, numLines));
						}
						else
							cities.addVertex(input);
					}
					else {
						readCities = true;
					}
				} else {
					// Determine if input is a blank line
					if (!input.equals(""))
					{
						parseConnections = new Scanner(input);
						city1 = parseConnections.next();
						city2 = parseConnections.next();
						dist = Integer.parseInt(parseConnections.next());
						parseConnections.close();
						
						Node cityNode1 = new Node();
						cityNode1.setName(city1);
						
						if (cities.findVertex(cityNode1)) {
							cities.addEdge(city2, dist);
							
							Node cityNode2 = new Node();
							cityNode2.setName(city2);
							
							if (cities.findVertex(cityNode2)) {
								cities.addEdge(city1, dist);
							} else {
								fileInput.close();
								throw new IOException(String.format("Unknown city \"%s\" in connections on line %d.", city2, numLines));
							}
						} else {
							fileInput.close();
							throw new IOException(String.format("Unknown city \"%s\" in connections on line %d.", city1, numLines));
						
						}
					}
				}
			}
			
			fileInput.close();
		} else 
			throw new FileNotFoundException(String.format("\"%s\" was not found.", filename));
	}
	
	/**
	 * Pre: LoadCities() must initialize cities and connections.
	 * Post: All neighboring connections are grouped together.
	 * @return A string displaying neighboring cities is returned.
	 */
	public String OutputCities() {
		String res = "";					// Returning value
		String neighbors = "";				// Connecting cities
		Boolean comma = false;				// Flag for commas
		Integer charCount = 0;				// Characters on line
		
		// Iterate through all cities
		cities.firstVertex();
		while (!cities.endOfVertices())
		{
			charCount = cities.getVertex().getName().length() + 2;
			
			cities.firstEdge();
			while (!cities.endOfEdges()) {
				// Determine if neighboring cities have been found
				if (neighbors == "")
					neighbors = String.format("%s: ", cities.getVertex().getName());
				
				// Determine if a comma should be output
				if (comma) {
					// Determine if a new line is needed
					if (charCount + cities.getEdge().getName().length() + 2 > MAX_CHARS_PER_LINE) {
						neighbors = neighbors + String.format(",\n\t%s", cities.getEdge().getName());
						charCount = cities.getEdge().getName().length() + 5;
					} else {
						neighbors = neighbors + String.format(", %s", cities.getEdge().getName());
						charCount = charCount + cities.getEdge().getName().length() + 2;
					}
				} else {
					neighbors = neighbors + cities.getEdge().getName();
					charCount = charCount + cities.getEdge().getName().length();
				}
				
				comma = true;
				
				cities.nextEdge();
			}
			
			// Determine if neighboring cities have been found
			if (neighbors != "") {
				if (res == "")
					res =  neighbors;
				else
					res = res + "\n" + neighbors;
				neighbors = "";
				comma = false;
			}
						
			cities.nextVertex();
		}
		
		// Determine if connections were loaded.
		if (res == "")
			res = "No connections were loaded.";
	
			return res;
	}
	
}
		
	 

