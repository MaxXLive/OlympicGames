package olympic.entity;

import java.util.HashSet;

public class Team {
    public static final HashSet<Team> teams = new HashSet<>();

    private final String name;
    private final String noc;

    public Team(String name, String noc) {
        this.name = name;
        this.noc = noc;
        teams.add(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Team)) {
            return false;
        }

        Team asTeam = (Team) obj;
        return (name.equals(asTeam.name));
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return name;
    }

    public String getNoc() {
        return noc;
    }
}
