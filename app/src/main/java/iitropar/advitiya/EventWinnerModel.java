package iitropar.advitiya;


public class EventWinnerModel {
    private String eventName ;
    private String winner1 ;
    private String winner2;
    private String winner3 ;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getWinner1() {
        return winner1;
    }

    public void setWinner1(String winner1) {
        this.winner1 = winner1;
    }

    public String getWinner2() {
        return winner2;
    }

    public void setWinner2(String winner2) {
        this.winner2 = winner2;
    }

    public String getWinner3() {
        return winner3;
    }

    public void setWinner3(String winner3) {
        this.winner3 = winner3;
    }

    public EventWinnerModel(String eventName, String winner1, String winner2, String winner3) {
        this.eventName = eventName;
        this.winner1 = winner1;
        this.winner2 = winner2;
        this.winner3 = winner3;
    }
}
