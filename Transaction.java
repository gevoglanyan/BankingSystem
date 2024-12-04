import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

enum TransactionType {
    Loan,
    Wire,
    Invalid
}

public class Transaction {
    int senderNum;
    int receiverNum;
    Timestamp transactionDate;
    int amount;
    TransactionType transactionType;
    int transactionID;

    public Transaction(ResultSet rs) throws SQLException {
        if(rs.next()){
            senderNum = rs.getInt("Sender Number");
            receiverNum = rs.getInt("Receiver Number");
            transactionDate = rs.getTimestamp("Transaction Date");
            amount = rs.getInt("Amount");
            transactionType = matchTransactionType(rs.getString("Transaction Type"));
            transactionID = rs.getInt("Transaction ID");
        }
    }

    @Override
    public String toString() {
        return "Transaction {" +
                "Sender Number=" + senderNum +
                ", Receiver Number=" + receiverNum +
                ", Transaction Date=" + transactionDate +
                ", Amount=" + amount +
                ", Transaction Type=" + transactionType +
                ", Transaction ID=" + transactionID +
                '}';
    }

    private TransactionType matchTransactionType(String transactionType){
        return switch (transactionType){
            case "Loan" -> TransactionType.Loan;
            case "Wire" -> TransactionType.Wire;
            default -> TransactionType.Invalid;
        };
    }
}