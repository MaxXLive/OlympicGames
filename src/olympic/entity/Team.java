package olympic.entity;

import java.util.HashSet;

public class Team {
    private static final HashSet<Team> teams = new HashSet<>();

    private final String name;
    private final String noc;

    /**
     * A list of unique teams for displaying.
     *
     * @return returns a list of teams.
     */
    public static HashSet<Team> getTeams() {
        return teams;
    }

    /**
     * Clears lists of teams. Use when loading new data.
     */
    public static void clearTeams() {
        teams.clear();
    }

    /**
     * Creates new team object from name and noc.
     *
     * @param name Name of team.
     * @param noc  National Olympic Committee of team.
     */
    public Team(String name, String noc) {
        this.name = name;
        this.noc = noc;
        teams.add(this);
    }

    /**
     * Overridden equals method to only compare team names.
     *
     * @param obj Object to compare with
     * @return Returns true if the team objects (names) are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Team)) {
            return false;
        }

        Team asTeam = (Team) obj;
        return (name.equals(asTeam.name));
    }

    /**
     * Overridden hash method to only hash the name of the team.
     *
     * @return Returns hash of name.
     */
    @Override
    public int hashCode() {
        return name.hashCode();
    }

    /**
     * Name of team.
     *
     * @return Returns name.
     */
    public String getName() {
        return name;
    }

    /**
     * National Olympic Committee of team.
     *
     * @return Returns National Olympic Committee.
     */
    public String getNoc() {
        return noc;
    }
}
