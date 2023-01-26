package com.example.backend;
// import java.awt.Color;
import java.util.HashMap;
import java.util.Random;


public class ConcreteM extends Node implements Runnable, M
{
	private Thread t;
	private final static int minServiceTime = 500;
	private final static int maxServiceTime = 2000;
    private int serviceTime;
    private Item currentItem;
    private boolean ready;
    private Q output;

    public ConcreteM(int id)
	{
		this.id = id;
        serviceTime = new Random().nextInt(minServiceTime,maxServiceTime);
        in_nodes = new HashMap<Integer, Node>();
        output = null;
        currentItem = null;
        ready = true;
		t = new Thread(this, "M" + id);
    }

	public boolean is_ready() {return ready;}

	synchronized public void try_feed(Item item)
	{
		if (!ready) return;
		currentItem = item;
	}

	void notify_inputs()
	{
		for (Node in: in_nodes.values())
		{
			((Q)in).request_item(this);
			if (currentItem == null) continue;
			break;
		}
	}

	void finish_service()
	{
		ready = true;
		output.add_item(currentItem);
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
				Thread.sleep(serviceTime);
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
}
