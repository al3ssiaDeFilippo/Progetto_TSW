package main.javas.util;

import main.javas.bean.UserBean;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordUtils {

    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return new BigInteger(1, salt).toString(16);
    }

    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            String input = password + salt;
            byte[] messageDigest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashtext = new StringBuilder(no.toString(16));
            while (hashtext.length() < 64) {
                hashtext.insert(0, "0");
            }
            return hashtext.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String bytesToHex(byte[] bytes) {
        StringBuilder hashtext = new StringBuilder();
        for (byte b : bytes) {
            hashtext.append(String.format("%02x", b));
        }
        return hashtext.toString();
    }

    public static boolean verifyPassword(UserBean user, String oldPassword) {
        // Get the hashed password and salt from the user
        String hashedPassword = user.getPassword();
        String salt = user.getSalt();

        // Generate a hashed version of the input password
        String hashedInputPassword = PasswordUtils.hashPassword(oldPassword, salt);

        // Compare the hashed input password with the user's hashed password
        return hashedPassword.equals(hashedInputPassword);
    }

}