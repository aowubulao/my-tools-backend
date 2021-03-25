package com.neoniou.tools.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * @author Neo.Zzj
 */
public class IpUtil {

	private static final String LOCALHOST = "127.0.0.1";
	private static final String UNKNOWN = "unknown";
	private static final String SPLIT = ",";
	private static final int MAX_LENGTH = 15;

	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress;
		try {
			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (LOCALHOST.equals(ipAddress)) {
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					assert inet != null;
					ipAddress = inet.getHostAddress();
				}
			}
			if (ipAddress != null && ipAddress.length() > MAX_LENGTH) {
				if (ipAddress.indexOf(SPLIT) > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
			}
		} catch (Exception e) {
			ipAddress = "";
		}

		return ipAddress;
	}

}