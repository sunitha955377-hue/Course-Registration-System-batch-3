package ui;

import service.StudentService;
import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginUI() {

        setTitle("Student Course Registration System");
        setSize(500, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(33, 150, 243));
        headerPanel.setPreferredSize(new Dimension(500, 70));

        JLabel title = new JLabel("Course Registration System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(title);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);

        JButton loginBtn = new JButton("Login");
        JButton adminBtn = new JButton("Admin Login");

        loginBtn.addActionListener(e -> {

            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter username and password");
                return;
            }

            StudentService service = new StudentService();

            if (service.validateStudent(username, password)) {

                String rollNo = service.getStudentRollNo(username);

                dispose();
                new DashboardUI(username, rollNo);

            } else {
                JOptionPane.showMessageDialog(this,
                        "Invalid username or password");
            }
        });

        adminBtn.addActionListener(e -> {
            new AdminLoginUI();
            dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        centerPanel.add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        centerPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        centerPanel.add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        centerPanel.add(passwordField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        centerPanel.add(loginBtn, gbc);

        gbc.gridy = 3;
        centerPanel.add(adminBtn, gbc);

        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}