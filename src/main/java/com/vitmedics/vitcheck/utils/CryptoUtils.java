package com.vitmedics.vitcheck.utils;

import java.security.MessageDigest;

public class CryptoUtils {

    public static String getMd5Hash(String input) {
        try {
            byte[] bytesOfMessage = input.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] thedigest = md.digest(bytesOfMessage);
            return thedigest.toString();
        }
        catch(Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
