package com.softech.ls360.util.network;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsConnectionUtil extends ConnectionUtil {

	public static HttpsURLConnection getHttpsConnection(String url) throws Exception {
		
		HttpsURLConnection urlConnection = null;
		
		// Create a trust manager that does not validate certificate chains
	    final TrustManager[] trustAllCerts = new TrustManager[] { 	
	    		new X509TrustManager() {   	
	    			public void checkClientTrusted( final X509Certificate[] chain, final String authType ) {
	    				//do nothing
	                }
	        
	                public void checkServerTrusted( final X509Certificate[] chain, final String authType ) {
	                	//do nothing
	                }
	        
	                public X509Certificate[] getAcceptedIssuers() {
	                    return null;
	                }
	    		}		
	    } ;
	    
	    // Install the all-trusting trust manager
	    final SSLContext sslContext = SSLContext.getInstance("SSL");
	    
	    sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
	    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
	    	 
	    // Create all-trusting host name verifier
	    HostnameVerifier allHostsValid = new HostnameVerifier() {            
	    	public boolean verify(String hostname, SSLSession session) {               
	    		return true;         
	    	}	        
	    };
	
	    // Install the all-trusting host verifier
	    HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
		try {
			URL clientUrl = new URL(url);
			urlConnection = (HttpsURLConnection) clientUrl.openConnection();
			
			// Tell the url connection object to use our socket factory which bypasses security checks
		    //( (HttpsURLConnection) urlConnection ).setSSLSocketFactory( sslSocketFactory );
			
		} catch (Exception e) {
			throw new Exception("Error in getting connecting to url: " + url + " :: " + e.getMessage());	
		}
		
		return urlConnection;
		
	} //end of getHttpsConnection()
	
	public static boolean isRedirect(HttpsURLConnection conn) throws Exception {
		
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
	
	public static String getResponseAsString(String certificateFilePath, String httpsUrl) throws Exception {
		
		URL url = new URL(httpsUrl);            
        SSLExcludeCipherConnectionHelper sslExclHelper = new SSLExcludeCipherConnectionHelper(certificateFilePath);
        String response = sslExclHelper.getResponseAsString(url);
        return response;
		
	}
	
	public static InputStream getResponseAsInputStream(String certificateFilePath, String httpsUrl) throws Exception {
		
		URL url = new URL(httpsUrl);            
        SSLExcludeCipherConnectionHelper sslExclHelper = new SSLExcludeCipherConnectionHelper(certificateFilePath);
        InputStream response = sslExclHelper.getResponseAsInputStream(url);
        return response;
		
	}
	
	public static InputStream getHttpsURLContent(String urlStr, String input) throws Exception {
		
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
			HttpsURLConnection connection = getHttpsConnection(urlStr);
			return getURLContent(connection, input);
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Error in sending Post: " + e.getMessage());
		}
	}
	
}
