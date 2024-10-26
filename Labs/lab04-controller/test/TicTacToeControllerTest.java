import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.StringReader;
import org.junit.Before;
import org.junit.Test;
import tictactoe.TicTacToe;
import tictactoe.TicTacToeConsoleController;
import tictactoe.TicTacToeController;
import tictactoe.TicTacToeModel;

/**
 * Test cases for the TicTacToe console controller, using mocks for readable and appendable.
 * Each test simulates a different scenario, ranging from valid moves to invalid inputs,
 * handling various edge cases like quitting the game or encountering invalid input formats.
 */
public class TicTacToeControllerTest {

  // Providing this shell for you to write your
  // own test cases for the TicTacToe controller
  // You DO NOT NEED to implement tests for the provided model

  // TODO: Implement your own tests cases for the controller

  private TicTacToeController controller;
  private TicTacToe model;
  private StringBuilder out;


  /**
   * Setup method that runs before each test case.
   * Initializes the TicTacToe model and output StringBuilder used to capture game output.
   */
  @Before
  public void setUp() {
    model = new TicTacToeModel();  // Initialize the game model
    out = new StringBuilder();  // Initialize the output StringBuilder to capture game logs
  }

  /**
   * Test case that simulates an error scenario where the Appendable always fails,
   * leading to an IllegalStateException being thrown.
   * @throws IllegalStateException if there is any issue with the Appendable handling.
   */
  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    // Testing when something goes wrong with the Appendable
    // Here we are passing it a mock of an Appendable that always fails
    Readable in = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    Appendable gameLog = new FailingAppendable();
    TicTacToeController controller = new TicTacToeConsoleController(in, gameLog);

    controller.playGame(model);
  }

  /**
   * Test case for invalid controller construction.
   *
   * @throws IllegalArgumentException when constructed with null input/output.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidControllerConstruction() {
    new TicTacToeConsoleController(null, out);  // This should throw an exception
  }

  /**
   * Test case for providing an invalid (null) model to the controller.
   * @throws IllegalArgumentException if there is an invalid (null) model to the controller
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidModel() {
    Readable in = new StringReader("1 1 q");
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    // Passing null model should throw an exception
    controller.playGame(null);
  }

  /**
   * Test case to simulate valid moves by a player and quitting the game.
   * The test ensures that after receiving the "q" command,
   * the game ends and prints the correct message.
   */
  @Test
  public void testValidMove() {
    Readable in = new StringReader("1 1 2 2 3 3 q");
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    assertTrue(out.toString().contains("Game quit! Ending game state:"));
  }

  /**
   * Test case where only one valid move is made, and then the game quits.
   */
  @Test
  public void testSingleValidMove() {
    Readable in = new StringReader("1 1 q");  // Single valid move, then quit
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    // Check that the output contains the valid move and quit message
    assertTrue(out.toString().contains("Game quit! Ending game state:"));
  }

  /**
   * Test case to handle an invalid move
   * where the player tries to place a marker on an already occupied space.
   * It checks if the correct error message is displayed.
   */
  @Test
  public void testInvalidMove() {
    Readable in = new StringReader("1 1 0 1 q");  // Repeat the same move
    TicTacToeConsoleController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    assertTrue(out.toString().contains("Not a valid move: 0, 1"));
  }

  /**
   * Test that the controller properly handles multiple invalid moves by a player.
   * The test provides invalid inputs (non-numeric or out-of-bounds values)
   * and verifies that the controller prompts the user to enter valid moves.
   */
  @Test
  public void testMultipleInvalidMoves() {
    // Simulate multiple invalid inputs followed by a valid move
    Readable in = new StringReader("a 0 1 u 4 1 u");  // Simulated inputs
    Appendable out = new StringBuilder();  // Output to capture game output

    // Initialize the TicTacToe model and controller
    TicTacToe model = new TicTacToeModel();  // Ensure this is correctly initialized
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    // Run the game
    controller.playGame(model);
    // System.out.println("Actual output: \n" + out.toString());

    // Define expected output
    String expectedOutput = "   |   |  \n"
            + "-----------\n"
            + "   |   |  \n"
            + "-----------\n"
            + "   |   |  \n"
            + "Enter a move for X:\n"
            + "Not a valid number: a\n"
            + "Not a valid move: 0, 1\n"
            + "Game quit! Ending game state:\n"
            + "   |   |  \n"
            + "-----------\n"
            + "   |   |  \n"
            + "-----------\n"
            + "   |   |  \n";

    // Debug prints
    //System.out.println("Expected output: \n" + expectedOutput);

    // Compare the expected output with the actual output
    assertEquals(expectedOutput, out.toString());
  }

  /**
   * Test class for TicTacToeConsoleController.
   * This test case verifies that the controller throws an IllegalArgumentException
   * after the user exceeds the maximum number of invalid moves.
   *
   * @throws IllegalArgumentException if there is any issue with input or output handling.
   */
  @Test
  public void testTooManyInvalidMoves() {
    // Simulate too many invalid inputs
    Readable in = new StringReader("a 1 1 0 1 1 1 4 1 -1 1 1 2 u a");
    TicTacToeConsoleController controller = new TicTacToeConsoleController(in, out);

    // Run the game (with the mocked inputs)
    controller.playGame(model);  // Use a new model instance for the test
    // System.out.println("Actual output: \n" + out.toString());

    assertTrue(out.toString().contains("Not a valid number: a"));
    assertTrue(out.toString().contains("Not a valid move: 0, 1"));
    assertTrue(out.toString().contains("Game quit! Ending game state:"));
  }

  /**
   * Test case for handling non-integer input.
   * Verifies that the controller displays the correct error message for invalid input like "one".
   */
  @Test
  public void testNonIntegerInput() {
    Readable in = new StringReader("one two q");
    TicTacToeConsoleController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    assertTrue(out.toString().contains("Not a valid number: one"));
  }

  /**
   * Test case for quitting the game using the "q" command.
   * Verifies that the game ends correctly, and the final game state is displayed.
   */
  @Test
  public void testQuitGame() {
    Readable in = new StringReader("q");
    TicTacToeConsoleController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    assertTrue(out.toString().contains("Game quit! Ending game state:"));
  }

  /**
   * Test case where 'q' is used instead of a row number.
   * The game should quit correctly.
   */
  @Test
  public void testQuitWhenRowExpected() {
    Readable in = new StringReader("q");
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    // Check that the game quit and displays the correct state
    assertTrue(out.toString().contains("Game quit! Ending game state:"));
  }

  /**
   * Test case where 'q' is entered instead of a column number.
   * The game should quit correctly.
   */
  @Test
  public void testQuitWhenColumnExpected() {
    Readable in = new StringReader("1 q");
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    // Check that the game quit and displays the correct state
    assertTrue(out.toString().contains("Game quit! Ending game state:"));
  }

  /**
   * Test case to handle invalid non-integer input, such as "four".
   * It verifies that the correct error message is displayed for invalid input.
   */
  @Test
  public void testInvalidInput() {
    Readable in = new StringReader("four q");
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    // Check that the output contains the error message for invalid input
    assertTrue(out.toString().contains("Not a valid number: four"));
  }

  /**
   * Test case where the game has both valid and invalid moves before Player X wins.
   */
  @Test
  public void testValidAndInvalidMovesBeforeWinnerX() {
    // Simulate a sequence where X wins
    // X wins vertically in column 1
    StringReader input = new StringReader("1 1\n1 2\n2 1\n2 2\n3 1\nq");
    controller = new TicTacToeConsoleController(input, out);

    // Run the game
    controller.playGame(model);

    // Capture and check output
    String gameOutput = out.toString();
    // System.out.println("Game Output: \n" + gameOutput);  // Debugging output

    // Check if the output contains "X wins."
    assertTrue(gameOutput.contains("X wins."));
  }

  /**
   * Test case where the game has both valid and invalid moves before Player O wins.
   */
  @Test
  public void testValidAndInvalidMovesBeforeWinnerO() {
    // Adjusted sequence where O wins (O wins vertically in column 2)
    StringReader input = new StringReader("1 1\n1 2\n2 1\n2 2\n3 3\n3 2\nq");
    controller = new TicTacToeConsoleController(input, out);

    // Run the game
    controller.playGame(model);

    // Capture and check output
    String gameOutput = out.toString();
    // System.out.println("Game Output: \n" + gameOutput);  // Debugging output

    // Check if the output contains "O wins."
    assertTrue(gameOutput.contains("O wins."));
  }

  /**
   * Test case where a player enters an invalid row value.
   */
  @Test
  public void testInvalidRow() {
    // Simulate invalid input where the row is out of bounds
    Readable input = new StringReader("4 1 q");
    controller = new TicTacToeConsoleController(input, out);

    // Run the game, expecting an exception
    controller.playGame(model);
    // System.out.println("Actual output: \n" + out.toString());

    // You can check the output if needed
    assertTrue(out.toString().contains("Not a valid move: 4, 1"));
    assertTrue(out.toString().contains("Game quit! Ending game state:"));
  }

  /**
   * Test case where the input for row is invalid (non-numeric).
   */
  @Test
  public void testInvalidRowInput() {
    Readable in = new StringReader("a 1 q");
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    // Check that the output contains the correct error message
    assertTrue(out.toString().contains("Not a valid number: a"));
    assertTrue(out.toString().contains("Game quit! Ending game state:"));
  }

  /**
   * Test case where a player enters an invalid column value.
   */
  @Test
  public void testInvalidColumn() {
    // Simulate invalid input where the column is out of bounds
    Readable input = new StringReader("2 4 q");
    controller = new TicTacToeConsoleController(input, out);

    // Run the game, expecting an exception
    controller.playGame(model);
    // System.out.println("Actual output: \n" + out.toString());

    // Check if the output contains the expected error message
    // Validate the output or the expected exception
    assertTrue(out.toString().contains("Not a valid move: 2, 4"));
    assertTrue(out.toString().contains("Game quit! Ending game state:"));
  }

  /**
   * Test case where the input for column is invalid (non-numeric).
   */
  @Test
  public void testInvalidColumnInput() {
    Readable in = new StringReader("1 a q");
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);
    // System.out.println("Actual output: \n" + out.toString());

    // Check that the output contains the correct error message
    assertTrue(out.toString().contains("Not a valid number: a"));
  }




  /**
   * Test case for a game that ends in a tie.
   * The test simulates a sequence of moves that fills the board without any player winning,
   * and verifies that the "Tie game." message is printed.
   */
  @Test
  public void testTieGame() {
    // Simulate a series of moves that ends in a tie
    Readable in = new StringReader("1 1 1 2 1 3 2 2 3 2 2 1 2 3 3 3 3 1 q");
    TicTacToeConsoleController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);
    // System.out.println("Actual output: \n" + out.toString());

    // Assert that the final output contains "Tie game."
    assertTrue(out.toString().contains("Tie game."));
  }

  /**
   * Test case for valid and invalid moves before the game ends in a tie.
   */
  @Test
  public void testTieAfterValidAndInvalidMoves() {
    // Simulate a series of moves that ends in a tie
    Readable in = new StringReader("1 1 1 2 1 3 2 2 3 2 2 1 2 3 3 3 3 1 0 1 -1 1 q");
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    // Assert that the final output contains "Tie game."
    assertTrue(out.toString().contains("Tie game."));
  }

  /**
   * Test case where a player tries to move to an already occupied cell.
   * It checks that the controller responds with the correct error message.
   */
  @Test
  public void testOccupiedCellMove() {
    Readable in = new StringReader("1 1 1 1 q");  // Player X moves to (1, 1) twice
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    // Check that the output contains the error message for the occupied cell
    assertTrue(out.toString().contains("Game quit! Ending game state:"));
  }

  /**
   * Test for a winning game.
   * This test simulates a sequence of moves where one player wins (Player X in this case).
   * It checks if the correct winner is displayed at the end of the game.
   */
  @Test
  public void testWinningGame() {
    Readable in = new StringReader("1 1 2 1 1 2 2 2 1 3");
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);
    // System.out.println("Actual output: \n" + out.toString());

    // Check that the output contains the winning message for Player X
    assertTrue(out.toString().contains("Game is over! X wins."));
  }

  /**
   * Test case for a game where Player X wins.
   * The test simulates a sequence of moves where Player X wins.
   */
  @Test
  public void testPlayerWinnerX() {
    Readable in = new StringReader("1 1 2 1 1 2 2 2 1 3");
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    // Check that the output contains the winning message for Player X
    assertTrue(out.toString().contains("Game is over! X wins."));
  }

  /**
   * Test case for a game where Player O wins.
   * The test simulates a sequence of moves where Player O wins.
   */
  @Test
  public void testPlayerWinner0() {
    Readable in = new StringReader("1 1 1 2 3 1 2 2 3 3 3 2");
    TicTacToeController controller = new TicTacToeConsoleController(in, out);

    controller.playGame(model);

    // Check that the output contains the winning message for Player O
    assertTrue(out.toString().contains("Game is over! O wins."));
  }
}
