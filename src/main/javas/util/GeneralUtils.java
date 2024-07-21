package main.javas.util;

public class GeneralUtils {

    public String zippedIDcard(String idCard) {
        if (idCard != null && idCard.length() == 16) {
            // Assuming the method is meant to mask or format the credit card number
            // and the original logic starts at index 12 and goes to index 16.
            // Adjust the logic here as per the original intention, ensuring it doesn't exceed the string's bounds.
            return idCard.substring(0, 4) + " **** **** " + idCard.substring(12, 16);
        } else {
            // Handle shorter strings appropriately, possibly logging a warning or error if the length is unexpected.
            return idCard; // Or return a default masked value or an error indicator as appropriate.
        }
    }

    public String zippedPassword(String password) {
        int pwdLength = password.length();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pwdLength; i++) {
            sb.append("*");
        }
        return sb.toString();
    }
}