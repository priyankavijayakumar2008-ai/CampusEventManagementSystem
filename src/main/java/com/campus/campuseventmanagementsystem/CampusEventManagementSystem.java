package com.campus.campuseventmanagementsystem;

public class CampusEventManagementSystem {

    public static void main(String[] args) {

        Event event = new Event(
                "Tech Fest",
                "2026-10-10",
                "10:00:00",
                "Main Auditorium",
                "College technical event"
        );

        EventManager manager = new EventManager();

        manager.addEvent(event);
    }
}