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

	public void new_M(int id)
	{
		nodes.put(id, new ConcreteM(id, collector));
	}

	
	public void new_Q(int id)
	{
		nodes.put(id, new ConcreteQ(id, collector));
	}

	public void add_connection(int start, int end)
	{
		Node in = nodes.get(start);
		Node out = nodes.get(end);
		in.add_output(out);
		out.add_input(in);
	}

	public void remove_connection(int start, int end)
	{
		Node in = nodes.get(start);
		Node out = nodes.get(end);
		in.remove_output(end);
		out.remove_input(start);
	}

	public void set_root_Q(int id)
	{
		rootQ = (ConcreteQ)nodes.get(id);
	}

	public ConcreteQ get_root_Q() {return rootQ;} 

	public void clear()
	{
		nodes = new HashMap<Integer, Node>();
	}

	public void clear_nodes()
	{
		for (Node node : nodes.values())
		{
			node.clear_contents();
		}
	}

}
