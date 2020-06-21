package com.GameEngine.engine.gui;

import com.GameEngine.engine.GameContainer;
import com.GameEngine.engine.Renderer;

public class DropDownMenu {
	Button[] options;
	String[] names;
	int[] backgroundColor;
	int[] hoverColor;
	int[] clickedColor;
	int[] fontColor;
	int offX;
	int offY;
	int buttonWidth;
	int buttonHeight;

	boolean visible;

	public DropDownMenu(String[] names, int[] backgroundColor, int[] hoverColor, int[] clickedColor, int[] fontColor, int offX, int offY,
			int width, int height) {
		this.names = names;
		this.backgroundColor = backgroundColor;
		this.clickedColor = clickedColor;
		this.fontColor = fontColor;
		this.offX = offX;
		this.offY = offY;
		this.buttonWidth = width;
		this.buttonHeight = height;
		this.options = new Button[names.length];
		for (int i = 0; i < names.length; i++) {
			options[i] = new Button(names[i], backgroundColor, hoverColor, clickedColor, fontColor, offX, offY + (i * height),
					width, height);
		}
	}

	public void draw(GameContainer gc, Renderer r) {
		if (visible) {
			for (int i = 0; i < options.length; i++) {
				options[i].draw(gc, r);
			}
		}
	}

	public int optionClicked(GameContainer gc) {
		if (visible) {
			for (int i = 0; i < options.length; i++) {
				if(options[i].checkClicked(gc)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public void setPosition(int x,int y) {
		offX = x;
		offY = y;
		for(int i = 0; i < options.length; i++) {
			options[i].setPosition(x,y+(buttonHeight*i));
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
