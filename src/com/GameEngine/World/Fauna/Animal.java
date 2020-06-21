package com.GameEngine.World.Fauna;

import com.GameEngine.World.Organism;
import com.GameEngine.World.Ground.Field;
import com.GameEngine.engine.gfx.ImageTile;

public abstract class Animal extends Organism{
	
	protected boolean adult;
	protected int adultAge;
	protected int[] tilePos;
	
	protected int daysWithoutFood;
	protected int daysUntilStarve;
	protected int daysBeforeReplicatePossible;
	protected int daysUntilReplicatePossible;
	
	public Animal(int adultAge, int lifeCycle, int[] pos, ImageTile image, Field f) {
		super(f);
		this.lifeCycle = lifeCycle;
		this.pos = pos;
		this.image = image;
		this.field = f;
		this.adultAge = adultAge;
		tilePos = new int[] {0,1};
	}
	
	public void age()
	{
		if (age < lifeCycle) {
			age++;
			if (age == adultAge) {
				//System.out.println("adulthood");
				adult = true;
			}
		} else {
			System.out.println("old age");
			isDead = true;
		}
	}
	
	abstract public void move();
	
	abstract public void multiply();

	public boolean isAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}

	public int getAdultAge() {
		return adultAge;
	}

	public void setAdultAge(int adultAge) {
		this.adultAge = adultAge;
	}

	public int[] getTilePos() {
		return tilePos;
	}

	public void setTilePos(int[] tilePosition) {
		this.tilePos = tilePosition;
	}

	public int getDaysBeforeReplicatePossible() {
		return daysBeforeReplicatePossible;
	}

	public void setDaysBeforeReplicatePossible(int daysBeforeReplicatePossible) {
		this.daysBeforeReplicatePossible = daysBeforeReplicatePossible;
	}

	public int getDaysUntilReplicatePossible() {
		return daysUntilReplicatePossible;
	}

	public void setDaysUntilReplicatePossible(int daysUntilReplicatePossible) {
		this.daysUntilReplicatePossible = daysUntilReplicatePossible;
	}
}
