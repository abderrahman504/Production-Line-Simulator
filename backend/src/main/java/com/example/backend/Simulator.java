package com.example.backend;

import java.util.ArrayList;
import java.util.Random;


public class Simulator 
{
	private static Snapshot snapshot;
	private final static int maxItemsQuantity = 20;
	private final static float maxItemRate = 1/2.0f;
	private final static float minItemRate = 1/8.0f; 
	public final static int timeUnit = 250;
	private static Simulator instance;
	private static int simulationItemCount;

	public static Simulator getInstance(){
        if (instance == null){
            instance = new Simulator();
        }
        return instance;
    }
	
	public void start_simulation()
	{
		int quantity = new Random().nextInt() % maxItemsQuantity;
		start_simulation(quantity);
	}

	public void start_simulation(int quantity)
	{
		simulationItemCount = quantity;
		ArrayList<Item> items = generate_items(quantity);
		ArrayList<Integer> itemRates = generate_input_rate(quantity);
		
		for (int i=0; i<items.size(); i++)
		{
			GraphManager.getInstance().get_root_Q().add_item(items.get(i));
			try {Thread.sleep(itemRates.get(i));}
			catch (InterruptedException e) {}
		}
			
	}

	public void start_prev_simulation()
	{
		ArrayList<Item> items = snapshot.get_items();
		ArrayList<Integer> itemRates = snapshot.get_rates();
		
		for (int i=0; i<items.size(); i++)
		{
			GraphManager.getInstance().get_root_Q().add_item(items.get(i));
			try {Thread.sleep(itemRates.get(i));}
			catch (InterruptedException e) {}
		}
	}

	ArrayList<Item> generate_items(int quantity)
	{
		ArrayList<Item> items = new ArrayList<Item>(quantity);
		while (quantity-- > 0)
		{
			items.add(new Item());
		}
		return items;
	}

	ArrayList<Integer> generate_input_rate(int quantity)
	{
		ArrayList<Integer> times = new ArrayList<Integer>(quantity-1);
		while (quantity-- >= 0)
		{
			times.add(new Random().nextInt(Math.round(1/maxItemRate), Math.round(1/minItemRate)) * timeUnit);
		}
		return times;
	}

	public int get_sim_items() {return simulationItemCount;}

	public void on_simulation_ended()
	{
		//Signal to front end that the simulation has ended.
	}
	
}



class Snapshot
{
	private ArrayList<Item> items;
	private ArrayList<Integer> waitTimes;

	public Snapshot(ArrayList<Item> items, ArrayList<Integer> itemRates)
	{
		this.items = items;
		waitTimes = itemRates;
	}

	public ArrayList<Item> get_items() {return items;}
	
	public ArrayList<Integer> get_rates() {return waitTimes;} 

}