package weather;

public class Main {
    public static void main(String[] args) {
        // Create instances of StevensonReading with various inputs
        StevensonReading reading1 = new StevensonReading(25.0, 15.0, 10.0, 5.0);
        StevensonReading reading2 = new StevensonReading(30.0, 25.0, 8.0, 2.0);
        StevensonReading reading3 = new StevensonReading(10.0, 5.0, 20.0, 0.0);

        // Display information for reading1
        System.out.println("Weather Reading 1:");
        displayReadingInfo(reading1);

        // Display information for reading2
        System.out.println("\nWeather Reading 2:");
        displayReadingInfo(reading2);

        // Display information for reading3
        System.out.println("\nWeather Reading 3:");
        displayReadingInfo(reading3);
    }

    // Method to display various readings and calculations
    private static void displayReadingInfo(StevensonReading reading) {
        System.out.println(reading.toString());
        System.out.printf("Temperature (C): %d%n", reading.getTemperature());
        System.out.printf("Dew Point (C): %d%n", reading.getDewPoint());
        System.out.printf("Wind Speed (mph): %d%n", reading.getWindSpeed());
        System.out.printf("Total Rain (mm): %d%n", reading.getTotalRain());
        System.out.printf("Relative Humidity (%%): %d%n", reading.getRelativeHumidity());
        System.out.printf("Heat Index (C): %d%n", reading.getHeatIndex());
        System.out.printf("Wind Chill (C): %d%n", reading.getWindChill());
    }
}

