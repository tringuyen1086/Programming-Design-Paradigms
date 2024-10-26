import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import tictactoe.Player;
import tictactoe.TicTacToeModel;

/**
 * Unit tests for the TicTacToeModel class.
 * These tests verify that the TicTacToe game logic is correctly implemented.
 */
public class TicTacToeModelTest {

  private TicTacToeModel game;

  /**
   * Setup a new TicTacToeModel before each test.
   * This ensures a fresh game instance is used in every test case.
   */
  @Before
  public void setUp() {
    game = new TicTacToeModel(); // Create a new game instance before each test
  }

  /**
   * Test the constructor of the TicTacToeModel class.
   * This ensures that when a new game is created:
   * - The board is empty
   * - The current player is Player X
   * - The game is not over
   * - There is no winner
   */
  @Test
  public void testConstructor() {
    assertFalse(game.isGameOver());         // Game should not be over
    assertEquals(Player.X, game.getTurn()); // Player X starts
    assertNull(game.getWinner());           // No winner initially
    Player[][] board = game.getBoard();     // Board should be empty
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        assertNull(board[r][c]);
      }
    }
  }

  /**
   * Test making a valid move.
   * Player X should place a mark, and then it should be Player O's turn.
   */
  @Test
  public void testValidMove() {
    // Player X makes a move at (0, 0)
    game.move(0, 0);
    assertEquals(Player.X, game.getMarkAt(0, 0)); // The mark should be X
    assertEquals(Player.O, game.getTurn());            // It should now be Player O's turn
  }

  /**
   * Test an invalid move (moving to an already occupied space).
   * This should throw an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMoveSpaceOccupied() {
    game.move(0, 0); // Player X marks (0, 0)
    game.move(0, 0); // Player O tries to mark the same spot
  }

  /**
   * Test an invalid move: moving to an out-of-bounds position.
   * This should throw an IllegalArgumentException.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testMoveOutOfBounds() {
    game.move(-1, 0); // Invalid move
  }

  /**
   * Test making a move after the game is over.
   * This should throw an IllegalStateException.
   */
  @Test(expected = IllegalStateException.class)
  public void testMoveAfterGameOver() {
    // Simulate a winning game for Player X
    game.move(0, 0); // X
    game.move(1, 0); // O
    game.move(0, 1); // X
    game.move(1, 1); // O
    game.move(0, 2); // X wins

    // Attempt to make a move after the game is over
    game.move(2, 2); // This should throw IllegalStateException
  }

  /**
   * Test a winning move.
   * After a player wins, the game should be over and no more moves should be allowed.
   */
  @Test(expected = IllegalStateException.class)
  public void testWinningMove() {
    // Simulate a game where Player X wins by filling the top row
    game.move(0, 0); // X
    game.move(1, 0); // O
    game.move(0, 1); // X
    game.move(1, 1); // O
    game.move(0, 2); // X wins

    // Check that Player X is the winner
    assertTrue(game.isGameOver());
    assertEquals(Player.X, game.getWinner());

    // No further moves should be allowed after the game is over
    game.move(2, 2); // Expected IllegalStateException
  }

  /**
   * Test a game where it ends in a tie.
   * The game should correctly detect that the board is full and that there is no winner.
   */
  @Test(expected = IllegalStateException.class)
  public void testTieGame() {
    // Simulate a game that ends in a tie
    game.move(0, 0); // X
    game.move(0, 1); // O
    game.move(0, 2); // X
    game.move(1, 1); // O
    game.move(1, 0); // X
    game.move(1, 2); // O
    game.move(2, 1); // X
    game.move(2, 0); // O
    game.move(2, 2); // X (Board is now full)

    // The game should be over, but there should be no winner
    assertTrue(game.isGameOver());
    assertNull(game.getWinner());

    // No further moves should be allowed after the game is over
    game.move(2, 2); // Expected IllegalStateException

  }

  /**
   * Test that turns alternate correctly after each valid move.
   * Player X should move first, followed by Player O,
   * and turns should alternate.
   */
  @Test
  public void testTurnSwitching() {
    // Initial turn should be Player X
    assertEquals(Player.X, game.getTurn());

    // Player X moves at (0, 0)
    game.move(0, 0);
    // It should now be Player O's turn
    assertEquals(Player.O, game.getTurn());

    // Player O moves at (1, 1)
    game.move(1, 1);
    // It should now be Player X's turn again
    assertEquals(Player.X, game.getTurn());
  }

  /**
   * Test that getTurn() returns the correct player when the game is ongoing.
   * Player X should go first, followed by Player O after a valid move.
   */
  @Test
  public void testGetTurnDuringGame() {
    // Initially, it should be Player X's turn
    assertEquals(Player.X, game.getTurn());

    // Player X makes a move, then it should be Player O's turn
    game.move(0, 0);
    assertEquals(Player.O, game.getTurn());
  }

  /**
   * Test that getTurn() throws an IllegalStateException
   * when the game is over due to a win.
   * Once the game is over, there should be no more turns.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetTurnAfterWin() {
    // Simulate a winning scenario for Player X
    game.move(0, 0); // X
    game.move(1, 0); // O
    game.move(0, 1); // X
    game.move(1, 1); // O
    game.move(0, 2); // X wins

    // Now that the game is over, getTurn() should throw an IllegalStateException
    game.getTurn();
  }

  /**
   * Test that getTurn() throws an IllegalStateException
   * when the game is over due to a tie.
   * Once the game is over with a tie, there should be no more turns.
   */
  @Test(expected = IllegalStateException.class)
  public void testGetTurnAfterTie() {
    // Simulate a tie game (fill the board with no winner)
    game.move(0, 0); // X
    game.move(0, 1); // O
    game.move(0, 2); // X
    game.move(1, 1); // O
    game.move(1, 0); // X
    game.move(1, 2); // O
    game.move(2, 1); // X
    game.move(2, 0); // O
    game.move(2, 2); // X (board is full)

    // Now that the game is tied, getTurn() should throw an IllegalStateException
    game.getTurn();
  }

  /**
   * Test the isGameOver() method.
   * It should return false until the game is over due to a win or a tie.
   */
  @Test
  public void testIsGameOver() {
    // Initially, the game should not be over
    assertFalse(game.isGameOver());

    // Simulate a game where Player X wins
    game.move(0, 0); // X
    game.move(1, 0); // O
    game.move(0, 1); // X
    game.move(1, 1); // O
    game.move(0, 2); // X wins

    // The game should be over after X wins
    assertTrue(game.isGameOver());
  }

  /**
   * Test the getWinner() method.
   * It should return null if there is no winner
   * and the correct player if a player wins.
   */
  @Test
  public void testGetWinner() {
    // Initially, there should be no winner
    assertNull(game.getWinner());

    // Simulate a game where Player X wins
    game.move(0, 0); // X
    game.move(1, 0); // O
    game.move(0, 1); // X
    game.move(1, 1); // O
    game.move(0, 2); // X wins

    // The winner should be Player X
    assertEquals(Player.X, game.getWinner());
  }

  /**
   * Test the getBoard() method.
   * It should return the current state of the game board.
   */
  @Test
  public void testGetBoard() {
    // The board should initially be empty
    Player[][] board = game.getBoard();
    // Ensure the board is empty
    for (int r = 0; r < 3; r++) {
      for (int c = 0; c < 3; c++) {
        assertNull(board[r][c]);
      }
    }

    // After making a move, the board should reflect the move
    game.move(0, 0); // X moves
    board = game.getBoard();
    assertEquals(Player.X, board[0][0]);

    // Ensure modifying the returned board does not affect the internal state
    board[0][0] = Player.O;
    // The internal board should still have Player X
    assertEquals(Player.X, game.getMarkAt(0, 0));
  }

  /**
   * Test the getMarkAt() method.
   * It should return the player mark at the given position or null if the position is empty.
   */
  @Test
  public void testGetMarkAtValidPositions() {
    // Initially, the board should be empty
    assertNull(game.getMarkAt(0, 0));

    // After making a move, the board should reflect the move
    game.move(0, 0); // X moves
    assertEquals(Player.X, game.getMarkAt(0, 0));

    // Another move at a different location
    game.move(1, 1); // O moves
    assertEquals(Player.O, game.getMarkAt(1, 1));
  }

  /**
   * Test getMarkAt() with an invalid row (out of bounds).
   * It should throw an IllegalArgumentException
   * when the row index is out of bounds.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetMarkAtInvalidRow() {
    // Try to get the mark at an out-of-bounds position (-1, 0)
    game.getMarkAt(-1, 0);
  }

  /**
   * Test getMarkAt() with invalid column (out of bounds).
   * It should throw an IllegalArgumentException
   * when the column index is out of bounds.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testGetMarkAtInvalidColumn() {
    // Try to get the mark at an out-of-bounds position (0, 3)
    game.getMarkAt(0, 3);
  }

  /**
   * Test checkWinner() indirectly via move().
   * This tests if Player X wins by completing the top row, which involves checkRow().
   */
  @Test
  public void testCheckWinnerTopRow() {
    // Player X makes moves to complete the top row and win
    game.move(0, 0); // X
    game.move(1, 0); // O
    game.move(0, 1); // X
    game.move(1, 1); // O
    game.move(0, 2); // X wins by row

    // The game should be over and Player X should be the winner
    assertTrue(game.isGameOver());
    assertEquals(Player.X, game.getWinner());
  }

  /**
   * Test checkWinner() indirectly via move().
   * This tests if Player O wins by completing the first column, which involves checkColumn().
   */
  @Test
  public void testCheckWinnerFirstColumn() {
    // Player O makes moves to complete the first column and win
    game.move(0, 1); // X
    game.move(0, 0); // O
    game.move(1, 1); // X
    game.move(1, 0); // O
    game.move(2, 2); // X
    game.move(2, 0); // O wins by column

    // The game should be over and Player O should be the winner
    assertTrue(game.isGameOver());
    assertEquals(Player.O, game.getWinner());
  }

  /**
   * Test checkWinner() indirectly via move().
   * This tests if Player X wins by completing a diagonal, which involves checkDiagonals().
   */
  @Test
  public void testCheckWinnerDiagonal() {
    // Player X makes moves to complete the diagonal and win
    game.move(0, 0); // X
    game.move(0, 1); // O
    game.move(1, 1); // X
    game.move(0, 2); // O
    game.move(2, 2); // X wins by diagonal

    // The game should be over and Player X should be the winner
    assertTrue(game.isGameOver());
    assertEquals(Player.X, game.getWinner());
  }

  /**
   * Test isBoardFull() indirectly via move().
   * This tests if the board is full after all spaces are filled and there is no winner (tie game).
   */
  @Test
  public void testIsBoardFull() {
    // Simulate a tie game by filling the board without any player winning
    game.move(0, 0); // X
    game.move(0, 1); // O
    game.move(0, 2); // X
    game.move(1, 1); // O
    game.move(1, 0); // X
    game.move(1, 2); // O
    game.move(2, 1); // X
    game.move(2, 0); // O
    game.move(2, 2); // X (board is now full, no winner)

    // The game should be over due to a full board, but there should be no winner
    assertTrue(game.isGameOver()); // Game is over because the board is full
    assertNull(game.getWinner()); // No winner in a tie
  }

  /**
   * Test checkWinner() when no player has won.
   * The game should continue, and isGameOver() should return false.
   */
  @Test
  public void testNoWinner() {
    // Make a few moves but no one has won yet
    game.move(0, 0); // X
    game.move(1, 0); // O
    game.move(0, 1); // X
    game.move(1, 1); // O

    // The game should not be over, and there should be no winner
    assertFalse(game.isGameOver());
    assertNull(game.getWinner());
  }

  /**
   * Test the toString() method.
   * It should return a formatted string representation of the current board state.
   */
  @Test
  public void testToString() {
    TicTacToeModel game = new TicTacToeModel();
    game.move(0, 0); // X moves
    game.move(1, 1); // O moves

    String expectedOutput = " X |   |  \n-----------\n   | O |  \n-----------\n   |   |  ";
    assertEquals(expectedOutput, game.toString());
  }

  /**
   * Test the equality of two TicTacToeModel objects.
   * Two games should be considered equal if their boards, current players, winners,
   * and game states are the same.
   */
  @Test
  public void testEqualsAndHashCode() {
    TicTacToeModel game1 = new TicTacToeModel();
    TicTacToeModel game2 = new TicTacToeModel();

    // Both games are in the initial state, so they should be equal
    assertEquals(game1, game2);
    assertEquals(game1.hashCode(), game2.hashCode());

    // Make a move in game1, now they should not be equal
    game1.move(0, 0);
    assertNotEquals(game1, game2);
  }
}