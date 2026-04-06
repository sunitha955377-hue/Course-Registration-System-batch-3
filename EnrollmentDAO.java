package dao;

import db.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {

    // ✅ ENROLL STUDENT
    public boolean enrollStudent(String rollNo, int courseId) {

        System.out.println("🔥 METHOD CALLED"); // 👈 ADD THIS

        try {
            Connection con = DatabaseUtil.getConnection();

            if (con == null) {
                System.out.println("❌ DB Connection failed");
                return false;
            }

            System.out.println("➡ Enrolling Student: " + rollNo + " | Course: " + courseId);

            // 🔒 1. Check already enrolled
            String checkSql = "SELECT * FROM enrollments WHERE student_roll_no=? AND course_id=?";
            PreparedStatement checkPs = con.prepareStatement(checkSql);
            checkPs.setString(1, rollNo.trim());
            checkPs.setInt(2, courseId);

            ResultSet checkRs = checkPs.executeQuery();

            if (checkRs.next()) {
                System.out.println("⚠ Already enrolled in course " + courseId);
                return true; // ✅ important
            }

            // 📦 2. Check seats
            String seatSql = "SELECT available_seats FROM courses WHERE id=?";
            PreparedStatement seatPs = con.prepareStatement(seatSql);
            seatPs.setInt(1, courseId);

            ResultSet seatRs = seatPs.executeQuery();

            if (!seatRs.next()) {
                System.out.println("❌ Course not found");
                return false;
            }

            int seats = seatRs.getInt("available_seats");

            if (seats <= 0) {
                System.out.println("❌ No seats available");
                return false;
            }
            System.out.println("🔥 INSERT QUERY RUNNING");

            // 💾 3. Insert enrollment
            // 💾 3. Insert enrollment
            String insertSql = "INSERT INTO enrollments (id, student_roll_no, course_id) VALUES (enroll_seq.NEXTVAL, ?, ?)";
            PreparedStatement insertPs = con.prepareStatement(insertSql);
            insertPs.setString(1, rollNo.trim());
            insertPs.setInt(2, courseId);

            int rows = insertPs.executeUpdate();
            System.out.println("ROWS INSERTED = " + rows);
            // 🔻 4. Reduce seats
            String updateSeatSql = "UPDATE courses SET available_seats = available_seats - 1 WHERE id=?";
            PreparedStatement updatePs = con.prepareStatement(updateSeatSql);
            updatePs.setInt(1, courseId);
            updatePs.executeUpdate();

            System.out.println("✅ Enrollment SUCCESS");

            con.close();

            return rows > 0;

        } catch (Exception e) {
            System.out.println("❌ Enrollment Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ✅ VIEW ENROLLMENTS
    public List<String[]> getStudentEnrollments(String rollNo) {

        List<String[]> list = new ArrayList<>();

        try {
            Connection con = DatabaseUtil.getConnection();

            System.out.println("➡ Fetching enrollments for RollNo: " + rollNo);

            String sql = "SELECT e.id, c.id, c.course_name, c.credits, NULL as enrollment_date " +
                    "FROM enrollments e " +
                    "JOIN courses c ON e.course_id = c.id " +
                    "WHERE e.student_roll_no=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, rollNo.trim());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                String[] row = new String[5];

                row[0] = String.valueOf(rs.getInt(1));
                row[1] = String.valueOf(rs.getInt(2));
                row[2] = rs.getString(3);
                row[3] = String.valueOf(rs.getInt(4));
                row[4] = rs.getString(5);

                list.add(row);
            }

            if (list.isEmpty()) {
                System.out.println("⚠ No enrollments found for " + rollNo);
            } else {
                System.out.println("✅ Enrollments fetched successfully");
            }

            con.close();

        } catch (Exception e) {
            System.out.println("❌ Error fetching enrollments: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }
}