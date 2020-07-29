package olympic.util;

import java.util.concurrent.ThreadLocalRandom;

public class StringUtils {

    public static final String CSV_HEADER = "\"ID\",\"Name\",\"Sex\",\"Age\",\"Height\",\"Weight\",\"Team\",\"NOC\",\"Games\",\"Year\",\"Season\",\"City\",\"Sport\",\"Event\",\"Medal\"";
    public static final String UNIT_HEIGHT = "cm";
    public static final String UNIT_WEIGHT = "kg";
    public static final String[] SEASONS = {"Summer", "Winter"};

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

    public static String formatNumber(float num) {
        if (num % 1 == 0) {
            return formatNumber(Math.round(num));
        } else {
            return String.valueOf(num);
        }
    }

    public static String formatNumber(int num) {
        if (num != 0) {
            return String.valueOf(num);
        } else {
            return "?";
        }
    }

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

    public static String roundFloat(float num) {
        if (num % 1 == 0) {
            return String.valueOf(Math.round(num));
        } else {
            return String.valueOf(num);
        }
    }

    public static boolean isNeitherEmptyNorBlank(String string) {
        return (!string.isEmpty() && !string.isBlank());
    }

}
