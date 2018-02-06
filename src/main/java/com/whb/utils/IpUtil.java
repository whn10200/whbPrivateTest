package com.whb.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
 
public class IpUtil {
	public static String getIp() {
		String localip = null;
		String netip = null;
		try {
			Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			boolean finded = false;
			while ((netInterfaces.hasMoreElements()) && (!finded)) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				Enumeration address = ni.getInetAddresses();
				while (address.hasMoreElements()) {
					ip = (InetAddress) address.nextElement();
					if ((!ip.isSiteLocalAddress()) && (!ip.isLoopbackAddress())
							&& (ip.getHostAddress().indexOf(":") == -1)) {
						netip = ip.getHostAddress();
						finded = true;
						break;
					}
					if ((ip.isSiteLocalAddress()) && (!ip.isLoopbackAddress())
							&& (ip.getHostAddress().indexOf(":") == -1)) {
						localip = ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		if ((netip != null) && (!"".equals(netip))) {
			return netip;
		}
		return localip;
	}

	public static String getLocalIpAddress(String ethName) {
		String retIp = "";
		try {
			Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
				if (ni.getName().toLowerCase().equals(ethName.toLowerCase())) {
					ip = (InetAddress) ni.getInetAddresses().nextElement();
					retIp = ip.getHostAddress();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retIp;
	}

	public static String getLocalIP() {
		String sIP = "";
		InetAddress ip = null;
		try {
			if (isWindowsOS()) {
				ip = InetAddress.getLocalHost();
			} else {
				boolean bFindIP = false;

				Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
				while ((netInterfaces.hasMoreElements()) && (!bFindIP)) {
					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					Enumeration ips = ni.getInetAddresses();
					do {
						if (!ips.hasMoreElements())
							break;
						ip = (InetAddress) ips.nextElement();
					} while ((!ip.isSiteLocalAddress()) || (ip.isLoopbackAddress())
							|| (ip.getHostAddress().indexOf(":") != -1));
					bFindIP = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (null != ip) {
			sIP = ip.getHostAddress();
		}
		return sIP;
	}

	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	public static String getUnixMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ifconfig eth0");

			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			do {
				if ((line = bufferedReader.readLine()) == null)
					break;
				index = line.toLowerCase().indexOf("hwaddr");
			} while (index < 0);
			mac = line.substring(index + "hwaddr".length() + 1).trim();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	public static String getWindowsMACAddress() {
		String mac = null;
		BufferedReader bufferedReader = null;
		Process process = null;
		try {
			process = Runtime.getRuntime().exec("ipconfig /all");

			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = null;
			int index = -1;
			do {
				if ((line = bufferedReader.readLine()) == null)
					break;
				index = line.toLowerCase().indexOf("physical address");
			} while (index < 0);
			index = line.indexOf(":");
			if (index >= 0)
				mac = line.substring(index + 1).trim();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null)
					bufferedReader.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			bufferedReader = null;
			process = null;
		}

		return mac;
	}

	public static String getMACAddress() {
		String retMAC = "";
		if (isWindowsOS())
			retMAC = getWindowsMACAddress();
		else {
			retMAC = getUnixMACAddress();
		}
		return retMAC;
	}

	public static String getStringIp() {
		return getLocalIP().replace(".", "");
	}
}