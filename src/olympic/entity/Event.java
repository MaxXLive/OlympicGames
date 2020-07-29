package olympic.entity;

import java.util.HashSet;

public class Event {


    public static final HashSet<Event> events = new HashSet<>();

    private final String name;
    private final int age;
    private final float height;
    private final float weight;
    private final Sport sport;
    private final Medal medal;
    private final Game game;

    public Event(String name, int age, float height, float weight, String sportName, Medal medal, String gameName, String season, String city, int year) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sport = new Sport(sportName);
        this.medal = medal;
        this.game = new Game(gameName, season, city, year);
        events.add(this);
    }

    @Override
    public String toString() {
        return game.getName() + " in " + game.getCity() + ": " + name + " → " + medal;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Event)) {
            return false;
        }

        Event asEvent = (Event) obj;
        return (name.equals(asEvent.name));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }


    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public Sport getSport() {
        return sport;
    }

    public Medal getMedal() {
        return medal;
    }

    public Game getGame() {
        return game;
    }
}
