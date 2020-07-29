package olympic.entity;

import java.util.HashSet;

public class Game {
    public static final HashSet<Game> games = new HashSet<>();

    private final String name;
    private final String season;
    private final String city;
    private final int year;

    public Game(String gameName, String season, String city, int year) {
        this.name = gameName;
        this.season = season;
        this.city = city;
        this.year = year;
        games.add(this);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Game)) {
            return false;
        }

        Game asGame = (Game) obj;
        return (name.equals(asGame.name));
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public int getYear() {
        return year;
    }

    public String getSeason() {
        return season;
    }
}
