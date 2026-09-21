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

@WebServlet("/FoodTokenRedeemServlet")
public class FoodTokenRedeemServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        String tokenCode = request.getParameter("tokenCode");

        response.setContentType("text/html;charset=UTF-8");

        if (tokenCode == null || tokenCode.trim().isEmpty()) {
            response.getWriter().println("""
                <html>
                <body style="font-family:Arial;text-align:center;padding:100px;">
                    <h2>❌ Please enter Food Token</h2>
                    <a href="FoodTokenRedeem.html">BACK</a>
                </body>
                </html>
                """);
            return;
        }

        try {

            Connection con = DBConnection.getConnection();

            // Check token
            String checkSql =
                    "SELECT token_id, status FROM food_tokens " +
                    "WHERE token_code = ?";

            PreparedStatement checkPs =
                    con.prepareStatement(checkSql);

            checkPs.setString(1, tokenCode);

            ResultSet rs = checkPs.executeQuery();

            if (!rs.next()) {

                rs.close();
                checkPs.close();
                con.close();

                response.getWriter().println("""
                    <html>
                    <body style="
                        font-family:Arial;
                        text-align:center;
                        padding:100px;
                        background:linear-gradient(135deg,#667eea,#764ba2);
                        color:white;
                    ">
                        <h2>❌ Invalid Food Token</h2>
                        <br>
                        <a href="FoodTokenRedeem.html"
                           style="
                           background:white;
                           color:#764ba2;
                           padding:12px 25px;
                           border-radius:10px;
                           text-decoration:none;
                           font-weight:bold;
                           ">
                           TRY AGAIN
                        </a>
                    </body>
                    </html>
                    """);

                return;
            }

            int tokenId = rs.getInt("token_id");
            String status = rs.getString("status");

            rs.close();
            checkPs.close();

            // Already used
            if ("USED".equalsIgnoreCase(status)) {

                con.close();

                response.getWriter().println("""
                    <html>
                    <body style="
                        font-family:Arial;
                        text-align:center;
                        padding:100px;
                        background:linear-gradient(135deg,#667eea,#764ba2);
                        color:white;
                    ">
                        <h2>⚠️ Token Already Used</h2>
                        <p>This food token has already been redeemed.</p>
                        <br>
                        <a href="FoodTokenRedeem.html"
                           style="
                           background:white;
                           color:#764ba2;
                           padding:12px 25px;
                           border-radius:10px;
                           text-decoration:none;
                           font-weight:bold;
                           ">
                           BACK
                        </a>
                    </body>
                    </html>
                    """);

                return;
            }

            // Change UNUSED → USED
            String updateSql =
                    "UPDATE food_tokens " +
                    "SET status = 'USED' " +
                    "WHERE token_id = ?";

            PreparedStatement updatePs =
                    con.prepareStatement(updateSql);

            updatePs.setInt(1, tokenId);

            int result = updatePs.executeUpdate();

            updatePs.close();
            con.close();

            if (result > 0) {

                response.getWriter().println("""
                    <html>
                    <head>
                        <title>Food Token Redeemed</title>
                    </head>

                    <body style="
                        margin:0;
                        font-family:Arial;
                        background:linear-gradient(135deg,#667eea,#764ba2);
                        height:100vh;
                        display:flex;
                        justify-content:center;
                        align-items:center;
                    ">

                        <div style="
                            background:rgba(255,255,255,0.15);
                            backdrop-filter:blur(15px);
                            padding:50px;
                            border-radius:20px;
                            text-align:center;
                            color:white;
                            box-shadow:0 8px 30px rgba(0,0,0,0.3);
                        ">

                            <h1>✅ Food Token Redeemed!</h1>

                            <p>
                                The food token has been successfully marked as USED.
                            </p>

                            <a href="FoodTokenRedeem.html"
                               style="
                               display:inline-block;
                               margin-top:20px;
                               background:white;
                               color:#764ba2;
                               padding:12px 25px;
                               border-radius:10px;
                               text-decoration:none;
                               font-weight:bold;
                               ">
                               REDEEM ANOTHER TOKEN
                            </a>

                        </div>

                    </body>
                    </html>
                    """);

            } else {

                response.getWriter().println(
                    "<h2 style='text-align:center;'>❌ Token Redemption Failed!</h2>"
                );
            }

        } catch (Exception e) {

            response.getWriter().println("""
                <html>
                <body style="
                    font-family:Arial;
                    text-align:center;
                    padding:100px;
                    background:#f5f5f5;
                ">

                    <h2>❌ Error</h2>

                    <p>
                """ + e.getMessage() + """
                    </p>

                    <a href="FoodTokenRedeem.html">
                        BACK
                    </a>

                </body>
                </html>
                """);
        }
    }
}