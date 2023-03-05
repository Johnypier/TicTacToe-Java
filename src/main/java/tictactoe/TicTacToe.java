package tictactoe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import tictactoe.logic.Game;
import tictactoe.logic.GameStatus;
import tictactoe.logic.Player;

public class TicTacToe extends Application {
	// Configuration Constants
	private static final String FONT_SIZE = "-fx-font-size: 20";
	private static final String GRIDPANE_STYLE = "-fx-background-color:rgb(40, 40, 43);"
			+ "-fx-border-color: rgb(40, 40, 43);"
			+ "-fx-text-fill: #D3D3D3;"
			+ FONT_SIZE;
	private static final String BOX_STYLE = "-fx-background-color: rgb(40, 40, 43);"
			+ FONT_SIZE;

	private static final String LABEL_STYLE = "-fx-background-color: rgb(40, 40, 43);"
			+ "-fx-text-fill: rgb(96, 130, 182);"
			+ FONT_SIZE;
	private static final String BUTTON_CELL_STYLE = "-fx-background-color: rgb(54, 69, 79);"
			+ "-fx-border-color: rgb(115, 147, 179);"
			+ "-fx-text-fill: #D3D3D3;"
			+ FONT_SIZE;
	private static final double BOX_SPACING = 10;
	private static final double GRIDPANE_GAP = 3;
	private static final double BUTTON_SIZE = 50;
	private static final double SCENE_SIZE = 270;

	// Used to launch the application.
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Game game = new Game();
		primaryStage.setTitle("TicTacToe");
		Label state = new Label("Game Status:  ");
		state.setStyle(LABEL_STYLE);
		Label info = new Label("Player X turn.");
		info.setStyle(LABEL_STYLE);

		Button firstCell = buttonConfiguration();
		firstCell.setOnAction(markCellEvent(0, 0, game, info));
		Button secondCell = buttonConfiguration();
		secondCell.setOnAction(markCellEvent(0, 1, game, info));
		Button thirdCell = buttonConfiguration();
		thirdCell.setOnAction(markCellEvent(0, 2, game, info));
		Button fourthCell = buttonConfiguration();
		fourthCell.setOnAction(markCellEvent(1, 0, game, info));
		Button fifthCell = buttonConfiguration();
		fifthCell.setOnAction(markCellEvent(1, 1, game, info));
		Button sixthCell = buttonConfiguration();
		sixthCell.setOnAction(markCellEvent(1, 2, game, info));
		Button seventhCell = buttonConfiguration();
		seventhCell.setOnAction(markCellEvent(2, 0, game, info));
		Button eighthCell = buttonConfiguration();
		eighthCell.setOnAction(markCellEvent(2, 1, game, info));
		Button ninthCell = buttonConfiguration();
		ninthCell.setOnAction(markCellEvent(2, 2, game, info));
		HBox labels = new HBox(state, info);
		labels.setAlignment(Pos.CENTER);

		GridPane gridPane = new GridPane();
		gridPane.add(firstCell, 0, 0);
		gridPane.add(secondCell, 1, 0);
		gridPane.add(thirdCell, 2, 0);
		gridPane.add(fourthCell, 0, 1);
		gridPane.add(fifthCell, 1, 1);
		gridPane.add(sixthCell, 2, 1);
		gridPane.add(seventhCell, 0, 2);
		gridPane.add(eighthCell, 1, 2);
		gridPane.add(ninthCell, 2, 2);
		gridPane.setHgap(GRIDPANE_GAP);
		gridPane.setVgap(GRIDPANE_GAP);
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setStyle(GRIDPANE_STYLE);

		Button restart = buttonConfiguration();
		restart.setText("Restart");
		restart.setOnAction(value -> {
			primaryStage.close();
			restart(primaryStage);

		});

		VBox vbox = new VBox(labels, gridPane, restart);
		vbox.setAlignment(Pos.CENTER);
		vbox.setSpacing(BOX_SPACING);
		vbox.setStyle(BOX_STYLE);
		Scene scene = new Scene(vbox, SCENE_SIZE, SCENE_SIZE);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	/**
	 * Resets the game window.
	 */
	private void restart(Stage stage) {
		try {
			start(stage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Marks the cells with according to the current player and updates the game
	 * status.
	 * 
	 * @param a    Cell's row.
	 * @param b    Cell's column.
	 * @param game Link to the actual game logic.
	 * @param info Information/Status label.
	 * @return Configured event.
	 */
	private EventHandler<ActionEvent> markCellEvent(int a, int b, Game game, Label info) {
		return value -> {
			Button currentButton = (Button) value.getSource();
			GameStatus current = game.getCurrentGameStatus();
			if (current == GameStatus.RUNNING) {
				if (currentButton.getText().isEmpty()) {
					game.markCell(a, b);
					currentButton.setText(game.getBoard()[a][b].name());
					info.setText(statusCheck(game.getCurrentGameStatus(), game.getCurrentPlayer()));
				} else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Invalid Action");
					alert.setHeaderText("");
					alert.setContentText("This cell is already marked!");
					alert.showAndWait();
				}
			} else {
				info.setText(statusCheck(game.getCurrentGameStatus(), game.getCurrentPlayer()));
			}
		};
	}

	/**
	 * Update the status label of the game.
	 * 
	 * @param currentStatus Actual game status
	 * @param player        Player who moves next.
	 * @return New game status.
	 */
	private String statusCheck(GameStatus currentStatus, Player player) {
		if (currentStatus == GameStatus.RUNNING) {
			return "Player " + player.name() + " turn.";
		}
		if (currentStatus == GameStatus.DRAW) {
			return "Draw!";
		} else {
			return currentStatus == GameStatus.O_WON ? "Player O won!" : "Player X won!";
		}
	}

	/**
	 * Simplifies multiple button's configuration.
	 * 
	 * @return Configured button.
	 */
	private Button buttonConfiguration() {
		Button temp = new Button();
		temp.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
		temp.setStyle(BUTTON_CELL_STYLE);
		return temp;
	}
}
