package com.GameEngine.engine.gui;

import java.awt.event.MouseEvent;

import com.GameEngine.engine.GameContainer;
import com.GameEngine.engine.Renderer;

public class Button {
	boolean pressed;
	String name;
	int[] backgroundColor;
	int[] hoverColor;
	int[] clickedColor;
	int[] fontColor;
	int offX;
	int offY;
	int width;
	int height;
	boolean wasPressed = false;
	boolean visible = true;

	public Button(String name, int[] backgroundColor, int[] hoverColor, int[] clickedColor, int[] fontColor, int offX,
			int offY, int width, int height) {
		this.name = name;
		this.backgroundColor = backgroundColor;
		this.hoverColor = hoverColor;
		this.clickedColor = clickedColor;
		this.fontColor = fontColor;
		this.offX = offX;
		this.offY = offY;
		this.width = width;
		this.height = height;
	}

	public void draw(GameContainer gc, Renderer r) {
		if (visible) {
			if (wasPressed) {
				r.fill(clickedColor[0], clickedColor[1], clickedColor[2]);
				wasPressed = false;
			} else if (checkMousedOver(gc)) {
				r.fill(hoverColor[0], hoverColor[1], hoverColor[2]);
			} else {
				r.fill(backgroundColor[0], backgroundColor[1], backgroundColor[2]);
			}
			r.noStroke();
			r.drawRect(offX, offY, width, height);
			r.fill(fontColor[0], fontColor[1], fontColor[2]);
			String buttonText = name;
			if(name.length()>(width/8)) {
				buttonText = name.substring(0, (width/8)-3);
				buttonText = buttonText.concat("...");
			}
			r.drawText(buttonText, offX + (width / 2) - r.getTextWidth(buttonText) / 2,
					offY + (height / 2) - (r.getFont().getFontImage().getH() / 2));
		}
	}

	public boolean checkClicked(GameContainer gc) {
		if (visible) {
			if (gc.getInput().getMouseX() > offX && gc.getInput().getMouseX() < offX + width) {
				if (gc.getInput().getMouseY() > offY && gc.getInput().getMouseY() < offY + height) {
					if (gc.getInput().isButtonUp(MouseEvent.BUTTON1)) {
						wasPressed = true;
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean checkMousedOver(GameContainer gc) {
		if (visible) {
			if (gc.getInput().getMouseX() > offX && gc.getInput().getMouseX() < offX + width) {
				if (gc.getInput().getMouseY() > offY && gc.getInput().getMouseY() < offY + height) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean checkPressed(GameContainer gc) {
		if (visible) {
			if (gc.getInput().getMouseX() > offX && gc.getInput().getMouseX() < offX + width) {
				if (gc.getInput().getMouseY() > offY && gc.getInput().getMouseY() < offY + height) {
					if (gc.getInput().isButton(MouseEvent.BUTTON1)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public void setPosition(int x, int y) {
		offX = x;
		offY = y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
