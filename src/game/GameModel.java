package game;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

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

	protected int totalValid = 0;
	protected int currentValid = 0;
	
	protected int bestTime = 0;
	protected int bestScore = 0;
	
	protected int score = 0;
	
	protected int seconds, minutes;
	protected String secFormat, minFormat;

	protected int[][] boardPuzzle;
	protected boolean gameStarted = false;
	protected boolean gameFinished = false;
	
	int [] defaultFiveBVals = {21,20,29,21,21};
	int [] defaultSixBVals = {51,18,0,33,18,12};
	int [] defaultSevBVals = {42,28,34,107,34,28,42};
	
	String[][] designBoard;

	private String[] row;
	private String[] col;
	String[] rowLabels;
	String[] colLabels;
	
	String[] rowLabelsDesign;
	String[] colLabelsDesign;
	
	protected int timerToSeconds() {
		minutes = getMinutes();
		seconds = getSeconds();
		
		seconds = seconds + (minutes * 60);
		bestTime = seconds;
		return bestTime;
	}
	
	protected void changeLanguage(String lang, String region) {
		this.currentLocale = new Locale.Builder().setLanguage(lang).setRegion(region).build();
		this.langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);
	}
	
	/**
	 * @return the gameFinished
	 */
	protected boolean getGameFinished() {
		return gameFinished;
	}

	/**
	 * @param gameFinished the gameFinished to set
	 */
	protected void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}
	
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

	protected String[] rowLabel() {
		rowLabels = new String[gridSize];
		String currentLabel = "";
		//String[] returnLabels = new String[gridSize];
		//System.out.println("\nPATTERN");
		int increment = 0;
		int totalPerRow = 0;
		// iterate throught the column string and get the labels
		for (int a = 0; a < gridSize; a++) {
			// runs through each element of the ROWS
			for (int b = 0; b < gridSize; b++) {
				// System.out.print(col[a].charAt(b)+"\n");
				if (getRow()[a].charAt(b) == '1') { // if 1
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
				currentLabel = currentLabel + increment + " ";
			}
			increment = 0;
			//System.out.println("");
			if (totalPerRow == 0) {
				currentLabel = currentLabel + " ";
				rowLabels[a] = currentLabel + " ";
			} 
			else {
				rowLabels[a] = currentLabel + " ";
				currentLabel = "";
			}
			
			totalPerRow = 0;
		}
		for (int i = 0; i < gridSize; i++) {
			System.out.println(rowLabels[i]);
		}
		return rowLabels;
	}
	
	protected String[] colLabel() {
		colLabels = new String[gridSize];
		String currentLabel = "";

		int increment = 0;
		int totalPerRow = 0;
		// iterate throught the column string and get the labels
		for (int a = 0; a < gridSize; a++) {
			// runs through each element of the ROWS
			for (int b = 0; b < gridSize; b++) {
				if (col[a].charAt(b) == '1') { // if 1
					increment++;
					totalPerRow++;
				} else { // if 0
					if (increment != 0) { // if 0 and increment is not 0
						currentLabel = currentLabel + increment + "\n";
					}
					increment = 0;
				}
			}
			if (increment != 0) {
				currentLabel = currentLabel + increment + "";
			}
			increment = 0;
			//System.out.println("");
			if (totalPerRow == 0) {
				currentLabel = currentLabel + "0";
				colLabels[a] = currentLabel + "";
			} 
			else {
				colLabels[a] = currentLabel + "";
				currentLabel = "";
			}
			
			totalPerRow = 0;
		}
		for (int i = 0; i < gridSize; i++) {
			System.out.println(colLabels[i]);
		}
		return colLabels;
	}
		
	protected void totalValidTiles() {
		totalValid = 0;
		for (int a = 0; a < gridSize; a++) {
			for (int b = 0; b < gridSize; b++) {
				if (row[a].charAt(b) == '1') { // if 1
					totalValid++;
				}
			}
		}
	}
	
	protected String writeDesignPattern() {
		String pattern = "";
		for (int i = 0; i< gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				pattern = pattern + designBoard[i][j];
			}
			pattern = pattern + "\n";
		}
		return pattern;
	}
	protected String writePattern() {
		String pattern = "";
		for (int i = 0; i < gridSize; i++) {
			pattern = pattern + (getRow()[i]) + "\n";
		}
		return pattern;
	}
	
	protected void readFileForDesign(Scanner fileReader) {
		int fileGridSize = fileReader.nextInt();
		gridSize = fileGridSize;
		System.out.println("GRIDSIZE " + gridSize);
		designBoard = new String[gridSize][gridSize];
		fileReader.nextLine();
		for (int i = 0; i < gridSize; i++) {
			String currentLine = fileReader.nextLine();
			System.out.println(currentLine);
			for (int j = 0; j < gridSize; j++) {
				designBoard[i][j] = String.valueOf(currentLine.charAt(j));
				//System.out.println(fileReader.next().charAt(j));
				//designBoard[i][j] = String.valueOf(fileReader.next().charAt(j));
			}
		}
		System.out.println("design board after reading");
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				System.out.println(designBoard[i][j]);

			}
		}
		
	}
	protected void readFile(Scanner fileReader) {
		// get gridsize first
		int fileGridSize = fileReader.nextInt();
		gridSize = fileGridSize;
		System.out.println("GRIDSIZE " + gridSize);
		row = new String[fileGridSize];
		fileReader.nextLine();
		for (int i = 0; i < gridSize; i++) {
			row[i] = fileReader.nextLine();
			System.out.println("Row at i " + row[i]);
		}
		if(fileReader.hasNextLine()) {
			///setBestScore(fileReader.nextInt());
			//System.out.println("Best Score: " + getBestScore());
			//setBestTime(fileReader.nextInt());	//Has to be fixed for grid sizes
			//System.out.println("Best Time: " + getBestTime());
		}
		System.out.println("model row is now: ");
		for (int i = 0; i < gridSize; i++) {
			System.out.println(row[i]);

		}
	}
	
	protected String intToBinary(int value) {
		String binVal = Integer.toBinaryString(value);
		while (binVal.length() < gridSize) {
			binVal = "0" + binVal;
		}
		return binVal;
	}
	
	protected String[] rowLabelDesign() {
		rowLabelsDesign = new String[gridSize];
		for(int i = 0; i<gridSize;i++) {
			rowLabelsDesign[i] = "0";
		}
		return rowLabelsDesign;
	}
	
	protected String[] colLabelDesign() {
		colLabelsDesign = new String[gridSize];
		for(int i = 0; i<gridSize;i++) {
			colLabelsDesign[i] = "0";
		}
		return colLabelsDesign;
	}
 	protected String[] generateRows(int maxPossible, boolean useDefault) {
		
		setRow(new String[gridSize]);
		
		if (useDefault == true) {
			switch(gridSize) {
			case 5:
				System.out.println("default 5x5");
				for (int i = 0; i < gridSize; i++) {
					getRow()[i] = intToBinary(defaultFiveBVals[i]);
					System.out.println(getRow()[i]);
				}
				break;
			case 6:
				System.out.println("default 6x6");
				for (int i = 0; i < gridSize; i++) {
					getRow()[i] = intToBinary(defaultSixBVals[i]);
					System.out.println(getRow()[i]);
				}
				break;
			case 7:
				System.out.println("default 6x6");
				for (int i = 0; i < gridSize; i++) {
					getRow()[i] = intToBinary(defaultSevBVals[i]);
					System.out.println(getRow()[i]);
				}
				break;
			}			
		}
		else {
			//System.out.println("generated values");
			for (int i = 0; i < gridSize; i++) {

				Random rand = new Random();
				int value = rand.nextInt(maxPossible);
				getRow()[i] = intToBinary(value);
			}
		
			System.out.println("\nROWS");
			for (int a = 0; a < gridSize; a++) {
				System.out.println(getRow()[a]);

			}
		}
		return getRow();
		
	}
	
	protected String[] generateCols() {
		col = new String[gridSize];
		for (int k = 0; k < gridSize; k++) {
			String colVal = "";
			for (int l = 0; l < gridSize; l++) {
				System.out.println("K " + k);
				System.out.println("L " + l);
				System.out.println(row[l].charAt(k));

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

	String updateLabels(int i) {
		StringBuilder builder = new StringBuilder();
		for (String row: designBoard[i]) {
			builder.append(row);
		}
		String newRow = builder.toString();
		rowLabelsDesign[i] = newRow;
		System.out.println(newRow);
		int increment = 0;
		return newRow;
	}
	void makeDesignBoard(int gridSize) {
		designBoard = new String[gridSize][gridSize];
		for (int i = 0; i< gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				designBoard[i][j] = "0";
			}
		}
	}
	
	String updateRow(int i, int j) {
		StringBuilder builder = new StringBuilder();
		for (String row: designBoard[i]) {
			if(row!=null) {
				builder.append(row);
			}
		}
		String newRow = builder.toString();
		rowLabelsDesign[i] = newRow;
		//System.out.println(newRow);
		int increment = 0;
		return newRow;
	}
	
	String updateCol(int i, int j) {
		StringBuilder builder = new StringBuilder();
		
		for(int k = 0; k < gridSize; k++) {
			if(designBoard[k][j]!=null) {
			builder.append(designBoard[k][j]);
			System.out.println("K " + k + " j " + j + " "+ designBoard[k][j]);
			}
		}
		
		String newCol = builder.toString();
		System.out.println(newCol);
		
		return newCol;
	}
	
	void updateDesignBoard(int i, int j) {
		System.out.println("tile clicked @: row = " + i + " number: "+j);
		designBoard[i][j] = "1";
		//return updateLabels(i);
	}
	void resetBoard() {
		rowLabels = new String[gridSize];
		colLabels = new String[gridSize];
		gridSize = 5;
		rowLabelsDesign = new String[gridSize];
		colLabelsDesign = new String[gridSize];
	}
	
	/**
	 * @return the gridSize
	 */
	protected int getGridSize() {
		return gridSize;
	}

	/**
	 * @param gridSize the gridSize to set
	 */
	protected void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	/**
	 * @return the timer
	 */
	protected Timer getTimer() {
		return timer;
	}

	/**
	 * @param timer the timer to set
	 */
	protected void setTimer(Timer timer) {
		this.timer = timer;
	}

	/**
	 * @return the scoreNumber
	 */
	protected int getScoreNumber() {
		return scoreNumber;
	}

	/**
	 * @param scoreNumber the scoreNumber to set
	 */
	protected void setScoreNumber(int scoreNumber) {
		this.scoreNumber = scoreNumber;
	}

	/**
	 * @return the timerNumber
	 */
	protected int getTimerNumber() {
		return timerNumber;
	}

	/**
	 * @param timerNumber the timerNumber to set
	 */
	protected void setTimerNumber(int timerNumber) {
		this.timerNumber = timerNumber;
	}

	/**
	 * @return the seconds
	 */
	protected int getSeconds() {
		return seconds;
	}

	/**
	 * @param seconds the seconds to set
	 */
	protected void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	/**
	 * @return the minutes
	 */
	protected int getMinutes() {
		return minutes;
	}

	/**
	 * @param minutes the minutes to set
	 */
	protected void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	/**
	 * @return the secFormat
	 */
	protected String getSecFormat() {
		return secFormat;
	}

	/**
	 * @param secFormat the secFormat to set
	 */
	protected void setSecFormat(String secFormat) {
		this.secFormat = secFormat;
	}

	/**
	 * @return the minFormat
	 */
	protected String getMinFormat() {
		return minFormat;
	}

	/**
	 * @param minFormat the minFormat to set
	 */
	protected void setMinFormat(String minFormat) {
		this.minFormat = minFormat;
	}

	/**
	 * @return the boardPuzzle
	 */
	protected int[][] getBoardPuzzle() {
		return boardPuzzle;
	}

	/**
	 * @param boardPuzzle the boardPuzzle to set
	 */
	protected void setBoardPuzzle(int[][] boardPuzzle) {
		this.boardPuzzle = boardPuzzle;
	}

	/**
	 * @return the gameStarted
	 */
	protected boolean isGameStarted() {
		return gameStarted;
	}

	/**
	 * @param gameStarted the gameStarted to set
	 */
	protected void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	/**
	 * @return the defaultFiveBVals
	 */
	protected int[] getDefaultFiveBVals() {
		return defaultFiveBVals;
	}

	/**
	 * @param defaultFiveBVals the defaultFiveBVals to set
	 */
	protected void setDefaultFiveBVals(int[] defaultFiveBVals) {
		this.defaultFiveBVals = defaultFiveBVals;
	}

	/**
	 * @return the defaultSixBVals
	 */
	protected int[] getDefaultSixBVals() {
		return defaultSixBVals;
	}

	/**
	 * @param defaultSixBVals the defaultSixBVals to set
	 */
	protected void setDefaultSixBVals(int[] defaultSixBVals) {
		this.defaultSixBVals = defaultSixBVals;
	}

	/**
	 * @return the defaultSevBVals
	 */
	protected int[] getDefaultSevBVals() {
		return defaultSevBVals;
	}

	/**
	 * @param defaultSevBVals the defaultSevBVals to set
	 */
	protected void setDefaultSevBVals(int[] defaultSevBVals) {
		this.defaultSevBVals = defaultSevBVals;
	}

	/**
	 * @return the row
	 */
	protected String getRow(int i ) {
		return row[i];
	}

	/**
	 * @param row the row to set
	 */
	protected void setRow(String[] row) {
		this.row = row;
	}

	/**
	 * @return the col
	 */
	protected String[] getCol() {
		return col;
	}

	/**
	 * @param col the col to set
	 */
	protected void setCol(String[] col) {
		this.col = col;
	}

	/**
	 * @return the currentValid
	 */
	protected int getCurrentValid() {
		return currentValid;
	}

	/**
	 * @param gridSize the gridSize to set
	 */
	protected void setCurrentValid(int currentValid) {
		this.currentValid = currentValid;
	}
	
	/**
	 * @return the bestTime
	 */
	protected int getBestTime() {
		return bestTime;
	}

	/**
	 * @param bestTime the time to set
	 */
	protected void setBestTime(int bestTime) {
		this.bestTime = bestTime;
	}

	/**
	 * @return the bestScore
	 */
	protected int getBestScore() {
		return bestScore;
	}

	/**
	 * @param bestScore the score to set
	 */
	protected void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}

	/**
	 * @return the Score
	 */
	protected int getScore() {
		return score;
	}

	/**
	 * @param Score the score to set
	 */
	protected void setScore(int score) {
		this.score = score;
	}

	
	protected String[] returnRows() {
		return getRow();
	}
	public String[] getRow() {
		return row;
	}
	


}
