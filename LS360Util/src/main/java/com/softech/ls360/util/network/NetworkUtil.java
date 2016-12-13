package com.softech.ls360.util.network;

import java.io.InputStream;
import java.net.HttpURLConnection;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtil {
	
	public static HttpURLConnection getHttpConnection(String url) throws Exception {
		return HttpConnectionUtil.getHttpConnection(url);
	} 

	public static HttpsURLConnection getHttpsConnection(String url) throws Exception {
		return HttpsConnectionUtil.getHttpsConnection(url);
	} 
	
	public static boolean isRedirect(HttpURLConnection conn) throws Exception {
		return HttpConnectionUtil.isRedirect(conn);
	}
	
	public static boolean isRedirect(HttpsURLConnection conn) throws Exception {
		return HttpsConnectionUtil.isRedirect(conn);
	}
	
	public static boolean isSecureConnection(String url) {
		
		boolean secureConnection = true;
		if (url.startsWith("http") || url.startsWith("HTTP")) {
			secureConnection = false;
		}
		return secureConnection;
	}
	
	public static InputStream getHttpURLContent(String urlStr, String input) throws Exception {
		return HttpConnectionUtil.getHttpURLContent(urlStr, input);
	}
	
	public static InputStream getHttpsURLContent(String urlStr, String input) throws Exception {
		return HttpsConnectionUtil.getHttpsURLContent(urlStr, input);
	}
	
	public static String getResponseAsString(String certificateFilePath, String httpsUrl) throws Exception {
		return HttpsConnectionUtil.getResponseAsString(certificateFilePath, httpsUrl);
	}
	
	public static InputStream getResponseAsInputStream(String certificateFilePath, String httpsUrl) throws Exception {
		return HttpsConnectionUtil.getResponseAsInputStream(certificateFilePath, httpsUrl);
	}
	
}
