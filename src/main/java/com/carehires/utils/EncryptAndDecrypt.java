package com.carehires.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptAndDecrypt {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";  // Use AES with CBC and PKCS5Padding
    private static final String KEY = "ThereIsPasswordEncryptionProcess"; // 32-character key



    public static String encrypt(String password) throws Exception {
        // Generate a random IV (Initialization Vector)
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];  // AES block size is 16 bytes
        secureRandom.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Prepare the cipher
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);

        // Encrypt the password
        byte[] encryptedPassword = cipher.doFinal(password.getBytes());

        // Combine the IV and the encrypted password for storage (you'll need both to decrypt)
        byte[] encryptedPasswordWithIV = new byte[iv.length + encryptedPassword.length];
        System.arraycopy(iv, 0, encryptedPasswordWithIV, 0, iv.length);
        System.arraycopy(encryptedPassword, 0, encryptedPasswordWithIV, iv.length, encryptedPassword.length);

        return Base64.getEncoder().encodeToString(encryptedPasswordWithIV);
    }

    public static String decrypt(String encryptedPassword) throws Exception {
        // Decode the encrypted password
        byte[] encryptedPasswordWithIV = Base64.getDecoder().decode(encryptedPassword);

        // Extract the IV from the beginning of the encrypted data
        byte[] iv = new byte[16];  // AES block size is 16 bytes
        System.arraycopy(encryptedPasswordWithIV, 0, iv, 0, iv.length);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

        // Extract the encrypted password data
        byte[] encryptedPasswordBytes = new byte[encryptedPasswordWithIV.length - iv.length];
        System.arraycopy(encryptedPasswordWithIV, iv.length, encryptedPasswordBytes, 0, encryptedPasswordBytes.length);

        // Prepare the cipher
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

        // Decrypt the password
        byte[] decryptedPassword = cipher.doFinal(encryptedPasswordBytes);

        return new String(decryptedPassword);
    }

    public static void main(String[] args) throws Exception {
        String text = "XXXXXXXXXXXXXX";
        String encryptedPassword = encrypt(text);
        System.out.println(encryptedPassword);
    }
}
