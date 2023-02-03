import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

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
		JPanel leftPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		JPanel boardPanel = new JPanel();
		JPanel colourPanel = new JPanel();
		JPanel scorePanel = new JPanel();
		JPanel timerPanel = new JPanel();
		JPanel comboPanel = new JPanel();
		JPanel frPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		
		
		// JButton intializations
		buttons = new JButton[gridSize][gridSize]; // 25 will be the dim^2
		resetButton = new JButton("RESET");
		solveButton = new JButton("SOLVE");
		newBoardButton = new JButton("NEW BOARD");
		instructionsButton = new JButton("INSTRUCTIONS");
		
		// JLabel init
		JLabel timerLabel = new JLabel("TIMER:  ");
		JLabel scoreLabel = new JLabel("SCORE: ");
		
		ImageIcon picrossLogo = new ImageIcon("picross_logo.png");
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
		JLabel gridSizeLabel = new JLabel("Grid Size: ");
		gridSizeCmbo = new JComboBox(options);
		gridSizeCmbo.addActionListener(this);
		
		comboPanel.add(gridSizeLabel);
		comboPanel.add(gridSizeCmbo);
		
		JPanel configurationPanel = new JPanel();
		JPanel languagePanel = new JPanel();
		JPanel engPanel = new JPanel();
		JPanel blYelPanel = new JPanel();
		JPanel whBluePanel = new JPanel();

		JLabel langLabel = new JLabel();
		JLabel colourLabel = new JLabel();
		
		langButtons = new ButtonGroup();
		
		engRadio = new JRadioButton("English", true);
		engRadio.addActionListener(this);
		frRadio = new JRadioButton("French ");
		frRadio.addActionListener(this);
		blYelRadio = new JRadioButton("Black/Yellow");
		blYelRadio.addActionListener(this);
		whBlueRadio = new JRadioButton("White/Blue    ");
		whBlueRadio.addActionListener(this);

		configurationPanel.setLayout(new GridLayout(1,2));
		
		languagePanel.setLayout(new GridLayout(0,1));
		langLabel.setText("Languages");
		
		engPanel.add(engRadio);
				
		frPanel.add(frRadio);

		langButtons.add(engRadio);
		langButtons.add(frRadio);
		
		languagePanel.add(langLabel);
		languagePanel.add(engPanel);
		languagePanel.add(frPanel);

		colourPanel.setLayout(new GridLayout(3,1));
		colourLabel.setText("Colour Scheme");
		
		ButtonGroup colour = new ButtonGroup();
		colour.add(blYelRadio);
		colour.add(whBlueRadio);
		blYelPanel.add(blYelRadio);
		whBluePanel.add(whBlueRadio);

		colourPanel.add(colourLabel);
		colourPanel.add(blYelPanel);
		colourPanel.add(whBluePanel);
		
		buttonPanel.setPreferredSize(new Dimension(120,150));
		
//		configurationPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//configurationPanel.setLayout(new GridLayout());
		configurationPanel.add(languagePanel);
		configurationPanel.add(colourPanel);

		//buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		//leftPanel.setLayout(new GridLayout(7,1));
		// adding buttons to left panel
		//buttonPanel.add(buttonPanel);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0,10));
		//buttonPanel.add(scorePanel);
		//buttonPanel.add(timerPanel);
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
		leftPanel.add(comboPanel);
		leftPanel.add(buttonPanel);
		leftPanel.add(configurationPanel);	
		
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		leftPanel.setPreferredSize(new Dimension (250, 200));
		
		controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		controlPanel.setPreferredSize(new Dimension(250, 200));
		
		JPanel historyPanel = new JPanel();
		history = new JTextArea();
		history.setLineWrap(true);
		history.setWrapStyleWord(true);
	
		history.setPreferredSize(new Dimension(200, 10000)); // important
		history.setBorder(new LineBorder(Color.BLACK));
		history.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(history);
		scroll.setPreferredSize(new Dimension(200,300));
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//history.add(scroll);
		
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
		rowPanel.setBackground(Color.blue);
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
		JLabel instructionsLabel = new JLabel();
		backButton = new JButton("Back");
		
//		instructionsLabel.setText(printInstructions);
		instructionsLabel.setBounds(20, -90, 500, 500);
		instructionsLabel.setFont(new Font("Calibri Regular",Font.PLAIN,18)); 
		
		
		backButton.setFont(new Font("Calibri Regular",Font.BOLD,12)); 
		backButton.setBounds(230, 375, 75, 35);
		backButton.setFocusable(false);
		backButton.addActionListener(this);
		
		instructionsWindow.setTitle("Instructions");
		instructionsWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		instructionsWindow.setResizable(false);
		instructionsWindow.setSize(560,500);
		instructionsWindow.setVisible(true);
		instructionsWindow.setLocationRelativeTo(null);
		
		instructionsWindow.add(instructionsLabel);
		instructionsWindow.add(backButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==resetButton) {
			history.append("\nYou clicked the reset button\n");
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					buttons[i][j].setEnabled(true);
					buttons[i][j].setBackground(null);
				}
			}
			
		}
		
		if(e.getSource()==solveButton) {
			history.append("\nYou clicked the solve button\n");
		}

		if(e.getSource()==newBoardButton) {
			history.append("\nYou clicked the new board button\n");
		}
				
		if(e.getSource()==instructionsButton) {
			history.append("\nYou clicked the instructions button\n");
			Instructions();
		}
		
		if(e.getSource()==backButton) {
			history.append("\nYou returned back to the picross game\n");
			instructionsWindow.dispose();
		}
		
		if(e.getSource()==engRadio) {
			history.append("\nYou changed the language to English\n");
		}
		
		if(e.getSource()==frRadio) {
			history.append("\nYou changed the language to French\n");
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
						history.append("\nYou clicked button: " + i + ", " + j + "\n");
					}
				}
			}
		}
	}
}

