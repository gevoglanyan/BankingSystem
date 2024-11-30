import java.sql.ResultSet;
import java.sql.SQLException;

public class Branch {
    int branchID;
    String branchName;
    String phoneNum;
    String location;

    public Branch(ResultSet rs) throws SQLException {
        if(rs.next()) {
            this.branchID = rs.getInt("branchID");
            this.branchName = rs.getString("branchName");
            this.phoneNum = rs.getString("phoneNum");
            this.location = rs.getString("location");
        }
    }


    @Override
    public String toString() {
        return "Branch{" +
                "branchID=" + branchID +
                ", branchName='" + branchName + '\'' +
                ", phoneNumber='" + phoneNum + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
