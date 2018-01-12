package com.whb.tuodao;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.common.base.Charsets;
import com.google.common.base.Strings;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;

public class HttpUtil {
	
	private static final Logger log = LoggerFactory.getLogger(HttpUtil.class);
	
	private static final String ALGORITHM = "HmacSHA1";
	
    private static final  RestTemplate httpRestTemplate = new RestTemplate();
    
    protected static HttpHeaders headers = new HttpHeaders();

	public static <T> String doService(String url, String action,String accessKey, ContentType type, T content) throws Exception{
		String requestUrl = url + "/" +action + "." + type.name().toLowerCase();
        // 转换成指定类型
		String requestBody = content(action, content, type);
		headers.set("format", type.name().toLowerCase());

		log.info(requestBody);
		try{

            String md5Sign = Hashing.md5().hashString(requestBody, Charsets.UTF_8).toString().toLowerCase();

			String hmacSha1Sign = HmacSha1UtilSignToString(md5Sign, accessKey, Charsets.UTF_8.name());

            headers.set("sign", URLEncoder.encode(hmacSha1Sign, Charsets.UTF_8.name()));

            headers.set("reqlength", String.valueOf(requestBody.length()));
            
		}catch(Exception e){
			throw new RuntimeException(e);
		}

        // post发送json
        HttpEntity<String> entity = new HttpEntity<String>(requestBody, headers);
        ResponseEntity<String> result = httpRestTemplate.exchange(requestUrl, HttpMethod.POST, entity, String.class);
        String resp = result.getBody();
        log.info(resp);
        return resp;
	}

    /**
     * 将content装换为指定类型的字符串
     *
     * @param action    接口名
     * @param content   内容体
     * @param type      类型(JSON, XML)
     * @param <T>       内容体类型
     * @return          转换后字符串
     */
	public static <T> String content(String action, T content, ContentType type) {

        try {

            ReqObject<T> obj = new ReqObject<T>();
            obj.setContent(content);
            ReqCommon common  = new ReqCommon();
            common.setAction(action);
            common.setReqtime(DateTime.now().toString("yyyyMMddHHmmss"));
            obj.setCommon(common);

            switch (type) {
                case XML:
                    headers.setContentType(MediaType.APPLICATION_XML);
                    return new XmlMapper().writer().withRootName("request").writeValueAsString(obj);
                case JSON:
                default:
                    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
                    return new ObjectMapper().writer().withRootName("request").writeValueAsString(obj);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
	
	/**
	 * HmacSHA1签名工具类,根据key和字符集算出data的hash值
	 *
	 * @param data 需要hash的字节
	 * @param key 使用HmacSHA1算法hash用key
	 * @param charsetName 字符集
	 * @return hash后得到的字符串
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws IllegalStateException
	 * @throws UnsupportedEncodingException
	 */
	public static String HmacSha1UtilSignToString(String data, String key, String charsetName)
			throws InvalidKeyException, NoSuchAlgorithmException, IllegalStateException, UnsupportedEncodingException {

		// 取得签名字节数组
		byte[] sign = sign(data, key, charsetName);

		String signStr = BaseEncoding.base64().encode(sign);

		return Strings.nullToEmpty(signStr).trim();
	}

	public static byte[] sign(String data, String key, String charsetName)
			throws NoSuchAlgorithmException, InvalidKeyException, IllegalStateException, UnsupportedEncodingException {

		SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(charsetName), ALGORITHM);
		Mac mac = Mac.getInstance(ALGORITHM);
		mac.init(signingKey);
		return mac.doFinal(data.getBytes(charsetName));
	}
	
	/**
	 * @Description: 传参类型
	 */
	public enum ContentType {
		JSON, XML;
	}
	
	/**
	 * @Description: 操作类型
	 */
	public enum OPERATIONTYPE {
		ADD(1), UPDATE(2);
		
		private Integer type;
		
		OPERATIONTYPE(int type) {
			this.type = type;
		}
		
		public Integer getType() {
			return type;
		}
		
	}
	
}
