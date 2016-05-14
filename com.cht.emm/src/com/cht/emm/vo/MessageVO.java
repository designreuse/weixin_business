package com.cht.emm.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import nariis.pi3000.framework.json.JSONArray;
import nariis.pi3000.framework.json.JSONException;
import nariis.pi3000.framework.json.JSONObject;

/**
 * 消息实体对象。
 * 
 * @author wjun
 * 
 */
public class MessageVO implements Serializable {

	/**
	 * 消息等级
	 */
	public static final int GENERAL = 1; // 一般
	public static final int IMPORTANT = 2; // 重要

	/**
	 * 消息类型
	 */
	public static final int UNDEFINED = 0; // 未定义
	public static final int MESSAGE = 1; // 普通消息
	public static final int TASK = 2; // 任务消息
	public static final int ORDER = 3; // 指令消息
	public static final int CONFIG = 4; // 配置消息

	/**
	 * 消息状态
	 */
	public static final int UNREAD = 0; // 未读
	public static final int READ = 1; // 已读

	private static final long serialVersionUID = -8868615329362596308L;

	private String id = null;

	private String title = null;

	private String content = null;

	private String sender = null;

	private List<String> receivers = null;

	private String receiverAction = null;

	private Timestamp date = null;

	private int level = 1;

	private int state = 0;

	private int type = 2;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取消息标题。
	 * 
	 * @return
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 设置消息标题。
	 * 
	 * @param p_title
	 *            指定的消息标题。
	 */
	public void setTitle(String p_title) {
		title = p_title;
	}

	/**
	 * 获取消息内容。
	 * 
	 * @return
	 */
	public String getContent() {
		return content;
	}

	/**
	 * 设置消息内容。
	 * 
	 * @param p_content
	 *            指定的消息内容。
	 */
	public void setContent(String p_content) {
		content = p_content;
	}

	/**
	 * 获取消息发送者。
	 * 
	 * @return
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * 设置发送者。
	 * 
	 * @param p_sender
	 *            指定的消息发送者。
	 */
	public void setSender(String p_sender) {
		sender = p_sender;
	}

	/**
	 * 获取消息接受者。
	 * 
	 * @return
	 */
	public List<String> getReceivers() {
		return receivers;
	}

	/**
	 * 设置接受者。
	 * 
	 * @param p_receiver
	 *            指定的消息接受者。
	 */
	public void setReceivers(List<String> p_receivers) {
		receivers = p_receivers;
	}

	/**
	 * 获取消息时间。
	 * 
	 * @return
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * 设置消息时间。
	 * 
	 * @param p_date
	 *            指定的消息时间。
	 */
	public void setDate(Timestamp p_date) {
		date = p_date;
	}

	/**
	 * 获取消息等级。
	 * 
	 * @return
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * 设置消息等级。
	 * 
	 * @param p_level
	 *            指定的消息等级。
	 */
	public void setLevel(int p_level) {
		level = p_level;
	}

	/**
	 * 获取消息状态。
	 * 
	 * @return
	 */
	public int getState() {
		return state;
	}

	/**
	 * 设置消息状态。
	 * 
	 * @param p_state
	 *            指定的消息状态。
	 */
	public void setState(int p_state) {
		state = p_state;
	}

	/**
	 * 获取消息类型。
	 * 
	 * @return
	 */
	public int getType() {
		return type;
	}

	/**
	 * 设置消息类型。
	 * 
	 * @param p_type
	 *            指定的消息类型。
	 */
	public void setType(int p_type) {
		type = p_type;
	}

	public String getReceiverAction() {
		return receiverAction;
	}

	public void setReceiverAction(String receiverAction) {
		this.receiverAction = receiverAction;
	}

	/**
	 * 与指定的数据实例进行比较，并返回比较结果。
	 * 
	 * @param p_Object
	 *            指定的数据实例。
	 */
	public boolean equals(Object p_Object) {
		if (p_Object == null) {
			return false;
		}
		if (p_Object instanceof MessageVO) {
			MessageVO info = (MessageVO) p_Object;
			return this.getId().equals(info.getId());
		} else {
			return false;
		}
	}

	public String toJsonString() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("id", this.id);
			obj.put("level", this.level);
			obj.put("state", this.state);
			obj.put("type", this.type);
			obj.put("content", this.content);
			obj.put("date", this.date.getTime());
			obj.put("receiverAction", this.receiverAction);
			JSONArray arr = new JSONArray();
			for (String receiver : this.receivers) {
				arr.put(receiver);
			}
			obj.put("receivers", arr);
			obj.put("sender", this.sender);
			obj.put("title", this.title);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj.toString();
	}

}
