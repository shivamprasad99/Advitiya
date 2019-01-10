package iitropar.advitiya;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DBHandler extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "storage.db";

    // chat Room table
    public static final String TABLE_EVENTS = "eventTable";
    public static final String COLUMN_EVENT_PK = "eventPK";
    public static final String COLUMN_EVENT_NAME = "eventName";
    public static final String COLUMN_EVENT_VENUE = "eventVenue";
    public static final String COLUMN_EVENT_TIME = "eventTime";
    public static final String COLUMN_EVENT_DAY= "eventDay";
    public static final String COLUMN_EVENT_DESCRIPTION = "eventDescription";
    public static final String COLUMN_EVENT_RULES = "eventRules";
    public static final String COLUMN_EVENT_TYPE = "eventType";
    public static final String COLUMN_EVENT_NOTIFY = "eventNotify";



    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryChat = "CREATE TABLE " + TABLE_EVENTS + "(" + COLUMN_EVENT_NAME + " TEXT,"
                + COLUMN_EVENT_PK + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EVENT_VENUE + " TEXT,"
                + COLUMN_EVENT_TIME + " TEXT,"
                + COLUMN_EVENT_DAY+ " INTEGER,"
                + COLUMN_EVENT_DESCRIPTION + " TEXT,"
                + COLUMN_EVENT_RULES+ " TEXT,"
                + COLUMN_EVENT_TYPE+ " INTEGER,"
                + COLUMN_EVENT_NOTIFY+ " INTEGER DEFAULT 0"

                + ");";
        db.execSQL(queryChat);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        onCreate(db);
    }


    // insert functions


    public long insertTableEvents(Event event) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(COLUMN_EVENT_NAME, event.getEventName());
        initialValues.put(COLUMN_EVENT_VENUE, event.getEventVenue());
        initialValues.put(COLUMN_EVENT_TIME, event.getEventTime());
        initialValues.put(COLUMN_EVENT_DAY, event.getEventDay());
        initialValues.put(COLUMN_EVENT_DESCRIPTION, event.getEventDescription());
        initialValues.put(COLUMN_EVENT_RULES, event.getEventRules());
        initialValues.put(COLUMN_EVENT_TYPE, event.getEventType());
        initialValues.put(COLUMN_EVENT_NOTIFY, event.isEventNotify());


        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_EVENTS, null, initialValues);
    }

    public long  updateNotifiy(int isNotify , int pK) {
        ContentValues updateValues = new ContentValues();
        updateValues.put(COLUMN_EVENT_NOTIFY,isNotify);
        SQLiteDatabase db = getWritableDatabase();

        return db.update(TABLE_EVENTS, updateValues, COLUMN_EVENT_PK + " = ?",new String[]{String.valueOf(pK)});
    }


    public void deleteEvent(int pK){
        String query = "DELETE FROM " +  TABLE_EVENTS + " WHERE " + COLUMN_EVENT_PK + " = " + pK;
        getWritableDatabase().execSQL(query);

    }
    public void clearDatabase(){
        String query = "DELETE FROM " +  TABLE_EVENTS ;
        getWritableDatabase().execSQL(query);
    }

    public ArrayList<Event> getData( ) {
        ArrayList<Event> dbstring = new ArrayList<Event>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS ;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            Event event = new Event();
            if (c.getString(c.getColumnIndex("eventPK")) != null) {

                event.setEventPk(c.getInt(c.getColumnIndex("eventPK")));
            }

            if (c.getString(c.getColumnIndex("eventName")) != null) {
                event.setEventName(c.getString(c.getColumnIndex("eventName")));
            }
            if (c.getString(c.getColumnIndex("eventVenue")) != null) {
                event.setEventVenue(c.getString(c.getColumnIndex("eventVenue")));
            }
            if (c.getString(c.getColumnIndex("eventTime")) != null) {
                event.setEventTime(c.getString(c.getColumnIndex("eventTime")));
            }
            if (c.getString(c.getColumnIndex("eventDay")) != null) {
                event.setEventDay(c.getInt(c.getColumnIndex("eventDay")));
            }
            if (c.getString(c.getColumnIndex("eventDescription")) != null) {
                event.setEventDescription(c.getString(c.getColumnIndex("eventDescription")));
            }
            if (c.getString(c.getColumnIndex("eventType")) != null) {
                event.setEventType(c.getInt(c.getColumnIndex("eventType")));
            }
            if (c.getString(c.getColumnIndex("eventRules")) != null) {
                event.setEventRules(c.getString(c.getColumnIndex("eventRules")));
            }
            if (c.getString(c.getColumnIndex("eventNotify")) != null) {

                int flag = c.getInt(c.getColumnIndex("eventNotify"));
                if (flag == 0){
                    event.setEventNotify(false);
                }
                else {
                    event.setEventNotify(true);
                }

            }
            dbstring.add(event);
            c.moveToNext();
        }
        db.close(); c.close();
        return dbstring ;
    }


    public ArrayList<Event> getDataDayType(int day, int type ) {
        ArrayList<Event> dbstring = new ArrayList<Event>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS  + " WHERE " + COLUMN_EVENT_DAY + " = " + day + " AND " + COLUMN_EVENT_TYPE + " = "  + type ;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            Event event = new Event();
            if (c.getString(c.getColumnIndex("eventPK")) != null) {

                event.setEventPk(c.getInt(c.getColumnIndex("eventPK")));
            }

            if (c.getString(c.getColumnIndex("eventName")) != null) {
                event.setEventName(c.getString(c.getColumnIndex("eventName")));
            }
            if (c.getString(c.getColumnIndex("eventVenue")) != null) {
                event.setEventVenue(c.getString(c.getColumnIndex("eventVenue")));
            }
            if (c.getString(c.getColumnIndex("eventTime")) != null) {
                event.setEventTime(c.getString(c.getColumnIndex("eventTime")));
            }
            if (c.getString(c.getColumnIndex("eventDay")) != null) {
                event.setEventDay(c.getInt(c.getColumnIndex("eventDay")));
            }
            if (c.getString(c.getColumnIndex("eventDescription")) != null) {
                event.setEventDescription(c.getString(c.getColumnIndex("eventDescription")));
            }
            if (c.getString(c.getColumnIndex("eventType")) != null) {
                event.setEventType(c.getInt(c.getColumnIndex("eventType")));
            }
            if (c.getString(c.getColumnIndex("eventRules")) != null) {
                event.setEventRules(c.getString(c.getColumnIndex("eventRules")));
            }
            if (c.getString(c.getColumnIndex("eventNotify")) != null) {

                int flag = c.getInt(c.getColumnIndex("eventNotify"));
                if (flag == 0){
                    event.setEventNotify(false);
                }
                else {
                    event.setEventNotify(true);
                }

            }
            dbstring.add(event);
            c.moveToNext();
        }
        db.close(); c.close();
        return dbstring ;
    }

    public ArrayList<Event> getDataDay(int day) {
        ArrayList<Event> dbstring = new ArrayList<Event>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS  + " WHERE " + COLUMN_EVENT_DAY + " = " + day;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            Event event = new Event();
            if (c.getString(c.getColumnIndex("eventPK")) != null) {

                event.setEventPk(c.getInt(c.getColumnIndex("eventPK")));
            }

            if (c.getString(c.getColumnIndex("eventName")) != null) {
                event.setEventName(c.getString(c.getColumnIndex("eventName")));
            }
            if (c.getString(c.getColumnIndex("eventVenue")) != null) {
                event.setEventVenue(c.getString(c.getColumnIndex("eventVenue")));
            }
            if (c.getString(c.getColumnIndex("eventTime")) != null) {
                event.setEventTime(c.getString(c.getColumnIndex("eventTime")));
            }
            if (c.getString(c.getColumnIndex("eventDay")) != null) {
                event.setEventDay(c.getInt(c.getColumnIndex("eventDay")));
            }
            if (c.getString(c.getColumnIndex("eventDescription")) != null) {
                event.setEventDescription(c.getString(c.getColumnIndex("eventDescription")));
            }
            if (c.getString(c.getColumnIndex("eventType")) != null) {
                event.setEventType(c.getInt(c.getColumnIndex("eventType")));
            }
            if (c.getString(c.getColumnIndex("eventRules")) != null) {
                event.setEventRules(c.getString(c.getColumnIndex("eventRules")));
            }
            if (c.getString(c.getColumnIndex("eventNotify")) != null) {

                int flag = c.getInt(c.getColumnIndex("eventNotify"));
                if (flag == 0){
                    event.setEventNotify(false);
                }
                else {
                    event.setEventNotify(true);
                }

            }
            dbstring.add(event);
            c.moveToNext();
        }
        db.close(); c.close();
        return dbstring ;
    }

    public ArrayList<Event> getNotifiedData(int day ) {
        ArrayList<Event> dbstring = new ArrayList<Event>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_EVENTS  + " WHERE " + COLUMN_EVENT_DAY + " = " + day + " AND " + COLUMN_EVENT_NOTIFY + " = "  + 1 ;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()) {
            Event event = new Event();
            if (c.getString(c.getColumnIndex("eventPK")) != null) {

                event.setEventPk(c.getInt(c.getColumnIndex("eventPK")));
            }

            if (c.getString(c.getColumnIndex("eventName")) != null) {
                event.setEventName(c.getString(c.getColumnIndex("eventName")));
            }
            if (c.getString(c.getColumnIndex("eventVenue")) != null) {
                event.setEventVenue(c.getString(c.getColumnIndex("eventVenue")));
            }
            if (c.getString(c.getColumnIndex("eventTime")) != null) {
                event.setEventTime(c.getString(c.getColumnIndex("eventTime")));
            }
            if (c.getString(c.getColumnIndex("eventDay")) != null) {
                event.setEventDay(c.getInt(c.getColumnIndex("eventDay")));
            }
            if (c.getString(c.getColumnIndex("eventDescription")) != null) {
                event.setEventDescription(c.getString(c.getColumnIndex("eventDescription")));
            }
            if (c.getString(c.getColumnIndex("eventType")) != null) {
                event.setEventType(c.getInt(c.getColumnIndex("eventType")));
            }
            if (c.getString(c.getColumnIndex("eventRules")) != null) {
                event.setEventRules(c.getString(c.getColumnIndex("eventRules")));
            }
            if (c.getString(c.getColumnIndex("eventNotify")) != null) {

                int flag = c.getInt(c.getColumnIndex("eventNotify"));
                if (flag == 0){
                    event.setEventNotify(false);
                }
                else {
                    event.setEventNotify(true);
                }

            }
            dbstring.add(event);
            c.moveToNext();
        }
        db.close(); c.close();
        return dbstring ;
    }




}

