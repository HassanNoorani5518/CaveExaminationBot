package ca.teambot.it.cave.examination.bot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class DataGeneration
{
    private static final Random random = new Random();

    public static String generateTemperature() {
        // Range: 10 to 30 °C
        double i = 10 + random.nextDouble() * 20;
        return String.valueOf(i);
    }

    public static String generateGasConcentration() {
        // Assume normal air with a variation
        // Oxygen: 20.5 - 21.5%
        // Nitrogen: 77.5 - 78.5%
        // Argon: 0.8 - 1.2%
        double i = 20.5 + random.nextDouble() * 1;
        return String.valueOf(i);
    }

    public static String generateHumidity() {
        // Range: 20 to 80%
        double i = 20 + random.nextDouble() * 60;
        return String.valueOf(i);
    }

    public static String generateAirPressure() {
        // Range: 1000 to 1100 hPa
        double i = 1000 + random.nextDouble() * 100;
        return String.valueOf(i);
    }

    public static String generateMagneticField() {
        // Range: 25 to 65 µT
        double i = 25 + random.nextDouble() * 40;
        return String.valueOf(i);
    }

    // Cave integrity (1: Stable, 2: Slight Instability, 3: Moderate Instability, 4: High Instability)
    public static String generateCaveIntegrity() {
        // Range: 1 to 4
        int i = 1 + random.nextInt(4);
        return String.valueOf(i);
    }

    public String getCurrentTime() {
        // Create a Calendar instance
        Calendar calendar = Calendar.getInstance();

        // Get the current time
        Date currentTime = calendar.getTime();

        // Define a format for the time
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss MM/dd/yyyy", Locale.getDefault());

        // Format the current time using the specified format
        return dateFormat.format(currentTime);
    }
}
