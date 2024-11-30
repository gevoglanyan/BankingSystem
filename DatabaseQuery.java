import javax.xml.transform.Result;
import java.sql.*;

@SuppressWarnings("unused")
public class DatabaseQuery{

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

    // BUISNESS LOGIC

    // CUSTOMER

    public static ResultSet getCustomerByEmail(String email) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM customer WHERE email = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email); 
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Customer by Email Query: " + e.getMessage());
        }
        return rs;
    }

    public static ResultSet getCustomerByAddress(String address) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM customer WHERE address LIKE ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, address);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Customer by Address Query: " + e.getMessage());
        }
        return rs;
    }

    // ACCOUNT

    public static ResultSet getAccountByCustomerID(int customerID) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM account WHERE customerID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Account Query: " + e.getMessage());
        }
        return rs;
    }

    public static ResultSet getAccountByBalance(int balance) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM account WHERE balance > ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, balance);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Account Query: " + e.getMessage());
        }
        return rs;
    }

    // TRANSACTIONS

    public static ResultSet transactionsRelatedToAccount(int accountNumber) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM transaction WHERE senderNum = ? or receiverNum = ?";
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

    // LOANS

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

    // BRANCH

    public static ResultSet getEmployeeFromBranch(int branchID) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM employee WHERE branchID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, branchID);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Get Employee With BranchID Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }
    public static ResultSet getBranchFromLocation(String location) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM branch WHERE location = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, location);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Get Branch With Location Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    //EMPLOYEE

    public static ResultSet getEmployeeFromRole(String role) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM employee WHERE role = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, role);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Get Branch With Location Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    public static ResultSet getEmployeeHiredOn(String dateHired) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM employee WHERE dateHired = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dateHired);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Employee Hired on Date Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    // ATM

    public static ResultSet getATMFromBranch(int branchID) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM atm WHERE branchID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, branchID);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Get ATM With BranchID Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }
    
    public static ResultSet getATMLessThanMaxCash() throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM atm WHERE currentCash< maxCash;";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Get ATM Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    // CARD

    public static ResultSet getCardFromAccountID(int accountID) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM card WHERE accountID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, accountID);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Get Card With accountID Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    public static ResultSet getExpiredCard(String expDate, int accountID) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM card WHERE Date(expDate) = Date(?) AND accountID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, expDate);
            pstmt.setInt(2, accountID);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Get Expired Card With accountID & expdate Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }
 
    // UTILITY FUNCTIONS

    public static int createCustomerQuery(String firstName, String lastName, String address, String email, String phoneNumber) throws SQLException {
        int resultCode = -1;
        boolean exists = checkIfEmailExists(email);
        if (exists) {
            System.err.println("Customer already exists with email: " + email);
            return -1;
        }
        String sql = String.format("INSERT INTO customer (firstName, lastName, address, email, phoneNumber) VALUES ('%s', '%s', '%s', '%s', '%s')",firstName, lastName, address, email, phoneNumber);
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

    public static int createTransaction(int senderNum, int recieverNum, String transactionDate, int amount, String transactionType) throws SQLException {
        int rs = -1;
        String sql = String.format("INSERT INTO transaction (senderNum, receiverNum, transactionDate, amount, transactionType) VALUES (%o, %o, '%s', %o, '%s')", senderNum, recieverNum, transactionDate, amount, transactionType);
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            rs = 1;
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with creating transaction query: " + e.getMessage());
        }
        return rs;
    }

    //UPDATE

    public static int updateCustomer(String firstName, String lastName, String address, String email, String phoneNumber, int customerID) throws SQLException {
        int rs = -1;
        String sql = String.format("UPDATE customer SET firstName = '%s', lastName = '%s', address = '%s', email = '%s', phoneNumber = '%s' Where customerID = %o", firstName, lastName, address, email, phoneNumber, customerID);
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            rs = 1;
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Updating customer query: " + e.getMessage());
            return -1;
        }
        return rs;
    }

    public static int updateLoanStatus(int loanStatus, int loanID, int customerID) throws SQLException {
        int rs = -1;
        String sql = String.format("UPDATE loan SET loanStatus = '%o' WHERE loanID = %o AND customerID = %o", loanStatus, loanID, customerID );
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            rs = 1;
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with Updating Loan query: " + e.getMessage());
            return -1;
        }
        return rs;
    }

    /* 
    public static int createAccountForCustomer(String firstName, String lastName){
        int resultCode = -1;
        String sql = String.format("INSERT INTO customer (firstName, lastName, Address, email, phoneNumber) VALUES ('%s', '%s', '%s', '%s', '%s')",firstName, lastName, address, email, phoneNumber);
        System.out.println(sql);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            resultCode = pstmt.executeUpdate();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error with creating customer query: " + e.getMessage());
       }
       return resultCode;
    }
    */

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

    public static ResultSet executeCustomQuery(String query) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
    
        try {
            stmt = conn.createStatement();
    
            rs = stmt.executeQuery(query);
            System.out.println("Custom query executed successfully.");
        } catch (SQLException e) {
            System.err.println("Error executing custom query: " + e.getMessage());
            throw e;
        }
    
        return rs;
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