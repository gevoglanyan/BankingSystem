import java.sql.*;

@SuppressWarnings("unused")
public class DataInserter {
    public static void main(String[] args) {
        
        /*

        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("Connected to Database");

            for (int i = 1; i <= 5; i++) {
                String firstName = "TestFirst" + i;
                String lastName = "TestLast" + i;
                String address = "TestAddress" + i;
                String email = "test" + i + "@example.com";
                String phoneNumber = "123-456-78" + i;

                int result = DatabaseQuery.createCustomerQuery(firstName, lastName, address, email, phoneNumber);
                if (result != -1) {
                    System.out.println("Inserted customer: " + email);
                } else {
                    System.out.println("Failed to insert customer: " + email);
                }
            }

            for (int customerID = 1; customerID <= 5; customerID++) {
                int branchID = (int) (Math.random() * 4) + 1; 
                int result = DatabaseQuery.createTransaction(branchID, customerID, "2024-11-30", 1000, "credit");
                if (result != -1) {
                    System.out.println("Created account for customerID: " + customerID);
                } else {
                    System.out.println("Failed to create account for customerID: " + customerID);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection(DatabaseQuery.getConnection());
        }

        */
    }
}