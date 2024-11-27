import java.sql.*;


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

    public static void printRs(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int CC = rsmd.getColumnCount();
        while (rs.next()) {
            for (int i = 1; i <= CC; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = rs.getString(i);
                System.out.print(rsmd.getColumnName(i) + ": " + columnValue );
            }
            System.out.println("");
        }
    }
}
