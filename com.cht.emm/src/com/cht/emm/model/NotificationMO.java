package com.cht.emm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModel;


/**
 * 消息推送列表
 * 
 * 
 * @author lyp
 * 
 */
@Entity
@Table(name = "mip_sys_notification")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class NotificationMO extends AbstractModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1580790204983674788L;
	/**
	 * 未发送
	 */
	public static final int STATUS_NOT_SEND = 0;
	/**
	 * 已发送
	 */
	public static final int STATUS_SEND = 1;
	/**
	 * 已接收
	 */
	public static final int STATUS_RECEIVE = 2;
	/**
	 * 已查看
	 */
	public static final int STATUS_READ = 3;

	public NotificationMO() {
	}

	@Id
	@Column(name = "id", length = 36)
	private String id;
	@Column(name = "username", length = 36)
	private String username;
	@Column(name = "title", length = 255)
	private String title;
	@Column(name = "message", length = 255)
	private String message;
	@Column(name = "status")
	private int status;
	@Column(name = "create_time")
	private Timestamp createTime;
	@Column(name = "update_time")
	private Timestamp updateTime;
	@Column(name = "type")
	private int type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
