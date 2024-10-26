package tictactoe;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents a console-based controller for the Tic Tac Toe game.
 * It interacts with the user through the console
 * and communicates with the TicTacToe model to process the game logic.
 */
public class TicTacToeConsoleController implements TicTacToeController {

  private static final int MaxInvalidMoves = 5; // Set a limit for consecutive invalid moves
  private final Readable in;
  private final Appendable out;

  /**
   * Constructor for the controller.
   *
   * @param in  the source to read from
   * @param out the target to print to
   * @throws IllegalArgumentException if either input or output is null
   */
  public TicTacToeConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Readable and Appendable can't be null");
    }
    this.in = in;
    this.out = out;
  }

  @Override
  public void playGame(TicTacToe model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    Scanner scanner = new Scanner(this.in);
    Player currentPlayer = Player.X; // Start with player X
    int invalidMoveCount = 0;  // Track the number of invalid moves

    // Initial display of the game state (without prompting for input)
    appendOutput(model.toString() + "\n");

    // Loop to play the game until a win, tie, or quit
    while (!model.isGameOver()) {
      if (invalidMoveCount >= MaxInvalidMoves) {
        appendOutput("Too many invalid inputs. Ending game.\n");
        appendOutput(model.toString() + "\n");  // Display the final state
        return; // End the game if invalid move limit is reached
      }

      // Prompt for the current player's move
      appendOutput("Enter a move for " + currentPlayer + ":\n");

      // Validate row input
      Integer row = getInput(scanner, "row");
      if (row == null) { // User quits the game
        appendOutput("Game quit! Ending game state:\n");
        appendOutput(model.toString() + "\n");
        return; // End the game if the user quits
      }

      // Validate column input
      Integer col = getInput(scanner, "column");
      if (col == null) { // User quits the game
        appendOutput("Game quit! Ending game state:\n");
        appendOutput(model.toString() + "\n");
        return;  // End the game if the user quits
      }

      // Attempt to make a move in the model
      try {
        model.move(row - 1, col - 1); // Adjust for 0-based index
        appendOutput(model.toString() + "\n");  // Display the updated game state after a valid move
        invalidMoveCount = 0; // Reset invalid move count after a valid move

        // Check if the game is over
        if (model.isGameOver()) {
          appendOutput("Game is over! ");
          if (model.getWinner() != null) {
            appendOutput(model.getWinner() + " wins.\n");
          } else {
            appendOutput("Tie game.\n");
          }
          break;
        }
        currentPlayer = model.getTurn(); // Switch to the next player
      } catch (IllegalArgumentException e) {
        // Handle invalid move (out of bounds or occupied cell)
        appendOutput("Not a valid move: " + row + ", " + col + "\n");

        // Treat out-of-bounds errors as critical and quit the game
        if (isOutOfBounds(row - 1, col - 1, model)) {
          appendOutput("Game quit! Ending game state:\n");
          appendOutput(model.toString() + "\n");
          return;  // End the game if the move was out of bounds
        }

        // Handle the case where the cell is occupied
        appendOutput("Game quit! Ending game state:\n");
        appendOutput(model.toString() + "\n");
        return;  // End the game immediately if the cell is occupied
      }

      // Check if too many invalid moves have been made
      if (invalidMoveCount >= MaxInvalidMoves) {
        appendOutput("Too many invalid inputs. Game quitting.\n");
        appendOutput("Game quit! Ending game state:\n");
        appendOutput(model.toString() + "\n");  // Display the final state
        return;  // End the game after too many invalid inputs
      }
    }
  }

  /**
   * Helper method to append output to the Appendable stream.
   * @param message The message to append to the output stream.
   */
  private void appendOutput(String message) {
    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IllegalStateException("Append failed", e);
    }
  }

  /**
   * Helper method to get and validate input for row or column.
   * Returns {@code null} if the user chooses to quit the game.
   * Continues to prompt for valid input in case of invalid input.
   *
   * @param scanner Scanner object to read input
   * @param prompt The input prompt (either "row" or "column")
   * @return the valid input integer, or {@code null} if the user quits
   */
  private Integer getInput(Scanner scanner, String prompt) {
    while (true) {
      if (!scanner.hasNext()) {
        return null; // If there is no more input
      }

      String input = scanner.next();
      if ("q".equalsIgnoreCase(input)) {
        return null;  // User chooses to quit
      }

      try {
        return Integer.parseInt(input); // Return valid integer input
      } catch (NumberFormatException e) {
        // Output the exact error message for invalid input (non-integer or malformed input)
        appendOutput("Not a valid number: " + input + "\n");
      }
    }
  }

  /**
   * Checks if the given row and column are out of bounds on the Tic Tac Toe board.
   *
   * @param row the row index of the move (0-based)
   * @param col the column index of the move (0-based)
   * @param model the game model to retrieve the board
   * @return {@code true} if the move is out of bounds, {@code false} otherwise
   *
   */
  private boolean isOutOfBounds(int row, int col, TicTacToe model) {
    Player[][] board = model.getBoard();  // Get the current board
    return row < 0 || row >= board.length || col < 0 || col >= board[0].length;
  }
}