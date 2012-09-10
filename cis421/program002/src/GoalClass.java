/*
 * Author: James Dubee
 * Filename: GoalClass.java
 * Date: 9/9/12
 * Purpose: To provide a goal for searches.
 */

interface GoalInterface {
	public boolean test(Node node);
}

public class GoalClass implements GoalInterface {
	String goal;	// Goal name
	
	/**
	 * Pre: Name of the goal must be passed.
	 * Post: The name of the goal will be set.
	 * @param goalName	Name of the goal.
	 */
	public GoalClass(String goalName) {
		goal = goalName;
	}
	
	/**
	 * Pre: A test node must be passed.
	 * Post: A value of true is returned if the test passed. Otherwise, false is returned.
	 * @param testNode	Node to test
	 * @return	Test result
	 */
	public boolean test(Node testNode) {
		// Ensure the node initialized
		if (testNode != null)
			return testNode.getName().equalsIgnoreCase(goal);
		else
			return false;
	}
}