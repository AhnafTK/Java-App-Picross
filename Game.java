import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Game {
	
	
	public Game() {
		
	}
	
	//TODO: Use a layout for the components of the left panel
	
	public static void main (String[] args) {
		
		// JPanel intializations
		JPanel titlePanel = new JPanel();
		JPanel languagePanel = new JPanel();
		JPanel markPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		JPanel boardPanel = new JPanel();


		// JButton intializations
		JButton[] buttons = new JButton[25]; // 25 will be the dim^2
		JButton resetButton = new JButton();
		JButton solveButton = new JButton();
		JButton newBoardButton = new JButton();
		JButton instructionsButton = new JButton();
		
		// JButton config
		resetButton.setText("RESET");
		solveButton.setText("SOLVE");
		newBoardButton.setText("NEW BOARD");
		instructionsButton.setText("INSTRUCTIONS");

		// JLabel init
		JLabel timerText = new JLabel();
		JLabel scoreText = new JLabel();

		// JLabel config
		scoreText.setText("SCORE: ");
		timerText.setText("TIMER: ");
		
		JFrame picrossWindow = new JFrame();
		JButton button = new JButton();
		JCheckBox checkBox = new JCheckBox();
		
			
		//JLabel labCols = new JLabel();
		//JLabel labRows = new JLabel();
		//butGame[N][N]
		
		// RESET button for left panel.

		//TODO: Make a "How to play" button that has a window popup of instructions
		
		
		titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		titlePanel.setPreferredSize(new Dimension (1000, 125));
		
		languagePanel.setSize(new Dimension(100,100));
		languagePanel.setBackground(Color.GREEN);
		//TODO: ImageIcon
		//TODO: Image
		
	
		markPanel.setBackground(Color.BLACK);
		markPanel.setPreferredSize(new Dimension (100, 100));

		// temp
		JLabel leftPanelText = new JLabel();
		leftPanelText.setText("LEFT PANEL"); // temp
		leftPanel.add(leftPanelText);
		//
		
		
		
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
		
		// adding buttons to left panel
		leftPanel.add(scoreText);
		leftPanel.add(scoreCounter);
		leftPanel.add(timerText);
		leftPanel.add(timerCounter);
		leftPanel.add(resetButton);
		leftPanel.add(solveButton);
		leftPanel.add(newBoardButton);
		leftPanel.add(instructionsButton);
		leftPanel.add(languagePanel);
		/**
		 * TODO: add score
		 * 	   : add time
		 * 	   : Language section
		 */
		
		
		leftPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		leftPanel.setPreferredSize(new Dimension (250, 200));
		
		// temp
		JLabel controlPanelText = new JLabel();
		controlPanelText.setText("CONTROL PANEL"); // temp
		controlPanel.add(controlPanelText);
		//
		controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		controlPanel.setPreferredSize(new Dimension (250, 200));
		
		boardPanel.setBackground(Color.GREEN);
		
		boardPanel.setLayout(new BorderLayout());
		JPanel colPanel = new JPanel();
		colPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		colPanel.setPreferredSize(new Dimension (600, 75));
		boardPanel.add(colPanel, BorderLayout.NORTH);
		
		JPanel rowPanel = new JPanel();
		rowPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		rowPanel.setPreferredSize(new Dimension (75, 600));
		boardPanel.add(rowPanel, BorderLayout.WEST);
		
		
		JPanel gridPanel = new JPanel();
		boardPanel.add(gridPanel, BorderLayout.CENTER);
		
		
		gridPanel.setLayout(new GridLayout(5,5));
		
		for(int i=0;i<25;i++) {
			buttons[i] = new JButton();
			buttons[i].setPreferredSize(new Dimension(50,50));
			gridPanel.add(buttons[i]);
			//buttons[i].setFont(new Font("MV Boli",Font.BOLD,120));

			buttons[i].setFocusable(false);
			
			//buttons[i].addActionListener(this);
			
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
}

