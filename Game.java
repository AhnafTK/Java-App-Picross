import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Game {
	
	
	public Game() {
		
	}
	
	
	public static void main (String[] args) {
		JFrame picrossWindow = new JFrame();
		JButton button = new JButton();
		JCheckBox checkBox = new JCheckBox();
		JLabel label = new JLabel();
		//JLabel labCols = new JLabel();
		//JLabel labRows = new JLabel();
		//butGame[N][N]
		
		// RESET button for left panel.
		JButton resetButton = new JButton();
		resetButton.setText("RESET");
		JButton solveButton = new JButton();
		solveButton.setText("SOLVE");
		JButton newBoardButton = new JButton();
		newBoardButton.setText("NEW BOARD");
		JButton instructionsButton = new JButton();
		instructionsButton.setText("INSTRUCTIONS");
		//TODO: Make a "How to play" button that has a window popup of instructions
		
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.RED);
		titlePanel.setPreferredSize(new Dimension (1000, 125));
		//TODO: ImageIcon
		//TODO: Image
		
		
		JPanel markPanel = new JPanel();
		markPanel.setBackground(Color.BLACK);
		markPanel.setPreferredSize(new Dimension (100, 100));

		JPanel leftPanel = new JPanel();
		// temp
		JLabel leftPanelText = new JLabel();
		leftPanelText.setText("LEFT PANEL"); // temp
		leftPanel.add(leftPanelText);
		//
		
		// adding buttons to left panel
		leftPanel.add(resetButton);
		leftPanel.add(solveButton);
		leftPanel.add(newBoardButton);
		leftPanel.add(instructionsButton);
		/**
		 * TODO: add score
		 * 	   : add time
		 * 	   : Language section
		 */
		
		
		leftPanel.setBackground(Color.GRAY);
		leftPanel.setPreferredSize(new Dimension (250, 200));
		
		JPanel controlPanel = new JPanel();
		// temp
		JLabel controlPanelText = new JLabel();
		controlPanelText.setText("CONTROL PANEL"); // temp
		controlPanel.add(controlPanelText);
		//
		controlPanel.setBackground(Color.ORANGE);
		controlPanel.setPreferredSize(new Dimension (250, 200));
		
		JPanel boardPanel = new JPanel();
		boardPanel.setBackground(Color.GREEN);
		boardPanel.setLayout(new GridLayout(5,5));
		
		JButton[] buttons = new JButton[25]; // 25 will be the dim^2
		for(int i=0;i<25;i++) {
			buttons[i] = new JButton();
			buttons[i].setPreferredSize(new Dimension(50,50));
			boardPanel.add(buttons[i]);
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
		picrossWindow.setTitle("Bobo");
		picrossWindow.setSize(1000, 600);
		picrossWindow.setLocationRelativeTo(null);
	}
}

