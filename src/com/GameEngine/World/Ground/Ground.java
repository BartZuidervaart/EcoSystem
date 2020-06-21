package com.GameEngine.World.Ground;

import com.GameEngine.engine.Renderer;
import com.GameEngine.engine.gfx.ImageTile;

public class Ground {
	private int x, y;
	private int width, height;
	private int positionX, positionY;

	private ImageTile image;

	public Ground(int x, int y, int width, int height, int positionX, int positionY, ImageTile image) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.positionX = positionX;
		this.positionY = positionY;
		this.image = image;
	}

	public void render(Renderer r) {
		r.drawImageTile(image, x * width, y * height, x,y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
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

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public ImageTile getImage() {
		return image;
	}

	public void setImage(ImageTile image) {
		this.image = image;
	}

}
