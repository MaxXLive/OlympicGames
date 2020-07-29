package olympic.entity;

import java.util.HashSet;

public class Sport {
    public static final HashSet<Sport> sports = new HashSet<>();

    private final String name;

    public Sport(String sportName) {
        this.name = sportName;
        sports.add(this);
    }



    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Sport)) {
            return false;
        }

        Sport asSport = (Sport) obj;
        return (name.equals(asSport.name));
    }

    public String getName() {
        return name;
    }
}
