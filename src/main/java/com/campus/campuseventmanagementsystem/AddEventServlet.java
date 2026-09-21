package com.campus.campuseventmanagementsystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/AddEventServlet")
public class AddEventServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String eventName = request.getParameter("eventName");
        String eventDate = request.getParameter("eventDate");
        String eventTime = request.getParameter("eventTime");
        String venue = request.getParameter("venue");
        String description = request.getParameter("description");

        String sql = "INSERT INTO events "
                + "(event_name, event_date, event_time, venue, description) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, eventName);
            ps.setString(2, eventDate);
            ps.setString(3, eventTime);
            ps.setString(4, venue);
            ps.setString(5, description);

            int result = ps.executeUpdate();

            ps.close();
            con.close();

            if (result > 0) {

                response.getWriter().println("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <meta charset="UTF-8">
                        <title>Event Added</title>

                        <style>
                            * {
                                box-sizing: border-box;
                                margin: 0;
                                padding: 0;
                                font-family: Arial, sans-serif;
                            }

                            body {
                                min-height: 100vh;
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                background: linear-gradient(
                                    135deg,
                                    #35127a,
                                    #7b22c9,
                                    #d23ee8
                                );
                            }

                            .box {
                                width: 90%;
                                max-width: 500px;
                                padding: 50px 35px;
                                text-align: center;
                                color: white;
                                background: rgba(255,255,255,0.18);
                                border: 1px solid rgba(255,255,255,0.3);
                                border-radius: 25px;
                                backdrop-filter: blur(15px);
                                box-shadow: 0 20px 50px rgba(0,0,0,0.3);
                            }

                            .icon {
                                font-size: 65px;
                                margin-bottom: 20px;
                            }

                            h1 {
                                font-size: 28px;
                                margin-bottom: 15px;
                            }

                            p {
                                margin-bottom: 30px;
                                font-size: 17px;
                            }

                            a {
                                display: inline-block;
                                padding: 13px 30px;
                                background: white;
                                color: #6a1bb1;
                                text-decoration: none;
                                border-radius: 25px;
                                font-weight: bold;
                            }

                            a:hover {
                                background: #f0d9ff;
                            }
                        </style>
                    </head>

                    <body>

                        <div class="box">

                            <div class="icon">✓</div>

                            <h1>Event Added Successfully!</h1>

                            <p>
                                The new event has been added
                                to the database.
                            </p>

                            <a href="StaffEventManagement.html">
                                ADD ANOTHER EVENT
                            </a>

                            <br><br>

                            <a href="event.html">
                                VIEW EVENTS
                            </a>

                        </div>

                    </body>
                    </html>
                    """);

            } else {

                response.getWriter().println(
                    "<h2>Event could not be added!</h2>"
                );
            }

        } catch (Exception e) {

            response.getWriter().println(
                "<h2>Event Error: "
                + e.getMessage()
                + "</h2>"
            );
        }
    }
}