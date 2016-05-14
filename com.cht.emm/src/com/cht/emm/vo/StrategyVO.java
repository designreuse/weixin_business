package com.cht.emm.vo;

import java.sql.Timestamp;

import com.cht.emm.model.Strategy;
import com.cht.emm.model.User;
import com.cht.emm.util.TimestampUtil;

import nariis.pi3000.framework.json.JSONException;
import nariis.pi3000.framework.json.JSONObject;
import nariis.pi3000.framework.utility.StringUtil;

public class StrategyVO {

	private String id;
	private String name;
	private String desc;
	private String creator;
	private String time;

	private String users;

	private int breakout;
	private int appList;
	private int password;
	private int osVersion;
	private int encrypt;

	private int condition;
	private int operation;

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

	public String getUsers() {
		return users;
	}

	public void setUsers(String users) {
		this.users = users;
	}

	public int getBreakout() {
		return breakout;
	}

	public void setBreakout(int breakout) {
		this.breakout = breakout;
	}

	public int getAppList() {
		return appList;
	}

	public void setAppList(int appList) {
		this.appList = appList;
	}

	public int getPassword() {
		return password;
	}

	public void setPassword(int password) {
		this.password = password;
	}

	public int getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(int osVersion) {
		this.osVersion = osVersion;
	}

	public int getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(int encrypt) {
		this.encrypt = encrypt;
	}

	public int getCondition() {
		return condition;
	}

	public void setCondition(int condition) {
		this.condition = condition;
	}

	public int getOperation() {
		return operation;
	}

	public void setOperation(int operation) {
		this.operation = operation;
	}

	public void fromStrategy(Strategy strategy) {
		id = strategy.getId();
		name = strategy.getName();
		desc = strategy.getDesc() == null ? "" : strategy.getDesc();
		creator = strategy.getCreator().getUsername();
		time = TimestampUtil.toString(strategy.getTime());

		if (StringUtil.isNotEmpty(strategy.getContent())) {
			try {
				JSONObject obj = new JSONObject(strategy.getContent());
				if (obj.has("breakout")) {
					breakout = obj.getInt("breakout");
				}
				if (obj.has("appList")) {
					appList = obj.getInt("appList");
				}
				if (obj.has("password")) {
					password = obj.getInt("password");
				}
				if (obj.has("osVersion")) {
					osVersion = obj.getInt("osVersion");
				}
				if (obj.has("encrypt")) {
					encrypt = obj.getInt("encrypt");
				}
				if (obj.has("condition")) {
					condition = obj.getInt("condition");
				}
				if (obj.has("operation")) {
					operation = obj.getInt("operation");
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	public Strategy toStrategy(Strategy strategy) {
		if (strategy == null) {
			strategy = new Strategy();
		}
		strategy.setId(id);
		strategy.setName(name);
		strategy.setCreator(new User(creator));
		strategy.setDesc(desc);
		strategy.setTime(time != null ? Timestamp.valueOf(time)
				: new Timestamp(System.currentTimeMillis()));
		try {
			JSONObject obj = new JSONObject();
			obj.put("breakout", breakout);
			obj.put("appList", appList);
			obj.put("password", password);
			obj.put("osVersion", osVersion);
			obj.put("encrypt", encrypt);
			obj.put("condition", condition);
			obj.put("operation", operation);
			strategy.setContent(obj.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return strategy;
	}
}
