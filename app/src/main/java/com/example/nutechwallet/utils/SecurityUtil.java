package com.example.nutechwallet.utils;


import android.util.Base64;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityUtil {

    public static String encrypt(String key1, String key2, String a) throws Exception {
        byte[] keyMD5 = hashMD5(key1);
        byte[] keySHA256 = hashSHA256(key2);
        byte[] b = encryptAES(keyMD5, keySHA256, a.getBytes("UTF-8"));
        byte[] c = encodeBase64(b);

        return new String(c, "UTF-8");
    }

    public static String decrypt(String key1, String key2, String a) throws Exception {
        byte[] keyMD5 = hashMD5(key1);
        byte[] keySHA256 = hashSHA256(key2);
        byte[] b = decodeBase64(a);
        byte[] c = decryptAES(keyMD5, keySHA256, b);

        return new String(c, "UTF-8");
    }

    public static String encryptURL(String key1, String key2, String a) throws Exception {
        byte[] keyMD5 = hashMD5(key1);
        byte[] keySHA256 = hashSHA256(key2);
        byte[] b = encryptAES(keyMD5, keySHA256, a.getBytes("UTF-8"));
        byte[] c = encodeBase64URL(b);

        return new String(c, "UTF-8");
    }

    public static String decryptURL(String key1, String key2, String a) throws Exception {
        byte[] keyMD5 = hashMD5(key1);
        byte[] keySHA256 = hashSHA256(key2);
        byte[] b = decodeBase64URL(a);
        byte[] c = decryptAES(keyMD5, keySHA256, b);

        return new String(c, "UTF-8");
    }

    public static byte[] encryptFile(String key1, String key2, byte[] a) throws Exception {
        byte[] keyMD5 = hashMD5(key1);
        byte[] keySHA256 = hashSHA256(key2);
        byte[] b = encryptAES(keyMD5, keySHA256, a);

        return b;
    }

    public static byte[] decryptFile(String key1, String key2, byte[] a) throws Exception {
        byte[] keyMD5 = hashMD5(key1);
        byte[] keySHA256 = hashSHA256(key2);
        byte[] b = decryptAES(keyMD5, keySHA256, a);

        return b;
    }

    public static byte[] encryptAES(byte[] ivBytes, byte[] keyBytes, byte[] bytes) throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);

        return cipher.doFinal(bytes);
    }

    public static byte[] decryptAES(byte[] ivBytes, byte[] keyBytes, byte[] bytes)  throws Exception {
        AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        SecretKeySpec newKey = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);

        return cipher.doFinal(bytes);
    }

    // android.util.Base64
    public static byte[] encodeBase64(byte[] str) throws Exception {
        return Base64.encode(str, Base64.DEFAULT);
    }

    // android.util.Base64
    public static byte[] decodeBase64(String str) throws Exception {
        return Base64.decode(str.getBytes("UTF-8"), Base64.DEFAULT);
    }

    public static byte[] encodeBase64URL(byte[] str) throws Exception {
        return com.example.nutechwallet.utils.Base64.getUrlEncoder().encode(str);
    }

    public static byte[] decodeBase64URL(String str) throws Exception {
        return com.example.nutechwallet.utils.Base64.getUrlDecoder().decode(str);
    }

    public static byte[] hashMD5(String keyStr) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("MD5");
        sha.update(keyStr.getBytes());
        byte[] keyBytes = sha.digest();

        return keyBytes;
    }

    public static byte[] hashSHA256(String keyStr) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        sha.update(keyStr.getBytes());
        byte[] keyBytes = sha.digest();

        return keyBytes;
    }

    public static String hashMufins(String input) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        sha.update(input.getBytes("UTF-16"));
        byte bs[] = sha.digest();
        StringBuffer output = new StringBuffer();

        for (int ix = 0; ix < bs.length; ix++) {
            int i;
            byte b = bs[ix];

            if (b > 0)
                i = b;
            else
                i = 256 + b;

            int d = i / 16;

            if (d > 9)
                output.append((char) ('A' + d - 10));
            else
                output.append((char) ('0' + d));

            d = i % 16;

            if (d > 9)
                output.append((char) ('A' + d - 10));
            else
                output.append((char) ('0' + d));
        }
        return output.toString();
    }
}

