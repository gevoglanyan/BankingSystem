import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ATM {
    int branchID;
    int currentCash;
    int maxCash;
    int atmID;
    String location;
    Timestamp maintenanceDate;



    public ATM(ResultSet rs) throws SQLException {
        if (rs.next()) {
            branchID = rs.getInt("branchID");
            currentCash = rs.getInt("currentCash");
            maxCash = rs.getInt("maxCash");
            atmID = rs.getInt("atmID");
            location = rs.getString("location");
            maintenanceDate = rs.getTimestamp("maintenanceDate");
        }
    }

    @Override
    public String toString() {
        return "ATM{" +
                "branchID=" + branchID +
                ", currentCash=" + currentCash +
                ", maxCash=" + maxCash +
                ", atmID=" + atmID +
                ", location='" + location + '\'' +
                ", maintenanceDate=" + maintenanceDate +
                '}';
    }
}
