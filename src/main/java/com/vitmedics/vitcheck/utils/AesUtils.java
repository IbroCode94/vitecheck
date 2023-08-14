package com.vitmedics.vitcheck.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;


public class AesUtils {

    private static final int length = 128;

    private static final String AES_KEY_GENERATOR = "AES";
    public static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";

    public static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder().encodeToString(cipherText);
    }

    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static String generateIvString() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return Base64.getEncoder().encodeToString(iv);
    }

    public static IvParameterSpec ivSpecFromString(String base64Iv) {
        byte[] iv = Base64.getDecoder().decode(base64Iv);
        return new IvParameterSpec(iv);
    }

    // size of n (128, 192, and 256) bits
    public static SecretKey generateKey(int keySizeInBits) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_KEY_GENERATOR);
        keyGenerator.init(keySizeInBits);
        SecretKey key = keyGenerator.generateKey();
        return key;
    }

    public static String generateKeyString(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_KEY_GENERATOR);
        keyGenerator.init(n);
        return Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
    }

    public static SecretKey keyFromString(String base64Key) throws NoSuchAlgorithmException {
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, AES_KEY_GENERATOR);
    }
}