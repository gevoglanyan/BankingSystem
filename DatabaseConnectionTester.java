
import javax.xml.transform.Result;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DatabaseConnectionTester {

    public static void main(String[] args) throws SQLException {
        try{
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Connected to database");

//            ResultSet rs = db_query.customerQuery1("professor1@gmail.com")
//            db_query.printRs(rs);
//
//            ResultSet rs2 = db_query.getCustomerByAddress("hogwarts");
//            db_query.printRs(rs2);

//            int rs3 = db_query.createCustomerQuery("Maxwell","Kozlov","CSUN","maxwell.kozlov.595@my.csun.edu","818-523-6396");
//            Boolean status = db_query.checkIfEmailExists("maxwell.kozlov.55@my.csun.edu");ed
//            System.out.println(status);

//            ResultSet rs4 = db_query.transactionsRelatedToAccount(1);
//            db_query.printRs(rs4);

//                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date fromDate = formatter.parse("2024-11-12 00:00:00");
//                Date toDate = formatter.parse("2024-11-30 23:59:59");
//                ResultSet rs5 = db_query.transactionsBetweenDates(Timestamp.from(fromDate.toInstant()), Timestamp.from(toDate.toInstant()));
//                db_query.printRs(rs5);


            ResultSet rs6 = db_query.getLoansFromCustomer(1);
            db_query.printRs(rs6);


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }

}
