package AI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Iterator;


import AI.Node;
import AI.StateGen;
import AI.NodeComparator;
import AI.StateHeuristic; 

public class Search {
	public static <T> Node<T> aStar(Node<T> start, GoalEval goal, StateGen stateGenerator, StateHeuristic heuristic) {
		Comparator<Node<T>> comparator = new NodeComparator(); 							// Comparator for frontier
 		PriorityQueue<Node<T>> frontier =  new PriorityQueue<Node<T>>(10, comparator); 	// Nodes to search
		
		ArrayList<Node<T>> explored = new ArrayList<Node<T>>();							// Searched nodes
		Node<T> parent;																	// Parent node
		Node<T> child = null;															// Child node

		parent = start;
		
		heuristic.setCurrentState(start.getState());
	
		start.setGCost(0);
		heuristic.generateHeurstic();
		start.setHCost(heuristic.getHeuristic());

		frontier.add(start);

		// Search each edge of each vertex until the goal is found
		do {
			parent = frontier.poll();
            
			// Determine if the parent is the goal state
			if (goal.test(parent.getState()))
				return parent;
		
			explored.add(parent);
			
			stateGenerator.setState(parent.getState());
			stateGenerator.firstState();

			// Iterate through each generated puzzle
			while (!stateGenerator.endOfStates()) {
				
				
				child = new Node<T>(null);
				
				child.setState((T) stateGenerator.getCurrentState());
				child.setParent(parent);
				
				heuristic.setCurrentState(child.getState());
				heuristic.generateHeurstic();
				
				child.setGCost(child.getParent().getGCost() + 1);
				child.setGCost(child.getGCost() + 1);
				child.setHCost(heuristic.getHeuristic());
				

				// Determine if the child should be added to the frontier or 
				// if it is in the frontier will a higher cost
				if (!explored.contains(child) && !frontier.contains(child)) {
					frontier.add(child);
				} else if (frontier.contains(child)) {
					Iterator<Node<T>> queueIt = frontier.iterator();			// Queue iterator
					
					// Iterate through each item in the frontier
					while (queueIt.hasNext()) {  
						Node<T> queueNode = queueIt.next();					// Node at queue iterator
						
						// Replace node if it is the same as the child but with larger cost
						if (queueNode.equals(child) && queueNode.getGCost() > child.getGCost()) {
							frontier.remove(queueNode);
							frontier.add(child);
							break;
						}
					}
				}
				
				stateGenerator.nextState();
			}
		} while (frontier.size() > 0);
		

		return null;
	}
}
