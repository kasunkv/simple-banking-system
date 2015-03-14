package banksystem;

import com.sun.awt.AWTUtilities;
import java.awt.Window;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {
    
    public static void main(String[] args) {
        
        try {
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            //UIManager.setLookAndFeel(UIManager.setLookAndFeel(javax.swing.plaf.nimbus.NimbusLookAndFeel));
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        new Login().setVisible(true);        

        /*try {
            Login window = new Login();
            window.setUndecorated(false);
            AWTUtilities.setWindowOpacity(window, 0.25f);
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(null, "Error : " + e,"Error", JOptionPane.ERROR_MESSAGE);

        }*/
        

    }

}
