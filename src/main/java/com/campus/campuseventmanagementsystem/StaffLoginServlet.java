package com.campus.campuseventmanagementsystem;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/StaffLoginServlet")
public class StaffLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Staff Login Credentials
        String staffUsername = "staff";
        String staffPassword = "Staff@123";

        if (staffUsername.equals(username)
                && staffPassword.equals(password)) {

            // Login successful → Staff Dashboard
            response.sendRedirect("DashboardServlet");

        } else {

            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println("""
                <!DOCTYPE html>
                <html>
                <head>

                    <meta charset="UTF-8">

                    <title>Staff Login Failed</title>

                    <style>

                        * {
                            margin: 0;
                            padding: 0;
                            box-sizing: border-box;
                            font-family: Arial, sans-serif;
                        }

                        body {
                            min-height: 100vh;
                            display: flex;
                            justify-content: center;
                            align-items: center;

                            background:
                            linear-gradient(
                                135deg,
                                #35127a,
                                #7b22c9,
                                #d23ee8
                            );
                        }

                        .box {
                            width: 450px;
                            padding: 50px;

                            text-align: center;
                            color: white;

                            background:
                            rgba(255,255,255,0.18);

                            border:
                            1px solid
                            rgba(255,255,255,0.3);

                            border-radius: 25px;

                            backdrop-filter: blur(15px);

                            box-shadow:
                            0 20px 50px
                            rgba(0,0,0,0.25);
                        }

                        .icon {
                            font-size: 55px;
                            margin-bottom: 20px;
                        }

                        h1 {
                            font-size: 28px;
                            margin-bottom: 15px;
                        }

                        p {
                            margin-bottom: 30px;
                            color: #eeeeee;
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

                        <div class="icon">
                            ❌
                        </div>

                        <h1>
                            Invalid Staff Login
                        </h1>

                        <p>
                            Username or Password is incorrect.
                        </p>

                        <a href="StaffLogin.html">
                            TRY AGAIN
                        </a>

                    </div>

                </body>
                </html>
                """);
        }
    }
}