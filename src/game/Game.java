package game;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
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
	JButton instructionsButton, instructionsBack;
	JFrame designWindow, instructionsWindow, picrossWindow;
	JComboBox gridSizeCmbo;
	int gridSize = 5;
	JButton designButton, designBack;
	JButton resetButton, solveButton, newBoardButton;
	JTextArea history;
	JButton[][] buttons;
	JRadioButton engRadio, frRadio;
	JRadioButton blYelRadio, whBlueRadio;
	JPanel leftPanel = new JPanel();
	JPanel boardPanel = new JPanel();
	JPanel designMenuReturnPanel;
	JCheckBox markCheckBox;
	boolean markMode = false;
	int gameMode = 0; // 0 is design, 1 is play
	
	Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
	ResourceBundle langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);

	/*
	 ********************************************************************
	 * JLabel initializations.											*
	 * The text that is in the label changes depending 					*
	 * on what language is selected.									*
	 ********************************************************************
	 */
	JLabel timerLabel = new JLabel(langText.getString("timer"));
	JLabel scoreLabel = new JLabel(langText.getString("score"));
	JLabel gridSizeLabel = new JLabel(langText.getString("gridSize"));
	JLabel langLabel = new JLabel();
	JLabel colourLabel = new JLabel();

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

	/**
	 ************************************************************************
	 * Make Left Panel.														*
	 * 																		*	
	 * This is the 'top-level' panel that stores everything for the left	*
	 * panel. All of the 'lower-level' components get added to this panel.	*
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
		JPanel languageButtonPanel = new JPanel();
		JPanel colourPanel = new JPanel();

		/*
		 ********************************************************************
		 * JButton initializations.											*
		 * The text that is in the button changes depending 				*
		 * on what language is selected.									*
		 ********************************************************************
		 */
		designButton = new JButton("Design");
		designButton.setBackground(Color.WHITE);

		////////////////////////////////////////////////////////////////

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

		

		/*
		 ********************************************************************
		 * Score panel components.											*
		 * This is also where the score counter text field is created.		*
		 ********************************************************************
		 */		
		JTextField scoreCounter = new JTextField();
		scoreCounter.setBorder(new LineBorder(Color.BLACK, 1));
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
		timerCounter.setBorder(new LineBorder(Color.BLACK,1));
		timerCounter.setPreferredSize(new Dimension(100, 25));
		timerCounter.setEditable(false);
		
		timerPanel.add(timerLabel);
		timerPanel.add(timerCounter);

		/*
		 ********************************************************************
		 * Grid size combo box panel.										*
		 * Combo box options are initialized in a string array.				*
		 * Grid size label changes depending on what language is selected.  *
		 ********************************************************************
		 */
		String options[] = { "5x5", "6x6", "7x7" };
		gridSizeCmbo = new JComboBox(options);
		gridSizeCmbo.addActionListener(this);
		gridSizeCmbo.setBackground(Color.WHITE);
		
		JPanel gridSizeComboPanel = new JPanel();
		gridSizeComboPanel.add(gridSizeLabel);
		gridSizeComboPanel.add(gridSizeCmbo);
		gridSizeComboPanel.setPreferredSize(new Dimension(200, 30));

		/*
		 ********************************************************************
		 * Buttons Panel													*
		 * 																	*
		 * This panel is used to store/align all of the JButtons that 		*
		 * will later be added to the left panel.							*
		 ********************************************************************
		 */
		//Vertically aligns the buttons in the panel
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0,10));
		buttonPanel.setPreferredSize(new Dimension(120,150));
		
		//Sets all of the buttons to the same size and creates an ActionListener
		designButton.setPreferredSize(new Dimension(120, 25));
		designButton.addActionListener(this);

		////////////////////////////////////////////////////////////////

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
		buttonPanel.add(designButton);
		buttonPanel.add(resetButton);
		buttonPanel.add(solveButton);
		buttonPanel.add(newBoardButton);
		buttonPanel.add(instructionsButton);
		
		/*
		 ********************************************************************
		 * Language Settings												*
		 * 																	*
		 * This is where all of the language panels, labels, radio buttons	*
		 * and button group are located.									*
		 * 																	*
		 * Whatever radio button is selected, will change the game to 		*
		 * that language. 													*
		 * "English" is selected by default when the game is started.		*
		 ********************************************************************
		 */
		// JLabel language text, dependent on language radio button selection
		JLabel langLabel = new JLabel();
		langLabel.setText(langText.getString("languages"));

		// Button group that holds the "English" and "French" radio buttons
		ButtonGroup langRButtons = new ButtonGroup();
		engRadio = new JRadioButton(langText.getString("english"), true);
		engRadio.addActionListener(this);
		
		////////////////////////////////////////////////////////////////

		frRadio = new JRadioButton(langText.getString("french"));
		frRadio.addActionListener(this);
		
		langRButtons.add(engRadio);
		langRButtons.add(frRadio);
		
		// Panel to align the language radio buttons
		languageButtonPanel.setLayout(new BoxLayout(languageButtonPanel, BoxLayout.Y_AXIS));
		//Adds the radio buttons to the panel
		languageButtonPanel.add(engRadio);
		languageButtonPanel.add(frRadio);	
		
		// Main language panel that stores/aligns all of the labels/radio buttons
		JPanel languagePanel = new JPanel();		
		languagePanel.add(langLabel);
		languagePanel.add(languageButtonPanel);

		/*
		 ********************************************************************
		 * Colour Settings													*
		 * 																	*
		 * This is where all of the colour panels, labels, radio buttons	*
		 * and button group are located.									*
		 * 																	*
		 * Whatever radio button is selected, will change the game to 		*
		 * that colour scheme. 												*
		 ********************************************************************
		 */		
		// JLabel colour text, dependent on language radio button selection
		JLabel colourLabel = new JLabel();
		colourLabel.setText(langText.getString("colorScheme"));
		
		// Button group that holds the Black/Yellow and White/Blue colour schemes
		ButtonGroup colourRButtons = new ButtonGroup();
		blYelRadio = new JRadioButton(langText.getString("blc_yel_colorScheme"));
		blYelRadio.addActionListener(this);
		
		////////////////////////////////////////////////////////////////
		
		whBlueRadio = new JRadioButton(langText.getString("white_blue_colorScheme"));
		whBlueRadio.addActionListener(this);
		
		colourRButtons.add(blYelRadio);
		colourRButtons.add(whBlueRadio);
		
		// Panel to align the colour radio buttons
		JPanel colorButtonPanel = new JPanel();
		colorButtonPanel.setLayout(new BoxLayout(colorButtonPanel, BoxLayout.Y_AXIS));
		//Adss the radio buttons to the panel
		colorButtonPanel.add(blYelRadio);
		colorButtonPanel.add(whBlueRadio);
		
		// Main colour panel that stores/aligns all of the labels/radio buttons
		colourPanel.add(colourLabel);
		colourPanel.add(colorButtonPanel);

		/*
		 ********************************************************************
		 * Configuration Settings											*
		 * 																	*
		 * This is 'top-level' panel that stores the language/colour panels	*
		 * in order for them to be propely layed out in the left panel.		*
		 ********************************************************************
		 */				
		JPanel configurationPanel = new JPanel();
		configurationPanel.setLayout(new GridLayout(1,2));
		configurationPanel.setPreferredSize(new Dimension(225,100));

		configurationPanel.add(languagePanel);
		configurationPanel.add(colourPanel);


		/*
		 ********************************************************************
		 * Left panel														*
		 * 																	*
		 * This is 'top-level' panel that stores all the 'lower level'		*
		 * panels in order for all the components to properly align.		*
		 ********************************************************************
		 */		
		leftPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,2, Color.black));
		leftPanel.setPreferredSize(new Dimension (250, 200));
		
		//Adds all of the 'lower level' panels 
		leftPanel.add(scorePanel);
		leftPanel.add(timerPanel);
		leftPanel.add(gridSizeComboPanel);
		leftPanel.add(buttonPanel);
		leftPanel.add(configurationPanel);	

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
		ImageIcon picrossLogo = new ImageIcon("picross.jpg");
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

		history.setPreferredSize(new Dimension(200, 10000));
		history.setBorder(new LineBorder(Color.BLACK));
		history.setEditable(false);

		////////////////////////////////////////////////////////////////

		// This is the scroll bar for the history panel 
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
	 ********************************************************************
	 */	
	private JPanel makeBoardPanel(int gridSize) {
		
		// Column panel
		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(gridSize, 1));
		colPanel.setPreferredSize(new Dimension(75, 0));
				
		for (int i = 0; i<gridSize; i++) {
			JLabel grid = new JLabel("0,0", SwingConstants.CENTER);
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
		rowPanel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.BLACK));
		
		for (int i = 0; i < gridSize; i++) {
			JLabel grid = new JLabel("0,0", SwingConstants.CENTER);
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
				//newGridButton.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
				buttons[i][j] = newGridButton;
				buttons[i][j].addActionListener(this);
				gridButtonPanel.add(buttons[i][j]);
			}
		}
		
		////////////////////////////////////////////////////////////////
		
		// This is only entered when the design button is pressed
		if (gameMode == 0) {
			designMenuReturnPanel = new JPanel();
			designMenuReturnPanel.setPreferredSize(new Dimension(500, 25));
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
	 ********************************************************************
	 */	
	private void updateText(Locale currentLocale, ResourceBundle langText) {
		
		timerLabel.setText((langText.getString("timer")));
		scoreLabel.setText(langText.getString("score"));
		gridSizeLabel.setText(langText.getString("gridSize"));
		resetButton.setText(langText.getString("reset"));
		instructionsButton.setText(langText.getString("instructions"));
		solveButton.setText(langText.getString("solve"));
		newBoardButton.setText(langText.getString("newBoard"));
		langLabel.setText(langText.getString("languages"));
		colourLabel.setText(langText.getString("colorScheme"));
		markCheckBox.setText(langText.getString("mark"));		
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
		 * Picross window frame												*
		 * 																	*
		 * This is where everything gets handled for the picross JFrame.	*
		 ********************************************************************
		 */	
		gameMode = 1;
		picrossWindow = new JFrame();

		picrossWindow.setLayout(new BorderLayout());
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
	 ********************************************************************
	 * Design method													*
	 * 																	*
	 * This shows the "default" board layout to the user.				*
	 ********************************************************************
	 */	
	private void Design() {
		gameMode = 0;
		designWindow = new JFrame();
		
		designWindow.setLayout(new BorderLayout());
		designWindow.add(makeBoardPanel(gridSize), BorderLayout.CENTER);
		
		////////////////////////////////////////////////////////////////

		designWindow.setResizable(false);
		designWindow.setVisible(true);
		designWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		designWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		designWindow.setSize(500, 500);
		designWindow.setLocationRelativeTo(null);
	}
	
	/**
	 ************************************************************************
	 * This is the instructions class that explains 						*
	 * how the game is supposed to be played.								*
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
	 ********************************************************************
	 */	
	@Override
	public void actionPerformed(ActionEvent e) {

		// Design button
		if (e.getSource() == designButton) {
			picrossWindow.dispose();
			Design();
		}

		////////////////////////////////////////////////////////////////

		// Design back button
		if (e.getSource() == designBack) {
			designWindow.dispose();
			new Game();
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
			history.append("\n" + langText.getString("upon_lang_change") + langText.getString("english")  + "\n");
			currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
			langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);

			updateText(currentLocale, langText);
			leftPanel.revalidate();
		}

		////////////////////////////////////////////////////////////////

		// French radio button
		if (e.getSource() == frRadio) {
			history.append("\n" + langText.getString("upon_lang_change") + langText.getString("french") + "\n");
			currentLocale = new Locale.Builder().setLanguage("fr").setRegion("FR").build();
			langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);

			updateText(currentLocale, langText);
			leftPanel.revalidate();
		}

		////////////////////////////////////////////////////////////////

		// White and blue radio button for the colour scheme
		if (e.getSource() == whBlueRadio) {
			history.append(langText.getString("upon_color_change") + langText.getString("white_blue_colorScheme") + "\n");
		}

		////////////////////////////////////////////////////////////////

		// Black and yellow radio button for the colour scheme
		if (e.getSource() == blYelRadio) {
			history.append(langText.getString("upon_color_change") + langText.getString("blc_yel_colorScheme") + "\n");
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
				history.append(langText.getString("upon_grid_change") + " 5x5\n");
				boardPanel.removeAll();
				boardPanel = makeBoardPanel(5);
				boardPanel.revalidate();
				
				break;

			// "6x6" option
			case "6x6":
				history.append(langText.getString("upon_grid_change")  + " 6x6\n");
				gridSize = 6;
				boardPanel.removeAll();
				boardPanel = makeBoardPanel(6);
				boardPanel.revalidate();

				break;

			// "7x7" option
			case "7x7":
				history.append(langText.getString("upon_grid_change") + " 7x7\n");
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
					
					// If the user is in the game window
					if (e.getSource() == buttons[i][j] && (!markMode)) {
						buttons[i][j].setEnabled(false);
						buttons[i][j].setBackground(Color.BLACK);
						if (gameMode == 1) {
						history.append(langText.getString("upon_click") + langText.getString("button") + i + ", "
								+ j + "\n"); 
						}
					}
					
					// If the user is in the design window
					else {
						if (e.getSource() == buttons[i][j]) {
							buttons[i][j].setBackground(Color.LIGHT_GRAY);
						}
					}
				}
			}
		}
	}
}
