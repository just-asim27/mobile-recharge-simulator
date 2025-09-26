package projects.asim.recharge.util;

public class MobileNumberUtils {

    // ========================================================
    // Section: Mobile Number Masking & SIM Opearator Detection 
    // ========================================================

    public static String maskMobileNumber(String mobile) {

        return mobile.substring(0, 4) + "****" + mobile.substring(mobile.length() - 3);

    }
    
    public static String detectSimOperator(String mobileNumber) {

        char x = mobileNumber.charAt(2);

        switch (x) {

            case '0':
            case '2': return "Jazz";
            case '1': return "Zong";
            case '3': return "Ufone";
            case '4': return "Telenor";
            default: return "Unknown Operator";

        }

    }
    
}
