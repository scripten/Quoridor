/*
 * Author: James Dubee
 * Filename: TreeNavClass.java
 * Date: 9/9/12
 * Purpose: To provide an easily navigate a tree representation.
 */

import java.util.ArrayList;
import java.util.NoSuchElementException;


interface TreeInterface {
	public void firstVertex();
	public Node getVertex();
	public boolean findVertex(Node node);
	public Node getEdge();
	public void nextVertex();
	
	public void firstEdge();
	public void nextEdge();
	public boolean endOfEdges();
}

class TreeClass implements TreeInterface {
	private ArrayList<Vertex> vertices;			// Tree representation
	private int vertexIndex;					// Vertex index
	private int edgeIndex;						// Edge index
	
	/**
	 * Pre: A tree representation must be passed.
	 * Post: The object will be initialized.
	 * @param verticesList	tree representation to navigate
	 */
	public TreeClass() {
		vertices = new ArrayList<Vertex>();
		vertexIndex = 0;
		edgeIndex = 0;
	}
	
	/**
	 * Pre: The name of the vertex must be passed.
	 * Post: The vertex will be added to the tree.
	 * @param name	Name of the vertex.
	 */
	public void addVertex(String name) {
		Vertex vertex = new Vertex();
		vertex.setName(name);
		vertices.add(vertex);
	}
	
	/**
	 * Pre: The name of the edge and its cost must be passed.
	 * Post: The edge will be added to the current vertex.
	 * @param name	Name of the edge.
	 * @param dist	Cost of the edge
	 */
	public void addEdge(String name, int dist) {
		// Ensure the vertex exists
		if (vertexIndex < vertices.size())
			vertices.get(vertexIndex).addEdge(name, dist);
		else
			throw new NoSuchElementException("Element does not exist."); 
	}
	
	/**
	 * Pre: The name of the vertex must be passed.
	 * Post: The index of the vertex will be returned.
	 * @param name	Name of vertex
	 * @return		Index of the vertex
	 */
	public int indexOfVertex(String name) {
		
		// Attempt to find the vertex
		for (int i = 0; i < vertices.size(); i++) {
			if (vertices.get(i).getName().equalsIgnoreCase(name)) 
				return i;
		}
		
		return -1;
	}
	
	/**
	 * Pre: Node.
	 * Post: The vertex index will be set to zero
	 */
	public void firstVertex() {
		vertexIndex = 0;
	}
	
	/**
	 * Pre: None.
	 * Post: The current vertex will be returned.
	 * @return	The current vertex.
	 * @throws NoSuchElementException	Thrown if the vertex index is beyond the end of the list.
	 */
	public Node getVertex() {
		// Determine if the index is beyond the list
		if (vertexIndex < vertices.size()) {
			Node node = new Node();
			node.setName(vertices.get(vertexIndex).getName());
			node.setCost(0);
			node.setParent(null);

			return node;
		} else
			throw new NoSuchElementException("Element does not exist.");
	}
	
	/**
	 * Pre: The vertex to find must be passed.
	 * Post: The current vertex will be set to the found vertex.
	 * @param node	Vertex to find.
	 * @return	Found flag
	 */
	public boolean findVertex(Node node) {
		// Iterate through each vertex
		for (int i = 0; i < vertices.size(); i++) {
			// Determine if the vertices are the same
			if (vertices.get(i).getName().equalsIgnoreCase(node.getName())) 
			{
				vertexIndex = i;
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Pre: None.
	 * Post: The current edge will be returned.
	 * @return	The current edge
	 * @throws NoSuchElementException	Thrown if the vertex or edge indices are beyond the end of the lists.
	 */
	public Node getEdge() throws NoSuchElementException {
		// Determine if the indices is beyond the list
		if (vertexIndex < vertices.size() && edgeIndex < vertices.get(vertexIndex).getEdges().size()) {
			Node node = new Node();
			node.setName(vertices.get(vertexIndex).getEdges().get(edgeIndex).getName());
			node.setCost(vertices.get(vertexIndex).getEdges().get(edgeIndex).getCost());
			node.setParent(null);
			return node;
		} else
			throw new NoSuchElementException("Element does not exist.");
	}
	
	/**
	 * Pre: None.
	 * Post: The vertex index will be incremented.
	 * @throws NoSuchElementException	Throw if the vertex index is beyond the end of the list.
	 */
	public void nextVertex() throws NoSuchElementException {
		// Determine if the index is beyond the list
		if (vertexIndex < vertices.size())
			vertexIndex++;
		else
			throw new NoSuchElementException("Element does not exist.");
	}
	
	/**
	 * Pre: None.
	 * Post: The edge index will be set to zero.
	 */
	public void firstEdge() {
		edgeIndex = 0;
	}
	
	
	/**
	 * Pre: None.
	 * Post: A value of true is returned if the vertex index is at the end of the list. Otherwise, false is returned.
	 * @return	End of list flag.
	 */
	public boolean endOfVertices() {
		return vertexIndex == vertices.size();
	}
	
	/**
	 * Pre: None.
	 * Post: The edge index will be incremented.
	 * @throws NoSuchElementException	Thrown if the vertex or edge indices are beyond the end of the lists. 
	 */
	public void nextEdge() throws NoSuchElementException {
		// Ensure the element exists
		if (vertexIndex < vertices.size() && edgeIndex < vertices.get(vertexIndex).getEdges().size()) 
			edgeIndex++;
		else
			throw new NoSuchElementException("Element does not exist.");
	}
	
	/**
	 * Pre: None.
	 * Post: A value of true is returned if the edge index is at the end of the list. Otherwise, false is returned.
	 * @return	End of list flag.
	 */
	public boolean endOfEdges() {
		return edgeIndex == vertices.get(vertexIndex).getEdges().size();
	}
	
	/**
	 * Pre: None.
	 * Post: The number of edges will be returned.
	 * @return	The number of edges.
	 */
	public int GetNumEdges() {
		int res = 0;
		
		// Get the number of edges
		for (int i = 0; i < vertices.size(); i++) 
			res = res + vertices.get(i).getEdges().size();
		
		return res;
	}
	
	/**
	 * Pre: None.
	 * Post: The number of nodes will be returned.
	 * @return	The number of nodes.
	 */
	public int GetNumNodes() {
		int res = 0;
		res = vertices.size() + GetNumEdges();
		return res;
	}
}