package olympic.util;

import java.util.HashMap;
import olympic.entity.Athlete;
import olympic.entity.Event;
import olympic.entity.Game;
import olympic.entity.Sport;
import olympic.entity.Team;
import static olympic.util.StringUtils.generateUniqueId;

/**
 * Handles everything related to saving and retrieving of athletes while runtime. Uses hash map.
 */
public class ListUtils {
    private static final HashMap<String, Athlete> athleteHashMap = new HashMap<>();

    /**
     * Returns athlete entries.
     *
     * @return Returns hash map of athlete entries.
     */
    public static HashMap<String, Athlete> getAthletes() {
        return athleteHashMap;
    }

    /**
     * Adds athlete entry to list. Checks whether id is given, if not, generates new Id.
     * If id is given, checks if id already exists. If so only adds event.
     *
     * @param athlete New athlete entry to add.
     */
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

    /**
     * Gets athlete by id.
     *
     * @param id Id of athlete.
     * @return Returns athlete with passed id. If doesn't exist, returns {@code null}.
     */
    public static Athlete get(String id) {
        return athleteHashMap.get(id);
    }

    /**
     * Returns amount of entries.
     *
     * @return Returns amount of entries in list.
     */
    public static int size() {
        return athleteHashMap.size();
    }

    /**
     * Resets athlete, event, team, game and sport lists.
     */
    public static void clear() {
        athleteHashMap.clear();
        Event.clearEvents();
        Team.clearTeams();
        Game.clearGames();
        Sport.clearSports();
    }
}
