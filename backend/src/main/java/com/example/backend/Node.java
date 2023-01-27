package com.example.backend;

import java.util.HashMap;


public abstract class Node {
	protected int id;
	protected HashMap<Integer, Node> in_nodes;

	public int get_id(){return id;}

	abstract public void add_input(Node in);
	

	abstract public void add_output(Node out);

	public void remove_input(int id)
	{
		in_nodes.remove(id);
	}

	abstract public void remove_output(int id);

	abstract public void clear_contents();

}
