package com.hanyao.passwordmanager.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by HanYao-Huang on 2016/1/1.
 */
public class SHA256Util {
    public static String Encrypt(String strSrc){
        return Encrypt(strSrc,"SHA-256");
    }
    public static String Encrypt(String strSrc, String encName) {
        MessageDigest md = null;
        StringBuilder sb = new StringBuilder();

        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance(encName);
            byte[] result = md.digest(bt);
            for (byte b : result) {
                sb.append(String.format("%02x", b));
            }
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return sb.toString();
    }
}
