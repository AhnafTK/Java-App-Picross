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
public class Game extends JFrame implements ActionListener{
	JButton instructionsButton;
	JButton backButton;
	JFrame instructionsWindow;
	JFrame picrossWindow;
	JComboBox gridSizeCmbo;
	int gridSize = 5;
	ButtonGroup langRButtons;
	String language = "English";
	JButton resetButton;
	JTextArea history;
	JButton solveButton;
	JButton newBoardButton;
	JButton[][] buttons;
	JRadioButton engRadio;
	JRadioButton frRadio;
	JRadioButton blYelRadio;
	JRadioButton whBlueRadio;
	JPanel leftPanel = new JPanel();

	Locale currentLocale = new Locale.Builder().setLanguage("fr").setRegion("FR").build();
	ResourceBundle langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);
	
	
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
	 * Default constructor for the Game class								*
	 * This is where all of the GUI of the game gets handled.				*
	 ************************************************************************
	 */
	public Game() {
		//Creates a new JFrame for the game
		picrossWindow = new JFrame();
		
		// JPanel initializations
		JPanel titlePanel = new JPanel();
		JPanel markPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		JPanel boardPanel = new JPanel();
		JPanel colourPanel = new JPanel();
		JPanel scorePanel = new JPanel();
		JPanel timerPanel = new JPanel();
		JPanel gridSizeComboPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		
		/*
		 ********************************************************************
		 * JButton initializations.											*
		 * The text that is in the button changes depending 				*
		 * on what language is selected.									*
		 ********************************************************************/
		buttons = new JButton[gridSize][gridSize]; 
		resetButton = new JButton(langText.getString("reset"));
		solveButton = new JButton(langText.getString("solve"));
		newBoardButton = new JButton(langText.getString("newBoard"));
		instructionsButton = new JButton(langText.getString("instructions"));
		
		/*
		 ********************************************************************
		 * JLabel initializations.											*
		 * The text that is in the label changes depending 					*
		 * on what language is selected.									*
		 ********************************************************************/
		JLabel timerLabel = new JLabel(langText.getString("timer"));
		JLabel scoreLabel = new JLabel(langText.getString("score"));
		
		/*
		 ********************************************************************
		 * Title panel settings.											*
		 * This is also where the ImageIcon for the 						*
		 * Picross logo is created/set.										*
		 ********************************************************************/
		ImageIcon picrossLogo = new ImageIcon("picross_logo.png");
		JLabel picrossLabel = new JLabel();
		picrossLabel.setIcon(picrossLogo);
		titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		titlePanel.setPreferredSize(new Dimension (1000, 125));
		
		titlePanel.add(picrossLabel);
		
		/*
		 ********************************************************************
		 * Score panel components.											*
		 * This is also where the score counter text field is created.		*
		 ********************************************************************/
		JTextField scoreCounter = new JTextField();
		scoreCounter.setBorder(new LineBorder(Color.BLACK,1));
		scoreCounter.setPreferredSize(new Dimension(100, 25));
		scoreCounter.setEditable(false);
		
		scorePanel.add(scoreLabel);
		scorePanel.add(scoreCounter);

		/*
		 ********************************************************************
		 * Timer panel components.											*
		 * This is also where the timer counter text field is created.		*
		 ********************************************************************/
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
		String options[] = {"5x5", "6x6", "7x7"};
		JLabel gridSizeLabel = new JLabel(langText.getString("gridSize"));
		gridSizeCmbo = new JComboBox(options);
		gridSizeCmbo.addActionListener(this);
		gridSizeComboPanel.add(gridSizeLabel);
		gridSizeComboPanel.add(gridSizeCmbo);
		
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
		resetButton.setPreferredSize(new Dimension(120,25));
		resetButton.addActionListener(this);

		////////////////////////////////////////////////////////////////

		solveButton.setPreferredSize(new Dimension(120,25));
		solveButton.addActionListener(this);

		////////////////////////////////////////////////////////////////

		newBoardButton.setPreferredSize(new Dimension(120,25));
		newBoardButton.addActionListener(this);
		
		////////////////////////////////////////////////////////////////

		instructionsButton.setPreferredSize(new Dimension(120,25));
		instructionsButton.addActionListener(this);
		
		//Adds all of the buttons to the panel
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
		langRButtons = new ButtonGroup();
		engRadio = new JRadioButton(langText.getString("english"), true);
		engRadio.addActionListener(this);
		
		////////////////////////////////////////////////////////////////

		frRadio = new JRadioButton(langText.getString("french"));
		frRadio.addActionListener(this);
		
		langRButtons.add(engRadio);
		langRButtons.add(frRadio);
		
		// Panel to align the language radio buttons
		JPanel languageButtonPanel = new JPanel();
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
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		leftPanel.setPreferredSize(new Dimension (250, 200));
		
		//Adds all of the 'lower level' panels 
		leftPanel.add(scorePanel);
		leftPanel.add(timerPanel);
		leftPanel.add(gridSizeComboPanel);
		leftPanel.add(buttonPanel);
		leftPanel.add(configurationPanel);	
		
		/*
		 ********************************************************************
		 * Control panel													*
		 * 																	*
		 * This is 'top-level' panel that stores all the 'lower level'		*
		 * components.														*
		 ********************************************************************
		 */		
		controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		controlPanel.setPreferredSize(new Dimension(250, 200));
		
		////////////////////////////////////////////////////////////////
		
		// This is the history text area where all of the user inputs will be recorded
		history = new JTextArea();
		history.setPreferredSize(new Dimension(200, 10000)); // important
		history.setBorder(new LineBorder(Color.BLACK));
		history.setEditable(false);
		
		////////////////////////////////////////////////////////////////
		
		// This is the scroll bar for the history panel 
		JScrollPane scroll = new JScrollPane(history);
		scroll.setPreferredSize(new Dimension(200,300));
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		////////////////////////////////////////////////////////////////
		
//		I don't think we need this.
		
//		JPanel historyPanel = new JPanel();
//		historyPanel.add(scroll);
//		controlPanel.add(historyPanel);
		
		// Adds the scroll bar to the panel
		controlPanel.add(scroll);

		
		boardPanel.setLayout(new BorderLayout());
		boardPanel.setBackground(Color.red);
		
		markPanel.setBackground(Color.gray);
		boardPanel.add(markPanel, BorderLayout.NORTH);
		markPanel.setPreferredSize(new Dimension(10,10));
		
		JPanel colPanel = new JPanel();
		colPanel.setPreferredSize(new Dimension(75,0));
		colPanel.setBackground(Color.green);

		JPanel rowPanel = new JPanel();
		rowPanel.setPreferredSize(new Dimension(0,75));
		rowPanel.setBackground(Color.red);
		boardPanel.add(rowPanel, BorderLayout.NORTH);
		boardPanel.add(colPanel,  BorderLayout.WEST);
		
		JPanel gridButtonPanel = new JPanel();
		gridButtonPanel.setLayout(new GridLayout(gridSize, gridSize));

		for(int i=0; i<gridSize; i++) {
			for(int j=0; j<gridSize; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].addActionListener(this);
				gridButtonPanel.add(buttons[i][j]);
			}
		}
		boardPanel.add(gridButtonPanel, BorderLayout.CENTER);
		/**
		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(1,6));
		markPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		colPanel.add(markPanel);
		JPanel[] columns = new JPanel[gridSize]; 
		for(int i=0; i<gridSize; i++) {
			columns[i] = new JPanel();
			columns[i].setBorder(BorderFactory.createLineBorder(Color.black));
			colPanel.add(columns[i]);
		}
		
//		colPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		colPanel.setPreferredSize(new Dimension (600, 75));
		boardPanel.add(colPanel, BorderLayout.NORTH);
		**/
		/**
		JPanel rowPanel = new JPanel();
		rowPanel.setLayout(new GridLayout(5,1));
		JPanel[] rows = new JPanel[gridSize]; 
		for(int i=0; i<gridSize; i++) {
			rows[i] = new JPanel();
			rows[i].setBorder(BorderFactory.createLineBorder(Color.black));
			rowPanel.add(rows[i]);
		}
//		rowPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		rowPanel.setPreferredSize(new Dimension (80, 600));
		boardPanel.add(rowPanel, BorderLayout.WEST);
		
		**/
		//JPanel gridPanel = new JPanel();
		//boardPanel.add(gridPanel, BorderLayout.CENTER);
		
		//gridPanel.setLayout(new GridLayout(gridSize, gridSize));
		/**
		for(int i=0; i<gridSize; i++) {
			for(int j=0; j<gridSize; j++) {
				buttons[i][j] = new JButton();
				buttons[i][j].addActionListener(this);
				//gridPanel.add(buttons[i][j]);
			}
		}
		**/
		//boardPanel.setBackground(Color.green);
		//gridPanel.setBackground(Color.green);
		picrossWindow.setLayout(new BorderLayout());
		picrossWindow.add(titlePanel, BorderLayout.NORTH);
		picrossWindow.add(leftPanel, BorderLayout.WEST);
		picrossWindow.add(controlPanel, BorderLayout.EAST);
		picrossWindow.add(boardPanel, BorderLayout.CENTER);
		picrossWindow.pack();
		
		picrossWindow.setResizable(false);
		picrossWindow.setVisible(true);
		picrossWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		picrossWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		picrossWindow.setSize(1000, 600);
		picrossWindow.setLocationRelativeTo(null);
			
	}

	public void Instructions() {
        instructionsWindow = new JFrame();
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setPreferredSize(new Dimension(500, 400));
        JLabel instructionsLabel = new JLabel();
        backButton = new JButton("Back");
        
        /**
        String printInstructions = "<html><br/>There is are multiple rows and columns that are adjacent<br/>"
                                   + "to the grid of buttons, these will have numbers that will indicate<br/>"
                                   + "how many correct tiles are in that row/column.<br/><br/>"
                                 + "The buttons in the grid can either be clicked or marked as empty,<br/>"
                                 + "they will be highlighted to indicate right or wrong. <br/><br/>"
                                 + "Once a button is clicked, the timer will start and the score<br/>"
                                 + "will be initialized to 0. When all of the correct tiles are placed,<br/>"
                                 + "the score goes up and the timer resets.<br/><br/>"
                                 + "At any time you can solve the grid, reset the board or generate<br/>"
                                 + "a new board that has a random pattern.<br/><br/></html>";
        **/
        String printInstructions = langText.getString("instructions_text");
        instructionsLabel.setText(printInstructions);
        instructionsLabel.setFont(new Font("Calibri Regular",Font.PLAIN,16)); 
        
        backButton.setFont(new Font("Calibri Regular",Font.BOLD,12)); 
        backButton.addActionListener(this);
        
        instructionsPanel.add(instructionsLabel);
        instructionsPanel.add(backButton);
        instructionsWindow.add(instructionsPanel);
        
        instructionsWindow.setTitle("Instructions - Skylar Phanenhour, Ahnaf Kamal");
        instructionsWindow.setResizable(false);
        instructionsWindow.setSize(525, 425);
        instructionsWindow.setVisible(true);
        instructionsWindow.setLocationRelativeTo(null);
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==resetButton) {
			history.append("\n" + langText.getString("upon_click") + langText.getString("button") + langText.getString("reset"));
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					buttons[i][j].setEnabled(true);
					buttons[i][j].setBackground(null);
				}
			}
			
		}
		
		if(e.getSource()==solveButton) {
			//history.append("\nYou clicked the solve button\n");
			history.append("\n"+ langText.getString("upon_click") + langText.getString("button")+langText.getString("solve") );

		}

		if(e.getSource()==newBoardButton) {
			//history.append("\nYou clicked the new board button\n");
			history.append("\n" + langText.getString("upon_click") + langText.getString("button")+langText.getString("newBoard"));
		}
				
		if(e.getSource()==instructionsButton) {
			history.append("\n" + langText.getString("upon_click") + langText.getString("button")+langText.getString("instructions"));
			Instructions();
			instructionsButton.setEnabled(false);
		}
		
		if(e.getSource()==backButton) {
			history.append("\nYou returned back to the picross game\n");
			instructionsWindow.dispose();
			instructionsButton.setEnabled(true);

		}
		
		if(e.getSource()==engRadio) {
			//history.append("\nYou changed the language to English\n");
			history.append("\n" + langText.getString("upon_lang_change") + langText.getString("english"));

		}
		
		if(e.getSource()==frRadio) {
			history.append("\n" + langText.getString("upon_lang_change") + langText.getString("french"));
			Locale currentLocale = new Locale.Builder().setLanguage("fr").setRegion("FR").build();
			langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);
			leftPanel.remove(resetButton);
			leftPanel.revalidate();
			leftPanel.repaint();
		}
		
		if(e.getSource()==whBlueRadio) {
			history.append("\nYou changed the game colour to white & blue\n");
		}
		
		if(e.getSource()==blYelRadio) {
			history.append("\nYou changed the game colour to black & yellow\n");
		}
		
		if(e.getSource()==gridSizeCmbo) {
			String options = (String) gridSizeCmbo.getSelectedItem();
	
			switch (options) {
				case "5x5":
					history.append("\nYou changed the grid to 5x5\n");
					//gridSize = 5;
					break;
					
				case "6x6":
					history.append("\nYou changed the grid to 6x6\n");
					//gridSize = 6;
					break;
					
				case "7x7":
					history.append("\nYou changed the grid to 7x7\n");
					//gridSize = 7;
					break;
			}
		}
		else {
			for(int i=0; i<gridSize; i++) {
				for(int j=0; j<gridSize; j++) {
					if(e.getSource()==buttons[i][j]) {
						buttons[i][j].setEnabled(false);
						buttons[i][j].setBackground(Color.red);
						history.append("\n"+ langText.getString("upon_click")+ langText.getString("button") + i + ", " + j + "\n");
					}
				}
			}
		}
	}
}

