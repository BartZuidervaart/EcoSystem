package com.GameEngine.engine.math;

public class MathUtilities {
	public double map(double x, double in_min, double in_max, double out_min, double out_max) {
		return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
	}
	
	public double constrain(double n, double min, double max) {
		if(n < min) {
			n = min;
		}
		if(n > max) {
			n = max;
		}
		return n;
	}
	
	double dist(int x1, int y1, int x2, int y2) {
		int width = Math.abs(x1-x2);
		int height = Math.abs(y1-y2);
		return Math.sqrt((width*width)+(height*height));
	}
	
	public int rollDice(int numberOfDice, int diceSize) {
		int number = 0;
		for(int i = 0; i < numberOfDice; i++) {
			number += constrain(Math.floor(Math.random()*(diceSize+1)),1,diceSize);
		}
		return number;
	}
}
