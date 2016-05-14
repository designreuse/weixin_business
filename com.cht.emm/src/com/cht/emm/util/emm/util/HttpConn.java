package com.cht.emm.util.emm.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConn {
	public static String getConnResp(String httpUrl,String param){
		URL url = null;
		HttpURLConnection http = null;
		String result = "";
		BufferedReader in = null;
		try {
			url = new URL(httpUrl);
			http = (HttpURLConnection) url.openConnection();
			http.setDoInput(true);
			http.setDoOutput(true);
			http.setUseCaches(false);
			http.setConnectTimeout(50000);//设置连接超时
			http.setReadTimeout(50000);//设置读取超时
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type", Constants.CONTENT_TYPE);
			http.setRequestProperty("Accept-Charset", Constants.UTF8);
			http.connect();
			OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream(), "utf-8");
			osw.write(param);
			osw.flush();
			osw.close();
			if (http.getResponseCode() == 200) {
				in = new BufferedReader(new InputStreamReader(http.getInputStream(), "utf-8"));
				String inputLine;
				while ((inputLine = in.readLine()) != null) {
					result += inputLine;
				}
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (http != null) http.disconnect();
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return result;
	}
}
