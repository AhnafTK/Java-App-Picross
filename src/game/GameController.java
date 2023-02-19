package game;

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
		view.engRadio.addActionListener((actionEvent) -> {
			System.out.println("AAA");
		});

		view.frRadio.addActionListener((actionEvent) -> {
			System.out.println("AAA");

		});

		view.instructionsButton.addActionListener((actionEvent) -> {

			view.instructionsButton.setEnabled(false);
		});

		view.instructionsBack.addActionListener((actionEvent) -> {
			view.instructionsWindow.dispose();
			view.instructionsBack.setEnabled(true);
		});

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

	private void performViewActions() {
		launcherActions();		
	}
}
