package com.campus.campuseventmanagementsystem;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame() {

        setTitle("Campus Event Management System");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ---------------- TITLE ----------------

        JLabel title = new JLabel(
                "Campus Event Management System",
                SwingConstants.CENTER
        );

        title.setFont(new Font("Arial", Font.BOLD, 22));

        JLabel subtitle = new JLabel(
                "Manage Events, Students and Registrations",
                SwingConstants.CENTER
        );

        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));

        // ---------------- BUTTONS ----------------

        JButton addEvent = new JButton("Add Event");
        JButton viewEvents = new JButton("View Events");
        JButton updateEvent = new JButton("Update Event");
        JButton deleteEvent = new JButton("Delete Event");

        JButton registerStudent =
                new JButton("Student Registration");

        JButton updateStudent =
                new JButton("Update Student");

        JButton deleteStudent =
                new JButton("Delete Student");

        JButton registerEvent =
                new JButton("Register for Event");

        JButton viewRegistrations =
                new JButton("View Registrations");

        // ---------------- MAIN PANEL ----------------

        JPanel mainPanel = new JPanel(
                new BorderLayout(10, 10)
        );

        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 20, 20, 20
                )
        );

        // ---------------- HEADER ----------------

        JPanel header = new JPanel(
                new GridLayout(2, 1, 5, 5)
        );

        header.add(title);
        header.add(subtitle);

        mainPanel.add(
                header,
                BorderLayout.NORTH
        );

        // ---------------- BUTTON PANEL ----------------

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(0, 2, 15, 15)
                );

        buttonPanel.add(addEvent);
        buttonPanel.add(viewEvents);

        buttonPanel.add(updateEvent);
        buttonPanel.add(deleteEvent);

        buttonPanel.add(registerStudent);
        buttonPanel.add(updateStudent);

        buttonPanel.add(deleteStudent);

        buttonPanel.add(registerEvent);
        buttonPanel.add(viewRegistrations);

        mainPanel.add(
                buttonPanel,
                BorderLayout.CENTER
        );

        add(mainPanel);

        // =================================================
        // ADD EVENT
        // =================================================

        addEvent.addActionListener(e -> {

            JTextField nameField = new JTextField();
            JTextField dateField = new JTextField();
            JTextField timeField = new JTextField();
            JTextField venueField = new JTextField();
            JTextField descriptionField = new JTextField();

            JPanel form = new JPanel();

            form.setLayout(
                    new BoxLayout(
                            form,
                            BoxLayout.Y_AXIS
                    )
            );

            form.add(new JLabel("Event Name:"));
            form.add(nameField);

            form.add(new JLabel("Date (YYYY-MM-DD):"));
            form.add(dateField);

            form.add(new JLabel("Time (HH:MM:SS):"));
            form.add(timeField);

            form.add(new JLabel("Venue:"));
            form.add(venueField);

            form.add(new JLabel("Description:"));
            form.add(descriptionField);

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            form,
                            "Add New Event",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if (result == JOptionPane.OK_OPTION) {

                Event event = new Event(
                        nameField.getText(),
                        dateField.getText(),
                        timeField.getText(),
                        venueField.getText(),
                        descriptionField.getText()
                );

                EventManager manager =
                        new EventManager();

                manager.addEvent(event);

                JOptionPane.showMessageDialog(
                        this,
                        "Event Added Successfully!"
                );
            }
        });

        // =================================================
        // VIEW EVENTS
        // =================================================

        viewEvents.addActionListener(e -> {

            EventManager manager =
                    new EventManager();

            String events =
                    manager.viewEvents();

            if (events.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No Events Found!"
                );

            } else {

                JTextArea area =
                        new JTextArea(events);

                area.setEditable(false);

                JScrollPane scrollPane =
                        new JScrollPane(area);

                scrollPane.setPreferredSize(
                        new Dimension(450, 300)
                );

                JOptionPane.showMessageDialog(
                        this,
                        scrollPane,
                        "All Events",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        // =================================================
        // UPDATE EVENT
        // =================================================

        updateEvent.addActionListener(e -> {

            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField dateField = new JTextField();
            JTextField timeField = new JTextField();
            JTextField venueField = new JTextField();
            JTextField descriptionField = new JTextField();

            JPanel form = new JPanel();

            form.setLayout(
                    new BoxLayout(
                            form,
                            BoxLayout.Y_AXIS
                    )
            );

            form.add(new JLabel("Event ID:"));
            form.add(idField);

            form.add(new JLabel("Event Name:"));
            form.add(nameField);

            form.add(new JLabel("Date (YYYY-MM-DD):"));
            form.add(dateField);

            form.add(new JLabel("Time (HH:MM:SS):"));
            form.add(timeField);

            form.add(new JLabel("Venue:"));
            form.add(venueField);

            form.add(new JLabel("Description:"));
            form.add(descriptionField);

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            form,
                            "Update Event",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if (result == JOptionPane.OK_OPTION) {

                try {

                    int eventId =
                            Integer.parseInt(
                                    idField.getText()
                            );

                    EventManager manager =
                            new EventManager();

                    boolean updated =
                            manager.updateEvent(
                                    eventId,
                                    nameField.getText(),
                                    dateField.getText(),
                                    timeField.getText(),
                                    venueField.getText(),
                                    descriptionField.getText()
                            );

                    if (updated) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Event Updated Successfully!"
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Event ID Not Found!"
                        );
                    }

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a valid Event ID!"
                    );
                }
            }
        });

        // =================================================
        // DELETE EVENT
        // =================================================

        deleteEvent.addActionListener(e -> {

            String input =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Event ID to Delete:"
                    );

            if (input != null) {

                try {

                    int eventId =
                            Integer.parseInt(input);

                    int confirm =
                            JOptionPane.showConfirmDialog(
                                    this,
                                    "Are you sure you want to delete this event?",
                                    "Confirm Delete",
                                    JOptionPane.YES_NO_OPTION
                            );

                    if (confirm ==
                            JOptionPane.YES_OPTION) {

                        EventManager manager =
                                new EventManager();

                        boolean deleted =
                                manager.deleteEvent(
                                        eventId
                                );

                        if (deleted) {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Event Deleted Successfully!"
                            );

                        } else {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Event ID Not Found!"
                            );
                        }
                    }

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a valid Event ID!"
                    );
                }
            }
        });

        // =================================================
        // STUDENT REGISTRATION
        // =================================================

        registerStudent.addActionListener(e -> {

            JTextField nameField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField departmentField = new JTextField();

            JPanel form = new JPanel();

            form.setLayout(
                    new BoxLayout(
                            form,
                            BoxLayout.Y_AXIS
                    )
            );

            form.add(new JLabel("Student Name:"));
            form.add(nameField);

            form.add(new JLabel("Email:"));
            form.add(emailField);

            form.add(new JLabel("Department:"));
            form.add(departmentField);

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            form,
                            "Student Registration",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if (result == JOptionPane.OK_OPTION) {

                Student student = new Student(
                        nameField.getText(),
                        emailField.getText(),
                        departmentField.getText()
                );

                EventManager manager =
                        new EventManager();

                manager.addStudent(student);

                JOptionPane.showMessageDialog(
                        this,
                        "Student Registered Successfully!"
                );
            }
        });

        // =================================================
        // UPDATE STUDENT
        // =================================================

        updateStudent.addActionListener(e -> {

            JTextField idField = new JTextField();
            JTextField nameField = new JTextField();
            JTextField emailField = new JTextField();
            JTextField departmentField = new JTextField();

            JPanel form = new JPanel();

            form.setLayout(
                    new BoxLayout(
                            form,
                            BoxLayout.Y_AXIS
                    )
            );

            form.add(new JLabel("Student ID:"));
            form.add(idField);

            form.add(new JLabel("Student Name:"));
            form.add(nameField);

            form.add(new JLabel("Email:"));
            form.add(emailField);

            form.add(new JLabel("Department:"));
            form.add(departmentField);

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            form,
                            "Update Student",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if (result == JOptionPane.OK_OPTION) {

                try {

                    int studentId =
                            Integer.parseInt(
                                    idField.getText()
                            );

                    EventManager manager =
                            new EventManager();

                    boolean updated =
                            manager.updateStudent(
                                    studentId,
                                    nameField.getText(),
                                    emailField.getText(),
                                    departmentField.getText()
                            );

                    if (updated) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Student Updated Successfully!"
                        );

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Student ID Not Found!"
                        );
                    }

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a valid Student ID!"
                    );
                }
            }
        });

        // =================================================
        // DELETE STUDENT
        // =================================================

        deleteStudent.addActionListener(e -> {

            String input =
                    JOptionPane.showInputDialog(
                            this,
                            "Enter Student ID to Delete:"
                    );

            if (input != null) {

                try {

                    int studentId =
                            Integer.parseInt(input);

                    int confirm =
                            JOptionPane.showConfirmDialog(
                                    this,
                                    "Are you sure you want to delete this student?",
                                    "Confirm Delete",
                                    JOptionPane.YES_NO_OPTION
                            );

                    if (confirm ==
                            JOptionPane.YES_OPTION) {

                        // FIXED: id instead of student_id
                        String sql =
                                "DELETE FROM students WHERE id = ?";

                        try {

                            java.sql.Connection con =
                                    DBConnection.getConnection();

                            java.sql.PreparedStatement ps =
                                    con.prepareStatement(sql);

                            ps.setInt(
                                    1,
                                    studentId
                            );

                            int rows =
                                    ps.executeUpdate();

                            ps.close();
                            con.close();

                            if (rows > 0) {

                                JOptionPane.showMessageDialog(
                                        this,
                                        "Student Deleted Successfully!"
                                );

                            } else {

                                JOptionPane.showMessageDialog(
                                        this,
                                        "Student ID Not Found!"
                                );
                            }

                        } catch (Exception ex) {

                            // FIXED: show actual database error
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Delete Error: "
                                    + ex.getMessage()
                            );
                        }
                    }

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter a valid Student ID!"
                    );
                }
            }
        });

        // =================================================
        // REGISTER FOR EVENT
        // =================================================

        registerEvent.addActionListener(e -> {

            JTextField studentIdField =
                    new JTextField();

            JTextField eventIdField =
                    new JTextField();

            JPanel form = new JPanel();

            form.setLayout(
                    new BoxLayout(
                            form,
                            BoxLayout.Y_AXIS
                    )
            );

            form.add(new JLabel("Student ID:"));
            form.add(studentIdField);

            form.add(new JLabel("Event ID:"));
            form.add(eventIdField);

            int result =
                    JOptionPane.showConfirmDialog(
                            this,
                            form,
                            "Register for Event",
                            JOptionPane.OK_CANCEL_OPTION
                    );

            if (result == JOptionPane.OK_OPTION) {

                try {

                    int studentId =
                            Integer.parseInt(
                                    studentIdField.getText()
                            );

                    int eventId =
                            Integer.parseInt(
                                    eventIdField.getText()
                            );

                    Registration registration =
                            new Registration(
                                    studentId,
                                    eventId
                            );

                    EventManager manager =
                            new EventManager();

                    manager.registerStudentForEvent(
                            registration
                    );

                    JOptionPane.showMessageDialog(
                            this,
                            "Student Registered for Event Successfully!"
                    );

                } catch (NumberFormatException ex) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Please enter valid ID numbers!"
                    );
                }
            }
        });

        // =================================================
        // VIEW REGISTRATIONS
        // =================================================

        viewRegistrations.addActionListener(e -> {

            EventManager manager =
                    new EventManager();

            String registrations =
                    manager.viewRegistrations();

            if (registrations.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "No Registrations Found!"
                );

            } else {

                JTextArea area =
                        new JTextArea(registrations);

                area.setEditable(false);

                JScrollPane scrollPane =
                        new JScrollPane(area);

                scrollPane.setPreferredSize(
                        new Dimension(450, 300)
                );

                JOptionPane.showMessageDialog(
                        this,
                        scrollPane,
                        "All Registrations",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }

    // =================================================
    // MAIN
    // =================================================

    public static void main(String[] args) {

        MainFrame frame =
                new MainFrame();

        frame.setVisible(true);
    }
}