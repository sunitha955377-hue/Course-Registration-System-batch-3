package ui;

import service.StudentService;
import javax.swing.*;
import java.awt.*;

public class RegisterUI extends JFrame {

    private JTextField usernameField;
    private JTextField nameField;
    private JTextField rollField;
    private JTextField branchField;
    private JTextField phoneField;
    private JPasswordField passwordField;
    private JFrame parent;

    public RegisterUI(JFrame parent) {
        this.parent = parent;

        setTitle("Student Registration");
        setSize(400, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));

        formPanel.add(new JLabel("Username"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Name"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Roll No"));
        rollField = new JTextField();
        formPanel.add(rollField);

        formPanel.add(new JLabel("Branch"));
        branchField = new JTextField();
        formPanel.add(branchField);

        formPanel.add(new JLabel("Phone"));
        phoneField = new JTextField();
        formPanel.add(phoneField);

        formPanel.add(new JLabel("Password"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        JButton registerBtn = new JButton("Register");
        JButton backBtn = new JButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(registerBtn);
        buttonPanel.add(backBtn);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        registerBtn.addActionListener(e -> registerStudent());
        backBtn.addActionListener(e -> {
            if (parent != null)
                parent.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void registerStudent() {

        String username = usernameField.getText().trim();
        String name = nameField.getText().trim();
        String rollNo = rollField.getText().trim();
        String branch = branchField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || name.isEmpty() || rollNo.isEmpty()
                || branch.isEmpty() || phone.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        StudentService service = new StudentService();

        boolean success = service.registerStudent(
                username, password, name, rollNo, branch, phone);

        if (success) {
            JOptionPane.showMessageDialog(this, "Registration Successful");
            if (parent != null)
                parent.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Registration Failed");
        }
    }
}