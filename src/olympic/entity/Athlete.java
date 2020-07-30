package olympic.entity;

import java.util.ArrayList;
import java.util.List;
import olympic.util.StringUtils;
import static olympic.util.StringUtils.UNIT_HEIGHT;
import static olympic.util.StringUtils.UNIT_WEIGHT;
import static olympic.util.StringUtils.formatNumber;
import static olympic.util.StringUtils.splitCSVLine;

public class Athlete {

    private String id;
    private final String name;
    private final char sex;
    private final Team team;
    private final List<Event> events = new ArrayList<>();

    /**
     * Calls splitting method and creates athlete object from csv line.
     *
     * @param csvLine One line of .db file.
     * @return Returns athlete object.
     */
    public static Athlete fromCSVLine(String csvLine) {
        String[] split = splitCSVLine(csvLine);

        String id = split[0];
        String name = split[1];
        char sex = split[2].charAt(0);
        int age = (split[3].equals("NA")) ? 0 : Integer.parseInt(split[3]);
        float height = (split[4].equals("NA")) ? 0 : Float.parseFloat(split[4]);
        float weight = (split[5].equals("NA")) ? 0 : Float.parseFloat(split[5]);
        String team = split[6];
        String noc = split[7];
        String game = split[8];
        int year = Integer.parseInt(split[9]);
        String season = split[10];
        String city = split[11];
        String sport = split[12];
        String eventName = split[13];
        Medal medal = Medal.valueOf(split[14]);


        Event event = new Event(eventName, age, height, weight, sport, medal, game, season, city, year);

        return new Athlete(id, name, sex, team, noc, event);
    }

    /**
     * Creates athlete object from single parameters
     *
     * @param id    Id of athlete
     * @param name  Name of athlete
     * @param sex   Sex of athlete (possible values: 'M'/'F')
     * @param team  Team of athlete
     * @param noc   National Olympic Committee of athlete
     * @param event Event of athlete
     */
    public Athlete(String id, String name, char sex, String team, String noc, Event event) {

        this.id = id;
        this.name = name.trim().replaceAll(" +", " "); // Remove unnecessary spaces
        this.sex = sex;
        this.team = new Team(team, noc);
        this.events.add(event);
    }

    /**
     * Overridden equals method to only compare ids.
     *
     * @param obj Object to compare with
     * @return Returns true if the athlete objects (ids) are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Athlete)) {
            return false;
        }

        Athlete asAthlete = (Athlete) obj;
        return (id.equals(asAthlete.id));
    }

    /**
     * Overridden hash method to only hash the id.
     *
     * @return Returns hash of id.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Overridden toString method to only output athlete name in list.
     *
     * @return Returns athlete name.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Name of athlete.
     *
     * @return Returns name.
     */
    public String getName() {
        return name;
    }

    /**
     * List of events of athlete.
     *
     * @return Returns events.
     */
    public List<Event> getEvents() {
        return events;
    }

    /**
     * Adds event to athlete.
     *
     * @param event Event to add to athlete.
     */
    public void addEvent(Event event) {
        this.events.add(event);
    }

    /**
     * Id of athlete.
     *
     * @return Returns id.
     */
    public String getId() {
        return id;
    }

    /**
     * Id of athlete as integer for sorting when saving .db file
     *
     * @return Returns id as {@code int}.
     */
    public int getIdNumber() {
        int idNum;
        try {
            idNum = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            idNum = id.hashCode();
        }
        return idNum;
    }

    /**
     * Sets id of athlete. Used for generating new id if {@code null}.
     *
     * @param id New id.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sex of athlete.
     *
     * @return Returns sex.
     */
    public char getSex() {
        return sex;
    }

    /**
     * Team of athlete.
     *
     * @return Returns team object containing team name and noc.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Calls last event and formats age.
     *
     * @return Returns formatted last age of athlete.
     */
    public String getLastAgeFormatted() {
        return formatNumber(getLastEvent().getAge());
    }

    /**
     * Calls last event and rounds height.
     *
     * @return Returns rounded last height of athlete.
     */
    public String getLastHeightRounded() {
        float num = getLastEvent().getHeight();
        return StringUtils.roundFloat(num);
    }

    /**
     * Calls last event and formats height.
     *
     * @return Returns formatted last height of athlete.
     */
    public String getLastHeightFormatted() {
        return formatNumber(getLastEvent().getHeight()) + " " + UNIT_HEIGHT;
    }

    /**
     * Calls last event and rounds weight.
     *
     * @return Returns rounded last weight of athlete.
     */
    public String getLastWeightRounded() {
        float num = getLastEvent().getWeight();
        return StringUtils.roundFloat(num);
    }

    /**
     * Calls last event and formats weight.
     *
     * @return Returns formatted last weight of athlete.
     */
    public String getLastWeightFormatted() {
        return formatNumber(getLastEvent().getWeight()) + " " + UNIT_WEIGHT;
    }

    /**
     * Sorts events by year and season and finds the last.
     *
     * @return Returns the last event the athlete was involved in.
     */
    private Event getLastEvent() {
        Event lastEvent = null;
        for (Event event : events) {
            if (lastEvent == null) {
                lastEvent = event;
            } else {
                String currentGameSeason = event.getGame().getSeason();
                int currentGameYear = event.getGame().getYear();
                String lastGameSeason = lastEvent.getGame().getSeason();
                int lastGameYear = lastEvent.getGame().getYear();

                if (currentGameYear == lastGameYear) {
                    if (currentGameSeason.compareTo(lastGameSeason) < 0) {
                        lastEvent = event;
                    }
                } else if (currentGameYear > lastGameYear) {
                    lastEvent = event;
                }
            }
        }
        return lastEvent;
    }

    /**
     * Converts athlete object to serialized csv line.
     *
     * @return Returns csv line for .db file.
     */
    public String toCSV() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);

            stringBuilder.append("\"")
                    .append(id).append("\",\"")
                    .append(name).append("\",\"")
                    .append(sex).append("\",")
                    .append(event.getAge()).append(",")
                    .append(event.getHeight()).append(",")
                    .append(event.getWeight()).append(",\"")
                    .append(team.getName()).append("\",\"")
                    .append(team.getNoc()).append("\",\"")
                    .append(event.getGame().getName()).append("\",")
                    .append(event.getGame().getYear()).append(",\"")
                    .append(event.getGame().getSeason()).append("\",\"")
                    .append(event.getGame().getCity()).append("\",\"")
                    .append(event.getSport().getName()).append("\",\"")
                    .append(event.getName()).append("\",")
                    .append(Medal.toCSV(event.getMedal()));

            if (i < events.size() - 1) {
                stringBuilder.append("\n");
            }

        }


        return stringBuilder.toString();
    }
}
