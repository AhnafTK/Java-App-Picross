package game;

import java.awt.Color;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JColorChooser;
import javax.swing.Timer;

public class GameModel {
	Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
	/** Resource bundle to get the language messages */
	ResourceBundle langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);
	/** Int variable to hold the grid size, 5x5 by default */
	protected int gridSize = 5;
	/** Boolean for the mark mode, false by default */
	protected boolean markMode = false;
	/** Int variable to check what the current game mode is, 0=design, 1=play */
	Timer timer;
	protected int gameMode = 0;
	protected int scoreNumber = 0;
	protected int timerNumber = 0;

	protected int seconds, minutes;
	protected String secFormat, minFormat;

	protected int[][] boardPuzzle;
	protected boolean gameStarted = false;

	/**
	 * @return the gameMode
	 */
	protected int getGameMode() {
		return gameMode;
	}

	/**
	 * @return the markMode
	 */
	protected boolean isMarkMode() {
		return markMode;
	}

	/**
	 * @param markMode the markMode to set
	 */
	protected void setMarkMode(boolean markMode) {
		this.markMode = markMode;
	}

	/**
	 * @param gameMode the gameMode to set
	 */
	protected void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	protected int time = 0;
	protected int score = 0;

	/**
	 * @return the currentLocale
	 */
	protected Locale getCurrentLocale() {
		return currentLocale;
	}

	/**
	 * @param currentLocale the currentLocale to set
	 */
	protected void setCurrentLocale(Locale currentLocale) {
		this.currentLocale = currentLocale;
	}

	/**
	 * @return the langText
	 */
	protected ResourceBundle getLangText() {
		return langText;
	}

	/**
	 * @param langText the langText to set
	 */
	protected void setLangText(ResourceBundle langText) {
		this.langText = langText;
	}

	protected void getLabel(String[] array) {
		System.out.println("\nPATTERN");
		int thing = 0;
		// iterate throught the column string and get the labels
		for (int b = 0; b < gridSize; b++) {
			// runs through each element of the ROWS
			for (int a = 0; a < gridSize; a++) {
				// System.out.print(col[a].charAt(b)+"\n");
				if (array[b].charAt(a) == '1') {
					thing++;
				} else {
					if (thing != 0) {
						System.out.print(thing + ",");
					}
					thing = 0;
				}
			}
			if (thing != 0) {
				System.out.print(thing);
			}
			thing = 0;
			System.out.println("");

		}
	}

	protected String[] generateRows(int maxPossible) {
		String[] row = new String[gridSize];

		System.out.println("generated values");
		for (int i = 0; i < gridSize; i++) {

			Random rand = new Random();
			int value = rand.nextInt(maxPossible);
			System.out.print(value);
			
			String binVal = Integer.toBinaryString(value);
			while (binVal.length() < gridSize) {
				binVal = "0" + binVal;
			}
			System.out.print("	" + binVal + "\n");
			row[i] = binVal;
		}
	
		System.out.println("\nROWS");
		for (int a = 0; a < gridSize; a++) {
			System.out.println(row[a]);

		}
		getLabel(row);

		return row;
	}

	protected void generateCols(String[] row) {
		String[] col = new String[gridSize];

		for (int k = 0; k < gridSize; k++) {
			String colVal = "";
			for (int l = 0; l < gridSize; l++) {
				colVal = colVal + row[l].charAt(k);
			}
			col[k] = colVal;
		}

		System.out.println("\nCOL");

		for (int a = 0; a < gridSize; a++) {
			System.out.println(col[a]);
		}
		getLabel(col);

	}

	protected void generateInts(int gridSize) {
		int maxPossible = (int) (Math.pow(2, gridSize) - 1);
		String[] row = new String[gridSize];
		String[] col = new String[gridSize];
		row = generateRows(maxPossible);
		generateCols(row);

	}

}
