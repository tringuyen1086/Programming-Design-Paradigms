import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import transmission.AutomaticTransmission;

/**
 * Unit tests for the AutomaticTransmission class using JUnit 4.
 * These tests validate various behaviors such as speed increases, gear shifts,
 * speed decreases, exception handling, and string representation of the transmission state.
 */
public class AutomaticTransmissionTest {

  private AutomaticTransmission transmission;

  /**
   * Sets up an AutomaticTransmission instance with valid speed thresholds before each test.
   * This method runs before every test to ensure the transmission is initialized correctly.
   */
  @Before
  public void setUp() {
    // Initialize a transmission with valid speed thresholds for testing
    transmission = new AutomaticTransmission(10, 20, 30, 40, 50);
  }

  /**
   * Test that verifies the constructor throws an IllegalArgumentException
   * when the first threshold is 0.
   * The transmission requires that the first threshold be strictly greater than 0,
   * as speed 0 corresponds to the idle gear.
   *
   * @throws IllegalArgumentException when the first threshold is 0.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithZeroThreshold() {
    AutomaticTransmission transmission = new AutomaticTransmission(0, 20, 30, 40, 50);
    assertEquals(0, transmission.getSpeed());
    assertEquals(0, transmission.getGear());  // Transmission should be in idle state
  }

  /**
   * Test that verifies the constructor throws an IllegalArgumentException when the first
   * threshold is negative. Thresholds must be positive and strictly increasing, and the
   * first threshold must be greater than 0.
   *
   * @throws IllegalArgumentException when the first threshold is negative.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testConstructorWithNegativeThreshold() {
    new AutomaticTransmission(-1, 20, 30, 40, 50);  // Should throw IllegalArgumentException
  }

  /**
   * Test to verify that the transmission starts in an idle state with speed = 0 and gear = 0.
   * This ensures the constructor initializes the object properly.
   */
  @Test
  public void testInitialState() {
    // Assert that the transmission starts with speed 0 and gear 0 (idle state)
    assertEquals(0, transmission.getSpeed());
    assertEquals(0, transmission.getGear());
  }

  /**
   * Test to verify that increasing the speed appropriately changes the gear
   * according to the specified thresholds.
   * Speeds from 1 to 9 should stay in gear 1,
   * speed 10 should switch to gear 2,
   * speed 20 should switch to gear 3,
   * speed 30 should switch to gear 4,
   * speed 40 should switch to gear 5
   * until reaching gear 6 at speeds beyond 50.
   */
  @Test
  public void testIncreaseSpeedAndGearShift() {
    // From speed 1 to 9, the car should remain in gear 1
    for (int i = 1; i <= 9; i++) {
      transmission.increaseSpeed();
      assertEquals(i, transmission.getSpeed());
      assertEquals(1, transmission.getGear());
    }

    // Speed 10 should shift the car to gear 2
    transmission.increaseSpeed(); // increase the current speed to 10
    assertEquals(10, transmission.getSpeed());
    assertEquals(2, transmission.getGear());

    // Continue increasing current speed 10 to speed 49
    for (int i = transmission.getSpeed(); i < 49; i++) {
      transmission.increaseSpeed();
    }
    // Increase speed and verify gear shift to gear 6 at speed 50
    transmission.increaseSpeed(); // increase speed to 50
    assertEquals(50, transmission.getSpeed());
    assertEquals(6, transmission.getGear());  // At speed 50, the car should be in gear 6
  }

  /**
   * Test to verify that decreasing the speed appropriately shifts the gear down.
   * The test starts by increasing the speed to a higher gear, then decreases speed
   * to ensure the gear adjusts correctly at the thresholds.
   */
  @Test
  public void testDecreaseSpeedAndGearShift() {
    // Increase speed to 30 to reach gear 4
    for (int i = 0; i < 30; i++) {
      transmission.increaseSpeed();
    }

    // Decrease speed to 29 should shift the car to gear 3
    transmission.decreaseSpeed();
    assertEquals(29, transmission.getSpeed());
    assertEquals(3, transmission.getGear());

    // Decrease speed and check if the gear shifts down correctly
    transmission.decreaseSpeed();
    assertEquals(28, transmission.getSpeed());
    assertEquals(3, transmission.getGear());

    // Continue decreasing current speed (speed 28) and verify gear shift to gear 2 at speed 19
    for (int i = 0; i < 9; i++) {
      transmission.decreaseSpeed();
    }
    assertEquals(19, transmission.getSpeed());
    assertEquals(2, transmission.getGear());

    // Continue decreasing current speed (speed 19) and verify gear shift to gear 1 at speed 9
    for (int i = 0; i < 10; i++) {
      transmission.decreaseSpeed();
    }
    assertEquals(9, transmission.getSpeed());
    assertEquals(1, transmission.getGear());
  }

  /**
   * Test to verify that an IllegalStateException is thrown when decreasing speed below 0.
   * This test ensures that the transmission does not allow negative speed values.
   *
   * @throws IllegalStateException when attempting to decrease speed below 0.
   */
  @Test(expected = IllegalStateException.class)
  public void testDecreaseSpeedBelowZeroThrowsException() {
    // Attempting to decrease speed when it is already 0 should throw an IllegalStateException
    transmission.decreaseSpeed();  // This should throw the expected exception
  }

  /**
   * Test to verify that providing invalid thresholds during transmission initialization
   * (i.e., thresholds not in strictly increasing order) throws an IllegalArgumentException.
   * This ensures that the transmission is created with valid gear thresholds.
   *
   * @throws IllegalArgumentException if the speed thresholds are not strictly increasing.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidThresholdsThrowException() {
    // Initialize a transmission with invalid thresholds (thresholds not strictly increasing)
    new AutomaticTransmission(10, 20, 20, 30, 40);  // Should throw IllegalArgumentException
  }

  /**
   * Test to verify the correct output of the toString method.
   * The toString method should return a string representing the current state of the transmission,
   * including the speed and gear.
   */
  @Test
  public void testToString() {
    // Initially, the transmission should be in idle (speed = 0, gear = 0)
    assertEquals("Transmission (speed = 0, gear = 0)", transmission.toString());

    // Increase speed to 1 and verify the string representation
    transmission.increaseSpeed();
    assertEquals("Transmission (speed = 1, gear = 1)", transmission.toString());

    // Further increase speed and check the representation
    for (int i = 0; i < 20; i++) {
      transmission.increaseSpeed();
    }
    assertEquals("Transmission (speed = 21, gear = 3)", transmission.toString());
  }
}