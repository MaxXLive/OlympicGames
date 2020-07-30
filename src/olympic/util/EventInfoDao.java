package olympic.util;

import java.util.ArrayList;
import java.util.List;
import olympic.entity.Athlete;
import olympic.entity.Event;
import static olympic.util.StringUtils.UNIT_HEIGHT;
import static olympic.util.StringUtils.UNIT_WEIGHT;
import static olympic.util.StringUtils.formatNumber;

/**
 * Objects of this class are needed for displaying the event table data.
 * It is for displaying only therefore all data types are string.
 */
public class EventInfoDao {

    private final String event;
    private final String game;
    private final String city;
    private final String age;
    private final String height;
    private final String weight;
    private final String medal;
    private final String sport;

    /**
     * Reads and formats data from event object and creates one with only displaying data.
     *
     * @param source Event which should be displayed in table
     */
    public EventInfoDao(Event source) {
        this.event = source.getName();
        this.game = source.getGame().getName();
        this.city = source.getGame().getCity();
        this.age = formatNumber(source.getAge());
        this.height = formatNumber(source.getHeight());
        this.weight = formatNumber(source.getWeight());
        this.medal = source.getMedal().toString();
        this.sport = source.getSport().getName();
    }

    /**
     * Provides list of all events of athlete as table entries.
     *
     * @param athlete Athlete you want the events from.
     * @return Returns list of events of the passed athlete.
     */
    public static List<EventInfoDao> getListFromAthlete(Athlete athlete) {
        List<EventInfoDao> list = new ArrayList<>();
        for (int i = 0; i < athlete.getEvents().size(); i++) {
            list.add(new EventInfoDao(athlete.getEvents().get(i)));
        }
        return list;
    }

    /**
     * Gets name of event to display in table.
     *
     * @return Returns name of event.
     */
    public String getEvent() {
        return event;
    }

    /**
     * Gets name of game to display in table.
     *
     * @return Returns name of game.
     */
    public String getGame() {
        return game;
    }

    /**
     * Gets city of game of event to display in table.
     *
     * @return Returns city of game of event.
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets formatted age of athlete at the event to display in table.
     *
     * @return Returns formatted age of athlete at the event.
     */
    public String getAge() {
        return age;
    }

    /**
     * Gets formatted height of athlete at the event to display in table.
     *
     * @return Returns formatted height of athlete at the event.
     */
    public String getHeight() {
        return height + " " + UNIT_HEIGHT;
    }

    /**
     * Gets formatted weight of athlete at the event to display in table.
     *
     * @return Returns formatted weight of athlete at the event.
     */
    public String getWeight() {
        return weight + " " + UNIT_WEIGHT;
    }

    /**
     * Gets serialized medal of athlete at the event to display in table.
     *
     * @return Returns serialized medal of athlete at the event.
     */
    public String getMedal() {
        return medal;
    }

    /**
     * Gets name of sport of event to display in table.
     *
     * @return Returns name of sport of event.
     */
    public String getSport() {
        return sport;
    }
}
