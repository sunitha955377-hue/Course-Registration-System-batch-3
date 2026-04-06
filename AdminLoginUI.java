package ui;

import service.AdminService;
import javax.swing.*;
import java.awt.*;

public class AdminLoginUI extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLoginUI() {

        setTitle("Admin Login");
        setSize(350, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));

        panel.add(new JLabel("Username"));
        usernameField = new JTextField();
        panel.add(usernameField);

        panel.add(new JLabel("Password"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        JButton loginBtn = new JButton("Login");
        JButton backBtn = new JButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(loginBtn);
        buttonPanel.add(backBtn);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> {
            new LoginUI();
            dispose();
        });

        loginBtn.addActionListener(e -> {

            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            AdminService service = new AdminService();

            if (service.login(username, password)) {

                JOptionPane.showMessageDialog(null, "Login Successful");
                new AdminDashboardUI().setVisible(true);
                dispose();

            } else {

                JOptionPane.showMessageDialog(null, "Invalid Credentials");
            }
        });

        setVisible(true);
    }
}