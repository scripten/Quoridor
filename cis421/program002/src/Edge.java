/*
 * Author: James Dubee
 * Filename: Edge.java
 * Date: 9/9/12
 * Purpose: To provide an Edge object for a tree.
 */

public class Edge {
	private String name;		// Name of edge
	private int cost;			// Cost of edge
	
	/**
	 * Pre: None.
	 * Post: Object will be initialized.
	 */
	public void child() {
		name = "";
		cost = 0;
	}

	/**
	 * Pre: Name of the node must be passed.
	 * Post: The name of the edge will be set.
	 * @param nodeName	Name of the node
	 */
	public void setName(String nodeName) {
		name = nodeName;
	}
	
	/**
	 * Pre: Node.
	 * Post: The name of the edge will be returned.
	 * @return	The name of the edge is returned.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Pre: A cost must be passed.
	 * Post: The cost will be set.
	 * @param costVal	Cost of the edge.
	 */
	public void setCost(int costVal) {
		cost = costVal;
	}
	
	/**
	 * Pre: Node.
	 * Post: The cost of the edge will be returned.
	 * @return	The cost of the edge.
	 */
	public int getCost() {
		return cost;
	}
}
