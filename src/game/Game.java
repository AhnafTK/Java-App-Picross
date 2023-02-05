package game;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Game extends JFrame implements ActionListener {
	JButton instructionsButton;
	JButton instructionsBack;
	JButton designBack;
	JFrame startWindow;
	JFrame designWindow;
	JFrame instructionsWindow;
	JFrame picrossWindow;
	JComboBox gridSizeCmbo;
	int gridSize = 5;
	ButtonGroup langButtons;
	String language = "English";
	JButton playButton;
	JButton designButton;
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
	JPanel boardPanel = new JPanel();
	JCheckBox markCheckBox;
	boolean markMode = false;
	
	Locale currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
	ResourceBundle langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);

	// JLabel init
			JLabel timerLabel = new JLabel(langText.getString("timer"));
			JLabel scoreLabel = new JLabel(langText.getString("score"));
			JLabel gridSizeLabel = new JLabel(langText.getString("gridSize"));
			JLabel langLabel = new JLabel();
			JLabel colourLabel = new JLabel();

	public static void main(String[] args) {
		new Game();

	}

	private JPanel makeLeftPanel(Locale locale, ResourceBundle langText) {
		JPanel scorePanel = new JPanel();
		JPanel timerPanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		JPanel languageButtonPanel = new JPanel();
		JPanel colourPanel = new JPanel();

		// JButton intializations
		resetButton = new JButton(langText.getString("reset"));
		resetButton.setBackground(Color.WHITE);
		solveButton = new JButton(langText.getString("solve"));
		solveButton.setBackground(Color.WHITE);
		newBoardButton = new JButton(langText.getString("newBoard"));
		newBoardButton.setBackground(Color.WHITE);
		instructionsButton = new JButton(langText.getString("instructions"));
		instructionsButton.setBackground(Color.WHITE);

		

		// This is the text field for the score counter needs to be organized.
		JTextField scoreCounter = new JTextField();
		scoreCounter.setBorder(new LineBorder(Color.BLACK, 1));
		scoreCounter.setPreferredSize(new Dimension(100, 25));
		scoreCounter.setEditable(false);
		//

		// This is the text field for the score counter needs to be organized.
		JTextField timerCounter = new JTextField();
		timerCounter.setBorder(new LineBorder(Color.BLACK, 1));
		timerCounter.setPreferredSize(new Dimension(100, 25));
		timerCounter.setEditable(false);
		//

		// Score Panel

		scorePanel.add(scoreLabel);
		scorePanel.add(scoreCounter);

		// timer Panel
		timerPanel.add(timerLabel);
		timerPanel.add(timerCounter);

		String options[] = { "5x5", "6x6", "7x7" };
		gridSizeCmbo = new JComboBox(options);
		gridSizeCmbo.addActionListener(this);
		gridSizeCmbo.setBackground(Color.WHITE);
		JPanel gridSizeComboPanel = new JPanel();
		gridSizeComboPanel.add(gridSizeLabel);
		gridSizeComboPanel.add(gridSizeCmbo);
		// gridSizeComboPanel.setBackground(Color.RED);
		gridSizeComboPanel.setPreferredSize(new Dimension(200, 30));

		JPanel configurationPanel = new JPanel();
		JPanel languagePanel = new JPanel();

		

		engRadio = new JRadioButton(langText.getString("english"), true);
		engRadio.addActionListener(this);

		frRadio = new JRadioButton(langText.getString("french"));
		frRadio.addActionListener(this);

		// if(currentLocale.getCountry() == "US") {engRadio.setSelected(true);}
		// else {frRadio.setSelected(true);}

		blYelRadio = new JRadioButton(langText.getString("blc_yel_colorScheme"));
		blYelRadio.addActionListener(this);
		whBlueRadio = new JRadioButton(langText.getString("white_blue_colorScheme"));
		whBlueRadio.addActionListener(this);

		configurationPanel.setLayout(new GridLayout(1, 2));

		langButtons = new ButtonGroup();
		langButtons.add(engRadio);
		langButtons.add(frRadio);

		langLabel.setText(langText.getString("languages"));

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
		configurationPanel.setPreferredSize(new Dimension(225, 100));

		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		buttonPanel.setPreferredSize(new Dimension(120, 150));

		resetButton.setPreferredSize(new Dimension(120, 25));
		resetButton.addActionListener(this);
		buttonPanel.add(resetButton);
		solveButton.setPreferredSize(new Dimension(120, 25));
		solveButton.addActionListener(this);
		buttonPanel.add(solveButton);
		newBoardButton.setPreferredSize(new Dimension(120, 25));
		newBoardButton.addActionListener(this);
		buttonPanel.add(newBoardButton);
		instructionsButton.setPreferredSize(new Dimension(120, 25));
		instructionsButton.addActionListener(this);
		buttonPanel.add(instructionsButton);
		leftPanel.add(scorePanel);
		leftPanel.add(timerPanel);
		leftPanel.add(gridSizeComboPanel);
		leftPanel.add(buttonPanel);
		leftPanel.add(configurationPanel);

		leftPanel.setBorder(BorderFactory.createMatteBorder(0,0,0,2, Color.black));
		leftPanel.setPreferredSize(new Dimension(250, 200));

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
		//titlePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		titlePanel.setPreferredSize(new Dimension(1000, 125));
		titlePanel.add(picrossLabel);

		return titlePanel;
	}

	private JPanel makeHistoryPanel() {

		JPanel historyPanel = new JPanel();
		history = new JTextArea();
		history.setLineWrap(true);
		history.setWrapStyleWord(true);

		history.setPreferredSize(new Dimension(200, 10000)); // important
		history.setBorder(new LineBorder(Color.BLACK));
		history.setEditable(false);

		////////////////////////////////////////////////////////////////

		JScrollPane scroll = new JScrollPane(history);
		scroll.setPreferredSize(new Dimension(200, 300));
		scroll.getVerticalScrollBar().setUnitIncrement(10);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

		////////////////////////////////////////////////////////////////

		historyPanel.add(scroll);
		//historyPanel.setBackground(Color.WHITE);
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
		//controlPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		controlPanel.setPreferredSize(new Dimension(250, 200));
		
		controlPanel.add(makeHistoryPanel());
		//controlPanel.setBackground(Color.BLACK);
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
		
		////////////////////////////////////////////////////////////////

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(gridSize, 1));
		colPanel.setPreferredSize(new Dimension(75, 0));
		//colPanel.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Color.BLACK));
		//colPanel.setBackground(Color.green);
				
		for (int i = 0; i<gridSize; i++) {
			JLabel grid = new JLabel("0,0", SwingConstants.CENTER);
			grid.setBackground(Color.GRAY);
			colPanel.add(grid);
		}

		////////////////////////////////////////////////////////////////

		//JPanel boardPanel = new JPanel();
		markCheckBox = new JCheckBox("Mark");
		markCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		markCheckBox.addActionListener(this);
		boardPanel.setLayout(new BorderLayout());
		//boardPanel.setBackground(Color.red);
		
		//JLabel markLabel = new JLabel("Mark", SwingConstants.CENTER);
		// add checkmark
		//markPanel.setLayout(new CardLayout());
		
		////////////////////////////////////////////////////////////////

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

		//boardPanel.add(markPanel, BorderLayout.WEST);
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

		//gridButtonPanel.setBorder(BorderFactory.createMatteBorder(3,2,3,2, Color.BLACK));
		boardPanel.add(gridButtonPanel, BorderLayout.CENTER);
		
		return boardPanel;
	}
	
	/**
	 ********************************************************************
	 * Make Design panel												*
	 * 																	*
	 * This is a duplicate method of the Make Board Panel in order to	*
	 * display the layout of the default grid.							*
	 ********************************************************************
	 */	
	private JPanel makeDesignPanel(int gridSize) {
		
		////////////////////////////////////////////////////////////////

		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(gridSize, 1));
		colPanel.setPreferredSize(new Dimension(75, 0));
		//colPanel.setBorder(BorderFactory.createMatteBorder(1,0,0,0, Color.BLACK));
		//colPanel.setBackground(Color.green);
				
		for (int i = 0; i<gridSize; i++) {
			JLabel grid = new JLabel("0,0", SwingConstants.CENTER);
			grid.setBackground(Color.GRAY);
			colPanel.add(grid);
		}

		////////////////////////////////////////////////////////////////

		//JPanel boardPanel = new JPanel();
		JCheckBox markCheckBox = new JCheckBox("Mark");
		markCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		boardPanel.setLayout(new BorderLayout());
		//boardPanel.setBackground(Color.red);
		
		//JLabel markLabel = new JLabel("Mark", SwingConstants.CENTER);
		// add checkmark
		//markPanel.setLayout(new CardLayout());
		
		////////////////////////////////////////////////////////////////

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

		//boardPanel.add(markPanel, BorderLayout.WEST);
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
				gridButtonPanel.add(buttons[i][j]);
			}
		}
		
		////////////////////////////////////////////////////////////////
		
		//gridButtonPanel.setBorder(BorderFactory.createMatteBorder(3,2,3,2, Color.BLACK));
		JPanel bottomDesign = new JPanel();
		bottomDesign.setPreferredSize(new Dimension(500, 50));
		designBack = new JButton("Back");
		designBack.addActionListener(this);
		bottomDesign.add(designBack);
		
		boardPanel.add(gridButtonPanel, BorderLayout.CENTER);
		boardPanel.add(bottomDesign, BorderLayout.SOUTH);
		return boardPanel;
	}
	
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
		//engRadio.setText(langText.getString("reset"));
		
	}
	
	public Game() {
		startWindow = new JFrame();
		JPanel startPanel = new JPanel();
		
		designButton = new JButton("Design");
		designButton.setPreferredSize(new Dimension(100, 30));
		designButton.addActionListener(this);
		
		playButton = new JButton("Play");
		playButton.setPreferredSize(new Dimension(100, 30));
		playButton.addActionListener(this);
	
		startPanel.add(designButton);
		startPanel.add(playButton);
		startWindow.add(startPanel);
		startWindow.pack();
		
		startWindow.setVisible(true);
		startWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		startWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startWindow.setResizable(false);
		startWindow.setSize(300, 300);
		startWindow.setLocationRelativeTo(null);
	}

	public void Design() {
		designWindow = new JFrame();
		
		designWindow.setLayout(new BorderLayout());
		designWindow.add(makeDesignPanel(gridSize), BorderLayout.CENTER);
		
		designWindow.setResizable(false);
		designWindow.setVisible(true);
		designWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		designWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		designWindow.setSize(500, 500);
		designWindow.setLocationRelativeTo(null);
	}
	
	public void Play() {
		/*
		 ********************************************************************
		 * Picross window frame												*
		 * 																	*
		 * This is where everything gets handled for the picross JFrame.	*
		 ********************************************************************
		 */	
		picrossWindow = new JFrame();

		picrossWindow.setLayout(new BorderLayout());
		picrossWindow.add(makeTitlePanel(), BorderLayout.NORTH);
		picrossWindow.add(makeLeftPanel(currentLocale, langText), BorderLayout.WEST);
		picrossWindow.add(makeControlPanel(), BorderLayout.EAST);
		picrossWindow.add(makeBoardPanel(gridSize), BorderLayout.CENTER);
		picrossWindow.pack();

		picrossWindow.setResizable(false);
		picrossWindow.setVisible(true);
		picrossWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		picrossWindow.setTitle("Picross - Skylar Phanenhour, Ahnaf Kamal");
		picrossWindow.setSize(1000, 600);
		picrossWindow.setLocationRelativeTo(null);
	}
	
	/**
	 ************************************************************************
	 * This is the instructions class that explains 						*
	 * how the game is supposed to be played.								*
	 ************************************************************************
	 */
	public void Instructions() {
		instructionsWindow = new JFrame();
		JPanel instructionsPanel = new JPanel();
		instructionsPanel.setPreferredSize(new Dimension(500, 400));
		JLabel instructionsLabel = new JLabel();
		instructionsBack = new JButton("Back");

		/**
		 * String printInstructions = "<html><br/>
		 * There is are multiple rows and columns that are adjacent<br/>
		 * " + "to the grid of buttons, these will have numbers that will indicate<br/>
		 * " + "how many correct tiles are in that row/column.<br/>
		 * <br/>
		 * " + "The buttons in the grid can either be clicked or marked as empty,<br/>
		 * " + "they will be highlighted to indicate right or wrong. <br/>
		 * <br/>
		 * " + "Once a button is clicked, the timer will start and the score<br/>
		 * " + "will be initialized to 0. When all of the correct tiles are placed,<br/>
		 * " + "the score goes up and the timer resets.<br/>
		 * <br/>
		 * " + "At any time you can solve the grid, reset the board or generate<br/>
		 * " + "a new board that has a random pattern.<br/>
		 * <br/>
		 * </html>";
		 **/
		String printInstructions = langText.getString("instructions_text");
		instructionsLabel.setText(printInstructions);
		instructionsLabel.setFont(new Font("Calibri Regular", Font.PLAIN, 16));

		instructionsBack.setFont(new Font("Calibri Regular", Font.BOLD, 12));
		instructionsBack.addActionListener(this);

		instructionsPanel.add(instructionsLabel);
		instructionsPanel.add(instructionsBack);
		instructionsWindow.add(instructionsPanel);

		instructionsWindow.setTitle("Instructions - Skylar Phanenhour, Ahnaf Kamal");
		instructionsWindow.setResizable(false);
		instructionsWindow.setSize(525, 425);
		instructionsWindow.setVisible(true);
		instructionsWindow.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == designButton) {
			startWindow.dispose();
			Design();
		}
		
		if (e.getSource() == designBack) {
			designWindow.dispose();
			new Game();
		}
		
		if (e.getSource() == playButton) {
			startWindow.dispose();
			Play();
		}
		
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

		if (e.getSource() == solveButton) {
			// history.append("\nYou clicked the solve button\n");
			history.append(langText.getString("upon_click") + langText.getString("button") 
					+ langText.getString("solve")  + "\n");

		}

		if (e.getSource() == newBoardButton) {
			// history.append("\nYou clicked the new board button\n");
			history.append(langText.getString("upon_click") + langText.getString("button")
					+ langText.getString("newBoard") + "\n");
		}

		if (e.getSource() == instructionsButton) {
			history.append(langText.getString("upon_click") + langText.getString("button")
					+ langText.getString("instructions") + "\n");
			Instructions();
			instructionsButton.setEnabled(false);
		}

		if (e.getSource() == instructionsBack) {
			//history.append("\nYou returned back to the picross game\n");
			history.append(langText.getString("upon_return") + " picross\n");

			instructionsWindow.dispose();
			instructionsButton.setEnabled(true);

		}

		if (e.getSource() == engRadio) {
			// history.append("\nYou changed the language to English\n");
			history.append("\n" + langText.getString("upon_lang_change") + langText.getString("english")  + "\n");
			currentLocale = new Locale.Builder().setLanguage("en").setRegion("US").build();
			langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);
			//leftPanel.removeAll();
			//leftPanel = makeLeftPanel(currentLocale);
			//leftPanel.revalidate();
			//leftPanel.revalidate();
			//leftPanel.repaint();
			//leftPanel.removeAll();
			//leftPanel = makeLeftPanel(currentLocale, langText);
			//leftPanel.revalidate();
			//leftPanel.removeAll();
			updateText(currentLocale, langText);
			leftPanel.revalidate();
		}

		if (e.getSource() == frRadio) {
			history.append("\n" + langText.getString("upon_lang_change") + langText.getString("french") + "\n");
			currentLocale = new Locale.Builder().setLanguage("fr").setRegion("FR").build();
			langText = ResourceBundle.getBundle("MessagesBundle", currentLocale);
			//leftPanel.removeAll();
			//leftPanel = makeLeftPanel(currentLocale, langText);
			//leftPanel.revalidate();
			//leftPanel.removeAll();
			//leftPanel.removeAll();
			//leftPanel = makeLeftPanel(currentLocale);
			//leftPanel.revalidate();
			updateText(currentLocale, langText);
			leftPanel.revalidate();


		}

		if (e.getSource() == whBlueRadio) {
			history.append(langText.getString("upon_color_change") + langText.getString("white_blue_colorScheme") + "\n");
			//leftPanel.setForeground(Color.RED);
			//leftPanel.validate();
			//leftPanel.repaint();
		}

		if (e.getSource() == blYelRadio) {
			//history.append("\nYou changed the game colour to black & yellow\n");
			history.append(langText.getString("upon_color_change") + langText.getString("blc_yel_colorScheme") + "\n");

		}
		
		if (e.getSource() == markCheckBox) {
			if (markCheckBox.isSelected()) {				
				history.append("Mark Mode: True\n");
				markMode = true;
			}
			else {
				history.append("Mark Mode: False\n");
				markMode = false;
			}
			
		}

		if (e.getSource() == gridSizeCmbo) {
			String options = (String) gridSizeCmbo.getSelectedItem();

			switch (options) {
			case "5x5":
				//history.append("\nYou changed the grid to 5x5\n");
				gridSize = 5;
				history.append(langText.getString("upon_grid_change") + " 5x5\n");
				boardPanel.removeAll();
				boardPanel = makeBoardPanel(5);
				boardPanel.revalidate();
				
				break;

			case "6x6":
				history.append(langText.getString("upon_grid_change")  + " 6x6\n");
				 gridSize = 6;
				boardPanel.removeAll();
				boardPanel = makeBoardPanel(6);
				boardPanel.revalidate();

				break;

			case "7x7":
				history.append(langText.getString("upon_grid_change") + " 7x7\n");
				gridSize = 7;
				boardPanel.removeAll();
				boardPanel = makeBoardPanel(7);
				boardPanel.revalidate();

				break;
			}
		} else {
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					if (e.getSource() == buttons[i][j] && (!markMode)) {
						buttons[i][j].setEnabled(false);
						buttons[i][j].setBackground(Color.BLACK);
						history.append(langText.getString("upon_click") + langText.getString("button") + i + ", "
								+ j + "\n");
					}
					else {
						if (e.getSource() == buttons[i][j]) {
							buttons[i][j].setBackground(Color.GRAY);
						}
					}
				}
			}
		}
	}
}
