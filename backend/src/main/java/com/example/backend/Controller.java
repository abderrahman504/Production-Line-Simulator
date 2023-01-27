package com.example.backend;


import java.util.ArrayList;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")

@RequestMapping("/back")
public class Controller {
	
	Simulator simulator;
	GraphManager graphManager;
	Collector collector;
	
	public Controller()
	{
		simulator = Simulator.getInstance();
		graphManager = GraphManager.getInstance();
		collector = Collector.getInstance();
	}
	
	@GetMapping("/newM")
	public void new_M(@RequestParam int id)
	{
		System.out.println(id);
		graphManager.new_M(id);
		System.out.print("Added M");
	}
	
	@GetMapping("/newQ")
	public void new_Q(@RequestParam int id)
	{
		System.out.println(id);
		graphManager.new_Q(id);
		System.out.print("Added Q");
	}
	
	@GetMapping("/connectMQ")
	public void connect_M_to_Q(@RequestParam int start, @RequestParam int end)
	{
		graphManager.connect_M_to_Q(start, end);
	}

	@GetMapping("/connectQM")
	public void connect_Q_to_M(@RequestParam int start, @RequestParam int end)
	{
		graphManager.connect_Q_to_M(start, end);
	}
	
	// @GetMapping("/removeConnection")
	// public void remove_connection(@RequestParam int start, @RequestParam int end)
	// {
	// 	graphManager.remove_connection(start, end);
	// }
	
	// @GetMapping("/setRoot")
	// public void set_root_Q(@RequestParam int id)
	// {
	// 	graphManager.set_root_Q(id);
	// }
	
	@GetMapping("/clearBoard")
	public void clear_board()
	{
		graphManager.clear();
	}
	
	@GetMapping("/startSimulation")
	public void start_simulation(@RequestParam int productsNumber)
	{
		System.out.println(productsNumber);
		simulator.start_simulation(productsNumber);
	}
	
	@GetMapping("/restartSimulation")
	public void restart_simulation()
	{
		simulator.start_prev_simulation();
	}
	
	@GetMapping("/pauseSimulation")
	public void pause_simulation()
	{
		// simulator.();
	}
	
	@GetMapping("/resumeSimulation")
	public void resume_simulation()
	{
		// simulator.start_simulation();
	}
	
	@PostMapping("/update")
	public ArrayList<Transition> get_updates()
	{
		return collector.getUpdates();
	}
}
