package transmission;

/**
 * The AutomaticTransmission class represents an automatic car transmission system.
 * It automatically shifts gears based on the speed of the car.
 *
 * The transmission starts in an idle state (gear 0) and shifts gears as the car accelerates
 * past predefined speed thresholds. It also allows speed to be decreased with automatic
 * gear adjustment.
 *
 * There are 6 possible gears: idle (0) and gears 1 through 6. The thresholds for shifting
 * between gears 1 to 6 are provided during instantiation and must be strictly increasing.
 */
public class AutomaticTransmission implements Transmission {
  private static final int IDLE_GEAR = 0;
  private int speed;  // The current speed of the car
  private int gear;   // The current gear of the car
  private final int[] speedThresholds;  // Speed thresholds for gear changes


  /**
   * Constructor for an AutomaticTransmission.
   *
   * @param threshold1 the speed at which the transmission shifts from gear 1 to gear 2
   * @param threshold2 the speed at which the transmission shifts from gear 2 to gear 3
   * @param threshold3 the speed at which the transmission shifts from gear 3 to gear 4
   * @param threshold4 the speed at which the transmission shifts from gear 4 to gear 5
   * @param threshold5 the speed at which the transmission shifts from gear 5 to gear 6
   * @throws IllegalArgumentException if any thresholds are not in strictly increasing order
   */
  public AutomaticTransmission(int threshold1, int threshold2,
                               int threshold3, int threshold4, int threshold5) {
    // Ensure that thresholds are in strictly increasing order
    if (threshold1 <= 0 || threshold1 >= threshold2 || threshold2 >= threshold3
        || threshold3 >= threshold4 || threshold4 >= threshold5) {
      throw new IllegalArgumentException("Thresholds must be in strictly increasing order, "
              + "and the first threshold must be greater than 0.");
    }

    this.speedThresholds = new int[]{threshold1, threshold2, threshold3, threshold4, threshold5};
    this.speed = 0;
    this.gear = 0;  // Initially in idle state
  }

  /**
   * Helper method to update the current gear based on the speed.
   * This method automatically adjusts the gear as the speed increases or decreases.
   */
  private void updateGear() {
    if (speed == 0) {
      gear = IDLE_GEAR;  // Idle state
    } else if (speed < speedThresholds[0]) {
      gear = 1;
    } else if (speed < speedThresholds[1]) {
      gear = 2;
    } else if (speed < speedThresholds[2]) {
      gear = 3;
    } else if (speed < speedThresholds[3]) {
      gear = 4;
    } else if (speed < speedThresholds[4]) {
      gear = 5;
    } else {
      gear = 6;
    }
  }

  /**
   * Increases the speed of the car by 1 MPH.
   * The gear is automatically adjusted based on the new speed.
   */
  @Override
  public void increaseSpeed() {
    speed++;
    updateGear();
  }

  /**
   * Decreases the speed of the car by 1 MPH.
   * The gear is automatically adjusted based on the new speed.
   *
   * @throws IllegalStateException if decreasing the speed would result in a negative speed
   */
  @Override
  public void decreaseSpeed() {
    if (speed == 0) {
      throw new IllegalStateException("Speed cannot be negative.");
    }
    speed--;
    updateGear();
  }

  /**
   * Returns the current speed of the transmission.
   *
   * @return the current speed of the car
   */
  @Override
  public int getSpeed() {
    return speed;
  }

  /**
   * Returns the current gear of the transmission.
   *
   * @return the current gear of the car
   */
  @Override
  public int getGear() {
    return gear;
  }

  /**
   * Returns a string representation of the transmission state, showing the current speed and gear.
   *
   * @return the current transmission state in the format "Transmission (speed = X, gear = Y)"
   */
  @Override
  public String toString() {
    return String.format("Transmission (speed = %d, gear = %d)", speed, gear);
  }
}
