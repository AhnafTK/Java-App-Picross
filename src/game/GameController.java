package game;

import java.awt.Color;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.Locale;

import javax.swing.JButton;

import java.util.ResourceBundle;
import javax.swing.Timer;

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
		leftPanelActions();
		view.markCheckBox.addActionListener((actionEvent) -> {
			// System.out.println("AAA");
			if (view.markCheckBox.isSelected()) {
				if (model.getGameMode() == 0) {
					view.history.append(view.langText.getString("mark") + ": " + view.langText.getString("true") + "\n");
					model.setMarkMode(true);
				}
			} else {
				view.history.append(view.langText.getString("mark") + ": " + view.langText.getString("false") + "\n");
				model.setMarkMode(false);

			}
		});
		boardActions();
		gridSizeActions();
	}

	private void playActions() {
		model.setGameMode(1);
		gridSizeActions();
		leftPanelActions();
		markCheckBoxAction(); // checkbox features
		boardActions();
		timerCounter();
		model.timer.start();
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
		for (JButton[] i : view.buttons) {
			for (JButton j : i) {
				j.addActionListener((actionEvent) -> {
					if (model.isMarkMode()) {
						j.setBackground(new Color(226, 222, 222));
					} else {
						j.setEnabled(false);
						j.setBackground(new Color(17, 15, 15));
					}
				});
			}
		}
	}

	private void markCheckBoxAction() {
		view.markCheckBox.addActionListener((actionEvent) -> {
			// System.out.println("AAA");
			if (view.markCheckBox.isSelected()) {
				if (model.getGameMode() == 1) {
					view.history.append(view.langText.getString("mark") + ": " + view.langText.getString("true") + "\n");
					model.setMarkMode(true);
				}

			} else {
				view.history.append(view.langText.getString("mark") + ": " + view.langText.getString("false") + "\n");
				model.setMarkMode(false);

			}
		});
	}

	private void languageActions() {

	}
	
	private void gridSizeActions() {

		view.gridSizeCmbo.addActionListener((actionEvent) -> {
			System.out.println(model.getGameMode());
			String options = (String) view.gridSizeCmbo.getSelectedItem();
			switch (options) {

			case "5x5":
				view.history.append(view.langText.getString("upon_grid_change") + " 5x5\n");
				model.gridSize = 5;
				if (model.getGameMode() == 1) {
					view.picrossWindow.remove(view.boardPanel);
					view.picrossWindow.add(view.makeBoardPanel(5,model.isMarkMode()));
					view.boardPanel.revalidate();
				} else {
					view.designWindow.remove(view.boardPanel);
					view.designWindow.add(view.makeBoardPanel(5,model.isMarkMode()));
					view.boardPanel.revalidate();
				}
				boardActions();
				markCheckBoxAction();
				break;

			// "6x6" option
			case "6x6":
				view.history.append(view.langText.getString("upon_grid_change") + " 6x6\n");
				model.gridSize = 6;
				if (model.getGameMode() == 1) {
					view.picrossWindow.remove(view.boardPanel);
					view.picrossWindow.add(view.makeBoardPanel(6, model.isMarkMode()));
					view.boardPanel.revalidate();
				} else {
					view.designWindow.remove(view.boardPanel);
					view.designWindow.add(view.makeBoardPanel(6,model.isMarkMode()));
					view.boardPanel.revalidate();
				}
				boardActions();
				markCheckBoxAction();
				break;

			case "7x7":
				view.history.append(view.langText.getString("upon_grid_change") + " 7x7\n");
				model.gridSize = 7;
				if (model.getGameMode() == 1) {
					view.picrossWindow.remove(view.boardPanel);
					view.picrossWindow.add(view.makeBoardPanel(7,model.isMarkMode()));
					view.boardPanel.revalidate();
				} else {
					view.designWindow.remove(view.boardPanel);
					view.designWindow.add(view.makeBoardPanel(7,model.isMarkMode()));
					view.boardPanel.revalidate();
				}
				boardActions();
				markCheckBoxAction();
				break;
			}
		});

	}
	
	private void timerCounter() {
		model.seconds = 0;
		model.minutes = 0;
		DecimalFormat dFormat = new DecimalFormat("00");
		model.timer = new Timer(1000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				model.seconds++;
				
				if (model.seconds == 60) {
					model.minutes++;
					model.seconds = 0;
				}
				
				model.secFormat = dFormat.format(model.seconds);
				model.minFormat = dFormat.format(model.minutes);

				view.timerCounter.setText(model.minFormat + ":" + model.secFormat);
			}
		});
	}
	
	private void leftPanelActions() {
		
		view.engRadio.addActionListener((actionEvent) -> {

			model.currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
			model.langText = ResourceBundle.getBundle("MessagesBundle", model.currentLocale);

			view.updateText(model.currentLocale, model.langText);
			view.leftPanel.revalidate();
			view.history.append("\n" + model.langText.getString("upon_lang_change") + model.langText.getString("english") + "\n");


		});
		view.frRadio.addActionListener((actionEvent) -> {

			model.currentLocale = new Locale.Builder().setLanguage("fr").setRegion("FR").build();
			model.langText = ResourceBundle.getBundle("MessagesBundle", model.currentLocale);

			view.updateText(model.currentLocale, model.langText);
			view.leftPanel.revalidate();
			view.history.append("\n" + model.langText.getString("upon_lang_change") + model.langText.getString("french") + "\n");


		});
		
		view.playToLauncher.addActionListener((actionEvent) -> {
			model.setMarkMode(false);
			if (model.getGameMode() == 1) {
				view.picrossWindow.dispose();
				view.launcher();
				launcherActions();
				model.timer.stop();
			}
			else {
				view.designWindow.dispose();
				System.out.println("clicked design back button");
				view.launcher();
				launcherActions();
			}
			
		});
		
		view.resetButton.addActionListener((actionEvent) -> {
			for (JButton[] i : view.buttons) {
				for (JButton j : i) {
					j.setBackground(Color.WHITE);
					j.setEnabled(true);
				}
			}

		});
		
		view.solveButton.addActionListener((actionEvent) -> {
			view.history.append(model.langText.getString("upon_click") + model.langText.getString("button")
					+ model.langText.getString("solve") + "\n");
		});

		view.instructionsButton.addActionListener((actionEvent) -> {
			view.Instructions(model.currentLocale);
			view.instructionsButton.setEnabled(false);
			instructionsActions();
		});
		
		view.newBoardButton.addActionListener((actionEvent) -> {

		});

	}
}
