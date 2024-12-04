import java.sql.ResultSet;
import java.sql.SQLException;

enum AccountType {
    Checkings,
    Savings,
    CreditCard,
    Loan,
    Invalid
}

public class Account {
    int accountID;
    int accountNum;
    AccountType accountType;
    int balance;
    int customerID;

    public Account(ResultSet rs) throws SQLException {
        if(rs.next()) {
            accountID = rs.getInt("Account ID");
            accountNum = rs.getInt("Account Number");
            accountType = matchAccountType(rs.getString("Account Type"));
            balance = rs.getInt("Balance");
            customerID = rs.getInt("Customer ID");
        }
    }

    private AccountType matchAccountType(String accountType) {
        return switch (accountType) {
            case "Checkings" -> AccountType.Checkings;
            case "Savings" -> AccountType.Savings;
            case "Credit Card" -> AccountType.CreditCard;
            case "Loan" -> AccountType.Loan;
            default -> AccountType.Invalid;
        };
    }

    @Override
    public String toString() {
        return "Account {" +
                "Account ID=" + accountID +
                ", Account Number=" + accountNum +
                ", Account Type=" + accountType +
                ", Balance=" + balance +
                ", Customer ID=" + customerID +
                '}';
    }
}