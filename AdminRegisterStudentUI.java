package ui;

import service.AdminService;
import javax.swing.*;
import java.awt.*;

public class AdminRegisterStudentUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JTextField rollField;
    private JTextField branchField;
    private JTextField phoneField;

    public AdminRegisterStudentUI() {

        setTitle("Admin - Register Student");
        setSize(400, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        nameField = new JTextField();
        rollField = new JTextField();
        branchField = new JTextField();
        phoneField = new JTextField();

        // Form Fields
        formPanel.add(new JLabel("Username"));
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password"));
        formPanel.add(passwordField);

        formPanel.add(new JLabel("Name"));
        formPanel.add(nameField);

        formPanel.add(new JLabel("Roll Number"));
        formPanel.add(rollField);

        formPanel.add(new JLabel("Branch"));
        formPanel.add(branchField);

        formPanel.add(new JLabel("Phone"));
        formPanel.add(phoneField);

        JButton registerBtn = new JButton("Register Student");
        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> {
            new AdminDashboardUI();
            dispose();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Register button action
        registerBtn.addActionListener(e -> registerStudent());

        setVisible(true);
    }

    private void registerStudent() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String name = nameField.getText().trim();
        String rollNo = rollField.getText().trim();
        String branch = branchField.getText().trim();
        String phone = phoneField.getText().trim();

        // Validation
        if (username.isEmpty() || password.isEmpty() || name.isEmpty() || rollNo.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all required fields",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        AdminService service = new AdminService();

        boolean success = service.registerStudent(
                username, password, name, rollNo, branch, phone);

        if (success) {

            JOptionPane.showMessageDialog(this,
                    "Student Registered Successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);

            new AdminDashboardUI().setVisible(true);
            dispose();

        } else {

            JOptionPane.showMessageDialog(this,
                    "Registration Failed",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}