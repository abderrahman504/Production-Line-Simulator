package com.example.backend;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
// import java.awt.Color;



public class ConcreteQ extends Node
{
	Collector collector;
	private Queue<Item> queue;
	private HashMap<Integer, ConcreteM> out_nodes;
	//private boolean wasEmpty;
	private boolean accessed;


	public ConcreteQ(int id, Collector collector)
	{
		this.collector = collector;
		this.id = id;
		queue = new LinkedList<Item>();
		this.out_nodes = new HashMap<Integer, ConcreteM>();
		in_nodes = new HashMap<Integer, Node>();
		//this.wasEmpty = true;
		this.accessed = false;
	}

	public void add_input(Node in)
	{
		in_nodes.put(in.get_id(), in);
	}

	public void add_output(Node out)
	{
		out_nodes.put(out.get_id(), (ConcreteM)out);
	}

	public void remove_output(int id)
	{
		out_nodes.remove(id);
	}

	// Input to queue
	synchronized public void add_item(Item i)
	{
		this.queue.add(i);
		if (out_nodes.size() == 0  && queue.size() == Simulator.getInstance().get_sim_items())
		{
			Simulator.getInstance().on_simulation_ended();
		}
	}

	// Machine requests output from queue and returns true if successful and false otherwise
	synchronized public boolean request_item(ConcreteM destination)
	{
		if (queue.isEmpty()) return false;
		boolean success = destination.try_feed(queue.peek());
		if (success){
			collector.addUpdate(new Transition(this.get_id(), destination.get_id(), queue.remove().getColor(), false));
			return true;
		}
		return false;
	}

	public void clear_contents() {queue = new LinkedList<Item>();}
}