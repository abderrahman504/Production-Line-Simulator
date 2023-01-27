package com.example.backend;

import java.util.ArrayList;

public class GraphManager 
{
	private ArrayList<ConcreteQ> queues;
	private ArrayList<ConcreteM> machines;
	
	private Collector collector;
	private ConcreteQ rootQ;
	private static GraphManager instance;
	private boolean firstQ = true;

	public GraphManager()
	{
		machines = new ArrayList<ConcreteM>();
		queues = new ArrayList<ConcreteQ>();
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
		ConcreteM machine = new ConcreteM(id, collector);
		machines.add(machine);
	}

	/**
	 * Adds a Q node to the board and assigns it the given id.
	 * @param id
	 */
	public void new_Q(int id)
	{
		queues.add(new ConcreteQ(id, collector));
		if (firstQ) set_root_Q(id);
	}

	/**
	 * Adds a connection from the node with id start to the node with id end.
	 * @param start id of the start node
	 * @param end id of the end node
	 */
	public void connect_M_to_Q(int start, int end)
	{
		Node in = machines.get(start);
		Node out = queues.get(end);
		in.add_output(out);
		out.add_input(in);
	}

	public void connect_Q_to_M(int start, int end)
	{
		Node in = queues.get(start);
		Node out = machines.get(end);
		in.add_output(out);
		out.add_input(in);
	}

	// /**
	//  * Removes the connection from the node with id start to the node with id end.
	//  * @param start id of the start node
	//  * @param end id of the end node
	//  */
	// public void remove_connection(int start, int end)
	// {
	// 	Node in = nodes.get(start);
	// 	Node out = nodes.get(end);
	// 	in.remove_output(end);
	// 	out.remove_input(start);
	// }

	/**
	 * Sets the Q node that gets fed the items at the start of the simulation 
	 * @param id
	 */
	public void set_root_Q(int id)
	{
		rootQ = (ConcreteQ)queues.get(id);
	}

	/**
	 * 
	 * @return The Root Q node
	 */
	public ConcreteQ get_root_Q() {return rootQ;} 

	public void clear()
	{
		queues = new ArrayList<ConcreteQ>();
		firstQ = true;
	}

	/**
	 * Deletes all nodes on the board
	 */
	public void clear_nodes()
	{
		for (Node node : queues)
		{
			node.clear_contents();
		}
		for (Node node : machines)
		{
			node.clear_contents();
		}
	}

}
