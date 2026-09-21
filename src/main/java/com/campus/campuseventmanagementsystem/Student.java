package com.campus.campuseventmanagementsystem;

public class Student {

    private String name;
    private String email;
    private String department;
    private String password;

    public Student(String name, String email, String department, String password) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.password = password;
    }

    // 3-value constructor
    public Student(String name, String email, String department) {
        this(name, email, department, "");
    }

    public String getName() {
        return name;
    }

    public String getStudentName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getPassword() {
        return password;
    }
}