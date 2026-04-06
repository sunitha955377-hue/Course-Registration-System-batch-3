package service;

import dao.CourseDAO;
import dao.SemesterCourseDAO;

import java.sql.ResultSet;

public class CourseService {

    private CourseDAO courseDAO;
    private SemesterCourseDAO semesterDAO;

    public CourseService() {

        courseDAO = new CourseDAO();
        semesterDAO = new SemesterCourseDAO();
    }

    // ✅ ADD COURSE WITH TYPE (MANDATORY / ELECTIVE)
    public boolean addCourse(int courseId, String name, int credits, int seats, String type) {

        if (type == null || (!type.equalsIgnoreCase("MANDATORY") && !type.equalsIgnoreCase("ELECTIVE"))) {
            System.out.println("Invalid course type");
            return false;
        }

        return courseDAO.addCourse(courseId, name, credits, seats, type.toUpperCase());
    }

    // ✅ DELETE COURSE
    public boolean deleteCourse(int id) {
        return courseDAO.deleteCourse(id);
    }

    // ✅ ASSIGN COURSE TO SEMESTER
    public boolean assignCourseToSemester(int courseId, int semester) {
        return semesterDAO.assignCourseToSemester(courseId, semester);
    }

    // ✅ GET MANDATORY COURSES
    public ResultSet getMandatoryCourses() {
        return courseDAO.getCoursesByType("MANDATORY");
    }

    // ✅ GET ELECTIVE COURSES
    public ResultSet getElectiveCourses() {
        return courseDAO.getCoursesByType("ELECTIVE");
    }

    // ✅ CHECK COURSE EXISTS
    public boolean courseExists(int courseId) {
        return courseDAO.courseExists(courseId);
    }

    // ✅ CHECK SEATS
    public boolean hasAvailableSeats(int courseId) {
        return courseDAO.hasAvailableSeats(courseId);
    }
}