package com.prod.productionLine;
import java.util.ArrayList;
import java.util.Random;

class Machine implements Runnable{
    private int time;
    private Item currentItem;
    private boolean ready;
    private ArrayList<LineQueue> inputs;
    private LineQueue output;

    public Machine(){
        this.time = new Random().nextInt(1,10);
        this.inputs = new ArrayList<>();
        this.output = null;
        this.currentItem = null;
        this.ready = true;
    }

    public boolean isReady() {return this.ready;}

    public void addInput(LineQueue q) {this.inputs.add(q);}
    public void setOutput(LineQueue q) {this.output = q;}

    //Input to machine
    public void machineIn(Item i) {this.currentItem = i; this.ready = false;}

    public void notifyInputs(){
        for (LineQueue q : inputs) q.queueOut(this);
    }

    //Run method outputs from machine to next phase/queue
    @Override
    public void run(){
        try {
            Thread.sleep(this.time*1000);
            this.output.queueIn(this.currentItem);
            this.currentItem = null;
            this.ready = true;
            this.notifyInputs();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
