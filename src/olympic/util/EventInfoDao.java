package olympic.util;

import olympic.entity.Athlete;
import olympic.entity.Event;
import java.util.ArrayList;
import java.util.List;

import static olympic.util.StringUtils.UNIT_HEIGHT;
import static olympic.util.StringUtils.UNIT_WEIGHT;
import static olympic.util.StringUtils.formatNumber;

public class EventInfoDao {

    private final String event;
    private final String game;
    private final String city;
    private final String age;
    private final String height;
    private final String weight;
    private final String medal;
    private final String sport;

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

    public static List<EventInfoDao> getListFromAthlete(Athlete athlete) {
        List<EventInfoDao> list = new ArrayList<>();
        for (int i = 0; i < athlete.getEvents().size(); i++) {
            list.add(new EventInfoDao(athlete.getEvents().get(i)));
        }
        return list;
    }


    public String getEvent() {
        return event;
    }

    public String getGame() {
        return game;
    }

    public String getCity() {
        return city;
    }

    public String getAge() {
        return age;
    }

    public String getHeight() {
        return height + " " + UNIT_HEIGHT;
    }

    public String getWeight() {
        return weight + " " + UNIT_WEIGHT;
    }

    public String getMedal() {
        return medal;
    }

    public String getSport() {
        return sport;
    }
}
