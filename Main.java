

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Main {
	
	public static void main (String[] args) {
		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		JButton button = new JButton();
		JCheckBox checkBox = new JCheckBox();
		JLabel label = new JLabel();
		
		//frame.setLayout(new GridLayout(5, 5));
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setLocationRelativeTo(null);
		frame.setTitle("Bobo");
		frame.setSize(800, 600);
		
		
		panel.setBackground(Color.RED);
		panel.setSize(100, 100);

		frame.add(panel);
		
		
	}
}
