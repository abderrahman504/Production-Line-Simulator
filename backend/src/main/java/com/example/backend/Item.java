package com.example.backend;

import java.awt.Color;
import java.util.Random;

public class Item {
    private Color color;
    public Item(){
        Random rand = new Random();
        int r = rand.nextInt() % 256;
        int g = rand.nextInt() % 256;
        int b = rand.nextInt() % 256;
        color = new Color(r, g, b);
    }

	public String getColor()
	{
		return "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
	}

}
