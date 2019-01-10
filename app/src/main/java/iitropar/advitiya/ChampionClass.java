package iitropar.advitiya;


public class ChampionClass {
    private String collegeName ;
    private String points ;

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public ChampionClass(String collegeName, String points) {
        this.collegeName = collegeName;
        this.points = points;
    }

}
