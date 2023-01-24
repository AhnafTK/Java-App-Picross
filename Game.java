import java.awt.Color;
import java.awt.GridLayout;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		
		//TODO: Make a "How to play" button that has a window popup of instructions
		GridLayout gridLayout = new GridLayout(3,3);
		picrossWindow.setLayout(gridLayout);
		
		//Board Panel
		JPanel boardPanel = new JPanel();
		boardPanel.setBackground(Color.RED);
		
		//Mark Panel
		JPanel markPanel = new JPanel();
		markPanel.setBackground(Color.BLACK);

		//Top Panel
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.GREEN);
		
		//Title Panel
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(Color.ORANGE);
		
		//Left Panel
		JPanel leftPanel = new JPanel();
		leftPanel.setBackground(Color.BLUE);
		
		//Control Panel
		JPanel controlPanel = new JPanel();

		picrossWindow.add(boardPanel);
		picrossWindow.add(markPanel);
		picrossWindow.add(topPanel);
		picrossWindow.add(titlePanel);
		picrossWindow.add(leftPanel);
		picrossWindow.add(controlPanel);
		picrossWindow.pack();
		
		//frame.setLayout(new GridLayout(5, 5));
		picrossWindow.setResizable(false);
		picrossWindow.setVisible(true);
		picrossWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		picrossWindow.setTitle("Bobo");
		picrossWindow.setSize(800, 600);
		picrossWindow.setLocationRelativeTo(null);
	}
}

