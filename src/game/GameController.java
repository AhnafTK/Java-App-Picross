package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Formatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Beginning of the controller class. Interacts with the view and model. Responsible for action listeners.
 */
public class GameController {
	private GameModel model;
	private GameView view;

	/**
	 * Default constructor
	 */
	public GameController() {}
	/**
	 * Overloaded constructor
	 * @param model Instance of model
	 * @param view Instance of view
	 */
	public GameController(GameModel model, GameView view) {
		this.model = model;
		this.view = view;
	}
	
	/**
	 * Starts the controller/game by calling the splash 
	 */
	protected void startController() {
		splashActions();
	}
	
	/*
	 * Responsible for actions related to the splash screen.
	 */
	private void splashActions() {
		view.startLauncher(model.getCurrentLocale(), model.getLangText());
		launcherActions();
	}
	/*
	 * Responsible for actions related to the launcher.
	 */
	private void launcherActions() {
		view.getPlayButton().addActionListener((actionEvent) -> {
			model.resetBoard();
			view.resetRowsAndCol();
			view.getStartWindow().dispose();
			view.Play(model.getCurrentLocale(), model.getLangText());
			playActions();
		});

		view.getDesignButton().addActionListener((actionEvent) -> {
			model.setGameStarted(true);
			model.resetBoard();
			view.resetRowsAndCol();
			System.out.println("clicked design button");
			view.getStartWindow().dispose();
			view.Design(model.getCurrentLocale(), model.getLangText());
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

	/*
	 * Responsible for actions related to the changing of languages while in the launcher.
	 */
	private void updateLauncherLanguage() {
		view.getDesignButton().setText(model.getLangText().getString("design"));
		view.getPlayButton().setText(model.getLangText().getString("play"));
		view.getEngRadio().setText(model.getLangText().getString("english"));
		view.getFrRadio().setText(model.getLangText().getString("french"));
	}
	/*
	 * Responsible for actions related to the design mode/window.
	 */
	private void designActions() {
		model.setGameMode(0);
		model.makeDesignBoard(model.getGridSize());
		leftPanelActions();
		markCheckBoxAction();
		boardActions();
		gridSizeActions();
		menuBarActions();
	}
	/*
	 * Responsible for actions related to the play mode/window.
	 */
	private void playActions() {
		model.setGameMode(1);
		gridSizeActions();
		leftPanelActions();
		markCheckBoxAction(); // checkbox features
		newPlayBoard("5x5", true, false);
		menuBarActions();
	}

	/*
	 * Responsible for actions related to the ending of the game.
	 */
	private void gameOverActions() {
		view.getGameCompleteSave().addActionListener((actionEvent) -> {
			saveGameActions();
		});

		view.getGameCompleteClose().addActionListener((actionEvent) -> {
			view.getGameCompleteWindow().dispose();
		});
	}

	/*
	 * Responsible for actions related to showing the instructions
	 */
	private void instructionsActions() {
		view.getInstructionsBack().addActionListener((actionEvent) -> {
			view.getInstructionsWindow().dispose();
			view.getInstructionsButton().setEnabled(true);
		});
	}

	/*
	 * Responsible for actions related to the board
	 */
	private void boardActions() {

		ActionListener listener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getGameMode() == 1) {
					model.totalValidTiles();
				}

				for (int i = 0; i < model.getGridSize(); i++) {
					for (int j = 0; j < model.getGridSize(); j++) {
						if (e.getSource() == view.getButtons()[i][j]) {
							// in design mode
							if (model.getGameMode() == 0) {
								if (model.isMarkMode()) {
									view.getButtons()[i][j].setBackground(new Color(226, 222, 222));

								} else {
									view.getButtons()[i][j].setBackground(new Color(17, 15, 15));
									model.updateDesignBoard(i, j);
									String newRow = model.updateRow(i, j);
									String newCol = model.updateCol(i, j);
									view.updateDesignRow(newRow, i);
									view.updateDesignCol(newCol, j);
									view.getButtons()[i][j].setEnabled(false);
								}
							}
							// in play mode
							else {
								if (model.isMarkMode()) {
									view.getButtons()[i][j].setBackground(view.mark_color);
									view.history.append(model.getLangText().getString("upon_grid_mark") + ": [" + i
											+ ", " + j + "]\n");
								} else {
									view.history.append(model.getLangText().getString("upon_grid_click") + ": [" + i
											+ ", " + j + "]\n");
									if (model.isGameStarted() == false) {
										timerCounter();
										model.getTimer().start();
									}
									if (model.getRow(i).charAt(j) == '1') {
										System.out.println("correct");
										model.setCurrentValid(model.getCurrentValid() + 1);
										model.setScore(model.getScore() + 1);
										view.getButtons()[i][j].setBackground(view.tile_color);

										if (model.getCurrentValid() == model.getTotalValid()) {
											model.getTimer().stop();
											model.setBestScore(model.getScore());
											model.setBestTime(model.timerToSeconds());
											model.setGameFinished(true);
											for (int a = 0; a < model.getGridSize(); a++) {
												for (int b = 0; b < model.getGridSize(); b++) {
													view.getButtons()[a][b].setEnabled(false);
												}
											}
											view.gameCompleted(model.getCurrentLocale(), model.getLangText(),model.getBestScore(), model.getBestTime());
											gameOverActions();

										}
									} else {
										model.setScore(model.getScore() - 1);
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
			for (JButton j : i) {
				j.addActionListener(listener);
			}
		}
	}

	/*
	 * Responsible for actions related to the checkbox (mark mode on/off)
	 */
	private void markCheckBoxAction() {
		view.getMarkCheckBox().addActionListener((actionEvent) -> {
			if (view.getMarkCheckBox().isSelected()) {
				view.history.append(
						model.getLangText().getString("mark") + ": " + model.getLangText().getString("true") + "\n");
				model.setMarkMode(true);
			} else {
				view.history.append(
						model.getLangText().getString("mark") + ": " + model.getLangText().getString("false") + "\n");
				model.setMarkMode(false);
			}
		});
	}

	/*
	 * Responsible for actions related to the changing of language in general.
	 */
	private void languageActions() {
		view.getEngRadio().addActionListener((actionEvent) -> {
			model.changeLanguage("en", "US");
			view.updateText(model.getCurrentLocale(), model.getLangText());
			view.getLeftPanel().revalidate();
			view.history.append("\n" + model.getLangText().getString("upon_lang_change")
					+ model.getLangText().getString("english") + "\n");
		});
		view.getFrRadio().addActionListener((actionEvent) -> {
			model.changeLanguage("fr", "FR");
			view.updateText(model.getCurrentLocale(), model.getLangText());
			view.getLeftPanel().revalidate();
			view.history.append("\n" + model.getLangText().getString("upon_lang_change")
					+ model.getLangText().getString("french") + "\n");
		});
	}

	/*
	 * Responsible for actions related to changing the gridSize
	 */
	private void gridSizeActions() {
		view.getGridSizeCmbo().addActionListener((actionEvent) -> {
			String options = (String) view.getGridSizeCmbo().getSelectedItem();
			changeGridSize(options);
		});
	}

	/*
	 * Actions related to the timer.
	 * Referenced: https://www.youtube.com/watch?v=zWw72j-EbqI&list=PL_ql-
	 * Q0xEccQzbifFP2SI5y3a_LkNaxA3&index=4 (Skylar)
	 */
	private void timerCounter() {
		model.setGameStarted(true);
		model.setSeconds(0);
		model.setMinutes(0);
		DecimalFormat dFormat = new DecimalFormat("00");
		model.setTimer(new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.setSeconds(model.getSeconds() + 1);
				if (model.getSeconds() == 60) {
					model.setMinutes(model.getMinutes() + 1);
					model.setSeconds(0);
				}
				model.setSecFormat(dFormat.format(model.getSeconds()));
				model.setMinFormat(dFormat.format(model.getMinutes()));
				view.getTimerCounter().setText(model.getMinFormat() + ":" + model.getSecFormat());
			}
		}));
	}

	/*
	 * Responsible for actions related to going back to the launcher
	 */
	private void backToLauncher() {
		model.setMarkMode(false);
		if (model.getGameMode() == 1) {
			view.getPicrossWindow().dispose();
			view.launcher(model.getLangText(), model.getCurrentLocale());
			launcherActions();
			if (model.isGameStarted() == false) {
				return;
			} else {
				model.getTimer().stop();
				model.setGameStarted(false);
				view.getTimerCounter().setText("00:00");
			}
		} else {
			model.setGameStarted(false);
			view.getDesignWindow().dispose();
			System.out.println("clicked design back button");
			view.launcher(model.getLangText(), model.getCurrentLocale());
			launcherActions();
		}
	}

	/*
	 * Responsible for actions related to the components in the left panel of the play board, some parts of design
	 */
	private void leftPanelActions() {
		languageActions();
		
		view.getPlayToLauncher().addActionListener((actionEvent) -> {
			model.setMarkMode(false);
			if (model.getGameMode() == 1) {
				view.getPicrossWindow().dispose();
				view.launcher(model.getLangText(), model.getCurrentLocale());
				launcherActions();
				if (model.isGameStarted() == false) {
					return;
				} else {
					model.getTimer().stop();
					model.setGameStarted(false);
					view.getTimerCounter().setText("00:00");
				}
			} else {
				model.setGameStarted(false);
				view.getDesignWindow().dispose();
				System.out.println("clicked design back button");
				view.launcher(model.getLangText(), model.getCurrentLocale());
				launcherActions();
			}

		});

		view.getResetButton().addActionListener((actionEvent) -> {
			resetBoard();
		});
		view.getSolveButton().addActionListener((actionEvent) -> {
			view.history.append(model.getLangText().getString("upon_click") + model.getLangText().getString("button") + model.getLangText().getString("solve") + "\n");
			view.setViewRows(model.getRow());
			view.solveBoard(model.gridSize);
		});

		view.getInstructionsButton().addActionListener((actionEvent) -> {
			showInstructions();
		});
		view.getNewBoardButton().addActionListener((actionEvent) -> {
			view.history.append(model.getLangText().getString("upon_click") + model.getLangText().getString("button") + model.getLangText().getString("newBoard") + "\n");
			newPlayBoard((String) view.getGridSizeCmbo().getSelectedItem(), false, false);
		});

	}

	/*
	 * Responsible for actions related to saving the game
	 */
	private void saveGameActions() {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));

		if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

			try {
				BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
				fileWriter.write(model.gridSize + "\n");

				if (model.getGameMode() == 0) {
					fileWriter.write(model.writeDesignPattern());

				} else {
					fileWriter.write(model.writePattern());
					if (model.getGameFinished() == true) {
						fileWriter.write(Integer.toString(model.getBestScore()) + "\n");
						fileWriter.write(Integer.toString(model.getBestTime()) + "\n");
						model.setUsername(view.getNameTextField().getText());
						if (model.getUsername() != null) {
							fileWriter.write(model.getUsername());
						}
					}
				}

				fileWriter.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * Responsible for actions related to the menu bar
	 */
	private void menuBarActions() {
		view.getSaveMenuOption().addActionListener((actionEvent) -> {
			saveGameActions();
		});

		view.getLoadMenuOption().addActionListener((actionEvent) -> {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			fileChooser.setFileFilter(new FileNameExtensionFilter("Text files", "txt"));
			
			if (model.getGameMode() == 0) {
				model.setRow(new String[model.gridSize]);
			}

			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

				try {
					Scanner fileReader = new Scanner(file);

					if (file.isFile()) {
						if (model.getGameMode() == 1) {
							model.readFile(fileReader);
							newPlayBoard(Integer.toString(model.getGridSize()), false, true);
						} else {
							model.readFileDesign(fileReader);
							newDesignBoard(Integer.toString(model.getGridSize()), true);
							for (int i = 0; i < model.gridSize; i++) {
								for (int j = 0; j < model.gridSize; j++) {
									if (model.getDesignBoard()[i][j].equals("1")) {
										view.getButtons()[i][j].setBackground(Color.black);
										view.getButtons()[i][j].setEnabled(false);
									}
								}
							}
						}
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		view.getNewMenuOption().addActionListener((actionEvent) -> {
			newPlayBoard((String) view.getGridSizeCmbo().getSelectedItem(), false, false);
		});

		view.getResetMenuOption().addActionListener((actionEvent) -> {
			resetBoard();
		});

		view.getSolveMenuOption().addActionListener((actionEvent) -> {
			view.setViewRows(model.getRow());
			view.solveBoard(model.getGridSize());
		});

		view.getFiveFive().addActionListener((actionEvent) -> {
			changeGridSize("5x5");
			view.getGridSizeCmbo().setSelectedIndex(0); // set the selected on the combo box to correct one.
		});

		view.getSixSix().addActionListener((actionEvent) -> {
			changeGridSize("6x6");
			view.getGridSizeCmbo().setSelectedIndex(1);
		});

		view.getSevSev().addActionListener((actionEvent) -> {
			changeGridSize("7x7");
			view.getGridSizeCmbo().setSelectedIndex(2);
		});

		view.getToLauncherMenuOption().addActionListener((actionEvent) -> {
			backToLauncher();
		});

		view.getExitMenuOption().addActionListener((actionEvent) -> {
			System.exit(0);
		});

		view.getAboutMenuOption().addActionListener((actionEvent) -> {
			showInstructions();
		});

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
			view.component_color = JColorChooser.showDialog(null, "Pick a color...", Color.black);
			view.getScoreCounter().setBackground(view.component_color);
			view.getTimerCounter().setBackground(view.component_color);
			view.getGridSizeCmbo().setBackground(view.component_color);
			view.getPlayToLauncher().setBackground(view.component_color);
			view.getResetButton().setBackground(view.component_color);
			view.getSolveButton().setBackground(view.component_color);
			view.getNewBoardButton().setBackground(view.component_color);
			view.getInstructionsButton().setBackground(view.component_color);
			view.history.setBackground(view.component_color);
			for (JButton[] i : view.getButtons()) {
				for (JButton j : i) {
					if (j.isEnabled()) {
						j.setBackground(view.component_color);
					}
				}
			}
		});

	}

	/*
	 * Responsible for actions related to resetting the board.
	 */
	private void resetBoard() {
		view.history.append(model.getLangText().getString("upon_click") + model.getLangText().getString("button") + model.getLangText().getString("reset") + "\n");
		if (model.getGameMode() == 1 && model.isGameStarted() == true) {
			model.setCurrentValid(0);
			model.setScore(0);
			view.getScoreCounter().setText(Integer.toString(model.getScore()));
			model.getTimer().stop();
			model.setGameStarted(false);
			model.setGameFinished(false);
			view.getTimerCounter().setText("00:00");
		}
		if (model.getGameMode() == 0) {
			newDesignBoard(Integer.toString(model.getGridSize()), false);
		}
		view.resetBoard();
	}

	/*
	 * Responsible for actions related to showing the instructions
	 */
	private void showInstructions() {
		view.history.append(model.getLangText().getString("upon_click") + model.getLangText().getString("button") + model.getLangText().getString("instructions") + "\n");
		view.Instructions(model.getCurrentLocale(), model.getLangText());
		view.getInstructionsButton().setEnabled(false);
		instructionsActions();
	}

	/*
	 * Responsible for actions related to making a new board for play mode
	 */
	private void newPlayBoard(String options, boolean isDefault, boolean readingFile) {
		int maxPossible;
		model.setCurrentValid(0);
		model.setGameFinished(false);

		if (isDefault == false && model.isGameStarted() == true && model.getGameMode() == 1) {
			model.setScore(0);
			view.getScoreCounter().setText(Integer.toString(model.getScore()));
			model.getTimer().stop();
			model.setGameStarted(false);
			view.getTimerCounter().setText("00:00");
		}

		if (options.equals("5x5") || options.equals("5")) { model.setGridSize(5); }
		if (options.equals("6x6") || options.equals("6")) { model.setGridSize(6); }
		if (options.equals("7x7") || options.equals("7")) { model.setGridSize(7); }
		
		if (readingFile) {
			System.out.println("Reading file...");
			view.setViewRows(model.getRow());
			view.setViewRowLabels(new String[model.gridSize]);
			view.setViewColLabels(new String[model.gridSize]);
			view.setViewCols(model.generateCols());
			view.setViewRowLabels(model.rowLabel());
			view.setViewColLabels(model.colLabel());
			view.getPicrossWindow().remove(view.getBoardPanel());
			view.getPicrossWindow().add(view.makeBoardPanel(model.getLangText(), model.getGridSize(), model.isMarkMode()));
			view.getBoardPanel().revalidate();
			boardActions();
			markCheckBoxAction();
		} else {
			maxPossible = (int) (Math.pow(2, model.getGridSize()) - 1);
			view.setViewRows(model.generateRows(maxPossible, isDefault));
			view.setViewCols(model.generateCols());
			view.setViewRowLabels(model.rowLabel());
			view.setViewColLabels(model.colLabel());
			view.getPicrossWindow().remove(view.getBoardPanel());
			view.getPicrossWindow().add(view.makeBoardPanel(model.getLangText(), model.getGridSize(), model.isMarkMode()));
			view.getBoardPanel().revalidate();
			boardActions();
			markCheckBoxAction();
		}

	}

	/*
	 * Responsible for actions related to making a new board for design mode.
	 */
	private void newDesignBoard(String options, boolean readingFile) {
		if (options.equals("5x5") || options.equals("5")) { model.setGridSize(5); }
		if (options.equals("6x6") || options.equals("6")) { model.setGridSize(6); }
		if (options.equals("7x7") || options.equals("7")) { model.setGridSize(7); }

		if (readingFile) {
			System.out.println("Reading file...");
			view.setViewRows(model.getRow());
			view.setViewRowLabels(new String[model.gridSize]);
			view.setViewColLabels(new String[model.gridSize]);
			view.setViewCols(model.generateCols());
			view.setViewRowLabels(model.rowLabel());
			view.setViewColLabels(model.colLabel());
			view.getDesignWindow().remove(view.getBoardPanel());
			view.getDesignWindow().add(view.makeBoardPanel(model.getLangText(), model.getGridSize(), model.isMarkMode()));
			view.getDesignWindow().revalidate();
			boardActions();
			markCheckBoxAction();
		} else {
			view.setViewRowLabels(model.makeRowLabelDesign());
			view.setViewColLabels(model.makeColLabelDesign());
			view.getDesignWindow().remove(view.getBoardPanel());
			view.getDesignWindow().add(view.makeBoardPanel(model.getLangText(), model.getGridSize(), model.isMarkMode()));
			view.getBoardPanel().revalidate();
			model.makeDesignBoard(model.getGridSize());
			boardActions();
			markCheckBoxAction();
		}

	}

	/*
	 * Responsible for actions related to changing the gridSize
	 */
	private void changeGridSize(String options) {
		if (model.getGameMode() == 1) {
			newPlayBoard(options, true, false);
			if (model.isGameStarted() == false) {
				return;
			} else {
				model.getTimer().stop();
				model.setGameStarted(false);
				view.getTimerCounter().setText("00:00");
			}
		} else {
			newDesignBoard(options, false);
		}
	}
}
