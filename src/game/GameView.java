package game;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.LineBorder;

// make the gui
public class GameView {
	private JButton instructionsButton;
	/** Button to return from the instructions page to the main game */
	private JButton instructionsBack;
	/** JFrame for the design window */
	private JFrame designWindow;
	/** JFrame for the start window */
	private JFrame startWindow;
	private JFrame splashWindow;
	/** JFrame for the instructions window */
	private JFrame instructionsWindow;
	/** JFrame for the main picross window */
	private JFrame picrossWindow;
	/** Combo box to change the grid size */
	private JComboBox<String> gridSizeCmbo;
	/** Button to play the game */
	private JButton playButton;
	/** Button to open the design window */
	private JButton designButton;
	/** Button to return from the design page to the main page */
	private JButton designBack;
	/** Button to reset the game */
	private JButton resetButton;
	/** Button to solve the game */
	private JButton solveButton;
	/** Button to make a new board */
	private JButton newBoardButton;
	/** Text area to display the input history */
	protected JTextArea history;
	/** 2d-button array for the grid */
	private JButton[][] buttons;
	
	private String [] rows;
	Color tile_color = new Color(17, 15, 15);
	Color mark_color = new Color(226, 222, 222);
	Color err_color = Color.red;
	
	private String[] viewRows;
	private String[] viewCols;
	private String[] viewRowLabels= {"0","0","0","0","0"};
	private String[] viewColLabels={"0","0","0","0","0"};
	String[] viewRowLabelsDesign;
	String[] viewRowLabelsCol;

	
	private JButton[][] buttonsDesign;
	private JButton playToLauncher;
	private JButton newGridButton;
	
	/** Radio button to change the language to English */
	private JRadioButton engRadio;
	/** Radio button to change the language to French */
	private JRadioButton frRadio;
	/** Left panel to hold all of the menu components */
	private JPanel leftPanel;
	/** Board panel to hold the grid of buttons */
	private JPanel boardPanel;
	/** Panel to hold the back button in the design window */
	private JPanel designMenuReturnPanel;
	/** Check box for to enable the mark mode */
	private JCheckBox markCheckBox;
	private JTextField scoreCounter;
	private JTextField timerCounter;
	


	/** Boolean for the mark mode, false by default */
	//protected boolean markMode = false;
	/** Int variable to check what the current game mode is, 0=design, 1=play */
	private int gameMode = 0;
	/*
	 ********************************************************************
	 * JLabel initializations. * The text that is in the label changes depending *
	 * on what language is selected. *
	 ********************************************************************
	 */
	/** Label for the timer */
	private JLabel timerLabel;
	//protected JLabel timerLabel = new JLabel(langText.getString("timer"));
	/** Label for the score */
	//protected JLabel scoreLabel = new JLabel(langText.getString("score"));
	private JLabel scoreLabel;
	/** Label for the grid size */
	//protected JLabel gridSizeLabel = new JLabel(langText.getString("gridSize"));
	private JLabel gridSizeLabel;
	/** Label for the language */
	private JLabel langLabel = new JLabel();
	/** Label for the colour */
	private JLabel colourLabel = new JLabel();
	/**
	 * 		newMenuOption = new JMenuItem("New Board", new ImageIcon(getClass().getResource("/images/piciconnew.gif")));
		exitMenuOption = new JMenuItem("Exit To Desktop", new ImageIcon(getClass().getResource("/images/piciconext.gif")));
		resetMenuOption = new JMenuItem("Reset Board", new ImageIcon(getClass().getResource("/images/reset.jpg")));
		solveMenuOption = new JMenuItem("Solve Puzzle", new ImageIcon(getClass().getResource("/images/solve.jpg")));
		toLauncherMenuOption = new JMenuItem("To Launcher", new ImageIcon(getClass().getResource("/images/toLauncher.jpg")));
		saveMenuOption = new JMenuItem("Save", new ImageIcon(getClass().getResource("/images/save.jpg")));
		loadMenuOption = new JMenuItem("Load", new ImageIcon(getClass().getResource("/images/load.jpg")));
	 */
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
	private JMenuBar menuBar;
	private JMenuItem newMenuItem;
	private JMenuItem solutionMenuItem;
	protected JMenuItem exitMenuItem;
	private JMenuItem backgroundColour;
	private JMenuItem textColour;
	private JMenuItem componentColour;
	private JMenuItem aboutMenu;
	private JMenuItem gameMenu;
	private JMenu gameMenuItemsContainer;
	private JMenuItem newMenuOption;
	private JMenuItem exitMenuOption;
	private JMenuItem resetMenuOption;
	private JMenuItem solveMenuOption;
	private JMenuItem toLauncherMenuOption;
	private JMenuItem saveMenuOption;
	private JMenuItem loadMenuOption;
	private JMenu gridSizeItemsContainer;
	private JMenuItem aboutMenuOption;
	private JMenuItem fiveFive = new JMenuItem("5x5");
	private JMenuItem sixSix = new JMenuItem("6x6");
	private JMenuItem sevSev = new JMenuItem("7x7");
	
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
		splashWindow.setSize(400, 175);
		splashWindow.setLocationRelativeTo(null);
		try {
			Thread.sleep(1000);
			splashWindow.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		launcher(langText, currentLocale);

	}
	
	protected void makeMenuBar(JFrame window, Locale currentLocale, ResourceBundle langText, int gameMode) {
		menuBar = new JMenuBar();
		window.setJMenuBar(menuBar);
		gameMenuItemsContainer = new JMenu("Game");
		newMenuOption = new JMenuItem("New Board", new ImageIcon(getClass().getResource("/images/piciconnew.gif")));
		exitMenuOption = new JMenuItem("Exit To Desktop", new ImageIcon(getClass().getResource("/images/piciconext.gif")));
		resetMenuOption = new JMenuItem("Reset Board", new ImageIcon(getClass().getResource("/images/reset.jpg")));
		solveMenuOption = new JMenuItem("Solve Puzzle", new ImageIcon(getClass().getResource("/images/solve.jpg")));
		toLauncherMenuOption = new JMenuItem("To Launcher", new ImageIcon(getClass().getResource("/images/toLauncher.jpg")));
		saveMenuOption = new JMenuItem("Save", new ImageIcon(getClass().getResource("/images/save.jpg")));
		loadMenuOption = new JMenuItem("Load", new ImageIcon(getClass().getResource("/images/load.jpg")));
		gridSizeItemsContainer = new JMenu("Grid Size");
		gridSizeItemsContainer.setIcon(new ImageIcon(getClass().getResource("/images/gridSize.jpg")));
		gridSizeItemsContainer.add(fiveFive);
		gridSizeItemsContainer.add(sixSix);
		gridSizeItemsContainer.add(sevSev);


		if (gameMode == 0) {
			gameMenuItemsContainer.add(saveMenuOption);
			gameMenuItemsContainer.add(loadMenuOption);
			gameMenuItemsContainer.addSeparator();
			gameMenuItemsContainer.add(resetMenuOption);
			gameMenuItemsContainer.add(gridSizeItemsContainer);
			gameMenuItemsContainer.addSeparator();
			gameMenuItemsContainer.add(solveMenuOption);
			gameMenuItemsContainer.addSeparator();				
		}
		else {
			gameMenuItemsContainer.add(loadMenuOption);
			gameMenuItemsContainer.addSeparator();
			gameMenuItemsContainer.add(newMenuOption);
			gameMenuItemsContainer.add(resetMenuOption);
			gameMenuItemsContainer.add(gridSizeItemsContainer);
			gameMenuItemsContainer.addSeparator();
			gameMenuItemsContainer.add(solveMenuOption);
			gameMenuItemsContainer.addSeparator();			
		}
		
		gameMenuItemsContainer.add(toLauncherMenuOption);
		gameMenuItemsContainer.add(exitMenuOption);

		JMenu helpMenuItemsContainer = new JMenu("Help");
		JMenu colourMenu = new JMenu("Colours"); // submenu under helpMenuItemsContainer		
		aboutMenuOption = new JMenuItem("About",new ImageIcon(getClass().getResource("/images/instructions.jpg")));
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
		languagePanel = new JPanel();
		languageButtonPanel = new JPanel();
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
		gridSizeComboPanel = new JPanel();
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

	/**
	 ********************************************************************
	 * Make Title Panel * * This is where the ImageIcon for the * Picross logo is
	 * created/set. *
	 ********************************************************************
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
	 ********************************************************************
	 * Make History Panel * * This is where the history text area gets managed. *
	 * All of the user inputs will be recorded here. *
	 ********************************************************************
	 */
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

	/**
	 ********************************************************************
	 * Make Control panel * * This is 'top-level' panel that stores everything for
	 * the history.*
	 ********************************************************************
	 */
	private JPanel makeControlPanel() {
		controlPanel = new JPanel();
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
		rowPanel = new JPanel();
		rowPanel.setLayout(new GridLayout(gridSize, 1));
		rowPanel.setPreferredSize(new Dimension(75, 0));
		
		for(int i = 0; i< gridSize; i++) {
			JLabel rowLabel = new JLabel(viewRowLabels[i],SwingConstants.CENTER);
			rowPanel.add(rowLabel);
		}
		// Mark panel
		markCheckBox = new JCheckBox(langText.getString("mark")); // TODO: resets to english when grid size changes
		// incase the grid size is changing, check to see if in markMode.
		// if true, then set the checkmark in new board
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

		for(int i = 0; i< gridSize; i++) {
			JLabel colLabel = new JLabel(viewColLabels[i], SwingConstants.CENTER);
			colPanel.add(colLabel);
		}
		// Adds the components to the board panel
		boardPanel.add(colPanel, BorderLayout.NORTH);
		boardPanel.add(rowPanel, BorderLayout.WEST);

		JPanel gridButtonPanel = new JPanel();
		gridButtonPanel.setLayout(new GridLayout(gridSize, gridSize));
		buttons = new JButton[gridSize][gridSize];

		for (int i = 0; i < gridSize; i++) {
			for (int j = 0; j < gridSize; j++) {
				newGridButton = new JButton();
				newGridButton.setBackground(Color.WHITE);
				buttons[i][j] = newGridButton;
				gridButtonPanel.add(buttons[i][j]);
			}
		}

		boardPanel.add(gridButtonPanel, BorderLayout.CENTER);
		return boardPanel;
	}

	void updateDesignRow(String newThing, int index) {
		JLabel newLabel = new JLabel(newThing, SwingConstants.CENTER);
		rowPanel.remove(index);
		rowPanel.invalidate();
		rowPanel.add(newLabel, index);
		rowPanel.revalidate();
	}
	
	void updateDesignCol(String newThing, int index) {
		
		JLabel newLabel = new JLabel(newThing, SwingConstants.CENTER);
		colPanel.remove(index+1);
		colPanel.invalidate();
		colPanel.add(newLabel, index+1);
		colPanel.revalidate();
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
		solveMenuOption.setText(langText.getString("solve"));
		newMenuOption.setText(langText.getString("newBoard"));
		resetMenuOption.setText(langText.getString("reset"));
		saveMenuOption.setText(langText.getString("save"));
		loadMenuOption.setText(langText.getString("load"));
		toLauncherMenuOption.setText(langText.getString("toLauncher"));
		gridSizeItemsContainer.setText(langText.getString("gridSize"));
		exitMenuOption.setText(langText.getString("exit"));
	}

	

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
	 ********************************************************************
	 * Design method * * This shows the "default" board layout to the user. *
	 ********************************************************************
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
	 ********************************************************************
	 * Play method * * This shows the main game. *
	 ********************************************************************
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
	 ************************************************************************
	 * This is the instructions class that explains * how the game is supposed to be
	 * played. * *
	 * 
	 * @param locale - Used to get the selected language. *
	 ************************************************************************
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
				if (getRows()[i].charAt(j) == '1') {
					buttons[i][j].setBackground(Color.black);
					buttons[i][j].setEnabled(false);
				}
			}
		}
	}
	
	protected void resetBoard() {
		 for (JButton[] i : buttons) {
	            for (JButton j : i) {
	                j.setBackground(Color.WHITE);
	                j.setEnabled(true);
	            }
	        }
	}

	public String[] getViewRows() {
		return viewRows;
	}

	public void setViewRows(String[] viewRows) {
		this.viewRows = viewRows;
	}

	public String[] getViewCols() {
		return viewCols;
	}

	public void setViewCols(String[] viewCols) {
		this.viewCols = viewCols;
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

	public String [] getRows() {
		return rows;
	}

	public void setRows(String [] rows) {
		this.rows = rows;
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

	
	protected int gridSize = 5;
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
	 * @return the buttons
	 */
	protected JButton[][] getButtons() {
		return buttons;
	}

	/**
	 * @param buttons the buttons to set
	 */
	protected void setButtons(JButton[][] buttons) {
		this.buttons = buttons;
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
	 * @return the colourLabel
	 */
	protected JLabel getColourLabel() {
		return colourLabel;
	}

	/**
	 * @param colourLabel the colourLabel to set
	 */
	protected void setColourLabel(JLabel colourLabel) {
		this.colourLabel = colourLabel;
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
	 * @return the menuBar
	 */
	protected JMenuBar getMenuBar() {
		return menuBar;
	}

	/**
	 * @param menuBar the menuBar to set
	 */
	protected void setMenuBar(JMenuBar menuBar) {
		this.menuBar = menuBar;
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
	 * @return the gameMenuItemsContainer
	 */
	protected JMenu getGameMenuItemsContainer() {
		return gameMenuItemsContainer;
	}

	/**
	 * @param gameMenuItemsContainer the gameMenuItemsContainer to set
	 */
	protected void setGameMenuItemsContainer(JMenu gameMenuItemsContainer) {
		this.gameMenuItemsContainer = gameMenuItemsContainer;
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
	 * @return the gridSizeItemsContainer
	 */
	protected JMenu getGridSizeItemsContainer() {
		return gridSizeItemsContainer;
	}

	/**
	 * @param gridSizeItemsContainer the gridSizeItemsContainer to set
	 */
	protected void setGridSizeItemsContainer(JMenu gridSizeItemsContainer) {
		this.gridSizeItemsContainer = gridSizeItemsContainer;
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
