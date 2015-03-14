
package banksystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class EmployeeLogin {
    private String userName = null;
    private String password = null;
    
    public EmployeeLogin(String userName, String pass) {
        this.userName = userName;
        this.password = pass;
    }
    
    public String login(){
        String userN = null;
        String passW = null;
        String userType = "X";
        
        try {
            Connection dbConn = DBConnection.connect();
            Statement dbQuary = dbConn.createStatement();
            ResultSet result = dbQuary.executeQuery("SELECT l.userName, l.passW, e.authLimit, e.name, e.empNo FROM login AS l JOIN employee AS e ON e.empNo = l.empNo WHERE l.userName = '"+userName+"' AND l.passW = '"+password+"';");
            
            while(result.next())
            {
                userN = result.getString("userName");
                passW = result.getString("passW");
                userType = result.getString("authLimit");
                PlaceHolder.loggedUser = result.getString("name");
                PlaceHolder.loggedEmpID = result.getString("empNo");
            }
            
            if (!(userName.equals(userN) && password.equals(passW)))
                userType = "X";
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Related Error!\nError : " + e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        finally{
            return userType;
        }
            
    }
    
    
}
