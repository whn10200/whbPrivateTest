package com.whb.tuodao;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
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
    
    private static final String ACCESS_ID = "tdFinaceClientAccessId";
    private static final String ACCESS_KEY = "tdFinaceClientAccessKey";
    private static final String REQUEST_TYPE = "2";
    private static final String USER_NO = "TDFINACE_ACCESS_API";

	@SuppressWarnings("unchecked")
	public static <T> String doService(String url, String action, ContentType type, T content) throws Exception{
		String requestUrl = url + "/" +action + "." + type.name().toLowerCase();
		
		Class<BasePojo> parentClass = BasePojo.class;
		Class<T> childClass = (Class<T>) content.getClass();
		if(parentClass.isAssignableFrom(childClass)){
			//只有继承BasePojo类的子类才允许进行设置请求参数
			setFieldValue(content, "accessId", ACCESS_ID);
			setFieldValue(content, "requestType", REQUEST_TYPE);
			setFieldValue(content, "userNo", USER_NO);
			setFieldValue(content, "ip", "");
		} 
		
		// 转换成指定类型
		String requestBody = content(action, content, type);
		headers.set("format", type.name().toLowerCase());

		log.info(requestBody);
		try{

            String md5Sign = Hashing.md5().hashString(requestBody, Charsets.UTF_8).toString().toLowerCase();

			String hmacSha1Sign = HmacSha1UtilSignToString(md5Sign, ACCESS_KEY, Charsets.UTF_8.name());

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
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter 
     * @param object : 子类对象 
     * @param fieldName : 父类中的属性名 
     * @param value : 将要设置的值 
     */  
	public static void setFieldValue(Object object, String fieldName, Object value) {

		// 根据 对象和属性名通过反射 调用上面的方法获取 Field对象
		Field field = getDeclaredField(object, fieldName);
		// 抑制Java对其的检查
		field.setAccessible(true);
		try {
			// 将 object 中 field 所代表的值 设置为 value
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/** 
     * 循环向上转型, 获取对象的 DeclaredField 
     * @param object : 子类对象 
     * @param fieldName : 父类中的属性名 
     * @return 父类中的属性对象 
     */
	public static Field getDeclaredField(Object object, String fieldName) {
		Field field = null;
		Class<?> clazz = object.getClass();
		for (; clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				return field;
			} catch (Exception e) {
				// 这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
				// 如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
			}
		}
		return null;
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
