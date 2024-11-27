import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class db_query {

    public static ResultSet customerQuery1(String email) throws SQLException {
        Connection conn = DatabaseConnection.getConnection();
        ResultSet rs = null;
        Statement stmt = conn.createStatement();
        String sql = "select * from customer where email = '" + email + "'";
        try{
            rs = stmt.executeQuery(sql);
            System.out.println("Query Successful");
        }catch (Exception e) {
            System.out.println("Error with Email: " + e.getMessage());
        }
        return rs;
    }

    public static void printRs(ResultSet rs){
        //working to setup print method to show data

        /*while(rs.next()){
            System.out.println(rs.getString("columnName") + " " + rs.getString("columnName") +....);
        }
        */
    }
}
