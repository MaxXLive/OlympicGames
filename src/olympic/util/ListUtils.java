package olympic.util;

import olympic.entity.Athlete;
import olympic.entity.Event;

import java.util.HashMap;
import olympic.entity.Game;
import olympic.entity.Sport;
import olympic.entity.Team;

import static olympic.util.StringUtils.generateUniqueId;

public class ListUtils {
    private static final HashMap<String, Athlete> athleteHashMap = new HashMap<>();

    public static HashMap<String, Athlete> getAthletes() {
        return athleteHashMap;
    }

    public static void add(Athlete athlete) {
        Event event = athlete.getEvents().get(0);

        if (athlete.getId() != null) {
            if (athleteHashMap.get(athlete.getId()) != null) {
                athleteHashMap.get(athlete.getId()).addEvent(event);
            } else {
                athleteHashMap.put(athlete.getId(), athlete);
            }
        } else {
            String id = generateUniqueId();
            System.out.println("New ID: " + id);

            athlete.setId(id);
            athleteHashMap.put(id, athlete);
        }

    }

    public static Athlete get(String key) {
        return athleteHashMap.get(key);
    }

    public static int size() {
        return athleteHashMap.size();
    }

    public static void clear() {
        athleteHashMap.clear();
        Event.events.clear();
        Team.teams.clear();
        Game.games.clear();
        Sport.sports.clear();
    }
}
