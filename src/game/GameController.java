package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class GameController {
	GameModel model;
	GameView view;

	public GameController(GameModel model, GameView view) {
		// TODO Auto-generated constructor stub
		this.model = model;
		this.view = view;
	}

	protected void startController() {
		performViewActions();
	}

	private void launcherActions() {
		view.playButton.addActionListener((actionEvent) -> {
			System.out.println("clickd play");
			view.startWindow.dispose();
			view.Play();
			playActions();
		});

		view.designButton.addActionListener((actionEvent) -> {
			System.out.println("clicked design button");
			view.startWindow.dispose();
			view.Design();
			designActions();
		});
	}

	private void designActions() {
		view.designBack.addActionListener((actionEvent) -> {
			view.designWindow.dispose();
			System.out.println("clicked design back button");
			view.launcher();
			launcherActions();
		});
	}

	private void playActions() {
		
		view.playToLauncher.addActionListener((actionEvent)->{
			view.picrossWindow.dispose();
			view.launcher();
			launcherActions();
		});
		
		view.engRadio.addActionListener((actionEvent) -> {
			System.out.println("AAA");
		});

		view.frRadio.addActionListener((actionEvent) -> {
			System.out.println("AAA");

		});

		view.instructionsButton.addActionListener((actionEvent) -> {

			view.instructionsButton.setEnabled(false);
		});

		// needs its own action listerner function
		//view.instructionsBack.addActionListener((actionEvent) -> {
			//view.instructionsWindow.dispose();
			//view.instructionsBack.setEnabled(true);
		//});

		view.markCheckBox.addActionListener((actionEvent) -> {
			System.out.println("AAA");

		});

		view.newBoardButton.addActionListener((actionEvent) -> {

		});

		view.resetButton.addActionListener((actionEvent) -> {
			System.out.println("AAA");

		});

		view.solveButton.addActionListener((actionEvent) -> {
			view.history.append(model.langText.getString("upon_click") + model.langText.getString("button")
					+ model.langText.getString("solve") + "\n");
		});
		
		
	}
	
	void addBoardListener(ActionListener listenForCalcButton) {
		
		for (int i = 0; i < view.buttons.length; i++) {
			
		}
		//view.buttons[][].addActionListener(listenForCalcButton);
		
	}
	private class boardButtonActionMaker implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			for (int i = 0; i < model.getGridSize(); i++) {
				for (int j = 0; j < model.getGridSize(); j++) {
					
					// If the user clicks on a button with the mark mode disabled
					if (e.getSource() == view.buttons[i][j] && (!view.markMode)) {
						
						view.buttons[i][j].setEnabled(false);
						view.buttons[i][j].setBackground(new Color(17,15,15));
						if (view.gameMode == 1) {
						view.history.append(view.langText.getString("upon_click") + view.langText.getString("button") + i + ", "
								+ j + "\n"); 
						}
					}
					
					// If the user clicks on a button with the mark mode enabled
					else {
						if (e.getSource() == view.buttons[i][j]) {
							view.buttons[i][j].setBackground(new Color(226,222,222));
						}
					}
				}
			}
		}
	}
	
	private void performViewActions() {
		launcherActions();		
	}
	
	int getGridSize() {
		return model.getGridSize();
	}
	
	int getGameMode() {
		return model.getGameMode();
	}
}
