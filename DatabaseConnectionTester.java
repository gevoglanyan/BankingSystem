import javax.xml.transform.Result;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import java.sql.Connection;
import java.sql.SQLException;

@SuppressWarnings("unused")
public class DatabaseConnectionTester {
    public static void main(String[] args) {
        try {
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("Connected to Database");

            testAccountCreation();
            testAccountClosure();
            testTransactionValidation();
            testLoanApproval();
            testInterestCalculation();
            testCardExpiryValidation();
            testLoanPayment();
            testBranchTransfer();
            testMinimumBalanceCheck();
            testSalaryCalculation();

            /* 

            ResultSet rs = DatabaseQuery.customerQuery1("professor1@gmail.com")
            DatabaseQuery.printRs(rs);

            ResultSet rs2 = DatabaseQuery.getCustomerByAddress("hogwarts");
            DatabaseQuery.printRs(rs2);

            int rs3 = DatabaseQuery.createCustomerQuery("Maxwell","Kozlov","CSUN","maxwell.kozlov.595@my.csun.edu","818-523-6396");
            Boolean status = DatabaseQuery.checkIfEmailExists("maxwell.kozlov.55@my.csun.edu");ed
            System.out.println(status);

            ResultSet rs4 = DatabaseQuery.transactionsRelatedToAccount(1);
            DatabaseQuery.printRs(rs4);

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fromDate = formatter.parse("2024-11-12 00:00:00");
            Date toDate = formatter.parse("2024-11-30 23:59:59");
            ResultSet rs5 = DatabaseQuery.transactionsBetweenDates(Timestamp.from(fromDate.toInstant()), Timestamp.from(toDate.toInstant()));
            DatabaseQuery.printRs(rs5);

            ResultSet rs6 = DatabaseQuery.getLoansFromCustomer(1);
            DatabaseQuery.printRs(rs6);

            Customer customer = new Customer(DatabaseQuery.getCustomerByEmail("professor1@gmail.com"));
            System.out.println(customer);

            Employee employee = new Employee(DatabaseQuery.getEmployeeFromBranch(1));
            System.out.println(employee);

            Loan loan = new Loan(DatabaseQuery.getLoansFromCustomer(1));
            System.out.println(loan);

            */

            System.out.println("All Tests Completed Successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void testAccountCreation() throws SQLException {
        System.out.println("Testing Account Creation...");
        int result = DatabaseQuery.createAccount(1, "Savings");
        if (result > 0) {
            System.out.println("Account Created Successfully");
        } else {
            System.out.println("Failed to Create Account");
        }
    }

    private static void testAccountClosure() throws SQLException {
        System.out.println("Testing Account Closure...");
        int result = DatabaseQuery.closeAccount(1); 
        if (result > 0) {
            System.out.println("Account Closed Successfully");
        } else {
            System.out.println("Failed to Close Account");
        }
    }

    private static void testTransactionValidation() throws SQLException {
        System.out.println("Testing Transaction Validation...");
        int result = DatabaseQuery.createTransaction(1, 2, 100); 
        if (result > 0) {
            System.out.println("Transaction Successful");
        } else {
            System.out.println("Transaction Failed: Insufficient Balance or Invalid Accounts");
        }
    }

    private static void testLoanApproval() throws SQLException {
        System.out.println("Testing Loan Approval...");
        boolean approved = DatabaseQuery.approveLoan(1, 5000); 
        if (approved) {
            System.out.println("Loan Approved");
        } else {
            System.out.println("Loan Denied: Does Not Meet Criteria");
        }
    }

    private static void testInterestCalculation() throws SQLException {
        System.out.println("Testing Interest Calculation...");
        DatabaseQuery.calculateInterest(0.05); 
        System.out.println("Interest Applied Successfully");
    }

    private static void testCardExpiryValidation() throws SQLException {
        System.out.println("Testing Card Expiry Validation...");
        boolean isValid = DatabaseQuery.validateCard(1); 
        System.out.println(isValid ? "Card is Valid" : "Card has Expired");
    }

    private static void testLoanPayment() throws SQLException {
        System.out.println("Testing Loan Payment...");
        DatabaseQuery.makeLoanPayment(1, 500); 
        System.out.println("Payment Applied Successfully");
    }

    private static void testBranchTransfer() throws SQLException {
        System.out.println("Testing Branch Transfer...");
        DatabaseQuery.transferEmployee(1, 2); 
        System.out.println("Employee Transferred Successfully");
    }

    private static void testMinimumBalanceCheck() throws SQLException {
        System.out.println("Testing Minimum Balance Check...");
        DatabaseQuery.applyMinimumBalanceFee(500, 50); 
        System.out.println("Fee Applied to Accounts Below Minimum Balance");
    }

    private static void testSalaryCalculation() throws SQLException {
        System.out.println("Testing Employee Salary Calculation...");
        DatabaseQuery.calculateSalaryIncrement(1, 1.1); 
        System.out.println("Salary Updated Successfully");
    }
}