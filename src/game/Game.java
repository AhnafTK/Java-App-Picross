
package game;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 ****************************************************************************
 * This is the game class that handles the GUI processing of our game		*
 * 																			*
 * @author Skylar Phanenhour, Ahnaf Kamal									*
 ****************************************************************************
 */
public class Game extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		GameModel model = new GameModel();
		GameView view = new GameView();
		GameController controller = new GameController(model,view);
		controller.startController();
		
		
	}
	
}