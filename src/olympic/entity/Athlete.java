package olympic.entity;

import olympic.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

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

    public Athlete(String id, String name, char sex, String team, String noc, Event event) {

        this.id = id;
        this.name = name.trim().replaceAll(" +", " "); // Remove unnecessary spaces
        this.sex = sex;
        this.team = new Team(team, noc);
        this.events.add(event);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Athlete)) {
            return false;
        }

        Athlete asAthlete = (Athlete) obj;
        return (id.equals(asAthlete.id));
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return name;
    }

    public String toCSV() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < events.size(); i++){
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

            if(i < events.size() -1){
               stringBuilder.append("\n");
            }

        }


        return stringBuilder.toString();
    }

    public static Athlete fromCSVLine(String csvLine){
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

    public String getName() {
        return name;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    public String getId() {
        return id;
    }

    public int getIdNumber(){
        return Integer.parseInt(id);
    }

    public char getSex() {
        return sex;
    }

    public Team getTeam() {
        return team;
    }

    public String getLastAgeFormatted() {
        return formatNumber(getLastEvent().getAge());
    }

    public String getLastHeightRounded() {
        float num = getLastEvent().getHeight();
        return StringUtils.roundFloat(num);
    }

    public String getLastHeightFormatted() {
        return formatNumber(getLastEvent().getHeight()) + " " + UNIT_HEIGHT;
    }

    public String getLastWeightRounded(){
        float num = getLastEvent().getWeight();
        return StringUtils.roundFloat(num);
    }

    public String getLastWeightFormatted() {
        return formatNumber(getLastEvent().getWeight()) + " " + UNIT_WEIGHT;
    }

    private Event getLastEvent(){
        Event lastEvent = null;
        for (Event event : events) {
            if(lastEvent == null){
                lastEvent = event;
            }else{
                String currentGameSeason = event.getGame().getSeason();
                int currentGameYear = event.getGame().getYear();
                String lastGameSeason = lastEvent.getGame().getSeason();
                int lastGameYear = lastEvent.getGame().getYear();

                if(currentGameYear == lastGameYear){
                    if(currentGameSeason.compareTo(lastGameSeason) < 0) {
                        lastEvent = event;
                    }
                }
                else if(currentGameYear > lastGameYear){
                    lastEvent = event;
                }
            }
        }
        return lastEvent;
    }

    public void setId(String id) {
        this.id = id;
    }
}
