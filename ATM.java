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
            branchID = rs.getInt("Branch ID");
            currentCash = rs.getInt("Current Cash");
            maxCash = rs.getInt("Max Cash");
            atmID = rs.getInt("ATM ID");
            location = rs.getString("Location");
            maintenanceDate = rs.getTimestamp("Maintenance Date");
        }
    }

    @Override
    public String toString() {
        return "ATM {" +
                "Branch ID=" + branchID +
                ", Current Cash=" + currentCash +
                ", Max Cash=" + maxCash +
                ", ATM ID=" + atmID +
                ", Location='" + location + '\'' +
                ", Maintenance Date=" + maintenanceDate +
                '}';
    }
}