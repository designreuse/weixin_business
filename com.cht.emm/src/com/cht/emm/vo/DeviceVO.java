package com.cht.emm.vo;

import java.sql.Timestamp;

import com.cht.emm.model.Device;
import com.cht.emm.model.DeviceDetail;
import com.cht.emm.util.TimestampUtil;

import nariis.pi3000.framework.json.JSONException;
import nariis.pi3000.framework.json.JSONObject;

public class DeviceVO {

	private String id;
	private String name;
	private int os;
	private int type;
	private int status;
	/**
	 * 发送消息用的ID
	 */
	private String msgId;
	
	
	private String os_str;
	private String type_str;
	private String status_str;

	private String register;
	private String destroy;

	private String users;

	private int width;
	private int height;
	private String appVersion;
	private boolean safeCard;
	private String osVersion;
	private String sim;
	private String mac;
	private String ip;
	private String imei;
	private String product;
	private int mem;
	private String phone;
	private String model;
	private String SDK;
	private String imsi;
	private String battery;
	private int totalMem;
	private String latitude;
	private String longitude;
	private String altitude;

	private boolean online;

	private String remark;

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

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

	public int getOs() {
		return os;
	}

	public void setOs(int os) {
		this.os = os;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the msgId
	 */
	public String getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	/**
	 * @return os_str
	 */
	public String getOs_str() {
		return os_str;
	}

	/**
	 * @param os_str
	 *            要设置的 os_str
	 */
	public void setOs_str(String os_str) {
		this.os_str = os_str;
	}

	/**
	 * @return type_str
	 */
	public String getType_str() {
		return type_str;
	}

	/**
	 * @param type_str
	 *            要设置的 type_str
	 */
	public void setType_str(String type_str) {
		this.type_str = type_str;
	}

	/**
	 * @return status_str
	 */
	public String getStatus_str() {
		return status_str;
	}

	/**
	 * @param status_str
	 *            要设置的 status_str
	 */
	public void setStatus_str(String status_str) {
		this.status_str = status_str;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getDestroy() {
		return destroy;
	}

	public void setDestroy(String destroy) {
		this.destroy = destroy;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getSim() {
		return sim;
	}

	public void setSim(String sim) {
		this.sim = sim;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSDK() {
		return SDK;
	}

	public void setSDK(String sDK) {
		SDK = sDK;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getAltitude() {
		return altitude;
	}

	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	public boolean isSafeCard() {
		return safeCard;
	}

	public void setSafeCard(boolean safeCard) {
		this.safeCard = safeCard;
	}

	public int getMem() {
		return mem;
	}

	public void setMem(int mem) {
		this.mem = mem;
	}

	public int getTotalMem() {
		return totalMem;
	}

	public void setTotalMem(int totalMem) {
		this.totalMem = totalMem;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Device toDevice(Device device) {
		if (device == null) {
			device = new Device();
		}
		device.setId(id);
		device.setName(name);
		device.setOs(os);
		device.setType(type);
		device.setStatus(status);
		device.setMsgId(msgId);
		device.setDeleted(0);

		return device;
	}

	public void fromDevice(Device device) {
		id = device.getId();
		name = device.getName() == null ? "" : device.getName();
		os = device.getOs();
		type = device.getType();
		status = device.getStatus();
	}

	public void fromJSON(String object) {
		try {
			JSONObject device = new JSONObject(object);
			if (device.has("os")) {
				os = device.getInt("os");
			}
			if (device.has("phone")) {
				phone = device.getString("phone");
			}
			if (device.has("model")) {
				name = device.getString("model");
			}
			if (device.has("osVersion")) {
				osVersion = device.getString("osVersion");
			}
			if (device.has("altitude")) {
				altitude = device.getString("altitude");
			}
			if (device.has("imei")) {
				imei = device.getString("imei");
			}
			if (device.has("width")) {
				width = device.getInt("width");
			}
			if (device.has("mac")) {
				mac = device.getString("mac");
			}
			if (device.has("type")) {
				type = device.getInt("type");
			}
			if (device.has("SDK")) {
				SDK = device.getString("SDK");
			}
			if (device.has("ip")) {
				ip = device.getString("ip");
			}
			if (device.has("totalMem")) {
				totalMem = device.getInt("totalMem");
			}
			if (device.has("product")) {
				product = device.getString("product");
			}
			id = device.getString("id");
			if (device.has("height")) {
				height = device.getInt("height");
			}
			if (device.has("appVersion")) {
				appVersion = device.getString("appVersion");
			}
			if (device.has("battery")) {
				battery = device.getString("battery");
			}
			if (device.has("mem")) {
				mem = device.getInt("mem");
			}
			if (device.has("sim")) {
				sim = device.getString("sim");
			}
			if (device.has("longtitude")) {
				longitude = device.getString("longitude");
			}
			if (device.has("latitude")) {
				latitude = device.getString("latitude");
			}
			if (device.has("imsi")) {
				imsi = device.getString("imsi");
			}
			if (device.has("safeCard")) {
				safeCard = device.getBoolean("safeCard");
			}
			if (device.has("msgId")) {
				msgId = device.getString("msgId");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public DeviceDetail toDeviceDetail(DeviceDetail detail) {
		if (detail == null) {
			detail = new DeviceDetail();
		}
		detail.setId(id);
		detail.setRegister(register != null ? Timestamp.valueOf(register)
				: new Timestamp(System.currentTimeMillis()));
		detail.setDestroy(destroy != null ? Timestamp.valueOf(destroy) : null);
		JSONObject info = new JSONObject();
		try {
			info.put("width", width);
			info.put("height", height);
			info.put("appVersion", appVersion);
			info.put("safeCard", safeCard);
			info.put("osVersion", osVersion);
			info.put("sim", sim);
			info.put("mac", mac);
			info.put("ip", ip);
			info.put("imei", imei);
			info.put("product", product);
			info.put("mem", mem);
			info.put("phone", phone);
			info.put("SDK", SDK);
			info.put("imsi", imsi);
			info.put("battery", battery);
			info.put("totalMem", totalMem);
			info.put("latitude", latitude);
			info.put("longitude", longitude);
			info.put("altitude", altitude);
		} catch (Exception e) {

		}
		detail.setDetail(info.toString());
		detail.setDeleted(0);
		return detail;
	}

	public void fromDeviceDetail(DeviceDetail detail) {
		id = detail.getId();
		register = TimestampUtil.toString(detail.getRegister());
		destroy = TimestampUtil.toString(detail.getDestroy());
		remark = detail.getRemark() == null ? "" : detail.getRemark();

		try {
			JSONObject device = new JSONObject(detail.getDetail());
			if (device.has("phone")) {
				phone = device.getString("phone");
			}
			if (device.has("osVersion")) {
				osVersion = device.getString("osVersion");
			}
			if (device.has("altitude")) {
				altitude = device.getString("altitude");
			}
			if (device.has("imei")) {
				imei = device.getString("imei");
			}
			if (device.has("width")) {
				width = device.getInt("width");
			}
			if (device.has("mac")) {
				mac = device.getString("mac");
			}
			if (device.has("SDK")) {
				SDK = device.getString("SDK");
			}
			if (device.has("ip")) {
				ip = device.getString("ip");
			}
			if (device.has("totalMem")) {
				totalMem = device.getInt("totalMem");
			}
			if (device.has("product")) {
				product = device.getString("product");
			}
			if (device.has("height")) {
				height = device.getInt("height");
			}
			if (device.has("appVersion")) {
				appVersion = device.getString("appVersion");
			}
			if (device.has("battery")) {
				battery = device.getString("battery");
			}
			if (device.has("mem")) {
				mem = device.getInt("mem");
			}
			if (device.has("sim")) {
				sim = device.getString("sim");
			}
			if (device.has("longtitude")) {
				longitude = device.getString("longitude");
			}
			if (device.has("latitude")) {
				latitude = device.getString("latitude");
			}
			if (device.has("imsi")) {
				imsi = device.getString("imsi");
			}
			if (device.has("safeCard")) {
				safeCard = device.getBoolean("safeCard");
			}
		} catch (Exception e) {

		}
	}
}
