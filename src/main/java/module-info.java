module TicTacToe {
    requires javafx.controls;
    requires transitive javafx.graphics;

    opens tictactoe to javafx.graphics;

    exports tictactoe;
}
