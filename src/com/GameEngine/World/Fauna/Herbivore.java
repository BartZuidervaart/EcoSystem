package com.GameEngine.World.Fauna;

import com.GameEngine.World.Ground.Field;
import com.GameEngine.engine.gfx.ImageTile;

public class Herbivore extends Animal {

	public Herbivore(int[] pos, ImageTile image, Field f) {
		super(20, 100, pos, image, f);
		setNumOfSeeds(1);
		setSeedRange(2);
		daysBeforeReplicatePossible = 20;
		daysUntilStarve = 100;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void move() {
		if (daysWithoutFood < daysUntilStarve) {
			daysWithoutFood++;

			// image young or old
			if (adult) {
				tilePos[1] = 0;
			} else {
				tilePos[1] = 1;
			}

			if(daysUntilReplicatePossible > 0) {
				daysUntilReplicatePossible--;
			}
			boolean foundGrass[][] = new boolean[3][3];
			boolean grassFound = false;
			for (int y = -1; y < 2; y++) {
				for (int x = -1; x < 2; x++) {
					int posX = pos[0] + x, posY = pos[1] + y;
					if (withinBounds(posX, posY)) {
						if (field.getPlants().containsKey(field.getGround()[pos[0]][pos[1]])) {
							foundGrass[x + 1][y + 1] = true;
							grassFound = true;
						}
					}
				}
			}

			if (grassFound) {
				boolean moveToGrassSpot = false;
				while (!moveToGrassSpot) {
					int x = 1, y = 1;
					if (Math.random() > 0.5) {
						x = (int) (Math.random() * 3);
						if (x - 1 < 0) {
							tilePos[0] = 1;
						} else {
							tilePos[0] = 2;
						}
					} else {
						y = (int) (Math.random() * 3);
						if (y - 1 < 0) {
							tilePos[0] = 0;
						} else {
							tilePos[0] = 3;
						}
					}
					if (foundGrass[x][y]) {
						moveToGrassSpot = true;
						makeMove(x, y);
						if (field.getPlants().containsKey(field.getGround()[pos[0]][pos[1]])) {
							daysWithoutFood = 0;
							field.getPlants().get(field.getGround()[pos[0]][pos[1]]).setDead(true);
						}
					}
				}

			} else {
				// our brave sprout tries to find food
				int x = 1, y = 1;
				if (Math.random() > 0.5) {
					x = (int) (Math.random() * 3);
					if (x - 1 < 0) {
						tilePos[0] = 1;
					} else {
						tilePos[0] = 2;
					}
				} else {
					y = (int) (Math.random() * 3);
					if (y - 1 < 0) {
						tilePos[0] = 0;
					} else {
						tilePos[0] = 3;
					}
				}
				makeMove(x, y);
			}
			if (adult) {
				if(daysUntilReplicatePossible == 0) {
				multiply();
				}
			}
		} else {
			//System.out.println("starvation");
			field.getAnimals().remove(field.getGround()[pos[0]][pos[1]]);
		}
	}

	private void makeMove(int x, int y) {
		if (withinBounds(pos[0] + x - 1, pos[1] + y - 1)) {
			if (!field.getAnimals().containsKey(field.getGround()[pos[0] + (x - 1)][pos[1] + (y - 1)])) {
				field.getAnimals().remove(field.getGround()[pos[0]][pos[1]]);
				pos[0] += x - 1;
				pos[1] += y - 1;
				field.getAnimals().putIfAbsent(field.getGround()[pos[0]][pos[1]], this);
			}
		}
	}

	private boolean withinBounds(int x, int y) {
		if ((x >= 0 && x < xNum) && (y >= 0 && y < yNum)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void multiply() {
		multiplied: for (int y = -1; y < 2; y++) {
			for (int x = -1; x < 2; x++) {
				int posX = pos[0] + x, posY = pos[1] + y;
				if (withinBounds(posX, posY) && !(x == 0 && y == 0)) {
					if (field.getAnimals().containsKey(field.getGround()[posX][posY])) {
						if (field.getAnimals().get(field.getGround()[posX][posY]).isAdult()) {
							int newX = pos[0] + (int) (Math.random() * 3) - 1,
									newY = pos[1] + (int) (Math.random() * 3) - 1;
							if (withinBounds(newX, newY) && !(newX == pos[0] && newY == pos[1])
									&& !(newX == posX && newY == posY)) {
								if (!field.getAnimals().containsKey(field.getGround()[newX][newY])) {
									field.getAnimals().putIfAbsent(field.getGround()[newX][newY],
											new Herbivore(new int[] { newX, newY }, image, field));
									//System.out.println("multiply");
									daysUntilReplicatePossible = daysBeforeReplicatePossible;
									field.getAnimals().get(field.getGround()[posX][posY]).setDaysUntilReplicatePossible(daysBeforeReplicatePossible);
									
								}
								break multiplied;
							}
						}
					}
				}
			}
		}
	}

}
