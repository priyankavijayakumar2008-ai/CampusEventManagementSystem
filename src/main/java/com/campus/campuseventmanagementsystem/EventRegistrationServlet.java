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

@WebServlet("/EventRegistrationServlet")
public class EventRegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Check student login
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("studentId") == null) {
            response.sendRedirect("Login.html");
            return;
        }

        int studentId = (int) session.getAttribute("studentId");

        // Get event ID
        String eventIdText = request.getParameter("eventId");

        if (eventIdText == null || eventIdText.trim().isEmpty()) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(
                    "<h2 style='text-align:center;'>Invalid Event ID!</h2>"
            );
            return;
        }

        int eventId = Integer.parseInt(eventIdText);

        try {

            Connection con = DBConnection.getConnection();

            // -----------------------------------------
            // 1. Check duplicate registration
            // -----------------------------------------

            String checkSql =
                    "SELECT registration_id FROM registrations " +
                    "WHERE student_id = ? AND event_id = ?";

            PreparedStatement checkPs = con.prepareStatement(checkSql);

            checkPs.setInt(1, studentId);
            checkPs.setInt(2, eventId);

            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {

                rs.close();
                checkPs.close();
                con.close();

                response.setContentType("text/html;charset=UTF-8");

                response.getWriter().println("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset="UTF-8">
                        <title>Already Registered</title>

                        <style>
                            body {
                                margin: 0;
                                font-family: Arial, sans-serif;
                                background: linear-gradient(135deg, #667eea, #764ba2);
                                height: 100vh;
                                display: flex;
                                justify-content: center;
                                align-items: center;
                            }

                            .card {
                                background: rgba(255,255,255,0.15);
                                backdrop-filter: blur(15px);
                                padding: 45px;
                                border-radius: 20px;
                                text-align: center;
                                color: white;
                                box-shadow: 0 8px 30px rgba(0,0,0,0.3);
                            }

                            h2 {
                                margin-bottom: 25px;
                            }

                            a {
                                display: inline-block;
                                padding: 12px 25px;
                                background: white;
                                color: #764ba2;
                                text-decoration: none;
                                border-radius: 10px;
                                font-weight: bold;
                            }
                        </style>
                    </head>

                    <body>

                        <div class="card">

                            <h2>⚠️ Already Registered for this Event!</h2>

                            <a href="event.html">BACK TO EVENTS</a>

                        </div>

                    </body>
                    </html>
                    """);

                return;
            }

            rs.close();
            checkPs.close();

            // -----------------------------------------
            // 2. Insert registration with ₹200 amount
            // -----------------------------------------

            String insertSql =
                    "INSERT INTO registrations " +
                    "(student_id, event_id, amount) " +
                    "VALUES (?, ?, ?)";

            PreparedStatement insertPs = con.prepareStatement(insertSql);

            insertPs.setInt(1, studentId);
            insertPs.setInt(2, eventId);
            insertPs.setDouble(3, 200.00);

            int result = insertPs.executeUpdate();

            insertPs.close();
            con.close();

            // -----------------------------------------
            // 3. Redirect to Payment page
            // -----------------------------------------

            if (result > 0) {

                response.sendRedirect(
                        "Payment.html?eventId=" + eventId
                );

            } else {

                response.setContentType("text/html;charset=UTF-8");

                response.getWriter().println(
                        "<h2 style='text-align:center;'>Registration Failed!</h2>"
                );
            }

        } catch (Exception e) {

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Registration Error</title>
                </head>

                <body style="
                    background:linear-gradient(135deg,#667eea,#764ba2);
                    color:white;
                    font-family:Arial;
                    text-align:center;
                    padding-top:150px;
                ">

                    <h2>❌ Registration Error</h2>

                    <p>
                """ + e.getMessage() + """
                    </p>

                    <a href="event.html"
                       style="
                       color:#764ba2;
                       background:white;
                       padding:12px 25px;
                       border-radius:10px;
                       text-decoration:none;
                       font-weight:bold;
                       ">
                       BACK TO EVENTS
                    </a>

                </body>
                </html>
                """);
        }
    }
}