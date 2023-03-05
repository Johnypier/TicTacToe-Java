package tictactoe.logic;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class Game {
	private static final int BOARD_SIZE = 3;
	private Player[][] board = new Player[BOARD_SIZE][BOARD_SIZE];
	private Player currentPlayer;

	public Game() {
		currentPlayer = Player.X;
	}

	public GameStatus getCurrentGameStatus() {
		if (getWinner() != null) {
			return getWinner() == Player.X ? GameStatus.X_WON : GameStatus.O_WON;
		}
		if (isDraw()) {
			return GameStatus.DRAW;
		}
		return GameStatus.RUNNING;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Mark cell on board. Throws a runtime exception if the cell has already been
	 * marked
	 *
	 * @param x x-coordinate on board
	 * @param y y-coordinate on board
	 */
	public void markCell(int x, int y) {
		if (board[x][y] != null) {
			throw new IllegalArgumentException("This cell has already marked.");
		}
		board[x][y] = currentPlayer;
		currentPlayer = currentPlayer == Player.X ? Player.O : Player.X;
	}

	/**
	 * Return the winning player if existent, otherwise null
	 *
	 * @return winning player
	 */
	public Player getWinner() {
		// 3 in a row
		for (int i = 0; i < BOARD_SIZE; i++) {
			Player[] row = new Player[] { board[0][i], board[1][i], board[2][i] };
			if (playerWithThreeInALine(row) != null) {
				return playerWithThreeInALine(row);
			}
		}

		// 3 in a row
		for (Player[] column : board) {
			if (playerWithThreeInALine(column) != null) {
				return playerWithThreeInALine(column);
			}
		}

		// 3 in a diagonal
		Player[] diagonalLineTopLeftRightBottom = diagonalLineLeftTopRightBottom();
		Player[] diagonalLineTopRightLeftBottom = diagonalLineTopRightLeftBottom();

		if (playerWithThreeInALine(diagonalLineTopLeftRightBottom) != null) {
			return playerWithThreeInALine(diagonalLineTopLeftRightBottom);
		}
		if (playerWithThreeInALine(diagonalLineTopRightLeftBottom) != null) {
			return playerWithThreeInALine(diagonalLineTopRightLeftBottom);
		}

		return null;
	}

	public boolean isDraw() {
		return Arrays.stream(board).flatMap(Stream::of).allMatch(Objects::nonNull);
	}

	public Player[][] getBoard() {
		return board.clone();
	}

	private Player playerWithThreeInALine(Player[] line) {
		if (marksInALineForPlayer(Player.X, line) == BOARD_SIZE) {
			return Player.X;
		} else if (marksInALineForPlayer(Player.O, line) == BOARD_SIZE) {
			return Player.O;
		} else {
			return null;
		}
	}

	private long marksInALineForPlayer(Player player, Player[] line) {
		return Arrays.stream(line).filter(i -> i == player).count();
	}

	private Player[] diagonalLineLeftTopRightBottom() {
		return new Player[] { board[0][0], board[1][1], board[2][2] };
	}

	private Player[] diagonalLineTopRightLeftBottom() {
		return new Player[] { board[2][0], board[1][1], board[0][2] };
	}

	public void printGameBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			Player[] row = new Player[] { board[0][i], board[1][i], board[2][i] };
			System.out.println(Arrays.toString(row));
		}
	}
}
