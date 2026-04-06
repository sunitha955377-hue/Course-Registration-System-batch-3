package dao;

import db.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SemesterCourseDAO {

    public boolean assignCourseToSemester(int courseId, int semester) {

        Connection con = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        try {
            con = DatabaseUtil.getConnection();
            if (con == null) {
                System.out.println("ERROR: Database connection failed for assigning course");
                return false;
            }

            // Check if course exists using id column
            String check = "SELECT id FROM courses WHERE id=?";
            ps1 = con.prepareStatement(check);
            ps1.setInt(1, courseId);

            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) {
                System.out.println("ERROR: Course ID " + courseId + " not found in database");
                rs.close();
                ps1.close();
                con.close();
                return false;
            }

            System.out.println("Course ID " + courseId + " found. Assigning to semester " + semester);

            // Check if already assigned to this semester
            String checkAssignSQL = "SELECT id FROM semester_courses WHERE course_id=? AND semester=?";
            PreparedStatement ps0 = con.prepareStatement(checkAssignSQL);
            ps0.setInt(1, courseId);
            ps0.setInt(2, semester);
            ResultSet checkRS = ps0.executeQuery();
            if (checkRS.next()) {
                System.out.println("ERROR: Course ID " + courseId + " already assigned to semester " + semester);
                checkRS.close();
                ps0.close();
                rs.close();
                ps1.close();
                con.close();
                return false;
            }
            checkRS.close();
            ps0.close();

            // Assign course to semester
            String sql = "INSERT INTO semester_courses(id, course_id, semester) VALUES (semester_courses_seq.NEXTVAL, ?, ?)";

            ps2 = con.prepareStatement(sql);
            ps2.setInt(1, courseId);
            ps2.setInt(2, semester);

            int rows = ps2.executeUpdate();
            System.out.println("Course assignment completed. Rows affected: " + rows);

            rs.close();
            ps1.close();
            ps2.close();
            con.close();
            System.out.println("Data automatically committed (autocommit enabled)");
            return rows > 0;

        } catch (Exception e) {
            System.out.println("ERROR assigning course to semester: " + e.getMessage());
            e.printStackTrace();
            try {
                if (ps2 != null) ps2.close();
                if (ps1 != null) ps1.close();
                if (con != null)
                    con.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}