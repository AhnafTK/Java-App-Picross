package game;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Game extends JFrame implements ActionListener{
	JButton instructionsButton;
	JButton backButton;
	JFrame instructionsWindow;
	JFrame picrossWindow;
	JComboBox gridSizeCmbo;
	int gridSize = 5;
	ButtonGroup langButtons;
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
	public static void main(String[] args) {
		new Game();
	
	}
	
	public Game() {
		picrossWindow = new JFrame();

		//TODO: Use a layout for the components of the left panel
		JButton button = new JButton();
		JCheckBox checkBox = new JCheckBox();
		
		// JPanel intializations
		JPanel titlePanel = new JPanel();
		JPanel markPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		JPanel boardPanel = new JPanel();
		JPanel colourPanel = new JPanel();
		JPanel scorePanel = new JPanel();
		JPanel timerPanel = new JPanel();
		JPanel gridSizeComboPanel = new JPanel();
		JPanel frPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		
		// JButton intializations
		buttons = new JButton[gridSize][gridSize]; // 25 will be the dim^2
		resetButton = new JButton(langText.getString("reset"));
		solveButton = new JButton(langText.getString("solve"));
		newBoardButton = new JButton(langText.getString("newBoard"));
		instructionsButton = new JButton(langText.getString("instructions"));
		
		// JLabel init
		JLabel timerLabel = new JLabel(langText.getString("timer"));
		JLabel scoreLabel = new JLabel(langText.getString("score"));
		
		ImageIcon picrossLogo = new ImageIcon("./src/picross_logo.png");
		JLabel picrossLabel = new JLabel();
		picrossLabel.setIcon(picrossLogo);
		titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		titlePanel.setPreferredSize(new Dimension (1000, 125));
		titlePanel.add(picrossLabel);
		
		// This is the text field for the score counter needs to be organized.
		JTextField scoreCounter = new JTextField();
		scoreCounter.setBorder(new LineBorder(Color.BLACK,1));
		scoreCounter.setPreferredSize(new Dimension(100, 25));
		scoreCounter.setEditable(false);
		//
		
		// This is the text field for the score counter needs to be organized.
		JTextField timerCounter = new JTextField();
		timerCounter.setBorder(new LineBorder(Color.BLACK,1));
		timerCounter.setPreferredSize(new Dimension(100, 25));
		timerCounter.setEditable(false);
		//
		
		//Score Panel
		
		scorePanel.add(scoreLabel);
		scorePanel.add(scoreCounter);
		
		//timer Panel
		timerPanel.add(timerLabel);
		timerPanel.add(timerCounter);
		
		String options[] = {"5x5", "6x6", "7x7"};
		JLabel gridSizeLabel = new JLabel(langText.getString("gridSize"));
		gridSizeCmbo = new JComboBox(options);
		gridSizeCmbo.addActionListener(this);
		
		gridSizeComboPanel.add(gridSizeLabel);
		gridSizeComboPanel.add(gridSizeCmbo);
		
		JPanel configurationPanel = new JPanel();
		JPanel languagePanel = new JPanel();

		JLabel langLabel = new JLabel();
		JLabel colourLabel = new JLabel();
				
		engRadio = new JRadioButton(langText.getString("english"), true);
		engRadio.addActionListener(this);
		
		frRadio = new JRadioButton(langText.getString("french"));
		frRadio.addActionListener(this);
		
		blYelRadio = new JRadioButton(langText.getString("blc_yel_colorScheme"));
		blYelRadio.addActionListener(this);
		whBlueRadio = new JRadioButton(langText.getString("white_blue_colorScheme"));
		whBlueRadio.addActionListener(this);

		configurationPanel.setLayout(new GridLayout(1,2));
		
		langButtons = new ButtonGroup();
		langButtons.add(engRadio);
		langButtons.add(frRadio);
		
		langLabel.setText(langText.getString("languages"));
		
	
		JPanel languageButtonPanel = new JPanel();
		languageButtonPanel.setLayout(new BoxLayout(languageButtonPanel, BoxLayout.Y_AXIS));
		languageButtonPanel.add(engRadio);
		languageButtonPanel.add(frRadio);	
		
		languagePanel.add(langLabel);
		languagePanel.add(languageButtonPanel);
	
		////////////////////////////////////////////////////////////////////////
		
		colourLabel.setText(langText.getString("colorScheme"));
		
		ButtonGroup colour = new ButtonGroup();
		colour.add(blYelRadio);
		colour.add(whBlueRadio);
		
		// the jpanel that holds the radio buttons
		JPanel colorButtonPanel = new JPanel();
		colorButtonPanel.setLayout(new BoxLayout(colorButtonPanel, BoxLayout.Y_AXIS));
		colorButtonPanel.add(blYelRadio);
		colorButtonPanel.add(whBlueRadio);
		
		// add the label to the top level panel
		colourPanel.add(colourLabel);
		// add the buttons to the top level panel
		colourPanel.add(colorButtonPanel);
		
		//////////////////////////////////////////////////////////////////

		configurationPanel.add(languagePanel);
		configurationPanel.add(colourPanel);
		configurationPanel.setPreferredSize(new Dimension(225,100));
		
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0,10));
		buttonPanel.setPreferredSize(new Dimension(120,150));

		resetButton.setPreferredSize(new Dimension(120,25));
		resetButton.addActionListener(this);
		buttonPanel.add(resetButton);
		solveButton.setPreferredSize(new Dimension(120,25));
		solveButton.addActionListener(this);
		buttonPanel.add(solveButton);
		newBoardButton.setPreferredSize(new Dimension(120,25));
		newBoardButton.addActionListener(this);
		buttonPanel.add(newBoardButton);
		instructionsButton.setPreferredSize(new Dimension(120,25));
		instructionsButton.addActionListener(this);
		buttonPanel.add(instructionsButton);
		leftPanel.add(scorePanel);
		leftPanel.add(timerPanel);
		leftPanel.add(gridSizeComboPanel);
		leftPanel.add(buttonPanel);
		leftPanel.add(configurationPanel);	
		
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		leftPanel.setPreferredSize(new Dimension (250, 200));
		
		controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		controlPanel.setPreferredSize(new Dimension(250, 200));
		
		///////////////////////////////////////////////////////////////
		
		JPanel historyPanel = new JPanel();
		history = new JTextArea();
		history.setLineWrap(true);
		history.setWrapStyleWord(true);
	
		history.setPreferredSize(new Dimension(200, 10000)); // important
		history.setBorder(new LineBorder(Color.BLACK));
		history.setEditable(false);
		
		////////////////////////////////////////////////////////////////
		
		JScrollPane scroll = new JScrollPane(history);
		scroll.setPreferredSize(new Dimension(200,300));
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		////////////////////////////////////////////////////////////////
		
		historyPanel.add(scroll);
		controlPanel.add(historyPanel);
		
		
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

