
package banksystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class Customer extends Person {
    protected String accNo, pin;
    
    public Customer(String name, String nicNo, String adr, String phoneNo, String dob, String accNo, String pinNo) {
        this.fulName = name;
        this.NIC = nicNo;
        this.address = adr;
        this.dob = dob;
        this.phone = phoneNo;
        this.pin = pinNo;
        this.accNo = accNo;
    }
    
    
    

    public boolean create() {
        boolean rtn = false;
        
        try {
            Connection dbConnect = DBConnection.connect();
            PreparedStatement dbInsert = dbConnect.prepareStatement("INSERT INTO customer (cusNIC, name, address, phone, dob, accNo, pin) VALUES ('"+NIC+"', '"+fulName+"', '"+address+"', '"+phone+"', '"+dob+"', '"+accNo+"', '"+pin+"')");
            dbInsert.executeUpdate();
            dbInsert.close();
            rtn = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inserting to 'Customer Database'.\nError Message : " + e.getMessage(), "Error Inserting", JOptionPane.ERROR_MESSAGE);
            rtn = false;
        }finally{
            return rtn;
        }
        
    }
}
