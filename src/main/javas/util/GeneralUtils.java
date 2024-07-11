package main.javas.util;

public class GeneralUtils {

    public String zippedIDcard(String idCard) {
        return "****" + idCard.substring(12, 16);
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
