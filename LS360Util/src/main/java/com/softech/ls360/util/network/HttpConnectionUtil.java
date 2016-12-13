package com.softech.ls360.util.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnectionUtil extends ConnectionUtil {

	public static HttpURLConnection getHttpConnection(String url) throws Exception {
		
		HttpURLConnection urlConnection = null;
		try {
			URL myUrl = new URL(url);
			urlConnection = (HttpURLConnection) myUrl.openConnection();
		} catch (Exception e) {
			throw new Exception("Error in getting connecting to url: " + url + " :: " + e.getMessage());
		}
		return urlConnection;
	} //end of getHttpConnection()
	
	public static boolean isRedirect(HttpURLConnection conn) throws Exception {
		
		boolean redirect = false;
		
		// normally, 3xx is redirect
		int status = conn.getResponseCode();
		if (status != HttpURLConnection.HTTP_OK) {
			if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER) {
				redirect = true;
			}	
		}
		System.out.println("Response Code ... " + status);
		return redirect;
	}
	
	public static InputStream getHttpURLContent(String urlStr, String input) throws Exception {
		
		try  {
			
			/**
			 * URLConnection is an abstract class and you cannot create its object directly. You need to use the 
			 * openConnection() method of the URL object to get a URLConnection object. The URL class will handle
			 * the creation of an URLConnection object, which will be appropriate to handle the data for the 
			 * protocol used in the URL.
			 * 
			 * The openConnection() method of the URL class returns a URLConnection object, which is not 
			 * connected to the URL source yet. You must set all connection-related parameters to this object 
			 * before it is connected. For example, if you want to write data to the URL, you must call the 
			 * setDoOutput(true) method on the connection object before it is connected. 
			 */
			//URLConnection connection = url.openConnection();
			HttpURLConnection connection = getHttpConnection(urlStr);
			return getURLContent(connection, input);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Error in sending Post: " + e.getMessage());
		}
	}
	
}
