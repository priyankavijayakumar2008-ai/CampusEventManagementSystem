package com.campus.campuseventmanagementsystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/UpdateEventServlet")
public class UpdateEventServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String eventId = request.getParameter("eventId");
        String eventName = request.getParameter("eventName");
        String eventDate = request.getParameter("eventDate");
        String eventTime = request.getParameter("eventTime");
        String venue = request.getParameter("venue");
        String description = request.getParameter("description");

        String sql =
                "UPDATE events SET " +
                "event_name = ?, " +
                "event_date = ?, " +
                "event_time = ?, " +
                "venue = ?, " +
                "description = ? " +
                "WHERE event_id = ?";

        try {

            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, eventName);
            ps.setString(2, eventDate);
            ps.setString(3, eventTime);
            ps.setString(4, venue);
            ps.setString(5, description);
            ps.setInt(6, Integer.parseInt(eventId));

            int result = ps.executeUpdate();

            ps.close();
            con.close();

            if (result > 0) {

                response.getWriter().println(
                    "<html><body style='font-family:Arial;" +
                    "text-align:center;padding-top:100px;" +
                    "background:linear-gradient(135deg,#35127a,#d23ee8);" +
                    "color:white;'>" +

                    "<h1>✓ Event Updated Successfully!</h1>" +

                    "<br>" +

                    "<a href='StaffEventManagement.html' " +
                    "style='color:white;'>BACK TO EVENT MANAGEMENT</a>" +

                    "</body></html>"
                );

            } else {

                response.getWriter().println(
                    "<h2>Event not found!</h2>"
                );
            }

        } catch (Exception e) {

            response.getWriter().println(
                "<h2>Update Event Error: "
                + e.getMessage()
                + "</h2>"
            );
        }
    }
}