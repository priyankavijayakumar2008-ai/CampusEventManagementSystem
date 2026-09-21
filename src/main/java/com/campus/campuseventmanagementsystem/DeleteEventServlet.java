package com.campus.campuseventmanagementsystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/DeleteEventServlet")
public class DeleteEventServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String eventId = request.getParameter("eventId");

        String sql = "DELETE FROM events WHERE event_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, Integer.parseInt(eventId));

            int result = ps.executeUpdate();

            ps.close();
            con.close();

            if (result > 0) {

                response.getWriter().println(
                    "<html>" +
                    "<head>" +
                    "<title>Delete Event</title>" +
                    "</head>" +
                    "<body style='font-family:Arial;" +
                    "text-align:center;" +
                    "padding-top:100px;" +
                    "background:linear-gradient(135deg,#35127a,#d23ee8);" +
                    "color:white;'>" +

                    "<h1>✓ Event Deleted Successfully!</h1>" +
                    "<br>" +

                    "<a href='StaffEventManagement.html' " +
                    "style='color:white;" +
                    "text-decoration:none;" +
                    "font-weight:bold;'>" +
                    "BACK TO EVENT MANAGEMENT" +
                    "</a>" +

                    "</body>" +
                    "</html>"
                );

            } else {

                response.getWriter().println(
                    "<h2>Event ID not found!</h2>"
                );
            }

        } catch (NumberFormatException e) {

            response.getWriter().println(
                "<h2>Invalid Event ID!</h2>"
            );

        } catch (Exception e) {

            response.getWriter().println(
                "<h2>Delete Event Error: "
                + e.getMessage()
                + "</h2>"
            );
        }
    }
}