package service;

import dao.EnrollmentDAO;

public class EnrollmentService {

    EnrollmentDAO dao = new EnrollmentDAO();

    public boolean enrollStudent(String rollNo, int courseId) {
        return dao.enrollStudent(rollNo, courseId);
    }
}