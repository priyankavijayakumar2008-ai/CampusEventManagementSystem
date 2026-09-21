package com.campus.campuseventmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EventManager {

    // =========================
    // ADD EVENT
    // =========================
    public void addEvent(Event event) {

        String sql = "INSERT INTO events "
                + "(event_name, event_date, event_time, venue, description) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, event.getEventName());
            ps.setString(2, event.getEventDate());
            ps.setString(3, event.getEventTime());
            ps.setString(4, event.getVenue());
            ps.setString(5, event.getDescription());

            ps.executeUpdate();

            ps.close();
            con.close();

            System.out.println("Event Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // VIEW EVENTS
    // =========================
    public String viewEvents() {

        StringBuilder events = new StringBuilder();

        String sql = "SELECT * FROM events";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                events.append("Event ID: ")
                        .append(rs.getInt("event_id"))
                        .append("\n");

                events.append("Event Name: ")
                        .append(rs.getString("event_name"))
                        .append("\n");

                events.append("Date: ")
                        .append(rs.getString("event_date"))
                        .append("\n");

                events.append("Time: ")
                        .append(rs.getString("event_time"))
                        .append("\n");

                events.append("Venue: ")
                        .append(rs.getString("venue"))
                        .append("\n");

                events.append("Description: ")
                        .append(rs.getString("description"))
                        .append("\n");

                events.append("-----------------------------\n");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return events.toString();
    }

    // =========================
    // ADD STUDENT
    // =========================
    public void addStudent(Student student) {

        String sql = "INSERT INTO students "
                + "(name, email, department, password) "
                + "VALUES (?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getDepartment());
            ps.setString(4, student.getPassword());

            ps.executeUpdate();

            ps.close();
            con.close();

            System.out.println("Student Added Successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // REGISTER STUDENT FOR EVENT
    // =========================
    public void registerStudentForEvent(Registration registration) {

        String sql = "INSERT INTO registrations "
                + "(student_id, event_id) VALUES (?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, registration.getStudentId());
            ps.setInt(2, registration.getEventId());

            ps.executeUpdate();

            ps.close();
            con.close();

            System.out.println(
                    "Student Registered for Event Successfully!"
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // =========================
    // VIEW REGISTRATIONS
    // =========================
    public String viewRegistrations() {

        StringBuilder registrations = new StringBuilder();

        String sql =
                "SELECT r.registration_id, "
                + "s.name, s.email, "
                + "e.event_name, e.event_date, e.venue "
                + "FROM registrations r "
                + "JOIN students s "
                + "ON r.student_id = s.id "
                + "JOIN events e "
                + "ON r.event_id = e.event_id";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                registrations.append("Registration ID: ")
                        .append(rs.getInt("registration_id"))
                        .append("\n");

                registrations.append("Student: ")
                        .append(rs.getString("name"))
                        .append("\n");

                registrations.append("Email: ")
                        .append(rs.getString("email"))
                        .append("\n");

                registrations.append("Event: ")
                        .append(rs.getString("event_name"))
                        .append("\n");

                registrations.append("Date: ")
                        .append(rs.getString("event_date"))
                        .append("\n");

                registrations.append("Venue: ")
                        .append(rs.getString("venue"))
                        .append("\n");

                registrations.append("-----------------------------\n");
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return registrations.toString();
    }

    // =========================
    // UPDATE EVENT
    // =========================
    public boolean updateEvent(
            int eventId,
            String eventName,
            String eventDate,
            String eventTime,
            String venue,
            String description) {

        String sql = "UPDATE events SET "
                + "event_name = ?, "
                + "event_date = ?, "
                + "event_time = ?, "
                + "venue = ?, "
                + "description = ? "
                + "WHERE event_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, eventName);
            ps.setString(2, eventDate);
            ps.setString(3, eventTime);
            ps.setString(4, venue);
            ps.setString(5, description);
            ps.setInt(6, eventId);

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // DELETE EVENT
    // =========================
    public boolean deleteEvent(int eventId) {

        String sql = "DELETE FROM events WHERE event_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, eventId);

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // UPDATE STUDENT
    // =========================
    public boolean updateStudent(
            int studentId,
            String studentName,
            String email,
            String department) {

        String sql = "UPDATE students SET "
                + "name = ?, "
                + "email = ?, "
                + "department = ? "
                + "WHERE id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, studentName);
            ps.setString(2, email);
            ps.setString(3, department);
            ps.setInt(4, studentId);

            int rows = ps.executeUpdate();

            ps.close();
            con.close();

            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}