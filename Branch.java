import java.sql.ResultSet;
import java.sql.SQLException;

public class Branch {
    int branchID;
    String branchName;
    String phoneNum;
    String location;

    public Branch(ResultSet rs) throws SQLException {
        if(rs.next()) {
            this.branchID = rs.getInt("Branch ID");
            this.branchName = rs.getString("Branch Name");
            this.phoneNum = rs.getString("Phone Number");
            this.location = rs.getString("Location");
        }
    }

    @Override
    public String toString() {
        return "Branch {" +
                "Branch ID=" + branchID +
                ", Branch Name='" + branchName + '\'' +
                ", Phone Number='" + phoneNum + '\'' +
                ", Location='" + location + '\'' +
                '}';
    }
}