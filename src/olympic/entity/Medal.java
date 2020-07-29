package olympic.entity;

public enum Medal {
    NA,
    Bronze,
    Silver,
    Gold;


    @Override
    public String toString() {
        if(this == Medal.NA){
            return "None";
        }else{
            return super.toString();
        }

    }


    public static Medal fromString(String value) {
        if(value.equals("None")){
            return Medal.NA;
        }else{
            return Medal.valueOf(value);
        }
    }

    public static String toCSV(Medal medal) {
        if(medal == Medal.NA){
            return "NA";
        }
        return "\"" + medal.toString() + "\"";

    }


}
