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

@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        int studentCount = 0;
        int eventCount = 0;
        int registrationCount = 0;

        try {
            Connection con = DBConnection.getConnection();

            // Count Students
            PreparedStatement ps1 =
                    con.prepareStatement("SELECT COUNT(*) FROM students");
            ResultSet rs1 = ps1.executeQuery();

            if (rs1.next()) {
                studentCount = rs1.getInt(1);
            }

            // Count Events
            PreparedStatement ps2 =
                    con.prepareStatement("SELECT COUNT(*) FROM events");
            ResultSet rs2 = ps2.executeQuery();

            if (rs2.next()) {
                eventCount = rs2.getInt(1);
            }

            // Count Registrations
            PreparedStatement ps3 =
                    con.prepareStatement("SELECT COUNT(*) FROM registrations");
            ResultSet rs3 = ps3.executeQuery();

            if (rs3.next()) {
                registrationCount = rs3.getInt(1);
            }

            rs1.close();
            rs2.close();
            rs3.close();

            ps1.close();
            ps2.close();
            ps3.close();

            con.close();

            request.setAttribute("studentCount", studentCount);
            request.setAttribute("eventCount", eventCount);
            request.setAttribute("registrationCount", registrationCount);

            request.getRequestDispatcher("StaffDashboard.jsp")
                   .forward(request, response);

        } catch (Exception e) {

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println(
                    "<h2>Dashboard Error: "
                    + e.getMessage()
                    + "</h2>"
            );
        }
    }
}