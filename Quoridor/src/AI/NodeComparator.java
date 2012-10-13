/**
 * @author James Dubee
 * Purpose: To provide a node comparator.
 */

package AI;

import java.util.Comparator;
import AI.Node;

class NodeComparator<T> implements Comparator<Node<T>> 
{ 
	/**
	 * Pre: Nodes to compare must be passed.
	 * Post: The node's costs will be compared to determine which is lager.
	 * @param Node x	Node to compare
	 * @param Node x	Node to compare
	 * @return	Result of comparison
	 */
	 @Override
    public int compare(Node<T> firstNode, Node<T> secondNode) 
    {
		 // Determine which cost is greater
		 if (firstNode.getCost() < secondNode.getCost())
			 return -1; 
		 else if (firstNode.getCost() > secondNode.getCost()) 
			 return 1;
		 else
        	return 0;
    } 
} 