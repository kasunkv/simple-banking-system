
package banksystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public abstract class Account {
    protected String accNo, name, status;
    protected String authorizedBy, dateOpened, dateColsed, accType;
    protected float initialDeposit, balance;
    
    /* Check Balance Variables */
    
    protected String cusName = null;
    protected String NICnum = null;
    protected String accStatus = null;
    protected String accbalance = null;
    
    /* withdraw Balance variables */
    
    protected float newAccountBalance, prevAccountBalance;
    
    /* transfer VAriables */
    
    protected float newTMAccountBalance, prevTMAccountBalance;

    public abstract boolean  create();
    
    public boolean close(String accNo){
        boolean rtn = false;
                
        try {
            Connection dbConnect = DBConnection.connect();
            PreparedStatement dbInsert = dbConnect.prepareStatement("UPDATE accounts SET accStatus = 'Deactive' WHERE accNo = '"+accNo+"';");
            dbInsert.executeUpdate();
            dbInsert.close();
            rtn = true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating 'Account Table'\nError Message : " + e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
            rtn = false;
        }
        finally{
            return rtn;
        }
    }    
               
    public boolean deposit(float depAmmount, String AccNo, String Method) {
        boolean rtn = false;
        String TID = "X";
        String tempTID = "2020001";
        long tNo;
        
        try {
            Connection dbConnect = DBConnection.connect();
            Statement dbQuery = dbConnect.createStatement();
            ResultSet result = dbQuery.executeQuery("SELECT TID FROM trans;");
            
           while(result.last())
            {
                TID = result.getString("TID");
            }
             result.absolute(-2);
             TID = result.getString("TID");
             
        
            if (TID.equals("X"))
            {
                TID = tempTID;
            }
            else
            {
                tNo = Long.parseLong(TID);
                tNo += 2;
                TID = String.valueOf(tNo);
                
            }     
            
            result.close();
            dbQuery.close();
            dbConnect.close();
                    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error *** : " + e.getMessage());
        }
        
        try {
            Connection dbConnect = DBConnection.connect();
            PreparedStatement dbInsert = dbConnect.prepareStatement("INSERT INTO trans (TID, timestp, amount, transType, method, accNo, empNo) VALUES ('"+TID+"', NOW(), "+depAmmount+", 'Deposit', '"+Method+"', '"+AccNo+"', '"+PlaceHolder.loggedEmpID+"');");
            dbInsert.executeUpdate();
            dbInsert.close();
            rtn = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Inserting to the 'Transaction Database'. \nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            rtn = false;
        } finally {
            return rtn;
        }
        
        
    }
    
    public boolean withdraw(String accNo, float wBalance) {
        boolean rtn = false;
        String AccType = null;
        String tempAccType;
        String AccountStatus = null;
        float accBalance = 0;
        float minimumBal = 0;
        float ODLimit = 0;
        float newBalance = 0;
        
        String TID = "2020001";
        String tempTID = "2020001";
        long tNo;
        
        try { // to get the next TID
            Connection dbConnect = DBConnection.connect();
            Statement dbQuery = dbConnect.createStatement();
            ResultSet result = dbQuery.executeQuery("SELECT TID FROM trans;");
            
            while(result.last())
            {
                TID = result.getString("TID");
            }
            
             result.absolute(-2);
             TID = result.getString("TID");
            
            
            
            if (TID.equals("X"))
            {
                TID = tempTID;
            }
            else
            {
                tNo = Long.parseLong(TID);
                tNo += 2;
                TID = String.valueOf(tNo);
            }
            
            result.close();
            dbQuery.close();
            dbConnect.close();
            
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Creating the Transaction ID", "Error", JOptionPane.ERROR_MESSAGE); 
            rtn = false;
        }       // end of try 1
        
        
        try { // to get the account type
            Connection dbConnect = DBConnection.connect();
            
            Statement dbQuery1 = dbConnect.createStatement();
            ResultSet result1 = dbQuery1.executeQuery("SELECT accType, accStatus FROM accounts WHERE accNo = '"+accNo+"';");
            
            while(result1.next())
            {
                AccType = result1.getString("accType");
                AccountStatus = result1.getString("accStatus");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Accessing 'Accounts Datatbase'\nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            rtn = false;
        }
        
        // check for accStatus
        if (AccountStatus.equals("Deactive"))
        {
            JOptionPane.showMessageDialog(null, "Account Number : " + accNo + "\nThe Account is Closed. Transactions can not be done.", "Closed Account", JOptionPane.INFORMATION_MESSAGE);
        }
        else
        {
            
            if (AccType.equals("sav"))
                tempAccType = "Savings Account";
            else if (AccType.equals("cur"))
                tempAccType = "Current Account";

            if (AccType.equals("sav"))  
            {
                try {
                    Connection dbConnect = DBConnection.connect();                    
                    Statement dbQuery = dbConnect.createStatement();
                    ResultSet result = dbQuery.executeQuery("SELECT a.balance, s.minBalance FROM accounts AS a JOIN savings AS s ON s.accNO = a.accNO WHERE a.accNo = '"+accNo+"';");

                    while(result.next())
                    {
                        accBalance = result.getFloat("balance");
                        minimumBal = result.getFloat("minBalance");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error Accessing 'Accounts, Savings Datatbase'\nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    rtn = false;
                }           

                if (minimumBal > (accBalance - wBalance))
                {
                   JOptionPane.showMessageDialog(null, "The Withdraw Amount : " + wBalance + "\nInsufficiant Balance for Transaction.", "Insufficant Balance", JOptionPane.INFORMATION_MESSAGE);
                   rtn = false;
                }
                else
                {
                    newBalance = accBalance - wBalance;
                    newAccountBalance = newBalance;
                    prevAccountBalance = accBalance;

                    try {
                        Connection dbConnect = DBConnection.connect();
                        PreparedStatement dbUpdate = dbConnect.prepareStatement("UPDATE accounts SET balance = "+newAccountBalance+" WHERE accNo = '"+accNo+"';");
                        dbUpdate.executeUpdate();
                        dbUpdate.close();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Update failed in 'Accounts Database'\nError Message : " + e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
                        rtn = false;
                    }

                    try {
                        Connection dbConnect = DBConnection.connect();
                        PreparedStatement dbInsert = dbConnect.prepareStatement("INSERT INTO trans (TID, timestp, amount, transType, method, accNo, empNo) VALUES ('"+TID+"', NOW(), "+wBalance+", 'Withdraw', 'Cash', '"+accNo+"', '"+PlaceHolder.loggedEmpID+"');");
                        dbInsert.executeUpdate();
                        dbInsert.close();
                        rtn = true;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error Accessing 'Accounts, Current, Savings Datatbase'\nError Message : " + e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
                        rtn = false;
                    } 
                    
                }

            }
            else if (AccType.equals("cur"))
            {
                try {

                    Connection dbConnect = DBConnection.connect();                    
                    Statement dbQuery = dbConnect.createStatement();
                    ResultSet result = dbQuery.executeQuery("SELECT a.balance, c.odLimit FROM accounts AS a JOIN current AS c ON c.accNO = a.accNO WHERE a.accNo = '"+accNo+"';");

                    while(result.next())
                    {
                        accBalance = result.getFloat("balance");
                        ODLimit = result.getFloat("odLimit");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error Updating 'Transactions Datatbase'\nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    rtn = false;
                }   

                ODLimit = ODLimit * (-1);
                

                if (ODLimit > (accBalance - wBalance))
                {
                    JOptionPane.showMessageDialog(null, "The Withdraw Amount : " + wBalance + "\nThe Withdraw is exceding the Over-Draft Limit.", "Exceding Over-Draft Balance", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    newBalance = accBalance - wBalance;
                    newAccountBalance = newBalance;
                    prevAccountBalance = accBalance;

                    try {
                        Connection dbConnect = DBConnection.connect();
                        PreparedStatement dbUpdate = dbConnect.prepareStatement("UPDATE accounts SET balance = "+newAccountBalance+" WHERE accNo = '"+accNo+"';");
                        dbUpdate.executeUpdate();
                        dbUpdate.close();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Update failed in 'Accounts Database'\nError Message : " + e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
                        rtn = false;
                    }

                    try {
                        Connection dbConnect = DBConnection.connect();
                        PreparedStatement dbInsert = dbConnect.prepareStatement("INSERT INTO trans (TID, timestp, amount, transType, method, accNo, empNo) VALUES ('"+TID+"', NOW(), "+wBalance+", 'Withdraw', 'Cheque', '"+accNo+"', '"+PlaceHolder.loggedEmpID+"');");
                        dbInsert.executeUpdate();
                        dbInsert.close();
                        rtn = true;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Error Updating 'Transactions Datatbase'\nError Message : " + e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
                        rtn =  false;
                    }       
                }   
            }
            rtn = true;
        }
        return rtn;
    }
    
    public boolean checkBalance(String AccNo) {
        boolean rtn = false;
        try {
            Connection dbConnect = DBConnection.connect();
            Statement dbQuery = dbConnect.createStatement();
            ResultSet result = dbQuery.executeQuery("SELECT * FROM customer AS c JOIN accounts AS a ON a.accNo = c.accNO WHERE a.accNo = '"+AccNo+"';");
            
            if (!result.next())
            {
                rtn = false;
            }
            else
            {
                result.beforeFirst();
                while(result.next())
                {
                    cusName = result.getString("name");
                    NICnum = result.getString("cusNIC");
                    accStatus = result.getString("accStatus");
                    accbalance = String.valueOf(result.getFloat("balance"));
                }
                rtn = true;                
            }            
                   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error accessing the 'Accounts, Customer Databases.'\nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            //rtn = false;
        } finally {
            return rtn;
        }
    }   
    
    public boolean transfer(String sourceAccNo, String destiAccNo, float transferAmmonut) {
        boolean rtn = false;
        float accountsPrevBalance = 0;
        float accountNewBalance = 0;
        
        String TID = "2020001";
        String tempTID = "2020001";
        long tNo;
        
        try { // to get the next TID
            Connection dbConnect = DBConnection.connect();
            Statement dbQuery = dbConnect.createStatement();
            ResultSet result = dbQuery.executeQuery("SELECT TID FROM trans;");
            
            while(result.last())
            {
                TID = result.getString("TID");
            }
          
            
            if (TID.equals("X"))
            {
                TID = tempTID;
            }
            else
            {
                tNo = Long.parseLong(TID);
                tNo += 2;
                TID = String.valueOf(tNo);
            }
            
            result.close();
            dbQuery.close();
            dbConnect.close();
            
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Creating the Transaction ID", "Error 01", JOptionPane.ERROR_MESSAGE); 
            rtn = false;
        }
        
        
        try { // getting the balance form the source account
            Connection dbConnect = DBConnection.connect();
            Statement dbQuery = dbConnect.createStatement();
            ResultSet result = dbQuery.executeQuery("SELECT balance FROM accounts WHERE accNo = '"+sourceAccNo+"';");
            
            while(result.next())
            {
                accountsPrevBalance = result.getFloat("balance");                
            }
            
            accountNewBalance = accountsPrevBalance - transferAmmonut;
            newTMAccountBalance = accountNewBalance;
            prevTMAccountBalance = accountsPrevBalance;
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading from 'Accounts Database'\nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            rtn = false;
        }
        
        try { // updating the account table, source accounts record
            Connection dbConnect = DBConnection.connect();
            PreparedStatement dbUpdate = dbConnect.prepareStatement("UPDATE accounts SET balance = "+accountNewBalance+" WHERE accNo = '"+sourceAccNo+"';");
            dbUpdate.executeUpdate();
            dbUpdate.close();
            rtn = true;
                      
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating the 'Accounts Database'\nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            rtn = false;
        }
        
        try { // reading data from the account table for the destination account
            Connection dbConnect = DBConnection.connect();
            Statement dbQuery = dbConnect.createStatement();
            ResultSet result = dbQuery.executeQuery("SELECT balance FROM accounts WHERE accNo = '"+destiAccNo+"';");
            
            while(result.next())
            {
                accountsPrevBalance = result.getFloat("balance");                
            }
            
            accountNewBalance = accountsPrevBalance + transferAmmonut;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error reading from 'Accounts Database'\nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            rtn = false;
        }
        
        try { // updating the account table, destination accounts record
            Connection dbConnect = DBConnection.connect();
            PreparedStatement dbUpdate = dbConnect.prepareStatement("UPDATE accounts SET balance = "+accountNewBalance+" WHERE accNo = '"+destiAccNo+"';");
            dbUpdate.executeUpdate();
            dbUpdate.close();
            rtn = true;
                      
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error updating the 'Accounts Database'\nError Message : " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            rtn = false;
        }
        
        try {
            Connection dbConnect = DBConnection.connect();
            PreparedStatement dbUpdate = dbConnect.prepareStatement("INSERT INTO trans (TID, timeStp, amount, transType, method, accNo, empNo, destiAccNo) VALUES ('"+TID+"', NOW(), '"+transferAmmonut+"', 'Transfer', 'Cash', '"+sourceAccNo+"', '"+PlaceHolder.loggedEmpID+"', '"+destiAccNo+"');");
            dbUpdate.executeUpdate();
            dbUpdate.close();
            rtn = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error inserting to  'Transaction Database'\nError Message : " + e.getMessage(), "Error 02", JOptionPane.ERROR_MESSAGE);
            rtn = false;
        }
        return rtn;
    }
    public abstract boolean printReport();
    public abstract float updateInterest();
    
    
    
    /* getters */
    
    public String getWDNewAccountBalance(){
        return String.valueOf(newAccountBalance);
    }
    
    public String getWDPrevAccountBalance() {
        return String.valueOf(prevAccountBalance);
    }
    
    public String getCustomerName(){
        return cusName;
    }
    
    public String getNIC(){
        return NICnum;
    }
    
    public String getAccountStatus(){
        return accStatus;
    }
    
    public String getBalance() {
        return accbalance;
    }
    
    public String getTMNewBalance() {
        return String.valueOf(newTMAccountBalance);
    }
    
    public String getTMPrevBalance() {
        return String.valueOf(prevTMAccountBalance);
    }
            
    
    
    
    
}
