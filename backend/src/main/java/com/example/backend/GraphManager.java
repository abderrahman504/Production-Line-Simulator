package com.example.backend;

import java.util.HashMap;

public class GraphManager 
{
	private HashMap<Integer, Node> nodes;
	private Collector collector;
	private ConcreteQ rootQ;
	private static GraphManager instance;

	public GraphManager()
	{
		nodes = new HashMap<Integer, Node>();
		collector = Collector.getInstance();
	}

	public static synchronized GraphManager getInstance(){
        if (instance == null){
            instance = new GraphManager();
        }
        return instance;
    }

	/**
	 * Adds an M node to the board and assigns it the given id.
	 * @param id
	 */
	public void new_M(int id)
	{
		nodes.put(id, new ConcreteM(id, collector));
	}

	/**
	 * Adds a Q node to the board and assigns it the given id.
	 * @param id
	 */
	public void new_Q(int id)
	{
		nodes.put(id, new ConcreteQ(id, collector));
	}

	/**
	 * Adds a connection from the node with id start to the node with id end.
	 * @param start id of the start node
	 * @param end id of the end node
	 */
	public void add_connection(int start, int end)
	{
		Node in = nodes.get(start);
		Node out = nodes.get(end);
		in.add_output(out);
		out.add_input(in);
	}

	/**
	 * Removes the connection from the node with id start to the node with id end.
	 * @param start id of the start node
	 * @param end id of the end node
	 */
	public void remove_connection(int start, int end)
	{
		Node in = nodes.get(start);
		Node out = nodes.get(end);
		in.remove_output(end);
		out.remove_input(start);
	}

	/**
	 * Sets the Q node that gets fed the items at the start of the simulation 
	 * @param id
	 */
	public void set_root_Q(int id)
	{
		rootQ = (ConcreteQ)nodes.get(id);
	}

	/**
	 * 
	 * @return The Root Q node
	 */
	public ConcreteQ get_root_Q() {return rootQ;} 

	public void clear()
	{
		nodes = new HashMap<Integer, Node>();
	}

	/**
	 * Deletes all nodes on the board
	 */
	public void clear_nodes()
	{
		for (Node node : nodes.values())
		{
			node.clear_contents();
		}
	}

}
