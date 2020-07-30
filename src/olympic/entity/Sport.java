package olympic.entity;

import java.util.HashSet;

public class Sport {
    private static final HashSet<Sport> sports = new HashSet<>();

    private final String name;

    /**
     * A list of unique sports for displaying.
     *
     * @return returns a list of sports.
     */
    public static HashSet<Sport> getSports() {
        return sports;
    }

    /**
     * Clears lists of sports. Use when loading new data.
     */
    public static void clearSports() {
        sports.clear();
    }

    /**
     * Creates new sport object from name.
     *
     * @param sportName Name of sport.
     */
    public Sport(String sportName) {
        this.name = sportName;
        sports.add(this);
    }

    /**
     * Overridden equals method to only compare sport names.
     *
     * @param obj Object to compare with
     * @return Returns true if the sport objects (names) are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Sport)) {
            return false;
        }

        Sport asSport = (Sport) obj;
        return (name.equals(asSport.name));
    }

    /**
     * Overridden hash method to only hash the name of the sport.
     *
     * @return Returns hash of name.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Name of sport.
     *
     * @return Returns name of Sport.
     */
    public String getName() {
        return name;
    }
}
