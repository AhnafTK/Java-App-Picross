package game;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.LineBorder;

// make the gui
public class GameView {
	protected JButton instructionsButton;
	/** Button to return from the instructions page to the main game */
	protected JButton instructionsBack;
	/** JFrame for the design window */
	protected JFrame designWindow;
	/** JFrame for the start window */
	protected JFrame startWindow;
	protected JFrame splashWindow;
	/** JFrame for the instructions window */
	protected JFrame instructionsWindow;
	/** JFrame for the main picross window */
	protected JFrame picrossWindow;
	/** Combo box to change the grid size */
	protected JComboBox<String> gridSizeCmbo;
	/** Button to play the game */
	protected JButton playButton;
	/** Button to open the design window */
	protected JButton designButton;
	/** Button to return from the design page to the main page */
	protected JButton designBack;
	/** Button to reset the game */
	protected JButton resetButton;
	/** Button to solve the game */
	protected JButton solveButton;
	/** Button to make a new board */
	protected JButton newBoardButton;
	/** Text area to display the input history */
	protected JTextArea history;
	/** 2d-button array for the grid */
	protected JButton[][] buttons;

	protected JButton[][] buttonsDesign;
	JButton playToLauncher;

	
	/** Radio button to change the language to English */
	protected JRadioButton engRadio;
	/** Radio button to change the language to French */
	protected JRadioButton frRadio;
	/** Left panel to hold all of the menu components */
	protected JPanel leftPanel;
	/** Board panel to hold the grid of buttons */
	protected JPanel boardPanel;
	/** Panel to hold the back button in the design window */
	protected JPanel designMenuReturnPanel;
	/** Check box for to enable the mark mode */
	protected JCheckBox markCheckBox;
	protected JTextField timerCounter;
	
	/** Local builder to change the language */
	
	//Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
	/** Resource bundle to get the language messages */
	//ResourceBundle langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);

	protected int gridSize = 5;
	/** Boolean for the mark mode, false by default */
	//protected boolean markMode = false;
	/** Int variable to check what the current game mode is, 0=design, 1=play */
	protected int gameMode = 0;
	/*
	 ********************************************************************
	 * JLabel initializations. * The text that is in the label changes depending *
	 * on what language is selected. *
	 ********************************************************************
	 */
	/** Label for the timer */
	protected JLabel timerLabel;
	//protected JLabel timerLabel = new JLabel(langText.getString("timer"));
	/** Label for the score */
	//protected JLabel scoreLabel = new JLabel(langText.getString("score"));
	protected JLabel scoreLabel;
	/** Label for the grid size */
	//protected JLabel gridSizeLabel = new JLabel(langText.getString("gridSize"));
	protected JLabel gridSizeLabel;
	/** Label for the language */
	protected JLabel langLabel = new JLabel();
	/** Label for the colour */
	protected JLabel colourLabel = new JLabel();

	protected JMenuBar menuBar;
	protected JMenuItem newMenu;
	protected JMenuItem solutionMenu;
	protected JMenuItem exitMenu;
	
	protected JMenuItem colourMenu;
	protected JMenuItem aboutMenu;
	
	protected void splashScreen(Locale currentLocale, ResourceBundle langText) {
		splashWindow = new JFrame();
		JPanel splashPanel = new JPanel();
		
		ImageIcon splashLogo = new ImageIcon("./src/images/picross.jpg");
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
		splashWindow.setSize(400, 175);
		splashWindow.setLocationRelativeTo(null);
		try {
			Thread.sleep(3000);
			splashWindow.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		launcher(langText, currentLocale);

	}
	
	private void makeMenuBar() {
		menuBar = new JMenuBar();
		picrossWindow.setJMenuBar(menuBar);
		
		////////////////////////////////////////////////////////////////

		JMenu gameMenu = new JMenu("Game");
		newMenu = new JMenuItem("New", new ImageIcon(getClass().getResource("/images/piciconnew.gif")));
		solutionMenu = new JMenuItem("Solution", new ImageIcon(getClass().getResource("/images/piciconsol.gif")));
		exitMenu = new JMenuItem("Exit", new ImageIcon(getClass().getResource("/images/piciconext.gif")));

		gameMenu.add(newMenu);
		gameMenu.add(solutionMenu);
		gameMenu.add(exitMenu);

		////////////////////////////////////////////////////////////////

		JMenu helpMenu = new JMenu("Help");
		colourMenu = new JMenuItem("Colours", new ImageIcon(getClass().getResource("/images/piciconcol.gif")));
		aboutMenu = new JMenuItem("About", new ImageIcon(getClass().getResource("/images/piciconabt.gif")));

		helpMenu.add(colourMenu);
		helpMenu.add(aboutMenu);
		
		////////////////////////////////////////////////////////////////

		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
	}

	/**
	 ************************************************************************
	 * Default constructor for the Game class * This is where all of the GUI of the
	 * game gets handled. *
	 ************************************************************************
	 */
	public GameView() {
		
	}
	
	protected void startLauncher(Locale currentLocale, ResourceBundle langText) {
		splashScreen(currentLocale, langText);
	}
	/*
	 ********************************************************************
	 * Make Language Panel * * This is where all of the language panels, labels,
	 * radio buttons * and button group are located. * * Whatever radio button is
	 * selected, will change the game to * that language. * "English" is selected by
	 * default when the game is started. *
	 ********************************************************************
	 */
	private JPanel makeLanguagePanel(Locale currentLocale, ResourceBundle langText) {
		JPanel languagePanel = new JPanel();
		JPanel languageButtonPanel = new JPanel();
		// Button group that holds the "English" and "French" radio buttons
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
		langLabel.setText(langText.getString("languages"));
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

	/*
	 ********************************************************************
	 * Make Grid Size Combo * * Grid size combo box panel. * Combo box options are
	 * initialized in a string array. * Grid size label changes depending on what
	 * language is selected. *
	 ********************************************************************
	 */
	private JPanel makeGridSizeCombo(ResourceBundle langText) {
		String options[] = { "5x5", "6x6", "7x7" };
		gridSizeCmbo = new JComboBox<>(options);
		gridSizeCmbo.setBackground(Color.WHITE);
		JPanel gridSizeComboPanel = new JPanel();
		gridSizeLabel = new JLabel(langText.getString("gridSize"));
		gridSizeComboPanel.add(gridSizeLabel);
		gridSizeComboPanel.add(gridSizeCmbo);
		gridSizeComboPanel.setPreferredSize(new Dimension(200, 30));

		return gridSizeComboPanel;
	}

	/**
	 ************************************************************************
	 * Make Left Panel. * * This is the 'top-level' panel that stores everything for
	 * the left * panel. All of the 'lower-level' components get added to this
	 * panel. * *
	 * 
	 * @param locale   - Used to get the selected language. *
	 * @param langText - Used to get the text from the language file. *
	 ************************************************************************
	 */
	private JPanel makeLeftPanel(Locale currentLocale, ResourceBundle langText, int gameMode) {
		leftPanel = new JPanel();
		scoreLabel = new JLabel(langText.getString("score"));
		timerLabel = new JLabel(langText.getString("timer"));
		resetButton = new JButton(langText.getString("reset"));
		resetButton.setBackground(Color.WHITE);
		////////////////////////////////////////////////////////////////
		solveButton = new JButton(langText.getString("solve"));
		solveButton.setBackground(Color.WHITE);
		////////////////////////////////////////////////////////////////
		newBoardButton = new JButton(langText.getString("newBoard"));
		newBoardButton.setBackground(Color.WHITE);
		////////////////////////////////////////////////////////////////
		instructionsButton = new JButton(langText.getString("instructions"));
		instructionsButton.setBackground(Color.WHITE);
		playToLauncher = new JButton(langText.getString("back"));
		playToLauncher.setBackground(Color.WHITE);
		////////////////////////////////////////////////////////////////
		JPanel scorePanel = new JPanel();
		JPanel timerPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JTextField scoreCounter = new JTextField();
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
		
		JPanel configurationPanel = new JPanel();
		configurationPanel.setLayout(new GridLayout(1, 2));
		configurationPanel.add(makeLanguagePanel(currentLocale, langText));
		configurationPanel.setPreferredSize(new Dimension(225, 100));
		// Vertically aligns the buttons in the panel
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		//buttonPanel.setPreferredSize(new Dimension(120, 180));
		// Sets all of the buttons to the same size and creates an ActionListener
		resetButton.setPreferredSize(new Dimension(120, 25));
		solveButton.setPreferredSize(new Dimension(120, 25));
		newBoardButton.setPreferredSize(new Dimension(120, 25));
		instructionsButton.setPreferredSize(new Dimension(120, 25));
		playToLauncher.setPreferredSize(new Dimension(120, 25));
		buttonPanel.add(playToLauncher);
		buttonPanel.add(resetButton);
		buttonPanel.add(instructionsButton);
		if (gameMode == 1) {
			leftPanel.add(scorePanel);
			leftPanel.add(timerPanel);
			buttonPanel.add(solveButton);
			buttonPanel.add(newBoardButton);
			buttonPanel.setPreferredSize(new Dimension(120, 180));
		}
		else {
			buttonPanel.setPreferredSize(new Dimension(120, 120));
		}
	
		
		leftPanel.add(makeGridSizeCombo(langText));
		leftPanel.add(buttonPanel);
		leftPanel.add(configurationPanel);
		leftPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, (new Color(17, 15, 15))));
		leftPanel.setPreferredSize(new Dimension(250, 200));
		return leftPanel;
	}

	/**
	 ********************************************************************
	 * Make Title Panel * * This is where the ImageIcon for the * Picross logo is
	 * created/set. *
	 ********************************************************************
	 */
	private JPanel makeTitlePanel() {
		JPanel titlePanel = new JPanel();
		ImageIcon picrossLogo = new ImageIcon("./src/images/picross.jpg");
		JLabel picrossLabel = new JLabel();
		picrossLabel.setIcon(picrossLogo);
		titlePanel.setPreferredSize(new Dimension(1000, 125));
		titlePanel.add(picrossLabel);

		return titlePanel;
	}

	/**
	 ********************************************************************
	 * Make History Panel * * This is where the history text area gets managed. *
	 * All of the user inputs will be recorded here. *
	 ********************************************************************
	 */
	private JPanel makeHistoryPanel() {

		// This is the history text area where all of the user inputs will be recorded
		JPanel historyPanel = new JPanel();
		history = new JTextArea();
		history.setLineWrap(true);
		history.setWrapStyleWord(true);

		history.setPreferredSize(new Dimension(200, 10000)); // important
		history.setBorder(new LineBorder(new Color(17, 15, 15)));
		history.setEditable(false);

		////////////////////////////////////////////////////////////////

		JScrollPane scroll = new JScrollPane(history);
		scroll.setPreferredSize(new Dimension(200, 300));
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		////////////////////////////////////////////////////////////////

		historyPanel.add(scroll);

		return historyPanel;
	}

	/**
	 ********************************************************************
	 * Make Control panel * * This is 'top-level' panel that stores everything for
	 * the history.*
	 ********************************************************************
	 */
	private JPanel makeControlPanel() {

		JPanel controlPanel = new JPanel();
		controlPanel.setPreferredSize(new Dimension(250, 200));
		controlPanel.add(makeHistoryPanel());

		return controlPanel;
	}

	/**
	 ********************************************************************
	 * Make Board panel * * This is where the button grid, row/column labels and
	 * mark panel * are handled. * *
	 * 
	 * @param gridSize - Used to change the grid size. *
	 ********************************************************************
	 */
	protected JPanel makeBoardPanel(ResourceBundle langText, int gridSize, boolean markMode) {
		boardPanel = new JPanel();
		// Column panel
		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(gridSize, 1));
		colPanel.setPreferredSize(new Dimension(75, 0));

		for (int i = 0; i < gridSize; i++) {
			JLabel grid = new JLabel("0 0", SwingConstants.CENTER);
			grid.setBackground(Color.GRAY);
			colPanel.add(grid);
		}

		////////////////////////////////////////////////////////////////

		// Mark panel
		markCheckBox = new JCheckBox(langText.getString("mark")); // TODO: resets to english when grid size changes
		// incase the grid size is changing, check to see if in markMode.
		// if true, then set the checkmark in new board
		if (markMode == true) {
			markCheckBox.setSelected(true);
		}
		markCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		boardPanel.setLayout(new BorderLayout());

		////////////////////////////////////////////////////////////////

		// Row panel
		JPanel rowPanel = new JPanel();
		rowPanel.add(markCheckBox);
		rowPanel.setLayout(new GridLayout(1, gridSize + 1));
		rowPanel.setPreferredSize(new Dimension(0, 75));
		rowPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(17, 15, 15)));

		for (int i = 0; i < gridSize; i++) {
			JLabel grid = new JLabel("<html>0<br/>0<br/>0<br/></html>", SwingConstants.CENTER);
			rowPanel.add(grid);
		}

		////////////////////////////////////////////////////////////////
		// Adds the components to the board panel
		boardPanel.add(rowPanel, BorderLayout.NORTH);
		boardPanel.add(colPanel, BorderLayout.WEST);
		////////////////////////////////////////////////////////////////

		JPanel gridButtonPanel = new JPanel();
		gridButtonPanel.setLayout(new GridLayout(gridSize, gridSize));
		buttons = new JButton[gridSize][gridSize];

		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				JButton newGridButton = new JButton();
				newGridButton.setBackground(Color.WHITE);
				buttons[i][j] = newGridButton;
				gridButtonPanel.add(buttons[i][j]);
			}
		}

		////////////////////////////////////////////////////////////////
		boardPanel.add(gridButtonPanel, BorderLayout.CENTER);
		return boardPanel;
	}

	/**
	 ********************************************************************
	 * Update Text * * This updates the text after the language is changed. * *
	 * 
	 * @param locale   - Used to get the selected language. *
	 * @param langText - Used to get the text from the language file. *
	 ********************************************************************
	 */
	protected void updateText(Locale currentLocale, ResourceBundle langText) {

		// This is only entered in the main game
		if (gameMode == 1) {
			timerLabel.setText((langText.getString("timer")));
			scoreLabel.setText(langText.getString("score"));
			colourLabel.setText(langText.getString("colorScheme"));
			
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

	}

	

	protected void launcher(ResourceBundle langText, Locale currentLocale) {
		//Locale defaultLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
		//ResourceBundle defaultLangText = ResourceBundle.getBundle("MessagesBundle", defaultLocale);
		startWindow = new JFrame();
		startWindow.setLayout(new BorderLayout());
		////////////////////////////////////////////////////////////////
		JPanel titlePanel = new JPanel();
		ImageIcon titleLogo = new ImageIcon("./src/images/picross.jpg");
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
		//designButton.addActionListener(this);
		////////////////////////////////////////////////////////////////
		playButton = new JButton(langText.getString("play"));
		playButton.setPreferredSize(new Dimension(100, 30));
		//playButton.addActionListener(this);
		playButton.setBackground(Color.WHITE);
		////////////////////////////////////////////////////////////////
		//JPanel configurationPanel = new JPanel();
		//configurationPanel.setLayout(new GridLayout(1, 2));
		//configurationPanel.add(makeLanguagePanel());
		//configurationPanel.setPreferredSize(new Dimension(225, 60));
		//configurationPanel.setBackground(Color.WHITE);
		/////////////////////////////////////////////////////////////////
		startPanel.add(designButton);
		startPanel.add(playButton);
		//startPanel.add(configurationPanel);
		startWindow.add(titlePanel, BorderLayout.NORTH);
		startWindow.add(startPanel,BorderLayout.CENTER);
		startWindow.add(makeLanguagePanel(currentLocale, langText), BorderLayout.SOUTH);
		startWindow.pack();
		////////////////////////////////////////////////////////////////		
		startWindow.setVisible(true);
		startWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startWindow.setResizable(false);
		startWindow.setSize(400, 290);
		startWindow.setLocationRelativeTo(null);
	}

	/**
	 ********************************************************************
	 * Design method * * This shows the "default" board layout to the user. *
	 ********************************************************************
	 */
	protected void Design(Locale currentLocale, ResourceBundle langText) {
		designBack = new JButton("Back");
		designWindow = new JFrame();
		designWindow.setLayout(new BorderLayout());
		//JPanel configGrid = new JPanel();
		//configGrid.setLayout(new GridLayout(2, 2, 0, 0));
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
	 ********************************************************************
	 * Play method * * This shows the main game. *
	 ********************************************************************
	 */
	protected void Play(Locale currentlocale, ResourceBundle langText) {
		gameMode = 1;
		picrossWindow = new JFrame();
		picrossWindow.setLayout(new BorderLayout());
		makeMenuBar();
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
	 ************************************************************************
	 * This is the instructions class that explains * how the game is supposed to be
	 * played. * *
	 * 
	 * @param locale - Used to get the selected language. *
	 ************************************************************************
	 */
	protected void Instructions(Locale currentLocale) {
		instructionsWindow = new JFrame();
		JPanel instructionsPanel = new JPanel();
		instructionsPanel.setPreferredSize(new Dimension(500, 400));
		JLabel instructionsLabel = new JLabel();
		instructionsBack = new JButton("Back");

		////////////////////////////////////////////////////////////////

		String printInstructions = "";

		// If the language is set to English
		if (currentLocale.getCountry() == "US") {
			printInstructions = "<html><br/>There are multiple rows and columns that are adjacent<br/>"
					+ "to the grid of buttons, these will have numbers that will indicate<br/>"
					+ "how many correct tiles are in that row/column.<br/><br/>"
					+ "The buttons in the grid can either be clicked or marked as empty,<br/>"
					+ "they will be highlighted to indicate right or wrong. <br/><br/>"
					+ "Once a button is clicked, the timer will start and the score<br/>"
					+ "will be initialized to 0. When all of the correct tiles are placed,<br/>"
					+ "the score goes up and the timer resets.<br/><br/>"
					+ "At any time you can solve the grid, reset the board or generate<br/>"
					+ "a new board that has a random pattern.<br/><br/></html>";
		}

		////////////////////////////////////////////////////////////////

		// If the language is set to French
		else {
			printInstructions = "<html><br/>Il y a plusieurs lignes et colonnes adjacentes à la<br/>"
					+ "grille de boutons. Les numéros de ces rangées indiquent le nombre<br/>"
					+ "de tuiles corriges dans la rangée ou la colonne en question.<br/>"
					+ "Les boutons de la grille peuvent être cliqués ou marqués<br/>"
					+ "comme étant vides. Ils seront mis en évidence pour indiquer<br/>"
					+ "s'ils sont bons ou mauvais. <br/><br/>"
					+ "Dès qu'un bouton est cliqué, le minuteur démarre et<br/>" + "le score est initialisé à 0<br/>"
					+ "Lorsque toutes les bonnes tuiles sont placées, le score augmente<br/>"
					+ "et le minuteur se remet à zéro. <br/><br/>"
					+ "À tout moment, vous pouvez résoudre la grille, réinitialiser le<br/>"
					+ "plateau ou générer un nouveau plateau avec un motif aléatoire.<br/><br/></html>";
		}

		////////////////////////////////////////////////////////////////

		instructionsLabel.setText(printInstructions);
		instructionsLabel.setFont(new Font("Calibri Regular", Font.PLAIN, 16));

		instructionsBack.setFont(new Font("Calibri Regular", Font.BOLD, 12));

		////////////////////////////////////////////////////////////////

		instructionsPanel.add(instructionsLabel);
		instructionsPanel.add(instructionsBack);
		instructionsWindow.add(instructionsPanel);

		////////////////////////////////////////////////////////////////

		instructionsWindow.setTitle("Instructions - Skylar Phanenhour, Ahnaf Kamal");
		instructionsWindow.setResizable(false);
		instructionsWindow.setSize(525, 425);
		instructionsWindow.setVisible(true);
		instructionsWindow.setLocationRelativeTo(null);
	}


	
	void updateView(int gridSize, int gameMode, boolean markMode) {
		
	}

}
