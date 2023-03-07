package game;

import java.awt.Color;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
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
	
	int [] defaultFiveBVals = {21,20,29,21,21};
	int [] defaultSixBVals = {51,18,0,33,18,12};
	int [] defaultSevBVals = {42,28,34,107,34,28,42};


	/**
	 * 5x5
		10101 	= 21
		10100	= 20
		11101	= 29
		10101	= 21
		10101   = 21
		
		6x6
		110011	= 51
		010010	= 18
		000000	= 0
		100001	= 33
		010010	= 18
		001100	= 12
		
		7x7
		0101010	= 42
		0011100	= 28
		0100010	= 34
		1101011 = 107
		0100010	= 34
		0011100	= 28
		0101010 = 42
	 */
	String[] row;
	String[] col;
	
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

	protected String[] getLabel(String[] array) {
		
		String[] returnLabels = new String[gridSize];
		String currentLabel = "";
		//System.out.println("\nPATTERN");
		int increment = 0;
		int totalPerRow = 0;
		// iterate throught the column string and get the labels
		for (int a = 0; a < gridSize; a++) {
			// runs through each element of the ROWS
			for (int b = 0; b < gridSize; b++) {
				// System.out.print(col[a].charAt(b)+"\n");
				if (array[a].charAt(b) == '1') { // if 1
					increment++;
					totalPerRow++;
				} else { // if 0
					if (increment != 0) { // if 0 and increment is not 0
						//System.out.print(increment + " ");
						currentLabel = currentLabel + increment + " ";
					}
					
					increment = 0;
				}
			}
			if (increment != 0) {
				//System.out.print(increment);
				currentLabel = currentLabel + increment + " ";

			}
			increment = 0;
			//System.out.println("");
			if (totalPerRow == 0) {
				currentLabel = currentLabel + "0";
				returnLabels[a] = currentLabel + " ";

			} 
			else {
				returnLabels[a] = currentLabel + " ";
				currentLabel = "";
			}
			
			totalPerRow = 0;
		}
		//System.out.println("return label val: " + returnLabel);
		System.out.println("array");
		for (String i: returnLabels) {
			System.out.println(i);
		}
		return returnLabels;
	}

	
	protected String intToBinary(int value) {
		String binVal = Integer.toBinaryString(value);
		while (binVal.length() < gridSize) {
			binVal = "0" + binVal;
		}
		return binVal;
	}
	
	protected String[] generateRows(int maxPossible, boolean useDefault) {
		
		row = new String[gridSize];
		
		if (useDefault == true) {
			switch(gridSize) {
			case 5:
				System.out.println("default 5x5");
				for (int i = 0; i < gridSize; i++) {
					row[i] = intToBinary(defaultFiveBVals[i]);
					System.out.println(row[i]);
				}
				break;
			case 6:
				System.out.println("default 6x6");
				for (int i = 0; i < gridSize; i++) {
					row[i] = intToBinary(defaultSixBVals[i]);
					System.out.println(row[i]);
				}
				break;
			case 7:
				System.out.println("default 6x6");
				for (int i = 0; i < gridSize; i++) {
					row[i] = intToBinary(defaultSevBVals[i]);
					System.out.println(row[i]);
				}
				break;
			}			
		}
		else {
			//System.out.println("generated values");
			for (int i = 0; i < gridSize; i++) {

				Random rand = new Random();
				int value = rand.nextInt(maxPossible);
				row[i] = intToBinary(value);
			}
		
			System.out.println("\nROWS");
			for (int a = 0; a < gridSize; a++) {
				System.out.println(row[a]);

			}
		}
	
		return row;
	}

	protected String[] generateCols(String[] row) {
		col = new String[gridSize];
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
		//getLabel(col);
		return col;

	}

	protected void solveBoard() {
		for (int i=0; i < gridSize; i++) {
			System.out.println(row[i]);
			//System.out.println(col[i]);
		}
	}
	
	protected String[] returnRows() {
		return row;
	}
//	protected void generateInts(int gridSize) {
//		int maxPossible = (int) (Math.pow(2, gridSize) - 1);
//		String[] row = new String[gridSize];
//		String[] col = new String[gridSize];
//		
//		row = generateRows(maxPossible);
//		col = generateCols(row);
//		
//		String[] returnLabelRow = getLabel(row);
//		String[] returnLabelCol = getLabel(col);
//		
//
//	}

}
