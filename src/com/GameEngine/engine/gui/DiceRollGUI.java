package com.GameEngine.engine.gui;

import com.GameEngine.engine.GameContainer;
import com.GameEngine.engine.Renderer;
import com.GameEngine.engine.math.MathUtilities;

public class DiceRollGUI {
	MathUtilities m = new MathUtilities();
	Button diceType;
	DropDownMenu chooseDiceType;
	Button numberOfDiceAdd;
	Button numberOfDiceSubtract;
	Button additionalAdd;
	Button additionalSubtract;
	Button roll;

	final int MARGE = 10;
	final int ADDITIONAL_BUTTON_WIDTH = 20;
	final int CHOOSE_BUTTON_WIDTH = 50;

	int numberOfDice = 1;
	int additional;
	int chosenDiceType;
	String[] diceOptions = { "d4", "d6", "d8", "d10", "d12", "d20" };
	int[] dices = { 4, 6, 8, 10, 12, 20 };

	int[] rolls;
	String output = "";

	int px, py;

	boolean visible = true;

	public DiceRollGUI(int px, int py) {
		this.px = px;
		this.py = py;

		diceType = new Button(diceOptions[chosenDiceType], new int[] { 100, 100, 100 }, new int[] { 150, 150, 150 },
				new int[] { 50, 50, 50 }, new int[] { 255, 255, 255 }, px, py, CHOOSE_BUTTON_WIDTH, 20);

		numberOfDiceSubtract = new Button("<", new int[] { 100, 100, 100 }, new int[] { 150, 150, 150 },
				new int[] { 50, 50, 50 }, new int[] { 255, 255, 255 }, px + CHOOSE_BUTTON_WIDTH + MARGE, py,
				ADDITIONAL_BUTTON_WIDTH, 20);
		numberOfDiceAdd = new Button(">", new int[] { 100, 100, 100 }, new int[] { 150, 150, 150 },
				new int[] { 50, 50, 50 }, new int[] { 255, 255, 255 },
				px + (CHOOSE_BUTTON_WIDTH*2) + (MARGE * 3) + (ADDITIONAL_BUTTON_WIDTH * 1), py, ADDITIONAL_BUTTON_WIDTH,
				20);
		additionalSubtract = new Button("<", new int[] { 100, 100, 100 }, new int[] { 150, 150, 150 },
				new int[] { 50, 50, 50 }, new int[] { 255, 255, 255 },
				px + (CHOOSE_BUTTON_WIDTH*2) + (MARGE * 4) + (ADDITIONAL_BUTTON_WIDTH * 2), py, ADDITIONAL_BUTTON_WIDTH,
				20);
		additionalAdd = new Button(">", new int[] { 100, 100, 100 }, new int[] { 150, 150, 150 },
				new int[] { 50, 50, 50 }, new int[] { 255, 255, 255 },
				px + (CHOOSE_BUTTON_WIDTH*3) + (MARGE * 6) + (ADDITIONAL_BUTTON_WIDTH * 3), py, ADDITIONAL_BUTTON_WIDTH,
				20);
		roll = new Button("ROLL", new int[] { 100, 100, 100 }, new int[] { 150, 150, 150 }, new int[] { 50, 50, 50 },
				new int[] { 255, 255, 255 }, px + (CHOOSE_BUTTON_WIDTH*3) + (MARGE * 7) + (ADDITIONAL_BUTTON_WIDTH * 4), py,
				CHOOSE_BUTTON_WIDTH, 20);
		chooseDiceType = new DropDownMenu(diceOptions, new int[] { 100, 100, 100 }, new int[] { 150, 150, 150 },
				new int[] { 50, 50, 50 }, new int[] { 255, 255, 255 }, px, py, CHOOSE_BUTTON_WIDTH, 20);
		chooseDiceType.setVisible(false);
	}

	public void draw(GameContainer gc, Renderer r) {
		if (visible) {
			diceType.draw(gc, r);
			numberOfDiceSubtract.draw(gc, r);
			numberOfDiceAdd.draw(gc, r);
			additionalSubtract.draw(gc, r);
			additionalAdd.draw(gc, r);
			roll.draw(gc, r);
			chooseDiceType.draw(gc, r);

			r.fill(50, 50, 50);
			r.drawText(numberOfDice + diceOptions[chosenDiceType],
					px + CHOOSE_BUTTON_WIDTH + (MARGE * 2) + ADDITIONAL_BUTTON_WIDTH, py+8);
			r.drawText((additional >= 0 ? "+" : "") + additional,
					px + (CHOOSE_BUTTON_WIDTH*2) + (MARGE * 5) + (ADDITIONAL_BUTTON_WIDTH * 3), py+8);
			r.drawText(output, px + (CHOOSE_BUTTON_WIDTH*4) + (MARGE * 8) + (ADDITIONAL_BUTTON_WIDTH * 4), py+8);
		}
	}

	public boolean checkInput(GameContainer gc) {
		if (visible) {
			int selectedOption = chooseDiceType.optionClicked(gc);
			if (selectedOption >= 0) {
				chosenDiceType = selectedOption;
				additional = 0;
				numberOfDice = 1;
				diceType.setName(diceOptions[selectedOption]);
				chooseDiceType.setVisible(false);
			}
			if (diceType.checkClicked(gc)) {
				if (!chooseDiceType.isVisible()) {
					chooseDiceType.setVisible(true);
					chooseDiceType.setPosition(gc.getInput().getMouseX(), gc.getInput().getMouseY());
				} else {
					chooseDiceType.setVisible(false);
				}
			}

			if (numberOfDiceSubtract.checkClicked(gc)) {
				if (numberOfDice > 1) {
					numberOfDice--;
				}
			}
			if (numberOfDiceAdd.checkClicked(gc)) {
				numberOfDice++;
			}

			if (additionalSubtract.checkClicked(gc)) {
				additional--;
			}
			if (additionalAdd.checkClicked(gc)) {
				additional++;
			}
			if (roll.checkClicked(gc)) {
				rolls = new int[numberOfDice];
				String allRollsAligned = "";
				int total = 0;
				for (int i = 0; i < rolls.length; i++) {
					rolls[i] = m.rollDice(1, dices[chosenDiceType]);
					if (i != 0) {
						allRollsAligned += " ";
					}
					total += rolls[i];
					allRollsAligned += rolls[i];
					if(i != rolls.length-1) {
						allRollsAligned += ",";
					}
				}
				output = "(" + allRollsAligned + ") + " + additional + " = " + (total + additional);
			}
		}
		return false;
	}
}
