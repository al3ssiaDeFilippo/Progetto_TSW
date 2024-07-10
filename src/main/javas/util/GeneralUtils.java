package main.javas.util;

public class GeneralUtils {

    public String zippedIDcard(String idCard) {
        return "****" + idCard.substring(12, 16);
    }
}
