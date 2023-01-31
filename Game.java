import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Game extends JFrame implements ActionListener{
	JButton instructionsButton;
	JButton backButton;
	JFrame instructionsWindow;
	JFrame picrossWindow;
	JComboBox gridSizeCmbo;
	int gridSize = 5;
	
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
		JButton[][] buttons = new JButton[gridSize][gridSize]; // 25 will be the dim^2
		JButton resetButton = new JButton("RESET");
		JButton solveButton = new JButton("SOLVE");
		JButton newBoardButton = new JButton("NEW BOARD");
		instructionsButton = new JButton("INSTRUCTIONS");
		
		// JLabel init
		JLabel timerLabel = new JLabel("TIMER: ");
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
		
		ButtonGroup langButtons = new ButtonGroup();
		
		JRadioButton engRadio = new JRadioButton("English");
		JRadioButton frRadio = new JRadioButton("French");
		JRadioButton blYelRadio = new JRadioButton("Black/Yellow");
		JRadioButton whBlueRadio = new JRadioButton("White/Blue");


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
		buttonPanel.add(resetButton);
		solveButton.setPreferredSize(new Dimension(120,25));
		buttonPanel.add(solveButton);
		newBoardButton.setPreferredSize(new Dimension(120,25));
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
		JTextField history = new JTextField();
		history.setPreferredSize(new Dimension(200, 300));
		history.setBorder(new LineBorder(Color.BLACK,1));
		history.setEditable(false);
		controlPanel.add(history);
		
		boardPanel.setBackground(Color.GREEN);
		
		boardPanel.setLayout(new BorderLayout());
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
		
		colPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		colPanel.setPreferredSize(new Dimension (600, 75));
		boardPanel.add(colPanel, BorderLayout.NORTH);
		
		JPanel rowPanel = new JPanel();
		rowPanel.setLayout(new GridLayout(5,1));
		JPanel[] rows = new JPanel[gridSize]; 
		for(int i=0; i<gridSize; i++) {
			rows[i] = new JPanel();
			rows[i].setBorder(BorderFactory.createLineBorder(Color.black));
			rowPanel.add(rows[i]);
		}
		rowPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		rowPanel.setPreferredSize(new Dimension (75, 600));
		boardPanel.add(rowPanel, BorderLayout.WEST);
		
		
		JPanel gridPanel = new JPanel();
		boardPanel.add(gridPanel, BorderLayout.CENTER);
		
		
		gridPanel.setLayout(new GridLayout(gridSize, gridSize));
		
		for(int i=0; i<gridSize; i++) {
			for(int j=0; j<gridSize; j++) {
				buttons[i][j] = new JButton();
				gridPanel.add(buttons[i][j]);
			}
		}
		
		picrossWindow.setLayout(new BorderLayout());
		picrossWindow.add(titlePanel, BorderLayout.NORTH);
		picrossWindow.add(leftPanel, BorderLayout.WEST);
		picrossWindow.add(controlPanel, BorderLayout.EAST);
		picrossWindow.add(boardPanel, BorderLayout.CENTER);
		picrossWindow.pack();
		
		picrossWindow.setResizable(false);
		picrossWindow.setVisible(true);
		picrossWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		picrossWindow.setTitle("Picross");
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
		
		if(e.getSource()==instructionsButton) {
			picrossWindow.dispose();
			Instructions();
		}
		
		if(e.getSource()==backButton) {
			instructionsWindow.dispose();
			new Game();
		}
		
		if(e.getSource()==gridSizeCmbo) {
			String options = (String) gridSizeCmbo.getSelectedItem();
	
			switch (options) {
				case "5x5":
					gridSize = 5;
					break;
					
				case "6x6":
					gridSize = 6;
					break;
					
				case "7x7":
					gridSize = 7;
					break;
			}
		}
	}
}

