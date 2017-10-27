package com.ys.admin.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;

public class AesSecret {
    
    /**
     * 加密 生成16进制的字符串
     * @param content
     * @return
     * @create_time 2017年3月31日 下午11:48:50
     */
    public static String encrypt(String content) throws Exception{
        return encrypt(content, AesKey.secretKey());
    }
    
    /**
     * 16进制字符串 解密
     * @param content
     * @return
     * @create_time 2017年3月31日 下午11:49:17
     */
    public static String decrypt(String content) throws Exception{
        return decrypt(Hex.hexStr2Bytes(content), AesKey.secretKey());
    }
	
	public static String encrypt(String content, String secureKey) throws Exception{
	    KeyGenerator kgen = KeyGenerator.getInstance("AES");
	    
	    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );  
	    secureRandom.setSeed(secureKey.getBytes());  
	    kgen.init(128,secureRandom);  
	    
	    SecretKey secretKey = kgen.generateKey();
	    byte[] enCodeFormat = secretKey.getEncoded();
	    SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
	    Cipher cipher = Cipher.getInstance("AES");
	    byte[] byteContent = content.getBytes("utf-8");
	    cipher.init(Cipher.ENCRYPT_MODE, key);
	    byte[] result = cipher.doFinal(byteContent);			
	    return Hex.byte2HexStr(result);
	}

	public static String decrypt(byte[] content, String secureKey) throws Exception {
	    KeyGenerator kgen = KeyGenerator.getInstance("AES");
	    SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );  
	    secureRandom.setSeed(secureKey.getBytes());  
	    kgen.init(128,secureRandom);  
	    
	    SecretKey secretKey = kgen.generateKey();
	    byte[] enCodeFormat = secretKey.getEncoded();
	    SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
	    Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, key);
	    byte[] result = cipher.doFinal(content);
	    return new String(result,"utf-8"); 
	}

}
