
package banksystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Employee {
    private String fullName, address, nic, phone, dob;
    private String authLevel, grade, designation, employeeID;
    private String userName, password;
    int rtn;
    
    public Employee() {        
    }
    
    public Employee(String name, String adr, String nicNo, String phoneNo, String dateOfBirth, String auth, String grade, String desig, String empID, String uName, String pWord) {
        this.fullName = name;
        this.address = adr;
        this.nic = nicNo;
        this.phone = phoneNo;
        this.dob = dateOfBirth;
        this.authLevel = auth;
        this.grade = grade;
        this.designation = desig;
        this.employeeID = empID;
        this.userName = uName;
        this.password = pWord;
    }
    
    public int add() {
        try {
            Connection dbConn = DBConnection.connect();
            PreparedStatement dbInsert = dbConn.prepareStatement("INSERT INTO employee (empNo, empNIC, name, address, phone, dob, grade, authLimit, designation) VALUES ('"+employeeID+"', '"+nic+"', '"+fullName+"', '"+address+"', '"+phone+"', '"+dob+"', '"+grade+"', '"+authLevel+"', '"+designation+"');");
            dbInsert.executeUpdate();
            dbInsert.close();
            
            Connection dbConn2 = DBConnection.connect();
            PreparedStatement dbInsert2 = dbConn.prepareStatement("INSERT INTO login VALUES ('"+employeeID+"', '"+userName+"', '"+password+"');");
            dbInsert2.executeUpdate();
            dbInsert2.close();
            
            rtn = 1;
                        
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database Related Error!\nError Message : " + e.getMessage(), "Error!", JOptionPane.WARNING_MESSAGE);
            rtn = 0;
        }
        finally {
            return rtn;
        }
    }
    
    public boolean remove(String empNum) {
        boolean Rtn = false;
        try {
            Connection dbConn = DBConnection.connect();
            PreparedStatement dbInsert = dbConn.prepareStatement("DELETE FROM employee WHERE empNo = '"+empNum+"'");
            dbInsert.executeUpdate();
            dbInsert.close();
            Rtn = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Deleting from 'Employee Database'.\nError Message : " + e.getMessage(), "Remove Employee", JOptionPane.ERROR_MESSAGE);
            Rtn = false;
        } finally {
            return Rtn;
        }
    }
}
