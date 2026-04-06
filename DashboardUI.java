package ui;

import javax.swing.*;
import java.awt.*;

public class DashboardUI extends JFrame {

    private String username;
    private String rollNo;

    public DashboardUI(String username, String rollNo) {

        this.username = username;
        this.rollNo = rollNo;

        setTitle("Student Dashboard");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel welcome = new JLabel("Welcome " + this.username + " (Roll No: " + this.rollNo + ")");
        welcome.setHorizontalAlignment(SwingConstants.CENTER);

        JButton enrollBtn = new JButton("Enroll Course");
        JButton viewBtn = new JButton("View Enrollments");

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));

        panel.add(welcome);
        panel.add(enrollBtn);
        panel.add(viewBtn);

        add(panel);

        enrollBtn.addActionListener(e -> {
            new CourseEnrollUI(rollNo);
        });

        viewBtn.addActionListener(e -> {
            new EnrollmentViewUI(rollNo);
        });

        setVisible(true);
    }
}