import java.sql.*;
public class DataInserter {
    public static void main(String[] args) throws SQLException {

        Connection conn = DatabaseConnection.getConnection();
        System.out.println("Connected to Database");

        /* 

        for (int i = 0; i < 110; i++) {
            ResultSet rs = DatabaseQuery.getCustomerByID(i);

            if (rs.next()) {
                int createAccountRs = DatabaseQuery.createAccount((int)(Math.random()*4) + 1, rs.getInt("customerID"));
              System.out.println("Created account " + createAccountRs);
            }
        }

        */
    }
}