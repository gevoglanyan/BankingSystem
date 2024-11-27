import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // jdbc:mysql://some-instance.rds.amazonaws.com/<database_name>

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found", e);
        }
    }


    private static final String URL = "jdbc:mysql://database-1.chiwu04ostol.us-west-2.rds.amazonaws.com/BankSystem";
    private static final String USER = "admin";
    private static final String PASSWORD = "comp440project";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}