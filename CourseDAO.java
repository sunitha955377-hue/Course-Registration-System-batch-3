package dao;

import db.DatabaseUtil;
import java.sql.*;

public class CourseDAO {

    // ✅ ADD COURSE (WITH TYPE)
    public boolean addCourse(int courseId, String name, int credits, int seats, String type) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DatabaseUtil.getConnection();

            if (con == null) {
                System.out.println("❌ DB connection failed");
                return false;
            }

            System.out.println("✅ Connected User: " + con.getMetaData().getUserName());

            // 🔥 Auto commit ON (simplify)
            con.setAutoCommit(true);

            String sql = "INSERT INTO courses(id, course_name, credits, total_seats, available_seats, course_type) VALUES (?, ?, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);
            ps.setString(2, name);
            ps.setInt(3, credits);
            ps.setInt(4, seats);
            ps.setInt(5, seats);
            ps.setString(6, type);

            int rows = ps.executeUpdate();

            System.out.println("✅ Course inserted successfully. Rows: " + rows);

            return rows > 0;

        } catch (SQLIntegrityConstraintViolationException e) {

            System.out.println("❌ Course ID already exists!");
            return false;

        } catch (Exception e) {

            System.out.println("❌ FULL ERROR:");
            e.printStackTrace();
            return false;

        } finally {

            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ✅ DELETE COURSE
    public boolean deleteCourse(int id) {

        try {
            Connection con = DatabaseUtil.getConnection();

            String sql = "DELETE FROM courses WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            con.close();
            return rows > 0;

        } catch (Exception e) {
            System.out.println("❌ Error deleting course:");
            e.printStackTrace();
            return false;
        }
    }

    // ✅ CHECK COURSE EXISTS
    public boolean courseExists(int courseId) {

        try {
            Connection con = DatabaseUtil.getConnection();

            String sql = "SELECT id FROM courses WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);

            ResultSet rs = ps.executeQuery();
            boolean exists = rs.next();

            con.close();
            return exists;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ✅ GET COURSES BY TYPE
    public ResultSet getCoursesByType(String type) {

        try {
            Connection con = DatabaseUtil.getConnection();

            String sql = "SELECT * FROM courses WHERE course_type=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, type);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ✅ GET ALL COURSES
    public ResultSet getAllCourses() {

        try {
            Connection con = DatabaseUtil.getConnection();

            String sql = "SELECT * FROM courses";
            PreparedStatement ps = con.prepareStatement(sql);

            return ps.executeQuery();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ✅ GET COURSE NAME
    public String getCourseName(int courseId) {

        try {
            Connection con = DatabaseUtil.getConnection();

            String sql = "SELECT course_name FROM courses WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String name = rs.getString("course_name");
                con.close();
                return name;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ CHECK SEATS
    public boolean hasAvailableSeats(int courseId) {

        try {
            Connection con = DatabaseUtil.getConnection();

            String sql = "SELECT available_seats FROM courses WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, courseId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int seats = rs.getInt("available_seats");
                con.close();
                return seats > 0;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}