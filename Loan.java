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
            customerID = rs.getInt("customerID");
            loanType = matchLoanType(rs.getString("loanType"));
            loanID = rs.getInt("loanID");
            loanStatus = rs.getInt("loanStatus");
            loanAmount = rs.getInt("loanAmount");
        }
    }

    @Override
    public String toString() {
        return "Loan{" +
                "customerID=" + customerID +
                ", loanType=" + loanType +
                ", loanID=" + loanID +
                ", loanStatus=" + loanStatus +
                ", loanAmount=" + loanAmount +
                '}';
    }

    private LoanType matchLoanType(String loanType){
        return switch (loanType) {
            case "mortgage" -> LoanType.Mortgage;
            case "credit" -> LoanType.Credit;
            case "vehicle" -> LoanType.Vehicle;
            case "student" -> LoanType.Student;
            case "business" -> LoanType.Business;
            default -> LoanType.Invalid;
        };
    }
}



