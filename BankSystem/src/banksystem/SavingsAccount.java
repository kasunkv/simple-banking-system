
package banksystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class SavingsAccount extends Account {
    protected float intRate, minimumBalance;
    
    public SavingsAccount(){
        
    }
    
    public SavingsAccount(String accountNo, String cusName, String accStatus, String autho, String dateOpen, String dateClose, String type, float init, float bal,  float minBal, float interestRate) {
        this.accNo = accountNo;
        this.name = cusName;
        this.status = accStatus;
        this.authorizedBy = autho;
        this.dateOpened = dateOpen;
        this.dateColsed = dateClose;
        this.accType = type;
        this.initialDeposit = init;
        this.balance = bal;
        this.minimumBalance = minBal;
        this.intRate = interestRate;
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
            PreparedStatement dbInsert = dbConnect.prepareStatement("INSERT INTO savings (accNo, intRate, minBalance) VALUES ('"+accNo+"', "+intRate+", "+minimumBalance+");");
            dbInsert.executeUpdate();
            dbInsert.close(); 
            rtn = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inserting to the 'Savings' table. \nError Message : " + e.getMessage(), "Database Insert Error", JOptionPane.ERROR_MESSAGE);            
            rtn = false;
        } finally {
            return rtn;
        }       
        //JOptionPane.showMessageDialog(null, "Account created successfully.", "Create Account", JOptionPane.INFORMATION_MESSAGE);
        
        
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
            ResultSet result = dbQuery.executeQuery("SELECT * FROM accounts AS a JOIN savings AS s WHERE a.accNo = s.accNo AND a.accNo = '"+AccNo+"';");
            
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
                intRate = result.getFloat("intRate");
                minimumBalance = result.getFloat("minBalance");
                
                        
            }
            rtn = true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Reading form the Database\nError Message : " + e.getMessage(), "Read Error (S)", JOptionPane.ERROR_MESSAGE);
            rtn = false;
        } finally {
            return rtn;
        }  
        
        
    }
    
    public float updateInterest(){
        return 0;
    }
    
    public float updateInterest(String AccNum, float AccountBalance) {
        float interestRate = 0;
        float newAccBalance = 0;
        
        try {
            Connection dbConnect = DBConnection.connect();
            Statement dbQuery = dbConnect.createStatement();
            ResultSet result = dbQuery.executeQuery("SELECT * FROM savings WHERE accNO = '"+AccNum+"';");
            
            while(result.next())
            {
                interestRate = result.getFloat("intRate");
            }
            
             newAccBalance = AccountBalance + (AccountBalance * interestRate);
             
             try {            
                PreparedStatement dbUpdate = dbConnect.prepareStatement("UPDATE accounts SET balance = "+newAccBalance+" WHERE accNo = '"+AccNum+"';");
                dbUpdate.executeUpdate();
                dbUpdate.close();
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error Updating 'Accounts Database;.\nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
             
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Accessing 'Savings Database;.\nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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
    
    public String getIntRate(){
        return String.valueOf(intRate);
    }
    
    public String getMinimumBalance(){
        return String.valueOf(minimumBalance);
    }

    @Override
    public boolean printReport() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
        
    
    
}
