package com.whb.mq.util;

import com.alibaba.fastjson.JSONObject;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import org.apache.commons.lang.StringUtils;

/**
 * @author whb
 * @date 2017年11月15日 上午10:08:16 
 * @Description: 工具类
 */
public class CommonUtil {
	private static String[] chars = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
			"q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
			"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
			"W", "X", "Y", "Z" };

	public static boolean assertSuccess(List<String> retList) {
		boolean ret = false;
		if ((retList.size() > 0) && (Integer.valueOf((String) retList.get(0)).intValue() >= 0)) {
			ret = true;
		}
		return ret;
	}

	public static String getSerialNo() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}

	public static String getSessionId(String stbId) {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "") + stbId.substring(stbId.length() - 6);
	}

	public static String getModulePropertiesPath(String moduleName) throws IllegalAccessException {
		StringBuffer sb = new StringBuffer("");
		sb.append("module");
		sb.append("/");
		sb.append(moduleName);
		sb.append("/");
		sb.append(moduleName);
		sb.append(".properties");
		return sb.toString();
	}

	public static StringBuffer getModuleXmlPath(String moduleName) throws IllegalAccessException {
		StringBuffer sb = new StringBuffer("");
		sb.append("module");
		sb.append("/");
		sb.append(moduleName);
		sb.append("/");
		sb.append(moduleName);
		sb.append(".xml");
		return sb;
	}

	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[(x % 62)]);
		}
		return shortBuffer.toString();
	}

	public static List<String> getRealIpList() {
		List hostRealIpList = new ArrayList();
		Collection<InetAddress> colInetAddress = getAllHostAddress();
		for (InetAddress address : colInetAddress) {
			if ((!address.isLoopbackAddress()) && (address.getHostAddress().split("\\.").length == 4)
					&& (!address.getHostAddress().equals("127.0.0.1"))) {
				hostRealIpList.add(address.getHostAddress());
			}
		}
		return hostRealIpList;
	}

	public static Collection<InetAddress> getAllHostAddress() {
		try {
			Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
			Collection addresses = new ArrayList();

			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
				Enumeration inetAddresses = networkInterface.getInetAddresses();
				while (inetAddresses.hasMoreElements()) {
					InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
					addresses.add(inetAddress);
				}
			}
			return addresses;
		} catch (SocketException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Properties loadFile(String file) throws IOException {
		try {
			FileInputStream fin = new FileInputStream(file);
			Properties p = new Properties();
			p.load(fin);
			return p;
		} catch (FileNotFoundException localFileNotFoundException) {
		}
		return null;
	}

	public static int getIntPropertyValue(Properties p, String name, int def) {
		String value = getStringPropertyValue(p, name);
		return isNullorEmptyString(value) ? def : Integer.parseInt(value);
	}

	public static String getStringPropertyValue(Properties p, String name) {
		return getStringPropertyValue(p, name, "");
	}

	public static String getStringPropertyValue(Properties p, String name, String def) {
		String value = p.getProperty(name);
		if (isNullorEmptyString(value)) {
			return isNullorEmptyString(def) ? "" : def;
		}
		return value;
	}

	public static String[] splitString(String str) {
		return splitString(",", 0);
	}

	public static String[] splitString(String str, int limit) {
		return str.split(",", limit);
	}

	public static boolean isNullorEmptyString(String str) {
		return (str == null) || (str.equals(""));
	}

	public static String getThisDayStr() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
		return sdf.format(cal.getTime());
	}

	public static String getProperty(JSONObject json, String property, String defalut) {
		String retMes = null;
		if (json.containsKey(property))
			retMes = json.getString(property);
		if (StringUtils.isBlank(retMes))
			retMes = defalut;
		return retMes;
	}

	public static String getPid() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		System.out.println(name);

		String pid = name.split("@")[0];
		return pid;
	}

	public static void main(String[] args) {
		System.out.println(getPid());
	}
}