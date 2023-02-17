package game;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameController {
	GameModel model;
	GameView view;

	public GameController(GameModel model, GameView view) {
		// TODO Auto-generated constructor stub
		this.model = model;
		this.view = view;
	}

	protected void startController() {
		view.viewAddListener(new viewActionListener());
		// performViewActions();
	}

	class viewActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// Design button
			if (e.getSource() == view.designButton) {
				view.startWindow.dispose();
				view.Design();
			}

			////////////////////////////////////////////////////////////////

			// Design back button
			if (e.getSource() == view.designBack) {
				view.designWindow.dispose();

				new Game();
			}

			////////////////////////////////////////////////////////////////

			// Play button
			if (e.getSource() == view.playButton) {
				view.startWindow.dispose();
				view.Play();
			}

			////////////////////////////////////////////////////////////////

			// Reset button
			if (e.getSource() == view.resetButton) {
				view.history.append(view.langText.getString("upon_click") + view.langText.getString("button")
						+ view.langText.getString("reset") + "\n");
				for (int i = 0; i < view.gridSize; i++) {
					for (int j = 0; j < view.gridSize; j++) {
						view.buttons[i][j].setEnabled(true);
						view.buttons[i][j].setBackground(Color.WHITE);
					}
				}
			}

			////////////////////////////////////////////////////////////////

			// Solve button
			if (e.getSource() == view.solveButton) {
				//view.history.append(view.langText.getString("upon_click") + langText.getString("button")
					//	+ langText.getString("solve") + "\n");
			}

			////////////////////////////////////////////////////////////////

			// New board button
			if (e.getSource() == view.newBoardButton) {
				//history.append(langText.getString("upon_click") + langText.getString("button")
						//+ langText.getString("newBoard") + "\n");
			}

			////////////////////////////////////////////////////////////////

			// Instructions button
			if (e.getSource() == view.instructionsButton) {
				//history.append(langText.getString("upon_click") + langText.getString("button")
					//	+ langText.getString("instructions") + "\n");
				//Instructions(currentLocale);
				//instructionsButton.setEnabled(false);
			}

			////////////////////////////////////////////////////////////////

			// Instructions back button
			if (e.getSource() == view.instructionsBack) {
				//history.append(langText.getString("upon_return") + " picross\n");

				view.instructionsWindow.dispose();
				view.instructionsButton.setEnabled(true);
			}

		}

	}

	/**
	 * private void performViewActions() {
	 * 
	 * view.playButton.addActionListener((actionEvent) -> { view.Play(); });
	 * 
	 * view.designButton.addActionListener((actionEvent) -> { view.Design(); });
	 * 
	 * view.designBack.addActionListener((actionEvent) -> {
	 * 
	 * view.designWindow.dispose();
	 * 
	 * new Game();
	 * 
	 * });
	 * 
	 * view.engRadio.addActionListener((actionEvent) -> {
	 * 
	 * });
	 * 
	 * view.frRadio.addActionListener((actionEvent) -> {
	 * 
	 * });
	 * 
	 * view.instructionsButton.addActionListener((actionEvent) -> {
	 * 
	 * });
	 * 
	 * view.markCheckBox.addActionListener((actionEvent) -> {
	 * 
	 * });
	 * 
	 * view.newBoardButton.addActionListener((actionEvent) -> {
	 * 
	 * });
	 * 
	 * view.resetButton.addActionListener((actionEvent) -> {
	 * 
	 * });
	 * 
	 * view.solveButton.addActionListener((actionEvent) -> {
	 * 
	 * });
	 * 
	 * }
	 **/

}
