package projects.asim.recharge.util;

public class CardUtils {

    // ===================================
    // Section: Card Validations & Masking 
    // ===================================
    
    public static boolean allDigitsSame(String s) {

        char first = s.charAt(0);

        for (int i = 1; i < s.length(); i++) {

            if (s.charAt(i) != first)
                return false;

        }

        return true;

    }

    public static boolean passesLuhn(String number) {

        int sum = 0;
        boolean alternate = false;

        for (int i = number.length() - 1; i >= 0; i--) {

            int n = number.charAt(i) - '0';

            if (alternate) {

                n *= 2;

                if (n > 9)
                    n -= 9;

            }

            sum += n;
            alternate = !alternate;

        }

        return (sum % 10 == 0);

    }

    public static String maskCardNumber(String cardNumber) {

        String lastFour = cardNumber.substring(cardNumber.length() - 4);
        return "**** **** **** " + lastFour;

    }

}
