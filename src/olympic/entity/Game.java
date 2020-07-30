package olympic.entity;

import java.util.HashSet;

public class Game {
    private static final HashSet<Game> games = new HashSet<>();

    private final String name;
    private final String season;
    private final String city;
    private final int year;

    /**
     * A list of unique olympic games for displaying.
     *
     * @return returns a list of olympic games.
     */
    public static HashSet<Game> getGames() {
        return games;
    }

    /**
     * Clears lists of olympic games. Use when loading new data.
     */
    public static void clearGames() {
        games.clear();
    }

    /**
     * Creates new olympic game object from single parameters.
     *
     * @param gameName Name of the olympic game. Composed of year and season.
     * @param season   Season in which the olympic game took place.
     * @param city     City in which the olympic game took place.
     * @param year     Year in which the olympic game took place.
     */
    public Game(String gameName, String season, String city, int year) {
        this.name = gameName;
        this.season = season;
        this.city = city;
        this.year = year;
        games.add(this);
    }

    /**
     * Overridden equals method to only compare olympic game names.
     *
     * @param obj Object to compare with
     * @return Returns true if the olympic game objects (names) are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Game)) {
            return false;
        }

        Game asGame = (Game) obj;
        return (name.equals(asGame.name));
    }

    /**
     * Overridden hash method to only hash the name of the olympic game.
     *
     * @return Returns hash of name.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Name of olympic game.
     *
     * @return Returns name.
     */
    public String getName() {
        return name;
    }

    /**
     * City of olympic game.
     *
     * @return Returns city in which the olympic game took place.
     */
    public String getCity() {
        return city;
    }

    /**
     * Year of olympic game.
     *
     * @return Returns year in which the olympic game took place.
     */
    public int getYear() {
        return year;
    }

    /**
     * Season of olympic game.
     *
     * @return Returns season in which the olympic game took place.
     */
    public String getSeason() {
        return season;
    }
}
