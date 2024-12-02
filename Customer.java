import java.sql.ResultSet;
import java.sql.SQLException;

public class Customer {
    int customerID;
    String firstName;
    String lastName;
    String address;
    String email;
    String phoneNumber;

    public Customer(ResultSet rs) throws SQLException {
        if(rs.next()) {
            customerID = rs.getInt("customerID");
            firstName = rs.getString("firstName");
            lastName = rs.getString("lastName");
            address = rs.getString("address");
            email = rs.getString("email");
            phoneNumber = rs.getString("phoneNumber");
        }
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}