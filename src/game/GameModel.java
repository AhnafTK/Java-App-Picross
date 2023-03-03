package game;

import java.awt.Color;
import java.util.Locale;
import java.util.ResourceBundle;

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
	
	

	
}


