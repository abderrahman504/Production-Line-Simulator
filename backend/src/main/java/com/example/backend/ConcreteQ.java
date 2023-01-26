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
	private boolean wasEmpty = true;
	

    public ConcreteQ(int id, Collector collector)
	{
		this.collector = collector;
		this.id = id;
        queue = new LinkedList<Item>();
        this.out_nodes = new HashMap<Integer, ConcreteM>();
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
		if (wasEmpty)
		{
			wasEmpty = false;
			feed_outputs();
		}
    }

	// Machine requests output from queue
	synchronized public void request_item(ConcreteM destination)
	{
		if (queue.isEmpty()) 
		{
			wasEmpty = true;
			return;
		}
		boolean success = destination.try_feed(queue.peek());
		if (success){
			collector.addUpdate(new Transition(this.get_id(), destination.get_id(), queue.remove().getColor().toString()));
		}
	}

	void feed_outputs()
	{
		for (ConcreteM out: out_nodes.values())
		{
			if (queue.isEmpty())
			{
				wasEmpty = true;
				return;
			}
			if (out.is_ready())
			{
				request_item(out);
				out.start_machine();
			}
		}
	}
}