package com.example.backend;
// import java.awt.Color;
import java.util.HashMap;
import java.util.Random;


public class ConcreteM extends Node implements Runnable
{
	private Thread t;
	Collector collector;
	private final static int minServiceTime = 1;
	private final static int maxServiceTime = 20;
	private int serviceTime;
    private Item currentItem;
    private boolean ready;
    private ConcreteQ output;

    public ConcreteM(int id, Collector collector)
	{
		this.id = id;
		this.collector = collector;
        serviceTime = new Random().nextInt(minServiceTime,maxServiceTime);
        in_nodes = new HashMap<Integer, Node>();
        output = null;
        currentItem = null;
        ready = true;
		t = new Thread(this, "M" + id);
    }

	public void add_output(Node out)
	{
		output = (ConcreteQ)out;
	}

	public void remove_output(int id)
	{
		output = null;
	}

	public boolean is_ready() {return ready;}


	// Caller inputs to machine
	synchronized public boolean try_feed(Item item)
	{
		if (!ready) return false;
		currentItem = item;
		return true;
	}

	void notify_inputs()
	{
		for (Node in: in_nodes.values())
		{
			((ConcreteQ)in).request_item(this);
			if (currentItem == null) continue;
			break;
		}
	}

	// Add finished item to output
	void finish_service()
	{
		ready = true;
		output.add_item(currentItem);
		collector.addUpdate(new Transition(this.get_id(), output.get_id(), currentItem.getColor()));
		//Call Controller to signal to front to update this M to the idle color. 
		currentItem = null;
		notify_inputs();
	}

    
    @Override
    public void run(){
        try {
			while (currentItem != null)
			{
				//Call Controller to signal to front to update this M's color.
				Thread.sleep(serviceTime*Simulator.getInstance().timeUnit);
				finish_service();
				
			}
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

	public void start_machine()
	{
		t.start();
	}

	public void clear_contents() {currentItem = null;}
}
