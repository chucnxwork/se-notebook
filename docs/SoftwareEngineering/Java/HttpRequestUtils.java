/**
 * 
 */
package com.chucnx.socketcs.utils;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

public class HttpRequestUtils {
	public static CustomHttpResponse sendHttpRequest(String url, String method, HashMap<String, String> params, String data) {
		HttpURLConnection connection = null;

		try {
			// Create connection
			URL urlObj = new URL(url);
			connection = (HttpURLConnection) urlObj.openConnection();

			System.out.println("Sending request: "+url);
			
			if (method != null && !"get".equalsIgnoreCase(method)) {
				connection.setRequestMethod(method);
			} else {
				connection.setRequestMethod("GET");
			}

			/*
			 * connection.setRequestProperty("Content-Type",
			 * "application/x-www-form-urlencoded");
			 */

			/*
			 * connection.setRequestProperty("Content-Length",
			 * Integer.toString(urlParameters.getBytes().length));
			 * connection.setRequestProperty("Content-Language", "en-US");
			 */

			if (params != null && !params.isEmpty()) {
				Set<String> keySet = params.keySet();
				for(String key: keySet) {
					if(Utils.isNullOrEmpty(key)) {
						continue;
					}
					String value = params.get(key);
					connection.setRequestProperty(key, value);
				}
			}

			connection.setUseCaches(false);
			
			// Send request
			if ("POST".equals(method) || "post".equals(method)) {
				connection.setDoOutput(true);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
				if (data != null) {
					bw.write(data);
				}
				bw.flush();
				bw.close();
			}

			// Get Response
			InputStream is = connection.getInputStream();
			String contentType = connection.getContentType();
			byte[] media = Utils.inputStreamToByte(is);
						
			//System.out.println("response: "+new String(media));
			
			CustomHttpResponse response = new CustomHttpResponse();
			response.setBody(media);
			response.setContentType(contentType);
			
			return response;
		} catch (IOException e) {
			try {
				InputStream is = connection.getErrorStream();				
				byte[] media = Utils.inputStreamToByte(is);				
				String contentType = connection.getContentType();
				
				CustomHttpResponse response = new CustomHttpResponse();
				response.setBody(media);
				response.setContentType(contentType);
				
				return response;
			} catch (Exception e2) {
				e2.printStackTrace();
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}
