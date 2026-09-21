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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String sql = "SELECT * FROM students WHERE email = ? AND password = ?";

        response.setContentType("text/html;charset=UTF-8");

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request.getSession().setAttribute("studentId", rs.getInt("id"));
                String name = rs.getString("name");

                response.getWriter().println("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Login Successful</title>

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
                                background: linear-gradient(135deg, #35127a, #7b22c9, #d23ee8);
                            }

                            .success-box {
                                width: 500px;
                                padding: 50px 40px;
                                text-align: center;
                                background: rgba(255, 255, 255, 0.18);
                                border: 1px solid rgba(255, 255, 255, 0.3);
                                border-radius: 25px;
                                box-shadow: 0 20px 50px rgba(0, 0, 0, 0.25);
                                backdrop-filter: blur(15px);
                                color: white;
                            }

                            .icon {
                                font-size: 60px;
                                margin-bottom: 20px;
                            }

                            h1 {
                                font-size: 30px;
                                margin-bottom: 15px;
                            }

                            p {
                                font-size: 17px;
                                margin-bottom: 30px;
                            }

                            .home-btn {
                                display: inline-block;
                                padding: 13px 30px;
                                background: white;
                                color: #6a1bb1;
                                text-decoration: none;
                                border-radius: 25px;
                                font-weight: bold;
                                transition: 0.3s;
                            }

                            .home-btn:hover {
                                background: #f0d9ff;
                                transform: scale(1.05);
                            }
                        </style>
                    </head>

                    <body>

                        <div class="success-box">

                            <div class="icon">✓</div>

                            <h1>Login Successful!</h1>

                            <p>Welcome, %s!</p>

                            <a href="index.html" class="home-btn">
                                BACK TO HOME
                            </a>

                        </div>

                    </body>
                    </html>
                    """.formatted(name));

            } else {

                response.getWriter().println("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Login Failed</title>
                        <style>
                            body {
                                min-height: 100vh;
                                display: flex;
                                justify-content: center;
                                align-items: center;
                                background: linear-gradient(135deg, #35127a, #7b22c9, #d23ee8);
                                font-family: Arial, sans-serif;
                            }

                            .box {
                                padding: 50px;
                                text-align: center;
                                color: white;
                                background: rgba(255,255,255,0.18);
                                border-radius: 25px;
                                backdrop-filter: blur(15px);
                            }

                            h1 {
                                margin-bottom: 25px;
                            }

                            a {
                                display: inline-block;
                                padding: 12px 25px;
                                background: white;
                                color: #6a1bb1;
                                text-decoration: none;
                                border-radius: 25px;
                                font-weight: bold;
                            }
                        </style>
                    </head>

                    <body>
                        <div class="box">
                            <h1>Invalid Email or Password!</h1>
                            <a href="Login.html">TRY AGAIN</a>
                        </div>
                    </body>
                    </html>
                    """);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            response.getWriter().println("<h2>Login Error: " + e.getMessage() + "</h2>");
        }
    }
}