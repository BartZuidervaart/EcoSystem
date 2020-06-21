package com.GameEngine.engine.gui;

import com.GameEngine.engine.GameContainer;
import com.GameEngine.engine.Renderer;
import com.GameEngine.engine.audio.SoundClip;
import com.GameEngine.engine.math.MathUtilities;

public class chooseAudio {
	MathUtilities m = new MathUtilities();
	SoundClip[] audio;
	int chosenSound;

	Button button;
	DropDownMenu clickmenu;
	Slider slider;

	int offX;
	int offY;
	String[] names;
	String[] filePaths;

	double volume;
	boolean visible = true;

	int fileLoadCounter; // because loading all files at once kills the stream

	public chooseAudio(String[] names, String[] filePaths, int offX, int offY) {
		this.names = names;
		this.filePaths = filePaths;
		this.offX = offX;
		this.offY = offY;

		audio = new SoundClip[filePaths.length];
		for (int i = 0; i < filePaths.length; i++) {
			// System.out.println("Trying to load:" + names[i] + "...");
			audio[i] = new SoundClip(filePaths[i]);
		}

		button = new Button(names[0], new int[] { 100, 100, 100 }, new int[] { 150, 150, 150 },
				new int[] { 50, 50, 50 }, new int[] { 255, 255, 255 }, offX, offY, 200, 20);

		clickmenu = new DropDownMenu(names, new int[] { 100, 100, 100 }, new int[] { 150, 150, 150 },
				new int[] { 50, 50, 50 }, new int[] { 255, 255, 255 }, offX, offY, 150, 20);
		slider = new Slider("VOLUME", new int[] { 75, 75, 75 },
				new int[][] { { 100, 100, 100 }, { 150, 150, 150 }, { 50, 50, 50 } }, new int[] { 0, 0, 0 }, offX,
				offY + 70, 200, 10, 20, 30);

	}

	public void draw(GameContainer gc, Renderer r) {
		if (visible) {
			button.draw(gc, r);
			if(volume > 0.0) {
				slider.setFontColor(new int[] { 0, 0, 0 });
				slider.setSliderColor(new int[] { 50, 50, 50 });
			} else {
				slider.setFontColor(new int[] { 150, 150, 150 });
				slider.setSliderColor(new int[] { 150, 150, 150 });
			}
			slider.draw(gc, r);
			clickmenu.draw(gc, r);
		}
	}

	public boolean checkInput(GameContainer gc) {
		if (visible) {
			slider.checkClicked(gc);
			if (volume != slider.getValue()) {
				// actions
				volume = slider.getValue();
				if (volume == 0.0) {
					audio[chosenSound].stop();
				} else {
					if (!audio[chosenSound].isRunning()) {
						audio[chosenSound].loop();
					}
				}
				audio[chosenSound].setVolume((int) m.map(volume, 0.0, 1.0, -30, 6));
			}
			int selectedOption = clickmenu.optionClicked(gc);
			if (selectedOption >= 0) {
				button.setName(names[selectedOption]);
				System.out.println(filePaths[selectedOption]);
				for (int i = 0; i < audio.length; i++) {
					if (audio[i].isRunning()) {
						audio[i].stop();
					}
				}
				chosenSound = selectedOption;
				audio[chosenSound].setVolume((float) m.map(volume, 0.0, 1.0, -30, 6));
				if (volume != 0.0) {
					audio[chosenSound].loop();
				}
				clickmenu.setVisible(false);
			}
			if (button.checkClicked(gc)) {
				if (clickmenu.optionClicked(gc) < 0) {
					clickmenu.setPosition(gc.getInput().getMouseX(), gc.getInput().getMouseY());
					if (clickmenu.isVisible()) {
						clickmenu.setVisible(false);
					} else {
						clickmenu.setVisible(true);
					}
				}
			}
		}
		return false;
	}
}
