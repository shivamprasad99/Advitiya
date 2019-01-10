package iitropar.advitiya;

public class Event {
    private String eventName;
    private String eventVenue;
    private String eventTime;
    private String eventDescription ;
    private boolean eventNotify ;
    private String eventRules ;
    private int eventDay  ; // which day
    private int eventPk ;



    private int eventType ; // 1 for competition 2 for concert 3 for informal


    public Event(String eventName, String eventVenue, String eventTime, String eventDescription) {
        this.eventName = eventName;
        this.eventVenue = eventVenue;
        this.eventTime = eventTime;
        this.eventDescription = eventDescription;
        this.eventNotify = false;

    }


    public Event(String eventName, String eventVenue, String eventTime, int eventDay) {
        this.eventName = eventName;
        this.eventVenue = eventVenue;
        this.eventTime = eventTime;
        this.eventDescription = eventDescription;
        this.eventDay = eventDay;

    }

    public Event(){

    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

    public int getEventPk() {
        return eventPk;
    }

    public void setEventPk(int eventPk) {
        this.eventPk = eventPk;
    }

    public int getEventDay() {
        return eventDay;
    }

    public void setEventDay(int eventDay) {
        this.eventDay = eventDay;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventRules() {
        return eventRules;
    }

    public void setEventRules(String eventRules) {
        this.eventRules = eventRules;
    }

    public boolean isEventNotify() {
        return eventNotify;
    }

    public void setEventNotify(boolean eventNotify) {
        this.eventNotify = eventNotify;
    }


    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventVenue() {
        return eventVenue;
    }

    public void setEventVenue(String eventVenue) {
        this.eventVenue = eventVenue;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }


}
