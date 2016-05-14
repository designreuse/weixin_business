package com.cht.emm.vo;

import java.util.List;

public class DeviceVO2 {

	private String id;
	private String name;
	private String start_time;
	private String start_addr;
	private String addr;
	private int compliance;
	private int password;
	private int net;
	private List<ApplicationVO2> apps;
	private List<DeviceLog> logs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getStart_addr() {
		return start_addr;
	}

	public void setStart_addr(String start_addr) {
		this.start_addr = start_addr;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getCompliance() {
		return compliance;
	}

	public void setCompliance(int compliance) {
		this.compliance = compliance;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public int getNet() {
		return net;
	}

	public void setNet(int net) {
		this.net = net;
	}

	public List<ApplicationVO2> getApps() {
		return apps;
	}

	public void setApps(List<ApplicationVO2> apps) {
		this.apps = apps;
	}

	public List<DeviceLog> getLogs() {
		return logs;
	}

	public void setLogs(List<DeviceLog> logs) {
		this.logs = logs;
	}

}
