import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseHelper — JDBC helper (Experiment 3: PreparedStatement with MySQL).
 * All SQL uses PreparedStatement to prevent injection.
 */
public class DatabaseHelper {

    private static final String URL      = "jdbc:mysql://localhost:3306/college_hub?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) {
            System.err.println("[DB] MySQL driver not found: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    // -----------------------------------------------------------------------
    // INSERT
    // -----------------------------------------------------------------------

    /**
     * Inserts a new student record using PreparedStatement.
     */
    public boolean insertStudent(Student s) {
        String sql = "INSERT INTO students (name, roll_number, branch, gender, gpa) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, s.getName());
            ps.setString(2, s.getRollNumber());
            ps.setString(3, s.getBranch());
            ps.setString(4, s.getGender());
            ps.setDouble(5, s.getGpa());

            return ps.executeUpdate() > 0;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("[DB] Duplicate roll number: " + s.getRollNumber());
            return false;
        } catch (SQLException e) {
            System.err.println("[DB] insertStudent error: " + e.getMessage());
            return false;
        }
    }

    // -----------------------------------------------------------------------
    // SEARCH
    // -----------------------------------------------------------------------

    /**
     * Finds a student by roll number. Returns null if not found.
     */
    public Student searchStudent(String rollNumber) {
        String sql = "SELECT * FROM students WHERE roll_number = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rollNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("[DB] searchStudent error: " + e.getMessage());
        }
        return null;
    }

    // -----------------------------------------------------------------------
    // LIST ALL
    // -----------------------------------------------------------------------

    /**
     * Returns all students ordered by name.
     */
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY name";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            System.err.println("[DB] getAllStudents error: " + e.getMessage());
        }
        return list;
    }

    // -----------------------------------------------------------------------
    // Utility
    // -----------------------------------------------------------------------

    private Student mapRow(ResultSet rs) throws SQLException {
        return new Student(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("roll_number"),
                rs.getString("branch"),
                rs.getString("gender"),
                rs.getDouble("gpa")
        );
    }
}
