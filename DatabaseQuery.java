import java.sql.*;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("unused")
public class DatabaseQuery{

    private static Connection conn;

    static {
        try {
            conn = DatabaseConnection.getConnection();
            System.out.println("Database Connection Initialized.");
        } catch (SQLException e) {
            System.err.println("Failed to Initialize Database Connection: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return conn;
    }

    public static ResultSet getCustomerByEmail(String email) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM customer WHERE email = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email); 
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error With Customer by Email Query: " + e.getMessage());
        }
        return rs;
    }

    public static DefaultTableModel getCustomerDetails(String email) throws SQLException {
        String sql = "SELECT c.customerID, c.firstName, c.lastName, c.address, c.email, c.phoneNumber, " +
                     "a.accountID " +
                     "FROM customer c " +
                     "LEFT JOIN account a ON c.customerID = a.customerID " +
                     "WHERE c.email = ?";
        DefaultTableModel tableModel = new DefaultTableModel();
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
    
                for (int i = 1; i <= columnCount; i++) {
                    tableModel.addColumn(metaData.getColumnName(i));
                }
    
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error Fetching Customer Details: " + e.getMessage());
            throw e;
        }
        return tableModel;
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
            System.err.println("Error With Customer by Address Query: " + e.getMessage());
        }
        return rs;
    }

    public static ResultSet getAccountByCustomerID(int customerID) throws SQLException {
        ResultSet rs = null;
        String sql = "SELECT * FROM account WHERE customerID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, customerID);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error With Account Query: " + e.getMessage());
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
            System.err.println("Error With Account Query: " + e.getMessage());
        }
        return rs;
    }

    public static DefaultTableModel transactionsRelatedToAccount(int accountNumber) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE senderNum = ? OR receiverNum = ?";
        DefaultTableModel tableModel = new DefaultTableModel();
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountNumber);
            pstmt.setInt(2, accountNumber);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
    
                for (int i = 1; i <= columnCount; i++) {
                    tableModel.addColumn(metaData.getColumnName(i));
                }
    
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error With Transaction Related To Account Query: " + e.getMessage());
            throw e;
        }
    
        return tableModel;
    }
    
    public static DefaultTableModel transactionsBetweenDates(Timestamp from, Timestamp to) throws SQLException {
        String sql = "SELECT * FROM transaction WHERE transactionDate >= ? AND transactionDate <= ?";
        DefaultTableModel tableModel = new DefaultTableModel();
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, from);
            pstmt.setTimestamp(2, to);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
    
                for (int i = 1; i <= columnCount; i++) {
                    tableModel.addColumn(metaData.getColumnName(i));
                }
    
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error With Transaction Between Dates Query: " + e.getMessage());
            throw e;
        }
    
        return tableModel;
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
            System.err.println("Error With Get Loans From Customer Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    /*

    public static boolean approveLoan(int customerID, int loanAmount) throws SQLException {
        String sqlCheck = "SELECT creditScore, accountHistory FROM customer WHERE customerID = ?";
        String sqlInsert = "INSERT INTO loan (customerID, amount, status) VALUES (?, ?, 'Approved')";
        
        try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {
            pstmtCheck.setInt(1, customerID);
            ResultSet rs = pstmtCheck.executeQuery();
            
            if (rs.next()) {
                int creditScore = rs.getInt("creditScore");
                int accountHistory = rs.getInt("accountHistory");

                int creditScoreThreshold = 700;
                int accountHistoryThreshold = 2;

                if (creditScore > creditScoreThreshold && accountHistory > accountHistoryThreshold) {
                    try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
                        pstmtInsert.setInt(1, customerID);
                        pstmtInsert.setInt(2, loanAmount);
                        pstmtInsert.executeUpdate();
                        return true;
                    }
                }
            }
            return false;
        }
    }

    */

    public static void makeLoanPayment(int loanID, int paymentAmount) throws SQLException {
        String sqlUpdate = "UPDATE loan SET balance = balance - ?, paymentHistory = CONCAT(paymentHistory, ?) WHERE loanID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setInt(1, paymentAmount);
            pstmt.setString(2, paymentAmount + " paid on " + LocalDate.now().toString() + "; ");
            pstmt.setInt(3, loanID);
            pstmt.executeUpdate();
        }
    }

    public static int cancelStudentLoans() throws SQLException {
        int resultCode = -1;
        String sqlUpdate = "DELETE FROM loan WHERE loanType = 'student' and loanAmount > 100000";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            resultCode = pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error With Cancel Student Loans Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }

        return resultCode;
    }

    public static void calculateInterest(double interestRate) throws SQLException {
        String sqlUpdate = "UPDATE account SET balance = balance + (balance * ?) WHERE accountType = 'Savings'";
        try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
            pstmtUpdate.setDouble(1, interestRate);
            pstmtUpdate.executeUpdate();
        }
    }

    public static ResultSet getEmployeeFromBranch(int branchID) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM employee WHERE branchID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, branchID);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error With Get Employee With BranchID Query: " + e.getMessage());
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
            System.err.println("Error With Get Branch With Location Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    public static void transferEmployee(int employeeID, int newBranchID) throws SQLException {
        String sqlUpdate = "UPDATE employee SET branchID = ? WHERE employeeID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setInt(1, newBranchID);
            pstmt.setInt(2, employeeID);
            pstmt.executeUpdate();
        }
    }

    public static ResultSet getEmployeeFromRole(String role) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM employee WHERE role = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, role);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error With Get Branch With Location Query: " + e.getMessage());
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
            System.err.println("Error With Employee Hired on Date Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    public static ResultSet getATMFromBranch(int branchID) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM atm WHERE branchID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, branchID);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error With Get ATM With BranchID Query: " + e.getMessage());
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
            System.err.println("Error With Get ATM Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    public static ResultSet getCardFromAccountID(int accountID) throws SQLException{
        ResultSet rs = null;
        String sql = "SELECT * FROM card WHERE accountID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, accountID);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error With Get Card With accountID Query: " + e.getMessage());
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
            System.err.println("Error With Expired Card With accountID & expdate Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    public static boolean validateCard(int cardID) throws SQLException {
        String sql = "SELECT expDate FROM card WHERE cardID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cardID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Date expiry = rs.getDate("expDate");
                return expiry.after(new Date());
            }
            return false;
        }
    }
 
    public static int createCustomerQuery(String firstName, String lastName, String address, String email, String phoneNumber) throws SQLException {
        int resultCode = -1;
        boolean exists = checkIfEmailExists(email);
        if (exists) {
            System.err.println("Customer Already Exists with Email: " + email);
            return -1;
        }
        String sql = String.format("INSERT INTO customer (firstName, lastName, address, email, phoneNumber) VALUES ('%s', '%s', '%s', '%s', '%s')",firstName, lastName, address, email, phoneNumber);
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            resultCode = pstmt.executeUpdate();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error With Creating Customer Query: " + e.getMessage());
        }
        return resultCode;
    }

    private static boolean hasSufficientBalance(int accountID, int amount) throws SQLException {
        String sql = "SELECT balance FROM account WHERE accountID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("balance") >= amount;
            }
            return false;
        }
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
            System.err.println("Error With Creating Customer Query: " + e.getMessage());
        }
        return exists;
    }

    public static DefaultTableModel createTransactionAndGetDetails(int senderNum, int receiverNum, int amount) throws SQLException {
        String deductBalanceQuery = "UPDATE account SET balance = balance - ? WHERE accountID = ?";
        String addBalanceQuery = "UPDATE account SET balance = balance + ? WHERE accountID = ?";
        String fetchAccountQuery = "SELECT accountID, balance FROM account WHERE accountID = ?";
        DefaultTableModel tableModel = new DefaultTableModel();
    
        try (PreparedStatement deductBalanceStmt = conn.prepareStatement(deductBalanceQuery);
             PreparedStatement addBalanceStmt = conn.prepareStatement(addBalanceQuery);
             PreparedStatement fetchAccountStmt = conn.prepareStatement(fetchAccountQuery)) {

            deductBalanceStmt.setInt(1, amount);
            deductBalanceStmt.setInt(2, senderNum);
            int senderUpdated = deductBalanceStmt.executeUpdate();
    
            if (senderUpdated == 0) {
                throw new SQLException("Failed to update sender's balance. Sender account may not exist.");
            }
    
            addBalanceStmt.setInt(1, amount);
            addBalanceStmt.setInt(2, receiverNum);
            int receiverUpdated = addBalanceStmt.executeUpdate();
    
            if (receiverUpdated == 0) {
                throw new SQLException("Failed to update receiver's balance. Receiver account may not exist.");
            }
    
            fetchAccountStmt.setInt(1, senderNum);
            try (ResultSet senderRs = fetchAccountStmt.executeQuery()) {
                if (tableModel.getColumnCount() == 0) {
                    ResultSetMetaData metaData = senderRs.getMetaData();
                    int columnCount = metaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        tableModel.addColumn(metaData.getColumnName(i));
                    }
                }
                while (senderRs.next()) {
                    Object[] row = new Object[tableModel.getColumnCount()];
                    for (int i = 0; i < tableModel.getColumnCount(); i++) {
                        row[i] = senderRs.getObject(i + 1);
                    }
                    tableModel.addRow(row);
                }
            }
    
            fetchAccountStmt.setInt(1, receiverNum);
            try (ResultSet receiverRs = fetchAccountStmt.executeQuery()) {
                while (receiverRs.next()) {
                    Object[] row = new Object[tableModel.getColumnCount()];
                    for (int i = 0; i < tableModel.getColumnCount(); i++) {
                        row[i] = receiverRs.getObject(i + 1);
                    }
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error Creating Transaction and Fetching Details: " + e.getMessage());
            throw e;
        }
    
        return tableModel;
    }
    
    public static void applyMinimumBalanceFee(int minimumBalance, int fee) throws SQLException {
        String sqlUpdate = "UPDATE account SET balance = balance - ? WHERE balance < ? AND accountType = 'Savings'";
        try (PreparedStatement pstmt = conn.prepareStatement(sqlUpdate)) {
            pstmt.setInt(1, fee);
            pstmt.setInt(2, minimumBalance);
            pstmt.executeUpdate();
        }
    }

    public static DefaultTableModel calculateSalaryIncrement(int employeeID, double incrementFactor) throws SQLException {
        String sqlUpdate = "UPDATE employee SET salary = salary * ? WHERE employeeID = ?";
        String sqlFetch = "SELECT employeeID, firstName, lastName, salary FROM employee WHERE employeeID = ?";
        DefaultTableModel tableModel = new DefaultTableModel();
    
        try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate);
             PreparedStatement pstmtFetch = conn.prepareStatement(sqlFetch)) {
    
            pstmtUpdate.setDouble(1, incrementFactor);
            pstmtUpdate.setInt(2, employeeID);
            int rowsUpdated = pstmtUpdate.executeUpdate();
    
            if (rowsUpdated == 0) {
                return tableModel; 
            }
    
            pstmtFetch.setInt(1, employeeID);
            try (ResultSet rs = pstmtFetch.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
    
                for (int i = 1; i <= columnCount; i++) {
                    tableModel.addColumn(metaData.getColumnName(i));
                }
    
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    tableModel.addRow(row);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error Calculating Salary Increment: " + e.getMessage());
            throw e;
        }
    
        return tableModel;
    }

    public static ResultSet getFraudulentCustomer() throws SQLException {
        ResultSet rs = null;
        String sql = "select customer.* from customer join loan on customer.customerID = loan.customerID join transaction on senderNum where loanStatus = 0 and amount > 50000";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error With Expired Card With accountID & expdate Query: " + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    public static ResultSet getATMSNeedService(int branchID) throws SQLException {
        ResultSet rs = null;
        String sql = "select * from atm where (maintenanceDate < DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY) or currentCash < 500) and branchID = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, branchID);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error With Getting ATMs Needing service" + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    public static ResultSet getATMSNeedService() throws SQLException {
        ResultSet rs = null;
        String sql = "select * from atm where (maintenanceDate < DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY) or currentCash < 500)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error With Getting ATMs Needing service" + e.getMessage());
            System.err.println(e.getErrorCode());
        }
        return rs;
    }

    public static DefaultTableModel getATMsRequiringMaintenance(int branchID) throws SQLException {
        String sql = "SELECT * FROM atm WHERE branchID = ? AND (maintenanceDate < NOW() OR currentCash < 500)";
        DefaultTableModel tableModel = new DefaultTableModel();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchID);

            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    tableModel.addColumn(metaData.getColumnName(i));
                }

                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    tableModel.addRow(row);
                }
            }
        }

        return tableModel;
    }

    public static DefaultTableModel getCardsExpiringSoon(int accountID) throws SQLException {
        String sql = "SELECT * FROM card WHERE accountID = ? AND expDate <= DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY)";
        DefaultTableModel tableModel = new DefaultTableModel();
    
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, accountID);
    
            try (ResultSet rs = pstmt.executeQuery()) {
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
    
                for (int i = 1; i <= columnCount; i++) {
                    tableModel.addColumn(metaData.getColumnName(i));
                }
    
                while (rs.next()) {
                    Object[] row = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        row[i - 1] = rs.getObject(i);
                    }
                    tableModel.addRow(row);
                }
                System.out.println("Cards Expiring Soon Query Successful");
            }
        } catch (SQLException e) {
            System.err.println("Error Fetching Cards Expiring Soon: " + e.getMessage());
            throw e;
        }
    
        return tableModel;
    }

    /*

    public static ResultSet getCardsExpiringSoon() throws SQLException {
        ResultSet rs = null;
        String SQL = "select * from card inner join BankSystem.account a on card.accountID = a.accountID where expDate <= DATE_ADD(CURRENT_DATE, INTERVAL 5 DAY)";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error With Expired Cards (from any account)" + e.getMessage());
            System.err.println(e.getErrorCode());
        }

        return rs;
    }

    */

    public static int updateCustomer(String firstName, String lastName, String address, String email, String phoneNumber, int customerID) throws SQLException {
        int rs = -1;
        String sql = String.format("UPDATE customer SET firstName = '%s', lastName = '%s', address = '%s', email = '%s', phoneNumber = '%s' Where customerID = %o", firstName, lastName, address, email, phoneNumber, customerID);
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            rs = 1;
            System.out.println("Query Successful");
        } catch (SQLException e) {
            System.err.println("Error With Updating Customer Query: " + e.getMessage());
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
            System.err.println("Error with Updating Loan Query: " + e.getMessage());
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

    public static int createAccount(int customerID, String accountType) throws SQLException {
        String sqlCheck = "SELECT COUNT(*) FROM account WHERE customerID = ?";
        String sqlInsert = "INSERT INTO account (customerID, balance, accountType) VALUES (?, 0, ?)";
        
        try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck);
             PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert)) {
            pstmtCheck.setInt(1, customerID);
            ResultSet rs = pstmtCheck.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                throw new SQLException("Account Already Exists for Customer.");
            }
            pstmtInsert.setInt(1, customerID);
            pstmtInsert.setString(2, accountType);
            return pstmtInsert.executeUpdate();
        }
    }

    public static int closeAccount(int accountID) throws SQLException {
        String sqlCheck = "SELECT balance, (SELECT COUNT(*) FROM loan WHERE accountID = ? AND status = 'Pending') AS loans FROM account WHERE accountID = ?";
        String sqlDelete = "DELETE FROM account WHERE accountID = ?";
        
        try (PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck);
             PreparedStatement pstmtDelete = conn.prepareStatement(sqlDelete)) {
            pstmtCheck.setInt(1, accountID);
            pstmtCheck.setInt(2, accountID);
            ResultSet rs = pstmtCheck.executeQuery();
            
            if (rs.next()) {
                if (rs.getInt("balance") < 0 || rs.getInt("loans") > 0) {
                    throw new SQLException("Cannot Close Account with Negative Balance or Pending Loans.");
                }
            }
            pstmtDelete.setInt(1, accountID);
            return pstmtDelete.executeUpdate();
        }
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
            System.out.println("No Results to Display.");
        }
    }

    public static ResultSet executeCustomQuery(String query) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
    
        try {
            stmt = conn.createStatement();
    
            rs = stmt.executeQuery(query);
            System.out.println("Custom Query Executed Successfully.");
        } catch (SQLException e) {
            System.err.println("Error Executing Custom Query: " + e.getMessage());
            throw e;
        }
    
        return rs;
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database Connection Closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error Closing Database Connection: " + e.getMessage());
        }
    }
}