package game;

import java.util.Locale;
import java.util.ResourceBundle;

public class GameModel {
	Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
	/** Resource bundle to get the language messages */
	ResourceBundle langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);
	
	
	Locale getLocale() {
		return currentLocale;
		
	};
}


