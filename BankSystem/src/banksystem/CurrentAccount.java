
package banksystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class CurrentAccount extends Account{
    protected float odLimit, odIntRate;
    protected String introBy;
    
    public CurrentAccount(){
        
    }
    
    public CurrentAccount(String accountNo, String cusName, String accStatus, String autho, String dateOpen, String dateClose, String type, float init, float bal,  float ODLimit, float ODInterestRate, String introducedBy) {
        this.accNo = accountNo;
        this.name = cusName;
        this.status = accStatus;
        this.authorizedBy = autho;
        this.dateOpened = dateOpen;
        this.dateColsed = dateClose;
        this.accType = type;
        this.initialDeposit = init;
        this.balance = bal;
        this.odLimit = ODLimit;
        this.odIntRate = ODInterestRate;
        this.introBy = introducedBy;
    }
    
    
    public boolean create() {
        boolean rtn;
        try {
            Connection dbConnect = DBConnection.connect();
            PreparedStatement dbInsert = dbConnect.prepareStatement("INSERT INTO accounts (accNo, name, balance, intDeposite, dateOpened, accStatus, authoBy, accType) VALUES ('"+accNo+"', '"+name+"', "+balance+", "+initialDeposit+", CURDATE(), '"+status+"', '"+authorizedBy+"', '"+accType+"');");
            dbInsert.executeUpdate();
            dbInsert.close();    
            rtn = true;            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inserting to the 'Accounts' table. \nError Message : " + e.getMessage(), "Database Insert Error", JOptionPane.ERROR_MESSAGE);            
            rtn = false;
        }       
        
        try {
            Connection dbConnect = DBConnection.connect();
            PreparedStatement dbInsert = dbConnect.prepareStatement("INSERT INTO current (accNo, odLimit, odInt, introBy) VALUES ('"+accNo+"', "+odLimit+", "+odIntRate+", '"+introBy+"');");
            dbInsert.executeUpdate();
            dbInsert.close(); 
            rtn = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inserting to the 'Current' table. \nError Message : " + e.getMessage(), "Database Insert Error", JOptionPane.ERROR_MESSAGE);            
            rtn = false;
        } finally {
            return rtn;
        }       
    }

    public boolean close() {
        boolean rtn = true;
        return rtn;
    }

    public boolean deposit() {
        boolean rtn = true;
        return rtn; 
    }
    
    public boolean withdraw() {
        boolean rtn = true;
        return rtn;
    }
    
    public boolean transfer() {
        boolean rtn = true;
        return rtn;
    }
    
    public boolean checkBalance() {
        boolean rtn = true;
        return rtn;
    }
    
    public boolean printReport(String AccNo) {
        boolean rtn = false;
               
        try {
            Connection dbConnect = DBConnection.connect();
            Statement dbQuery = dbConnect.createStatement();
            ResultSet result = dbQuery.executeQuery("SELECT * FROM accounts AS a JOIN current AS c WHERE a.accNo = c.accNo AND a.accNo = '"+AccNo+"';");
            
            while(result.next())
            {
                name = result.getString("name");
                balance = result.getFloat("balance");
                initialDeposit = result.getFloat("intDeposite");
                dateOpened = result.getString("dateOpened");
                dateColsed  = result.getString("dateColsed");
                accStatus = result.getString("accStatus");
                authorizedBy = result.getString("authoBy");
                accType = result.getString("accType");
                introBy = result.getString("introBy");
                odLimit = result.getFloat("odLimit");
                odIntRate = result.getFloat("odInt");
                        
            }
            rtn = true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Reading form the Database\nError Message : " + e.getMessage(), "Read Error (C)", JOptionPane.ERROR_MESSAGE);
            rtn = false;
        } finally {
            return rtn;
        }
        
        
    }
    
    public float updateInterest(){
        return 0;
    }
    
    public float updateInterest(String AccNum, float AccountBalance) {
        float odInterestRate = 0;
        float newAccBalance = 0;
        
        try {
            Connection dbConnect = DBConnection.connect();
            Statement dbQuery = dbConnect.createStatement();
            ResultSet result = dbQuery.executeQuery("SELECT * FROM current WHERE accNO = '"+AccNum+"';");
            
            while(result.next())
            {
                odInterestRate = result.getFloat("odInt");
            }
            
            if (AccountBalance < 0)
            {
                newAccBalance = AccountBalance + (AccountBalance * odInterestRate);
             
                 try {            
                    PreparedStatement dbUpdate = dbConnect.prepareStatement("UPDATE accounts SET balance = "+newAccBalance+" WHERE accNo = '"+AccNum+"';");
                    dbUpdate.executeUpdate();
                    dbUpdate.close();

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error Updating 'Accounts Database;.\nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                newAccBalance = AccountBalance;
                JOptionPane.showMessageDialog(null, "Over-Draft Balance has not yet been reached.\nThe interest will not be calculated.", "Calculate Interest", JOptionPane.INFORMATION_MESSAGE);
            }
             
             
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Accessing 'Current Database;.\nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
       return newAccBalance;
    }
    
    // getter methods 
    
    public String getName() {
        return name;
    }
            
    public String getAccBalance() {
        return String.valueOf(balance);
    }
    
    public String getIniDeposite(){
        return String.valueOf(initialDeposit);
    }
    
    public String getDateOpened() {
        return dateOpened;
    }
    
    public String getDateClosed(){
        return dateColsed;
    }
    
    public String getAccStatus(){
        return accStatus;
    }
    
    public String getAuthoBy(){
        return authorizedBy;
    }
    
    public String getType(){
        return accType;
    }
    
    public String getOdLimit(){
        return String.valueOf(odLimit);
    }
    
    public String getOdInt(){
        return String.valueOf(odIntRate);
    }
    
    public String getIntroby() {
        return introBy;
    }

    @Override
    public boolean printReport() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
