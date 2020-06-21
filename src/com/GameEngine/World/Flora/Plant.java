package com.GameEngine.World.Flora;

import com.GameEngine.World.Organism;
import com.GameEngine.World.Ground.Field;
import com.GameEngine.engine.gfx.ImageTile;
import com.GameEngine.engine.math.MathUtilities;

public abstract class Plant extends Organism {
	private int sproutAge;

	public abstract void sprout();

	public Plant(int sproutAge, int lifeCycle, int[] pos, ImageTile image, Field f) {
		super(f);
		this.sproutAge = sproutAge;
		this.lifeCycle = lifeCycle;
		this.pos = pos;
		this.image = image;
		this.xNum = f.getxNum();
		this.yNum = f.getyNum();
		field = f;
	}

	public void age() {
		if(age < lifeCycle) {
		age++;
		} else {
			isDead = true;
		}
		if (age % sproutAge == 0) {
			for (int i = 0; i < Math.random()*numOfSeeds; i++) {
				readyToSproud = true;
			}
		}
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getSproutAge() {
		return sproutAge;
	}

	public void setSproutAge(int sproutAge) {
		this.sproutAge = sproutAge;
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

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
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

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public boolean isReadyToSproud() {
		return readyToSproud;
	}

	public void setReadyToSproud(boolean readyToSproud) {
		this.readyToSproud = readyToSproud;
	}

}
