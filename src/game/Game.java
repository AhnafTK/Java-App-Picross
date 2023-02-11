
package game;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.LineBorder;

/**
 ****************************************************************************
 * This is the game class that handles the GUI processing of our game		*
 * 																			*
 * @author Skylar Phanenhour, Ahnaf Kamal									*
 ****************************************************************************
 */
public class Game extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	/**Button to open the instructions window*/
	protected JButton instructionsButton;
	/**Button to return from the instructions page to the main game*/
	protected JButton instructionsBack;
	/**JFrame for the design window*/
	private JFrame designWindow;
	/**JFrame for the start window*/
	private JFrame startWindow;
	/**JFrame for the instructions window*/
	private JFrame instructionsWindow;
	/**JFrame for the main picross window*/
	private JFrame picrossWindow;
	/**Combo box to change the grid size*/
	protected JComboBox<String> gridSizeCmbo;
	/**Button to play the game*/
	protected JButton playButton;
	/**Button to open the design window*/
	protected JButton designButton;
	/**Button to return from the design page to the main page*/
	protected JButton designBack;
	/**Button to reset the game*/
	protected JButton resetButton;
	/**Button to solve the game*/
	protected JButton solveButton;
	/**Button to make a new board*/
	protected JButton newBoardButton;
	/**Text area to display the input history*/
	protected JTextArea history;
	/**2d-button array for the grid*/
	protected JButton[][] buttons;
	/**Radio button to change the language to English*/
	protected JRadioButton engRadio;
	/**Radio button to change the language to French*/
	protected JRadioButton frRadio;
	/**Left panel to hold all of the menu components*/
	protected JPanel leftPanel = new JPanel();
	/**Board panel to hold the grid of buttons*/
	protected JPanel boardPanel = new JPanel();
	/**Panel to hold the back button in the design window*/
	protected JPanel designMenuReturnPanel;
	/**Check box for to enable the mark mode*/
	protected JCheckBox markCheckBox;
	/**Int variable to hold the grid size, 5x5 by default*/
	protected int gridSize = 5;
	/**Boolean for the mark mode, false by default*/
	protected boolean markMode = false;
	/**Int variable to check what the current game mode is, 0=design, 1=play*/
	protected int gameMode = 0;
	/**Local builder to change the language*/
	Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
	/**Resource bundle to get the language messages*/
	ResourceBundle langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);

	/*
	 ********************************************************************
	 * JLabel initializations.											*
	 * The text that is in the label changes depending 					*
	 * on what language is selected.									*
	 ********************************************************************
	 */
	/**Label for the timer*/
	protected JLabel timerLabel = new JLabel(langText.getString("timer"));
	/**Label for the score*/
	protected JLabel scoreLabel = new JLabel(langText.getString("score"));
	/**Label for the grid size*/
	protected JLabel gridSizeLabel = new JLabel(langText.getString("gridSize"));
	/**Label for the language*/
	protected JLabel langLabel = new JLabel();
	/**Label for the colour*/
	protected JLabel colourLabel = new JLabel();

	/**
	 ************************************************************************
	 * Main method of the project that creates a new game					*
	 * by calling the default constructor.									*
	 * 																		*	
	 * @param args - main method arguments									*
	 ************************************************************************
	 */		
	public static void main(String[] args) {
		new Game();
	}
	
	
	private void makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		JMenu helpMenu = new JMenu("Help");
		
		ImageIcon newIcon = new ImageIcon(".src/images/piciconnew.gif");
		JMenuItem newMenu = new JMenuItem("New", newIcon);
		
		ImageIcon solutionIcon = new ImageIcon(".src/images/piciconsol.gif");
		JMenuItem solutionMenu = new JMenuItem("Solution", solutionIcon);		
		
		ImageIcon exitIcon = new ImageIcon(".src/images/piciconext.gif");
		JMenuItem exitMenu = new JMenuItem("Exit", exitIcon);
		
		gameMenu.add(newMenu);
		gameMenu.add(solutionMenu);
		gameMenu.add(exitMenu);
		
		ImageIcon colourIcon = new ImageIcon(".src/images/piciconcol.gif");
		JMenuItem colourMenu = new JMenuItem("Colours", colourIcon);
		colourMenu.setIcon(colourIcon);
		
		ImageIcon aboutIcon = new ImageIcon(".src/images/piciconabt.gif");
		JMenuItem aboutMenu = new JMenuItem("About", aboutIcon);
		aboutMenu.setIcon(aboutIcon);
		
		helpMenu.add(colourMenu);
		helpMenu.add(aboutMenu);
		
		menuBar.add(gameMenu);
		menuBar.add(helpMenu);
		
		picrossWindow.setJMenuBar(menuBar);
	}
	
	
	/*
	 ********************************************************************
	 * Make Language Panel												*
	 * 																	*
	 * This is where all of the language panels, labels, radio buttons	*
	 * and button group are located.									*
	 * 																	*
	 * Whatever radio button is selected, will change the game to 		*
	 * that language. 													*
	 * "English" is selected by default when the game is started.		*
	 ********************************************************************
	 */
	private JPanel makeLanguagePanel() {
		
		JPanel languagePanel = new JPanel();
		JPanel languageButtonPanel = new JPanel();

		// Button group that holds the "English" and "French" radio buttons
		////////////////////////////////////////////////////////////////

		engRadio = new JRadioButton(langText.getString("english"), true);
		engRadio.addActionListener(this);

		////////////////////////////////////////////////////////////////

		frRadio = new JRadioButton(langText.getString("french"));
		frRadio.addActionListener(this);

		////////////////////////////////////////////////////////////////

		ButtonGroup langButtons = new ButtonGroup();
		langButtons.add(engRadio);
		langButtons.add(frRadio);

		langLabel.setText(langText.getString("languages"));

		// Panel to align the language radio buttons
		languageButtonPanel.setLayout(new BoxLayout(languageButtonPanel, BoxLayout.Y_AXIS));
		//Adds the radio buttons to the panel
		languageButtonPanel.add(engRadio);
		languageButtonPanel.add(frRadio);

		// Main language panel that stores/aligns all of the labels/radio buttons
		languagePanel.add(langLabel);
		languagePanel.add(languageButtonPanel);
		
		return languagePanel;
	}
	
	/*
	 ********************************************************************
	 * Make Grid Size Combo												*
	 * 																	*
	 * Grid size combo box panel.										*
	 * Combo box options are initialized in a string array.				*
	 * Grid size label changes depending on what language is selected.  *
	 ********************************************************************
	 */
	private JPanel makeGridSizeCombo() {
		String options[] = { "5x5", "6x6", "7x7" };
		gridSizeCmbo = new JComboBox<>(options);
		gridSizeCmbo.addActionListener(this);
		gridSizeCmbo.setBackground(Color.WHITE);
		JPanel gridSizeComboPanel = new JPanel();
		gridSizeComboPanel.add(gridSizeLabel);
		gridSizeComboPanel.add(gridSizeCmbo);
		gridSizeComboPanel.setPreferredSize(new Dimension(200, 30));
		
		return gridSizeComboPanel;
	}

	/**
	 ************************************************************************
	 * Make Left Panel.														*
	 * 																		*	
	 * This is the 'top-level' panel that stores everything for the left	*
	 * panel. All of the 'lower-level' components get added to this panel.	*
	 * 																		*
	 * @param locale - Used to get the selected language.					*
	 * @param langText - Used to get the text from the language file.		*
	 ************************************************************************
	 */	
	private JPanel makeLeftPanel(Locale locale, ResourceBundle langText) {
		/*
		 ********************************************************************
		 * JPanel initializations.											*
		 * The text that is in the button changes depending 				*
		 * on what language is selected.									*
		 ********************************************************************
		 */
		JPanel scorePanel = new JPanel();
		JPanel timerPanel = new JPanel();
		JPanel buttonPanel = new JPanel();

		/*
		 ********************************************************************
		 * JButton initializations.											*
		 * The text that is in the button changes depending 				*
		 * on what language is selected.									*
		 ********************************************************************
		 */
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

		////////////////////////////////////////////////////////////////

		/*
		 ********************************************************************
		 * Score panel components.											*
		 * This is also where the score counter text field is created.		*
		 ********************************************************************
		 */	
		JTextField scoreCounter = new JTextField();
		scoreCounter.setBorder(new LineBorder((new Color(17,15,15)), 1));
		scoreCounter.setPreferredSize(new Dimension(100, 25));
		scoreCounter.setEditable(false);
		
		scorePanel.add(scoreLabel);
		scorePanel.add(scoreCounter);
		
		/*
		 ********************************************************************
		 * Timer panel components.											*
		 * This is also where the timer counter text field is created.		*
		 ********************************************************************
		 */
		JTextField timerCounter = new JTextField();
		timerCounter.setBorder(new LineBorder((new Color(17,15,15)), 1));
		timerCounter.setPreferredSize(new Dimension(100, 25));
		timerCounter.setEditable(false);

		timerPanel.add(timerLabel);
		timerPanel.add(timerCounter);

		
		/*
		 ********************************************************************
		 * Configuration Settings											*
		 * 																	*
		 * This is 'top-level' panel that stores the language/colour panels	*
		 * in order for them to be propely layed out in the left panel.		*
		 ********************************************************************
		 */		
		JPanel configurationPanel = new JPanel();
		configurationPanel.setLayout(new GridLayout(1, 2));
		configurationPanel.add(makeLanguagePanel());
		configurationPanel.setPreferredSize(new Dimension(225, 100));

		/*
		 ********************************************************************
		 * Buttons Panel													*
		 * 																	*
		 * This panel is used to store/align all of the JButtons that 		*
		 * will later be added to the left panel.							*
		 ********************************************************************
		 */
		//Vertically aligns the buttons in the panel
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		buttonPanel.setPreferredSize(new Dimension(120, 160));
		
		//Sets all of the buttons to the same size and creates an ActionListener
		resetButton.setPreferredSize(new Dimension(120, 25));
		resetButton.addActionListener(this);

		////////////////////////////////////////////////////////////////
		
		solveButton.setPreferredSize(new Dimension(120, 25));
		solveButton.addActionListener(this);

		////////////////////////////////////////////////////////////////

		newBoardButton.setPreferredSize(new Dimension(120, 25));
		newBoardButton.addActionListener(this);

		////////////////////////////////////////////////////////////////

		instructionsButton.setPreferredSize(new Dimension(120, 25));
		instructionsButton.addActionListener(this);

		////////////////////////////////////////////////////////////////
		
		//Adds all of the buttons to the panel
		buttonPanel.add(resetButton);
		buttonPanel.add(solveButton);
		buttonPanel.add(newBoardButton);
		buttonPanel.add(instructionsButton);
		
		/*
		 ********************************************************************
		 * Left panel														*
		 * 																	*
		 * This is 'top-level' panel that stores all the 'lower level'		*
		 * panels in order for all the components to properly align.		*
		 ********************************************************************
		 */		
		//Adds all of the 'lower level' panels 
		leftPanel.add(scorePanel);
		leftPanel.add(timerPanel);
		leftPanel.add(makeGridSizeCombo());
		leftPanel.add(buttonPanel);
		leftPanel.add(configurationPanel);

		leftPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,2, (new Color(17,15,15))));
		leftPanel.setPreferredSize(new Dimension(250, 200));

		return leftPanel;
	}

	/**
	 ********************************************************************
	 * Make Title Panel													*
	 * 																	*
	 * This is  where the ImageIcon for the 							*
	 * Picross logo is created/set.										*
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
	 * Make History Panel												*
	 * 																	*
	 * This is  where the history text area gets managed. 			 	*
	 * All of the user inputs will be recorded here.					*
	 ********************************************************************
	 */
	private JPanel makeHistoryPanel() {
		
		// This is the history text area where all of the user inputs will be recorded
		JPanel historyPanel = new JPanel();
		history = new JTextArea();
		history.setLineWrap(true);
		history.setWrapStyleWord(true);

		history.setPreferredSize(new Dimension(200, 10000)); // important
		history.setBorder(new LineBorder(new Color(17,15,15)));
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
	 * Make Control panel												*
	 * 																	*
	 * This is 'top-level' panel that stores everything for the history.*
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
	 * Make Board panel													*
	 * 																	*
	 * This is where the button grid, row/column labels and mark panel 	*
	 * are handled.														*
	 * 																	*
	 * @param gridSize - Used to change the grid size.					*
	 ********************************************************************
	 */	
	private JPanel makeBoardPanel(int gridSize) {
		
		// Column panel
		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(gridSize, 1));
		colPanel.setPreferredSize(new Dimension(75, 0));
				
		for (int i = 0; i<gridSize; i++) {
			JLabel grid = new JLabel("0 0", SwingConstants.CENTER);
			grid.setBackground(Color.GRAY);
			colPanel.add(grid);
		}

		////////////////////////////////////////////////////////////////

		// Mark panel
		markCheckBox = new JCheckBox("Mark");
		// incase the grid size is changing, check to see if in markMode.
		// if true, then set the checkmark in new board
		if (markMode == true) {
			markCheckBox.setSelected(true);
		}
		markCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		markCheckBox.addActionListener(this);
		boardPanel.setLayout(new BorderLayout());
		
		////////////////////////////////////////////////////////////////

		// Row panel
		JPanel rowPanel = new JPanel();
		rowPanel.add(markCheckBox);
		rowPanel.setLayout(new GridLayout(1, gridSize+1));
		rowPanel.setPreferredSize(new Dimension(0, 75));
		rowPanel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, new Color(17,15,15)));
		
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
				buttons[i][j].addActionListener(this);
				gridButtonPanel.add(buttons[i][j]);
			}
		}
		
		////////////////////////////////////////////////////////////////
		
		// This is only entered when the design button is pressed
		if (gameMode == 0) {
			designMenuReturnPanel = new JPanel();
			designMenuReturnPanel.setPreferredSize(new Dimension(500, 50));
			designBack = new JButton("Back");
			designBack.addActionListener(this);
			designMenuReturnPanel.add(designBack);
			boardPanel.add(designMenuReturnPanel, BorderLayout.SOUTH);

		}

		boardPanel.add(gridButtonPanel, BorderLayout.CENTER);
		
		return boardPanel;
	}
	
	
	/**
	 ********************************************************************
	 * Update Text														*
	 * 																	*
	 * This updates the text after the language is changed.				*
	 * 																	*
	 * @param locale - Used to get the selected language.				*
	 * @param langText - Used to get the text from the language file.	*
	 ********************************************************************
	 */	
	private void updateText(Locale currentLocale, ResourceBundle langText) {
		
		// This is only entered in the main game
		if (gameMode == 1) {
			timerLabel.setText((langText.getString("timer")));
			scoreLabel.setText(langText.getString("score"));
			resetButton.setText(langText.getString("reset"));
			instructionsButton.setText(langText.getString("instructions"));
			solveButton.setText(langText.getString("solve"));
			newBoardButton.setText(langText.getString("newBoard"));
			langLabel.setText(langText.getString("languages"));
			colourLabel.setText(langText.getString("colorScheme"));
		}
		
		gridSizeLabel.setText(langText.getString("gridSize"));
		markCheckBox.setText(langText.getString("mark"));
		engRadio.setText(langText.getString("english"));
		frRadio.setText(langText.getString("french"));
		
	}
	
	/**
	 ************************************************************************
	 * Default constructor for the Game class								*
	 * This is where all of the GUI of the game gets handled.				*
	 ************************************************************************
	 */
	public Game() {
		/*
		 ********************************************************************
		 * Start window frame												*
		 * 																	*
		 * This is where everything gets handled for the start JFrame.		*
		 ********************************************************************
		 */	
		startWindow = new JFrame();
		startWindow.setLayout(new BorderLayout());

		////////////////////////////////////////////////////////////////

		JPanel splashTitlePanel = new JPanel();
		ImageIcon titleLogo = new ImageIcon("./src/images/picross.jpg");
		JLabel titleLabel = new JLabel();
		titleLabel.setIcon(titleLogo);
		splashTitlePanel.setPreferredSize(new Dimension(500, 125));
		splashTitlePanel.add(titleLabel);
		
		////////////////////////////////////////////////////////////////
		
		JPanel startPanel = new JPanel();
		startPanel.setBackground(new Color(17,15,15));
		startPanel.setPreferredSize(new Dimension(100,100));
		
		////////////////////////////////////////////////////////////////

		designButton = new JButton("Design");
		designButton.setPreferredSize(new Dimension(100, 30));
		designButton.setBackground(Color.WHITE);
		designButton.addActionListener(this);

		////////////////////////////////////////////////////////////////

		playButton = new JButton("Play");
		playButton.setPreferredSize(new Dimension(100, 30));
		playButton.addActionListener(this);
		playButton.setBackground(Color.WHITE);

		////////////////////////////////////////////////////////////////

		startPanel.add(designButton);
		startPanel.add(playButton);
		startWindow.add(splashTitlePanel, BorderLayout.NORTH);
		startWindow.add(startPanel,BorderLayout.CENTER);
		startWindow.pack();
		
		////////////////////////////////////////////////////////////////		
		
		startWindow.setVisible(true);
		startWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startWindow.setResizable(false);
		startWindow.setSize(400, 220);
		startWindow.setLocationRelativeTo(null);
	}

	/**
	 ********************************************************************
	 * Design method													*
	 * 																	*
	 * This shows the "default" board layout to the user.				*
	 ********************************************************************
	 */	
	private void Design() {
		designWindow = new JFrame();
		
		designWindow.setLayout(new BorderLayout());
		JPanel configGrid = new JPanel();
		configGrid.setLayout(new GridLayout(2,2,0,0));
		
		////////////////////////////////////////////////////////////////

		designWindow.add(makeBoardPanel(gridSize), BorderLayout.CENTER);
		configGrid.add(makeLanguagePanel());
		configGrid.add(makeGridSizeCombo());

		////////////////////////////////////////////////////////////////

		designWindow.add(configGrid, BorderLayout.NORTH);
		designWindow.setResizable(false);
		designWindow.setVisible(true);
		designWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		designWindow.setSize(500, 650);
		designWindow.setLocationRelativeTo(null);
	}
	
	/**
	 ********************************************************************
	 * Play method														*
	 * 																	*
	 * This shows the main game.										*
	 ********************************************************************
	 */	
	private void Play() {
		/*
		 ********************************************************************
		 * Picross window frame												*
		 * 																	*
		 * This is where everything gets handled for the picross JFrame.	*
		 ********************************************************************
		 */	
		gameMode = 1;
		picrossWindow = new JFrame();

		picrossWindow.setLayout(new BorderLayout());
		makeMenuBar();
		picrossWindow.add(makeTitlePanel(), BorderLayout.NORTH);
		picrossWindow.add(makeLeftPanel(currentLocale, langText), BorderLayout.WEST);
		picrossWindow.add(makeControlPanel(), BorderLayout.EAST);
		picrossWindow.add(makeBoardPanel(gridSize), BorderLayout.CENTER);
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
	 * This is the instructions class that explains 						*
	 * how the game is supposed to be played.								*
	 * 																		*
	 * @param locale - Used to get the selected language.					*
	 ************************************************************************
	 */
	private void Instructions(Locale currentLocale) {
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
	                		  + "Dès qu'un bouton est cliqué, le minuteur démarre et<br/>"
	                		  + "le score est initialisé à 0<br/>"
	                		  + "Lorsque toutes les bonnes tuiles sont placées, le score augmente<br/>"
	                		  + "et le minuteur se remet à zéro. <br/><br/>"
	                		  + "À tout moment, vous pouvez résoudre la grille, réinitialiser le<br/>"
	                		  + "plateau ou générer un nouveau plateau avec un motif aléatoire.<br/><br/></html>";
		}
		
		////////////////////////////////////////////////////////////////

		 
		instructionsLabel.setText(printInstructions);
		instructionsLabel.setFont(new Font("Calibri Regular", Font.PLAIN, 16));

		instructionsBack.setFont(new Font("Calibri Regular", Font.BOLD, 12));
		instructionsBack.addActionListener(this);

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

	/**
	 ********************************************************************
	 * Action listener													*
	 * 																	*
	 * This is where all of the action event handlers get managed when	*
	 * a component gets clicked.										*
	 * 																	*
	 * @param e - Action Event handler for components.					*
	 ********************************************************************
	 */	
	@Override
	public void actionPerformed(ActionEvent e) {

		// Design button
		if (e.getSource() == designButton) {
			startWindow.dispose();
			Design();
		}
		
		////////////////////////////////////////////////////////////////

		// Design back button
		if (e.getSource() == designBack) {
			designWindow.dispose();
			
			new Game();
		}
		
		////////////////////////////////////////////////////////////////

		// Play button
		if (e.getSource() == playButton) {
			startWindow.dispose();
			Play();
		}
		
		////////////////////////////////////////////////////////////////

		// Reset button
		if (e.getSource() == resetButton) {
			history.append(langText.getString("upon_click") + langText.getString("button")
					+ langText.getString("reset")  + "\n");
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					buttons[i][j].setEnabled(true);
					buttons[i][j].setBackground(Color.WHITE);
				}
			}
		}

		////////////////////////////////////////////////////////////////

		// Solve button
		if (e.getSource() == solveButton) {
			history.append(langText.getString("upon_click") + langText.getString("button") 
					+ langText.getString("solve")  + "\n");
		}
		
		////////////////////////////////////////////////////////////////

		// New board button
		if (e.getSource() == newBoardButton) {
			history.append(langText.getString("upon_click") + langText.getString("button")
					+ langText.getString("newBoard") + "\n");
		}

		////////////////////////////////////////////////////////////////

		// Instructions button
		if (e.getSource() == instructionsButton) {
			history.append(langText.getString("upon_click") + langText.getString("button")
					+ langText.getString("instructions") + "\n");
			Instructions(currentLocale);
			instructionsButton.setEnabled(false);
		}

		////////////////////////////////////////////////////////////////

		// Instructions back button
		if (e.getSource() == instructionsBack) {
			history.append(langText.getString("upon_return") + " picross\n");

			instructionsWindow.dispose();
			instructionsButton.setEnabled(true);
		}

		////////////////////////////////////////////////////////////////

		// English radio button
		if (e.getSource() == engRadio) {
			if (gameMode == 1) {
				history.append("\n" + langText.getString("upon_lang_change") + langText.getString("english")  + "\n");

			}
			currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
			langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);

			updateText(currentLocale, langText);
			leftPanel.revalidate();
		}

		////////////////////////////////////////////////////////////////

		// French radio button
		if (e.getSource() == frRadio) {
			if (gameMode == 1) {
				history.append("\n" + langText.getString("upon_lang_change") + langText.getString("french") + "\n");
			}
			currentLocale = new Locale.Builder().setLanguage("fr").setRegion("FR").build();
			langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);

			updateText(currentLocale, langText);
			leftPanel.revalidate();


		}
		
		////////////////////////////////////////////////////////////////

		// Mark panel check box
		if (e.getSource() == markCheckBox) {
			
			// If the check box is marked
			if (markCheckBox.isSelected()) {				
				if (gameMode == 1) {
					history.append(langText.getString("mark") + ": " + langText.getString("true") + "\n");
				}
				markMode = true;
			}
			
			////////////////////////////////////////////////////////////////

			// If the check box is not marked
			else {
				if (gameMode == 1) {
					history.append(langText.getString("mark") + ": " + langText.getString("false") + "\n");
				}
				markMode = false;
			}
			
		}

		////////////////////////////////////////////////////////////////

		// Combo box options
		if (e.getSource() == gridSizeCmbo) {
			String options = (String) gridSizeCmbo.getSelectedItem();

			switch (options) {
			
			// "5x5" option
			case "5x5":
				gridSize = 5;
				if (gameMode == 1) {
					history.append(langText.getString("upon_grid_change") + " 5x5\n");
				}
				boardPanel.removeAll();
				boardPanel = makeBoardPanel(5);
				boardPanel.revalidate();
				
				break;

			// "6x6" option
			case "6x6":
				if (gameMode == 1) {
				history.append(langText.getString("upon_grid_change")  + " 6x6\n");
				}
				gridSize = 6;
				boardPanel.removeAll();
				boardPanel = makeBoardPanel(6);
				boardPanel.revalidate();

				break;

			// "7x7" option
			case "7x7":
				if (gameMode == 1) {
					history.append(langText.getString("upon_grid_change") + " 7x7\n");
				}
				gridSize = 7;
				boardPanel.removeAll();
				boardPanel = makeBoardPanel(7);
				boardPanel.revalidate();

				break;
			}
			
		////////////////////////////////////////////////////////////////

		} else {
			
			// Nested for-loop to handle the 2d-button array
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					
					// If the user clicks on a button with the mark mode disabled
					if (e.getSource() == buttons[i][j] && (!markMode)) {
						
						buttons[i][j].setEnabled(false);
						buttons[i][j].setBackground(new Color(17,15,15));
						if (gameMode == 1) {
						history.append(langText.getString("upon_click") + langText.getString("button") + i + ", "
								+ j + "\n"); 
						}
					}
					
					// If the user clicks on a button with the mark mode enabled
					else {
						if (e.getSource() == buttons[i][j]) {
							buttons[i][j].setBackground(new Color(226,222,222));
						}
					}
				}
			}
		}
	}
}
