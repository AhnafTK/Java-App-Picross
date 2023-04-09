package game;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/**
 * This class is used to handle the GUI processing of the MVC
 * 
 * @author Skylar Phanenhour and Ahnaf Kamal
 *
 */
public class GameView {

	private JFrame designWindow, startWindow, splashWindow, instructionsWindow, gameCompleteWindow, picrossWindow, clientWindow, serverWindow;
	// Buttons for the splash screen
	private JButton playButton, designButton, clientButton, serverButton, playToLauncher;
	// Buttons to return back to previous JFrames
	private JButton designBack, instructionsBack;
	// Buttons for the play frame, some also used in design
	private JButton resetButton, solveButton, newBoardButton, instructionsButton;
	// Buttons for the design and play grids
	private JButton[][] buttonsPlay;
	private JButton newGridButton;
	// Buttons for the game completed window
	private JButton gameCompleteSave = new JButton();
	private JButton gameCompleteClose = new JButton();
	protected JButton startServer, clientConnect, clientEnd, clientPlay, clientLoad, clientSendGame, clientSendData, clientDesign; 
	protected JButton leaderboardButton, disconnectServer, endConnections;
	
	// JTextField Declarations
	// Text field for the score and timer in play
	private JTextField scoreCounter, timerCounter;
	// Text field for the name, best time, and best score when the game is completed
	private JTextField nameTextField, bestTimeTextField, bestScoreTextField;
	protected JTextField clientUserNameText, clientServerText, clientPortText;
	protected JTextField serverPortText;
	protected JTextField textChat;
	protected JTextField gameChat;
	
	// JPanel Declarations
	private JPanel languagePanel;
	private JPanel languageButtonPanel;
	private JPanel gridSizeComboPanel;
	private JPanel scorePanel;
	private JPanel timerPanel;
	private JPanel buttonPanel;
	private JPanel configurationPanel;
	private JPanel historyPanel;
	private JPanel controlPanel;
	private JPanel colPanel;
	private JPanel rowPanel;
	private JPanel leftPanel;
	private JPanel boardPanel;

	// JMenu Declarations

	private JMenu gameMenuItemsContainer;
	private JMenu helpMenuItemsContainer;
	private JMenu networkingMenuItemsContainer;
	private JMenu colourMenu;
	private JMenu gridSizeItemsContainer;

	// JMenuItem Declarations

	// Menu items for the game menu
	private JMenuItem newMenuItem;
	private JMenuItem solutionMenuItem;
	private JMenuItem exitMenuItem;
	private JMenuItem gameMenu;
	private JMenuItem newMenuOption;
	private JMenuItem exitMenuOption;
	private JMenuItem resetMenuOption;
	private JMenuItem solveMenuOption;
	private JMenuItem toLauncherMenuOption;
	private JMenuItem saveMenuOption;
	private JMenuItem loadMenuOption;
	private JMenuItem aboutMenuOption;
	private JMenuItem clientMenuOption;
	private JMenuItem serverMenuOption;
	private JMenuItem fiveFive = new JMenuItem("5x5");
	private JMenuItem sixSix = new JMenuItem("6x6");
	private JMenuItem sevSev = new JMenuItem("7x7");

	// Menu items for the help menu
	private JMenuItem aboutMenu;
	private JMenuItem backgroundColour, componentColour, textColour;

	// JLabel Declarations
	private JLabel timerLabel;
	private JLabel scoreLabel;
	private JLabel userNameLabel = new JLabel();
	private JLabel bestTimeLabel = new JLabel();
	private JLabel bestScoreLabel = new JLabel();
	private JLabel gridSizeLabel;
	private JLabel langLabel;
	private JLabel clientUserLabel; 
	private JLabel clientServerLabel; 
	private JLabel clientPortLabel;
	private JLabel serverPortLabel;
	
	// Colour Declarations

	/** Colour for when the tile is clicked correctly */
	protected Color tile_color = new Color(17, 15, 15);
	/** Colour for when the tile is marked */
	protected Color mark_color = new Color(226, 222, 222);
	/** Colour for when the tile is clicked incorrectly */
	protected Color err_color = Color.red;
	/** Default colour for the components, gets changed from colour picker */
	protected Color component_color = new Color(255, 255, 255);

	// JRadioButton Declarations

	private JRadioButton engRadio;
	private JRadioButton frRadio;

	// String Declarations

	private String[] viewRows;
	private String[] viewCols;
	private String[] viewRowLabels = { "0", "0", "0", "0", "0" };
	private String[] viewColLabels = { "0", "0", "0", "0", "0" };

	// Other Declarations

	/** Text area to display the input history */
	protected JTextArea history;
	protected JTextArea logTextArea;
	private JComboBox<String> gridSizeCmbo;
	private JCheckBox markCheckBox;
	private int gridSize = 5;

	/**
	 * Default constructor for the GameView class.
	 */
	protected GameView() {

	}

	/**
	 * This is the method that gets started when the game loads up, to start the
	 * splash screen
	 * 
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 */
	protected void startLauncher(Locale currentLocale, ResourceBundle langText) {
		splashScreen(currentLocale, langText);
	}

	/**
	 * This method is to make the splash screen JFrame and all its components
	 * 
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 */
	protected void splashScreen(Locale currentLocale, ResourceBundle langText) {

		// Panel to set the image icon of the splash screen
		JPanel splashPanel = new JPanel();
		ImageIcon splashLogo = new ImageIcon(getClass().getResource("/images/picross.jpg"));
		JLabel splashLabel = new JLabel();
		splashLabel.setIcon(splashLogo);
		splashPanel.setPreferredSize(new Dimension(500, 125));
		splashPanel.add(splashLabel);

		// JFrame for the splash screen
		splashWindow = new JFrame();
		splashWindow.add(splashPanel);
		splashWindow.pack();
		splashWindow.setVisible(true);
		splashWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		splashWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		splashWindow.setResizable(false);
		splashWindow.setSize(400, 165);
		splashWindow.setLocationRelativeTo(null);

		// Pauses the thread, to make the splash screen disappear after 3 seconds
		try {
			Thread.sleep(1000);
			splashWindow.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// After the splash screen disappears, the launcher starts
		launcher(langText, currentLocale);
	}

	/**
	 * This method is used to start the launcher window. The user can choose to go
	 * to the design or play mode from here.
	 * 
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 */
	protected void launcher(ResourceBundle langText, Locale currentLocale) {

		// Panel for the image icon that gets shown
		JPanel titlePanel = new JPanel();
		ImageIcon titleLogo = new ImageIcon(getClass().getResource("/images/picross.jpg"));
		JLabel titleLabel = new JLabel();
		titleLabel.setIcon(titleLogo);
		titlePanel.setPreferredSize(new Dimension(500, 125));
		titlePanel.add(titleLabel);

		////////////////////////////////////////////////////////////////

		// Panel to hold the design and play buttons
		JPanel startPanel = new JPanel();
		startPanel.setBackground(new Color(17, 15, 15));
		startPanel.setPreferredSize(new Dimension(250, 40));

		// Creates the design button
		designButton = new JButton(langText.getString("design"));
		designButton.setPreferredSize(new Dimension(100, 30));
		designButton.setBackground(Color.WHITE);

		// Creates the play button
		playButton = new JButton(langText.getString("play"));
		playButton.setPreferredSize(new Dimension(100, 30));
		playButton.setBackground(Color.WHITE);

		
		// Panel to hold the client and server buttons
		JPanel serverPanel = new JPanel();
		serverPanel.setBackground(new Color(17, 15, 15));
		serverPanel.setPreferredSize(new Dimension(250, 40));
		
		// Creates the client button
		clientButton = new JButton(langText.getString("client"));
		clientButton.setPreferredSize(new Dimension(100, 30));
		clientButton.setBackground(Color.WHITE);
		
		// Creates the server button
		serverButton = new JButton(langText.getString("server"));
		serverButton.setPreferredSize(new Dimension(100, 30));
		serverButton.setBackground(Color.WHITE);
		
		// Adds the buttons to the panels
		startPanel.add(designButton);
		startPanel.add(playButton);
		serverPanel.add(clientButton);
		serverPanel.add(serverButton);

		// Main panel to hold all of the buttons; design, play, client and server
		JPanel launcherButtonsPanel = new JPanel();
		launcherButtonsPanel.setBackground(new Color(17, 15, 15));
		launcherButtonsPanel.setPreferredSize(new Dimension(100, 200));
		
		launcherButtonsPanel.add(startPanel);
		launcherButtonsPanel.add(serverPanel);
		
		////////////////////////////////////////////////////////////////

		// JFrame for the launcher/start window
		startWindow = new JFrame();
		startWindow.setLayout(new BorderLayout());
		startWindow.add(titlePanel, BorderLayout.NORTH);
		startWindow.add(launcherButtonsPanel);
		startWindow.add(makeLanguagePanel(currentLocale, langText), BorderLayout.SOUTH);
		startWindow.pack();
		startWindow.setVisible(true);
		startWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startWindow.setResizable(false);
		startWindow.setSize(400, 325);
		startWindow.setLocationRelativeTo(null);
	}

	/**
	 * This is the method to build the design game mode
	 * 
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 */
	protected void Design(Locale currentLocale, ResourceBundle langText) {

		designBack = new JButton("Back");
		designWindow = new JFrame();
		designWindow.setLayout(new BorderLayout());
		makeMenuBar(designWindow, currentLocale, langText, 0);

		////////////////////////////////////////////////////////////////

		// JFrame for the design window
		designWindow.add(makeTitlePanel(), BorderLayout.NORTH);
		designWindow.add(makeBoardPanel(langText, gridSize, false), BorderLayout.CENTER); // mark mode false as default
		designWindow.add(makeLeftPanel(currentLocale, langText, 0), BorderLayout.WEST); // 0 for design
		designWindow.add(makeControlPanel(), BorderLayout.EAST);
		designWindow.pack();
		designWindow.setResizable(false);
		designWindow.setVisible(true);
		//designWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		designWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		designWindow.setSize(1000, 600);
		designWindow.setLocationRelativeTo(null);
	}

	/**
	 * This is the method to build the play game mode
	 * 
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 */
	protected void Play(Locale currentLocale, ResourceBundle langText) {
		picrossWindow = new JFrame();
		picrossWindow.setLayout(new BorderLayout());
		makeMenuBar(picrossWindow, currentLocale, langText, 1);
		picrossWindow.add(makeTitlePanel(), BorderLayout.NORTH);
		picrossWindow.add(makeLeftPanel(currentLocale, langText, 1), BorderLayout.WEST); // 1 for play mode
		picrossWindow.add(makeControlPanel(), BorderLayout.EAST);
		picrossWindow.add(makeBoardPanel(langText, gridSize, false), BorderLayout.CENTER); // mark mode false as default
		picrossWindow.pack();

		////////////////////////////////////////////////////////////////

		// JFrame for the picross/game window
		picrossWindow.setResizable(false);
		picrossWindow.setVisible(true);
		//picrossWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		picrossWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		picrossWindow.setSize(1000, 600);
		picrossWindow.setLocationRelativeTo(null);
	}

	/**
	 * This is the method to build the play game mode
	 * 
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 */
	protected void Client(Locale currentLocale, ResourceBundle langText) {
		// JFrame for the client window
		clientWindow = new JFrame();
		clientWindow.setLayout(new BorderLayout());
		clientWindow.add(makeTitlePanel(), BorderLayout.NORTH);
		clientWindow.add(makeClientPanel(currentLocale, langText), BorderLayout.CENTER);
		//clientWindow.add(makeServerLog());
		clientWindow.pack();
		clientWindow.setResizable(false);
		clientWindow.setVisible(true);
		clientWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		clientWindow.setTitle("Client - Skylar Phanenhour, Ahnaf Kamal");
		clientWindow.setSize(600, 400);
		clientWindow.setLocationRelativeTo(null);
	}

	/**
	 * This is the method to build the play game mode
	 * 
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 */
	protected void Server(Locale currentLocale, ResourceBundle langText) {
		// JFrame for the server window
		serverWindow = new JFrame();
		serverWindow.setLayout(new BorderLayout());
		serverWindow.add(makeTitlePanel(), BorderLayout.NORTH);
		serverWindow.add(makeServerPanel(currentLocale, langText), BorderLayout.CENTER);
		serverWindow.pack();
		serverWindow.setResizable(false);
		serverWindow.setVisible(true);
		serverWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		serverWindow.setTitle("Server - Skylar Phanenhour, Ahnaf Kamal");
		serverWindow.setSize(600, 400);
		serverWindow.setLocationRelativeTo(null);
	}
	
	protected JPanel makeServerLog() {
		JPanel serverLogPanel = new JPanel();
		serverLogPanel.setLayout(new BorderLayout());
		
		logTextArea = new JTextArea();
		logTextArea.setLineWrap(true);
		logTextArea.setWrapStyleWord(true);
		logTextArea.setPreferredSize(new Dimension(200, 10000));
		logTextArea.setBorder(new LineBorder(new Color(17, 15, 15)));
		logTextArea.setEditable(false);
		
		// Makes the scroll bar for our text area
		JScrollPane logScroll = new JScrollPane(logTextArea);
		logScroll.setPreferredSize(new Dimension(575, 125));
		logScroll.getVerticalScrollBar().setUnitIncrement(10);
		logScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		textChat = new JTextField("Type to enter a chat: ");
		textChat.setForeground(Color.GRAY);
		textChat.setBorder(new LineBorder(new Color(17, 15, 15)));
		
		serverLogPanel.add(logScroll, BorderLayout.CENTER);
		serverLogPanel.add(textChat, BorderLayout.SOUTH);

		return serverLogPanel;
	}
	
	protected JPanel makeClientPanel(Locale currentLocale, ResourceBundle langText) {
		JPanel clientPanel = new JPanel();

		clientPanel.add(makeClientButtons(currentLocale, langText));
		clientPanel.add(makeServerLog());

		return clientPanel;
	}

	
	protected JPanel makeClientButtons(Locale currentLocale, ResourceBundle langText) {
		JPanel clientButtonPanel = new JPanel();
		clientButtonPanel.setPreferredSize(new Dimension(600, 75));
		
		clientUserLabel = new JLabel(langText.getString("user") + ": ");
		clientUserNameText = new JTextField();
		clientUserNameText.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		clientUserNameText.setPreferredSize(new Dimension(100, 25));
		
		clientServerLabel = new JLabel(langText.getString("server") + ": ");
		clientServerText = new JTextField();
		clientServerText.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		clientServerText.setPreferredSize(new Dimension(100, 25));
		
		clientPortLabel = new JLabel(langText.getString("port") + ": ");
		clientPortText = new JTextField();
		clientPortText.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		clientPortText.setPreferredSize(new Dimension(75, 25));
		
		
		/****************************
		 * TODO: the text for the buttons when french is selected, 
		 * get cut off due to the size
		 ****************************/
		
		clientConnect = new JButton(langText.getString("connect"));
		clientConnect.setPreferredSize(new Dimension(90, 30));
		clientConnect.setBackground(Color.WHITE);
		
		clientEnd = new JButton(langText.getString("end"));
		clientEnd.setPreferredSize(new Dimension(60, 30));
		clientEnd.setBackground(Color.WHITE);
		
		clientPlay = new JButton(langText.getString("play"));
		clientPlay.setPreferredSize(new Dimension(75, 30));
		clientPlay.setBackground(Color.WHITE);
		
		clientLoad = new JButton(langText.getString("load"));
		clientLoad.setPreferredSize(new Dimension(75, 30));
		clientLoad.setBackground(Color.WHITE);
		
		clientSendGame = new JButton(langText.getString("send_game"));
		clientSendGame.setPreferredSize(new Dimension(100, 30));
		clientSendGame.setBackground(Color.WHITE);
		///clientSendGame.setEnabled(false);
		
		clientSendData = new JButton(langText.getString("send_data"));
		clientSendData.setPreferredSize(new Dimension(100, 30));
		clientSendData.setBackground(Color.WHITE);		
		
		clientDesign = new JButton(langText.getString("design"));
		clientDesign.setPreferredSize(new Dimension(100, 30));
		clientDesign.setBackground(Color.WHITE);		
		
		clientButtonPanel.add(clientUserLabel);
		clientButtonPanel.add(clientUserNameText);
		clientButtonPanel.add(clientServerLabel);
		clientButtonPanel.add(clientServerText);
		clientButtonPanel.add(clientPortLabel);
		clientButtonPanel.add(clientPortText);
		clientButtonPanel.add(clientConnect);
		clientButtonPanel.add(clientEnd);
		clientButtonPanel.add(clientPlay);
		clientButtonPanel.add(clientLoad);
		clientButtonPanel.add(clientSendGame);
		clientButtonPanel.add(clientSendData);
		clientButtonPanel.add(clientDesign);
		return clientButtonPanel;
	}
	
	protected JPanel makeServerPanel(Locale currentLocale, ResourceBundle langText) {
		JPanel serverPanel = new JPanel();

		serverPanel.add(makeServerButtons(currentLocale, langText));
		serverPanel.add(makeServerLog());

		return serverPanel;
	}

	
	protected JPanel makeServerButtons(Locale currentLocale, ResourceBundle langText) {
		JPanel serverButtonPanel = new JPanel();
		serverButtonPanel.setPreferredSize(new Dimension(600, 50));
		
		serverPortLabel = new JLabel(langText.getString("port") + ": ");
		serverPortText = new JTextField();
		serverPortText.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		serverPortText.setPreferredSize(new Dimension(75, 25));
		
		leaderboardButton = new JButton(langText.getString("leaderboard"));
		leaderboardButton.setPreferredSize(new Dimension(125, 30));
		leaderboardButton.setBackground(Color.WHITE);
		
		startServer = new JButton(langText.getString("start_server"));
		startServer.setPreferredSize(new Dimension(125,30));
		startServer.setBackground(Color.WHITE);
		
		disconnectServer = new JButton(langText.getString("disconnect"));
		disconnectServer.setPreferredSize(new Dimension(125, 30));
		disconnectServer.setBackground(Color.WHITE);		
		
		//endConnections = new JButton(langText.getString("end"));
		//endConnections.setPreferredSize(new Dimension(60, 30));
		///endConnections.setBackground(Color.WHITE);		
		
		serverButtonPanel.add(serverPortLabel);
		serverButtonPanel.add(serverPortText);
		serverButtonPanel.add(startServer);
		serverButtonPanel.add(disconnectServer);
		serverButtonPanel.add(leaderboardButton);
		//serverButtonPanel.add(endConnections);
		return serverButtonPanel;
	}
	
	/**
	 * This method is used to make the menu bar and all its sub-menus
	 * 
	 * @param window        - is the JFrame that the menu bar will be put on
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 * @param gameMode      - Checks if the the user is in the design or play mode
	 */
	protected void makeMenuBar(JFrame window, Locale currentLocale, ResourceBundle langText, int gameMode) {
		// Makes a new menu bar for the JFrame passed in params
		JMenuBar menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);

		// Creates all of the menu items for the game menu with text based on the
		// selected languages and the associated image icons
		gameMenuItemsContainer = new JMenu(langText.getString("game"));
		newMenuOption = new JMenuItem(langText.getString("newBoard"),new ImageIcon(getClass().getResource("/images/newboard.jpg")));
		exitMenuOption = new JMenuItem(langText.getString("exit"),new ImageIcon(getClass().getResource("/images/exit.jpg")));
		resetMenuOption = new JMenuItem(langText.getString("reset"),new ImageIcon(getClass().getResource("/images/reset.jpg")));
		solveMenuOption = new JMenuItem(langText.getString("solve"),new ImageIcon(getClass().getResource("/images/solve.jpg")));
		toLauncherMenuOption = new JMenuItem(langText.getString("toLauncher"),new ImageIcon(getClass().getResource("/images/toLauncher.jpg")));
		saveMenuOption = new JMenuItem(langText.getString("save"),new ImageIcon(getClass().getResource("/images/save.jpg")));
		loadMenuOption = new JMenuItem(langText.getString("load"),new ImageIcon(getClass().getResource("/images/load.jpg")));
		// Builds a sub-menu for the grid sizes
		gridSizeItemsContainer = new JMenu(langText.getString("gridSize"));
		gridSizeItemsContainer.setIcon(new ImageIcon(getClass().getResource("/images/gridSize.jpg")));
		gridSizeItemsContainer.add(fiveFive);
		gridSizeItemsContainer.add(sixSix);
		gridSizeItemsContainer.add(sevSev);

		// Builds the game menu with the menu items, different menu items are added for
		// design and play
		gameMenuItemsContainer.add(saveMenuOption);
		gameMenuItemsContainer.add(loadMenuOption);
		gameMenuItemsContainer.addSeparator();

		// Adds the menu items for the design mode
		if (gameMode == 0) {
			gameMenuItemsContainer.add(resetMenuOption);
			gameMenuItemsContainer.add(gridSizeItemsContainer);
			gameMenuItemsContainer.addSeparator();
		}

		// Adds the menu items for the play mode
		else {
			// add networking stuff
			
			gameMenuItemsContainer.add(newMenuOption);
			gameMenuItemsContainer.add(resetMenuOption);
			gameMenuItemsContainer.add(gridSizeItemsContainer);
			gameMenuItemsContainer.addSeparator();
			gameMenuItemsContainer.add(solveMenuOption);
			gameMenuItemsContainer.addSeparator();
		}

		gameMenuItemsContainer.add(toLauncherMenuOption);
		gameMenuItemsContainer.add(exitMenuOption);

		////////////////////////////////////////////////////////////////

		// Creates all of the menu items for the help menu with the selected language
		// text and the associated image icons
		helpMenuItemsContainer = new JMenu(langText.getString("help"));
		aboutMenuOption = new JMenuItem(langText.getString("about"),new ImageIcon(getClass().getResource("/images/instructions.jpg")));
		// Builds a sub-menu for the color choosers
		colourMenu = new JMenu(langText.getString("colours"));
		colourMenu.setIcon(new ImageIcon(getClass().getResource("/images/colors.jpg")));
		backgroundColour = new JMenuItem("Background Colour");
		textColour = new JMenuItem("Text Colour");
		componentColour = new JMenuItem("Component Colour");
		colourMenu.add(backgroundColour);
		colourMenu.add(textColour);
		colourMenu.add(componentColour);

		// Builds the help menu with the menu items
		helpMenuItemsContainer.add(aboutMenuOption);
		helpMenuItemsContainer.add(colourMenu);

		networkingMenuItemsContainer = new JMenu("Online");
		clientMenuOption = new JMenuItem("Client");
		serverMenuOption = new JMenuItem("Server");
		networkingMenuItemsContainer.add(clientMenuOption);
		networkingMenuItemsContainer.add(serverMenuOption);

		
		// Adds the game and help menus into the menubar
		menuBar.add(gameMenuItemsContainer);
		menuBar.add(helpMenuItemsContainer);
		menuBar.add(networkingMenuItemsContainer);

	}

	/**
	 * This method is used to make the language panel to switch between english and
	 * french
	 * 
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 * @return - returns the built language panel
	 */
	private JPanel makeLanguagePanel(Locale currentLocale, ResourceBundle langText) {

		// If the selected language is english
		if (currentLocale.getCountry() == "US") {
			engRadio = new JRadioButton(langText.getString("english"), true);
			frRadio = new JRadioButton(langText.getString("french"));
		}
		// If the selected language is french
		else {
			engRadio = new JRadioButton(langText.getString("english"));
			frRadio = new JRadioButton(langText.getString("french"), true);
		}
		// Adds the radio buttons to a button group
		ButtonGroup langButtons = new ButtonGroup();
		langButtons.add(engRadio);
		langButtons.add(frRadio);
		langLabel = new JLabel(langText.getString("languages"));
		// Panel to align the language radio buttons
		languageButtonPanel = new JPanel();
		languageButtonPanel.setLayout(new BoxLayout(languageButtonPanel, BoxLayout.Y_AXIS));
		// Adds the radio buttons to the panel
		languageButtonPanel.add(engRadio);
		languageButtonPanel.add(frRadio);
		// Main language panel that stores/aligns all of the labels/radio buttons
		languagePanel = new JPanel();
		languagePanel.add(langLabel);
		languagePanel.add(languageButtonPanel);

		return languagePanel;
	}

	/**
	 * This method is used to make a panel for the grid size combo box
	 * 
	 * @param langText - Used to get the text from the language file.
	 * @return - returns the build grid size combo box panel
	 */
	private JPanel makeGridSizeCombo(ResourceBundle langText) {

		// Sets the String options in the combo box
		String options[] = { "5x5", "6x6", "7x7" };
		gridSizeCmbo = new JComboBox<>(options);
		gridSizeCmbo.setBackground(Color.WHITE);
		gridSizeLabel = new JLabel(langText.getString("gridSize"));
		gridSizeComboPanel = new JPanel();
		gridSizeComboPanel.add(gridSizeLabel);
		gridSizeComboPanel.add(gridSizeCmbo);
		gridSizeComboPanel.setPreferredSize(new Dimension(200, 30));

		return gridSizeComboPanel;
	}

	/**
	 * This method is used to make the left panel, that stores all of the menu
	 * handling, like score counter, timer counter, and all of the configuration
	 * buttons.
	 * 
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 * @return - returns the built left panel
	 */
	private JPanel makeLeftPanel(Locale currentLocale, ResourceBundle langText, int gameMode) {

		// Score panel to hold the label and the counter text field
		scoreLabel = new JLabel(langText.getString("score"));
		scoreCounter = new JTextField("0");
		scoreCounter.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		scoreCounter.setPreferredSize(new Dimension(100, 25));
		scoreCounter.setEditable(false);
		scorePanel = new JPanel();
		scorePanel.add(scoreLabel);
		scorePanel.add(scoreCounter);

		////////////////////////////////////////////////////////////////

		// Timer panel to hold the label and the counter text field
		timerLabel = new JLabel(langText.getString("timer"));
		timerCounter = new JTextField("00:00");
		timerCounter.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		timerCounter.setPreferredSize(new Dimension(100, 25));
		timerCounter.setEditable(false);
		timerPanel = new JPanel();
		timerPanel.add(timerLabel);
		timerPanel.add(timerCounter);

		////////////////////////////////////////////////////////////////

		// All of the configuration buttons
		resetButton = new JButton(langText.getString("reset"));
		resetButton.setBackground(Color.WHITE);
		resetButton.setPreferredSize(new Dimension(120, 25));

		solveButton = new JButton(langText.getString("solve"));
		solveButton.setBackground(Color.WHITE);
		solveButton.setPreferredSize(new Dimension(120, 25));

		newBoardButton = new JButton(langText.getString("newBoard"));
		newBoardButton.setBackground(Color.WHITE);
		newBoardButton.setPreferredSize(new Dimension(120, 25));

		instructionsButton = new JButton(langText.getString("instructions"));
		instructionsButton.setBackground(Color.WHITE);
		instructionsButton.setPreferredSize(new Dimension(120, 25));

		playToLauncher = new JButton(langText.getString("back"));
		playToLauncher.setBackground(Color.WHITE);
		playToLauncher.setPreferredSize(new Dimension(120, 25));

		////////////////////////////////////////////////////////////////

		// Configuration panel for the language
		configurationPanel = new JPanel();
		configurationPanel.setLayout(new GridLayout(1, 2));
		configurationPanel.add(makeLanguagePanel(currentLocale, langText));
		configurationPanel.setPreferredSize(new Dimension(225, 100));

		leftPanel = new JPanel();
		buttonPanel = new JPanel();
		// Vertically aligns the buttons in the panel
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));

		// If the game mode is play, add certain buttons
		if (gameMode == 1) {
			leftPanel.add(scorePanel);
			leftPanel.add(timerPanel);
			buttonPanel.add(newBoardButton);
			buttonPanel.add(solveButton);
			buttonPanel.setPreferredSize(new Dimension(120, 180));
		}
		// If the game mode is design
		else {
			buttonPanel.setPreferredSize(new Dimension(120, 120));
		}

		// Adds the rest of the buttons, that both design and play use
		buttonPanel.add(resetButton);
		buttonPanel.add(instructionsButton);
		buttonPanel.add(playToLauncher);

		////////////////////////////////////////////////////////////////

		// Adds everything to the left panel
		leftPanel.add(makeGridSizeCombo(langText));
		leftPanel.add(buttonPanel);
		leftPanel.add(configurationPanel);
		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, (new Color(17, 15, 15))));
		leftPanel.setPreferredSize(new Dimension(250, 200));

		return leftPanel;
	}

	/**
	 * This method is used to make the title panel that holds the image icon for our
	 * game
	 * 
	 * @return - returns the built title panel with the image icon
	 */
	private JPanel makeTitlePanel() {
		JPanel titlePanel = new JPanel();
		ImageIcon picrossLogo = new ImageIcon(getClass().getResource("/images/picross.jpg"));
		JLabel picrossLabel = new JLabel();
		picrossLabel.setIcon(picrossLogo);
		titlePanel.setPreferredSize(new Dimension(1000, 125));
		titlePanel.add(picrossLabel);

		return titlePanel;
	}

	/**
	 * This method is used to make the history panel with the scroll bar and text
	 * area
	 * 
	 * @return - returns the built history panel
	 */
	private JPanel makeHistoryPanel() {
		// This is the history text area where all of the user inputs will be recorded
		historyPanel = new JPanel();
		history = new JTextArea();
		history.setLineWrap(true);
		history.setWrapStyleWord(true);
		history.setPreferredSize(new Dimension(200, 10000));
		history.setBorder(new LineBorder(new Color(17, 15, 15)));
		history.setEditable(false);

		// Makes the scroll bar for our text area
		JScrollPane scroll = new JScrollPane(history);
		scroll.setPreferredSize(new Dimension(200, 300));
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		historyPanel.add(scroll);

		return historyPanel;
	}

	/**
	 * This method is used to make the control/right panel that holds the history
	 * panel
	 * 
	 * @return - returns the built control panel
	 */
	private JPanel makeControlPanel() {
		controlPanel = new JPanel();
		controlPanel.setPreferredSize(new Dimension(250, 400));
		controlPanel.add(makeHistoryPanel());
		

		gameChat = new JTextField("Type to enter a chat: ");	
		gameChat.setPreferredSize(new Dimension(200, 75));
		gameChat.setForeground(Color.GRAY);
		gameChat.setBorder(new LineBorder(new Color(17, 15, 15)));

		controlPanel.add(gameChat);
		return controlPanel;
	}

	/**
	 * This method is used to make the board panel that holds the 2d button grid
	 * 
	 * @param langText - Used to get the text from the language file.
	 * @param gridSize - Gets the current selected grid size, to know how many times
	 *                 to loop the array
	 * @param markMode - Detects whether or not the mark check box is selected
	 * 
	 * @return - returns the built board panel
	 */
	protected JPanel makeBoardPanel(ResourceBundle langText, int gridSize, boolean markMode) {

		// Row panel to store the labels horizontally
		rowPanel = new JPanel();
		rowPanel.setLayout(new GridLayout(gridSize, 1));
		rowPanel.setPreferredSize(new Dimension(75, 0));

		// Loops through the length of the grid size and makes new JLabels with the text
		for (int i = 0; i < gridSize; i++) {
			JLabel rowLabel = new JLabel(viewRowLabels[i], SwingConstants.CENTER);
			rowPanel.add(rowLabel);
		}


		// Checks if the mark check box is selected
		markCheckBox = new JCheckBox(langText.getString("mark"));
		if (markMode == true) {
			markCheckBox.setSelected(true);
		}
		markCheckBox.setHorizontalAlignment(SwingConstants.CENTER);


		// Col panel to store the labels vertically
		colPanel = new JPanel();
		colPanel.add(markCheckBox);
		colPanel.setLayout(new GridLayout(1, gridSize + 1));
		colPanel.setPreferredSize(new Dimension(0, 75));
		colPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(17, 15, 15)));

		// Loops through the length of the grid size and makes new JLabels with the text
		for (int i = 0; i < gridSize; i++) {
			JLabel colLabel = new JLabel(viewColLabels[i], SwingConstants.CENTER);
			colPanel.add(colLabel);
		}

		// Makes the 2d button grid, based on the grid size
		JPanel gridButtonPanel = new JPanel();
		gridButtonPanel.setLayout(new GridLayout(gridSize, gridSize));
		buttonsPlay = new JButton[gridSize][gridSize];

		// Loops through the length of the grid size and makes a 2d button array
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				newGridButton = new JButton();
				newGridButton.setBackground(Color.WHITE);
				buttonsPlay[i][j] = newGridButton;
				gridButtonPanel.add(buttonsPlay[i][j]);
			}
		}

		// Adds the components to the board panel
		boardPanel = new JPanel();
		boardPanel.setLayout(new BorderLayout());
		boardPanel.add(colPanel, BorderLayout.NORTH);
		boardPanel.add(rowPanel, BorderLayout.WEST);
		boardPanel.add(gridButtonPanel, BorderLayout.CENTER);

		return boardPanel;
	}

	/**
	 * This method is used to update the row labels in the design mode
	 * 
	 * @param newText - String value that stores the text to be set
	 * @param index   - Used to remove the old label, and add the new label to the
	 *                row panel
	 */
	protected void updateDesignRow(String newText, int index) {
		JLabel newLabel = new JLabel(newText, SwingConstants.CENTER);
		rowPanel.remove(index);
		rowPanel.invalidate();
		rowPanel.add(newLabel, index);
		rowPanel.revalidate();
	}

	/**
	 * This method is used to update the col labels in the design mode
	 * 
	 * @param newText - String value that stores the text to be set
	 * @param index   - Used to remove the old label, and add the new label to the
	 *                col panel
	 */
	protected void updateDesignCol(String newText, int index) {
		JLabel newLabel = new JLabel(newText, SwingConstants.CENTER);
		colPanel.remove(index + 1);
		colPanel.invalidate();
		colPanel.add(newLabel, index + 1);
		colPanel.revalidate();
	}

	/**
	 * This method is used to change all of the text when the language radio button
	 * is changed. The correct text is received from the Message Bungle property
	 * files.
	 * 
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 */
	protected void updateText(Locale currentLocale, ResourceBundle langText) {

		// Gets the text from the ResourceBundle and sets it everywhere needed
		clientButton.setText(langText.getString("client"));
		serverButton.setText(langText.getString("server"));
		timerLabel.setText(langText.getString("timer"));	
		scoreLabel.setText(langText.getString("score"));
		userNameLabel.setText(langText.getString("user_name"));
		bestTimeLabel.setText(langText.getString("best_time"));
		bestScoreLabel.setText(langText.getString("best_score"));
		gameCompleteSave.setText(langText.getString("save"));
		gameCompleteClose.setText(langText.getString("back"));
		gridSizeLabel.setText(langText.getString("gridSize"));
		markCheckBox.setText(langText.getString("mark"));
		engRadio.setText(langText.getString("english"));
		frRadio.setText(langText.getString("french"));
		playToLauncher.setText(langText.getString("back"));
		resetButton.setText(langText.getString("reset"));
		instructionsButton.setText(langText.getString("instructions"));
		solveButton.setText(langText.getString("solve"));
		langLabel.setText(langText.getString("languages"));
		newBoardButton.setText(langText.getString("newBoard"));
		solveMenuOption.setText(langText.getString("solve"));
		newMenuOption.setText(langText.getString("newBoard"));
		resetMenuOption.setText(langText.getString("reset"));
		saveMenuOption.setText(langText.getString("save"));
		loadMenuOption.setText(langText.getString("load"));
		toLauncherMenuOption.setText(langText.getString("toLauncher"));
		exitMenuOption.setText(langText.getString("exit"));
		helpMenuItemsContainer.setText(langText.getString("help"));
		colourMenu.setText(langText.getString("colours"));
		gridSizeItemsContainer.setText(langText.getString("gridSize"));
		aboutMenuOption.setText(langText.getString("about"));
		gameMenuItemsContainer.setText(langText.getString("game"));
	}

	/**
	 * This method is used to show the JFrame when the game is completed.
	 * 
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 * @param bestScore     - Gets the final score when the game is complete
	 * @param bestTime      - Gets the final time when the game is complete
	 */
	protected void gameCompleted(Locale currentLocale, ResourceBundle langText, int bestScore, int bestTime) {
		
		// Panel for the username label and text field
		userNameLabel = new JLabel("<html>"+langText.getString("user_name")+"</html>", SwingConstants.CENTER);
		userNameLabel.setPreferredSize(new Dimension(110, 25));
		nameTextField = new JTextField();
		nameTextField.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		nameTextField.setPreferredSize(new Dimension(125, 25));

		nameTextField = new JTextField();
		nameTextField.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		nameTextField.setPreferredSize(new Dimension(125, 25));

		JPanel namePanel = new JPanel();
		namePanel.setPreferredSize(new Dimension(275, 35));
		namePanel.add(userNameLabel);
		namePanel.add(nameTextField);

		// Panel for the score label and text field
		bestScoreLabel = new JLabel(langText.getString("best_score"), SwingConstants.CENTER);
		bestScoreLabel.setPreferredSize(new Dimension(110, 25));
		bestScoreTextField = new JTextField();
		bestScoreTextField.setText(" " + Integer.toString(bestScore));
		bestScoreTextField.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		bestScoreTextField.setBackground(Color.WHITE);
		bestScoreTextField.setPreferredSize(new Dimension(125, 25));
		bestScoreTextField.setEditable(false);

		JPanel bestScorePanel = new JPanel();
		bestScorePanel.setPreferredSize(new Dimension(275, 35));
		bestScorePanel.add(bestScoreLabel);
		bestScorePanel.add(bestScoreTextField);

		// Panel for the time label and text field
		bestTimeLabel = new JLabel(langText.getString("best_time"), SwingConstants.CENTER);
		bestTimeLabel.setPreferredSize(new Dimension(110, 25));
		bestTimeTextField = new JTextField();
		bestTimeTextField.setText(" " + Integer.toString(bestTime) + " " + langText.getString("seconds"));
		bestTimeTextField.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		bestTimeTextField.setBackground(Color.WHITE);
		bestTimeTextField.setPreferredSize(new Dimension(125, 25));
		bestTimeTextField.setEditable(false);

		JPanel bestTimePanel = new JPanel();
		bestTimePanel.setPreferredSize(new Dimension(275, 35));
		bestTimePanel.add(bestTimeLabel);
		bestTimePanel.add(bestTimeTextField);

		// Higher level panel that stores the three panels above
		JPanel statsPanel = new JPanel();
		statsPanel.setPreferredSize(new Dimension(300, 400));
		statsPanel.add(namePanel);
		statsPanel.add(bestScorePanel);
		statsPanel.add(bestTimePanel);

		// JButton to save the user's stats
		gameCompleteSave.setText(langText.getString("save"));
		gameCompleteSave.setPreferredSize(new Dimension(100, 30));
		gameCompleteSave.setBackground(Color.WHITE);

		// JButton to close the game complete window
		gameCompleteClose.setText(langText.getString("back"));
		gameCompleteClose.setPreferredSize(new Dimension(100, 30));
		gameCompleteClose.setBackground(Color.WHITE);

		// Adds the save and close buttons to a panel, to keep the formatting
		JPanel gameOverButtonPanel = new JPanel();
		gameOverButtonPanel.setPreferredSize(new Dimension(100, 100));
		gameOverButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		gameOverButtonPanel.add(gameCompleteSave);
		gameOverButtonPanel.add(gameCompleteClose);

		// Adds the button panel
		JPanel gameOverRightPanel = new JPanel();
		gameOverRightPanel.setPreferredSize(new Dimension(210, 200));
		gameOverRightPanel.add(gameOverButtonPanel);

		// JFrame for the game completed window
		gameCompleteWindow = new JFrame();
		gameCompleteWindow.setLayout(new BorderLayout());
		gameCompleteWindow.add(makeTitlePanel(), BorderLayout.NORTH);
		gameCompleteWindow.add(statsPanel, BorderLayout.WEST);
		gameCompleteWindow.add(gameOverRightPanel, BorderLayout.EAST);
		//gameCompleteWindow.setTitle("Game Complete - Skylar Phanenhour, Ahnaf Kamal");
		gameCompleteWindow.setTitle(langText.getString("game_complete"));
		gameCompleteWindow.setResizable(false);
		gameCompleteWindow.setSize(525, 300);
		gameCompleteWindow.setVisible(true);
		gameCompleteWindow.setLocationRelativeTo(null);

	}

	/**
	 * This method is used to show the JFrame when the instructions button is
	 * clicked
	 * 
	 * @param currentLocale - Used to get the selected language.
	 * @param langText      - Used to get the text from the language file.
	 */
	protected void Instructions(Locale currentLocale, ResourceBundle langText) {

		// JTextArea that is used to hold all of the text, to describe the game
		// instructions
		JTextArea instructionsLabel = new JTextArea();
		instructionsLabel.setPreferredSize(new Dimension(450, 325));
		instructionsLabel.setText(langText.getString("instructions_text"));
		instructionsLabel.setLineWrap(true);
		instructionsLabel.setEditable(false);
		instructionsLabel.setOpaque(false);
		instructionsLabel.setFont(new Font("Calibri Regular", Font.PLAIN, 16));

		// Button the close the instructions window
		instructionsBack = new JButton("Back");
		instructionsBack.setFont(new Font("Calibri Regular", Font.BOLD, 12));

		// Panel to hold all of the text and the button to close the window
		JPanel instructionsPanel = new JPanel();
		instructionsPanel.setPreferredSize(new Dimension(500, 400));
		instructionsPanel.add(instructionsLabel);
		instructionsPanel.add(instructionsBack);

		// JFrame for the instructions window
		instructionsWindow = new JFrame();
		instructionsWindow.add(instructionsPanel);
		instructionsWindow.setTitle("Instructions - Skylar Phanenhour, Ahnaf Kamal");
		instructionsWindow.setResizable(false);
		instructionsWindow.setSize(525, 425);
		instructionsWindow.setVisible(true);
		instructionsWindow.setLocationRelativeTo(null);
	}

	/**
	 * This method is used to display all of the correct tiles in the grid
	 * 
	 * @param gridSize - Gets the currently selected grid size to determine how many
	 *                 times to loop
	 */
	protected void solveBoard(int gridSize) {

		// Loops through the 2d array and changed the correct tiles color and disables
		// all buttons
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (viewRows[i].charAt(j) == '1') {
					buttonsPlay[i][j].setBackground(tile_color);
				}
				buttonsPlay[i][j].setEnabled(false);
			}
		}
	}

	/**
	 * This method is used to reset the current board of the game
	 */
	protected void resetBoard() {

		// Loops through each button, changes the color and sets everything to enabled
		for (JButton[] i : buttonsPlay) {
			for (JButton j : i) {
				j.setBackground(component_color);
				j.setEnabled(true);
			}
		}
	}

	/**
	 * This method is used to reset all of the labels in the design mode
	 */
	protected void resetRowsAndCol() {

		// Loops through the arrays and makes the Strings = "0"
		for (int i = 0; i < gridSize; i++) {
			viewRowLabels[i] = "0";
			viewColLabels[i] = "0";
		}

		viewRows = new String[gridSize];
		viewCols = new String[gridSize];
	}

	/**	 
	 * Get the viewRows
	 * @return the viewRows
	 */
	protected String[] getViewRows() {
		return viewRows;
	}

	/**	 
	 * Set the viewRows
	 * @param viewRows the viewRows to set
	 */
	protected void setViewRows(String[] viewRows) {
		this.viewRows = viewRows;
	}

	/**	 
	 * Get the viewCols
	 * @return the viewCols
	 */
	protected String[] getViewCols() {
		return viewCols;
	}

	/**	 
	 * Set the viewCols
	 * @param viewCols the viewCols to set
	 */
	protected void setViewCols(String[] viewCols) {
		this.viewCols = viewCols;
	}

	/**	 
	 * Get the nameTextField
	 * @return the nameTextField
	 */
	protected JTextField getNameTextField() {
		return nameTextField;
	}

	/**	 
	 * Set the nameTextField
	 * @param nameTextField the nameTextField to set
	 */
	protected void setNameTextField(JTextField nameTextField) {
		this.nameTextField = nameTextField;
	}

	/**	 
	 * Get the viewRowLabels
	 * @return the viewRowLabels
	 */
	protected String[] getViewRowLabels() {
		return viewRowLabels;
	}

	/**	 
	 * Set the viewRowLabels
	 * @param viewRowLabels the viewRowLabels to set
	 */
	protected void setViewRowLabels(String[] viewRowLabels) {
		this.viewRowLabels = viewRowLabels;
	}

	/**	 
	 * Get the viewColLabels
	 * @return the viewColLabels
	 */
	protected String[] getViewColLabels() {
		return viewColLabels;
	}

	/**	 
	 * Set the viewColLabels
	 * @param viewColLabels the viewColLabels to set
	 */
	protected void setViewColLabels(String[] viewColLabels) {
		this.viewColLabels = viewColLabels;
	}

	/**	 
	 * Get the gameCompleteSave
	 * @return the gameCompleteSave
	 */
	protected JButton getGameCompleteSave() {
		return gameCompleteSave;
	}

	/**	 
	 * Set the gameCompleteSave
	 * @param gameCompleteSave the gameCompleteSave to set
	 */
	protected void setGameCompleteSave(JButton gameCompleteSave) {
		this.gameCompleteSave = gameCompleteSave;
	}

	/**	 
	 * Get the gameCompleteClose
	 * @return the gameCompleteClose
	 */
	protected JButton getGameCompleteClose() {
		return gameCompleteClose;
	}

	/**	 
	 * Set the gameCompleteClose
	 * @param gameCompleteClose the gameCompleteClose to set
	 */
	protected void setGameCompleteClose(JButton gameCompleteClose) {
		this.gameCompleteClose = gameCompleteClose;
	}

	/**	 
	 * Get the instructionsButton
	 * @return the instructionsButton
	 */
	protected JButton getInstructionsButton() {
		return instructionsButton;
	}

	/**	 
	 * Set the instructionsButton
	 * @param instructionsButton the instructionsButton to set
	 */
	protected void setInstructionsButton(JButton instructionsButton) {
		this.instructionsButton = instructionsButton;
	}

	/**	 
	 * Get the instructionsBack
	 * @return the instructionsBack
	 */
	protected JButton getInstructionsBack() {
		return instructionsBack;
	}

	/**	 
	 * Set the instructionsBack
	 * @param instructionsBack the instructionsBack to set
	 */
	protected void setInstructionsBack(JButton instructionsBack) {
		this.instructionsBack = instructionsBack;
	}

	/**	 
	 * Get the designWindow
	 * @return the designWindow
	 */
	protected JFrame getDesignWindow() {
		return designWindow;
	}

	/**	 
	 * Get the gameCompleteWindow
	 * @return the gameCompleteWindow
	 */
	protected JFrame getGameCompleteWindow() {
		return gameCompleteWindow;
	}

	/**	 
	 * Set the gameCompleteWindow
	 * @param gameCompleteWindow the gameCompleteWindow to set
	 */
	protected void setGameCompleteWindow(JFrame gameCompleteWindow) {
		this.gameCompleteWindow = gameCompleteWindow;
	}

	/**	 
	 * Set the designWindow
	 * @param designWindow the designWindow to set
	 */
	protected void setDesignWindow(JFrame designWindow) {
		this.designWindow = designWindow;
	}

	/**	 
	 * Get the startWindow
	 * @return the startWindow
	 */
	protected JFrame getStartWindow() {
		return startWindow;
	}

	/**	 
	 * Set the startWindow
	 * @param startWindow the startWindow to set
	 */
	protected void setStartWindow(JFrame startWindow) {
		this.startWindow = startWindow;
	}

	/**	 
	 * Get the instructionsWindow
	 * @return the instructionsWindow
	 */
	protected JFrame getInstructionsWindow() {
		return instructionsWindow;
	}

	/**	 
	 * Set the instructionsWindow
	 * @param instructionsWindow the instructionsWindow to set
	 */
	protected void setInstructionsWindow(JFrame instructionsWindow) {
		this.instructionsWindow = instructionsWindow;
	}

	/**	 
	 * Get the picrossWindow
	 * @return the picrossWindow
	 */
	protected JFrame getPicrossWindow() {
		return picrossWindow;
	}

	/**	 
	 * Set the picrossWindow
	 * @param picrossWindow the picrossWindow to set
	 */
	protected void setPicrossWindow(JFrame picrossWindow) {
		this.picrossWindow = picrossWindow;
	}

	/**	 
	 * Get the clientWindow
	 * @return the clientWindow
	 */
	protected JFrame getClientWindow() {
		return clientWindow;
	}

	/**	 
	 * Set the clientWindow
	 * @param clientWindow the clientWindow to set
	 */
	protected void setClientWindow(JFrame clientWindow) {
		this.clientWindow = clientWindow;
	}
	
	/**	 
	 * Get the gridSizeCmbo
	 * @return the gridSizeCmbo
	 */
	protected JComboBox<String> getGridSizeCmbo() {
		return gridSizeCmbo;
	}

	/**	 
	 * Set the gridSizeCmbo
	 * @param gridSizeCmbo the gridSizeCmbo to set
	 */
	protected void setGridSizeCmbo(JComboBox<String> gridSizeCmbo) {
		this.gridSizeCmbo = gridSizeCmbo;
	}

	/**	 
	 * Get the playButton
	 * @return the playButton
	 */
	protected JButton getPlayButton() {
		return playButton;
	}

	/**	 
	 * Set the playButton
	 * @param playButton the playButton to set
	 */
	protected void setPlayButton(JButton playButton) {
		this.playButton = playButton;
	}

	/**	 
	 * Get the designButton
	 * @return the designButton
	 */
	protected JButton getDesignButton() {
		return designButton;
	}

	/**	 
	 * Set the designButton
	 * @param designButton the designButton to set
	 */
	protected void setDesignButton(JButton designButton) {
		this.designButton = designButton;
	}

	/**	 
	 * Get the clientButton
	 * @return the clientButton
	 */
	protected JButton getClientButton() {
		return clientButton;
	}

	/**	 
	 * Set the clientButton
	 * @param clientButton the clientButton to set
	 */
	protected void setClientButton(JButton clientButton) {
		this.clientButton = clientButton;
	}
	
	/**	 
	 * Get the serverButton
	 * @return the serverButton
	 */
	protected JButton getServerButton() {
		return serverButton;
	}

	/**	 
	 * Set the serverButton
	 * @param serverButton the serverButton to set
	 */
	protected void setServerButton(JButton serverButton) {
		this.serverButton = serverButton;
	}
	
	/**	 
	 * Get the designBack
	 * @return the designBack
	 */
	protected JButton getDesignBack() {
		return designBack;
	}

	/**	 
	 * Set the designBack
	 * @param designBack the designBack to set
	 */
	protected void setDesignBack(JButton designBack) {
		this.designBack = designBack;
	}

	/**	 
	 * Get the resetButton
	 * @return the resetButton
	 */
	protected JButton getResetButton() {
		return resetButton;
	}

	/**	 
	 * Set the resetButton
	 * @param resetButton the resetButton to set
	 */
	protected void setResetButton(JButton resetButton) {
		this.resetButton = resetButton;
	}

	/**	 
	 * Get the solveButton
	 * @return the solveButton
	 */
	protected JButton getSolveButton() {
		return solveButton;
	}

	/**	 
	 * Set the solveButton
	 * @param solveButton the solveButton to set
	 */
	protected void setSolveButton(JButton solveButton) {
		this.solveButton = solveButton;
	}

	/**	 
	 * Get the newBoardButton
	 * @return the newBoardButton
	 */
	protected JButton getNewBoardButton() {
		return newBoardButton;
	}

	/**	 
	 * Set the newBoardButton
	 * @param newBoardButton the newBoardButton to set
	 */
	protected void setNewBoardButton(JButton newBoardButton) {
		this.newBoardButton = newBoardButton;
	}

	/**	 
	 * Get the buttonsPlay
	 * @return the buttonsPlay
	 */
	protected JButton[][] getButtons() {
		return buttonsPlay;
	}

	/**	 
	 * Set the buttonsPlay
	 * @param buttonsPlay the buttonsPlay to set
	 */
	protected void setButtons(JButton[][] buttonsPlay) {
		this.buttonsPlay = buttonsPlay;
	}

	/**	 
	 * Get the playToLauncher
	 * @return the playToLauncher
	 */
	protected JButton getPlayToLauncher() {
		return playToLauncher;
	}

	/**	 
	 * Set the playToLauncher
	 * @param playToLauncher the playToLauncher to set
	 */
	protected void setPlayToLauncher(JButton playToLauncher) {
		this.playToLauncher = playToLauncher;
	}

	/**	 
	 * Get the engRadio
	 * @return the engRadio
	 */
	protected JRadioButton getEngRadio() {
		return engRadio;
	}

	/**	 
	 * Set the engRadio
	 * @param engRadio the engRadio to set
	 */
	protected void setEngRadio(JRadioButton engRadio) {
		this.engRadio = engRadio;
	}

	/**	 
	 * Get the frRadio
	 * @return the frRadio
	 */
	protected JRadioButton getFrRadio() {
		return frRadio;
	}

	/**	 
	 * Set the frRadio
	 * @param frRadio the frRadio to set
	 */
	protected void setFrRadio(JRadioButton frRadio) {
		this.frRadio = frRadio;
	}

	/**	 
	 * Get the leftPanel
	 * @return the leftPanel
	 */
	protected JPanel getLeftPanel() {
		return leftPanel;
	}

	/**	 
	 * Set the leftPanel
	 * @param leftPanel the leftPanel to set
	 */
	protected void setLeftPanel(JPanel leftPanel) {
		this.leftPanel = leftPanel;
	}

	/**	 
	 * Get the boardPanel
	 * @return the boardPanel
	 */
	protected JPanel getBoardPanel() {
		return boardPanel;
	}

	/**	 
	 * Set the boardPanel
	 * @param boardPanel the boardPanel to set
	 */
	protected void setBoardPanel(JPanel boardPanel) {
		this.boardPanel = boardPanel;
	}

	/**	 
	 * Get the markCheckBox
	 * @return the markCheckBox
	 */
	protected JCheckBox getMarkCheckBox() {
		return markCheckBox;
	}

	/**	 
	 * Set the markCheckBox
	 * @param markCheckBox the markCheckBox to set
	 */
	protected void setMarkCheckBox(JCheckBox markCheckBox) {
		this.markCheckBox = markCheckBox;
	}

	/**	 
	 * Get the scoreCounter
	 * @return the scoreCounter
	 */
	protected JTextField getScoreCounter() {
		return scoreCounter;
	}

	/**	 
	 * Set the scoreCounter
	 * @param scoreCounter the scoreCounter to set
	 */
	protected void setScoreCounter(JTextField scoreCounter) {
		this.scoreCounter = scoreCounter;
	}

	/**	 
	 * Get the timerCounter
	 * @return the timerCounter
	 */
	protected JTextField getTimerCounter() {
		return timerCounter;
	}

	/**	 
	 * Set the timerCounter
	 * @param timerCounter the timerCounter to set
	 */
	protected void setTimerCounter(JTextField timerCounter) {
		this.timerCounter = timerCounter;
	}

	/**	 
	 * Get the timerLabel
	 * @return the timerLabel
	 */
	protected JLabel getTimerLabel() {
		return timerLabel;
	}

	/**	 
	 * Set the timerLabel
	 * @param timerLabel the timerLabel to set
	 */
	protected void setTimerLabel(JLabel timerLabel) {
		this.timerLabel = timerLabel;
	}

	/**	 
	 * Get the scoreLabel
	 * @return the scoreLabel
	 */
	protected JLabel getScoreLabel() {
		return scoreLabel;
	}

	/**	 
	 * Set the scoreLabel
	 * @param scoreLabel the scoreLabel to set
	 */
	protected void setScoreLabel(JLabel scoreLabel) {
		this.scoreLabel = scoreLabel;
	}

	/**	 
	 * Get the gridSizeLabel
	 * @return the gridSizeLabel
	 */
	protected JLabel getGridSizeLabel() {
		return gridSizeLabel;
	}

	/**	 
	 * Set the gridSizeLabel
	 * @param gridSizeLabel the gridSizeLabel to set
	 */
	protected void setGridSizeLabel(JLabel gridSizeLabel) {
		this.gridSizeLabel = gridSizeLabel;
	}

	/**	 
	 * Get the langLabel
	 * @return the langLabel
	 */
	protected JLabel getLangLabel() {
		return langLabel;
	}

	/**	 
	 * Set the langLabel
	 * @param langLabel the langLabel to set
	 */
	protected void setLangLabel(JLabel langLabel) {
		this.langLabel = langLabel;
	}

	/**	 
	 * Get the languagePanel
	 * @return the languagePanel
	 */
	protected JPanel getLanguagePanel() {
		return languagePanel;
	}

	/**	 
	 * Set the languagePanel
	 * @param languagePanel the languagePanel to set
	 */
	protected void setLanguagePanel(JPanel languagePanel) {
		this.languagePanel = languagePanel;
	}

	/**	 
	 * Get the languageButtonPanel
	 * @return the languageButtonPanel
	 */
	protected JPanel getLanguageButtonPanel() {
		return languageButtonPanel;
	}

	/**	 
	 * Set the languageButtonPanel
	 * @param languageButtonPanel the languageButtonPanel to set
	 */
	protected void setLanguageButtonPanel(JPanel languageButtonPanel) {
		this.languageButtonPanel = languageButtonPanel;
	}

	/**	 
	 * Get the gridSizeComboPanel
	 * @return the gridSizeComboPanel
	 */
	protected JPanel getGridSizeComboPanel() {
		return gridSizeComboPanel;
	}

	/**	 
	 * Set the gridSizeComboPanel
	 * @param gridSizeComboPanel the gridSizeComboPanel to set
	 */
	protected void setGridSizeComboPanel(JPanel gridSizeComboPanel) {
		this.gridSizeComboPanel = gridSizeComboPanel;
	}

	/**	 
	 * Get the scorePanel
	 * @return the scorePanel
	 */
	protected JPanel getScorePanel() {
		return scorePanel;
	}

	/**	 
	 * Set the scorePanel
	 * @param scorePanel the scorePanel to set
	 */
	protected void setScorePanel(JPanel scorePanel) {
		this.scorePanel = scorePanel;
	}

	/**	 
	 * Get the timerPanel
	 * @return the timerPanel
	 */
	protected JPanel getTimerPanel() {
		return timerPanel;
	}

	/**	 
	 * Set the timerPanel
	 * @param timerPanel the timerPanel to set
	 */
	protected void setTimerPanel(JPanel timerPanel) {
		this.timerPanel = timerPanel;
	}

	/**	 
	 * Get the buttonPanel
	 * @return the buttonPanel
	 */
	protected JPanel getButtonPanel() {
		return buttonPanel;
	}

	/**	 
	 * Set the buttonPanel
	 * @param buttonPanel the buttonPanel to set
	 */
	protected void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	/**	 
	 * Get the configurationPanel
	 * @return the configurationPanel
	 */
	protected JPanel getConfigurationPanel() {
		return configurationPanel;
	}

	/**	 
	 * Set the configurationPanel
	 * @param configurationPanel the configurationPanel to set
	 */
	protected void setConfigurationPanel(JPanel configurationPanel) {
		this.configurationPanel = configurationPanel;
	}

	/**	 
	 * Get the historyPanel
	 * @return the historyPanel
	 */
	protected JPanel getHistoryPanel() {
		return historyPanel;
	}

	/**	 
	 * Set the historyPanel
	 * @param historyPanel the historyPanel to set
	 */
	protected void setHistoryPanel(JPanel historyPanel) {
		this.historyPanel = historyPanel;
	}

	/**	 
	 * Get the controlPanel
	 * @return the controlPanel
	 */
	protected JPanel getControlPanel() {
		return controlPanel;
	}

	/**	 
	 * Set the controlPanel
	 * @param controlPanel the controlPanel to set
	 */
	protected void setControlPanel(JPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	/**	 
	 * Get the colPanel
	 * @return the colPanel
	 */
	protected JPanel getColPanel() {
		return colPanel;
	}

	/**	 
	 * Set the colPanel
	 * @param colPanel the colPanel to set
	 */
	protected void setColPanel(JPanel colPanel) {
		this.colPanel = colPanel;
	}

	/**	 
	 * Get the rowPanel
	 * @return the rowPanel
	 */
	protected JPanel getRowPanel() {
		return rowPanel;
	}

	/**	 
	 * Set the rowPanel
	 * @param rowPanel the rowPanel to set
	 */
	protected void setRowPanel(JPanel rowPanel) {
		this.rowPanel = rowPanel;
	}

	/**	 
	 * Get the newMenuItem
	 * @return the newMenuItem
	 */
	protected JMenuItem getNewMenuItem() {
		return newMenuItem;
	}

	/**	 
	 * Set the newMenuItem
	 * @param newMenuItem the newMenuItem to set
	 */
	protected void setNewMenuItem(JMenuItem newMenuItem) {
		this.newMenuItem = newMenuItem;
	}

	/**	 
	 * Get the solutionMenuItem
	 * @return the solutionMenuItem
	 */
	protected JMenuItem getSolutionMenuItem() {
		return solutionMenuItem;
	}

	/**	 
	 * Set the solutionMenuItem
	 * @param solutionMenuItem the solutionMenuItem to set
	 */
	protected void setSolutionMenuItem(JMenuItem solutionMenuItem) {
		this.solutionMenuItem = solutionMenuItem;
	}

	/**	 
	 * Get the exitMenuItem
	 * @return the exitMenuItem
	 */
	protected JMenuItem getExitMenuItem() {
		return exitMenuItem;
	}

	/**	 
	 * Set the exitMenuItem
	 * @param exitMenuItem the exitMenuItem to set
	 */
	protected void setExitMenuItem(JMenuItem exitMenuItem) {
		this.exitMenuItem = exitMenuItem;
	}

	/**	 
	 * Get the backgroundColour
	 * @return the backgroundColour
	 */
	protected JMenuItem getBackgroundColour() {
		return backgroundColour;
	}

	/**	 
	 * Set the backgroundColour
	 * @param backgroundColour the backgroundColour to set
	 */
	protected void setBackgroundColour(JMenuItem backgroundColour) {
		this.backgroundColour = backgroundColour;
	}

	/**	 
	 * Get the textColour
	 * @return the textColour
	 */
	protected JMenuItem getTextColour() {
		return textColour;
	}

	/**	 
	 * Set the textColour
	 * @param textColour the textColour to set
	 */
	protected void setTextColour(JMenuItem textColour) {
		this.textColour = textColour;
	}

	/**	 
	 * Get the componentColour
	 * @return the componentColour
	 */
	protected JMenuItem getComponentColour() {
		return componentColour;
	}

	/**	 
	 * Set the componentColour
	 * @param componentColour the componentColour to set
	 */
	protected void setComponentColour(JMenuItem componentColour) {
		this.componentColour = componentColour;
	}

	/**	 
	 * Get the aboutMenu
	 * @return the aboutMenu
	 */
	protected JMenuItem getAboutMenu() {
		return aboutMenu;
	}

	/**	 
	 * Set the aboutMenu
	 * @param aboutMenu the aboutMenu to set
	 */
	protected void setAboutMenu(JMenuItem aboutMenu) {
		this.aboutMenu = aboutMenu;
	}

	/**	 
	 * Get the gameMenu
	 * @return the gameMenu
	 */
	protected JMenuItem getGameMenu() {
		return gameMenu;
	}

	/**	 
	 * Set the gameMenu
	 * @param gameMenu the gameMenu to set
	 */
	protected void setGameMenu(JMenuItem gameMenu) {
		this.gameMenu = gameMenu;
	}

	/**	 
	 * Get the newMenuOption
	 * @return the newMenuOption
	 */
	protected JMenuItem getNewMenuOption() {
		return newMenuOption;
	}

	/**	 
	 * Set the newMenuOption
	 * @param newMenuOption the newMenuOption to set
	 */
	protected void setNewMenuOption(JMenuItem newMenuOption) {
		this.newMenuOption = newMenuOption;
	}

	/**	 
	 * Get the exitMenuOption
	 * @return the exitMenuOption
	 */
	protected JMenuItem getExitMenuOption() {
		return exitMenuOption;
	}

	/**	 
	 * Set the exitMenuOption
	 * @param exitMenuOption the exitMenuOption to set
	 */
	protected void setExitMenuOption(JMenuItem exitMenuOption) {
		this.exitMenuOption = exitMenuOption;
	}

	/**	 
	 * Get the resetMenuOption
	 * @return the resetMenuOption
	 */
	protected JMenuItem getResetMenuOption() {
		return resetMenuOption;
	}

	/**	 
	 * Set the resetMenuOption
	 * @param resetMenuOption the resetMenuOption to set
	 */
	protected void setResetMenuOption(JMenuItem resetMenuOption) {
		this.resetMenuOption = resetMenuOption;
	}

	/**	 
	 * Get the solveMenuOption
	 * @return the solveMenuOption
	 */
	protected JMenuItem getSolveMenuOption() {
		return solveMenuOption;
	}

	/**	 
	 * Set the solveMenuOption
	 * @param solveMenuOption the solveMenuOption to set
	 */
	protected void setSolveMenuOption(JMenuItem solveMenuOption) {
		this.solveMenuOption = solveMenuOption;
	}

	/**	 
	 * Get the toLauncherMenuOption
	 * @return the toLauncherMenuOption
	 */
	protected JMenuItem getToLauncherMenuOption() {
		return toLauncherMenuOption;
	}

	/**	 
	 * Set the toLauncherMenuOption
	 * @param toLauncherMenuOption the toLauncherMenuOption to set
	 */
	protected void setToLauncherMenuOption(JMenuItem toLauncherMenuOption) {
		this.toLauncherMenuOption = toLauncherMenuOption;
	}

	/**	 
	 * Get the saveMenuOption
	 * @return the saveMenuOption
	 */
	protected JMenuItem getSaveMenuOption() {
		return saveMenuOption;
	}

	/**	 
	 * Set the saveMenuOption
	 * @param saveMenuOption the saveMenuOption to set
	 */
	protected void setSaveMenuOption(JMenuItem saveMenuOption) {
		this.saveMenuOption = saveMenuOption;
	}

	/**	 
	 * Get the loadMenuOption
	 * @return the loadMenuOption
	 */
	protected JMenuItem getLoadMenuOption() {
		return loadMenuOption;
	}

	/**	 
	 * Set the loadMenuOption
	 * @param loadMenuOption the loadMenuOption to set
	 */
	protected void setLoadMenuOption(JMenuItem loadMenuOption) {
		this.loadMenuOption = loadMenuOption;
	}

	/**	 
	 * Get the aboutMenuOption
	 * @return the aboutMenuOption
	 */
	protected JMenuItem getAboutMenuOption() {
		return aboutMenuOption;
	}

	/**	 
	 * Set the aboutMenuOption
	 * @param aboutMenuOption the aboutMenuOption to set
	 */
	protected void setAboutMenuOption(JMenuItem aboutMenuOption) {
		this.aboutMenuOption = aboutMenuOption;
	}

	/**	 
	 * Get the fiveFive
	 * @return the fiveFive
	 */
	protected JMenuItem getFiveFive() {
		return fiveFive;
	}

	/**	 
	 * Set the fiveFive
	 * @param fiveFive the fiveFive to set
	 */
	protected void setFiveFive(JMenuItem fiveFive) {
		this.fiveFive = fiveFive;
	}

	/**	 
	 * Get the sixSix
	 * @return the sixSix
	 */
	protected JMenuItem getSixSix() {
		return sixSix;
	}

	/**	 
	 * Set the sixSix
	 * @param sixSix the sixSix to set
	 */
	protected void setSixSix(JMenuItem sixSix) {
		this.sixSix = sixSix;
	}

	/**	 
	 * Get the sevSev
	 * @return the sevSev
	 */
	protected JMenuItem getSevSev() {
		return sevSev;
	}

	/**	 
	 * Set the sevSev
	 * @param sevSev the sevSev to set
	 */
	protected void setSevSev(JMenuItem sevSev) {
		this.sevSev = sevSev;
	}
	/**
	 * @return the clientMenuOption
	 */
	protected JMenuItem getClientMenuOption() {
		return clientMenuOption;
	}

	/**
	 * @param clientMenuOption the clientMenuOption to set
	 */
	protected void setClientMenuOption(JMenuItem clientMenuOption) {
		this.clientMenuOption = clientMenuOption;
	}

	/**
	 * @return the serverMenuOption
	 */
	protected JMenuItem getServerMenuOption() {
		return serverMenuOption;
	}

	/**
	 * @param serverMenuOption the serverMenuOption to set
	 */
	protected void setServerMenuOption(JMenuItem serverMenuOption) {
		this.serverMenuOption = serverMenuOption;
	}
	
}
