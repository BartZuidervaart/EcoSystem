package com.GameEngine.engine.game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.GameEngine.World.Ground.Field;
import com.GameEngine.engine.AbstractGame;
import com.GameEngine.engine.GameContainer;
import com.GameEngine.engine.Renderer;
import com.GameEngine.engine.gfx.Image;
import com.GameEngine.engine.math.MathUtilities;

public class GameManager extends AbstractGame {
	private MathUtilities m = new MathUtilities();

	private int width = 1000, height = 1000;

	private int tileW = 20, tileH = 20;

	private Image groundImage = new Image("/Gravel.tex.png");
	
	private Field field = new Field(width / tileW, height / tileH, tileW, tileH);

	public GameManager() {

	}

	@Override
	public void update(GameContainer gc, float dt) {
		if (gc.getInput().isKey(KeyEvent.VK_Q)) {
			if (gc.getInput().isButton(MouseEvent.BUTTON1)) {
				int mouseX = gc.getInput().getMouseX(), mouseY = gc.getInput().getMouseY();
				if ((mouseX > 0 && mouseX < width) && (mouseY > 0 && mouseY < height)) {
					field.addHerbivore(mouseX / tileW, mouseY / tileH);
				}
			}
		} else if (gc.getInput().isButton(MouseEvent.BUTTON1)) {
			int mouseX = gc.getInput().getMouseX(), mouseY = gc.getInput().getMouseY();
			if ((mouseX > 0 && mouseX < width) && (mouseY > 0 && mouseY < height)) {
				field.addPlant(mouseX / tileW, mouseY / tileH);
			}
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawImage(groundImage, 0, 0);
		field.render(r);
		field.update();
	}

	public static void main(String args[]) {
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
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

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
}
