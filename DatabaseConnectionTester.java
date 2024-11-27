
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnectionTester {

    public static void main(String[] args) throws SQLException {
        try{
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connected to database");

            ResultSet rs = db_query.customerQuery1("professor1@gmail.com");
            db_query.printRs(rs);

        } catch (Exception e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

}
