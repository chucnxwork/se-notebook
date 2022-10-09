package com.chucnx.socketcs.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesEncryption {
	private static final String KEY = "Bar12345Bar12345"; // 128 bit key
	private static final String INIT_VECTOR = "RandomInitVector"; // 16 bytes IV
	
	private static boolean isNullOrEmpty(String value) {
		return (value == null || value.length() == 0);
	}
	
	private static boolean isNullOrEmptyOrBlank(String value) {
		return (value == null || value.length() == 0 || isNullOrEmpty(value.trim()));
	}
	
	public static String encrypt(String key, String initVector, String value) {
		if(isNullOrEmptyOrBlank(key) || isNullOrEmptyOrBlank(initVector) || 
				isNullOrEmptyOrBlank(value)) {
			return null;
		}
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());

            return java.util.Base64.getUrlEncoder().encodeToString(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String key, String initVector, String encrypted) {   
    	if(isNullOrEmptyOrBlank(key) || isNullOrEmptyOrBlank(initVector) || 
				isNullOrEmptyOrBlank(encrypted)) {
			return null;
		}
        try {
	    	IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	
	        byte[] original = cipher.doFinal(java.util.Base64.getUrlDecoder().decode(encrypted));
	
	        return new String(original);
	    } catch (Exception ex) {
	    	return null;
	    }        
    }
    
    public static String encrypt(String value) {
    	return encrypt(KEY, INIT_VECTOR, value);
    }
    
    public static String decrypt(String value) {
    	return decrypt(KEY, INIT_VECTOR, value);
    }

}
