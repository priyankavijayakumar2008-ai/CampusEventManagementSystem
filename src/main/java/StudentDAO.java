package com.campus.campuseventmanagementsystem;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class StudentDAO {

    public boolean registerStudent(Student student) {

        String sql = "INSERT INTO students (name, email, department, password) VALUES (?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getDepartment());
            ps.setString(4, student.getPassword());

            ps.executeUpdate();

            ps.close();
            con.close();

            return true;

        } catch (Exception e) {
    throw new RuntimeException("Database Error: " + e.getMessage(), e);
}
    }
}