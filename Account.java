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
            accountID = rs.getInt("accountID");
            accountNum = rs.getInt("accountNum");
            accountType = matchAccountType(rs.getString("accountType"));
            balance = rs.getInt("balance");
            customerID = rs.getInt("customerID");
        }
    }

    private AccountType matchAccountType(String accountType) {
        return switch (accountType) {
            case "checkings" -> AccountType.Checkings;
            case "savings" -> AccountType.Savings;
            case "credit card" -> AccountType.CreditCard;
            case "loan" -> AccountType.Loan;
            default -> AccountType.Invalid;
        };
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountID=" + accountID +
                ", accountNum=" + accountNum +
                ", accountType=" + accountType +
                ", balance=" + balance +
                ", customerID=" + customerID +
                '}';
    }
}