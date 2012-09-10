/*
 * Author: James Dubee
 * Filename: Node.java
 * Date: 9/9/12
 * Purpose: To provide a node object for a tree.
 */

public class Node {
	String name;		// Name of the node
	Node parent;		// Parent of the node
	int cost;			// Cost of the node
	
	/**
	 * Pre: None.
	 * Post: Object will be initialized.
	 */
	public Node() {
		name = "";
		parent = null;
	}
	
	/**
	 * Pre: Node.
	 * Post: returns the name of the node.
	 * @return	The name of the node
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Pre: A name must be passed.
	 * Post: The node's name will be changed.
	 * @param nodeName	Name of the node
	 */
	public void setName(String nodeName) {
		name = nodeName;
	}
	
	/**
	 * Pre: Node.
	 * Post: The parent of the node will be returned.
	 * @return	The parent of the node
	 */
	public Node getParent() {
		return parent;
	}
	
	/**
	 * Pre: The parent of the node must be passed.
	 * Post: The parent of the node will be set.
	 * @param nodeParent	The parent of the node.
	 */
	public void setParent(Node nodeParent) {
		parent = nodeParent;
	}
	
	/**
	 * Pre: Node.
	 * Post: The cost of the node will be set.
	 * @return The cost of the node
	 */
	public int getCost() {
		return cost;
	}
	
	/**
	 * Pre: A cost must be passed.
	 * Post: The cost will be set.
	 * @param costVal	The cost value
	 */
	public void setCost(int costVal) {
		cost = costVal;
	}
	
	/**
	 * Pre: A Node must be passed.
	 * Post: A value of true is returned if the nodes are equal. Otherwise, false is returned.
	 * @param obj	Node to compare to
	 * @return	Denotes whether the nodes are equal or not  
	 */
	@Override 
	public boolean equals(Object obj) { 
		// Determine if the nodes are equal
		if (obj == this) 
            return true;
        else if (obj == null || obj.getClass() != this.getClass()) 
            return false;
        else {
        	Node node = (Node)obj;
        	return (name.equalsIgnoreCase(node.getName()) /*&& cost == node.getCost()*/);
        }
	} 


}
