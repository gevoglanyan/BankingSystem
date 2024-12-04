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
            cardType = matchCardType(rs.getString("Card Type"));
            expDate = rs.getTimestamp("Expiration Date");
            cvv = rs.getInt("CVV");
            cardNum = rs.getInt("Card Number");
            accountID = rs.getInt("Account ID");
        }
    }

    private CardType matchCardType(String cardType){
        return switch (cardType) {
            case "Checking" -> CardType.Checking;
            case "Credit" -> CardType.Credit;
            default -> CardType.Invalid;
        };
    }

    @Override
    public String toString() {
        return "Card {" +
                "Card Type=" + cardType +
                ", Expiration Date=" + expDate +
                ", CVV=" + cvv +
                ", Card Number=" + cardNum +
                ", Account ID=" + accountID +
                '}';
    }
}