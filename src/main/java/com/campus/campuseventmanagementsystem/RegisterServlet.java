package com.campus.campuseventmanagementsystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String department = request.getParameter("department");
        String password = request.getParameter("password");

        Student student =
                new Student(name, email, department, password);

        StudentDAO dao = new StudentDAO();

        boolean result = dao.registerStudent(student);

        response.setContentType("text/html;charset=UTF-8");

        if (result) {

            response.getWriter().println(
                "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>Registration Successful</title>" +
                "<style>" +
                "body{" +
                "margin:0;" +
                "height:100vh;" +
                "display:flex;" +
                "justify-content:center;" +
                "align-items:center;" +
                "font-family:Arial,sans-serif;" +
                "background:linear-gradient(135deg,#35127a,#7b22c9,#d23ee8);" +
                "}" +
                ".box{" +
                "background:white;" +
                "padding:50px;" +
                "width:420px;" +
                "text-align:center;" +
                "border-radius:20px;" +
                "box-shadow:0 15px 40px rgba(0,0,0,0.3);" +
                "}" +
                "h1{color:#7020a8;}" +
                "p{color:#555;font-size:16px;}" +
                ".btn{" +
                "display:inline-block;" +
                "margin-top:20px;" +
                "padding:12px 25px;" +
                "background:#7020a8;" +
                "color:white;" +
                "text-decoration:none;" +
                "border-radius:25px;" +
                "font-weight:bold;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class='box'>" +
                "<h1>Registration Successful!</h1>" +
                "<p>Your student account has been created successfully.</p>" +
                "<a href='Login.html' class='btn'>Go to Login</a>" +
                "</div>" +
                "</body>" +
                "</html>"
            );

        } else {

            response.getWriter().println(
                "<h2>Registration Failed!</h2>"
            );
        }
    }
}