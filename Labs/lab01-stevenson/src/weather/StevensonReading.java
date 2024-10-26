package weather;

import java.util.Objects;

public final class StevensonReading implements WeatherReading {
    private final double airTemperature; // in Celsius
    private final double dewPoint;       // in Celsius, cannot be greater than air temperature
    private final double windSpeed;      // in miles per hour, non-negative
    private final double totalRain;      // in millimeters, non-negative

    // Constructor
    public StevensonReading(double airTemperature, double dewPoint, double windSpeed, double totalRain) {
        if (dewPoint > airTemperature) {
            throw new IllegalArgumentException("Dew point cannot be greater than air temperature.");
        }
        if (windSpeed < 0) {
            throw new IllegalArgumentException("Wind speed cannot be negative.");
        }
        if (totalRain < 0) {
            throw new IllegalArgumentException("Total rain cannot be negative.");
        }

        this.airTemperature = airTemperature;
        this.dewPoint = dewPoint;
        this.windSpeed = windSpeed;
        this.totalRain = totalRain;
    }

    // Implementing the methods from the WeatherReading interface
    @Override
    public int getTemperature() {
        return (int) Math.round(airTemperature);
    }

    @Override
    public int getDewPoint() {
        return (int) Math.round(dewPoint);
    }

    @Override
    public int getWindSpeed() {
        return (int) Math.round(windSpeed);
    }

    @Override
    public int getTotalRain() {
        return (int) Math.round(totalRain);
    }

    @Override
    public int getRelativeHumidity() {
        // Calculate relative humidity and round to the nearest integer
        double humidity = 100 * (Math.exp((17.625 * dewPoint) / (243.04 + dewPoint)) /
                Math.exp((17.625 * airTemperature) / (243.04 + airTemperature)));
        int roundedHumidity = (int) Math.round(humidity);

        // Throw exception if humidity is outside the 0-100% range
        if (roundedHumidity < 0 || roundedHumidity > 100) {
            throw new IllegalArgumentException("Relative humidity must be between 0% and 100%.");
        }
        return roundedHumidity;
    }



    @Override
    public int getHeatIndex() {
        double T = airTemperature;
        double R = getRelativeHumidity();

        // Coefficients for Heat Index formula
        double c1 = -8.78469475556;
        double c2 = 1.61139411;
        double c3 = 2.33854883889;
        double c4 = -0.14611605;
        double c5 = -0.012308094;
        double c6 = -0.0164248277778;
        double c7 = 0.002211732;
        double c8 = 0.00072546;
        double c9 = -0.000003582;

        double heatIndex = c1 + (c2 * T) + (c3 * R) + (c4 * T * R) + (c5 * T * T) + (c6 * R * R)
                + (c7 * T * T * R) + (c8 * T * R * R) + (c9 * T * T * R * R);
        return (int) Math.round(heatIndex);
    }

    @Override
    public int getWindChill() {
        double T = (airTemperature * 9/5) + 32; // Convert Celsius to Fahrenheit
        double v = windSpeed;

        if (T > 50 || v <= 3) {
            return (int) Math.round(T); // Wind chill calculation not applicable; return the temperature in Fahrenheit
        }

        // Wind Chill formula
        double windChill = 35.74 + (0.6215 * T) - (35.75 * Math.pow(v, 0.16)) + (0.4275 * T * Math.pow(v, 0.16));
        return (int) Math.round(windChill); // Return the temperature in Fahrenheit if wind chill is not applicable
    }

    @Override
    public String toString() {
        return String.format("Reading: T = %d, D = %d, v = %d, rain = %d",
                getTemperature(), getDewPoint(), getWindSpeed(), getTotalRain());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // If o isn't the right class then it can't be equal:
        if (!(o instanceof StevensonReading)) return false;
        StevensonReading that = (StevensonReading) o;
        return Double.compare(that.airTemperature, airTemperature) == 0 &&
                Double.compare(that.dewPoint, dewPoint) == 0 &&
                Double.compare(that.windSpeed, windSpeed) == 0 &&
                Double.compare(that.totalRain, totalRain) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(airTemperature, dewPoint, windSpeed, totalRain);
    }
}
