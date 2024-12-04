import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Employee {
    int branchID;
    String firstName;
    String lastName;
    String role;
    Timestamp dateHired;

    public Employee(ResultSet rs) throws SQLException {
        if(rs.next()) {
            branchID = rs.getInt("Branch ID");
            firstName = rs.getString("First Name");
            lastName = rs.getString("Last Name");
            role = rs.getString("Role");
            dateHired = rs.getTimestamp("Date Hired");
        }
    }

    @Override
    public String toString() {
        return "Employee {" +
                "Branch ID=" + branchID +
                ", First Name='" + firstName + '\'' +
                ", Last Name='" + lastName + '\'' +
                ", Role='" + role + '\'' +
                ", DateH ired=" + dateHired +
                '}';
    }
}