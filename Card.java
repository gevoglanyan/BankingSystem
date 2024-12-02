import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

enum CardType {
    Checking,
    Credit,
    Invalid
}

public class Card {
    CardType cardType;
    Timestamp expDate;
    int cvv;
    int cardNum;
    int accountID;

    public Card(ResultSet rs) throws SQLException {
        if(rs.next()){
            cardType = matchCardType(rs.getString("cardType"));
            expDate = rs.getTimestamp("expDate");
            cvv = rs.getInt("cvv");
            cardNum = rs.getInt("cardNum");
            accountID = rs.getInt("accountID");
        }
    }

    private CardType matchCardType(String cardType){
        return switch (cardType) {
            case "checking" -> CardType.Checking;
            case "credit" -> CardType.Credit;
            default -> CardType.Invalid;
        };
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardType=" + cardType +
                ", expDate=" + expDate +
                ", cvv=" + cvv +
                ", cardNum=" + cardNum +
                ", accountID=" + accountID +
                '}';
    }
}