package com.example.backend;

import java.awt.Color;
import java.util.Random;

public class Item {
    private Color color;
    public Item(){
        Random rand = new Random();
        int r = rand.nextInt(0, 255);
        int g = rand.nextInt(0, 255);
        int b = rand.nextInt(0, 255);
        color = new Color(r, g, b);
    }

	public String getColor()
	{
		return "rgb(" + color.getRed() + "," + color.getGreen() + "," + color.getBlue() + ")";
	}

}
