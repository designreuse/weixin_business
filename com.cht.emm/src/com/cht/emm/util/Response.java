package com.cht.emm.util;

import java.io.Serializable;

/**
 * 
 * rest服务返回对象
 * 
 * @author luoyupan
 * 
 */
public class Response implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7500590912546643986L;
	/**
	 * 服务调用是否成功
	 */
	private boolean isSuccessful;
	/**
	 * 服务调用失败时返回的错误信息
	 */
	private String resultMessage;
	
	/**
	 * 是否需重登陆
	 */
	private boolean  relogin = false;
	/**
	 * 服务调用成功时返回的结果对象
	 */
	private Object resultValue;

	public boolean isSuccessful() {
		return isSuccessful;
	}

	public void setSuccessful(boolean isSuccessful) {
		this.isSuccessful = isSuccessful;
	}

	public boolean isRelogin() {
		return relogin;
	}

	public void setRelogin(boolean relogin) {
		this.relogin = relogin;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public Object getResultValue() {
		return resultValue;
	}

	public void setResultValue(Object resultValue) {
		this.resultValue = resultValue;
	}

	public static Response newResponse(boolean success, String message,
			Object body) {
		Response response = new Response();
		response.setResultMessage(message);
		response.setResultValue(body);
		response.setSuccessful(success);
		return response;
	}

}
