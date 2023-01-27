package com.example.backend;

import java.util.ArrayList;
import java.util.Random;


public class Simulator 
{
	private Snapshot snapshot;
	private final int maxItemsQuantity = 20;
	private final float maxItemRate = 1/2.0f;
	private final float minItemRate = 1/8.0f; 
	public final int timeUnit = 250;
	private static Simulator instance;
	private int simulationItemCount;

	public static Simulator getInstance(){
        if (instance == null){
            instance = new Simulator();
        }
        return instance;
    }
	
	/**
	 *Starts a simulation. Generates a random number of items for the simulation.  
	 */
	public void start_simulation()
	{
		int quantity = new Random().nextInt() % maxItemsQuantity;
		start_simulation(quantity);
	}

	/**
	 *Starts a simulation. Generates the given number of items for the simulation.  
	 *Feeds each item to the root Q at a random rate.
	 Generates a snapshot of the items and their input rates.
	 * @param quantity
	 */
	public void start_simulation(int quantity)
	{
		simulationItemCount = quantity;
		ArrayList<Item> items = generate_items(quantity);
		ArrayList<Integer> itemRates = generate_input_time(quantity);
		
		for (int i=0; i<items.size(); i++)
		{
			GraphManager.getInstance().get_root_Q().add_item(items.get(i));
			try {Thread.sleep(itemRates.get(i));}
			catch (InterruptedException e) {}
		}
		
	}

	/**
	 * Starts the previous simulation again.
	 */
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

	/**
	 * Generate the given quantity of items
	 * @param quantity
	 * @return an arraylist of the items.
	 */
	ArrayList<Item> generate_items(int quantity)
	{
		ArrayList<Item> items = new ArrayList<Item>(quantity);
		while (quantity-- > 0)
		{
			items.add(new Item());
		}
		return items;
	}

	/**
	 * Generates the input times of the given quantity of items.
	 * @param quantity
	 * @return an arraylist of the input times
	 */
	ArrayList<Integer> generate_input_time(int quantity)
	{
		ArrayList<Integer> times = new ArrayList<Integer>(quantity-1);
		while (quantity-- >= 0)
		{
			times.add(new Random().nextInt(Math.round(1/maxItemRate), Math.round(1/minItemRate)) * timeUnit);
		}
		return times;
	}

	/**
	 * Gets the number of items in this simulation. 
	 * Used to check if the all items have reached the end Q.
	 * @return number of items in this simulation.
	 */
	public int get_sim_items() {return simulationItemCount;}

	/**
	 * Called when the simulation ends. Do clean up and somehow signal to Front that the simulation ended.
	 */
	public void on_simulation_ended()
	{
		//Signal to front end that the simulation has ended.
	}

	/**
	 * Pauses the simulation.
	 */
	public void pause_simulation()
	{

	}

	/**
	 * Resumes the paused simualation.
	 */
	public void resume_simulation()
	{

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