package ui;

import dao.CourseDAO;
import service.EnrollmentService;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CourseEnrollUI extends JFrame {

    private String rollNo;
    private List<JCheckBox> electiveBoxes = new ArrayList<>();
    private List<Integer> electiveCourseIds = new ArrayList<>();
    private List<Integer> mandatoryCourseIds = new ArrayList<>();

    public CourseEnrollUI(String rollNo) {

        this.rollNo = rollNo;

        setTitle("Enroll Course");
        setSize(450, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        CourseDAO dao = new CourseDAO();

        // 🔼 Mandatory Courses
        mainPanel.add(new JLabel("Mandatory Courses:"));

        try {
            ResultSet rs = dao.getCoursesByType("MANDATORY");
            while (rs.next()) {
                mainPanel.add(new JLabel("• " + rs.getString("course_name")));
                mandatoryCourseIds.add(rs.getInt("id"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mainPanel.add(Box.createVerticalStrut(15));

        // 🔽 Elective Courses
        mainPanel.add(new JLabel("Select exactly 2 Elective Courses:"));

        try {
            ResultSet rs = dao.getCoursesByType("ELECTIVE");
            while (rs.next()) {
                JCheckBox cb = new JCheckBox(rs.getString("course_name"));
                electiveBoxes.add(cb);
                electiveCourseIds.add(rs.getInt("id"));
                mainPanel.add(cb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        JButton enrollBtn = new JButton("Enroll");

        enrollBtn.addActionListener(e -> enrollCourses());

        add(new JScrollPane(mainPanel), BorderLayout.CENTER);
        add(enrollBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void enrollCourses() {

        try {
            EnrollmentService service = new EnrollmentService();

            // 🔽 Validate electives count
            int count = 0;
            for (JCheckBox cb : electiveBoxes) {
                if (cb.isSelected())
                    count++;
            }

            if (count != 2) {
                JOptionPane.showMessageDialog(this, "Select exactly 2 elective courses");
                return;
            }

            boolean allSuccess = true;

            // 🔼 Enroll Mandatory Courses
            for (int id : mandatoryCourseIds) {
                System.out.println("➡ Mandatory Course ID: " + id);

                boolean success = service.enrollStudent(rollNo, id);
                if (!success) {
                    allSuccess = false;
                }
            }

            // 🔽 Enroll Selected Electives
            for (int i = 0; i < electiveBoxes.size(); i++) {
                if (electiveBoxes.get(i).isSelected()) {

                    int courseId = electiveCourseIds.get(i);
                    System.out.println("➡ Elective Course ID: " + courseId);

                    boolean success = service.enrollStudent(rollNo, courseId);
                    if (!success) {
                        allSuccess = false;
                    }
                }
            }

            // ✅ Final message
            if (allSuccess) {
                JOptionPane.showMessageDialog(this, "✅ Enrollment Successful!");
            } else {
                JOptionPane.showMessageDialog(this,
                        "⚠ Some courses not enrolled (Already enrolled / No seats)");
            }

            dispose();

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }
}