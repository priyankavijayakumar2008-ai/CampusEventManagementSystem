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

@WebServlet("/FoodTokenServlet")
public class FoodTokenServlet extends HttpServlet {

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

        if (eventIdText == null || eventIdText.trim().isEmpty()) {
            response.getWriter().println(
                    "<h2>Event ID is missing!</h2>"
            );
            return;
        }

        try {
            int eventId = Integer.parseInt(eventIdText);

            Connection con = DBConnection.getConnection();

            String registrationSql =
                    "SELECT r.registration_id, " +
                    "s.name, s.email, e.event_name " +
                    "FROM registrations r " +
                    "JOIN students s ON r.student_id = s.id " +
                    "JOIN events e ON r.event_id = e.event_id " +
                    "WHERE r.student_id = ? AND r.event_id = ?";

            PreparedStatement registrationPs =
                    con.prepareStatement(registrationSql);

            registrationPs.setInt(1, studentId);
            registrationPs.setInt(2, eventId);

            ResultSet rs = registrationPs.executeQuery();

            if (!rs.next()) {

                response.getWriter().println(
                        "<h2>You are not registered for this event!</h2>"
                );

                rs.close();
                registrationPs.close();
                con.close();
                return;
            }

            int registrationId =
                    rs.getInt("registration_id");

            String studentName =
                    rs.getString("name");

            String email =
                    rs.getString("email");

            String eventName =
                    rs.getString("event_name");

            rs.close();
            registrationPs.close();

            String tokenCode = String.format(
                    "FOOD-%04d", registrationId
            );

            String checkSql =
                    "SELECT token_code, status " +
                    "FROM food_tokens " +
                    "WHERE registration_id = ?";

            PreparedStatement checkPs =
                    con.prepareStatement(checkSql);

            checkPs.setInt(1, registrationId);

            ResultSet tokenRs =
                    checkPs.executeQuery();

            String status = "UNUSED";

            if (tokenRs.next()) {

                tokenCode =
                        tokenRs.getString("token_code");

                status =
                        tokenRs.getString("status");

            } else {

                String insertSql =
                        "INSERT INTO food_tokens " +
                        "(registration_id, token_code, status) " +
                        "VALUES (?, ?, ?)";

                PreparedStatement insertPs =
                        con.prepareStatement(insertSql);

                insertPs.setInt(1, registrationId);
                insertPs.setString(2, tokenCode);
                insertPs.setString(3, "UNUSED");

                insertPs.executeUpdate();

                insertPs.close();
            }

            tokenRs.close();
            checkPs.close();
            con.close();

            response.getWriter().println(
                    "<!DOCTYPE html>" +
                    "<html lang='en'>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "<meta name='viewport' " +
                    "content='width=device-width, initial-scale=1.0'>" +
                    "<title>Food Token</title>" +
                    "<style>" +
                    "*{margin:0;padding:0;box-sizing:border-box;" +
                    "font-family:Arial,sans-serif;}" +

                    "body{min-height:100vh;display:flex;" +
                    "justify-content:center;align-items:center;" +
                    "padding:20px;" +
                    "background:linear-gradient(135deg,#35127a,#7b22c9,#d23ee8);" +
                    "color:white;}" +

                    ".token{width:480px;max-width:100%;" +
                    "padding:35px;border-radius:25px;" +
                    "background:rgba(255,255,255,0.18);" +
                    "border:1px solid rgba(255,255,255,0.3);" +
                    "backdrop-filter:blur(15px);" +
                    "box-shadow:0 20px 40px rgba(0,0,0,0.3);}" +

                    ".header{text-align:center;margin-bottom:25px;}" +

                    ".icon{font-size:60px;margin-bottom:10px;}" +

                    "h1{font-size:30px;}" +

                    ".subtitle{margin-top:8px;opacity:0.85;}" +

                    ".row{display:flex;justify-content:space-between;" +
                    "gap:20px;padding:14px 0;" +
                    "border-bottom:1px solid rgba(255,255,255,0.2);}" +

                    ".label{font-weight:bold;opacity:0.8;}" +

                    ".value{text-align:right;word-break:break-word;}" +

                    ".food-code{text-align:center;font-size:28px;" +
                    "font-weight:bold;letter-spacing:2px;" +
                    "margin:25px 0;padding:18px;" +
                    "border:2px dashed white;border-radius:15px;}" +

                    ".status{text-align:center;padding:13px;" +
                    "border-radius:25px;" +
                    "background:rgba(0,255,150,0.2);" +
                    "font-weight:bold;}" +

                    ".home{display:block;text-align:center;" +
                    "margin-top:25px;padding:13px;" +
                    "border-radius:25px;background:white;" +
                    "color:#6a1bb1;text-decoration:none;" +
                    "font-weight:bold;}" +

                    ".home:hover{background:#f0d9ff;}" +
                    "</style>" +
                    "</head>" +

                    "<body>" +

                    "<div class='token'>" +

                    "<div class='header'>" +
                    "<div class='icon'>🍔</div>" +
                    "<h1>Food Token</h1>" +
                    "<p class='subtitle'>Campus Event Management System</p>" +
                    "</div>" +

                    "<div class='row'>" +
                    "<span class='label'>Student Name</span>" +
                    "<span class='value'>" + studentName + "</span>" +
                    "</div>" +

                    "<div class='row'>" +
                    "<span class='label'>Email</span>" +
                    "<span class='value'>" + email + "</span>" +
                    "</div>" +

                    "<div class='row'>" +
                    "<span class='label'>Event</span>" +
                    "<span class='value'>" + eventName + "</span>" +
                    "</div>" +

                    "<div class='food-code'>" +
                    tokenCode +
                    "</div>" +

                    "<div class='status'>" +
                    "✓ " + status +
                    "</div>" +

                    "<a href='index.html' class='home'>" +
                    "BACK TO HOME" +
                    "</a>" +

                    "</div>" +

                    "</body>" +
                    "</html>"
            );

        } catch (NumberFormatException e) {

            response.getWriter().println(
                    "<h2>Invalid Event ID!</h2>"
            );

        } catch (Exception e) {

            response.getWriter().println(
                    "<h2>Food Token Error: "
                    + e.getMessage()
                    + "</h2>"
            );
        }
    }
}