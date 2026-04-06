package ui;

import service.CourseService;
import javax.swing.*;
import java.awt.*;

public class AdminDashboardUI extends JFrame {

    public AdminDashboardUI() {

        setTitle("Admin Dashboard");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1, 10, 10));

        JButton addBtn = new JButton("Add Course");
        JButton deleteBtn = new JButton("Delete Course");
        JButton registerStudentBtn = new JButton("Register Student");
        JButton assignCourseBtn = new JButton("Assign Course To Semester");
        JButton logoutBtn = new JButton("Logout");

        add(addBtn);
        add(deleteBtn);
        add(registerStudentBtn);
        add(assignCourseBtn);
        add(logoutBtn);

        addBtn.addActionListener(e -> addCourse());
        deleteBtn.addActionListener(e -> deleteCourse());

        registerStudentBtn.addActionListener(e -> {
            new AdminRegisterStudentUI();
            dispose();
        });

        // FIXED HERE
        assignCourseBtn.addActionListener(e -> {
            new AssignCourseUI(); // removed null parameter
            dispose();
        });

        logoutBtn.addActionListener(e -> {
            new LoginUI();
            dispose();
        });

        setVisible(true);
    }

    private void addCourse() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Course ID:"));
            String name = JOptionPane.showInputDialog("Course Name:");
            int credits = Integer.parseInt(JOptionPane.showInputDialog("Credits:"));
            int seats = Integer.parseInt(JOptionPane.showInputDialog("Total Seats:"));

            // 🔥 NEW PART
            String[] options = { "MANDATORY", "ELECTIVE" };
            String type = (String) JOptionPane.showInputDialog(
                    this,
                    "Select Course Type:",
                    "Course Type",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[0]);

            CourseService service = new CourseService();
            boolean success = service.addCourse(id, name, credits, seats, type);

            if (success)
                JOptionPane.showMessageDialog(this, "Course Added Successfully");
            else
                JOptionPane.showMessageDialog(this, "Error adding course");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Input");
        }
    }

    private void deleteCourse() {
        try {
            String idStr = JOptionPane.showInputDialog("Enter Course ID to delete:");
            if (idStr == null)
                return; // User cancelled
            int id = Integer.parseInt(idStr);

            CourseService service = new CourseService();
            boolean success = service.deleteCourse(id);

            if (success)
                JOptionPane.showMessageDialog(this, "Course ID " + id + " deleted successfully");
            else
                JOptionPane.showMessageDialog(this, "Course ID " + id + " not found in database");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input - please enter a valid course ID number");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}