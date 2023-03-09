package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.Timer;

public class GameController {
	GameModel model;
	GameView view;
	
	public GameController(GameModel model, GameView view) {
		// TODO Auto-generated constructor stub
		this.model = model;
		this.view = view;
	}

	protected void startController() {splashActions();}

	private void splashActions() {
		view.startLauncher(model.currentLocale, model.langText);			
		launcherActions();
	}
	
	private void launcherActions() {
		view.playButton.addActionListener((actionEvent) -> {
			// intialize default model
			view.startWindow.dispose();
			view.Play(model.currentLocale,model.langText);
			playActions();
		});

		view.designButton.addActionListener((actionEvent) -> {
			model.gameStarted = true;
			System.out.println("clicked design button");
			view.startWindow.dispose();
			view.Design(model.currentLocale, model.langText);
			designActions();
		});
		
		view.engRadio.addActionListener((actionEvent) -> {
			model.setCurrentLocale(new Locale.Builder().setLanguage("en").setRegion("US").build());
			model.setLangText(ResourceBundle.getBundle("MessagesBundle", model.currentLocale));
			updateLauncherLanguage();
		});
		
		view.frRadio.addActionListener((actionEvent) -> {
			model.setCurrentLocale(new Locale.Builder().setLanguage("fr").setRegion("FR").build());
			model.setLangText(ResourceBundle.getBundle("MessagesBundle", model.currentLocale));
			updateLauncherLanguage();
		});	
	}

	private void updateLauncherLanguage() {
		view.designButton.setText(model.langText.getString("design"));
		view.playButton.setText(model.langText.getString("play"));
		view.engRadio.setText(model.langText.getString("english"));
		view.frRadio.setText(model.langText.getString("french"));
	}
	private void designActions() {
		model.setGameMode(0);
		leftPanelActions();
		markCheckBoxAction();
		boardActions();
		gridSizeActions();
		menuBarActions();
	}

	private void playActions() {
		model.setGameMode(1);
		gridSizeActions();
		leftPanelActions();
		markCheckBoxAction(); // checkbox features
		newBoard("5x5",true);
		menuBarActions();
	}

	private void instructionsActions() {
		view.instructionsBack.addActionListener((actionEvent) -> {
			view.instructionsWindow.dispose();
			view.instructionsButton.setEnabled(true);
		});
	}

	private void boardActions() {

		ActionListener listener = new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	for (int i = 0; i < model.getGridSize(); i++) {
					for (int j = 0; j < model.getGridSize(); j++) {
						if (e.getSource() == view.buttons[i][j]) {
							if (model.getGameMode() == 0) {
								if (model.isMarkMode()) {
									view.buttons[i][j].setBackground(new Color(226, 222, 222));
								} 
								else {
									view.buttons[i][j].setBackground(new Color(17, 15, 15));
								}
								view.buttons[i][j].setEnabled(false);
							}
							else {
								if (model.isMarkMode()) {
									view.buttons[i][j].setBackground(view.mark_color);
								} else {
									if (model.isGameStarted() == false) {
										timerCounter(); 
										model.timer.start();	
									}
									if (model.getRow(i).charAt(j) == '1') {
										System.out.println("correct");
										model.setScore(model.getScore()+1);
										view.buttons[i][j].setBackground(view.tile_color);
									}
									else {
										model.setScore(model.getScore()-1);
										view.buttons[i][j].setBackground(view.err_color);
										System.out.println("false");
									}
									view.scoreCounter.setText(Integer.toString(model.getScore())); 
									view.buttons[i][j].setEnabled(false);
								}	
							}
						}
					}
				}
	        }
	    };
	    
		for (JButton[] i : view.buttons) {
			//indexi++;
			for (JButton j : i) {
				j.addActionListener(listener);
			}
		}
	}
	
	private void markCheckBoxAction() {
		view.markCheckBox.addActionListener((actionEvent) -> {
			if (view.markCheckBox.isSelected()) {
				view.history.append(model.langText.getString("mark") + ": " + model.langText.getString("true") + "\n");
				model.setMarkMode(true);
			} else {
				view.history.append(model.langText.getString("mark") + ": " + model.langText.getString("false") + "\n");
				model.setMarkMode(false);
			}
		});
	}

	private void languageActions() {
		view.engRadio.addActionListener((actionEvent) -> {
			model.setCurrentLocale(new Locale.Builder().setLanguage("en").setRegion("US").build());
			model.setLangText(ResourceBundle.getBundle("MessagesBundle", model.currentLocale));
			view.updateText(model.getCurrentLocale(), model.getLangText());
			view.leftPanel.revalidate();
			view.history.append("\n" + model.langText.getString("upon_lang_change") + model.langText.getString("english") + "\n");
		});
		view.frRadio.addActionListener((actionEvent) -> {
			model.setCurrentLocale(new Locale.Builder().setLanguage("fr").setRegion("FR").build());
			model.setLangText(ResourceBundle.getBundle("MessagesBundle", model.currentLocale));
			view.updateText(model.currentLocale, model.langText);
			view.leftPanel.revalidate();
			view.history.append("\n" + model.langText.getString("upon_lang_change") + model.langText.getString("french") + "\n");
		});
	}
	
	private void gridSizeActions() {
		view.gridSizeCmbo.addActionListener((actionEvent) -> {
			System.out.println(model.getGameMode());
			String options = (String) view.gridSizeCmbo.getSelectedItem();
			changeGridSize(options);
		});
	}
	
	/*
	 * https://www.youtube.com/watch?v=zWw72j-EbqI&list=PL_ql-Q0xEccQzbifFP2SI5y3a_LkNaxA3&index=4
	 */
	private void timerCounter() {
		model.setGameStarted(true);
		model.setSeconds(0);
		model.setMinutes(0);
		DecimalFormat dFormat = new DecimalFormat("00");
		model.timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.seconds++;
				if (model.getSeconds() == 60) {
					model.setMinutes(model.getMinutes()+1);
					model.setSeconds(0);
				}
				model.setSecFormat(dFormat.format(model.getSeconds()));
				model.setMinFormat(dFormat.format(model.getMinutes()));
				view.timerCounter.setText(model.getMinFormat() + ":" + model.getSecFormat());
			}
		});
	}
	
	private void backToLauncher() {
		model.setMarkMode(false);
		if (model.getGameMode() == 1) {
			view.picrossWindow.dispose();
			view.launcher(model.getLangText(), model.getCurrentLocale());
			launcherActions();
			if (model.isGameStarted() == false) {
				return;
			}
			else {
				model.timer.stop();
				model.setGameStarted(false);
				view.timerCounter.setText("00:00");
			}
		}
		else {
			model.setGameStarted(false);
			view.designWindow.dispose();
			System.out.println("clicked design back button");
			view.launcher(model.langText, model.currentLocale);
			launcherActions();
		}
	}
	
	private void leftPanelActions() {
		
		languageActions();
		
		view.playToLauncher.addActionListener((actionEvent) -> {
			model.setMarkMode(false);
			if (model.getGameMode() == 1) {
				view.picrossWindow.dispose();
				view.launcher(model.langText, model.currentLocale);
				launcherActions();
				if (model.isGameStarted() == false) {
					return;
				}
				else {
					model.timer.stop();
					model.setGameStarted(false);
					view.timerCounter.setText("00:00");
				}
			}
			else {
				model.setGameStarted(false);
				view.designWindow.dispose();
				System.out.println("clicked design back button");
				view.launcher(model.langText, model.currentLocale);
				launcherActions();
			}
			
		});
		
		view.resetButton.addActionListener((actionEvent) -> {resetBoard();});
		view.solveButton.addActionListener((actionEvent) -> {
			view.history.append(model.langText.getString("upon_click") + model.langText.getString("button")
					+ model.langText.getString("solve") + "\n");
			view.solveBoard(model.row, model.gridSize);
		});
		view.instructionsButton.addActionListener((actionEvent) -> {showInstructions();});
		view.newBoardButton.addActionListener((actionEvent) -> {
			view.history.append(model.langText.getString("upon_click") + model.langText.getString("button")
			+ model.langText.getString("newBoard") + "\n");
			newBoard((String) view.gridSizeCmbo.getSelectedItem(),false);
		});

	}
	
	private void menuBarActions() {
	
		view.saveMenuOption.addActionListener((actionEvent)->{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file;
				//PrintWriter fileOut;
				
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());			
			}
		});
		view.loadMenuOption.addActionListener((actionEvent)->{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file;
				//PrintWriter fileOut;
				
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				
			}
		});
		view.newMenuOption.addActionListener((actionEvent)->{newBoard((String) view.gridSizeCmbo.getSelectedItem(),false);});
		
		view.resetMenuOption.addActionListener((actionEvent)->{resetBoard();});
		
		view.solveMenuOption.addActionListener((actionEvent)->{view.solveBoard(model.row, model.gridSize);});
		
		view.fiveFive.addActionListener((actionEvent)->{changeGridSize("5x5");});
		
		view.sixSix.addActionListener((actionEvent)->{changeGridSize("6x6");});
		
		view.sevSev.addActionListener((actionEvent)->{changeGridSize("7x7");});
		
		view.toLauncherMenuOption.addActionListener((actionEvent)->{backToLauncher();});
		
		view.exitMenuOption.addActionListener((actionEvent)->{System.exit(0);});
		
		view.aboutMenuOption.addActionListener((actionEvent)->{showInstructions();});
		
		view.backgroundColour.addActionListener((actionEvent) -> {
			Color colour = JColorChooser.showDialog(null, "Pick a color...", Color.black);
			view.boardPanel.setBackground(colour);
			view.leftPanel.setBackground(colour);
			view.languagePanel.setBackground(colour);
			view.languageButtonPanel.setBackground(colour);
			view.gridSizeComboPanel.setBackground(colour);
			view.scorePanel.setBackground(colour);
			view.timerPanel.setBackground(colour);
			view.buttonPanel.setBackground(colour);
			view.configurationPanel.setBackground(colour);
			view.historyPanel.setBackground(colour);
			view.controlPanel.setBackground(colour);
			view.rowPanel.setBackground(colour);
			view.colPanel.setBackground(colour);
			view.engRadio.setBackground(colour);
			view.frRadio.setBackground(colour);
			view.markCheckBox.setBackground(colour);
		});
		
		
		view.textColour.addActionListener((actionEvent) -> {
			Color colour = JColorChooser.showDialog(null, "Pick a color...", Color.black);
			view.scoreLabel.setForeground(colour);
			view.scoreCounter.setForeground(colour);
			view.timerLabel.setForeground(colour);
			view.timerCounter.setForeground(colour);
			view.gridSizeLabel.setForeground(colour);
			view.gridSizeCmbo.setForeground(colour);
			view.playToLauncher.setForeground(colour);
			view.solveButton.setForeground(colour);
			view.resetButton.setForeground(colour);
			view.newBoardButton.setForeground(colour);
			view.instructionsButton.setForeground(colour);
			view.langLabel.setForeground(colour);
			view.engRadio.setForeground(colour);
			view.frRadio.setForeground(colour);
			view.markCheckBox.setForeground(colour);
		});
		
		view.componentColour.addActionListener((actionEvent) -> {			
			Color colour = JColorChooser.showDialog(null, "Pick a color...", Color.black);
			view.scoreCounter.setBackground(colour);
			view.timerCounter.setBackground(colour);
			view.gridSizeCmbo.setBackground(colour);
			view.playToLauncher.setBackground(colour);
			view.resetButton.setBackground(colour);
			view.solveButton.setBackground(colour);
			view.newBoardButton.setBackground(colour);
			view.instructionsButton.setBackground(colour);
			view.history.setBackground(colour);
			for (JButton[] i : view.getButtons()) {
				for (JButton j : i) {
					if(j.isEnabled()) {
						j.setBackground(colour);
					}
				}
			}
		});
		
		
	}
	
	private void resetBoard() {
		view.history.append(model.langText.getString("upon_click") + model.langText.getString("button")
	    + model.langText.getString("reset") + "\n");
	    view.resetBoard();
	}
	
	private void showInstructions() {
		view.history.append(model.langText.getString("upon_click") + model.langText.getString("button")
		+ model.langText.getString("instructions") + "\n");
		view.Instructions(model.currentLocale, model.langText);
		view.instructionsButton.setEnabled(false);
		instructionsActions();
	}
	
	private void newBoard(String options, boolean isDefault) {
		int maxPossible;
		if (isDefault == false && model.isGameStarted() == true && model.getGameMode() == 1) {
			model.setScore(0);
			view.getScoreCounter().setText(Integer.toString(model.getScore()));
	        model.timer.stop();
	        model.setGameStarted(false);
	        view.getTimerCounter().setText("00:00");
	    }
		
		switch (options) {
		case "5x5":
			model.setGridSize(5);
			break;
			
		case "6x6":
			model.setGridSize(6);
			break;
			
		case "7x7":
			model.setGridSize(7);
			break;
		}
		
		maxPossible = (int) (Math.pow(2, model.gridSize) - 1);
		view.setViewRows(model.generateRows(maxPossible, isDefault));
		view.setViewCols(model.generateCols());
		view.setViewRowLabels(model.rowLabel());
		view.setViewColLabels(model.colLabel());
		
		if (model.getGameMode() == 1) {
			view.picrossWindow.remove(view.boardPanel);
			view.picrossWindow.add(view.makeBoardPanel(model.getLangText(),model.getGridSize(),model.isMarkMode()));
		}
		else {
			view.designWindow.remove(view.boardPanel);
			view.designWindow.add(view.makeBoardPanel(model.getLangText(),model.getGridSize(),model.isMarkMode()));	
		}
		
		view.boardPanel.revalidate();
		boardActions();
		markCheckBoxAction();
	}
	
	// causing problems with design
	private void changeGridSize(String options) {
		newBoard(options, true);
		if (model.getGameMode() == 1){
			if (model.isGameStarted() == false) {
				return;
			}
			else {
				model.timer.stop();
				model.setGameStarted(false);
				view.timerCounter.setText("00:00");
			}
		}
	}
}
