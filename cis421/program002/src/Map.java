/*
 * Author: James Dubee
 * Filename: Map.java
 * Date: 8/31/12
 */

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Map {
	final static String BREADTH_FIRST = "breadth-first";	// Breadth-first command
	final static String DEPTH_FIRST = "depth-first";		// Depth-first command
	final static String BEST_FIRST = "best-first";			// Best-first command
	
	public static void main(String[] args) {
		MapClass map = new MapClass();				// Groups neighboring cities
		Scanner console = new Scanner(System.in);	// Reads input form the console
		String filename;							// Input filename
		PathInfo pathInfo;							// Path information
		
		String startCity = "";
		String goalCity = "";
		String searchType = "";

		// Determine if the right number of arguments were supplied
		if (args.length == 4) {
			searchType = args[0];
			filename = args[1];
			startCity = args[2];
			goalCity = args[3];
			
			// Attempt to load cities and connections
			try {
				map.LoadCities(filename);
			
				//System.out.println(map.OutputCities());
				
				// Attempt to search
				try {

					// Perform specified search
					if (searchType.equalsIgnoreCase(BREADTH_FIRST)) {
						pathInfo = map.breadthFirstSearch(startCity, goalCity);
						DiplaySearchResult(searchType, pathInfo, map.GetNumConnections(), map.GetNumNodes());
					} else if (searchType.equalsIgnoreCase(DEPTH_FIRST)) {
						pathInfo = map.depthFirstSearch(startCity, goalCity);
						DiplaySearchResult(searchType, pathInfo, map.GetNumConnections(), map.GetNumNodes());
					} else if (searchType.equalsIgnoreCase(BEST_FIRST)) {
						pathInfo = map.bestFirstSearch(startCity, goalCity);
						DiplaySearchResult(searchType, pathInfo, map.GetNumConnections(), map.GetNumNodes());
					} else {
						System.out.format("\"%s\" is not a valid search. Valid searches are breadth-first, depth-first, \n" +
								"and best-first.\n", searchType);
					}
				} catch (NoSuchElementException e){
					System.out.println(e.getMessage());
				}
			} catch (FileNotFoundException e) {
				
				System.out.format("\"%s\" was not found.\n", filename);
			} catch (IOException e){
				System.out.println(e.getMessage() + " The program will now terminate.");
				System.exit(0);
			}
		}
		else
			System.out.println("Four command-line arguments are needed. Please provide name of the " +
					"search \n(breadth-first, depth-first, or best-first), name of the map file, and " +
					"the name of the \nstarting city and ending city.\n");

		console.close();
	}
	
	/**
	 * Pre: A search type, PathInfo object, number of nodes, and number of connections must be passed.
	 * Post: Information about the search will be displayed.
	 * @param search			Type of search
	 * @param pathInfo			Information about path
	 * @param numConnections	number of edges in tree
	 * @param numNodes			number of nodes in tree
	 */
	public static void DiplaySearchResult(String search, PathInfo pathInfo, int numConnections, int numNodes) {
		ArrayList<Node> path = null;		// Path traveled
		
		System.out.format("Search type: %s\n", search);
		
		// Determine if the path was found
		if (pathInfo.getFound()) {
			System.out.format("Search cost: %d km\n", pathInfo.getCost());
			System.out.println("Search path:");
			
			path = pathInfo.getPath();
			
			// Display the path
			for (int i = 0; i < path.size(); i++)
				System.out.format("\t%s\n", path.get(i).getName());
		} else 
			System.out.println("A path could not be found.");
		
		System.out.format("\nNumber of edges: %s\n", numConnections);
		System.out.format("Number of nodes: %s\n", numNodes);
		System.out.format("Number of states expanded: %s\n", pathInfo.getStatesExpanded());
		System.out.format("Number of states seen: %s\n", pathInfo.getStatesSeen());
	}
}
