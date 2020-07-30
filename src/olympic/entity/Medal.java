package olympic.entity;

public enum Medal {
    NA,
    Bronze,
    Silver,
    Gold;

    /**
     * Parses string to medal with "None" as NA.
     *
     * @param value Medal as string.
     * @return Returns Modal.
     */
    public static Medal fromString(String value) {
        if (value.equals("None")) {
            return Medal.NA;
        } else {
            return Medal.valueOf(value);
        }
    }

    /**
     * Serializes medal to csv end. NA has no quotes.
     *
     * @param medal Medal to serialize.
     * @return Returns serialized string of medal.
     */
    public static String toCSV(Medal medal) {
        if (medal == Medal.NA) {
            return "NA";
        }
        return "\"" + medal.toString() + "\"";

    }

    /**
     * Overridden toString method to output "None" for NA.
     *
     * @return Returns medal as string.
     */
    @Override
    public String toString() {
        if (this == Medal.NA) {
            return "None";
        } else {
            return super.toString();
        }

    }
}
