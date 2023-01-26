package com.example.backend;

import java.util.HashMap;

public class GraphManager 
{
	private HashMap<Integer, Node> nodes;
	private Collector collector;

	public GraphManager()
	{
		nodes = new HashMap<Integer, Node>();
		collector = Collector.getInstance();
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

}
