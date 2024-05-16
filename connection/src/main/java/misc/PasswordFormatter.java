package misc;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class PasswordFormatter {
    public static String codify(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuffer sb = new StringBuffer();

        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    public static String generateRandomPassword(){
        int length=20;
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_+=<>?";

        // Objeto Random para generar valores aleatorios
        Random random = new Random();

        // StringBuilder para construir el string aleatorio
        StringBuilder sb = new StringBuilder(length);

        // Generar caracteres aleatorios y agregarlos al StringBuilder
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(allowedChars.length());
            char randomChar = allowedChars.charAt(randomIndex);
            sb.append(randomChar);
        }
        return codify(sb.toString());
    }
}
