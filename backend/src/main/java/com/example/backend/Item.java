package com.prod.productionLine;

import java.util.Random;

class Item {
    String color;
    public Item(){
        Random rand = new Random();
        float r = rand.nextFloat()/2 + 0.5f;
        float g = rand.nextFloat()/2 + 0.5f;
        float b = rand.nextFloat()/2 + 0.5f;
        this.color = "rgb(" + r + "," + g + "," + b + ")";
    }
}
