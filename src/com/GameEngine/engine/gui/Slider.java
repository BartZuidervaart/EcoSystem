package com.GameEngine.engine.gui;

import java.awt.event.MouseEvent;

import com.GameEngine.engine.GameContainer;
import com.GameEngine.engine.Renderer;
import com.GameEngine.engine.math.MathUtilities;

public class Slider {
	MathUtilities m = new MathUtilities();
	boolean pressed;
	String name;
	int[] sliderColor;
	int[][] buttonColors;
	int[] fontColor;
	int offX;
	int offY;
	int width;
	int height;
	int buttonWidth;
	int buttonHeight;
	boolean visible = true;
	boolean sliderDrag = true;
	double sliderValue = 0.0;
	Button button;

	public Slider(String name, int[] sliderColor, int[][] buttonColors, int[] fontColor, int offX, int offY, int width,
			int height, int buttonWidth, int buttonHeight) {
		this.name = name;
		this.buttonColors = buttonColors;
		this.sliderColor = sliderColor;
		this.fontColor = fontColor;
		this.offX = offX;
		this.offY = offY;
		this.width = width;
		this.height = height;
		this.buttonWidth = buttonWidth;
		this.buttonHeight = buttonHeight;
		button = new Button("", buttonColors[0], buttonColors[1], buttonColors[2], new int[] { 255, 255, 255 },
				offX - (buttonWidth / 2) + (int) m.map(sliderValue, 0.0, 1.0, 0, width), offY - (buttonHeight / 2),
				buttonWidth, buttonHeight);
	}

	public void draw(GameContainer gc, Renderer r) {
		if (visible) {
			r.fill(fontColor[0], fontColor[1], fontColor[2]);
			r.drawText(name, offX, offY-(buttonHeight/2)-12);
			r.fill(sliderColor[0],sliderColor[1],sliderColor[2]);
			r.drawRect(offX, offY, width, 1);
			button.draw(gc, r);
		}
	}

	public boolean checkClicked(GameContainer gc) {
		if (visible) {
			if (button.checkPressed(gc)) {
				int mouseX = gc.getInput().getMouseX();
				int mouseY = gc.getInput().getMouseY();
				if (mouseX >= offX && mouseX <= offX + width) {
					sliderDrag = true;
					return true;
				}
			}
			if (sliderDrag) {
				if (gc.getInput().isButton(MouseEvent.BUTTON1)) {
					sliderValue = m.map(gc.getInput().getMouseX() - offX, 0, width, 0.0, 1.0);
					sliderValue = m.constrain(sliderValue, 0.0, 1.0);
					button.setPosition(offX - (buttonWidth / 2) + (int) m.map(sliderValue, 0.0, 1.0, 0, width),
							offY - (buttonHeight / 2));
					return true;
				} else {
					sliderDrag = false;
					return false;
				}
			}
		}
		return false;
	}
	
	public double getValue() {
		return sliderValue;
	}
	
	public void setValue(double s) {
		this.sliderValue = s;
	}

	public int[] getSliderColor() {
		return sliderColor;
	}

	public void setSliderColor(int[] sliderColor) {
		this.sliderColor = sliderColor;
	}

	public int[][] getButtonColors() {
		return buttonColors;
	}

	public void setButtonColors(int[][] buttonColors) {
		this.buttonColors = buttonColors;
	}

	public int[] getFontColor() {
		return fontColor;
	}

	public void setFontColor(int[] fontColor) {
		this.fontColor = fontColor;
	}
}
