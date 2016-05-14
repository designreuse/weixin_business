package com.cht.emm.util.emm.entity;

import java.util.HashMap;
import java.util.Map;

public class RopSystemEntity {
	/**appKey*/
	private String appKey;
	/**sessionId*/
	private String sessionId;
	/**version*/
	private String v;
	/**method*/
	private String method;
	/**format*/
	private String format;
	/**locale*/
	private String locale;
	/**sign*/
	private String sign;
	
	
	
	public Map<String,String> toMap(){
		Map<String,String> sysMap = new HashMap<String,String>();
		if(appKey!=null && !"".equals(appKey)){
			sysMap.put("appKey", appKey);
		}
		
		if(sessionId!=null && !"".equals(sessionId)){
			sysMap.put("sessionId", sessionId);
		}
		
		if(v!=null && !"".equals(v)){
			sysMap.put("v", v);
		}
		
		if(method!=null && !"".equals(method)){
			sysMap.put("method", method);
		}
		
		if(format!=null && !"".equals(format)){
			sysMap.put("format", format);
		}
		
		if(locale!=null && !"".equals(locale)){
			sysMap.put("locale", locale);
		}
		
		if(sign!=null && !"".equals(sign)){
			sysMap.put("sign", sign);
		}
		return sysMap;
	}
	
	//getters and setters
	
	public String getAppKey() {
		return appKey;
	}
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getV() {
		return v;
	}
	public void setV(String v) {
		this.v = v;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
}
