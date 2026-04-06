package ui;

import dao.EnrollmentDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class EnrollmentViewUI extends JFrame {

    public EnrollmentViewUI(String rollNo) {

        setTitle("View Enrollments");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header label
        JLabel header = new JLabel("Viewing enrollments for Roll No: " + rollNo);
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        header.setFont(new Font("Arial", Font.BOLD, 14));
        add(header, BorderLayout.NORTH);

        // Table setup
        String[] columnNames = { "Enrollment ID", "Course ID", "Course Name", "Credits", "Enrollment Date" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Fetch enrollment data
        EnrollmentDAO dao = new EnrollmentDAO();
        List<String[]> enrollments = dao.getStudentEnrollments(rollNo);

        if (enrollments.isEmpty()) {
            model.addRow(new Object[] { "", "", "No enrollments found", "", "" });
        } else {
            for (String[] enrollment : enrollments) {
                model.addRow(new Object[] {
                        enrollment[0], enrollment[1], enrollment[2], enrollment[3], enrollment[4]
                });
            }
        }

        JTable table = new JTable(model);
        table.setEnabled(false);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Back button
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            new DashboardUI(rollNo, rollNo);
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(backBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }
}