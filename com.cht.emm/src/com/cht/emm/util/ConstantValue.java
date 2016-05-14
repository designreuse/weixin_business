package com.cht.emm.util;

import java.util.ArrayList;
import java.util.List;

import com.cht.emm.vo.AppVO;
import com.cht.emm.vo.CounterVO;
import com.cht.emm.vo.KeyValueVO;
import com.cht.emm.vo.ResourceTypeVO;


public class ConstantValue {
	public static final List<ResourceTypeVO> TYPES = new ArrayList<ResourceTypeVO>();
	private static final List<AppVO> APPS = new ArrayList<AppVO>();
	private static final List<CounterVO> DEVICE_STATUS_COUNTS = new ArrayList<CounterVO>();
	private static final List<CounterVO> DEVICE_TYPE_COUNTS = new ArrayList<CounterVO>();
	private static final List<CounterVO> DEVICE_COUNTS = new ArrayList<CounterVO>();
	private static final List<KeyValueVO> PHONE_OPS = new ArrayList<KeyValueVO>();
	private static final List<CounterVO> DEVICE_ALARM = new ArrayList<CounterVO>();
	private static final List<CounterVO> DEVICE_ONLINE = new ArrayList<CounterVO>();
	// DeviceStatusCount
	static {
		ResourceTypeVO url = new ResourceTypeVO();
		url.setKey("url");
		url.setValue(1);
		TYPES.add(url);
		ResourceTypeVO file = new ResourceTypeVO();
		file.setKey("文件");
		file.setValue(2);
		TYPES.add(file);
		String[] appName = { "淘宝App", "微博", "微信", "喜马拉雅", "内涵段子", "网易新闻",
				"大众点评", "京东", "当当网", "腾讯qq" };
		for (String string : appName) {
			AppVO app = new AppVO();
			app.setId(UUIDGen.getUUID());
			app.setAppName(string);
			APPS.add(app);
		}
		String[] deviceStatus = { "小米", "三星", "华为", "中兴", "其他" };
		for (String string : deviceStatus) {
			CounterVO deviceStatusCount = new CounterVO();
			deviceStatusCount.setId(UUIDGen.getUUID());
			deviceStatusCount.setName(string);
			DEVICE_STATUS_COUNTS.add(deviceStatusCount);
		}

		String[] deviceType = { "Andriod", "IOS", "WP" };
		for (String string : deviceType) {
			CounterVO deviceTypeCount = new CounterVO();
			deviceTypeCount.setId(UUIDGen.getUUID());
			deviceTypeCount.setName(string);
			DEVICE_TYPE_COUNTS.add(deviceTypeCount);
		}
		String[] deviceAlarm = { "报警设备", "未报警设备" };
		for (String string : deviceAlarm) {
			CounterVO deviceTypeCount = new CounterVO();
			deviceTypeCount.setId(UUIDGen.getUUID());
			deviceTypeCount.setName(string);
			DEVICE_ALARM.add(deviceTypeCount);
		}
		String[] deviceOnline = { "在线设备", "下线设备" };
		for (String string : deviceOnline) {
			CounterVO deviceTypeCount = new CounterVO();
			deviceTypeCount.setId(UUIDGen.getUUID());
			deviceTypeCount.setName(string);
			DEVICE_ONLINE.add(deviceTypeCount);
		}

		String[] devices = { "合规", "不合规" };
		for (String string : devices) {
			CounterVO deviceTypeCount = new CounterVO();
			deviceTypeCount.setId(UUIDGen.getUUID());
			deviceTypeCount.setName(string);
			DEVICE_COUNTS.add(deviceTypeCount);
		}
		String[] ops = { "iso", "android", "windows phone" };
		for (String op : ops) {
			KeyValueVO keyValue = new KeyValueVO();
			keyValue.setKey("uuid-ss-" + op);
			keyValue.setValue(op);
			PHONE_OPS.add(keyValue);
		}
	}

	public static List<ResourceTypeVO> getTypes() {
		return TYPES;
	}

	public static List<AppVO> getApps() {
		return APPS;
	}

	public static List<CounterVO> getDeviceStatusCounts() {
		return DEVICE_STATUS_COUNTS;
	}

	public static List<CounterVO> getDeviceTypeCounts() {
		return DEVICE_TYPE_COUNTS;
	}

	public static List<CounterVO> getDeviceCounts() {
		return DEVICE_COUNTS;
	}

	public static List<KeyValueVO> getOps() {
		// TODO Auto-generated method stub
		return PHONE_OPS;
	}

	public static List<CounterVO> getDeviceAlarms() {
		// TODO Auto-generated method stub
		return DEVICE_ALARM;
	}

	public static List<CounterVO> getDeviceOnline() {
		// TODO Auto-generated method stub
		return DEVICE_ONLINE;
	}
}
