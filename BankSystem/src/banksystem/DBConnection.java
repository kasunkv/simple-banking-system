
package banksystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static Connection con;
    
    public static Connection connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank_system?autoReconnect=true","root","");
        } catch (Exception e) {
            System.out.println("Database Connection Error in the DBConnection Class\nError : " + e.getMessage());
        }        
        return con;
    }
    
}
