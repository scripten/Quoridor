/*
 * Author: James Dubee
 * Filename: Search.java
 * Date: 9/9/12
 * Purpose: Contains breadth first, depth first, and best first searches.
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;


public class Search {
	
	/**
	 * Pre: TreeNavClass, Node, GoalClass, and PathInfo objects must be passed to the method. 
	 * 	All the objects must be instantiated, and the Node object most contain the start state.
	 * Post: A PathInfo object containing the path and found information will be returned.
	 * @param treeNav	Tree navigator
	 * @param start		Start state
	 * @param goal		Goal state
	 * @param pathInfo	Stores information about the given path
	 * @return 			A PathInfo object is returned containing path information.
	 * @throws NoSuchElementException Thrown if an element is being accessed in treeNav that does not exist.
	 */
	public PathInfo breadthFirst(TreeClass tree, Node start, GoalClass goal, PathInfo pathInfo) throws NoSuchElementException {
		
		Queue<Node> frontier = new LinkedList<Node>();			// Nodes to searched
		ArrayList<Node> explored = new ArrayList<Node>();		// Searched nodes
		Node parent = null;										// Parent node
		Node child = null;										// child node
		int statesSeen = 1;										// Number of sates seen
		int statesExpanded = 0;									// Number of states expanded
		
		// Attempt to search
		try {
			tree.firstVertex();
			parent = start;
			
			// Determine if the parent is the goal state
			if (goal.test(parent))
			{
				pathInfo.setFound();
				pathInfo.store(parent);
				pathInfo.setStatesSeen(statesSeen);
				pathInfo.setStatesExpanded(statesExpanded);
				return pathInfo;
			}
			
			frontier.add(parent);
			
			// Search each edge of each vertex until the goal is found
			do {
				parent = frontier.poll();
	            
				// Set the current treeNav vertex to the parent node
				if (tree.findVertex(parent)) {
					explored.add(parent);
					statesExpanded++;
					
					tree.firstEdge();
					
					// Iterate through each edge of the current vertex
					while (!tree.endOfEdges()) {
						child = tree.getEdge();
						child.setParent(parent);
						
						// Determine if the child is the goal state, or if it needs to be in the frontier
						if (goal.test(child))
						{
							pathInfo.setFound();
							pathInfo.store(child);
							pathInfo.setStatesSeen(statesSeen);
							pathInfo.setStatesExpanded(statesExpanded);
							return pathInfo;
						} else if (!explored.contains(child) && !frontier.contains(child))
						{
							frontier.add(child);
							statesSeen++;
						}
						
						tree.nextEdge();
					}
				}
			} while (frontier.size() > 0);
			
			pathInfo.setStatesSeen(statesSeen);
			pathInfo.setStatesExpanded(statesExpanded);
			return pathInfo;
		} catch (NoSuchElementException e){
			throw new NoSuchElementException("Element does not exist.");
		}
	}
	
	/**
	 * Pre: TreeNavClass, Node, GoalClass, and PathInfo objects must be passed to the method. 
	 * 	All the objects must be instantiated, and the Node object most contain the start state.
	 * Post: A PathInfo object containing the path and found information will be returned.
	 * @param treeNav	Tree navigator
	 * @param start		Start state
	 * @param goal		Goal state
	 * @param pathInfo	Stores information about the given path
	 * @return 			A PathInfo object is returned containing path information.
	 * @throws NoSuchElementException Thrown if an element is being accessed in treeNav that does not exist.
	 */
	public PathInfo depthFirst(TreeClass tree, Node start, GoalClass goal, PathInfo pathInfo) throws NoSuchElementException {
		Stack<Node> frontier = new Stack<Node>();				// Nodes to search
		ArrayList<Node> explored = new ArrayList<Node>();		// Searched nodes
		Node parent = null;										// Parent node
		Node child = null;										// Child node
		int statesSeen = 1;										// Number of sates seen
		int statesExpanded = 0;									// Number of states expanded
		
		// Attempt to search
		try {
			tree.firstVertex();
			parent = start;
			
			// Determine if the parent is the goal state
			if (goal.test(parent))
			{
				pathInfo.setFound();
				pathInfo.store(parent);
				pathInfo.setStatesSeen(statesSeen);
				pathInfo.setStatesExpanded(statesExpanded);
				return pathInfo;
			}
			
			frontier.add(parent);
			
			// Search each edge of each vertex until the goal is found
			do {
				parent = frontier.pop();
	            
				// Set the current treeNav vertex to the parent node
				if (tree.findVertex(parent)) {
					explored.add(parent);
					statesExpanded++;
					
					tree.firstEdge();
					
					// Iterate through each edge of the current vertex
					while (!tree.endOfEdges()) {
						child = tree.getEdge();
						child.setParent(parent);
						
						// Determine if the child is the goal state, or if it needs to be in the frontier
						if (goal.test(child))
						{
							pathInfo.setFound();
							pathInfo.store(child);
							pathInfo.setStatesSeen(statesSeen);
							pathInfo.setStatesExpanded(statesExpanded);
							return pathInfo;
						} else if (!explored.contains(child) && !frontier.contains(child))
						{
							frontier.push(child);
							statesSeen++;
						}
						
						tree.nextEdge();
					}
				}
			} while (frontier.size() > 0);
			
			
			pathInfo.setStatesSeen(statesSeen);
			pathInfo.setStatesExpanded(statesExpanded);
			return pathInfo;
		} catch (NoSuchElementException e){
			throw new NoSuchElementException("Element does not exist.");
		}
	}
	
	/**
	 * Pre: TreeNavClass, Node, GoalClass, and PathInfo objects must be passed to the method. 
	 * 	All the objects must be instantiated, and the Node object most contain the start state.
	 * Post: A PathInfo object containing the path and found information will be returned.
	 * @param treeNav	Tree navigator
	 * @param start		Start state
	 * @param goal		Goal state
	 * @param pathInfo	Stores information about the given path
	 * @return 			A PathInfo object is returned containing path information.
	 * @throws NoSuchElementException Thrown if an element is being accessed in treeNav that does not exist.
	 */
	public PathInfo bestFirst(TreeClass tree, Node start, GoalClass goal, PathInfo pathInfo) throws NoSuchElementException {
		Comparator<Node> comparator = new NodeComparator(); 						// Comparator for frontier
		PriorityQueue<Node> frontier =  new PriorityQueue<Node>(10, comparator); 	// Nodes to search
		ArrayList<Node> explored = new ArrayList<Node>();							// Searched nodes
		Node parent = null;															// Parent node
		Node child = null;															// Child node
		int statesSeen = 1;															// Number of sates seen
		int statesExpanded = 0;														// Number of states expanded
		
		// Attempt to search
		try {
			tree.firstVertex();
			parent = start;

			frontier.add(parent);
			
			// Search each edge of each vertex until the goal is found
			do {
				// Throw exception if frontier is empty
				if (frontier.isEmpty())
				{
					pathInfo.setStatesSeen(statesSeen);
					pathInfo.setStatesExpanded(statesExpanded);
					return pathInfo;
				}
				
				parent = frontier.poll();
				
				// Determine if the parent is the goal state
				if (goal.test(parent))
				{
					pathInfo.setFound();
					pathInfo.store(parent);
					pathInfo.setStatesSeen(statesSeen);
					pathInfo.setStatesExpanded(statesExpanded);
					return pathInfo;
				}  
					
				// Set the current treeNav vertex to the parent node
				if (tree.findVertex(parent)) {
					explored.add(parent);
					statesExpanded++;
					
					tree.firstEdge();
					
					// Iterate through each edge of the current vertex
					while (!tree.endOfEdges()) {
						child = tree.getEdge();
						child.setParent(parent);
						
						// Determine if the child needs to be added to the frontier or if the existing child needs to be replaced
						if (!explored.contains(child) && !frontier.contains(child))
						{
							frontier.add(child);
							statesSeen++;
						}
						else if (frontier.contains(child)) {
							Iterator<Node> it = frontier.iterator();
							
							// Iterate through each item in the frontier
							while (it.hasNext()) {  
								Node n = it.next();
								
								// Replace node if it is the same as the child but with larger cost
								if (n.equals(child)) {
									frontier.remove(n);
									frontier.add(child);
								
									break;
								}
							}
						}
						
						tree.nextEdge();
					}
				}
			} while (frontier.size() > 0);
			
			pathInfo.setStatesSeen(statesSeen);
			pathInfo.setStatesExpanded(statesExpanded);
			return pathInfo;
		} catch (NoSuchElementException e){
			throw new NoSuchElementException("Element does not exist.");
		}
	}
	
	
}
