package game;

import java.util.Locale;
import java.util.ResourceBundle;

public class GameModel {
	Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
	/** Resource bundle to get the language messages */
	ResourceBundle langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);
	/** Int variable to hold the grid size, 5x5 by default */
	protected int gridSize = 5;
	/** Boolean for the mark mode, false by default */
	protected boolean markMode = false;
	/** Int variable to check what the current game mode is, 0=design, 1=play */
	protected int gameMode = 0;
	
	protected int time = 0;
	protected int score = 0;
	
	
	/**
	 * @return the time
	 */
	protected int getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	protected void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return the score
	 */
	protected int getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	protected void setScore(int score) {
		this.score = score;
	}

	Locale getLocale() {
		return currentLocale;
		
	};
	
	int getGameMode() {
		return gameMode;
	}
	
	void setGameMode(int gameModeNum) {
		this.gameMode = gameModeNum;
	}
	
	int getGridSize() {
		return gridSize;
	}
	
	void setGridSize(int gridSize) {
		this.gameMode = gridSize;
	}
	
}


