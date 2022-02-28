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
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicTacToe extends Application {
	private static final String SIZE = "-fx-font-size: 20";
	private static final String BSTYLE = "-fx-background-color:rgb(40, 40, 43);" + "-fx-border-color: rgb(40, 40, 43);"
			+ "-fx-text-fill: #D3D3D3;" + SIZE;
	private static final String STYLE = "-fx-background-color: rgb(40, 40, 43);" + SIZE;

	private static final String FSTYLE = "-fx-background-color: rgb(40, 40, 43);" + "-fx-text-fill: rgb(96, 130, 182);"
			+ SIZE;
	private static final String BBSTYLE = "-fx-background-color: rgb(54, 69, 79);"
			+ "-fx-border-color: rgb(115, 147, 179);" + "-fx-text-fill: #D3D3D3;" + SIZE;
	private static final double BUTTONSSIZE = 50;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Game game = new Game();
		primaryStage.setTitle("TicTacToe");
		Label state = new Label("Game Status:  ");
		state.setStyle(FSTYLE);
		Label info = new Label("Player X turn.");
		info.setStyle(FSTYLE);

		Button firstCell = new Button();
		firstCell.setMinSize(BUTTONSSIZE, BUTTONSSIZE);
		firstCell.setStyle(BBSTYLE);
		firstCell.setOnAction(getEvent(0, 0, game, info));
		Button secondCell = new Button();
		secondCell.setMinSize(BUTTONSSIZE, BUTTONSSIZE);
		secondCell.setStyle(BBSTYLE);
		secondCell.setOnAction(getEvent(0, 1, game, info));
		Button thirdCell = new Button();
		thirdCell.setMinSize(BUTTONSSIZE, BUTTONSSIZE);
		thirdCell.setStyle(BBSTYLE);
		thirdCell.setOnAction(getEvent(0, 2, game, info));
		Button fourthCell = new Button();
		fourthCell.setMinSize(BUTTONSSIZE, BUTTONSSIZE);
		fourthCell.setStyle(BBSTYLE);
		fourthCell.setOnAction(getEvent(1, 0, game, info));
		Button fifthCell = new Button();
		fifthCell.setMinSize(BUTTONSSIZE, BUTTONSSIZE);
		fifthCell.setStyle(BBSTYLE);
		fifthCell.setOnAction(getEvent(1, 1, game, info));
		Button sixthCell = new Button();
		sixthCell.setMinSize(BUTTONSSIZE, BUTTONSSIZE);
		sixthCell.setStyle(BBSTYLE);
		sixthCell.setOnAction(getEvent(1, 2, game, info));
		Button seventhCell = new Button();
		seventhCell.setMinSize(BUTTONSSIZE, BUTTONSSIZE);
		seventhCell.setStyle(BBSTYLE);
		seventhCell.setOnAction(getEvent(2, 0, game, info));
		Button eighthCell = new Button();
		eighthCell.setMinSize(BUTTONSSIZE, BUTTONSSIZE);
		eighthCell.setStyle(BBSTYLE);
		eighthCell.setOnAction(getEvent(2, 1, game, info));
		Button ninthCell = new Button();
		ninthCell.setMinSize(BUTTONSSIZE, BUTTONSSIZE);
		ninthCell.setStyle(BBSTYLE);
		ninthCell.setOnAction(getEvent(2, 2, game, info));
		HBox labels = new HBox(state, info);
		labels.setAlignment(Pos.CENTER);

		GridPane gridPane = new GridPane();

		gridPane.add(firstCell, 0, 0, 1, 1);
		gridPane.add(secondCell, 1, 0, 1, 1);
		gridPane.add(thirdCell, 2, 0, 1, 1);
		gridPane.add(fourthCell, 0, 1, 1, 1);
		gridPane.add(fifthCell, 1, 1, 1, 1);
		gridPane.add(sixthCell, 2, 1, 1, 1);
		gridPane.add(seventhCell, 0, 2, 1, 1);
		gridPane.add(eighthCell, 1, 2, 1, 1);
		gridPane.add(ninthCell, 2, 2, 1, 1);
		gridPane.alignmentProperty().set(Pos.CENTER);
		gridPane.setStyle(BSTYLE);

		Button restart = new Button("Restart");
		restart.setOnAction(value -> {
			try {
				primaryStage.close();
				restart(primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		restart.setMinSize(BUTTONSSIZE, BUTTONSSIZE);
		restart.setStyle(BBSTYLE);
		VBox vbox = new VBox(labels, gridPane, restart);
		vbox.setSpacing(0);
		VBox.setVgrow(gridPane, Priority.ALWAYS);
		vbox.alignmentProperty().set(Pos.CENTER);
		vbox.setStyle(STYLE);
		Scene scene = new Scene(vbox, 270, 270);
		primaryStage.setScene(scene);
		primaryStage.show();
		primaryStage.setResizable(false);
	}

	private void addAlert() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Invalid Action");
		alert.setHeaderText(null);
		alert.setContentText("This cell is already marked!");
		alert.showAndWait();
	}

	private void restart(Stage stage) throws Exception {
		start(stage);
	}

	private String statusCheck(GameStatus current, Player p) {
		if (current == GameStatus.RUNNING) {
			return "Player " + p.name() + " turn.";
		} else {
			if (current == GameStatus.DRAW) {
				return "Draw!";
			} else {
				if (current == GameStatus.O_WON) {
					return "Player O won!";
				} else {
					return "Player X won!";
				}
			}
		}
	}

	private String statusEndCheck(GameStatus current) {
		if (current == GameStatus.DRAW) {
			return "Draw!";
		} else {
			if (current == GameStatus.O_WON) {
				return "Player O won!";
			} else {
				return "Player X won!";
			}
		}
	}

	private EventHandler<ActionEvent> getEvent(int a, int b, Game game, Label info) {
		return value -> {
			Button tmp = (Button) value.getSource();
			GameStatus current = game.getCurrentGameStatus();
			if (current == GameStatus.RUNNING) {
				if (tmp.getText().isEmpty()) {
					game.markCell(a, b);
					tmp.setText(game.getBoard()[a][b].name());
					info.setText(statusCheck(game.getCurrentGameStatus(), game.getCurrentPlayer()));
				} else {
					addAlert();
				}
			} else {
				info.setText(statusEndCheck(game.getCurrentGameStatus()));
			}
		};
	}
}
