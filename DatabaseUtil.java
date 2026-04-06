package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtil {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "system";
    private static final String PASSWORD = "Suni79818";
    private static boolean schemasInitialized = false;

    public static Connection getConnection() {

        Connection con = null;

        try {

            // Load Oracle Driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Create connection
            con = DriverManager.getConnection(URL, USER, PASSWORD);

            // Enable autocommit explicitly
            con.setAutoCommit(true);

            System.out.println("Database Connected Successfully");

            // Initialize schemas on first connection
            if (!schemasInitialized) {
                initializeSchemas(con);
                schemasInitialized = true;
            }

        } catch (ClassNotFoundException e) {

            System.out.println("Oracle JDBC Driver not found");
            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println("Database connection failed");
            e.printStackTrace();
        }

        return con;
    }

    private static void initializeSchemas(Connection con) {
        try {
            Statement stmt = con.createStatement();

            // Create STUDENTS table - only if it doesn't exist
            try {
                String createStudentsSQL = "CREATE TABLE students (" +
                        "username VARCHAR2(50) PRIMARY KEY, " +
                        "password VARCHAR2(50) NOT NULL, " +
                        "name VARCHAR2(100) NOT NULL, " +
                        "roll_no VARCHAR2(20) UNIQUE NOT NULL, " +
                        "branch VARCHAR2(50), " +
                        "phone VARCHAR2(15))";

                stmt.executeUpdate(createStudentsSQL);
                System.out.println("✓ Students table created successfully");
            } catch (SQLException e) {
                if (e.getMessage().contains("ORA-00955") || e.getMessage().contains("already exists")) {
                    System.out.println("✓ Students table already exists - data preserved");
                } else {
                    throw e;
                }
            }

            // Create COURSES table - only if it doesn't exist
            try {
                String createCoursesSQL = "CREATE TABLE courses (" +
                        "id INT PRIMARY KEY, " +
                        "course_name VARCHAR2(100) NOT NULL, " +
                        "credits INT NOT NULL, " +
                        "total_seats INT NOT NULL, " +
                        "available_seats INT NOT NULL)";

                stmt.executeUpdate(createCoursesSQL);
                System.out.println("✓ Courses table created successfully");
            } catch (SQLException e) {
                if (e.getMessage().contains("ORA-00955") || e.getMessage().contains("already exists")) {
                    System.out.println("✓ Courses table already exists - data preserved");
                } else {
                    throw e;
                }
            }

            // Create ENROLLMENTS table - only if it doesn't exist
            try {
                String createEnrollmentsSQL = "CREATE TABLE enrollments (" +
                        "id INT PRIMARY KEY, " +
                        "student_roll_no VARCHAR2(20) NOT NULL, " +
                        "course_id INT NOT NULL, " +
                        "enrollment_date DATE DEFAULT SYSDATE, " +
                        "UNIQUE(student_roll_no, course_id))";

                stmt.executeUpdate(createEnrollmentsSQL);
                System.out.println("✓ Enrollments table created successfully");
            } catch (SQLException e) {
                if (e.getMessage().contains("ORA-00955") || e.getMessage().contains("already exists")) {
                    System.out.println("✓ Enrollments table already exists - data preserved");
                } else {
                    throw e;
                }
            }

            // Create SEMESTER_COURSES table - only if it doesn't exist
            try {
                String createSemesterCoursesSQL = "CREATE TABLE semester_courses (" +
                        "id INT PRIMARY KEY, " +
                        "course_id INT NOT NULL, " +
                        "semester INT NOT NULL, " +
                        "UNIQUE(course_id, semester))";

                stmt.executeUpdate(createSemesterCoursesSQL);
                System.out.println("✓ Semester_courses table created successfully");
            } catch (SQLException e) {
                if (e.getMessage().contains("ORA-00955") || e.getMessage().contains("already exists")) {
                    System.out.println("✓ Semester_courses table already exists - data preserved");
                } else {
                    throw e;
                }
            }

            // Create sequence for enrollments id - only if doesn't exist
            try {
                stmt.executeUpdate("CREATE SEQUENCE enrollments_seq START WITH 1 INCREMENT BY 1");
                System.out.println("✓ Sequence enrollments_seq created");
            } catch (SQLException e) {
                if (e.getMessage().contains("ORA-00955") || e.getMessage().contains("already exists")) {
                    System.out.println("✓ Sequence enrollments_seq already exists");
                } else {
                    System.out.println("⚠ Sequence enrollments_seq: " + e.getMessage());
                }
            }

            // Create sequence for semester_courses id - only if doesn't exist
            try {
                stmt.executeUpdate("CREATE SEQUENCE semester_courses_seq START WITH 1 INCREMENT BY 1");
                System.out.println("✓ Sequence semester_courses_seq created");
            } catch (SQLException e) {
                if (e.getMessage().contains("ORA-00955") || e.getMessage().contains("already exists")) {
                    System.out.println("✓ Sequence semester_courses_seq already exists");
                } else {
                    System.out.println("⚠ Sequence semester_courses_seq: " + e.getMessage());
                }
            }

            stmt.close();
            // Data already committed - autocommit=true is enabled
            System.out.println("\n════════════════════════════════════════");
            System.out.println("✓ DATABASE READY - All schemas initialized");
            System.out.println("════════════════════════════════════════\n");

        } catch (SQLException e) {
            System.out.println("ERROR initializing schemas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}