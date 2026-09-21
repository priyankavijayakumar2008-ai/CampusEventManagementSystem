package com.campus.campuseventmanagementsystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/StudentPassServlet")
public class StudentPassServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();

        Integer studentId =
                (Integer) session.getAttribute("studentId");

        if (studentId == null) {
            response.sendRedirect("Login.html");
            return;
        }

        String eventIdText = request.getParameter("eventId");

        if (eventIdText == null || eventIdText.isEmpty()) {
            response.getWriter().println(
                    "<h2>Event ID is missing!</h2>"
            );
            return;
        }

        try {

            int eventId = Integer.parseInt(eventIdText);

            String sql =
                    "SELECT r.registration_id, " +
                    "s.name, s.email, " +
                    "e.event_name, e.event_date, " +
                    "e.event_time, e.venue " +
                    "FROM registrations r " +
                    "JOIN students s ON r.student_id = s.id " +
                    "JOIN events e ON r.event_id = e.event_id " +
                    "WHERE r.student_id = ? AND r.event_id = ?";

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, studentId);
            ps.setInt(2, eventId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                int registrationId =
                        rs.getInt("registration_id");

                String name =
                        rs.getString("name");

                String email =
                        rs.getString("email");

                String eventName =
                        rs.getString("event_name");

                String eventDate =
                        rs.getString("event_date");

                String eventTime =
                        rs.getString("event_time");

                String venue =
                        rs.getString("venue");

                String passId =
                        String.format("CEP-%04d", registrationId);


                response.getWriter().println(

                        "<!DOCTYPE html>" +

                        "<html lang='en'>" +

                        "<head>" +

                        "<meta charset='UTF-8'>" +

                        "<meta name='viewport' " +
                        "content='width=device-width, initial-scale=1.0'>" +

                        "<title>Digital Event Pass</title>" +

                        "<style>" +

                        "* {" +
                        "margin:0;" +
                        "padding:0;" +
                        "box-sizing:border-box;" +
                        "font-family:Arial,sans-serif;" +
                        "}" +

                        "body {" +
                        "min-height:100vh;" +
                        "display:flex;" +
                        "justify-content:center;" +
                        "align-items:center;" +
                        "background:linear-gradient(135deg,#35127a,#7b22c9,#d23ee8);" +
                        "color:white;" +
                        "padding:20px;" +
                        "}" +

                        ".pass {" +
                        "width:480px;" +
                        "max-width:100%;" +
                        "padding:35px;" +
                        "border-radius:25px;" +
                        "background:rgba(255,255,255,0.18);" +
                        "border:1px solid rgba(255,255,255,0.3);" +
                        "backdrop-filter:blur(15px);" +
                        "box-shadow:0 20px 40px rgba(0,0,0,0.3);" +
                        "}" +

                        ".header {" +
                        "text-align:center;" +
                        "margin-bottom:25px;" +
                        "}" +

                        ".icon {" +
                        "font-size:55px;" +
                        "margin-bottom:10px;" +
                        "}" +

                        "h1 {" +
                        "font-size:28px;" +
                        "}" +

                        ".subtitle {" +
                        "margin-top:8px;" +
                        "opacity:0.85;" +
                        "}" +

                        ".details {" +
                        "margin-top:20px;" +
                        "}" +

                        ".row {" +
                        "display:flex;" +
                        "justify-content:space-between;" +
                        "align-items:center;" +
                        "gap:20px;" +
                        "padding:13px 0;" +
                        "border-bottom:1px solid rgba(255,255,255,0.2);" +
                        "}" +

                        ".label {" +
                        "font-weight:bold;" +
                        "opacity:0.8;" +
                        "}" +

                        ".value {" +
                        "text-align:right;" +
                        "word-break:break-word;" +
                        "}" +

                        ".status {" +
                        "margin-top:25px;" +
                        "padding:13px;" +
                        "text-align:center;" +
                        "border-radius:25px;" +
                        "background:rgba(0,255,150,0.2);" +
                        "border:1px solid rgba(0,255,150,0.4);" +
                        "font-weight:bold;" +
                        "}" +

                        ".passid {" +
                        "text-align:center;" +
                        "margin-top:20px;" +
                        "font-weight:bold;" +
                        "font-size:17px;" +
                        "}" +

                        ".food {" +
                        "display:block;" +
                        "text-align:center;" +
                        "margin-top:20px;" +
                        "padding:13px;" +
                        "border-radius:25px;" +
                        "background:#ffd166;" +
                        "color:#5a3d00;" +
                        "text-decoration:none;" +
                        "font-weight:bold;" +
                        "}" +

                        ".food:hover {" +
                        "background:#ffbf3f;" +
                        "}" +

                        ".home {" +
                        "display:block;" +
                        "text-align:center;" +
                        "margin-top:15px;" +
                        "padding:13px;" +
                        "border-radius:25px;" +
                        "background:white;" +
                        "color:#6a1bb1;" +
                        "text-decoration:none;" +
                        "font-weight:bold;" +
                        "}" +

                        ".home:hover {" +
                        "background:#f0d9ff;" +
                        "}" +

                        "</style>" +

                        "</head>" +

                        "<body>" +

                        "<div class='pass'>" +

                        "<div class='header'>" +

                        "<div class='icon'>🎫</div>" +

                        "<h1>Digital Event Pass</h1>" +

                        "<p class='subtitle'>" +
                        "Campus Event Management System" +
                        "</p>" +

                        "</div>" +

                        "<div class='details'>" +

                        "<div class='row'>" +
                        "<span class='label'>Student Name</span>" +
                        "<span class='value'>" + name + "</span>" +
                        "</div>" +

                        "<div class='row'>" +
                        "<span class='label'>Email</span>" +
                        "<span class='value'>" + email + "</span>" +
                        "</div>" +

                        "<div class='row'>" +
                        "<span class='label'>Event</span>" +
                        "<span class='value'>" + eventName + "</span>" +
                        "</div>" +

                        "<div class='row'>" +
                        "<span class='label'>Date</span>" +
                        "<span class='value'>" + eventDate + "</span>" +
                        "</div>" +

                        "<div class='row'>" +
                        "<span class='label'>Time</span>" +
                        "<span class='value'>" + eventTime + "</span>" +
                        "</div>" +

                        "<div class='row'>" +
                        "<span class='label'>Venue</span>" +
                        "<span class='value'>" + venue + "</span>" +
                        "</div>" +

                        "</div>" +

                        "<div class='status'>" +
                        "✓ REGISTERED" +
                        "</div>" +

                        "<div class='passid'>" +
                        "Pass ID: " + passId +
                        "</div>" +

                        "<a href='FoodTokenServlet?eventId=" +
                        eventId +
                        "' class='food'>" +
                        "🍔 GET FOOD TOKEN" +
                        "</a>" +

                        "<a href='index.html' class='home'>" +
                        "BACK TO HOME" +
                        "</a>" +

                        "</div>" +

                        "</body>" +

                        "</html>"
                );

            } else {

                response.getWriter().println(
                        "<h2>No registration found for this event!</h2>"
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (NumberFormatException e) {

            response.getWriter().println(
                    "<h2>Invalid Event ID!</h2>"
            );

        } catch (Exception e) {

            response.getWriter().println(
                    "<h2>Digital Pass Error: "
                    + e.getMessage()
                    + "</h2>"
            );
        }
    }
}