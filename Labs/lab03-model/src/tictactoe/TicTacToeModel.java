package tictactoe;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Implementation of the TicTacToe game model. This class manages the state of the game,
 * including the current board, the current player, and determining whether the game has
 * been won or ended in a tie.
 */
public class TicTacToeModel implements TicTacToe {
  private final Player[][] board; // The 3x3 game board
  private Player currentPlayer;   // Tracks whose turn it is
  private Player winner;          // Tracks the winner if there is one
  private boolean isGameOver;     // Tracks if the game is over

  /**
   * Constructs a new TicTacToe game with an empty board and sets the current player to Player X.
   */
  public TicTacToeModel() {
    this.board = new Player[3][3]; // 3x3 board initialized with nulls
    this.currentPlayer = Player.X; // Player X starts first
    this.winner = null;
    this.isGameOver = false;
  }

  /**
   * Makes a move for the current player at the specified row and column.
   * The method validates the move, places the current player's mark,
   * checks for a win or tie, and switches the turn to the other player.
   *
   * @param r the row of the intended move (0, 1, or 2)
   * @param c the column of the intended move (0, 1, or 2)
   * @throws IllegalArgumentException if the move is invalid
    (position is out of bounds or already occupied)
   * @throws IllegalStateException if the game is already over
   */
  @Override
  public void move(int r, int c) {
    if (isGameOver) {
      throw new IllegalStateException("The game is over.");
    }
    if (r < 0 || r > 2 || c < 0 || c > 2 || board[r][c] != null) {
      throw new IllegalArgumentException("Invalid move. "
              + "The position is either occupied or out of bounds.");
    }

    // Place the mark for the current player
    board[r][c] = currentPlayer;

    // Check if the move results in a win or tie
    if (checkWinner(r, c)) {
      isGameOver = true;
      winner = currentPlayer;
    } else if (isBoardFull()) {
      isGameOver = true; // Game over due to tie
    } else {
      // Switch to the other player
      currentPlayer = (currentPlayer == Player.X) ? Player.O : Player.X;
    }
  }

  /**
   * Returns the player whose turn it is to move next.
   *
   * @return the current player (Player X or Player O)
   * @throws IllegalStateException if the game is already over
   */
  @Override
  public Player getTurn() {
    if (isGameOver) {
      throw new IllegalStateException("The game is over.");
    }
    return currentPlayer;
  }

  /**
   * Returns whether the game is over. The game is over if either one player has won
   * or if all spaces on the board are filled (resulting in a tie).
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    return isGameOver;
  }

  /**
   * Returns the winner of the game if there is one, or null if there is no winner.
   * The game must be over for there to be a winner.
   *
   * @return the winning player (Player X or Player O), or null if there is no winner
   */
  @Override
  public Player getWinner() {
    return winner;
  }

  /**
   * Returns a 2D array representing the current state of the game board.
   * Each position in the array contains the player (Player X or Player O)
   * who has marked that position, or null if the position is empty.
   *
   * @return the current state of the board as a 2D array
   */
  @Override
  public Player[][] getBoard() {
    Player[][] copy = new Player[3][3];
    for (int i = 0; i < 3; i++) {
      // Create a copy of each row
      System.arraycopy(this.board[i], 0, copy[i], 0, 3);
    }
    return copy;
  }

  /**
   * Returns the player mark at the specified position on the board,
   * or null if the position is empty.
   *
   * @param r the row of the position (0, 1, or 2)
   * @param c the column of the position (0, 1, or 2)
   * @return the player mark (Player X or Player O), or null if the position is empty
   * @throws IllegalArgumentException if the position is out of bounds
   */
  @Override
  public Player getMarkAt(int r, int c) {
    if (r < 0 || r > 2 || c < 0 || c > 2) {
      throw new IllegalArgumentException("Invalid position. "
              + "Row and column must be between 0 and 2.");
    }
    return board[r][c];
  }

  /**
   * Checks if the current player has won the game. A player wins by having three marks
   * in a row either horizontally, vertically, or diagonally.
   *
   * @param r the row of the last move
   * @param c the column of the last move
   * @return true if the current player has won, false otherwise
   */
  private boolean checkWinner(int r, int c) {
    return checkRow(r) || checkColumn(c) || checkDiagonals();
  }

  /**
   * Checks if the current player has filled an entire row.
   *
   * @param r the row to check
   * @return true if the row is filled with the current player's mark, false otherwise
   */
  private boolean checkRow(int r) {
    return board[r][0] != null && board[r][0] == board[r][1] && board[r][1] == board[r][2];
  }

  /**
   * Checks if the current player has filled an entire column.
   *
   * @param c the column to check
   * @return true if the column is filled with the current player's mark, false otherwise
   */
  private boolean checkColumn(int c) {
    return board[0][c] != null && board[0][c] == board[1][c] && board[1][c] == board[2][c];
  }

  /**
   * Checks if the current player has won by filling either diagonal.
   *
   * @return true if the current player has filled either diagonal, false otherwise
   */
  private boolean checkDiagonals() {
    return (board[0][0] != null && board[0][0] == board[1][1] && board[1][1] == board[2][2])
            || (board[0][2] != null && board[0][2] == board[1][1] && board[1][1] == board[2][0]);
  }

  /**
   * Checks if the board is completely filled with marks.
   * If all positions on the board are filled and no player has won, the game ends in a tie.
   *
   * @return true if all positions are filled, false otherwise
   */
  private boolean isBoardFull() {
    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 3; j++) {
        if (board[i][j] == null) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Returns a human-readable string representation of the current state of the board.
   * Empty spaces are represented by blank spaces, and player marks are separated by " | ".
   * Rows are separated by "-----------".
   *
   * @return the string representation of the game board
   */

  @Override
  public String toString() {
    // Using Java stream API to save code:
    return Arrays.stream(getBoard()).map(
      row -> " " + Arrays.stream(row).map(
        p -> p == null ? " " : p.toString()).collect(Collectors.joining(" | ")))
          .collect(Collectors.joining("\n-----------\n"));
    // This is the equivalent code as above, but using iteration, and still using the helpful
    // built-in String.join method.
    // List<String> rows = new ArrayList<>();
    // for(Player[] row : getBoard()) {
    //   List<String> rowStrings = new ArrayList<>();
    //   for(Player p : row) {
    //     if(p == null) {
    //       rowStrings.add(" ");
    //     } else {
    //       rowStrings.add(p.toString());
    //     }
    //   }
    //   rows.add(" " + String.join(" | ", rowStrings));
    // }
    // return String.join("\n-----------\n", rows);
  }

  /**
   * Checks if two TicTacToeModel objects are equal.
   * Two TicTacToeModel objects are considered equal if their game boards,
   * current players, winners, and game states (whether the game is over) are all the same.
   *
   * @param obj the object to be compared for equality
   * @return true if the two objects are equal, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof TicTacToeModel)) {
      return false;
    }
    TicTacToeModel other = (TicTacToeModel) obj;

    // Check if the boards are the same, as well as the current player, winner, and game over status
    return Arrays.deepEquals(this.board, other.board)
            && this.currentPlayer == other.currentPlayer
            && this.winner == other.winner
            && this.isGameOver == other.isGameOver;
  }

  /**
   * Returns a hash code value for the TicTacToeModel object.
   * The hash code is generated based on the game board, current player,
   * winner, and whether the game is over.
   *
   * @return a hash code value for this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(Arrays.deepHashCode(board), currentPlayer, winner, isGameOver);
  }
}
