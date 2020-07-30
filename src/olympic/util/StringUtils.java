package olympic.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Handles everything text, formatting and generally string related.
 */
public class StringUtils {

    /**
     * Header of .db file. Inserted at beginning.
     */
    public static final String CSV_HEADER = "\"ID\",\"Name\",\"Sex\",\"Age\",\"Height\",\"Weight\",\"Team\",\"NOC\",\"Games\",\"Year\",\"Season\",\"City\",\"Sport\",\"Event\",\"Medal\"";

    /**
     * Unit of Height.
     */

    public static final String UNIT_HEIGHT = "cm";

    /**
     * Unit of weight.
     */
    public static final String UNIT_WEIGHT = "kg";

    /**
     * Array of Seasons.
     */
    public static final String[] SEASONS = {"Summer", "Winter"};

    /**
     * Splits and deserializes csv lines into usable string data.
     *
     * @param rawLine Raw csv line separated by commas and strings surrounded by quotes.
     * @return Returns array of length 15 with entry data in thw order: ID, Name, Sex, Age, Height, Weight, Team, NOC, Olympic Games, Year, Season, City, Sport, Event, Medal.
     */
    public static String[] splitCSVLine(String rawLine) {
        rawLine = rawLine + ",";
        String[] parts = new String[15];
        int part = 0;
        int partStartIndex = 0;
        boolean openQuote = false;

        for (int i = 0; i < rawLine.length(); i++) {
            switch (rawLine.charAt(i)) {
                case '"':
                    openQuote = !openQuote;
                    break;
                case ',':
                    if (!openQuote) {
                        parts[part] = rawLine.substring(partStartIndex, i).replace("\"", "");
                        partStartIndex = i + 1;
                        part++;
                    }
                    break;
            }
        }
        return parts;
    }

    /**
     * Formats floats (Removes unnecessary commas and zeros at the end) and calls format int.
     *
     * @param num Takes number of type float.
     * @return Returns formatted number.
     */
    public static String formatNumber(float num) {
        if (num % 1 == 0) {
            return formatNumber(Math.round(num));
        } else {
            return String.valueOf(num);
        }
    }

    /**
     * Formats ints (If zero (N/A) returns question mark).
     *
     * @param num Takes number of type int.
     * @return Returns formatted number.
     */
    public static String formatNumber(int num) {
        if (num != 0) {
            return String.valueOf(num);
        } else {
            return "?";
        }
    }

    /**
     * Generates unique id for athlete entries.
     *
     * @return Returns unique id as string.
     */
    public static String generateUniqueId() {
        int min = 1;
        int max = 2 + ListUtils.size() * 2;

        int random;
        do {
            random = ThreadLocalRandom.current().nextInt(min, max);
        }
        while (ListUtils.get(String.valueOf(random)) != null);
        return String.valueOf(random);
    }

    /**
     * Rounds float.
     *
     * @param num Takes number of type float.
     * @return Rounded float as string.
     */
    public static String roundFloat(float num) {
        if (num % 1 == 0) {
            return String.valueOf(Math.round(num));
        } else {
            return String.valueOf(num);
        }
    }

    /**
     * Checks whether a string is neither empty ("") nor blank ("    ").
     *
     * @param string String to check.
     * @return Return true if string actually contains useful information.
     */
    public static boolean isNeitherEmptyNorBlank(String string) {
        return (!string.isEmpty() && !string.isBlank());
    }

    /**
     * Formats the sex from char
     *
     * @param sex 'F' = Female, 'M' = Male
     * @return Sex as full string
     */
    public static String formatSex(char sex) {
        if (sex == 'F') {
            return "Female";
        } else {
            return "Male";
        }
    }

}
