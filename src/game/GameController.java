package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
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
			view.instructionsButton.setEnabled(false);
		});

		// needs its own action listerner function
		//view.instructionsBack.addActionListener((actionEvent) -> {
			//view.instructionsWindow.dispose();
			//view.instructionsBack.setEnabled(true);
		//});

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

		view.newBoardButton.addActionListener((actionEvent) -> {

		});

		view.resetButton.addActionListener((actionEvent) -> {
			System.out.println("AAA");

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
	
	private void languageActions() {
		
	}

}
