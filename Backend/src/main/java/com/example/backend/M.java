package com.example.backend;
import java.awt.Color;
import java.util.HashMap;
import java.util.Random;


public class M extends Node implements Runnable
{
	private Thread t;
	private final static int minServiceTime = 500;
	private final static int maxServiceTime = 2000;
	private final static Color idleColor = Color.LIGHT_GRAY;
	private Color color;
    private int serviceTime;
    private Item currentItem;
    private boolean ready;
    private Q output;

    public M(){
        serviceTime = new Random().nextInt(minServiceTime,maxServiceTime);
        in_nodes = new HashMap<Integer, Node>();
        output = null;
        currentItem = null;
        ready = false;
		color = idleColor;
		t = new Thread(this);
    }

	synchronized public void try_feed(Item item)
	{
		if (!ready) return;
		currentItem = item;
		color = item.getColor();
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
		color = idleColor;
		currentItem = null;
		notify_inputs();
	}

    
    @Override
    public void run(){
        try {
			while (currentItem != null)
			{
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
