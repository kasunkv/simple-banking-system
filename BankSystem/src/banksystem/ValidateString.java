
package banksystem;

public class ValidateString {
    private String string1;
    private String string2;
    
    public ValidateString(String str1, String str2){
        this.string1 = str1;
        this.string2 = str2;
        
    }
    
    public static boolean equality(String str1, String str2) {
        if (str1.equals(str2))
            return true;                
        else
            return false;     
    }
    
    public static boolean length(String str, int n) {
        if (str.length() == n)
            return true;
        else
            return false;
    }
    
    public static boolean charAt(String str, int n, char c) {
        if (str.indexOf((int)c) == n)
            return true;
        else
            return false;
    }
    
}
