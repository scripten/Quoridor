
package AI;

import GameObjects.Board;

public class Node<T> {
	Node<T> parent;				// Parent of the node
	int cost;					// Total cost
	int g;						// Step cost
	int h;						// Heuristic cost
	
	private T state;
	
	
	/**
	 * Pre: None.
	 * Post: Object will be initialized.
	 */
	public Node(T t) {
		state = t;
	}
	
	public void setState(T t) {
		state = t;
	}
	
	/**
	 * Pre: Node.
	 * Post: returns the node's state.
	 * @return	The node's state
	 */
	public T getState() {
		return state;
	}
	
	/**
	 * Pre: Node.
	 * Post: The parent of the node will be returned.
	 * @return	The parent of the node
	 */
	public Node<T> getParent() {
		return parent;
	}
	
	/**
	 * Pre: The parent of the node must be passed.
	 * Post: The parent of the node will be set.
	 * @param parent2	The parent of the node.
	 */
	public void setParent(Node<T> parent2) {
		parent = parent2;
	}
	
	/**
	 * Pre: Node.
	 * Post: The cost of the node will be set.
	 * @return The cost of the node
	 */
	public int getCost() {
		return g + h;
	}
	
	/**
	 * Pre: A cost must be passed.
	 * Post: The cost will be set.
	 * @param costVal	The cost value
	 */
	public void setGCost(int gVal) {
		g = gVal;
	}
	
	/**
	 * Pre: Node.
	 * Post: The cost of the node will be set.
	 * @return The cost of the node
	 */
	public int getGCost() {
		return g;
	}
	
	/**
	 * Pre: A cost must be passed.
	 * Post: The cost will be set.
	 * @param costVal	The cost value
	 */
	public void setHCost(int gVal) {
		h = gVal;
	}
	
	/**
	 * Pre: Node.
	 * Post: The cost of the node will be set.
	 * @return The cost of the node
	 */
	public int getHCost() {
		return h;
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
        	Node<T> node = (Node<T>)obj;
        	return this.equals(node);
        }
	}

}
