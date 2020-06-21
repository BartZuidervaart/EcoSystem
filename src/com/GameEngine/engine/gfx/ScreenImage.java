package com.GameEngine.engine.gfx;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

public class ScreenImage{

	private int w, h;
	private int[] p;

	public void captureScreen(int x, int y, int width, int height) {
		Rectangle area = new Rectangle(x,y,width,height);
		Robot robot;
		try {
			robot = new Robot();
			BufferedImage image = robot.createScreenCapture(area);
			
			w = image.getWidth();
			h = image.getHeight();
			p = image.getRGB(0, 0, w, h, null, 0, w);
			
			image.flush();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public int[] getP() {
		return p;
	}

	public void setP(int[] p) {
		this.p = p;
	}
}
