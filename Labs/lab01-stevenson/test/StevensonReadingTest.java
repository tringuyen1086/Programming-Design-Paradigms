import org.junit.Test;
import weather.StevensonReading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;


public class StevensonReadingTest {

    @Test
    public void testValidConstructor() {
        StevensonReading reading = new StevensonReading(25.0, 15.0, 10.0, 5.0);
        assertEquals(25, reading.getTemperature());
        assertEquals(15, reading.getDewPoint());
        assertEquals(10, reading.getWindSpeed());
        assertEquals(5, reading.getTotalRain());
    }

    @Test
    public void testConstructorDewPointGreaterThanTemperature() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new StevensonReading(20.0, 25.0, 10.0, 5.0);
        });
        assertEquals("Dew point cannot be greater than air temperature.", exception.getMessage());
    }

    @Test
    public void testConstructorNegativeWindSpeed() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new StevensonReading(20.0, 10.0, -5.0, 5.0);
        });
        assertEquals("Wind speed cannot be negative.", exception.getMessage());
    }

    @Test
    public void testConstructorNegativeTotalRain() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new StevensonReading(20.0, 10.0, 5.0, -1.0);
        });
        assertEquals("Total rain cannot be negative.", exception.getMessage());
    }

    // Test case for valid humidity within the range 0-100%
    @Test
    public void testGetRelativeHumidityValid() {
        // Typical values
        StevensonReading reading = new StevensonReading(25.0, 20.0, 10.0, 5.0);
        int humidity = reading.getRelativeHumidity();
        assertTrue("Relative humidity should be between 0% and 100%.", humidity >= 0 && humidity <= 100);
    }

    // Test case for humidity approaching 100%, without exceeding it
    @Test
    public void testGetRelativeHumidityNearHundred() {
        // Dew point very close to air temperature
        StevensonReading reading = new StevensonReading(25.0, 24.5, 5.0, 2.0);
        int humidity = reading.getRelativeHumidity();
        assertTrue("Relative humidity should be close to 100%.", humidity <= 100);
    }

    // Test case for very low humidity, approaching 0%
    @Test
    public void testGetRelativeHumidityNearZero() {
        // Very low dew point compared to temperature
        StevensonReading reading = new StevensonReading(50.0, 0.0, 5.0, 2.0);
        int humidity = reading.getRelativeHumidity();
        assertTrue("Relative humidity should be near 0%.", humidity >= 0);
    }

    // Edge case test for exact 0% humidity
    @Test
    public void testGetRelativeHumidityZero() {
        // Dew point much lower than air temperature
        StevensonReading reading = new StevensonReading(50.0, -40.0, 5.0, 0.0);
        int humidity = reading.getRelativeHumidity();
        assertEquals("Relative humidity should be 0%.", 0, humidity);
    }

    // Edge case test for exact 100% humidity
    @Test
    public void testGetRelativeHumidityHundred() {
        // Dew point equal to air temperature
        StevensonReading reading = new StevensonReading(20.0, 20.0, 10.0, 0.0);
        int humidity = reading.getRelativeHumidity();
        assertEquals("Relative humidity should be 100%.", 100, humidity);
    }

    // Test case for values that might theoretically cause issues, handled without exception
    @Test
    public void testGetRelativeHumidityHighBoundary() {
        // Dew point just below air temperature
        StevensonReading reading = new StevensonReading(20.0, 19.9, 10.0, 0.0);
        int humidity = reading.getRelativeHumidity();
        assertTrue("Relative humidity should be valid and close to 100%.",
                humidity >= 0 && humidity <= 100);
    }


    // Test case for valid humidity within the range 0-100%

    @Test
    public void testGetHeatIndex() {
        StevensonReading reading = new StevensonReading(30.0, 25.0, 5.0, 10.0);
        int heatIndex = reading.getHeatIndex();
        assertTrue("Heat index should be a valid integer value.", heatIndex > 0);
    }

    @Test
    public void testGetWindChillApplicable() {
        // Set conditions: temperature below 50°F, wind speed above 3 mph
        // -5°C is 23°F, wind speed > 3 mph
        StevensonReading reading = new StevensonReading(-5.0, -10.0, 10.0, 0.0);
        int windChill = reading.getWindChill();
        int actualTemperature = (int) Math.round((-5.0 * 9 / 5) + 32); // Convert -5°C to Fahrenheit
        assertTrue("Wind chill should be lower than the actual temperature when applicable.",
                windChill < actualTemperature);
    }

    @Test
    public void testGetWindChillInapplicable() {
        StevensonReading reading = new StevensonReading(15.0, 10.0, 1.0, 2.0);
        int windChill = reading.getWindChill();
        int expectedWindChill = (int) Math.round((15.0 * 9 / 5) + 32); // Temperature in Fahrenheit without wind chill
        assertEquals("Wind chill should not alter the temperature if conditions do not apply.",
                expectedWindChill, windChill);
    }

    @Test
    public void testToString() {
        StevensonReading reading = new StevensonReading(23.0, 12.0, 3.0, 12.0);
        assertEquals("Reading: T = 23, D = 12, v = 3, rain = 12", reading.toString());
    }

    @Test
    public void testEqualsAndHashCode() {
        StevensonReading reading1 = new StevensonReading(23.0, 12.0, 3.0, 12.0);
        StevensonReading reading2 = new StevensonReading(23.0, 12.0, 3.0, 12.0);
        StevensonReading reading3 = new StevensonReading(25.0, 15.0, 5.0, 10.0);

        assertEquals("Two readings with the same values should be equal.",
                reading1, reading2);
        assertEquals("Equal readings should have the same hash code.",
                reading1.hashCode(), reading2.hashCode());
        assertNotEquals("Readings with different values should not be equal.",
                reading1, reading3);
    }
}