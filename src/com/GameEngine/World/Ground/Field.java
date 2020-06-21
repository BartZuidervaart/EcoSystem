package com.GameEngine.World.Ground;

import java.util.HashMap;
import java.util.Map;

import com.GameEngine.World.Fauna.Animal;
import com.GameEngine.World.Fauna.Herbivore;
import com.GameEngine.World.Flora.Plant;
import com.GameEngine.World.Flora.SimplePlant;
import com.GameEngine.engine.Renderer;
import com.GameEngine.engine.gfx.ImageTile;

public class Field {

	private int xNum, yNum;
	private int tileW, tileH;

	HashMap<Ground, Plant> plants = new HashMap<Ground, Plant>();
	HashMap<Ground, Animal> animals = new HashMap<Ground, Animal>();

	ImageTile simplePlant;
	ImageTile herbivore;

	int days;
	final int daysInYear = 30;
	
	Ground[][] ground;

	private ImageTile groundImage;

	public Field(int xNum, int yNum, int tileW, int tileH) {
		this.xNum = xNum;
		this.yNum = yNum;
		this.tileW = tileW;
		this.tileH = tileH;

		ground = new Ground[xNum][yNum];

		groundImage = new ImageTile("/grassnrocks.jpg", tileW, tileH);

		for (int y = 0; y < ground.length; y++) {
			for (int x = 0; x < ground[0].length; x++) {
				ground[x][y] = new Ground(x, y, tileW, tileH, 0, 0, groundImage);
			}
		}

		simplePlant = new ImageTile("/plantlife-animation.png", tileW, tileH);
		herbivore = new ImageTile("/herbivore.png",tileW, tileH);
	}

	public void update() {
		
	}

	public void render(Renderer r) {
		days++;
		
		if(days >= daysInYear) {
			days = 0;
		plants.entrySet().forEach(e -> e.getValue().age());
		animals.entrySet().forEach(e-> e.getValue().age());
		}
		
		HashMap<Ground,Animal> animalsCopy = (HashMap<Ground,Animal>) animals.clone();
		animalsCopy.entrySet().forEach(e -> e.getValue().move());
		
		HashMap<Ground,Plant> plantsCopy = (HashMap<Ground, Plant>) plants.clone();
		plantsCopy.entrySet().forEach(e -> e.getValue().sprout());
		
		plants.entrySet().removeIf(e -> e.getValue().isDead() == true);
		animals.entrySet().removeIf(e -> e.getValue().isDead() == true);
		
		plants.entrySet().forEach(e -> r.drawImageTile(e.getValue().getImage(),e.getKey().getX() * tileW, e.getKey().getY() * tileH, e.getValue().getAge(), 0));
		animals.entrySet().forEach(e -> r.drawImageTile(e.getValue().getImage(),e.getKey().getX() * tileW, e.getKey().getY() * tileH, e.getValue().getTilePos()[0], e.getValue().getTilePos()[1]));
	}

	public void addHerbivore(int x, int y) {
		animals.putIfAbsent(ground[x][y], new Herbivore(new int[] {x,y},herbivore,this));
	}
	
	public void addPlant(int x, int y) {
		plants.putIfAbsent(ground[x][y], new SimplePlant(new int[] { x, y }, simplePlant, this));
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

	public int getTileW() {
		return tileW;
	}

	public void setTileW(int tileW) {
		this.tileW = tileW;
	}

	public int getTileH() {
		return tileH;
	}

	public void setTileH(int tileH) {
		this.tileH = tileH;
	}

	public ImageTile getSimplePlant() {
		return simplePlant;
	}

	public void setSimplePlant(ImageTile simplePlant) {
		this.simplePlant = simplePlant;
	}

	public HashMap<Ground, Plant> getPlants() {
		return plants;
	}

	public void setPlants(HashMap<Ground, Plant> plants) {
		this.plants = plants;
	}

	public Ground[][] getGround() {
		return ground;
	}

	public void setGround(Ground[][] ground) {
		this.ground = ground;
	}

	public HashMap<Ground, Animal> getAnimals() {
		return animals;
	}

	public void setAnimals(HashMap<Ground, Animal> animals) {
		this.animals = animals;
	}

	public ImageTile getHerbivore() {
		return herbivore;
	}

	public void setHerbivore(ImageTile herbivore) {
		this.herbivore = herbivore;
	}

	public ImageTile getGroundImage() {
		return groundImage;
	}

	public void setGroundImage(ImageTile groundImage) {
		this.groundImage = groundImage;
	}
}
