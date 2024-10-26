/**
 * This is the main entry point for running the TicTacToe game via the console.
 * It sets up the necessary components for the game, including a controller
 * and a model, and starts the game loop where players can input their moves.
 */

package tictactoe;

import java.io.InputStreamReader;

/**
 * The Main class serves as the entry point for
 * running the TicTacToe game interactively via the console.
 * It sets up the game by creating
 * a {@link TicTacToeConsoleController} and a {@link TicTacToeModel},
 * allowing users to play the game in a simple text-based interface.
 * <p>
 * Users input their moves via the console,
 * and the current state of the game is displayed after each move.
 * The game follows a Model-View-Controller (MVC) architecture:
 * <ul>
 *   <li>The model manages the game's logic and state.</li>
 *   <li>The controller processes user input and updates the model accordingly.</li>
 *   <li>The view (represented as console output) displays the game's current state.</li>
 * </ul>
 * Players can quit the game at any time by entering 'q'.
 * </p>
 */

public class Main {
  /**
   * The main method starts the TicTacToe game,
   * setting up the input and output streams for user interaction via the console.
   * The game runs in an interactive loop, processing user moves
   * and updating the game board until a player wins, a tie occurs, or the user quits
   * by entering 'q'.
   *
   * @param args command-line arguments (not used in this implementation).
   */
  public static void main(String[] args) {
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;
    new TicTacToeConsoleController(input, output).playGame(new TicTacToeModel());
  }
}
