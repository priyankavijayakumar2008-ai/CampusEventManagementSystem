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

@WebServlet("/ViewEventsServlet")
public class ViewEventsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String search = request.getParameter("search");

        StringBuilder html = new StringBuilder();

        html.append("""
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">

                <title>Campus Events</title>

                <style>

                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                        font-family: Arial, sans-serif;
                    }

                    body {
                        min-height: 100vh;

                        background: linear-gradient(
                            135deg,
                            #35127a,
                            #7b22c9,
                            #d23ee8
                        );

                        padding: 40px;
                        color: white;
                    }

                    .container {
                        max-width: 1100px;
                        margin: auto;
                    }

                    h1 {
                        text-align: center;
                        font-size: 38px;
                        margin-bottom: 10px;
                    }

                    .subtitle {
                        text-align: center;
                        margin-bottom: 25px;
                    }

                    .search-box {
                        display: flex;
                        justify-content: center;
                        margin-bottom: 35px;
                    }

                    .search-box input {
                        width: 500px;
                        max-width: 90%;

                        padding: 15px 20px;

                        border: none;
                        border-radius: 30px;

                        outline: none;

                        font-size: 16px;
                    }

                    .search-box button {
                        margin-left: 10px;

                        padding: 15px 25px;

                        border: none;
                        border-radius: 30px;

                        background: white;
                        color: #6a1bb1;

                        font-weight: bold;

                        cursor: pointer;
                    }

                    .search-box button:hover {
                        background: #f0d9ff;
                    }

                    .events {
                        display: grid;

                        grid-template-columns:
                            repeat(auto-fit, minmax(280px, 1fr));

                        gap: 25px;
                    }

                    .event-card {
                        background: rgba(255,255,255,0.18);

                        border: 1px solid
                            rgba(255,255,255,0.3);

                        border-radius: 25px;

                        padding: 30px;

                        backdrop-filter: blur(15px);

                        box-shadow:
                            0 15px 35px
                            rgba(0,0,0,0.25);
                    }

                    .event-icon {
                        font-size: 45px;
                        margin-bottom: 15px;
                    }

                    h2 {
                        margin-bottom: 15px;
                        font-size: 24px;
                    }

                    p {
                        margin: 8px 0;
                        line-height: 1.5;
                    }

                    .register-btn {
                        display: inline-block;

                        margin-top: 20px;

                        padding: 12px 25px;

                        background: white;
                        color: #6a1bb1;

                        text-decoration: none;

                        border-radius: 25px;

                        font-weight: bold;
                    }

                    .register-btn:hover {
                        background: #f0d9ff;
                    }

                    .no-events {
                        text-align: center;

                        font-size: 20px;

                        margin-top: 30px;
                    }

                    .home-btn {
                        display: block;

                        width: fit-content;

                        margin: 40px auto 0;

                        padding: 12px 28px;

                        background:
                            rgba(255,255,255,0.2);

                        color: white;

                        text-decoration: none;

                        border:
                            1px solid
                            rgba(255,255,255,0.4);

                        border-radius: 25px;

                        font-weight: bold;
                    }

                    .home-btn:hover {
                        background:
                            rgba(255,255,255,0.3);
                    }

                </style>

            </head>

            <body>

            <div class="container">

                <h1>
                    🎪 Campus Events
                </h1>

                <p class="subtitle">
                    Explore upcoming events and register now!
                </p>

                <form
                    class="search-box"
                    method="get"
                    action="ViewEventsServlet">

                    <input
                        type="text"
                        name="search"
                        placeholder="🔍 Search event name..."
            """);

        if (search != null) {

            html.append(" value=\"");
            html.append(search);
            html.append("\"");

        }

        html.append("""
                    >

                    <button type="submit">
                        SEARCH
                    </button>

                </form>

                <div class="events">
            """);

        boolean found = false;

        try {

            Connection con =
                    DBConnection.getConnection();

            String sql;

            if (search != null &&
                !search.trim().isEmpty()) {

                sql =
                    "SELECT * FROM events " +
                    "WHERE event_name LIKE ? " +
                    "ORDER BY event_date";

            } else {

                sql =
                    "SELECT * FROM events " +
                    "ORDER BY event_date";
            }

            PreparedStatement ps =
                    con.prepareStatement(sql);

            if (search != null &&
                !search.trim().isEmpty()) {

                ps.setString(
                    1,
                    "%" + search.trim() + "%"
                );
            }

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                found = true;

                int eventId =
                        rs.getInt("event_id");

                String eventName =
                        rs.getString("event_name");

                String eventDate =
                        rs.getString("event_date");

                String eventTime =
                        rs.getString("event_time");

                String venue =
                        rs.getString("venue");

                String description =
                        rs.getString("description");

                html.append("""
                    <div class="event-card">

                        <div class="event-icon">
                            🎪
                        </div>

                        <h2>
                    """);

                html.append(eventName);

                html.append("""
                        </h2>

                        <p>
                            <strong>Date:</strong>
                    """);

                html.append(eventDate);

                html.append("""
                        </p>

                        <p>
                            <strong>Time:</strong>
                    """);

                html.append(eventTime);

                html.append("""
                        </p>

                        <p>
                            <strong>Venue:</strong>
                    """);

                html.append(venue);

                html.append("""
                        </p>

                        <p>
                    """);

                html.append(description);

                html.append("""
                        </p>

                        <a href="EventRegistration.html?eventId=
                    """);

                html.append(eventId);

                html.append("""
                        " class="register-btn">

                            Register Now

                        </a>

                    </div>
                    """);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            html.append("""
                <p class="no-events">
                    Unable to load events.
                </p>
                """);
        }

        if (!found &&
            search != null &&
            !search.trim().isEmpty()) {

            html.append("""
                <p class="no-events">
                    ❌ No events found.
                </p>
                """);
        }

        html.append("""
                </div>

                <a href="index.html"
                   class="home-btn">

                    BACK TO HOME

                </a>

            </div>

            </body>
            </html>
            """);

        response.getWriter()
                .println(html.toString());
    }
}