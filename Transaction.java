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
            senderNum = rs.getInt("senderNum");
            receiverNum = rs.getInt("receiverNum");
            transactionDate = rs.getTimestamp("transactionDate");
            amount = rs.getInt("amount");
            transactionType = matchTransactionType(rs.getString("transactionType"));
            transactionID = rs.getInt("transactionID");
        }
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "senderNum=" + senderNum +
                ", receiverNum=" + receiverNum +
                ", transactionDate=" + transactionDate +
                ", amount=" + amount +
                ", transactionType=" + transactionType +
                ", transactionID=" + transactionID +
                '}';
    }

    private TransactionType matchTransactionType(String transactionType){
        return switch (transactionType){
            case "loan" -> TransactionType.Loan;
            case "wire" -> TransactionType.Wire;
            default -> TransactionType.Invalid;
        };
    }
}