package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFrame;

import java.util.ResourceBundle;

public class GameController {
	GameModel model;
	GameView view;

	public GameController(GameModel model, GameView view) {
		// TODO Auto-generated constructor stub
		this.model = model;
		this.view = view;
	}

	protected void startController() {
		launcherActions();
	}

	private void launcherActions() {
		view.playButton.addActionListener((actionEvent) -> {
			System.out.println("clickd play");
			view.startWindow.dispose();
			view.Play();
			playActions();
		});

		view.designButton.addActionListener((actionEvent) -> {
			System.out.println("clicked design button");
			view.startWindow.dispose();
			view.Design();
			designActions();
		});
	}

	private void designActions() {
		model.setGameMode(0);

		view.designBack.addActionListener((actionEvent) -> {
			view.designWindow.dispose();
			System.out.println("clicked design back button");
			view.launcher();
			launcherActions();
		});
		view.markCheckBox.addActionListener((actionEvent) -> {
			//System.out.println("AAA");
			if (view.markCheckBox.isSelected()) {
				if (model.getGameMode() == 0) {
					view.history.append(view.langText.getString("mark") + ": " + view.langText.getString("true") + "\n");
					model.setMarkMode(true);
				}
				
			}
			else {
				view.history.append(view.langText.getString("mark") + ": " + view.langText.getString("false") + "\n");
				model.setMarkMode(false);

			}
		});
		boardActions();

	}

	private void playActions() {
		model.setGameMode(1);
		view.playToLauncher.addActionListener((actionEvent)->{
			view.picrossWindow.dispose();
			view.launcher();
			launcherActions();
		});
		
		view.gridSizeCmbo.addActionListener((actionEvent)->{
			String options = (String) view.gridSizeCmbo.getSelectedItem();

			switch (options) {
			
			case "5x5":
				view.history.append(view.langText.getString("upon_grid_change")  + " 5x5\n");
				model.gridSize = 5;
				view.picrossWindow.remove(view.boardPanel);
				view.picrossWindow.add(view.makeBoardPanel(5));
				view.boardPanel.revalidate();
				boardActions();
				markCheckBoxAction();
				break;

			// "6x6" option
			case "6x6":
				view.history.append(view.langText.getString("upon_grid_change")  + " 6x6\n");
				model.gridSize = 6;
				view.picrossWindow.remove(view.boardPanel);
				view.picrossWindow.add(view.makeBoardPanel(6));
				view.boardPanel.revalidate();
				boardActions();
				markCheckBoxAction();
				break;
				
			case "7x7":
				view.history.append(view.langText.getString("upon_grid_change")  + " 7x7\n");
				model.gridSize = 7;
				view.picrossWindow.remove(view.boardPanel);
				view.picrossWindow.add(view.makeBoardPanel(7));
				view.boardPanel.revalidate();
				boardActions();
				markCheckBoxAction();
				break;
			}
		});
		
		view.engRadio.addActionListener((actionEvent) -> {
			//System.out.println("AAA");
			if (model.gameMode == 1) {
				view.history.append("\n" + view.langText.getString("upon_lang_change") + view.langText.getString("english")  + "\n");

			}
			model.currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
			model.langText = ResourceBundle.getBundle("MessagesBundle", model.currentLocale);

			view.updateText(model.currentLocale, model.langText);
			view.leftPanel.revalidate();

			
		});
		view.frRadio.addActionListener((actionEvent) -> {
			if (model.gameMode == 1) {
				view.history.append("\n" + view.langText.getString("upon_lang_change") + view.langText.getString("french")  + "\n");

			}
			model.currentLocale = new Locale.Builder().setLanguage("fr").setRegion("FR").build();			
			model.langText = ResourceBundle.getBundle("MessagesBundle", model.currentLocale);

			view.updateText(model.currentLocale, model.langText);
			view.leftPanel.revalidate();

		});
		
		view.instructionsButton.addActionListener((actionEvent) -> {
			view.Instructions(model.currentLocale);
			view.instructionsButton.setEnabled(false);
			instructionsActions();
		});

		// needs its own action listerner function
		

		markCheckBoxAction(); // checkbox features

		view.newBoardButton.addActionListener((actionEvent) -> {

		});

		view.resetButton.addActionListener((actionEvent) -> {
			for(JButton[] i:view.buttons) {
				for(JButton j: i) {
					j.setBackground(Color.WHITE);
					j.setEnabled(true);
				}
			}

		});

		view.solveButton.addActionListener((actionEvent) -> {
			view.history.append(model.langText.getString("upon_click") + model.langText.getString("button")
					+ model.langText.getString("solve") + "\n");
		});
		
		boardActions();
		//for (int i = 0; i< view.gridSize; i++) {
			//for (int j = 0; j<view.gridSize; j++) {
				//view.buttons[i][j].addActionListener((actionEvent) -> {
					//view.buttons[i][j].setBackground(Color.red);
					//System.out.println("A");
				//});
			//}
		//}
		
	}
	
	void addBoardListener(ActionListener listenForCalcButton) {
		
		for (int i = 0; i < view.buttons.length; i++) {
			
		}
		//view.buttons[][].addActionListener(listenForCalcButton);
		
	}
	
	private void performViewActions() {
		launcherActions();		
	}
	private void instructionsActions() {
		view.instructionsBack.addActionListener((actionEvent) -> {
			view.instructionsWindow.dispose();
			view.instructionsButton.setEnabled(true);
		});
	}
	private void boardActions() {
		for(JButton[] i:view.buttons) {
			for(JButton j: i) {
				j.addActionListener((actionEvent) -> {
					if (model.isMarkMode()) {
						j.setBackground(new Color(226,222,222));
						System.out.println("int mark mode");
					}
					else {
						j.setEnabled(false);
						j.setBackground(new Color(17,15,15));
					}
					
					//j.setBackground(Color.red);
				});
			}
		}
	}
	private void markCheckBoxAction() {
		view.markCheckBox.addActionListener((actionEvent) -> {
			//System.out.println("AAA");
			if (view.markCheckBox.isSelected()) {
				if (model.getGameMode() == 1) {
					view.history.append(view.langText.getString("mark") + ": " + view.langText.getString("true") + "\n");
					model.setMarkMode(true);
				}
				
			}
			else {
				view.history.append(view.langText.getString("mark") + ": " + view.langText.getString("false") + "\n");
				model.setMarkMode(false);

			}
		});
	}
	private void languageActions() {
		
	}

}
