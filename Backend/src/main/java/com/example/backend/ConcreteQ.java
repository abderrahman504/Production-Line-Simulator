package com.example.backend;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
// import java.awt.Color;



public class ConcreteQ extends Node implements Q
{
    private Queue<Item> queue;
	private HashMap<Integer, M> out_nodes;
	private boolean wasEmpty = true;
	

    public ConcreteQ(int id)
	{
		this.id = id;
        queue = new LinkedList<Item>();
        this.out_nodes = new HashMap<Integer, M>();
    }
    
    synchronized public void add_item(Item i)
	{
        this.queue.add(i);
		if (wasEmpty)
		{
			wasEmpty = false;
			feed_outputs();
		}
    }

	synchronized public void request_item(M destination)
	{
		if (queue.isEmpty()) 
		{
			wasEmpty = true;
			return;
		}
		destination.try_feed(queue.remove());
	}

	void feed_outputs()
	{
		for (M out: out_nodes.values())
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