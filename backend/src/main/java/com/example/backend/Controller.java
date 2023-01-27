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
	
	@GetMapping("/addConnection")
	public void add_connection(@RequestParam int start, @RequestParam int end)
	{
		graphManager.add_connection(start, end);
	}
	
	@GetMapping("/removeConnection")
	public void remove_connection(@RequestParam int start, @RequestParam int end)
	{
		graphManager.remove_connection(start, end);
	}
	
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
	public void start_simulation()
	{
		simulator.start_simulation();
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
