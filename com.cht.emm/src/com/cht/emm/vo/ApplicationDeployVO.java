package com.cht.emm.vo;

public class ApplicationDeployVO {

	private String username;
	private String appname;
	private String time;
	private String version;
	private int status;
	private String devicename;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDevicename() {
		return devicename;
	}

	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

}
