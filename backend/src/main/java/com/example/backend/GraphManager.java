package com.example.backend;

import java.util.HashMap;

public class GraphManager 
{
	private HashMap<Integer, Node> nodes;

	public GraphManager()
	{
		nodes = new HashMap<Integer, Node>();
	}


	public void new_M(int id)
	{
		nodes.put(id, new ConcreteM(id));
	}

	
	public void new_Q(int id)
	{
		nodes.put(id, new ConcreteQ(id));
	}

	public void add_connection(int start, int end)
	{
		Node in = nodes.get(start);
		Node out = nodes.get(end);
		in.add_output(out);
		out.add_input(in);
	}

	
}
