package service;

import dao.StudentDAO;
import dao.CourseDAO;

public class AdminService {

    private StudentDAO studentDAO;
    private CourseDAO courseDAO;

    public AdminService() {
        studentDAO = new StudentDAO();
        courseDAO = new CourseDAO();
    }

    // ✅ ADMIN LOGIN
    public boolean login(String username, String password) {

        if (username.equals("admin") && password.equals("admin123")) {
            return true;
        }

        return false;
    }

    // ✅ REGISTER STUDENT
    public boolean registerStudent(String username,
            String password,
            String name,
            String roll,
            String branch,
            String phone) {

        System.out.println("Registering Student:");
        System.out.println(username + " " + name + " " + roll);

        return studentDAO.registerStudent(username, password, name, roll, branch, phone);
    }

    // ✅ ADD COURSE (UPDATED WITH TYPE)
    public boolean addCourse(int courseId,
            String courseName,
            int credits,
            int seats,
            String type) {

        System.out.println("Adding Course:");
        System.out.println(courseId + " " + courseName + " Type: " + type);

        // 🔥 validation
        if (type == null || (!type.equalsIgnoreCase("MANDATORY") && !type.equalsIgnoreCase("ELECTIVE"))) {
            System.out.println("Invalid course type");
            return false;
        }

        return courseDAO.addCourse(courseId, courseName, credits, seats, type.toUpperCase());
    }

    // ✅ DELETE COURSE
    public boolean deleteCourse(int courseId) {

        System.out.println("Deleting Course ID: " + courseId);
        return courseDAO.deleteCourse(courseId);
    }
}