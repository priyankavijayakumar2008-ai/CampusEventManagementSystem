package com.campus.campuseventmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String HOST =
            System.getenv("DB_HOST");

    private static final String PORT =
            System.getenv("DB_PORT");

    private static final String DATABASE =
            System.getenv("DB_NAME");

    private static final String USER =
            System.getenv("DB_USER");

    private static final String PASSWORD =
            System.getenv("DB_PASSWORD");

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE
            + "?ssl-mode=REQUIRED";

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "MySQL JDBC Driver not found!", e);
        }

        return DriverManager.getConnection(
                URL, USER, PASSWORD);
    }
}
