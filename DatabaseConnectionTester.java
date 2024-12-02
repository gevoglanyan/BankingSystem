import javax.xml.transform.Result;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@SuppressWarnings("unused")
public class DatabaseConnectionTester {

    public static void main(String[] args) throws SQLException {
        try{
            Connection conn = DatabaseConnection.getConnection();
            System.out.println("Connected to Database");

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

            */

//            Customer customer = new Customer(DatabaseQuery.getCustomerByEmail("professor1@gmail.com"));
//            System.out.println(customer);

//            Employee employee = new Employee(DatabaseQuery.getEmployeeFromBranch(1));
//            System.out.println(employee);

//            Loan loan = new Loan(DatabaseQuery.getLoansFromCustomer(1));
//            System.out.println(loan);

//            Transaction transaction = new Transaction(DatabaseQuery.transactionsRelatedToAccount(1));
//            System.out.println(transaction);

            int result = DatabaseQuery.createTransaction(1,2,1000000,"wire");
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error Connecting to Database: " + e.getMessage());
        }
    }
}