import javax.xml.transform.Result;
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


    //
    // DEFINED BUISNESS LOGIC
    //


    public static ResultSet customerQuery1(String email) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM customer WHERE email = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email); 
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Email Query: " + e.getMessage());
        }
        return rs;
    }

    public static ResultSet transactionsRelatedToAccount(int accountNumber) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM transaction WHERE senderNum = ? or recieverNum = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, accountNumber);
            pstmt.setInt(2, accountNumber);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Transaction Related To Query: " + e.getMessage());
            System.err.println(e.getErrorCode());

        }
        return rs;
    }

    public static ResultSet transactionsBetweenDates(Timestamp from, Timestamp to) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM transaction WHERE transactionDate >= ? and transactionDate <= ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, from);
            pstmt.setTimestamp(2, to);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Transaction Between Dates Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }


    public static ResultSet getLoansFromCustomer(int customerID) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM loan WHERE customerID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Get Loans From Customer Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }


    //
    // UTILITY FUNCTIONS
    //


    public static int createCustomerQuery(String firstName, String lastName, String address, String email, String phoneNumber) throws SQLException {
        int resultCode = -1;
        boolean exists = checkIfEmailExists(email);
        if (exists) {
            System.err.println("Customer already exists with email: " + email);
            return -1;
        }
        String sql = String.format("INSERT INTO customer (firstName, lastName, Address, email, phoneNumber) VALUES ('%s', '%s', '%s', '%s', '%s')",firstName, lastName, address, email, phoneNumber);
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            resultCode = pstmt.executeUpdate();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with creating customer query: " + e.getMessage());
        }
        return resultCode;
    }

    public static boolean checkIfEmailExists(String email) throws SQLException {
        ResultSet rs;
        boolean exists = false;
        String sql = "SELECT count(*) FROM customer WHERE email = ?";
        try{
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            rs.next();
            exists = rs.getInt(1) > 0;
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with creating customer query: " + e.getMessage());
        }
        return exists;
    }


//public static int createAccountForCustomer(String firstName, String lastName){
//        int resultCode = -1;
//        String sql = String.format("INSERT INTO customer (firstName, lastName, Address, email, phoneNumber) VALUES ('%s', '%s', '%s', '%s', '%s')",firstName, lastName, address, email, phoneNumber);
//        System.out.println(sql);
//        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            resultCode = pstmt.executeUpdate();
//            System.out.println("Query Successful");
//        } catch (SQLException e) {
//            System.err.println("Error with creating customer query: " + e.getMessage());
//        }
//        return resultCode;
//    }

    public static ResultSet getCustomerByAddress(String address) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        ResultSet rs = null;
        Statement stmt = conn.createStatement();
        String sql = "select * from customer where email like '" + address + "'";
        try{
            rs = stmt.executeQuery(sql);
            System.out.println("Query Successful");
        }catch (Exception e) {
            System.out.println("Error with Address: " + e.getMessage());
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
