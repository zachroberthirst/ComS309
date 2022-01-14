package com.example.frontend.model.helpers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Hash class to hash plaintext and verify hashes are identical
 */
public class HashPassword {
    /**
     * Hashes plaintext into a hashed string, from a given salt.
     * @param plainText plaintext to be hashed
     * @param salt salt to seed hashing
     * @return hashed string
     */
    public static String hash(String plainText, String salt){
        if(plainText == null || salt == null){
            return null;
        }
        //generate password hash
        try {
            //int iterations = 65536;
            int iterations = 1028;
            KeySpec keySpec = new PBEKeySpec(plainText.toCharArray(), salt.getBytes(), iterations, 128);
            //get algorithm to hash
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            //generate hash, using key
            byte[] hashedPassword = keyFactory.generateSecret(keySpec).getEncoded();
            return (new String(hashedPassword));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Generate new random salt for hashing
     * @return new salt
     */
    public static String newSalt() {
        //create RNG Generator
        SecureRandom secureRandom = new SecureRandom();
        //create salt
        byte[] salt = new byte[16];
        //get random 16 byte number
        secureRandom.nextBytes(salt);
        //return salt
        return (new String (salt));
    }

    /**
     * Check if plaintext hash matches stored hash, using given salt
     * @param plainText plaintext to be hashed and checked
     * @param salt salt to seed hashing
     * @param hashedPassword stored hash to compare against
     * @return whether plaintext hash matches stored hash
     */
    public static boolean verify(String plainText, String salt, String hashedPassword){
        if(plainText == null || salt == null || hashedPassword == null){
            return false;
        }
        String one = HashPassword.hash(plainText, salt);
        if (one != null) {
            return one.equals(hashedPassword);
        }
        return false;
    }
}
