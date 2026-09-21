package com.campus.campuseventmanagementsystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/ViewRegistrationsServlet")
public class ViewRegistrationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String sql =
                "SELECT r.registration_id, " +
                "s.name, s.email, " +
                "e.event_name, e.event_date, " +
                "e.event_time, e.venue, " +
                "r.amount " +
                "FROM registrations r " +
                "JOIN students s ON r.student_id = s.id " +
                "JOIN events e ON r.event_id = e.event_id " +
                "ORDER BY r.registration_id";

        double totalAmount = 0;

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            StringBuilder tableRows = new StringBuilder();

            while (rs.next()) {

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

                double amount =
                        rs.getDouble("amount");

                totalAmount += amount;

                tableRows.append(
                        "<tr>" +

                        "<td>" + registrationId + "</td>" +

                        "<td>" + name + "</td>" +

                        "<td>" + email + "</td>" +

                        "<td>" + eventName + "</td>" +

                        "<td>" + eventDate + "</td>" +

                        "<td>" + eventTime + "</td>" +

                        "<td>" + venue + "</td>" +

                        "<td>₹" +
                        String.format("%.2f", amount) +
                        "</td>" +

                        "</tr>"
                );
            }

            rs.close();
            ps.close();
            con.close();

            response.getWriter().println(

                    "<!DOCTYPE html>" +

                    "<html lang='en'>" +

                    "<head>" +

                    "<meta charset='UTF-8'>" +

                    "<meta name='viewport' " +
                    "content='width=device-width, initial-scale=1.0'>" +

                    "<title>View Registrations</title>" +

                    "<style>" +

                    "*{" +
                    "margin:0;" +
                    "padding:0;" +
                    "box-sizing:border-box;" +
                    "font-family:Arial,sans-serif;" +
                    "}" +

                    "body{" +
                    "min-height:100vh;" +
                    "background:linear-gradient(135deg,#35127a,#7b22c9,#d23ee8);" +
                    "color:white;" +
                    "padding:40px 20px;" +
                    "}" +

                    ".container{" +
                    "width:1200px;" +
                    "max-width:100%;" +
                    "margin:auto;" +
                    "}" +

                    "h1{" +
                    "text-align:center;" +
                    "font-size:32px;" +
                    "margin-bottom:30px;" +
                    "}" +

                    ".table-box{" +
                    "background:rgba(255,255,255,0.18);" +
                    "border:1px solid rgba(255,255,255,0.3);" +
                    "backdrop-filter:blur(15px);" +
                    "border-radius:20px;" +
                    "padding:20px;" +
                    "overflow-x:auto;" +
                    "box-shadow:0 15px 35px rgba(0,0,0,0.25);" +
                    "}" +

                    "table{" +
                    "width:100%;" +
                    "border-collapse:collapse;" +
                    "min-width:1000px;" +
                    "}" +

                    "th,td{" +
                    "padding:13px;" +
                    "text-align:center;" +
                    "border-bottom:1px solid rgba(255,255,255,0.2);" +
                    "}" +

                    "th{" +
                    "background:rgba(255,255,255,0.15);" +
                    "font-weight:bold;" +
                    "}" +

                    "tr:hover{" +
                    "background:rgba(255,255,255,0.08);" +
                    "}" +

                    ".total{" +
                    "margin-top:25px;" +
                    "padding:18px;" +
                    "text-align:right;" +
                    "font-size:22px;" +
                    "font-weight:bold;" +
                    "background:rgba(0,255,150,0.18);" +
                    "border:1px solid rgba(0,255,150,0.35);" +
                    "border-radius:15px;" +
                    "}" +

                    ".home{" +
                    "display:block;" +
                    "width:fit-content;" +
                    "margin:25px auto 0;" +
                    "padding:13px 30px;" +
                    "border-radius:25px;" +
                    "background:white;" +
                    "color:#6a1bb1;" +
                    "text-decoration:none;" +
                    "font-weight:bold;" +
                    "}" +

                    ".home:hover{" +
                    "background:#f0d9ff;" +
                    "}" +

                    "</style>" +

                    "</head>" +

                    "<body>" +

                    "<div class='container'>" +

                    "<h1>📋 Event Registrations</h1>" +

                    "<div class='table-box'>" +

                    "<table>" +

                    "<tr>" +

                    "<th>Registration ID</th>" +
                    "<th>Student Name</th>" +
                    "<th>Email</th>" +
                    "<th>Event Name</th>" +
                    "<th>Date</th>" +
                    "<th>Time</th>" +
                    "<th>Venue</th>" +
                    "<th>Amount</th>" +

                    "</tr>" +

                    tableRows.toString() +

                    "</table>" +

                    "</div>" +

                    "<div class='total'>" +

                    "TOTAL AMOUNT: ₹" +
                    String.format("%.2f", totalAmount) +

                    "</div>" +

                    "<a href='DashboardServlet' class='home'>" +

                    "BACK TO DASHBOARD" +

                    "</a>" +

                    "</div>" +

                    "</body>" +

                    "</html>"
            );

        } catch (Exception e) {

            response.getWriter().println(
                    "<h2>View Registrations Error: "
                    + e.getMessage()
                    + "</h2>"
            );
        }
    }
}