package com.prod.productionLine;
import javax.sound.sampled.Line;
import java.util.ArrayList;
import java.util.LinkedList;

class LineQueue{
    LinkedList<Item> queue;
    ArrayList<Machine> outputs;

    public LineQueue(){
        this.queue = new LinkedList<>();
        this.outputs = new ArrayList<>();
    }

    public int size() {return this.queue.size();}
    public boolean isEmpty() {return this.queue.isEmpty();}

    //Add item to queue
    public void queueIn(Item i){
        this.queue.addLast(i);
    }

    // Output item from queue to machine if the machine is ready and queue is not empty
    public void queueOut(Machine m){
        if (m.isReady() && !queue.isEmpty()) m.machineIn(queue.removeFirst());
    }

}