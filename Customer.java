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
            customerID = rs.getInt("Customer ID");
            firstName = rs.getString("First Name");
            lastName = rs.getString("Last Name");
            address = rs.getString("Address");
            email = rs.getString("Email");
            phoneNumber = rs.getString("Phone Number");
        }
    }

    @Override
    public String toString() {
        return "Customer {" +
                "Customer ID=" + customerID +
                ", First Name='" + firstName + '\'' +
                ", Last Name='" + lastName + '\'' +
                ", Address='" + address + '\'' +
                ", Email='" + email + '\'' +
                ", Phone Number='" + phoneNumber + '\'' +
                '}';
    }
}