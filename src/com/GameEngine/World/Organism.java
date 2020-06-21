package com.GameEngine.World;

import com.GameEngine.World.Ground.Field;
import com.GameEngine.engine.gfx.ImageTile;
import com.GameEngine.engine.math.MathUtilities;

public abstract class Organism {
	protected MathUtilities m = new MathUtilities();

	protected Field field;
	
	protected int age;
	protected int lifeCycle;

	protected int[] pos;
	protected int xNum, yNum;
	
	protected boolean readyToSproud;
	protected boolean isDead;
	
	protected int seedRange = 2;
	protected int numOfSeeds = 2;
	
	protected ImageTile image;
	
	public Organism(Field field) {
		this.xNum = field.getxNum();
		this.yNum = field.getyNum();
	}
	
	abstract public void age();
	
	public Field getField() {
		return field;
	}
	public void setField(Field field) {
		this.field = field;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getLifeCycle() {
		return lifeCycle;
	}
	public void setLifeCycle(int lifeCycle) {
		this.lifeCycle = lifeCycle;
	}
	public int[] getPos() {
		return pos;
	}
	public void setPos(int[] pos) {
		this.pos = pos;
	}
	public int getxNum() {
		return xNum;
	}
	public void setxNum(int xNum) {
		this.xNum = xNum;
	}
	public int getyNum() {
		return yNum;
	}
	public void setyNum(int yNum) {
		this.yNum = yNum;
	}
	public boolean isReadyToSproud() {
		return readyToSproud;
	}
	public void setReadyToSproud(boolean readyToSproud) {
		this.readyToSproud = readyToSproud;
	}
	public boolean isDead() {
		return isDead;
	}
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	public ImageTile getImage() {
		return image;
	}
	public void setImage(ImageTile image) {
		this.image = image;
	}

	public int getSeedRange() {
		return seedRange;
	}

	public void setSeedRange(int seedRange) {
		this.seedRange = seedRange;
	}

	public int getNumOfSeeds() {
		return numOfSeeds;
	}

	public void setNumOfSeeds(int numOfSeeds) {
		this.numOfSeeds = numOfSeeds;
	}
}
