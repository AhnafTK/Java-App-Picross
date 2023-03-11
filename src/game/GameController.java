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
		view.getPlayButton().addActionListener((actionEvent) -> {
			model.resetBoard();
			view.resetRowsAndCol();

			// intialize default model
			view.getStartWindow().dispose();
			view.Play(model.currentLocale,model.langText);
			playActions();
		});

		view.getDesignButton().addActionListener((actionEvent) -> {
			model.setGameStarted(true);
			model.resetBoard();
			view.resetRowsAndCol();
			System.out.println("clicked design button");
			view.getStartWindow().dispose();
			view.Design(model.currentLocale, model.langText);
			designActions();
		});
		
		view.getEngRadio().addActionListener((actionEvent) -> {
			model.changeLanguage("en", "US");
			updateLauncherLanguage();
		});
		
		view.getFrRadio().addActionListener((actionEvent) -> {
			model.changeLanguage("fr", "FR");
			updateLauncherLanguage();
		});	
	}

	private void updateLauncherLanguage() {
		view.getDesignButton().setText(model.langText.getString("design"));
		view.getPlayButton().setText(model.langText.getString("play"));
		view.getEngRadio().setText(model.langText.getString("english"));
		view.getFrRadio().setText(model.langText.getString("french"));
	}
	private void designActions() {
		model.setGameMode(0);
		model.makeDesignBoard(model.getGridSize());

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
		newPlayBoard("5x5",true);
		menuBarActions();
	}

	private void instructionsActions() {
		view.getInstructionsBack().addActionListener((actionEvent) -> {
			view.getInstructionsWindow().dispose();
			view.getInstructionsButton().setEnabled(true);
		});
	}

	private void boardActions() {

		ActionListener listener = new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	        	for (int i = 0; i < model.getGridSize(); i++) {
					for (int j = 0; j < model.getGridSize(); j++) {
						if (e.getSource() == view.getButtons()[i][j]) {
							// in design mode
							if (model.getGameMode() == 0) {
								if (model.isMarkMode()) {
									view.getButtons()[i][j].setBackground(new Color(226, 222, 222));
								} 
								else {
									view.getButtons()[i][j].setBackground(new Color(17, 15, 15));
									model.updateDesignBoard(i, j);
									String newRow = model.updateRow(i, j);
									String newCol = model.updateCol(i, j);
									view.updateDesignRow(newRow, i);
									view.updateDesignCol(newCol, j);
								}
								view.getButtons()[i][j].setEnabled(false);
							}
							// in play mode
							else {
								if (model.isMarkMode()) {
									view.getButtons()[i][j].setBackground(view.mark_color);
								} else {
									if (model.isGameStarted() == false) {
										timerCounter(); 
										model.timer.start();	
									}
									if (model.getRow(i).charAt(j) == '1') {
										System.out.println("correct");
										model.setScore(model.getScore()+1);
										view.getButtons()[i][j].setBackground(view.tile_color);
									}
									else {
										model.setScore(model.getScore()-1);
										view.getButtons()[i][j].setBackground(view.err_color);
										System.out.println("false");
									}
									view.getScoreCounter().setText(Integer.toString(model.getScore())); 
									view.getButtons()[i][j].setEnabled(false);
								}	
							}
						}
					}
				}
	        }
	    };
	    
		for (JButton[] i : view.getButtons()) {
			//indexi++;
			for (JButton j : i) {
				j.addActionListener(listener);
			}
		}
	}
	
	private void markCheckBoxAction() {
		view.getMarkCheckBox().addActionListener((actionEvent) -> {
			if (view.getMarkCheckBox().isSelected()) {
				view.history.append(model.langText.getString("mark") + ": " + model.langText.getString("true") + "\n");
				model.setMarkMode(true);
			} else {
				view.history.append(model.langText.getString("mark") + ": " + model.langText.getString("false") + "\n");
				model.setMarkMode(false);
			}
		});
	}

	private void languageActions() {
		view.getEngRadio().addActionListener((actionEvent) -> {
			model.changeLanguage("en", "US");
			view.updateText(model.getCurrentLocale(), model.getLangText());
			view.getLeftPanel().revalidate();
			view.history.append("\n" + model.langText.getString("upon_lang_change") + model.langText.getString("english") + "\n");
		});
		view.getFrRadio().addActionListener((actionEvent) -> {
			model.changeLanguage("fr", "FR");
			view.updateText(model.currentLocale, model.langText);
			view.getLeftPanel().revalidate();
			view.history.append("\n" + model.langText.getString("upon_lang_change") + model.langText.getString("french") + "\n");
		});
	}
	
	private void gridSizeActions() {
		view.getGridSizeCmbo().addActionListener((actionEvent) -> {
			//System.out.println(model.getGameMode());
			String options = (String) view.getGridSizeCmbo().getSelectedItem();
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
				view.getTimerCounter().setText(model.getMinFormat() + ":" + model.getSecFormat());
			}
		});
	}
	
	private void backToLauncher() {
		model.setMarkMode(false);
		if (model.getGameMode() == 1) {
			view.getPicrossWindow().dispose();
			view.launcher(model.getLangText(), model.getCurrentLocale());
			launcherActions();
			if (model.isGameStarted() == false) {
				return;
			}
			else {
				model.timer.stop();
				model.setGameStarted(false);
				view.getTimerCounter().setText("00:00");
			}
		}
		else {
			model.setGameStarted(false);
			view.getDesignWindow().dispose();
			System.out.println("clicked design back button");
			view.launcher(model.langText, model.currentLocale);
			launcherActions();
		}
	}
	
	private void leftPanelActions() {
		
		languageActions();
		
		view.getPlayToLauncher().addActionListener((actionEvent) -> {
			model.setMarkMode(false);
			if (model.getGameMode() == 1) {
				view.getPicrossWindow().dispose();
				view.launcher(model.langText, model.currentLocale);
				launcherActions();
				if (model.isGameStarted() == false) {
					return;
				}
				else {
					model.timer.stop();
					model.setGameStarted(false);
					view.getTimerCounter().setText("00:00");
				}
			}
			else {
				model.setGameStarted(false);
				view.getDesignWindow().dispose();
				System.out.println("clicked design back button");
				view.launcher(model.langText, model.currentLocale);
				launcherActions();
			}
			
		});
		
		view.getResetButton().addActionListener((actionEvent) -> {resetBoard();});
		view.getSolveButton().addActionListener((actionEvent) -> {
			view.history.append(model.langText.getString("upon_click") + model.langText.getString("button")
					+ model.langText.getString("solve") + "\n");
			view.setRows(model.getRow());
			view.solveBoard(model.gridSize);
		});
	
		view.getInstructionsButton().addActionListener((actionEvent) -> {showInstructions();});
		view.getNewBoardButton().addActionListener((actionEvent) -> {
			view.history.append(model.langText.getString("upon_click") + model.langText.getString("button")
			+ model.langText.getString("newBoard") + "\n");
			newPlayBoard((String) view.getGridSizeCmbo().getSelectedItem(),false);
		});

	}
	
	private void menuBarActions() {
	
		view.getSaveMenuOption().addActionListener((actionEvent)->{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file;
				//PrintWriter fileOut;
				
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());			
			}
		});
		view.getLoadMenuOption().addActionListener((actionEvent)->{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file;
				//PrintWriter fileOut;
				
				file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				
			}
		});
		view.getNewMenuOption().addActionListener((actionEvent)->{newPlayBoard((String) view.getGridSizeCmbo().getSelectedItem(),false);});
		
		view.getResetMenuOption().addActionListener((actionEvent)->{resetBoard();});
		
		view.getSolveMenuOption().addActionListener((actionEvent)->{
			view.setRows(model.getRow());
			view.solveBoard(model.getGridSize());}
		);
		
		view.getFiveFive().addActionListener((actionEvent)->{changeGridSize("5x5");});
		
		view.getSixSix().addActionListener((actionEvent)->{changeGridSize("6x6");});
		
		view.getSevSev().addActionListener((actionEvent)->{changeGridSize("7x7");});
		
		view.getToLauncherMenuOption().addActionListener((actionEvent)->{backToLauncher();});
		
		view.getExitMenuOption().addActionListener((actionEvent)->{System.exit(0);});
		
		view.getAboutMenuOption().addActionListener((actionEvent)->{showInstructions();});
		
		view.getBackgroundColour().addActionListener((actionEvent) -> {
			Color colour = JColorChooser.showDialog(null, "Pick a color...", Color.black);
			view.getBoardPanel().setBackground(colour);
			view.getLeftPanel().setBackground(colour);
			view.getLanguagePanel().setBackground(colour);
			view.getLanguageButtonPanel().setBackground(colour);
			view.getGridSizeComboPanel().setBackground(colour);
			view.getScorePanel().setBackground(colour);
			view.getTimerPanel().setBackground(colour);
			view.getButtonPanel().setBackground(colour);
			view.getConfigurationPanel().setBackground(colour);
			view.getHistoryPanel().setBackground(colour);
			view.getControlPanel().setBackground(colour);
			view.getRowPanel().setBackground(colour);
			view.getColPanel().setBackground(colour);
			view.getEngRadio().setBackground(colour);
			view.getFrRadio().setBackground(colour);
			view.getMarkCheckBox().setBackground(colour);
		});
		
		
		view.getTextColour().addActionListener((actionEvent) -> {
			Color colour = JColorChooser.showDialog(null, "Pick a color...", Color.black);
			view.getScoreLabel().setForeground(colour);
			view.getScoreCounter().setForeground(colour);
			view.getTimerLabel().setForeground(colour);
			view.getTimerCounter().setForeground(colour);
			view.getGridSizeLabel().setForeground(colour);
			view.getGridSizeCmbo().setForeground(colour);
			view.getPlayToLauncher().setForeground(colour);
			view.getSolveButton().setForeground(colour);
			view.getResetButton().setForeground(colour);
			view.getNewBoardButton().setForeground(colour);
			view.getInstructionsButton().setForeground(colour);
			view.getLangLabel().setForeground(colour);
			view.getEngRadio().setForeground(colour);
			view.getFrRadio().setForeground(colour);
			view.getMarkCheckBox().setForeground(colour);
		});
		
		view.getComponentColour().addActionListener((actionEvent) -> {			
			Color colour = JColorChooser.showDialog(null, "Pick a color...", Color.black);
			view.getScoreCounter().setBackground(colour);
			view.getTimerCounter().setBackground(colour);
			view.getGridSizeCmbo().setBackground(colour);
			view.getPlayToLauncher().setBackground(colour);
			view.getResetButton().setBackground(colour);
			view.getSolveButton().setBackground(colour);
			view.getNewBoardButton().setBackground(colour);
			view.getInstructionsButton().setBackground(colour);
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
		view.getInstructionsButton().setEnabled(false);
		instructionsActions();
	}
	
	private void newPlayBoard(String options, boolean isDefault) {
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
		
		maxPossible = (int) (Math.pow(2, model.getGridSize()) - 1);
		view.setViewRows(model.generateRows(maxPossible, isDefault));
		view.setViewCols(model.generateCols());
		view.setViewRowLabels(model.rowLabel());
		view.setViewColLabels(model.colLabel());
		view.getPicrossWindow().remove(view.getBoardPanel());
		view.getPicrossWindow().add(view.makeBoardPanel(model.getLangText(),model.getGridSize(),model.isMarkMode()));
		view.getBoardPanel().revalidate();
		boardActions();
		markCheckBoxAction();
	}
	private void newDesignBoard(String options) {
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
		
		view.setViewRowLabels(model.rowLabelDesign());
		view.setViewColLabels(model.colLabelDesign());
		view.getDesignWindow().remove(view.getBoardPanel());
		view.getDesignWindow().add(view.makeBoardPanel(model.getLangText(),model.getGridSize(),model.isMarkMode()));
		view.getBoardPanel().revalidate();
		model.makeDesignBoard(model.getGridSize());
		boardActions();
		markCheckBoxAction();
		
	}
	// causing problems with design
	private void changeGridSize(String options) {
		if (model.getGameMode() == 1){
			newPlayBoard(options, true);
			if (model.isGameStarted() == false) {
				return;
			}
			else {
				model.timer.stop();
				model.setGameStarted(false);
				view.getTimerCounter().setText("00:00");
			}
		}
		else {
			newDesignBoard(options);
		}
	}
}
