package service;

import dao.StudentDAO;

public class StudentService {

    private StudentDAO dao;

    public StudentService() {
        dao = new StudentDAO();
    }

    // Validate student login
    public boolean validateStudent(String username, String password) {
        return dao.validateStudent(username, password);
    }

    // Get roll number
    public String getStudentRollNo(String username) {
        return dao.getStudentRollNo(username);
    }

    // Register new student
    public boolean registerStudent(String username,
            String password,
            String name,
            String rollNo,
            String branch,
            String phone) {

        return dao.registerStudent(username, password, name, rollNo, branch, phone);
    }
}