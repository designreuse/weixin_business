package com.cht.emm.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.cht.emm.model.Config;
import com.cht.emm.model.User;
import com.cht.emm.util.DeviceOrderConstants;
import com.cht.emm.util.TimestampUtil;

import nariis.pi3000.framework.json.JSONArray;
import nariis.pi3000.framework.json.JSONException;
import nariis.pi3000.framework.json.JSONObject;
import nariis.pi3000.framework.utility.StringUtil;

public class ConfigVO {

	private String id;
	private String name;
	private String desc;
	private int type;
	private String creator;
	private String time;

	private String users;

	private int pwd_intensity = -1;
	private int pwd_length_min;
	private int pwd_time_max;

	private int data_encrypt;
	private int audit;
	private int camera;
	private int bluetooth;

	private List<WifiVO> wifies = new ArrayList<WifiVO>();;
	private List<VpnVO> vpns = new ArrayList<VpnVO>();;

	private int app_list_type;
	private String apps;

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

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getPwd_intensity() {
		return pwd_intensity;
	}

	public void setPwd_intensity(int pwd_intensity) {
		this.pwd_intensity = pwd_intensity;
	}

	public int getPwd_length_min() {
		return pwd_length_min;
	}

	public void setPwd_length_min(int pwd_length_min) {
		this.pwd_length_min = pwd_length_min;
	}

	public int getPwd_time_max() {
		return pwd_time_max;
	}

	public void setPwd_time_max(int pwd_time_max) {
		this.pwd_time_max = pwd_time_max;
	}

	public int getData_encrypt() {
		return data_encrypt;
	}

	public void setData_encrypt(int data_encrypt) {
		this.data_encrypt = data_encrypt;
	}

	public int getAudit() {
		return audit;
	}

	public void setAudit(int audit) {
		this.audit = audit;
	}

	public int getCamera() {
		return camera;
	}

	public void setCamera(int camera) {
		this.camera = camera;
	}

	public int getBluetooth() {
		return bluetooth;
	}

	public void setBluetooth(int bluetooth) {
		this.bluetooth = bluetooth;
	}

	public List<WifiVO> getWifies() {
		return wifies;
	}

	public void setWifies(List<WifiVO> wifies) {
		this.wifies = wifies;
	}

	public List<VpnVO> getVpns() {
		return vpns;
	}

	public void setVpns(List<VpnVO> vpns) {
		this.vpns = vpns;
	}

	public int getApp_list_type() {
		return app_list_type;
	}

	public void setApp_list_type(int app_list_type) {
		this.app_list_type = app_list_type;
	}

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public String getApps() {
		return apps;
	}

	public void setApps(String apps) {
		this.apps = apps;
	}

	public void fromConfig(Config config) {
		id = config.getId();
		name = config.getName();
		desc = config.getDesc() == null ? "" : config.getDesc();
		type = config.getType();
		creator = config.getCreator().getUsername();
		time = TimestampUtil.toString(config.getTime());

		if (StringUtil.isNotEmpty(config.getContent())) {
			try {
				JSONObject obj = new JSONObject(config.getContent());
				if (obj.has(DeviceOrderConstants.CONFIG_PASSWORD)) {
					JSONObject temp = obj
							.getJSONObject(DeviceOrderConstants.CONFIG_PASSWORD);
					if (temp.has(DeviceOrderConstants.CONFIG_PASSWORD_QUALITY)) {
						pwd_intensity = temp
								.getInt(DeviceOrderConstants.CONFIG_PASSWORD_QUALITY);
					}
					if (temp.has(DeviceOrderConstants.CONFIG_PWD_LENGTH_MIN)) {
						pwd_length_min = temp
								.getInt(DeviceOrderConstants.CONFIG_PWD_LENGTH_MIN);
					}

					if (temp.has(DeviceOrderConstants.CONFIG_PWD_TIME_MAX)) {
						pwd_time_max = temp
								.getInt(DeviceOrderConstants.CONFIG_PWD_TIME_MAX);
					}
				}
				if (obj.has(DeviceOrderConstants.CONFIG_SECURITY)) {
					JSONObject temp = obj
							.getJSONObject(DeviceOrderConstants.CONFIG_SECURITY);
					if (temp.has(DeviceOrderConstants.CONFIG_DATA_ENCRYPT)) {
						data_encrypt = temp
								.getInt(DeviceOrderConstants.CONFIG_DATA_ENCRYPT);
					}
					if (temp.has(DeviceOrderConstants.CONFIG_AUDIT)) {
						audit = temp.getInt(DeviceOrderConstants.CONFIG_AUDIT);
					}
					if (temp.has(DeviceOrderConstants.CONFIG_CAMERA)) {
						camera = temp
								.getInt(DeviceOrderConstants.CONFIG_CAMERA);
					}
					if (temp.has(DeviceOrderConstants.CONFIG_BLUETOOTH)) {
						bluetooth = temp
								.getInt(DeviceOrderConstants.CONFIG_BLUETOOTH);
					}
				}

				if (obj.has(DeviceOrderConstants.CONFIG_APP)) {
					JSONObject temp = obj
							.getJSONObject(DeviceOrderConstants.CONFIG_APP);
					if (temp.has(DeviceOrderConstants.CONFIG_APP_LIST_TYPE)) {
						app_list_type = temp
								.getInt(DeviceOrderConstants.CONFIG_APP_LIST_TYPE);
					}
					if (temp.has(DeviceOrderConstants.CONFIG_APPS)) {
						apps = temp.getString(DeviceOrderConstants.CONFIG_APPS);
					}
				}
				if (obj.has(DeviceOrderConstants.CONFIG_WIFI)) {
					JSONArray arr = obj
							.getJSONArray(DeviceOrderConstants.CONFIG_WIFI);
					for (int i = 0; i < arr.length(); i++) {
						JSONObject temp = arr.getJSONObject(i);
						WifiVO wifi = new WifiVO();
						wifi.setName(temp
								.getString(DeviceOrderConstants.CONFIG_WIFI_NAME));
						wifi.setPassword(temp
								.getString(DeviceOrderConstants.CONFIG_WIFI_PASSWORD));
						wifies.add(wifi);
					}
				}
				if (obj.has(DeviceOrderConstants.CONFIG_VPN)) {
					JSONArray arr = obj
							.getJSONArray(DeviceOrderConstants.CONFIG_VPN);
					for (int i = 0; i < arr.length(); i++) {
						JSONObject temp = arr.getJSONObject(i);
						VpnVO vpn = new VpnVO();
						vpn.setName(temp
								.getString(DeviceOrderConstants.CONFIG_VPN_NAME));
						vpn.setPassword(temp
								.getString(DeviceOrderConstants.CONFIG_VPN_PASSWORD));
						vpns.add(vpn);
					}
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public Config toConfig(Config config) {
		if (config == null) {
			config = new Config();
		}
		config.setId(id);
		config.setName(name);
		config.setCreator(new User(creator));
		config.setType(type);
		config.setDesc(desc);
		config.setTime(time != null ? Timestamp.valueOf(time) : new Timestamp(
				System.currentTimeMillis()));
		try {
			JSONObject obj = new JSONObject();
			JSONObject tempObj = new JSONObject();
			if (pwd_intensity != -1) {
				tempObj.put(DeviceOrderConstants.CONFIG_PASSWORD_QUALITY,
						pwd_intensity);
			}
			if (pwd_length_min != -1) {
				tempObj.put(DeviceOrderConstants.CONFIG_PWD_LENGTH_MIN,
						pwd_length_min);
			}
			if (pwd_time_max != -1) {
				tempObj.put(DeviceOrderConstants.CONFIG_PWD_TIME_MAX,
						pwd_time_max);
			}
			if (tempObj.length() > 0) {
				obj.put(DeviceOrderConstants.CONFIG_PASSWORD, tempObj);
			}
			tempObj = new JSONObject();
			if (data_encrypt != -1) {
				tempObj.put(DeviceOrderConstants.CONFIG_DATA_ENCRYPT,
						data_encrypt);
			}
			if (audit != -1) {
				tempObj.put(DeviceOrderConstants.CONFIG_AUDIT, audit);
			}
			if (camera != -1) {
				tempObj.put(DeviceOrderConstants.CONFIG_CAMERA, camera);
			}
			if (bluetooth != -1) {
				tempObj.put(DeviceOrderConstants.CONFIG_BLUETOOTH, bluetooth);
			}
			if (tempObj.length() > 0) {
				obj.put(DeviceOrderConstants.CONFIG_SECURITY, tempObj);
			}
			if (app_list_type != -1) {
				JSONObject temp = new JSONObject();
				temp.put(DeviceOrderConstants.CONFIG_APPS, apps);
				temp.put(DeviceOrderConstants.CONFIG_APP_LIST_TYPE,
						app_list_type);
				obj.put(DeviceOrderConstants.CONFIG_APP, temp);
			}
			if (wifies.size() > 0) {
				JSONArray arr = new JSONArray();
				for (WifiVO wifi : wifies) {
					JSONObject temp = new JSONObject();
					temp.put(DeviceOrderConstants.CONFIG_WIFI_NAME,
							wifi.getName());
					temp.put(DeviceOrderConstants.CONFIG_WIFI_PASSWORD,
							wifi.getPassword());
					arr.put(temp);
				}
				obj.put(DeviceOrderConstants.CONFIG_WIFI, arr);
			}
			if (vpns.size() > 0) {
				JSONArray arr1 = new JSONArray();
				for (VpnVO vpn : vpns) {
					JSONObject temp = new JSONObject();
					temp.put(DeviceOrderConstants.CONFIG_VPN_NAME,
							vpn.getName());
					temp.put(DeviceOrderConstants.CONFIG_VPN_PASSWORD,
							vpn.getPassword());
					arr1.put(temp);
				}
				obj.put(DeviceOrderConstants.CONFIG_VPN, arr1);
			}
			config.setContent(obj.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return config;
	}

}
