package game;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 * This class is used to handle the GUI processing of the MVC
 * 
 * @author Skylar Phanenhour and Ahnaf Kamal
 *
 */
public class GameView {

	private JFrame designWindow, startWindow, splashWindow, instructionsWindow, gameCompleteWindow, picrossWindow;
	//Buttons for the splash screen
	private JButton playButton, designButton, playToLauncher;
	//Buttons to return back to previous JFrames
	private JButton designBack, instructionsBack;
	//Buttons for the play frame, some also used in design
	private JButton resetButton, solveButton, newBoardButton, instructionsButton;
	//Buttons for the design and play grids
	private JButton[][] buttonsPlay, buttonsDesign;
	private JButton newGridButton;
	//Buttons for the game completed window
	private JButton gameCompleteSave = new JButton();
	private JButton gameCompleteClose = new JButton();

	//JTextField Declarations
	//Text field for the score and timer in play
	private JTextField scoreCounter, timerCounter;
	//Text field for the name, best time, and best score when the game is completed
	private JTextField nameTextField, bestTimeTextField, bestScoreTextField;

	//JPanel Declarations
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
	private JPanel designMenuReturnPanel;

	//JMenu Declarations
	
	private JMenu gameMenuItemsContainer;
	private JMenu helpMenuItemsContainer;
	private JMenu colourMenu;
	private JMenu gridSizeItemsContainer;
	
	//JMenuItem Declarations
	
	//Menu items for the game menu
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
	private JMenuItem fiveFive = new JMenuItem("5x5");
	private JMenuItem sixSix = new JMenuItem("6x6");
	private JMenuItem sevSev = new JMenuItem("7x7");
	
	//Menu items for the help menu
	private JMenuItem aboutMenu;
	private JMenuItem backgroundColour, componentColour, textColour;


	//JLabel Declarations
	private JLabel timerLabel;
	private JLabel scoreLabel;
	private JLabel userNameLabel = new JLabel();
	private JLabel bestTimeLabel = new JLabel();
	private JLabel bestScoreLabel = new JLabel();
	private JLabel gridSizeLabel;
	private JLabel langLabel;	
	

	//Colour Declarations
	
	/** Colour for when the tile is clicked correctly*/
	protected Color tile_color = new Color(17, 15, 15);
	/** Colour for when the tile is marked*/
	protected Color mark_color = new Color(226, 222, 222);
	/** Colour for when the tile is clicked incorrectly*/
	protected Color err_color = Color.red;

	//JRadioButton Declarations
	
	private JRadioButton engRadio;
	private JRadioButton frRadio;
	
	//String Declarations
	
	private String[] viewRows;
	private String[] viewCols;
	private String[] viewRowLabels= {"0","0","0","0","0"};
	private String[] viewColLabels={"0","0","0","0","0"};
	

	//Other Declarations
	
	/** Text area to display the input history */
	protected JTextArea history;
	private JComboBox<String> gridSizeCmbo;
	private JCheckBox markCheckBox;
	private int gameMode = 0;
	private int gridSize = 5;

	
	/**
	 ************************************************************************
	 * Default constructor for the Game class * This is where all of the GUI of the
	 * game gets handled. *
	 ************************************************************************
	 */
	protected GameView() {
		
	}
	
	/**
	 * This method is to make the splash screen JFrame and all its components
	 * 
	 * @param currentLocale - Used to get the selected language. 
	 * @param langText - Used to get the text from the language file. 
	 */
	protected void splashScreen(Locale currentLocale, ResourceBundle langText) {
		
		splashWindow = new JFrame();
		JPanel splashPanel = new JPanel();
		ImageIcon splashLogo = new ImageIcon(getClass().getResource("/images/picross.jpg"));
		JLabel splashLabel = new JLabel();
		splashLabel.setIcon(splashLogo);
		splashPanel.setPreferredSize(new Dimension(500, 125));
		splashPanel.add(splashLabel);
		splashWindow.add(splashPanel);
		splashWindow.pack();
		splashWindow.setVisible(true);
		splashWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		splashWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		splashWindow.setResizable(false);
		splashWindow.setSize(400, 165);
		splashWindow.setLocationRelativeTo(null);
		
		try {
			Thread.sleep(1000);
			splashWindow.dispose();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		launcher(langText, currentLocale);
	}
	
	/**
	 * This method is used to make the menu bar and all its sub-menus
	 * 
	 * @param window
	 * @param currentLocale - Used to get the selected language. 
	 * @param langText - Used to get the text from the language file. 
	 * @param gameMode
	 */
	protected void makeMenuBar(JFrame window, Locale currentLocale, ResourceBundle langText, int gameMode) {
		JMenuBar menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);
		gameMenuItemsContainer = new JMenu(langText.getString("game"));
		newMenuOption = new JMenuItem(langText.getString("newBoard"), new ImageIcon(getClass().getResource("/images/piciconnew.gif")));
		exitMenuOption = new JMenuItem("Exit To Desktop", new ImageIcon(getClass().getResource("/images/piciconext.gif")));
		resetMenuOption = new JMenuItem("Reset Board", new ImageIcon(getClass().getResource("/images/reset.jpg")));
		solveMenuOption = new JMenuItem("Solve Puzzle", new ImageIcon(getClass().getResource("/images/solve.jpg")));
		toLauncherMenuOption = new JMenuItem("To Launcher", new ImageIcon(getClass().getResource("/images/toLauncher.jpg")));
		saveMenuOption = new JMenuItem(langText.getString("save"), new ImageIcon(getClass().getResource("/images/save.jpg")));
		loadMenuOption = new JMenuItem(langText.getString("load"), new ImageIcon(getClass().getResource("/images/load.jpg")));
		gridSizeItemsContainer = new JMenu(langText.getString("gridSize"));
		gridSizeItemsContainer.setIcon(new ImageIcon(getClass().getResource("/images/gridSize.jpg")));
		gridSizeItemsContainer.add(fiveFive);
		gridSizeItemsContainer.add(sixSix);
		gridSizeItemsContainer.add(sevSev);

		gameMenuItemsContainer.add(saveMenuOption);
		gameMenuItemsContainer.add(loadMenuOption);
		gameMenuItemsContainer.addSeparator();
		if (gameMode == 0) {
			gameMenuItemsContainer.add(resetMenuOption);
			gameMenuItemsContainer.add(gridSizeItemsContainer);
			gameMenuItemsContainer.addSeparator();			
		}
		else {
			gameMenuItemsContainer.add(newMenuOption);
			gameMenuItemsContainer.add(resetMenuOption);
			gameMenuItemsContainer.add(gridSizeItemsContainer);
			gameMenuItemsContainer.addSeparator();
			gameMenuItemsContainer.add(solveMenuOption);
			gameMenuItemsContainer.addSeparator();			
		}
		
		gameMenuItemsContainer.add(toLauncherMenuOption);
		gameMenuItemsContainer.add(exitMenuOption);

		helpMenuItemsContainer = new JMenu(langText.getString("help"));
		colourMenu = new JMenu(langText.getString("colours")); 	
		aboutMenuOption = new JMenuItem(langText.getString("about"), new ImageIcon(getClass().getResource("/images/instructions.jpg")));
		colourMenu.setIcon(new ImageIcon(getClass().getResource("/images/piciconcol.gif")));
		backgroundColour = new JMenuItem("Background Colour");
		textColour = new JMenuItem("Text Colour");
		componentColour = new JMenuItem("Component Colour");
		colourMenu.add(backgroundColour);
		colourMenu.add(textColour);
		colourMenu.add(componentColour);
		
		helpMenuItemsContainer.add(aboutMenuOption);
		helpMenuItemsContainer.add(colourMenu);
		
		menuBar.add(gameMenuItemsContainer);
		menuBar.add(helpMenuItemsContainer);

	}
	
	/**
	 * 
	 * @param currentLocale - Used to get the selected language. 
	 * @param langText - Used to get the text from the language file. 
	 */
	protected void startLauncher(Locale currentLocale, ResourceBundle langText) {
		splashScreen(currentLocale, langText);
	}

	
	private JPanel makeLanguagePanel(Locale currentLocale, ResourceBundle langText) {
		languagePanel = new JPanel();
		languageButtonPanel = new JPanel();
		////////////////////////////////////////////////////////////////
		if (currentLocale.getCountry() == "US") {
			engRadio = new JRadioButton(langText.getString("english"), true);
			frRadio = new JRadioButton(langText.getString("french"));
		}
		else {
			engRadio = new JRadioButton(langText.getString("english"));
			frRadio = new JRadioButton(langText.getString("french"), true);
		}
		ButtonGroup langButtons = new ButtonGroup();
		langButtons.add(engRadio);
		langButtons.add(frRadio);
		langLabel = new JLabel(langText.getString("languages"));
		// Panel to align the language radio buttons
		languageButtonPanel.setLayout(new BoxLayout(languageButtonPanel, BoxLayout.Y_AXIS));
		// Adds the radio buttons to the panel
		languageButtonPanel.add(engRadio);
		languageButtonPanel.add(frRadio);
		// Main language panel that stores/aligns all of the labels/radio buttons
		languagePanel.add(langLabel);
		languagePanel.add(languageButtonPanel);
		
		return languagePanel;
	}


	private JPanel makeGridSizeCombo(ResourceBundle langText) {
		
		String options[] = { "5x5", "6x6", "7x7" };
		gridSizeCmbo = new JComboBox<>(options);
		gridSizeCmbo.setBackground(Color.WHITE);
		gridSizeComboPanel = new JPanel();
		gridSizeLabel = new JLabel(langText.getString("gridSize"));
		gridSizeComboPanel.add(gridSizeLabel);
		gridSizeComboPanel.add(gridSizeCmbo);
		gridSizeComboPanel.setPreferredSize(new Dimension(200, 30));
		return gridSizeComboPanel;
	}


	private JPanel makeLeftPanel(Locale currentLocale, ResourceBundle langText, int gameMode) {
		
		scoreLabel = new JLabel(langText.getString("score"));
		timerLabel = new JLabel(langText.getString("timer"));
		
		resetButton = new JButton(langText.getString("reset"));
		resetButton.setBackground(Color.WHITE);
		solveButton = new JButton(langText.getString("solve"));
		solveButton.setBackground(Color.WHITE);
		newBoardButton = new JButton(langText.getString("newBoard"));
		newBoardButton.setBackground(Color.WHITE);
		instructionsButton = new JButton(langText.getString("instructions"));
		instructionsButton.setBackground(Color.WHITE);
		playToLauncher = new JButton(langText.getString("back"));
		playToLauncher.setBackground(Color.WHITE);
		
		leftPanel = new JPanel();
		scorePanel = new JPanel();
		timerPanel = new JPanel();
		buttonPanel = new JPanel();
		
		scoreCounter = new JTextField("0");
		scoreCounter.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		scoreCounter.setPreferredSize(new Dimension(100, 25));
		scoreCounter.setEditable(false);
		scorePanel.add(scoreLabel);
		scorePanel.add(scoreCounter);
		
		timerCounter = new JTextField("00:00");
		timerCounter.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		timerCounter.setPreferredSize(new Dimension(100, 25));
		timerCounter.setEditable(false);
		timerPanel.add(timerLabel);
		timerPanel.add(timerCounter);
		
		configurationPanel = new JPanel();
		configurationPanel.setLayout(new GridLayout(1, 2));
		configurationPanel.add(makeLanguagePanel(currentLocale, langText));
		configurationPanel.setPreferredSize(new Dimension(225, 100));
		// Vertically aligns the buttons in the panel
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		// Sets all of the buttons to the same size and creates an ActionListener
		resetButton.setPreferredSize(new Dimension(120, 25));
		solveButton.setPreferredSize(new Dimension(120, 25));
		newBoardButton.setPreferredSize(new Dimension(120, 25));
		instructionsButton.setPreferredSize(new Dimension(120, 25));
		playToLauncher.setPreferredSize(new Dimension(120, 25));
		
		if (gameMode == 1) {
			leftPanel.add(scorePanel);
			leftPanel.add(timerPanel);
			buttonPanel.add(newBoardButton);
			buttonPanel.add(solveButton);
			buttonPanel.setPreferredSize(new Dimension(120, 180));
		}
		else {
			buttonPanel.setPreferredSize(new Dimension(120, 120));
		}
		buttonPanel.add(resetButton);
		buttonPanel.add(instructionsButton);
		buttonPanel.add(playToLauncher);
		leftPanel.add(makeGridSizeCombo(langText));
		leftPanel.add(buttonPanel);
		leftPanel.add(configurationPanel);
		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, (new Color(17, 15, 15))));
		leftPanel.setPreferredSize(new Dimension(250, 200));
		return leftPanel;
	}


	private JPanel makeTitlePanel() {
		JPanel titlePanel = new JPanel();
		ImageIcon picrossLogo = new ImageIcon(getClass().getResource("/images/picross.jpg"));
		JLabel picrossLabel = new JLabel();
		picrossLabel.setIcon(picrossLogo);
		titlePanel.setPreferredSize(new Dimension(1000, 125));
		titlePanel.add(picrossLabel);
		return titlePanel;
	}


	private JPanel makeHistoryPanel() {
		// This is the history text area where all of the user inputs will be recorded
		historyPanel = new JPanel();
		history = new JTextArea();
		history.setLineWrap(true);
		history.setWrapStyleWord(true);
		history.setPreferredSize(new Dimension(200, 10000)); // important
		history.setBorder(new LineBorder(new Color(17, 15, 15)));
		history.setEditable(false);
		JScrollPane scroll = new JScrollPane(history);
		scroll.setPreferredSize(new Dimension(200, 300));
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		historyPanel.add(scroll);
		return historyPanel;
	}

	private JPanel makeControlPanel() {
		controlPanel = new JPanel();
		controlPanel.setPreferredSize(new Dimension(250, 200));
		controlPanel.add(makeHistoryPanel());
		return controlPanel;
	}

	
	/**
	 * 
	 * @param langText - Used to get the text from the language file. 
	 * @param gridSize
	 * @param markMode
	 * @return
	 */
	protected JPanel makeBoardPanel(ResourceBundle langText, int gridSize, boolean markMode) {
		System.out.println("making board panel");
		boardPanel = new JPanel();
		rowPanel = new JPanel();
		rowPanel.setLayout(new GridLayout(gridSize, 1));
		rowPanel.setPreferredSize(new Dimension(75, 0));
		System.out.println("GridSize = " + gridSize);
		System.out.println("viewRowLabels size = " + viewRowLabels.length);

		for(int i = 0; i< gridSize; i++) {
			JLabel rowLabel = new JLabel(viewRowLabels[i],SwingConstants.CENTER);
			rowPanel.add(rowLabel);
		}
		markCheckBox = new JCheckBox(langText.getString("mark"));
		if (markMode == true) {
			markCheckBox.setSelected(true);
		}
		markCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		boardPanel.setLayout(new BorderLayout());
	
		colPanel = new JPanel();
		colPanel.add(markCheckBox);
		colPanel.setLayout(new GridLayout(1, gridSize + 1));
		colPanel.setPreferredSize(new Dimension(0, 75));
		colPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(17, 15, 15)));
		System.out.println("GridSize = " + gridSize);
		System.out.println("viewColLabels size = " + viewColLabels.length);
		for(int i = 0; i< gridSize; i++) {
			JLabel colLabel = new JLabel(viewColLabels[i], SwingConstants.CENTER);
			colPanel.add(colLabel);
		}
		// Adds the components to the board panel
		boardPanel.add(colPanel, BorderLayout.NORTH);
		boardPanel.add(rowPanel, BorderLayout.WEST);

		JPanel gridButtonPanel = new JPanel();
		gridButtonPanel.setLayout(new GridLayout(gridSize, gridSize));
		buttonsPlay = new JButton[gridSize][gridSize];

		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				newGridButton = new JButton();
				newGridButton.setBackground(Color.WHITE);
				buttonsPlay[i][j] = newGridButton;
				gridButtonPanel.add(buttonsPlay[i][j]);
			}
		}

		boardPanel.add(gridButtonPanel, BorderLayout.CENTER);
		return boardPanel;
	}

	protected void updateDesignRow(String newThing, int index) {
		JLabel newLabel = new JLabel(newThing, SwingConstants.CENTER);
		rowPanel.remove(index);
		rowPanel.invalidate();
		rowPanel.add(newLabel, index);
		rowPanel.revalidate();
	}
	
	protected void updateDesignCol(String newThing, int index) {
		
		JLabel newLabel = new JLabel(newThing, SwingConstants.CENTER);
		colPanel.remove(index+1);
		colPanel.invalidate();
		colPanel.add(newLabel, index+1);
		colPanel.revalidate();
	}

	/**
	 * 
	 * @param currentLocale - Used to get the selected language. 
	 * @param langText - Used to get the text from the language file. 
	 */
	protected void updateText(Locale currentLocale, ResourceBundle langText) {

		// This is only entered in the main game
		if (gameMode == 1) {
			timerLabel.setText((langText.getString("timer")));
			scoreLabel.setText(langText.getString("score"));
			userNameLabel.setText(langText.getString("user_name"));
			bestTimeLabel.setText(langText.getString("best_time"));
			bestScoreLabel.setText(langText.getString("best_score"));
			gameCompleteSave.setText(langText.getString("save"));
			gameCompleteClose.setText(langText.getString("back"));
		}

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
	 * 
	 * @param currentLocale - Used to get the selected language. 
	 * @param langText - Used to get the text from the language file. 
	 */
	protected void launcher(ResourceBundle langText, Locale currentLocale) {

		startWindow = new JFrame();
		startWindow.setLayout(new BorderLayout());
		////////////////////////////////////////////////////////////////
		JPanel titlePanel = new JPanel();
		ImageIcon titleLogo = new ImageIcon(getClass().getResource("/images/picross.jpg"));
		JLabel titleLabel = new JLabel();
		titleLabel.setIcon(titleLogo);
		titlePanel.setPreferredSize(new Dimension(500, 125));
		titlePanel.add(titleLabel);
		////////////////////////////////////////////////////////////////
		JPanel startPanel = new JPanel();
		startPanel.setBackground(new Color(17,15,15));
		startPanel.setPreferredSize(new Dimension(100,100));
		////////////////////////////////////////////////////////////////
		designButton = new JButton(langText.getString("design"));
		designButton.setPreferredSize(new Dimension(100, 30));
		designButton.setBackground(Color.WHITE);
		////////////////////////////////////////////////////////////////
		playButton = new JButton(langText.getString("play"));
		playButton.setPreferredSize(new Dimension(100, 30));
		playButton.setBackground(Color.WHITE);

		startPanel.add(designButton);
		startPanel.add(playButton);
		startWindow.add(titlePanel, BorderLayout.NORTH);
		startWindow.add(startPanel,BorderLayout.CENTER);
		startWindow.add(makeLanguagePanel(currentLocale, langText), BorderLayout.SOUTH);
		startWindow.pack();
		startWindow.setVisible(true);
		startWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startWindow.setResizable(false);
		startWindow.setSize(400, 290);
		startWindow.setLocationRelativeTo(null);
	}


	/**
	 * 
	 * @param currentLocale - Used to get the selected language. 
	 * @param langText - Used to get the text from the language file. 
	 */
	protected void Design(Locale currentLocale, ResourceBundle langText) {
				
		designBack = new JButton("Back");
		designWindow = new JFrame();
		designWindow.setLayout(new BorderLayout());
		makeMenuBar(designWindow, currentLocale, langText, 0);
		////////////////////////////////////////////////////////////////
		designWindow.add(makeTitlePanel(), BorderLayout.NORTH);
		designWindow.add(makeBoardPanel(langText, gridSize, false), BorderLayout.CENTER); // mark mode false as default
		designWindow.add(makeLeftPanel(currentLocale, langText, 0), BorderLayout.WEST); // 0 for design
		designWindow.add(makeControlPanel(),BorderLayout.EAST);
		designWindow.pack();
		designWindow.setResizable(false);
		designWindow.setVisible(true);
		designWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		designWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		designWindow.setSize(1000, 600);
		designWindow.setLocationRelativeTo(null);
	}


	/**
	 * 
	 * @param currentLocale - Used to get the selected language. 
	 * @param langText - Used to get the text from the language file. 
	 */
	protected void Play(Locale currentlocale, ResourceBundle langText) {
		gameMode = 1;
		picrossWindow = new JFrame();
		picrossWindow.setLayout(new BorderLayout());
		makeMenuBar(picrossWindow, currentlocale, langText, 1);
		picrossWindow.add(makeTitlePanel(), BorderLayout.NORTH);
		picrossWindow.add(makeLeftPanel(currentlocale, langText, 1), BorderLayout.WEST); // 1 for play mode 
		picrossWindow.add(makeControlPanel(), BorderLayout.EAST);
		picrossWindow.add(makeBoardPanel(langText,gridSize, false), BorderLayout.CENTER); // mark mode false as default
		picrossWindow.pack();
		////////////////////////////////////////////////////////////////
		picrossWindow.setResizable(false);
		picrossWindow.setVisible(true);
		picrossWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		picrossWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		picrossWindow.setSize(1000, 600);
		picrossWindow.setLocationRelativeTo(null);
	}

	/**
	 * 
	 * @param currentLocale - Used to get the selected language. 
	 * @param langText - Used to get the text from the language file. 
	 * @param bestScore
	 * @param bestTime
	 */
	protected void gameCompleted(Locale currentlocale, ResourceBundle langText, int bestScore, int bestTime) {
		gameCompleteWindow = new JFrame();
		gameCompleteWindow.setLayout(new BorderLayout());

		JPanel titlePanel = new JPanel();
		ImageIcon titleLogo = new ImageIcon(getClass().getResource("/images/picross.jpg"));
		JLabel titleLabel = new JLabel();
		titleLabel.setIcon(titleLogo);
		titlePanel.setPreferredSize(new Dimension(525, 125));
		titlePanel.add(titleLabel);
		
		JPanel namePanel = new JPanel();
		namePanel.setPreferredSize(new Dimension(275, 35));

		userNameLabel = new JLabel(langText.getString("user_name"), SwingConstants.CENTER);
		userNameLabel.setPreferredSize(new Dimension(110, 25));
		nameTextField = new JTextField();
		nameTextField.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		nameTextField.setPreferredSize(new Dimension(125, 25));
		
		namePanel.add(userNameLabel);
		namePanel.add(nameTextField);
		
		JPanel bestScorePanel = new JPanel();
		bestScorePanel.setPreferredSize(new Dimension(275, 35));
		bestScoreLabel = new JLabel(langText.getString("best_score"), SwingConstants.CENTER);
		bestScoreLabel.setPreferredSize(new Dimension(110, 25));
		bestScoreTextField = new JTextField();
		bestScoreTextField.setText(Integer.toString(bestScore));
		bestScoreTextField.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		bestScoreTextField.setBackground(Color.WHITE);
		bestScoreTextField.setPreferredSize(new Dimension(125, 25));
		bestScoreTextField.setEditable(false);
		
		bestScorePanel.add(bestScoreLabel);
		bestScorePanel.add(bestScoreTextField);
		
		JPanel bestTimePanel = new JPanel();
		bestTimePanel.setPreferredSize(new Dimension(275, 35));

		bestTimeLabel = new JLabel(langText.getString("best_time"), SwingConstants.CENTER);
		bestTimeLabel.setPreferredSize(new Dimension(110, 25));
		bestTimeTextField = new JTextField();
		bestTimeTextField.setText(Integer.toString(bestTime) + " Seconds");
		bestTimeTextField.setBorder(new LineBorder((new Color(17, 15, 15)), 1));
		bestTimeTextField.setBackground(Color.WHITE);
		bestTimeTextField.setPreferredSize(new Dimension(125, 25));
		bestTimeTextField.setEditable(false);
		
		bestTimePanel.add(bestTimeLabel);
		bestTimePanel.add(bestTimeTextField);
		
		JPanel statsPanel = new JPanel();
		statsPanel.setPreferredSize(new Dimension(300, 200));
		statsPanel.add(namePanel);
		statsPanel.add(bestScorePanel);
		statsPanel.add(bestTimePanel);

		gameCompleteSave.setText(langText.getString("save"));
		gameCompleteSave.setPreferredSize(new Dimension(100, 30));
		gameCompleteSave.setBackground(Color.WHITE);
		
		gameCompleteClose.setText(langText.getString("back"));
		gameCompleteClose.setPreferredSize(new Dimension(100, 30));
		gameCompleteClose.setBackground(Color.WHITE);
		
		JPanel gameOverRightPanel = new JPanel();
		gameOverRightPanel.setPreferredSize(new Dimension(210, 200));
		
		JPanel gameOverButtonPanel = new JPanel();
		gameOverButtonPanel.setPreferredSize(new Dimension(100, 100));
		gameOverButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		gameOverButtonPanel.add(gameCompleteSave);
		gameOverButtonPanel.add(gameCompleteClose);
		gameOverRightPanel.add(gameOverButtonPanel);
		
		gameCompleteWindow.add(titlePanel, BorderLayout.NORTH);
		gameCompleteWindow.add(statsPanel, BorderLayout.WEST);
		gameCompleteWindow.add(gameOverRightPanel, BorderLayout.EAST);
		gameCompleteWindow.setTitle("Game Complete - Skylar Phanenhour, Ahnaf Kamal");
		gameCompleteWindow.setResizable(false);
		gameCompleteWindow.setSize(525, 300);
		gameCompleteWindow.setVisible(true);
		gameCompleteWindow.setLocationRelativeTo(null);
		
	}

	/**
	 * 
	 * @param currentLocale - Used to get the selected language. 
	 * @param langText - Used to get the text from the language file. 
	 */
	protected void Instructions(Locale currentLocale, ResourceBundle langText) {
		instructionsWindow = new JFrame();
		JPanel instructionsPanel = new JPanel();
		instructionsPanel.setPreferredSize(new Dimension(500, 400));
		JTextArea instructionsLabel = new JTextArea();
		instructionsLabel.setPreferredSize(new Dimension (450, 325));
		instructionsBack = new JButton("Back");
		
		instructionsLabel.setText(langText.getString("instructions_text"));
		instructionsLabel.setLineWrap(true);
		instructionsLabel.setEditable(false);
		instructionsLabel.setOpaque(false);
		instructionsLabel.setFont(new Font("Calibri Regular", Font.PLAIN, 16));

		instructionsBack.setFont(new Font("Calibri Regular", Font.BOLD, 12));

		instructionsPanel.add(instructionsLabel);
		instructionsPanel.add(instructionsBack);
		instructionsWindow.add(instructionsPanel);
		instructionsWindow.setTitle("Instructions - Skylar Phanenhour, Ahnaf Kamal");
		instructionsWindow.setResizable(false);
		instructionsWindow.setSize(525, 425);
		instructionsWindow.setVisible(true);
		instructionsWindow.setLocationRelativeTo(null);
	}
	
	protected void solveBoard(int gridSize) {
		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				if (viewRows[i].charAt(j) == '1') {
					buttonsPlay[i][j].setBackground(Color.black);
					buttonsPlay[i][j].setEnabled(false);
				}
			}
		}
	}
	
	protected void resetBoard() {
		 for (JButton[] i : buttonsPlay) {
	            for (JButton j : i) {
	                j.setBackground(Color.WHITE);
	                j.setEnabled(true);
	            }
	        }
	}

	protected void resetRowsAndCol() {
		for(int i =0; i< gridSize; i++) {
			viewRowLabels[i] = "0";
			viewColLabels[i] = "0";
		}
		viewRows = new String[gridSize];
		viewCols = new String[gridSize];

		//{"0","0","0","0","0"};
		
	}
	
	protected String[] getViewRows() {
		return viewRows;
	}

	protected void setViewRows(String[] viewRows) {
		this.viewRows = viewRows;
	}

	protected String[] getViewCols() {
		return viewCols;
	}

	protected void setViewCols(String[] viewCols) {
		this.viewCols = viewCols;
	}
	
	/**
	 * @return the nameTextField
	 */
	public JTextField getNameTextField() {
		return nameTextField;
	}
	
	/**
	 * @param nameTextField the nameTextField to set
	 */
	public void setNameTextField(JTextField nameTextField) {
		this.nameTextField= nameTextField;
	}
	
	/**
	 * @return the viewRowLabels
	 */
	protected String[] getViewRowLabels() {
		return viewRowLabels;
	}

	/**
	 * @param viewRowLabels the viewRowLabels to set
	 */
	protected void setViewRowLabels(String[] viewRowLabels) {
		this.viewRowLabels = viewRowLabels;
	}

	/**
	 * @return the viewColLabels
	 */
	protected String[] getViewColLabels() {
		return viewColLabels;
	}

	/**
	 * @param viewColLabels the viewColLabels to set
	 */
	protected void setViewColLabels(String[] viewColLabels) {
		this.viewColLabels = viewColLabels;
	}

	
	/**
	 * @return the gameCompleteSave
	 */
	protected JButton getGameCompleteSave() {
		return gameCompleteSave;
	}

	/**
	 * @param gameCompleteSave the gameCompleteSave to set
	 */
	protected void setGameCompleteSave(JButton gameCompleteSave) {
		this.gameCompleteSave = gameCompleteSave;
	}

	/**
	 * @return the gameCompleteClose
	 */
	protected JButton getGameCompleteClose() {
		return gameCompleteClose;
	}

	/**
	 * @param gameCompleteClose the gameCompleteClose to set
	 */
	protected void setGameCompleteClose(JButton gameCompleteClose) {
		this.gameCompleteClose = gameCompleteClose;
	}
	/**
	 * @return the instructionsButton
	 */
	protected JButton getInstructionsButton() {
		return instructionsButton;
	}

	/**
	 * @param instructionsButton the instructionsButton to set
	 */
	protected void setInstructionsButton(JButton instructionsButton) {
		this.instructionsButton = instructionsButton;
	}

	/**
	 * @return the instructionsBack
	 */
	protected JButton getInstructionsBack() {
		return instructionsBack;
	}

	/**
	 * @param instructionsBack the instructionsBack to set
	 */
	protected void setInstructionsBack(JButton instructionsBack) {
		this.instructionsBack = instructionsBack;
	}

	/**
	 * @return the designWindow
	 */
	protected JFrame getDesignWindow() {
		return designWindow;
	}

	/**
	 * @return the gameCompleteWindow
	 */
	protected JFrame getGameCompleteWindow() {
		return gameCompleteWindow;
	}

	/**
	 * @param gameCompleteWindow the gameCompleteWindow to set
	 */
	protected void setGameCompleteWindow(JFrame gameCompleteWindow) {
		this.gameCompleteWindow = gameCompleteWindow;
	}
	
	/**
	 * @param designWindow the designWindow to set
	 */
	protected void setDesignWindow(JFrame designWindow) {
		this.designWindow = designWindow;
	}

	/**
	 * @return the startWindow
	 */
	protected JFrame getStartWindow() {
		return startWindow;
	}

	/**
	 * @param startWindow the startWindow to set
	 */
	protected void setStartWindow(JFrame startWindow) {
		this.startWindow = startWindow;
	}

	/**
	 * @return the splashWindow
	 */
	protected JFrame getSplashWindow() {
		return splashWindow;
	}

	/**
	 * @param splashWindow the splashWindow to set
	 */
	protected void setSplashWindow(JFrame splashWindow) {
		this.splashWindow = splashWindow;
	}

	/**
	 * @return the instructionsWindow
	 */
	protected JFrame getInstructionsWindow() {
		return instructionsWindow;
	}

	/**
	 * @param instructionsWindow the instructionsWindow to set
	 */
	protected void setInstructionsWindow(JFrame instructionsWindow) {
		this.instructionsWindow = instructionsWindow;
	}

	/**
	 * @return the picrossWindow
	 */
	protected JFrame getPicrossWindow() {
		return picrossWindow;
	}

	/**
	 * @param picrossWindow the picrossWindow to set
	 */
	protected void setPicrossWindow(JFrame picrossWindow) {
		this.picrossWindow = picrossWindow;
	}

	/**
	 * @return the gridSizeCmbo
	 */
	protected JComboBox<String> getGridSizeCmbo() {
		return gridSizeCmbo;
	}

	/**
	 * @param gridSizeCmbo the gridSizeCmbo to set
	 */
	protected void setGridSizeCmbo(JComboBox<String> gridSizeCmbo) {
		this.gridSizeCmbo = gridSizeCmbo;
	}

	/**
	 * @return the playButton
	 */
	protected JButton getPlayButton() {
		return playButton;
	}

	/**
	 * @param playButton the playButton to set
	 */
	protected void setPlayButton(JButton playButton) {
		this.playButton = playButton;
	}

	/**
	 * @return the designButton
	 */
	protected JButton getDesignButton() {
		return designButton;
	}

	/**
	 * @param designButton the designButton to set
	 */
	protected void setDesignButton(JButton designButton) {
		this.designButton = designButton;
	}

	/**
	 * @return the designBack
	 */
	protected JButton getDesignBack() {
		return designBack;
	}

	/**
	 * @param designBack the designBack to set
	 */
	protected void setDesignBack(JButton designBack) {
		this.designBack = designBack;
	}

	/**
	 * @return the resetButton
	 */
	protected JButton getResetButton() {
		return resetButton;
	}

	/**
	 * @param resetButton the resetButton to set
	 */
	protected void setResetButton(JButton resetButton) {
		this.resetButton = resetButton;
	}

	/**
	 * @return the solveButton
	 */
	protected JButton getSolveButton() {
		return solveButton;
	}

	/**
	 * @param solveButton the solveButton to set
	 */
	protected void setSolveButton(JButton solveButton) {
		this.solveButton = solveButton;
	}

	/**
	 * @return the newBoardButton
	 */
	protected JButton getNewBoardButton() {
		return newBoardButton;
	}

	/**
	 * @param newBoardButton the newBoardButton to set
	 */
	protected void setNewBoardButton(JButton newBoardButton) {
		this.newBoardButton = newBoardButton;
	}

	/**
	 * @return the history
	 */
	protected JTextArea getHistory() {
		return history;
	}

	/**
	 * @param history the history to set
	 */
	protected void setHistory(JTextArea history) {
		this.history = history;
	}

	/**
	 * @return the buttonsPlay
	 */
	protected JButton[][] getButtons() {
		return buttonsPlay;
	}

	/**
	 * @param buttonsPlay the buttonsPlay to set
	 */
	protected void setButtons(JButton[][] buttonsPlay) {
		this.buttonsPlay = buttonsPlay;
	}

	/**
	 * @return the buttonsDesign
	 */
	protected JButton[][] getButtonsDesign() {
		return buttonsDesign;
	}

	/**
	 * @param buttonsDesign the buttonsDesign to set
	 */
	protected void setButtonsDesign(JButton[][] buttonsDesign) {
		this.buttonsDesign = buttonsDesign;
	}

	/**
	 * @return the playToLauncher
	 */
	protected JButton getPlayToLauncher() {
		return playToLauncher;
	}

	/**
	 * @param playToLauncher the playToLauncher to set
	 */
	protected void setPlayToLauncher(JButton playToLauncher) {
		this.playToLauncher = playToLauncher;
	}

	/**
	 * @return the newGridButton
	 */
	protected JButton getNewGridButton() {
		return newGridButton;
	}

	/**
	 * @param newGridButton the newGridButton to set
	 */
	protected void setNewGridButton(JButton newGridButton) {
		this.newGridButton = newGridButton;
	}

	/**
	 * @return the engRadio
	 */
	protected JRadioButton getEngRadio() {
		return engRadio;
	}

	/**
	 * @param engRadio the engRadio to set
	 */
	protected void setEngRadio(JRadioButton engRadio) {
		this.engRadio = engRadio;
	}

	/**
	 * @return the frRadio
	 */
	protected JRadioButton getFrRadio() {
		return frRadio;
	}

	/**
	 * @param frRadio the frRadio to set
	 */
	protected void setFrRadio(JRadioButton frRadio) {
		this.frRadio = frRadio;
	}

	/**
	 * @return the leftPanel
	 */
	protected JPanel getLeftPanel() {
		return leftPanel;
	}

	/**
	 * @param leftPanel the leftPanel to set
	 */
	protected void setLeftPanel(JPanel leftPanel) {
		this.leftPanel = leftPanel;
	}

	/**
	 * @return the boardPanel
	 */
	protected JPanel getBoardPanel() {
		return boardPanel;
	}

	/**
	 * @param boardPanel the boardPanel to set
	 */
	protected void setBoardPanel(JPanel boardPanel) {
		this.boardPanel = boardPanel;
	}

	/**
	 * @return the designMenuReturnPanel
	 */
	protected JPanel getDesignMenuReturnPanel() {
		return designMenuReturnPanel;
	}

	/**
	 * @param designMenuReturnPanel the designMenuReturnPanel to set
	 */
	protected void setDesignMenuReturnPanel(JPanel designMenuReturnPanel) {
		this.designMenuReturnPanel = designMenuReturnPanel;
	}

	/**
	 * @return the markCheckBox
	 */
	protected JCheckBox getMarkCheckBox() {
		return markCheckBox;
	}

	/**
	 * @param markCheckBox the markCheckBox to set
	 */
	protected void setMarkCheckBox(JCheckBox markCheckBox) {
		this.markCheckBox = markCheckBox;
	}

	/**
	 * @return the scoreCounter
	 */
	protected JTextField getScoreCounter() {
		return scoreCounter;
	}

	/**
	 * @param scoreCounter the scoreCounter to set
	 */
	protected void setScoreCounter(JTextField scoreCounter) {
		this.scoreCounter = scoreCounter;
	}

	/**
	 * @return the timerCounter
	 */
	protected JTextField getTimerCounter() {
		return timerCounter;
	}

	/**
	 * @param timerCounter the timerCounter to set
	 */
	protected void setTimerCounter(JTextField timerCounter) {
		this.timerCounter = timerCounter;
	}

	/**
	 * @return the gridSize
	 */
	protected int getGridSize() {
		return gridSize;
	}

	/**
	 * @param gridSize the gridSize to set
	 */
	protected void setGridSize(int gridSize) {
		this.gridSize = gridSize;
	}

	/**
	 * @return the gameMode
	 */
	protected int getGameMode() {
		return gameMode;
	}

	/**
	 * @param gameMode the gameMode to set
	 */
	protected void setGameMode(int gameMode) {
		this.gameMode = gameMode;
	}

	/**
	 * @return the timerLabel
	 */
	protected JLabel getTimerLabel() {
		return timerLabel;
	}

	/**
	 * @param timerLabel the timerLabel to set
	 */
	protected void setTimerLabel(JLabel timerLabel) {
		this.timerLabel = timerLabel;
	}

	/**
	 * @return the scoreLabel
	 */
	protected JLabel getScoreLabel() {
		return scoreLabel;
	}

	/**
	 * @param scoreLabel the scoreLabel to set
	 */
	protected void setScoreLabel(JLabel scoreLabel) {
		this.scoreLabel = scoreLabel;
	}

	/**
	 * @return the gridSizeLabel
	 */
	protected JLabel getGridSizeLabel() {
		return gridSizeLabel;
	}

	/**
	 * @param gridSizeLabel the gridSizeLabel to set
	 */
	protected void setGridSizeLabel(JLabel gridSizeLabel) {
		this.gridSizeLabel = gridSizeLabel;
	}

	/**
	 * @return the langLabel
	 */
	protected JLabel getLangLabel() {
		return langLabel;
	}

	/**
	 * @param langLabel the langLabel to set
	 */
	protected void setLangLabel(JLabel langLabel) {
		this.langLabel = langLabel;
	}

	/**
	 * @return the languagePanel
	 */
	protected JPanel getLanguagePanel() {
		return languagePanel;
	}

	/**
	 * @param languagePanel the languagePanel to set
	 */
	protected void setLanguagePanel(JPanel languagePanel) {
		this.languagePanel = languagePanel;
	}

	/**
	 * @return the languageButtonPanel
	 */
	protected JPanel getLanguageButtonPanel() {
		return languageButtonPanel;
	}

	/**
	 * @param languageButtonPanel the languageButtonPanel to set
	 */
	protected void setLanguageButtonPanel(JPanel languageButtonPanel) {
		this.languageButtonPanel = languageButtonPanel;
	}

	/**
	 * @return the gridSizeComboPanel
	 */
	protected JPanel getGridSizeComboPanel() {
		return gridSizeComboPanel;
	}

	/**
	 * @param gridSizeComboPanel the gridSizeComboPanel to set
	 */
	protected void setGridSizeComboPanel(JPanel gridSizeComboPanel) {
		this.gridSizeComboPanel = gridSizeComboPanel;
	}

	/**
	 * @return the scorePanel
	 */
	protected JPanel getScorePanel() {
		return scorePanel;
	}

	/**
	 * @param scorePanel the scorePanel to set
	 */
	protected void setScorePanel(JPanel scorePanel) {
		this.scorePanel = scorePanel;
	}

	/**
	 * @return the timerPanel
	 */
	protected JPanel getTimerPanel() {
		return timerPanel;
	}

	/**
	 * @param timerPanel the timerPanel to set
	 */
	protected void setTimerPanel(JPanel timerPanel) {
		this.timerPanel = timerPanel;
	}

	/**
	 * @return the buttonPanel
	 */
	protected JPanel getButtonPanel() {
		return buttonPanel;
	}

	/**
	 * @param buttonPanel the buttonPanel to set
	 */
	protected void setButtonPanel(JPanel buttonPanel) {
		this.buttonPanel = buttonPanel;
	}

	/**
	 * @return the configurationPanel
	 */
	protected JPanel getConfigurationPanel() {
		return configurationPanel;
	}

	/**
	 * @param configurationPanel the configurationPanel to set
	 */
	protected void setConfigurationPanel(JPanel configurationPanel) {
		this.configurationPanel = configurationPanel;
	}

	/**
	 * @return the historyPanel
	 */
	protected JPanel getHistoryPanel() {
		return historyPanel;
	}

	/**
	 * @param historyPanel the historyPanel to set
	 */
	protected void setHistoryPanel(JPanel historyPanel) {
		this.historyPanel = historyPanel;
	}

	/**
	 * @return the controlPanel
	 */
	protected JPanel getControlPanel() {
		return controlPanel;
	}

	/**
	 * @param controlPanel the controlPanel to set
	 */
	protected void setControlPanel(JPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	/**
	 * @return the colPanel
	 */
	protected JPanel getColPanel() {
		return colPanel;
	}

	/**
	 * @param colPanel the colPanel to set
	 */
	protected void setColPanel(JPanel colPanel) {
		this.colPanel = colPanel;
	}

	/**
	 * @return the rowPanel
	 */
	protected JPanel getRowPanel() {
		return rowPanel;
	}

	/**
	 * @param rowPanel the rowPanel to set
	 */
	protected void setRowPanel(JPanel rowPanel) {
		this.rowPanel = rowPanel;
	}

	/**
	 * @return the newMenuItem
	 */
	protected JMenuItem getNewMenuItem() {
		return newMenuItem;
	}

	/**
	 * @param newMenuItem the newMenuItem to set
	 */
	protected void setNewMenuItem(JMenuItem newMenuItem) {
		this.newMenuItem = newMenuItem;
	}

	/**
	 * @return the solutionMenuItem
	 */
	protected JMenuItem getSolutionMenuItem() {
		return solutionMenuItem;
	}

	/**
	 * @param solutionMenuItem the solutionMenuItem to set
	 */
	protected void setSolutionMenuItem(JMenuItem solutionMenuItem) {
		this.solutionMenuItem = solutionMenuItem;
	}

	/**
	 * @return the exitMenuItem
	 */
	protected JMenuItem getExitMenuItem() {
		return exitMenuItem;
	}

	/**
	 * @param exitMenuItem the exitMenuItem to set
	 */
	protected void setExitMenuItem(JMenuItem exitMenuItem) {
		this.exitMenuItem = exitMenuItem;
	}

	/**
	 * @return the backgroundColour
	 */
	protected JMenuItem getBackgroundColour() {
		return backgroundColour;
	}

	/**
	 * @param backgroundColour the backgroundColour to set
	 */
	protected void setBackgroundColour(JMenuItem backgroundColour) {
		this.backgroundColour = backgroundColour;
	}

	/**
	 * @return the textColour
	 */
	protected JMenuItem getTextColour() {
		return textColour;
	}

	/**
	 * @param textColour the textColour to set
	 */
	protected void setTextColour(JMenuItem textColour) {
		this.textColour = textColour;
	}

	/**
	 * @return the componentColour
	 */
	protected JMenuItem getComponentColour() {
		return componentColour;
	}

	/**
	 * @param componentColour the componentColour to set
	 */
	protected void setComponentColour(JMenuItem componentColour) {
		this.componentColour = componentColour;
	}

	/**
	 * @return the aboutMenu
	 */
	protected JMenuItem getAboutMenu() {
		return aboutMenu;
	}

	/**
	 * @param aboutMenu the aboutMenu to set
	 */
	protected void setAboutMenu(JMenuItem aboutMenu) {
		this.aboutMenu = aboutMenu;
	}

	/**
	 * @return the gameMenu
	 */
	protected JMenuItem getGameMenu() {
		return gameMenu;
	}

	/**
	 * @param gameMenu the gameMenu to set
	 */
	protected void setGameMenu(JMenuItem gameMenu) {
		this.gameMenu = gameMenu;
	}


	/**
	 * @return the newMenuOption
	 */
	protected JMenuItem getNewMenuOption() {
		return newMenuOption;
	}

	/**
	 * @param newMenuOption the newMenuOption to set
	 */
	protected void setNewMenuOption(JMenuItem newMenuOption) {
		this.newMenuOption = newMenuOption;
	}

	/**
	 * @return the exitMenuOption
	 */
	protected JMenuItem getExitMenuOption() {
		return exitMenuOption;
	}

	/**
	 * @param exitMenuOption the exitMenuOption to set
	 */
	protected void setExitMenuOption(JMenuItem exitMenuOption) {
		this.exitMenuOption = exitMenuOption;
	}

	/**
	 * @return the resetMenuOption
	 */
	protected JMenuItem getResetMenuOption() {
		return resetMenuOption;
	}

	/**
	 * @param resetMenuOption the resetMenuOption to set
	 */
	protected void setResetMenuOption(JMenuItem resetMenuOption) {
		this.resetMenuOption = resetMenuOption;
	}

	/**
	 * @return the solveMenuOption
	 */
	protected JMenuItem getSolveMenuOption() {
		return solveMenuOption;
	}

	/**
	 * @param solveMenuOption the solveMenuOption to set
	 */
	protected void setSolveMenuOption(JMenuItem solveMenuOption) {
		this.solveMenuOption = solveMenuOption;
	}

	/**
	 * @return the toLauncherMenuOption
	 */
	protected JMenuItem getToLauncherMenuOption() {
		return toLauncherMenuOption;
	}

	/**
	 * @param toLauncherMenuOption the toLauncherMenuOption to set
	 */
	protected void setToLauncherMenuOption(JMenuItem toLauncherMenuOption) {
		this.toLauncherMenuOption = toLauncherMenuOption;
	}

	/**
	 * @return the saveMenuOption
	 */
	protected JMenuItem getSaveMenuOption() {
		return saveMenuOption;
	}

	/**
	 * @param saveMenuOption the saveMenuOption to set
	 */
	protected void setSaveMenuOption(JMenuItem saveMenuOption) {
		this.saveMenuOption = saveMenuOption;
	}

	/**
	 * @return the loadMenuOption
	 */
	protected JMenuItem getLoadMenuOption() {
		return loadMenuOption;
	}

	/**
	 * @param loadMenuOption the loadMenuOption to set
	 */
	protected void setLoadMenuOption(JMenuItem loadMenuOption) {
		this.loadMenuOption = loadMenuOption;
	}


	/**
	 * @return the aboutMenuOption
	 */
	protected JMenuItem getAboutMenuOption() {
		return aboutMenuOption;
	}

	/**
	 * @param aboutMenuOption the aboutMenuOption to set
	 */
	protected void setAboutMenuOption(JMenuItem aboutMenuOption) {
		this.aboutMenuOption = aboutMenuOption;
	}

	/**
	 * @return the fiveFive
	 */
	protected JMenuItem getFiveFive() {
		return fiveFive;
	}

	/**
	 * @param fiveFive the fiveFive to set
	 */
	protected void setFiveFive(JMenuItem fiveFive) {
		this.fiveFive = fiveFive;
	}

	/**
	 * @return the sixSix
	 */
	protected JMenuItem getSixSix() {
		return sixSix;
	}

	/**
	 * @param sixSix the sixSix to set
	 */
	protected void setSixSix(JMenuItem sixSix) {
		this.sixSix = sixSix;
	}

	/**
	 * @return the sevSev
	 */
	protected JMenuItem getSevSev() {
		return sevSev;
	}

	/**
	 * @param sevSev the sevSev to set
	 */
	protected void setSevSev(JMenuItem sevSev) {
		this.sevSev = sevSev;
	}


}
