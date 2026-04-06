package dao;

import db.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentDAO {

    // ✅ STUDENT LOGIN
    public boolean validateStudent(String username, String password) {

        try (Connection con = DatabaseUtil.getConnection()) {

            System.out.println("\n--- STUDENT LOGIN VALIDATION ---");
            System.out.println("Attempting login for: " + username);

            String sql = "SELECT * FROM students WHERE username=? AND password=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("✅ Login SUCCESS");
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Roll No: " + rs.getString("roll_no"));
                return true;
            } else {
                System.out.println("❌ Login FAILED");

                String checkSQL = "SELECT * FROM students WHERE username=?";
                PreparedStatement ps2 = con.prepareStatement(checkSQL);
                ps2.setString(1, username);

                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    System.out.println("⚠ Username exists but password wrong");
                } else {
                    System.out.println("⚠ Username not found in DB");
                }

                rs2.close();
                ps2.close();
                return false;
            }

        } catch (Exception e) {
            System.out.println("ERROR during login: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ✅ GET ROLL NO
    public String getStudentRollNo(String username) {

        try (Connection con = DatabaseUtil.getConnection()) {

            String sql = "SELECT roll_no FROM students WHERE username=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("roll_no");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // ✅ REGISTER STUDENT (FINAL FIX 🔥)
    public boolean registerStudent(String username,
            String password,
            String name,
            String rollNo,
            String branch,
            String phone) {

        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DatabaseUtil.getConnection();

            if (con == null) {
                System.out.println("❌ DB connection failed");
                return false;
            }

            System.out.println("\n--- STUDENT REGISTRATION ---");
            System.out.println("Username: " + username);
            System.out.println("Roll No: " + rollNo);

            // 🔥 MANUAL TRANSACTION
            con.setAutoCommit(false);

            // ✅ DUPLICATE CHECK
            String checkSQL = "SELECT * FROM students WHERE username=? OR roll_no=?";
            PreparedStatement checkPS = con.prepareStatement(checkSQL);
            checkPS.setString(1, username);
            checkPS.setString(2, rollNo);

            ResultSet checkRS = checkPS.executeQuery();

            if (checkRS.next()) {
                System.out.println("❌ Student already exists");
                checkRS.close();
                checkPS.close();
                con.close();
                return false;
            }

            checkRS.close();
            checkPS.close();

            // ✅ INSERT
            String sql = "INSERT INTO students (username, password, name, roll_no, branch, phone) VALUES (?, ?, ?, ?, ?, ?)";

            ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, name);
            ps.setString(4, rollNo);
            ps.setString(5, branch);
            ps.setString(6, phone);

            int rows = ps.executeUpdate();

            // 🔥 COMMIT (VERY IMPORTANT)
            con.commit();

            System.out.println("✅ Student saved permanently");

            return rows > 0;

        } catch (Exception e) {

            try {
                if (con != null) {
                    con.rollback(); // 🔥 rollback if error
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            System.out.println("❌ ERROR: " + e.getMessage());
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
}