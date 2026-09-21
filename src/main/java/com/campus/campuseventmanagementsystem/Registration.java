package com.campus.campuseventmanagementsystem;

public class Registration {

    private int studentId;
    private int eventId;

    public Registration() {
    }

    public Registration(int studentId, int eventId) {
        this.studentId = studentId;
        this.eventId = eventId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}