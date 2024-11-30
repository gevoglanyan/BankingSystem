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
            branchID = rs.getInt("branchID");
            firstName = rs.getString("firstName");
            lastName = rs.getString("lastName");
            role = rs.getString("role");
            dateHired = rs.getTimestamp("dateHired");
        }
    }

    @Override
    public String toString() {
        return "Employee{" +
                "branchID=" + branchID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role='" + role + '\'' +
                ", dateHired=" + dateHired +
                '}';
    }
}
