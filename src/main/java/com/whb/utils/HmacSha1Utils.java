package com.whb.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.io.BaseEncoding;

/**
 * HmacSHA1签名工具类。
 *
 * @author xieyushi
 * @version 1.0
 * @Created on 2015-02-11
 */
public class HmacSha1Utils {

    private static final String ALGORITHM = "HmacSHA1";

    private static final Logger log = LoggerFactory.getLogger(HmacSha1Utils.class);

    /**
     * 根据key和字符集算出data的hash值
     *
     * @param data        需要hash的字节
     * @param key         使用HmacSHA1算法hash用key
     * @param charsetName 字符集
     * @return hash后得到的字节数组
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws IllegalStateException
     * @throws UnsupportedEncodingException
     */
    public static byte[] sign(String data, String key, String charsetName)
            throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {

        SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(charsetName), ALGORITHM);
        Mac mac = Mac.getInstance(ALGORITHM);
        mac.init(signingKey);

        return mac.doFinal(data.getBytes(charsetName));
    }

    /**
     * 根据key和字符集算出data的hash值
     *
     * @param data        需要hash的字节
     * @param key         使用HmacSHA1算法hash用key
     * @param charsetName 字符集
     * @return hash后得到的字符串
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws IllegalStateException
     * @throws UnsupportedEncodingException
     */
    public static String signToString(String data, String key, String charsetName)
            throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {

        // 取得签名字节数组
        byte[] sign = sign(data, key, charsetName);

        String signStr = BaseEncoding.base64().encode(sign);

        return Strings.nullToEmpty(signStr).trim();
    }

    /**
     * 生成accessId
     *
     * @param data
     * @return add zkai
     */
    public static String createAccessId(String data) {
        return MD5Utils.md5(CommonUtils.encodeBase64String(data));

    }

    /**
     * 生成accessKey
     *
     * @param accessid
     * @return add zkai
     */
    public static String createAccessKey(String accessid) {
        return CommonUtils.encodeBase64String(CommonUtils.getRandom(accessid)).toLowerCase();
    }

    public static boolean signCheck(String data, String key, String sign, String charsetName) {
        try {
            String actual = signToString(data, key, charsetName);
            log.debug("signCheck/actual:[{}][{}]", actual, sign);
            return actual.equals(sign);
        } catch (Exception e) {
            log.error("签名错误", e);
            return false;
        }

    }

    public static void main(String[] args) {
        String accessId = createAccessId("18868807380_app");
        System.out.println("accessId:" + accessId);
        System.out.println(CommonUtils.decodeBase64String("MTg4Njg4MDczODBfYXBw"));
        String accessKey = createAccessKey(accessId);
        System.out.println("accessKey:" + accessKey);
    }

}
