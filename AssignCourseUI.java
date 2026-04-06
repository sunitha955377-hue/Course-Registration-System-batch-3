package ui;

import service.CourseService;
import javax.swing.*;
import java.awt.*;

public class AssignCourseUI extends JFrame {

    private JTextField courseIdField;
    private JTextField semesterField;

    public AssignCourseUI() {

        setTitle("Assign Course To Semester");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Course ID:"));
        courseIdField = new JTextField();
        panel.add(courseIdField);

        panel.add(new JLabel("Semester:"));
        semesterField = new JTextField();
        panel.add(semesterField);

        JButton assignBtn = new JButton("Assign");

        assignBtn.addActionListener(e -> {

            try {
                int courseId = Integer.parseInt(courseIdField.getText().trim());
                int semester = Integer.parseInt(semesterField.getText().trim());

                CourseService service = new CourseService();
                boolean success = service.assignCourseToSemester(courseId, semester);

                if (success)
                    JOptionPane.showMessageDialog(this, "Assigned Successfully");
                else
                    JOptionPane.showMessageDialog(this, "Course Not Found");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid Input");
            }
        });

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            new AdminDashboardUI();
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(assignBtn);
        buttonPanel.add(backBtn);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}