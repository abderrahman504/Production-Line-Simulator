package com.example.backend;

import java.util.ArrayList;

public class Collector {
    private static Collector instance;
    private ArrayList<Transition> updates;
    
	
	private Collector(){
        updates = new ArrayList<>();
    }

    public static synchronized Collector getInstance(){
        if (instance == null){
            instance = new Collector();
        }
        return instance;
    }

    public synchronized void addUpdate(Transition c){
        this.updates.add(c);
    }

    public ArrayList<Transition> getUpdates(){
        ArrayList<Transition> ret = new ArrayList<Transition>(updates);
        updates.clear();
        return ret;
    }
}
