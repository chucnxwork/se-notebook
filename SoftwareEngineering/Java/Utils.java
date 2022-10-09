package com.chucnx.socketcs.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	private static Logger logger = Logger.getLogger(Utils.class);

	public static boolean isNullOrEmpty(String value) {
		return (value == null || value.length() == 0 || value.trim() == null || value.trim().length() == 0);
	}

	public static boolean isLessThanZero(float value) {
		return (value <= 0);
	}

	public static String setBlankIfNull(String value) {
		if (isNullOrEmpty(value)) {
			return "";
		}
		return value;
	}
	
	public static boolean checkLength(String value,int length) {
		if (value.isEmpty() || value.length() >= length || value.contains(" ")) {
			return true;
		}
		return false;
	}

	public static String getAlphaNumeric(String val) {
		if (isNullOrEmpty(val)) {
			return "";
		}
		return val.replaceAll("[^a-zA-Z0-9]+", "");
	}

	public static String getAlphaNumericDot(String val) {
		if (isNullOrEmpty(val)) {
			return "";
		}
		return val.replaceAll("[^a-zA-Z0-9.]+", "");
	}

	public static String readPropertyValue(String propertyFileName, String key) {
		try {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			InputStream is = classloader.getResourceAsStream(propertyFileName);

			Properties properties = new Properties();
			properties.load(is);
			return properties.getProperty(key);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String inputStreamToString(InputStream is) throws IOException {
		final int bufferSize = 1024;
		final char[] buffer = new char[bufferSize];
		final StringBuilder out = new StringBuilder();
		Reader in = new InputStreamReader(is, "UTF-8");
		for (;;) {
			int rsz = in.read(buffer, 0, buffer.length);
			if (rsz < 0)
				break;
			out.append(buffer, 0, rsz);
		}
		return out.toString();
	}
	
	public static byte[] inputStreamToByte(InputStream is) throws IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		while ((nRead = is.read(data, 0, data.length)) != -1) {
		  buffer.write(data, 0, nRead);
		}

		return buffer.toByteArray();
	}

	public static Date getCurrentTime() {
		Date date = new Date();
		return date;
	}

	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	public static String getShortUUID() {
		UUID uuid = UUID.randomUUID();
		// String s = Long.toString(uuid.getMostSignificantBits(), 36) + '-' +
		// Long.toString(uuid.getLeastSignificantBits(), 36);
		long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
		return Long.toString(l, Character.MAX_RADIX);
	}

	// public static String generateRandomCssClass(String gadgetId) {
	// String shortUUID = getShortUUID();
	// gadgetId = gadgetId.replace(".", "");
	// return gadgetId+shortUUID;
	// }

	public static boolean deleteFolder(String path) {
		boolean check = false;
		File directory = new File(path);

		// make sure directory exists
		if (!directory.exists()) {
			logger.info("Directory does not exist.");

			check = false;

		} else {

			try {

				deleteFile(directory);

			} catch (IOException e) {
				check = false;
				logger.info("Error deleteFolder:" + e.toString());
				e.printStackTrace();

			}
		}

		return check;
	}

	public static void deleteFile(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				logger.info("Directory is deleted : " + file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					deleteFile(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();

					logger.info("Directory is deleted : " + file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			logger.info("Directory is deleted : " + file.getAbsolutePath());
		}
	}

	/**
	 * 
	 * @return
	 */
	public static String getResourcePath() {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String dir = classLoader.getResource("").getFile(); // = FileStorageService.class.getResource("/").getFile();
		return dir.substring(1);
	}

	public static String replaceString(String value, int beginIndex, int endIndex, String replaceString) {
		if (isNullOrEmpty(value)) {
			return "";
		}
		if (beginIndex < 0 || endIndex < 0 || endIndex < beginIndex || beginIndex >= value.length()) {
			return "";
		}

		StringBuilder builder = new StringBuilder();
		builder.append(value.substring(0, beginIndex));
		builder.append(replaceString);
		if (endIndex < value.length()) {
			builder.append(value.substring(endIndex + 1, value.length()));
		}
		String result = builder.toString();
		return result;
	}

	public static String listToString(List<String> list) {
		String result = "";
		if (list == null || list.isEmpty()) {
			return result;
		}
		StringBuilder builder = new StringBuilder();
		for (String line : list) {
			builder.append(line);
		}
		result = builder.toString();
		return result;
	}

	public static boolean isUrlMatch(String url, String pattern) {
		if (isNullOrEmpty(url) || isNullOrEmpty(pattern)) {
			return false;
		}

		pattern = pattern.replace("*", ".*");
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(url);

		if (m.matches()) {
			return true;
		}

		pattern = pattern.endsWith("/") ? (pattern) : (pattern + "/");
		url = url.endsWith("/") ? (url) : (url + "/");
		if (pattern.equals(url)) {
			return true;
		}

		return false;
	}

	public static boolean isUrlMatch(String url, List<String> patterns) {
		if (isNullOrEmpty(url) || patterns.isEmpty()) {
			return false;
		}
		for (String pattern : patterns) {
			if (isNullOrEmpty(pattern)) {
				continue;
			}
			pattern = pattern.replace("*", ".*");
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(url);

			if (m.matches()) {
				return true;
			}

			pattern = pattern.endsWith("/") ? (pattern) : (pattern + "/");
			url = url.endsWith("/") ? (url) : (url + "/");
			if (pattern.equals(url)) {
				return true;
			}
		}
		return false;
	}

	public static String mapToJsonString(Map<String, Object> map) {
		if (map == null) {
			return "{}";
		}
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			json = "{}";
		}

		// StringBuilder builder = new StringBuilder("{");
		// String result;
		// if (map == null || map.size() == 0) {
		// builder.append("}");
		// result = builder.toString();
		// return result;
		// }
		// for (String key : map.keySet()) {
		// builder.append(key + ":\"" + map.get(key) + "\",");
		// }
		// builder.deleteCharAt(builder.length() - 1);
		// builder.append("}");
		// result = builder.toString();
		return json;
	}
	
	public static String objectToJsonString(Object obj) {
		if (obj == null) {
			return "{}";
		}
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			json = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			json = "{}";
		}

		return json;
	}

	public static String sendHttpRequest(String url, String method, HashMap<String, String> params, String data) {
		HttpURLConnection connection = null;

		try {
			// Create connection
			URL urlObj = new URL(url);
			connection = (HttpURLConnection) urlObj.openConnection();

			if (method != null && !"get".equalsIgnoreCase(method)) {
				connection.setRequestMethod(method);
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
				for (String key : keySet) {
					if (Utils.isNullOrEmpty(key)) {
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
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();

			return response.toString();
		} catch (IOException e) {
			try {
				InputStream is = connection.getErrorStream();
				BufferedReader rd = new BufferedReader(new InputStreamReader(is));
				StringBuilder response = new StringBuilder();
				String line;
				while ((line = rd.readLine()) != null) {
					response.append(line);
					response.append('\r');
				}
				rd.close();
				return response.toString();
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

	public static String sendGetRequest(String url, HashMap<String, String> params) {
		return sendHttpRequest(url, "GET", params, null);
	}

	public static String sendGetRequest(String url) {
		return sendHttpRequest(url, "GET", null, null);
	}

	public static String getMd5Hash(String inputString) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputString.getBytes());

			byte[] digest = md.digest();
			String hash = convertByteToHex(digest);
			return hash;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String convertByteToHex(byte[] byteData) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < byteData.length; i++) {
			sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		}

		return sb.toString();
	}

	public static Date convertDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date date = null;

		try {

			date = formatter.parse(str);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static long convertDateToLong(Date date) {
		long time = date.getTime();
		return time;
	}

	public static ZonedDateTime utcTime(long time) {
		ZonedDateTime utc = ZonedDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.of("Asia/Ho_Chi_Minh"));
		return utc;
	}

	public static Path getAbsolutePath(Path relativepath) {
		Path absolutePath = new File(new File(relativepath.toString()).getAbsolutePath()).toPath();
		return absolutePath;
	}

	public static String concatenate(List<String> listOfItems, String separator) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> stit = listOfItems.iterator();

		while (stit.hasNext()) {
			sb.append(stit.next());
			if (stit.hasNext()) {
				sb.append(separator);
			}
		}

		return sb.toString();
	}

	private static boolean isCollectionEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isObjectEmpty(Object object) {
		if (object == null)
			return true;
		else if (object instanceof String) {
			if (((String) object).trim().length() == 0) {
				return true;
			}
		} else if (object instanceof Collection) {
			return isCollectionEmpty((Collection<?>) object);
		}
		return false;
	}

	public static <E> Map<String, Object> getFields(Class<E> componentClass) {
		Map<String, Object> attList = new HashMap<>();

		for (Field field : componentClass.getFields()) {
			String attributeName = field.getName();
			field.setAccessible(true);
			try {
				Object value = field.get(componentClass);

				attList.put(attributeName, value);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return attList;
	}

	public static void setCookie(String key, String value, HttpServletResponse response) {
		if (isNullOrEmpty(key) || isNullOrEmpty(value) || response == null) {
			return;
		}
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setMaxAge(3600000);
		response.addCookie(cookie);
	}

	public static String getCookie(String key, Cookie[] cookies) {
		if (isNullOrEmpty(key) || cookies == null || cookies.length == 0) {
			return "";
		}
		for (Cookie c : cookies) {
			if (key.equals(c.getName())) {
				return c.getValue();
			}
		}
		return "";
	}

	public static String getCookie(String key, HttpServletRequest request) {
		if (request == null) {
			return "";
		}
		Cookie[] cookies = request.getCookies();
		return getCookie(key, cookies);
	}

	public static void deleteCookie(String key, HttpServletResponse response) {
		if (Utils.isNullOrEmpty(key) || response == null) {
			return;
		}
		Cookie cookie = new Cookie(key, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");

		response.addCookie(cookie);
	}

	public static String mapToString(Map<String, String> map) {
		String result = "";
		if (map == null || map.size() == 0) {
			return result;
		}
		for (String key : map.keySet()) {
			result += "&" + key + "=" + map.get(key);
		}
		result = result.substring(1);
		return result;
	}

	public static int computeHash(String value) {
		int hash = 5381, i = value.length() - 1;

		while (i >= 0) {
			hash = (hash * 33) ^ ((int) value.charAt(i));
			i--;
		}

		return hash >>> 0;
	}

	public static String removeNumberDotFingerprint(String value) {
		// value = value.replaceAll("[\\d.]", "");
		value = value.replaceAll("[\\d.]", "");
		return value;
	}

	public static Map<String, String> getHeaderMapFromRequest(HttpServletRequest request) {
		if (request == null || request.getHeaderNames() == null) {
			return null;
		}
		Map<String, String> headerMap = new HashMap<>();
		Enumeration<String> headerNames = request.getHeaderNames();
		String header = "";
		while ((header = headerNames.nextElement()) != null) {
			headerMap.put(header.toLowerCase(), request.getHeader(header));
		}
		return headerMap;
	}

	public static String headerToString(Map<String, String> headerMap) {
		if (headerMap == null) {
			return "";
		}
		String[] checkingHeaders = { "user-agent", "upgrade-insecure-requests", "accept", "accept-encoding",
				"accept-language" };
		String result = "";
		for (String header : checkingHeaders) {
			if (header.equals("user-agent")) {
				result += headerMap.get(header.toLowerCase());
				result = removeNumberDotFingerprint(result);
				continue;
			}
			result += "|" + headerMap.get(header.toLowerCase());
		}
		return result;
	}

	public static <T> boolean isTwoListEqual(List<T> list1, List<T> list2) {
		return new HashSet<>(list1).equals(new HashSet<>(list2));
	}

	public static String genRand() {
		int length = 8;
		String alphanum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		SecureRandom random = new SecureRandom();
		StringBuilder rand = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			rand.append(alphanum.charAt(random.nextInt(alphanum.length())));
		}
		return rand.toString();
	}

	public static boolean isNullOrEmptyObject(Object value) {
		return (value == null);
	}

	public static boolean isValidUrl(String url) {
		if (isNullOrEmpty(url)) {
			return false;
		}
		try {
			new URL(url);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

//	public static boolean isValidEmailAddress(String email) {
//		boolean result = true;
//		try {
//			InternetAddress emailAddr = new InternetAddress(email);
//			emailAddr.validate();
//		} catch (AddressException ex) {
//			result = false;
//		}
//		return result;
//	}

	public static boolean saveImages(Path path, MultipartFile file, String fileName) {
		boolean check = true;
		try {

			if (!Files.exists(path)) {
				try {
					Files.createDirectories(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			Path targetLocation = Paths.get(path.toString()).resolve(fileName);

			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			check = false;
			e.printStackTrace();
		}
		return check;
	}

	public static String convertDateToString(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String strDate = dateFormat.format(date);

		return strDate;
	}
	
	public static String getHost(String url) {
		if(isNullOrEmpty(url)) {
			return "";
		}
		try {
			URL serverUrl = new URL(url);
			String protocol = serverUrl.getProtocol();
			String serverAuthority = serverUrl.getAuthority();	
			
			return protocol+"://"+serverAuthority;			
		} catch (MalformedURLException e) {
			return "";
		}
	}
	
	public static String getHost(HttpServletRequest request) {
		if(request == null) {
			return "";
		}
		String host = getHost(request.getRequestURL().toString());
		
		if(host!=null && host.indexOf("http://") == 0) {
			String forwardedProtocalHeader = request.getHeader("x-forwarded-proto");
			if(forwardedProtocalHeader!=null && forwardedProtocalHeader.equals("https")) {
				host = "https://" + host.substring(7);
			}
		}
		return host;
	}
	
	public static boolean isRequestFromCurrentServerHost(HttpServletRequest request, String referer) {
		if(request == null || isNullOrEmpty(referer)) {
			return false;
		}
		URL refererUrl;
		try {
			refererUrl = new URL(referer);
			String refererAuthority = refererUrl.getAuthority();
			
			URL serverUrl = new URL(request.getRequestURL().toString());
			String serverAuthority = serverUrl.getAuthority();	
			
			if(serverAuthority != null && serverAuthority.equals(refererAuthority)) {
				return true;
			}			
		} catch (MalformedURLException e) {
			return false;
		}
		return false;
	}
	
	public static boolean isRequestFromSameAuthority(String urlOne, String urlTwo) {
		if(isNullOrEmpty(urlOne) || isNullOrEmpty(urlTwo)) {
			return false;
		}
		try {
			URL urlOneObj = new URL(urlOne);
			String urlOneAuthority = urlOneObj.getAuthority();
			
			URL urlTwoObj = new URL(urlTwo);
			String urlTwoAuthority = urlTwoObj.getAuthority();
			
			if(urlOneAuthority != null && urlOneAuthority.equals(urlTwoAuthority)) {
				return true;
			}			
		} catch (MalformedURLException e) {
			return false;
		}
		return false;
	}
	
	public static boolean isValidFloat(String value) {
		if(Utils.isNullOrEmpty(value)) {
			return false;
		}
		try {
			Float.parseFloat(value);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	public static String encodeBase64UrlSafe(String value) {
		if(isNullOrEmpty(value)) {
			return "";
		}
		String encoded = Base64.getUrlEncoder()
	            .withoutPadding()
	            .encodeToString(value.getBytes(StandardCharsets.UTF_8));
		return encoded;
	}
	
	public static String decodeBase64UrlSafe(String encodedString) {
		if(isNullOrEmpty(encodedString)) {
			return "";
		}
		byte[] decodedBytes = Base64.getUrlDecoder()
	            .decode(encodedString.getBytes(StandardCharsets.UTF_8));
		String decoded = new String(decodedBytes, StandardCharsets.UTF_8);
		return decoded;
	}

}
