package com.softech.ls360.util.network;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public abstract class ConnectionUtil {

	protected static InputStream getURLContent(URLConnection connection, String input) throws Exception {
		
		try  {
			
			/**
			 * If you are writing as well as reading data from a URL, you must call the setDoOutput(true) before 
			 * you connect.
			 */
			connection.setDoOutput(true);
			
			/**
			 * A URLConnection object gets connected when you call its connect() method. However, it is 
			 * connected implicitly when you call its methods that require a connection. For example, writing 
			 * data to a URL and reading the URL’s data or header fields will connect the URLConnection object 
			 * automatically, if it is not already connected.
			 */
			// Now, connect to the remote object
			connection.connect();
			
			/**
			 * You must finish writing the data to the URL before you start reading the data. Writing data to a 
			 * URL will change the request method to POST. You cannot even get the input stream before you finish
			 * writing data to the URL.
			 */
			OutputStream ous = connection.getOutputStream();
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(ous));
			bw.write(input);
			bw.flush();
			bw.close();
			
			/**
			 * Must be placed after writing the data. Otherwise, it will result in error, because if write is 
			 * performed, read must be performed after the write
			 */
			//printRequestHeaders(connection);
			InputStream ins = connection.getInputStream();
			return ins;
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Error in sending Post: " + e.getMessage());
		}
	}
	
	protected static void printRequestHeaders(URLConnection connection) {
		Map<String, List<String>> headers = connection.getHeaderFields();
		System.out.println("Request Headers are:");
		System.out.println(headers);
		System.out.println();
	}
	
}
