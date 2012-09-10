/*
 * Author: James Dubee
 * Filename: NodeComparator.java
 * Date: 9/9/12
 * Purpose: To provide a node comparator.
 */

import java.util.Comparator;

class NodeComparator implements Comparator<Node> 
{ 
	/**
	 * Pre: Nodes to compare must be passed.
	 * Post: The node's costs will be compared to determine which is lager.
	 * @param Node x	Node to compare
	 * @param Node x	Node to compare
	 * @return	Result of comparison
	 */
	 @Override
    public int compare(Node x, Node y) 
    {
		 // Determine which cost is greater
		 if (x.getCost() < y.getCost())
			 return -1; 
		 else if (x.getCost() > y.getCost()) 
			 return 1;
		 else
        		return 0;
    } 
} 