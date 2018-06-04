package com.whb.dataSecurity;


import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DESCoderFUIOU
{
  private static String CHATSET = "UTF-8";
  
  public static String desEncrypt(String input, String keystr)
    throws Exception
  {
    try
    {
      byte[] datasource = input.getBytes(CHATSET);
      SecureRandom random = new SecureRandom();
      DESKeySpec desKey = new DESKeySpec(keystr.getBytes(CHATSET));
      
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
      SecretKey securekey = keyFactory.generateSecret(desKey);
      
      Cipher cipher = Cipher.getInstance("DES");
      
      cipher.init(1, securekey, random);
      

      return new BASE64Encoder().encode(cipher.doFinal(datasource));
    }
    catch (Throwable e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static String desDecrypt(String cipherText, String strkey)
    throws Exception
  {
    SecureRandom random = new SecureRandom();
    
    DESKeySpec desKey = new DESKeySpec(strkey.getBytes(CHATSET));
    
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    
    SecretKey securekey = keyFactory.generateSecret(desKey);
    
    Cipher cipher = Cipher.getInstance("DES");
    
    cipher.init(2, securekey, random);
    
    BASE64Decoder decoder = new BASE64Decoder();
    

    return new String(cipher.doFinal(decoder.decodeBuffer(cipherText)), CHATSET);
  }
  
  public static String padding(String str)
  {
    try
    {
      byte[] oldByteArray = str.getBytes(CHATSET);
      int numberToPad = 8 - oldByteArray.length % 8;
      byte[] newByteArray = new byte[oldByteArray.length + numberToPad];
      System.arraycopy(oldByteArray, 0, newByteArray, 0, oldByteArray.length);
      for (int i = oldByteArray.length; i < newByteArray.length; i++) {
        newByteArray[i] = 0;
      }
      return new String(newByteArray, CHATSET);
    }
    catch (UnsupportedEncodingException e)
    {
      System.out.println("Crypter.padding UnsupportedEncodingException");
    }
    return null;
  }
  
  public static byte[] removePadding(byte[] oldByteArray)
  {
    int numberPaded = 0;
    for (int i = oldByteArray.length; i >= 0; i--) {
      if (oldByteArray[(i - 1)] != 0)
      {
        numberPaded = oldByteArray.length - i;
        break;
      }
    }
    byte[] newByteArray = new byte[oldByteArray.length - numberPaded];
    System.arraycopy(oldByteArray, 0, newByteArray, 0, newByteArray.length);
    
    return newByteArray;
  }
  
  public static String getKey(String name)
  {
    StringBuffer buffer = new StringBuffer();
    buffer.append(MD5.MD5Encode(MD5.MD5Encode(name + "fuiou"))).append(MD5.MD5Encode(name + "20160112")).append(MD5.MD5Encode(name + "mpay")).append(MD5.MD5Encode(MD5.MD5Encode(name)));
    return buffer.toString();
  }
  
  public static String getKeyLength8(String key)
  {
    key = key == null ? "" : key.trim();
    int tt = key.length() % 64;
    
    String temp = "";
    for (int i = 0; i < 64 - tt; i++) {
      temp = temp + "D";
    }
    return key + temp;
  }
}

