package game;

import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.Timer;
/**
 * Beginning of the GameModel class. Responsible for logic/state of the game and its components.
 */
public class GameModel {
	private Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
	/** Resource bundle to get the language messages */
	private ResourceBundle langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);
	/** Int variable to hold the grid size, 5x5 by default */
	protected int gridSize = 5;
	/** Boolean for the mark mode, false by default */
	protected boolean markMode = false;
	/*** Instance of timer.*/
	private Timer timer;
	/*** Holds the game mode. 0 = design/launcher, 1 = play */
	private int gameMode = 0;
	/*** Holds the number of the timer.*/
	private int timerNumber = 0;
	/*** Holds the total number of valid tiles selected.*/
	private int totalValid = 0;
	/*** Holds current number of valid tiles selected.*/
	private int currentValid = 0;
	/*** Holds the best time.*/
	private int bestTime = 0;
	/*** Holds the best score.*/
	private int bestScore = 0;
	/*** Holds the score.*/
	private int score = 0;
	/*** holds seconds and minutes data.*/
	private int seconds, minutes;
	/*** Holds second format and minute format.*/
	private String secFormat, minFormat;
	/*** used to store the user name of the player.*/
	private String username;
	/*** Whether game is started or not.*/
	private boolean gameStarted = false;
	/*** Whether game is finished or not.*/
	private boolean gameFinished = false;
	/*** Default int values for the 5x5 grid*/
	private int[] defaultFiveBVals = { 21, 20, 29, 21, 21 };
	/** Default int values for the 6x6 grid*/
	private int[] defaultSixBVals = { 51, 18, 0, 33, 18, 12 };
	/*** Default int values for the 7x7 grid*/
	private	int[] defaultSevBVals = { 42, 28, 34, 107, 34, 28, 42 };
	/*** Design board. 0's when empty. 1's for when selected.*/
	private String[][] designBoard;
	/*** Stores values(Binary string) of rows*/
	private String[] row; 
	/*** Stores values (binary string) of col*/
	private String[] col;
	/** Holds the number of tiles for the row labels*/
	private String[] rowLabels;
	/** Holds  the number of tiles for the column labels*/
	private String[] colLabels;
	/*** Holds the row label numbers for design mode*/
	private String[] rowLabelsDesign;
	/*** Holds the col label numbers for design mode*/
	private String[] colLabelsDesign;
	
	/**
	 * Converts the timer to seconds format.
	 * @return Converted time.
	 */
	protected int timerToSeconds() {
		seconds = seconds + (minutes * 60);
		bestTime = seconds;
		return bestTime;
	}

	/**
	 * Changes the language of the game.
	 * @param lang The name of the language.
	 * @param region The region related to the language
	 */
	protected void changeLanguage(String lang, String region) {
		this.currentLocale = new Locale.Builder().setLanguage(lang).setRegion(region).build();
		this.langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);
		
	}

	/**
	 * Makes the labels for the rows
	 * @return Array of string containing the generated labels
	 */
	protected String[] rowLabel() {
		rowLabels = new String[gridSize];
		String currentLabel = "";
		int increment = 0;
		// iterate throught the column string and get the labels

		for (int i = 0; i < gridSize; i++) {
			System.out.println("row at i: " + row[i]);
			for (int j = 0; j < gridSize; j++) {
				if (row[i].charAt(j) == '1') {
					increment++;
				} else {
					if (increment != 0) {
						currentLabel += Integer.toString(increment) + " ";
					}
					increment = 0;
				}
				if (j + 1 == gridSize && increment != 0) {
					currentLabel += Integer.toString(increment);
					increment = 0;
				}
			}
			rowLabels[i] = currentLabel;
			currentLabel = "";
		}

		for (int i = 0; i < gridSize; i++) {
			System.out.println(rowLabels[i]);
		}
		return rowLabels;
	}
	
	/**
	 * Makes columns based on rows.
	 * @return Array of string containing generated columns
	 */
	protected String[] colLabel() {
		
		colLabels = new String[gridSize];
		String currentLabel = "";
		int increment = 0;
		// iterate throught the column string and get the labels
		for (int i = 0; i < gridSize; i++) {

			System.out.println("row at i: " + col[i]);
			for (int j = 0; j < gridSize; j++) {
				if (col[i].charAt(j) == '1') {
					increment++;
				} else {
					if (increment != 0) {
						currentLabel += Integer.toString(increment) + " ";
					}
					increment = 0;
				}
				if (j + 1 == gridSize && increment != 0) {
					currentLabel += Integer.toString(increment);
					increment = 0;
				}
			}
			colLabels[i] = currentLabel;
			currentLabel = "";
		}

		return colLabels;
	}

	/**
	 * Finds the number of valid tiles by iterating through the rows and counting the 1's
	 */
	protected void totalValidTiles() {
		
		totalValid = 0;
		for (int a = 0; a < gridSize; a++) {
			for (int b = 0; b < gridSize; b++) {
				if (row[a].charAt(b) == '1') { // if 1
					totalValid = totalValid + 1;
				}
			}
		}
	}

	/**
	 * Copies the designBoard row by row and returns the final concatenated string. (designMode)
	 * @return String containing the row data of 0's and 1's
	 */
	protected String writeDesignPattern() {
		String pattern = "";
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				pattern = pattern + getDesignBoard()[i][j];
			}
			pattern = pattern + "\n";
		}
		return pattern;
	}

	/**
	 * Copies the play board row by row and returns the final concatenated string. (play mode)
	 * @return String containing the row data of 0's and 1's
	 */
	protected String writePattern() {
		String pattern = "";
		for (int i = 0; i < gridSize; i++) {
			pattern = pattern + row[i] + "\n";
		}
		return pattern;
	}


	/**
	 * Reads a given file, and gathers necessary info to update the board.
	 * @param fileReader Scanner used to read the file.
	 */
	protected void readFile(Scanner fileReader) {
		System.out.println("READING FILE FOR PLAY");
		// get gridsize first
		gridSize = fileReader.nextInt();
		row = new String[gridSize];
		
		fileReader.nextLine();
		
		for (int i = 0; i < gridSize; i++) {
			row[i] = fileReader.nextLine();
		}
		
		if (fileReader.hasNextLine()) {
			bestScore = fileReader.nextInt();
			bestTime = fileReader.nextInt();

			fileReader.nextLine();
			String name = fileReader.nextLine();
			
			if (!name.equals(null)) {
				username = name;
				JOptionPane.showMessageDialog(null, "Read a file with username: " + username + "\nBest Score: "
						+ bestScore + "\nBest Time: " + bestTime);
			} else {
				setUsername(fileReader.nextLine());
				JOptionPane.showMessageDialog(null, "Read a file with best Score: " + bestScore + "\nBest Time: " + bestTime);
			}

		}
	}

	/**
	 * Reading a file in design mode.
	 * @param fileReader Used to read the file.
	 */
	protected void readFileDesign(Scanner fileReader) {
		gridSize = fileReader.nextInt();
		designBoard = new String[gridSize][gridSize];
		row = new String[gridSize];
		fileReader.nextLine();

		for (int i = 0; i < gridSize; i++) {
			row[i] = fileReader.nextLine();
			for (int j = 0; j < gridSize; j++) {
				getDesignBoard()[i][j] = String.valueOf(row[i].charAt(j));
			}
		}
	}

	/**
	 * Converts an int into binary as String datatype.
	 * @param value The value to be converted.
	 * @return Binary string
	 */
	protected String intToBinary(int value) {
		String binVal = Integer.toBinaryString(value);
		while (binVal.length() < gridSize) {
			binVal = "0" + binVal;
		}
		return binVal;
	}

	/**
	 * Initializes the RowLabelDesign array which contains the row labels, with 0's
	 * @return String array containing the row labels
	 */
	protected String[] makeRowLabelDesign() {
		rowLabelsDesign = new String[gridSize];
		for (int i = 0; i < gridSize; i++) {
			rowLabelsDesign[i] = "0";
		}
		return rowLabelsDesign;
	}

	/**
	 * Initializes the colLabelDesign array which contains the col labels, with 0's
	 * @return String array containing the col labels
	 */
	protected String[] makeColLabelDesign() {
		colLabelsDesign = new String[gridSize];
		for (int i = 0; i < gridSize; i++) {
			colLabelsDesign[i] = "0";
		}
		return colLabelsDesign;
	}

	/**
	 * Generates the rows given maxPossible and boolean useDefault. 
	 * @param maxPossible maximum possible int that can be generated based on gridSize. 
	 * @param useDefault True when using default values, false when random numbers are to be generated.
	 * @return row with the generated values.
	 */
	protected String[] generateRows(int maxPossible, boolean useDefault) {

		row = new String[gridSize];
		
		if (useDefault == true) {
			switch (gridSize) {
			case 5:
				for (int i = 0; i < gridSize; i++) {
					row[i] = intToBinary(defaultFiveBVals[i]);
				}
				break;
			case 6:
				for (int i = 0; i < gridSize; i++) {
					row[i] = intToBinary(defaultSixBVals[i]);
				}
				break;
			case 7:
				for (int i = 0; i < gridSize; i++) {
					row[i] = intToBinary(defaultSevBVals[i]);
				}
				break;
			}
		} else {
			for (int i = 0; i < gridSize; i++) {
				Random rand = new Random();
				int value = rand.nextInt(maxPossible);
				row[i] = intToBinary(value);
			}
		}
		return row;

	}

	/**
	 * Generate columns based on the rows
	 * @return Return generated column.
	 */
	protected String[] generateCols() {
		
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
		return col;
	}


	/**
	 * Makes the board for design.
	 * @param gridSize Size of the board.
	 */
	void makeDesignBoard(int gridSize) {
		designBoard = new String[gridSize][gridSize];
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				getDesignBoard()[i][j] = "0";
			}
		}
	}

	/**
	 * Updates the row labels when change is detected on the board in design mode.
	 * @param i index i, accesses rows.
	 * @param j index j, accesses items in rows.
	 * @return returned the string containing updated tile numbers.
	 */
	String updateRow(int i, int j) {
		
		StringBuilder builder = new StringBuilder();
		for (String row : getDesignBoard()[i]) {
			if (row != null) {
				builder.append(row);
			}
		}
		String newRow = builder.toString();
		String updatedRowLabel = "";
		
		int inc = 0;
		
		for (int c = 0; c < gridSize; c++) {
			if (newRow.charAt(c) == '1') {
				inc++;
			} else {
				if (inc != 0) {
					updatedRowLabel += Integer.toString(inc) + " ";
				}
				System.out.println("hit 0: inc is  " + inc);
				inc = 0;
			}
			if ((c + 1) == gridSize && inc != 0) {
				updatedRowLabel += inc;
			}

		}

		rowLabelsDesign[i] = updatedRowLabel;
		return updatedRowLabel;
		
	}

	/**
	 * Updates the col labels when change is detected on the board in design mode.
	 * @param i index i, accesses rows.
	 * @param j index j, accesses items in rows.
	 * @return returned the string containing updated tile numbers.
	 */
	String updateCol(int i, int j) {
		
		StringBuilder builder = new StringBuilder();
		System.out.println("DESIGN BOARD LENGTH WHEN UPDATECOL === " + getDesignBoard().length);
		for (int k = 0; k < gridSize; k++) {
			if (getDesignBoard()[k][j] != null) {
				builder.append(getDesignBoard()[k][j]);
				System.out.println("K " + k + " j " + j + " " + getDesignBoard()[k][j]);
			}
		}
		String updatedColLabel = "";
		String newCol = builder.toString();

		int inc = 0;
		for (int c = 0; c < gridSize; c++) {
			if (newCol.charAt(c) == '1') {
				inc++;
			} else {
				if (inc != 0) {
					updatedColLabel += Integer.toString(inc) + " ";
				}
				inc = 0;
			}
			// if im nearing the end
			if ((c + 1) == gridSize && inc != 0) {
				updatedColLabel += inc;
			}
		}

		return updatedColLabel;
	}

	/**
	 * Updates the design board by setting selected tiles as 1
	 * @param i Index i of the array (row)
	 * @param j Index j of the row.
	 */
	protected void updateDesignBoard(int i, int j) {
		designBoard[i][j] = "1";
	}

	/**
	 * Resets the board by setting the labels array to zero.
	 */
	protected void resetBoard() {
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
	 * @return the seconds
	 */
	protected int getSeconds() {
		return seconds;
	}

	/**
	 * Sets the seconds
	 * @param seconds the seconds to set
	 */
	protected void setSeconds(int seconds) {
		this.seconds = seconds;
	}

	/**
	 * gets the minutes
	 * @return the minutes
	 */
	protected int getMinutes() {
		return minutes;
	}

	/**
	 * Sets the minutes
	 * @param minutes the minutes to set
	 */
	protected void setMinutes(int minutes) {
		this.minutes = minutes;
	}

	/**
	 * Returns secFormat
	 * @return the secFormat
	 */
	protected String getSecFormat() {
		return secFormat;
	}

	/**
	 * Sets secFormat
	 * @param secFormat the secFormat to set
	 */
	protected void setSecFormat(String secFormat) {
		this.secFormat = secFormat;
	}

	/**
	 * gets minFormat
	 * @return the minFormat
	 */
	protected String getMinFormat() {
		return minFormat;
	}

	/**
	 * sets MinFormat
	 * @param minFormat the minFormat to set
	 */
	protected void setMinFormat(String minFormat) {
		this.minFormat = minFormat;
	}

	/**
	 * gets gameStarted
	 * @return the gameStarted
	 */
	protected boolean isGameStarted() {
		return gameStarted;
	}

	/**
	 * sets gameStarted
	 * @param gameStarted the gameStarted to set
	 */
	protected void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	/**
	 * gets the row array at an index
	 * @param i Index
	 * @return the row
	 */
	protected String getRow(int i) {
		return row[i];
	}

	/**
	 * sets the row array
	 * @param row the row to set
	 */
	protected void setRow(String[] row) {
		this.row = row;
	}

	/**
	 * Gets the col array.
	 * @return the col
	 */
	protected String[] getCol() {
		return col;
	}

	/**
	 * Sets the col
	 * @param col the col to set
	 */
	protected void setCol(String[] col) {
		this.col = col;
	}

	/**
	 * Gets the currentValid variable.
	 * @return the currentValid
	 */
	protected int getCurrentValid() {
		return currentValid;
	}

	/**
	 * Sets the currentValid variable
	 * @param currentValid the value to set it to.
	 */
	protected void setCurrentValid(int currentValid) {
		this.currentValid = currentValid;
	}

	/**
	 * gets the bestTime variable.
	 * @return the bestTime
	 */
	protected int getBestTime() {
		return bestTime;
	}

	/**
	 * Sets the bestTime variable.
	 * @param bestTime the time to set
	 */
	protected void setBestTime(int bestTime) {
		this.bestTime = bestTime;
	}

	/**
	 * Gets the best score variable
	 * @return the bestScore
	 */
	protected int getBestScore() {
		return bestScore;
	}

	/**
	 * Sets the bestScore varaible.
	 * @param bestScore the score to set
	 */
	protected void setBestScore(int bestScore) {
		this.bestScore = bestScore;
	}

	/**
	 * Returns the score variable
	 * @return the Score
	 */
	protected int getScore() {
		return score;
	}

	/**
	 * Sets the score variable.
	 * @param Score the score to set
	 */
	protected void setScore(int score) {
		this.score = score;
	}

	/**
	 * Gets the row array
	 * @return the row array.
	 */
	public String[] getRow() {
		return row;
	}
	
	/**
	 * Gets the username
	 * @return the username
	 */
	protected String getUsername() {
		return username;
	}

	/**
	 * Sets the userName
	 * @param username the username to set
	 */
	protected void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets gameFinished status.
	 * @return the gameFinished
	 */
	protected boolean getGameFinished() {
		return gameFinished;
	}

	/**
	 * Sets gameFinished status.
	 * @param gameFinished the gameFinished to set
	 */
	protected void setGameFinished(boolean gameFinished) {
		this.gameFinished = gameFinished;
	}

	/**
	 * Returns game mode.
	 * @return the gameMode
	 */
	protected int getGameMode() {
		return gameMode;
	}
	
	/**
	 * Sets the gameMode.
	 * @param gameMode the gameMode to set
	 */
	protected void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	/**
	 * returns markMode.
	 * @return the markMode
	 */
	protected boolean isMarkMode() {
		return markMode;
	}

	/**
	 * Sets mark mode.
	 * @param markMode the markMode to set
	 */
	protected void setMarkMode(boolean markMode) {
		this.markMode = markMode;
	}

	/**
	 * Returns the locale.
	 * @return the currentLocale
	 */
	protected Locale getCurrentLocale() {
		return currentLocale;
	}

	/**
	 * Sets the Locale
	 * @param currentLocale the currentLocale to set
	 */
	protected void setCurrentLocale(Locale currentLocale) {
		this.currentLocale = currentLocale;
	}

	/**
	 * Returns the current langText.
	 * @return the langText
	 */
	protected ResourceBundle getLangText() {
		return langText;
	}

	/**
	 * Sets the resourceBundle.
	 * @param langText the langText to set
	 */
	protected void setLangText(ResourceBundle langText) {
		this.langText = langText;
	}

	/**
	 * Returns the total valid number.
	 * @return totalValid: containing the total number of valids.
	 */
	public int getTotalValid() {
		return totalValid;
	}

	/**
	 * Gets the designBoard.
	 * @return designBoard array.
	 */
	public String[][] getDesignBoard() {
		return designBoard;
	}
}
