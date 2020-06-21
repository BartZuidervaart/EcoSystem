package com.GameEngine.World.Flora;

import java.util.HashMap;

import com.GameEngine.World.Ground.Field;
import com.GameEngine.World.Ground.Ground;
import com.GameEngine.engine.gfx.ImageTile;

public class SimplePlant extends Plant {

	public SimplePlant(int[] pos, ImageTile image, Field f) {
		super(3, 11, pos, image, f);
		setNumOfSeeds(3);
		setSeedRange(4);
	}

	@Override
	public void sprout() {
		if (isReadyToSproud()) {
			for (int i = 0; i < Math.random()*getNumOfSeeds(); i++) {
			int randomX = (int) m.map(Math.random(), 0, 1, -getSeedRange(), getSeedRange());
			int randomY = (int) m.map(Math.random(), 0, 1, -getSeedRange(), getSeedRange());
			if (getPos()[0] + randomX >= 0 && getPos()[0] + randomX < getxNum()) {
				if (getPos()[1] + randomY >= 0 && getPos()[1] + randomY < getyNum()) {
					int[] newPosition = new int[] { getPos()[0] + randomX, getPos()[1] + randomY };
					getField().addPlant(newPosition[0], newPosition[1]); // needs to be done outside of the loop
				}
			}
			}
			setReadyToSproud(false);
		}
		
	}

}
