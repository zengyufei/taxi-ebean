package com.zzsim.taxi.admin.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class IpHelper {
	private static final Logger logger = Logger.getLogger("IpHelper");

	private static String LOCAL_IP_STAR_STR = "192.168.";
	public static final String LOCAL_IP;
	public static final String HOST_NAME;
	
	
	public static String getReferer(HttpServletRequest request){
	    return request.getHeader("Referer");
	}
	
	public static String getUserAgent(HttpServletRequest request){
	    return request.getHeader("User-Agent");
	}

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if ((ip == null) || (ip.length() == 0) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equals(ip.trim())) {
            ip = request.getHeader("X-Real-IP");
        }
		if (StringUtils.isBlank(ip) || ("unknown".equalsIgnoreCase(ip))) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1")) {
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
					ip = inet.getHostAddress();
				} catch (UnknownHostException e) {
					logger.severe("IpHelper error." + e.toString());
				}

			}

		}

		if ((ip != null) && (ip.length() > 15) && (ip.indexOf(",") > 0)) {
			ip = ip.substring(0, ip.indexOf(","));
		}

		return ip;
	}

	static {
		String ip = null;
		String hostName = null;
		try {
			hostName = InetAddress.getLocalHost().getHostName();
			InetAddress[] ipAddr = InetAddress.getAllByName(hostName);
			for (int i = 0; i < ipAddr.length; ++i) {
				ip = ipAddr[i].getHostAddress();
				if (ip.startsWith(LOCAL_IP_STAR_STR)) {
					break;
				}
			}
			if (ip == null)
				ip = ipAddr[0].getHostAddress();
		} catch (UnknownHostException e) {
			logger.severe("IpHelper error.");
			e.printStackTrace();
		}

		LOCAL_IP = ip;
		HOST_NAME = hostName;
	}
}