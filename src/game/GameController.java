package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.text.DecimalFormat;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Beginning of the controller class. Interacts with the view and model.
 * Responsible for action listeners.
 */
public class GameController {
	private GameModel model;
	private GameView view;
	private GameClient client = null;
	private GameServer server = null;

	/**
	 * Default constructor
	 */
	public GameController() {
	}

	/**
	 * Overloaded constructor
	 * 
	 * @param model Instance of model
	 * @param view  Instance of view
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

	/**
	 * Responsible for the actions related to the client components
	 */
	private void clientActions() {
		// When the client connects to the server
		view.clientConnect.addActionListener((actionEvent) -> {
			// Checks if the server IP is blank
			if (view.clientServerText.getText().isBlank()) {
				view.logTextArea.append("You must enter a server IP to connect to...\n");
			}
			// Checks if the port is blank
			else if (view.clientPortText.getText().isBlank()) {
				view.logTextArea.append("You must enter a port number to connect to...\n");
			}
			// Checks if the username is blank
			else if (view.clientUserNameText.getText().isBlank()) {
				view.logTextArea.append("Please enter a username...\n");
			}
			// If they all have values
			else {
				try {
					int portNum = Integer.parseInt(view.clientPortText.getText());
					String userName = view.clientUserNameText.getText();
					if (!(1024 <= portNum && portNum <= 65355)) {
						view.logTextArea.append("Valid ports can only be between 1024 and 65355...\n");
					} else {
						String serverIP = view.clientServerText.getText();
						view.logTextArea.append("Attempting connection...\n");
						client = new GameClient(serverIP, portNum, userName, view.logTextArea, model);
						client.isConnected = true;
						Thread receiveMessages = new Thread(new serverMessageThread());
						receiveMessages.start();
						// view.clientConnect.setEnabled(false);
					}
				} catch (NumberFormatException e) {
					view.logTextArea.append("You must enter an integer in the port field...\n");
				}
			}
		});
		//When the client disconnects from the server
		view.clientEnd.addActionListener((actionEvent) -> {
			if (client == null) {
				view.logTextArea.append("You need to create a connection first...\n");
			} else {
				view.logTextArea.append("Disconnecting from server...\n");
				client.disconnectClient();
				client.isConnected = false;
				// view.getClientWindow().dispose();
			}
		});
		//When the client sends a game to the server
		view.clientSendGame.addActionListener((actionEvent) -> {
			if (client != null && client.isConnected) {
				view.logTextArea.append("Sending game...\n");
				client.sendGame();
			} else {
				view.logTextArea.append("Not connected to server...\n");
			}

		});
		//When the client sends data to the server
		view.clientSendData.addActionListener((actionEvent) -> {
			if (client != null && client.isConnected) {
				view.logTextArea.append("Sending data...\n");
				client.sendData();
			} else {
				view.logTextArea.append("Not connected to server...\n");
			}
		});
		//When the client design is clicked
		view.clientDesign.addActionListener((actionEvent) -> {
			view.logTextArea.append("Designing new game...\n");
			view.clientSendGame.setEnabled(true);
			model.setLoadClient(false);
			model.setGameStarted(true);
			model.resetBoard();
			view.resetRowsAndCol();
			// view.getStartWindow().dispose();
			view.Design(model.getCurrentLocale(), model.getLangText());
			designActions();
			textChatActions();
		});
		//When the client loads a game from a file
		view.clientLoad.addActionListener((actionEvent) -> {
			if (client != null) {
				view.logTextArea.append("Loading game...\n");
				model.setLoadClient(true);
				/// view.clientSendGame.setEnabled(true);
				loadGameActions();
			} else {
				view.logTextArea.append("Not connected to server...\n");
			}
		});
		//When the client plays a game
		view.clientPlay.addActionListener((actionEvent) -> {
			view.logTextArea.append("Starting new game...\n");
			// view.clientSendGame.setEnabled(true);
			model.setGameStarted(false);
			model.resetBoard();
			view.resetRowsAndCol();
			// view.getStartWindow().dispose();
			view.Play(model.getCurrentLocale(), model.getLangText());
			playActions();
			textChatActions();
		});
		// when client sends a chat message
		view.textChat.addActionListener((actionEvent) -> {
			String text = view.textChat.getText();
			client.sendMessage(text);
			view.textChat.setText("");
		});
	}

	/**
	 * Responsible for the actions related to the server components
	 */
	private void serverMakerActions() {
		view.startServer.addActionListener((actionEvent) -> {
			if (view.serverPortText.getText().isBlank()) {
				view.logTextArea.append("You must enter a port number to connect to...\n");
			} else {
				try {
					int portNum = Integer.parseInt(view.serverPortText.getText());

					if (!(1024 <= portNum && portNum <= 65355)) {
						view.logTextArea.append("Valid ports can only be between 1024 and 65355...\n");
					} else {
						try {
							ServerSocket testSocket = new ServerSocket(portNum);
							testSocket.close();
							server = new GameServer(portNum, view.logTextArea);
							view.logTextArea.append("Starting server at " + portNum + "...\n");
						} catch (IOException e) {
							System.out.println("AAAAAAAAA");
							view.logTextArea.append(
									"Port number " + portNum + " is already in use, please try another one...\n");
						}
					}
				} catch (NumberFormatException e) {
					view.logTextArea.append("You must enter an integer in the port field...\n");
				}
			}

		});

		view.disconnectServer.addActionListener((actionEvent) -> {
			if (server == null) {
				view.logTextArea.append("You need to create a connection first...\n");
			} else {
				server.disconnectServer();
			}
		});

		view.endConnections.addActionListener((actionEvent) -> {
			if (server == null) {
				view.logTextArea.append("You need to create a connection first...\n");
			} else {
				server.endConnections();
			}
		});

		view.leaderboardButton.addActionListener((actionEvent) -> {
			if (server == null) {
				view.logTextArea.append("You need to create a connection first...\n");
			} else {
				server.printLeaderboard();
			}
		});
	}

	/**
	 * When the text chat is clicked, gets rid of the default text
	 */
	private void textChatActions() {
		if (client != null || server != null) {
			view.textChat.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					view.textChat.setText("");
					view.textChat.setForeground(Color.BLACK);

				}
			});
		}
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
			textChatActions();
		});

		view.getDesignButton().addActionListener((actionEvent) -> {
			model.setGameStarted(true);
			model.resetBoard();
			view.resetRowsAndCol();
			view.getStartWindow().dispose();
			view.Design(model.getCurrentLocale(), model.getLangText());
			designActions();
			textChatActions();
		});

		view.getClientButton().addActionListener((actionEvent) -> {
			view.Client(model.getCurrentLocale(), model.getLangText());
			clientActions();
			textChatActions();
		});

		view.getServerButton().addActionListener((actionEvent) -> {
			view.Server(model.getCurrentLocale(), model.getLangText());
			serverMakerActions();
			textChatActions();
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
	 * Responsible for actions related to the changing of languages while in the
	 * launcher.
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
		if (client != null) {

			view.gameChat.addActionListener((actionEvent) -> {
				String text = view.gameChat.getText();
				client.sendMessage(text);
				view.gameChat.setText("");
			});

			view.gameChat.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					view.gameChat.setText("");
					view.gameChat.setForeground(Color.BLACK);

				}
			});
		}
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
			if (view.getNameTextField().getText().isBlank()) {
				model.setUsername(" ");
			} else {
				model.setUsername(view.getNameTextField().getText());
			}
			view.getGameCompleteWindow().dispose();
		});
	}

	/*
	 * Responsible for actions related to showing the instructions
	 */
	private void instructionsActions() {
		// WindowListener windowListener = new WindowListener();
		view.getInstructionsWindow().addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				view.getInstructionsButton().setEnabled(true);
				;
			}
		});

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
									view.history.append(model.getLangText().getString("upon_grid_mark") + ": \n[" + i
											+ ", " + j + "]\n");
								} else {
									view.history.append(model.getLangText().getString("upon_grid_click") + ": \n[" + i
											+ ", " + j + "]\n");
									if (model.isGameStarted() == false) {
										timerCounter();
										model.getTimer().start();
									}
									if (model.getRow(i).charAt(j) == '1') {
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
											view.gameCompleted(model.getCurrentLocale(), model.getLangText(),
													model.getBestScore(), model.getBestTime());
											gameOverActions();

										}
									} else {
										model.setScore(model.getScore() - 1);
										view.getButtons()[i][j].setBackground(view.err_color);
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
	 * Actions related to the timer. Referenced:
	 * https://www.youtube.com/watch?v=zWw72j-EbqI&list=PL_ql-
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
			view.launcher(model.getLangText(), model.getCurrentLocale());
			launcherActions();
		}
	}

	/*
	 * Responsible for actions related to the components in the left panel of the
	 * play board, some parts of design
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
				view.launcher(model.getLangText(), model.getCurrentLocale());
				launcherActions();
			}

		});

		view.getResetButton().addActionListener((actionEvent) -> {
			resetBoard();
		});
		view.getSolveButton().addActionListener((actionEvent) -> {
			view.history.append(model.getLangText().getString("upon_click") + model.getLangText().getString("button")
					+ model.getLangText().getString("solve") + "\n");
			view.setViewRows(model.getRow());
			if (model.getGameMode() == 1 && model.isGameStarted() == true) {
				model.setCurrentValid(0);
				model.setScore(0);
				view.getScoreCounter().setText(Integer.toString(model.getScore()));
				model.getTimer().stop();
				model.setGameStarted(false);
				model.setGameFinished(false);
				view.getTimerCounter().setText("00:00");
			}
			view.solveBoard(model.gridSize);
		});

		view.getInstructionsButton().addActionListener((actionEvent) -> {
			showInstructions();
		});
		view.getNewBoardButton().addActionListener((actionEvent) -> {
			view.history.append(model.getLangText().getString("upon_click") + model.getLangText().getString("button")
					+ model.getLangText().getString("newBoard") + "\n");
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
						if (view.getNameTextField().getText().isBlank()) {
							model.setUsername(" ");
						} else {
							model.setUsername(view.getNameTextField().getText());
						}
						fileWriter.write(model.getUsername());
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

	private void loadGameActions() {
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
					model.gridSize = fileReader.nextInt();
					if (model.isLoadClient() == true) {
						model.readFile(fileReader);
					} else {
						view.getGridSizeCmbo().setSelectedIndex(model.gridSize - 5);

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
				}
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
			model.setLoadClient(false);
			loadGameActions();
		});
		view.getNewMenuOption().addActionListener((actionEvent) -> {
			newPlayBoard((String) view.getGridSizeCmbo().getSelectedItem(), false, false);
		});

		view.getResetMenuOption().addActionListener((actionEvent) -> {
			resetBoard();
		});

		view.getSolveMenuOption().addActionListener((actionEvent) -> {
			view.setViewRows(model.getRow());
			if (model.getGameMode() == 1 && model.isGameStarted() == true) {
				model.setCurrentValid(0);
				model.setScore(0);
				view.getScoreCounter().setText(Integer.toString(model.getScore()));
				model.getTimer().stop();
				model.setGameStarted(false);
				model.setGameFinished(false);
				view.getTimerCounter().setText("00:00");
			}
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

		view.getClientMenuOption().addActionListener((actionEvent) -> {
			view.Client(model.getCurrentLocale(), model.getLangText());
			clientActions();
			textChatActions();
		});

		view.getServerMenuOption().addActionListener((actionEvent) -> {
			view.Server(model.getCurrentLocale(), model.getLangText());
			serverMakerActions();
			textChatActions();
		});

	}

	/*
	 * Responsible for actions related to resetting the board.
	 */
	private void resetBoard() {
		view.history.append(model.getLangText().getString("upon_click") + model.getLangText().getString("button")
				+ model.getLangText().getString("reset") + "\n");
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
		view.history.append(model.getLangText().getString("upon_click") + model.getLangText().getString("button")
				+ model.getLangText().getString("instructions") + "\n");
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

		if (options.equals("5x5") || options.equals("5")) {
			model.setGridSize(5);
		}
		if (options.equals("6x6") || options.equals("6")) {
			model.setGridSize(6);
		}
		if (options.equals("7x7") || options.equals("7")) {
			model.setGridSize(7);
		}

		if (readingFile) {
			view.setViewRows(model.getRow());
			view.setViewRowLabels(new String[model.gridSize]);
			view.setViewColLabels(new String[model.gridSize]);
			view.setViewCols(model.generateCols());
			view.setViewRowLabels(model.rowLabel());
			view.setViewColLabels(model.colLabel());
			view.getPicrossWindow().remove(view.getBoardPanel());
			view.getPicrossWindow()
					.add(view.makeBoardPanel(model.getLangText(), model.getGridSize(), model.isMarkMode()));
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
			view.getPicrossWindow()
					.add(view.makeBoardPanel(model.getLangText(), model.getGridSize(), model.isMarkMode()));
			view.getBoardPanel().revalidate();
			boardActions();
			markCheckBoxAction();
		}

	}

	/*
	 * Responsible for actions related to making a new board for design mode.
	 */
	private void newDesignBoard(String options, boolean readingFile) {
		if (options.equals("5x5") || options.equals("5")) {
			model.setGridSize(5);
		}
		if (options.equals("6x6") || options.equals("6")) {
			model.setGridSize(6);
		}
		if (options.equals("7x7") || options.equals("7")) {
			model.setGridSize(7);
		}

		if (readingFile) {
			view.setViewRows(model.getRow());
			view.setViewRowLabels(new String[model.gridSize]);
			view.setViewColLabels(new String[model.gridSize]);
			view.setViewCols(model.generateCols());
			view.setViewRowLabels(model.rowLabel());
			view.setViewColLabels(model.colLabel());
			view.getDesignWindow().remove(view.getBoardPanel());
			view.getDesignWindow()
					.add(view.makeBoardPanel(model.getLangText(), model.getGridSize(), model.isMarkMode()));
			view.getDesignWindow().revalidate();
			boardActions();
			markCheckBoxAction();
		} else {
			view.setViewRowLabels(model.makeRowLabelDesign());
			view.setViewColLabels(model.makeColLabelDesign());
			view.getDesignWindow().remove(view.getBoardPanel());
			view.getDesignWindow()
					.add(view.makeBoardPanel(model.getLangText(), model.getGridSize(), model.isMarkMode()));
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
				view.getScoreCounter().setText("0");
				model.setScore(0);
			}
		} else {
			newDesignBoard(options, false);
		}
	}
	
	private class serverMessageThread implements Runnable{

		@Override
		public void run() {
			
			while(client!=null && client.isConnected) {
		
			client.messageFromServer();
				
		
			}
			// TODO Auto-generated method stub
			}

		
	}

}
