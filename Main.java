import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	
	public static void main (String[] args) {
		JFrame frame = new JFrame();
		JButton button = new JButton();
		JCheckBox checkBox = new JCheckBox();
		JLabel label = new JLabel();
		
		
		//Board Panel
		JPanel boardPanel = new JPanel();
		boardPanel.setBounds(100,100,500,400);
		boardPanel.setBackground(Color.RED);
		
		//Mark Panel
		JPanel markPanel = new JPanel();
		markPanel.setBounds(0,0,100,100);
		markPanel.setBackground(Color.BLACK);

		//Top Panel
		JPanel topPanel = new JPanel();
		topPanel.setBounds(100,0,500,100);
		topPanel.setBackground(Color.GREEN);
		
		//Left Panel
		JPanel leftPanel = new JPanel();
		leftPanel.setBounds(0,100,100,400);
		leftPanel.setBackground(Color.BLUE);
		
		//Control Panel
		JPanel controlPanel = new JPanel();
		controlPanel.setBounds(600,100,200,400);
		controlPanel.setBackground(Color.WHITE);

		
		frame.add(boardPanel);
		frame.add(markPanel);
		frame.add(topPanel);
		frame.add(leftPanel);
		frame.add(controlPanel);
		
		
		//frame.setLayout(new GridLayout(5, 5));
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle("Bobo");
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
	}
}

