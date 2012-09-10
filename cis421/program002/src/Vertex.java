/*
 * Author: James Dubee
 * Filename: Vertex.java
 * Date: 9/9/12
 * Purpose: Used to represent a tree.
 */

import java.util.ArrayList;

public class Vertex {
	private String name;			// Name of vertex
	private ArrayList<Edge> edges;	// Edges of vertex
	
	/**
	 * Pre: None.
	 * Post: Object will be initialized.
	 */
	public Vertex() {
		name = "";
		edges = new ArrayList<Edge>();
	}
	
	/**
	 * Pre: The name of the vertex must be passed.
	 * Post: The name of the vertex will be set.
	 * @param nodeName	Name of the vertex
	 */
	public void setName(String nodeName) {
		name = nodeName;
	}
	
	/**
	 * Pre: None.
	 * Post: The name of the vertex will be returned.
	 * @return	The name of the vertex is returned.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Pre: The name of an edge and its cost must be passed.
	 * Post: A new edge will be added to the vertex.
	 * @param name	Name of the edge
	 * @param cost	Cost of the edge
	 */
	public void addEdge(String name, int cost) {
		  Edge edge = new Edge();
		  edge.setName(name);
		  edge.setCost(cost);
		  edges.add(edge);
	}
	
	/**
	 * Pre: Node.
	 * Post: All of edges of the vertex will be returned.
	 * @return	The edges will be returned in an ArrayList<Node> object.
	 */
	public ArrayList<Edge> getEdges() {
		return edges;
	}
}
