package com.example.backend;

import java.util.HashMap;


public class Node {
	protected int id;
	protected HashMap<Integer, Node> in_nodes;

	public int get_id(){return id;}

}
