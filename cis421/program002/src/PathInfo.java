/*
 * Author: James Dubee
 * Filename: PathInfo.java
 * Date: 9/9/12
 * Purpose: Stores information about searched paths.
 */

import java.util.ArrayList;
import java.util.Stack;


interface PathInfoInterface {
	public void store(Node node);
	public int getCost();
	public ArrayList<Node> getPath();
}

public class PathInfo implements PathInfoInterface {
	boolean found = false;							// Found path flag
	ArrayList<Node> path = new ArrayList<Node>();	// Path taken
	int cost = 0;									// Total cost of path
	int numStatesExpanded = 0;						// Number of states expanded
	int numStatesSeen = 0;							// Number of states seen
	
	/**
	 * Pre: A node must be passed.
	 * Post: All the parent nodes of the node passed will be stored as the search path.
	 * @param node	Node to extract path from
	 */
	public void store(Node node) {
		Stack<Node> pathStack;		// Stack to reverse path
	
		// Ensure the node is instantiated
		if (node != null)
		{
			pathStack =  new Stack<Node>();
			
			path.clear();
			cost = 0;
			
			// Iterate through each parent of the node
			while (node.parent != null) {
				pathStack.push(node);
				cost = cost + node.getCost();
				node = node.parent;	
			}
			
			pathStack.push(node);
			cost = cost + node.getCost();
			
			// Store the path in an array
			while (!pathStack.isEmpty()) 
				path.add(pathStack.pop());
		}
	}
	
	/**
	 * Pre: Node.
	 * Post: The path found flag will be set to true.
	 */
	public void setFound() {
		found = true;
	}
	
	/**
	 * Pre: Node.
	 * Post: The path found flag will be returned
	 * @return	Path found flag
	 */
	public boolean getFound() {
		return found;
	}
	
	/**
	 * Pre: Node.
	 * Post: The total cost of the path will be returned.
	 * @return	The total cost of the path.
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Pre: None.
	 * Post: The search path will be returned.
	 * @return 	The search path.
	 */
	public ArrayList<Node> getPath() {
		return path;
	}
	
	/**
	 * Pre: The number of states must be passed.
	 * Post: The number of states will be set.
	 * @param num	The number of states
	 */
	public void setStatesExpanded(int num) {
		numStatesExpanded = num;
	}
	
	/**
	 * Pre: None.
	 * Post: The number of states will be returned.
	 * @return	The number of states.
	 */
	public int getStatesExpanded() {
		return numStatesExpanded;
	}
	
	/**
	 * Pre: The number of states must be passed.
	 * Post: The number of states will be set.
	 * @param num	The number of states
	 */
	public void setStatesSeen(int num) {
		numStatesSeen = num;
	}
	
	/**
	 * Pre: None.
	 * Post: The number of states will be returned.
	 * @return	The number of states.
	 */
	public int getStatesSeen() {
		return numStatesSeen;
	}
}