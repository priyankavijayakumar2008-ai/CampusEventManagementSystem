package com.campus.campuseventmanagementsystem;

public class Event {

    private int eventId;
    private String eventName;
    private String eventDate;
    private String eventTime;
    private String venue;
    private String description;

    public Event(int eventId, String eventName, String eventDate,
                 String eventTime, String venue, String description) {

        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.venue = venue;
        this.description = description;
    }

    public Event(String eventName, String eventDate, String eventTime,
                 String venue, String description) {

        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.venue = venue;
        this.description = description;
    }

    public int getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public String getVenue() {
        return venue;
    }

    public String getDescription() {
        return description;
    }
}