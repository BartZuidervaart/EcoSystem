package com.GameEngine.engine;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import com.GameEngine.engine.game.GameManager;

public class GameContainer implements Runnable {
	private Thread thread;
	private Window window;
	private Renderer renderer;
	private Input input;
	private AbstractGame game;

	private ArrayList<Integer[]> screen = new ArrayList<Integer[]>();

	private boolean running = false;
	private final double UPDATE_CAP = 1.0 / 60.0;
	private final int MENU_HEIGHT = 300;
	private int width = 100, height = 100;
	private float scale = 1.0f;
	private int focussedScreen = 0;
	private String title = "Eco System";

	public GameContainer(GameManager game) {
		this.game = game;
		width = game.getWidth();
		height = game.getHeight();
	}

	public void start() {
		window = new Window(this);
		renderer = new Renderer(this);
		input = new Input(this);

		thread = new Thread(this);
		thread.run();
	}

	public void stop() {

	}

	public void run() {
		running = true;

		boolean render = false;
		double firstTime = 0;
		double lastTime = System.nanoTime() / 1_000_000_000.0;
		double passedTime = 0;
		double unprocessedTime = 0;

		double frameTime = 0;
		int frames = 0;
		int fps = 0;

		while (running) {
			render = false;

			firstTime = System.nanoTime() / 1_000_000_000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;

			unprocessedTime += passedTime;
			frameTime += passedTime;

			while (unprocessedTime >= UPDATE_CAP) {
				unprocessedTime -= UPDATE_CAP;
				render = true;
				game.update(this, (float) UPDATE_CAP);

				input.update();

				if (frameTime >= 1.0) {
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
			}

			if (render) {
				renderer.clear();
				game.render(this, renderer);
				renderer.fill(255, 255, 255);
				renderer.drawText("FPS: " + fps, 10, 10);
				window.update();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		dispose();
	}

	public void dispose() {

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

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}

	public ArrayList<Integer[]> getScreen() {
		return screen;
	}

	public void setScreen(ArrayList<Integer[]> screen) {
		this.screen = screen;
	}

	public int getFocussedScreen() {
		return focussedScreen;
	}

	public void setFocussedScreen(int focussedScreen) {
		this.focussedScreen = focussedScreen;
	}

	public int getMENU_HEIGHT() {
		return MENU_HEIGHT;
	}
}
