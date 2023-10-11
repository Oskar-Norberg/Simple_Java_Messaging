/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package slutprojekt;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author oskar.norberg
 */
public class PasswordFunctions {

    public static final int SALT_LENGTH = 16, ITERATION_COUNT = 65536, KEY_LENGTH = 128;
    public static final String ENCRYPTION_ALGORITHM = "PBKDF2WithHmacSHA1";

    public static String hashPassword(String password) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return genHash(password, salt);
    }

    public static boolean checkPasswordHash(String hash, String password) {
        byte[] decodedSalt = Base64.getDecoder().decode(hash.substring(0, 24));
        return genHash(password, decodedSalt).equals(hash);
    }

    private static String genHash(String password, byte[] salt) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        SecretKeyFactory f;
        byte[] hash = null;
        try {
            f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = f.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(PasswordFunctions.class.getName()).log(Level.SEVERE, null, ex);
        }
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(salt).concat(enc.encodeToString(hash));
    }
}
