
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionTester {

    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();


    }
}
