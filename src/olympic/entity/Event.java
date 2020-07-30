package olympic.entity;

import java.util.HashSet;

public class Event {
    private static final HashSet<Event> events = new HashSet<>();

    private final String name;
    private final int age;
    private final float height;
    private final float weight;
    private final Sport sport;
    private final Medal medal;
    private final Game game;

    /**
     * A list of unique events for displaying.
     *
     * @return returns a list of events.
     */
    public static HashSet<Event> getEvents() {
        return events;
    }

    /**
     * Clears lists of events. Use when loading new data.
     */
    public static void clearEvents() {
        events.clear();
    }

    /**
     * Creates new event from single parameters.
     *
     * @param name      Name of event.
     * @param age       Age of athlete at the event.
     * @param height    Height of athlete at the event.
     * @param weight    Weight of athlete at the event.
     * @param sportName Name of sport of the event.
     * @param medal     Medal the athlete has won at the event.
     * @param gameName  Name of the olympic game. Composed of year and season.
     * @param season    Season in which the olympic game took place.
     * @param city      City in which the olympic game took place.
     * @param year      Year in which the olympic game took place.
     */
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

    /**
     * Overridden equals method to only compare event names.
     *
     * @param obj Object to compare with
     * @return Returns true if the event objects (names) are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Event)) {
            return false;
        }

        Event asEvent = (Event) obj;
        return (name.equals(asEvent.name));
    }

    /**
     * Overridden hash method to only hash the name of the event.
     *
     * @return Returns hash of name.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Name of event.
     *
     * @return Returns name.
     */
    public String getName() {
        return name;
    }

    /**
     * Age of athlete at event.
     *
     * @return Returns age of athlete.
     */
    public int getAge() {
        return age;
    }

    /**
     * Height of athlete at event.
     *
     * @return Returns height of athlete.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Weight of athlete at event.
     *
     * @return Returns weight of athlete.
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Sport of event.
     *
     * @return returns Sport object.
     */
    public Sport getSport() {
        return sport;
    }

    /**
     * Medal the athlete has won at the event.
     *
     * @return Returns medal of the athlete.
     */
    public Medal getMedal() {
        return medal;
    }

    /**
     * Olympic game of event.
     *
     * @return returns olympic game object.
     */
    public Game getGame() {
        return game;
    }
}
