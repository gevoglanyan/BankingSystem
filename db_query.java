import java.sql.*;

public class db_query {

    private static Connection conn;

    static {
        try {
            conn = DatabaseConnection.getConnection();
            System.out.println("Database connection initialized.");
        } catch (SQLException e) {
            System.err.println("Failed to initialize database connection: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static ResultSet customerQuery1(String email) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM customer WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email); 
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Email Query: " + e.getMessage());
        }
        return rs;
    }

    public static void printRs(ResultSet rs) throws SQLException {
        if (rs != null) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int CC = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= CC; i++) {
                    if (i > 1) System.out.print(", ");
                    String columnValue = rs.getString(i);
                    System.out.print(rsmd.getColumnName(i) + ": " + columnValue);
                }
                System.out.println();
            }
        } else {
            System.out.println("No results to display.");
        }
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection: " + e.getMessage());
        }
    }
}
