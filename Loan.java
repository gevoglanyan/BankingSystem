import java.sql.SQLException;
import java.sql.ResultSet;

enum LoanType {
    Mortgage,
    Credit,
    Vehicle,
    Student,
    Business,
    Invalid
}

public class Loan {
    int customerID;
    LoanType loanType;
    int loanID;
    int loanStatus;
    int loanAmount;

    public Loan(ResultSet rs) throws SQLException {
        if(rs.next()) {
            customerID = rs.getInt("Customer ID");
            loanType = matchLoanType(rs.getString("Loan Type"));
            loanID = rs.getInt("Loan ID");
            loanStatus = rs.getInt("Loan Status");
            loanAmount = rs.getInt("Loan Amount");
        }
    }

    @Override
    public String toString() {
        return "Loan {" +
                "Customer ID=" + customerID +
                ", Loan Type=" + loanType +
                ", Loan ID=" + loanID +
                ", Loan Status=" + loanStatus +
                ", Loan Amount=" + loanAmount +
                '}';
    }

    private LoanType matchLoanType(String loanType){
        return switch (loanType) {
            case "Mortgage" -> LoanType.Mortgage;
            case "Credit" -> LoanType.Credit;
            case "Vehicle" -> LoanType.Vehicle;
            case "Student" -> LoanType.Student;
            case "Business" -> LoanType.Business;
            default -> LoanType.Invalid;
        };
    }
}