package com.cht.emm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;
import com.cht.emm.model.id.ApplicationDeploy;
import com.cht.emm.model.id.UserDevice;


/**
 * 设备基本信息
 * 
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_device")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Device extends AbstractModelUnDeletable {
	/**
	 * 设备的生命周期，包括注册、激活、驳回和注销四个状态
	 * 
	 * @author luoyupan
	 * 
	 */
	public enum DeviceStatus {
		// 注册、激活、驳回和注销状态
		REGISTER(0), ACTIVATE(1), DENY(2), CANCEL(3);

		private final int value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		DeviceStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	private static final long serialVersionUID = 6118807749145530834L;
	@Id
	private String id;
	/**
	 * 设备名称
	 */
	private String name;
	/**
	 * 操作系统类型（如：ANDROID,IOS,WINDOWS等）
	 */
	private int os;
	/**
	 * 设备接受消息的ID（设备的唯一标识号）
	 */
	private String msgId;
	/**
	 * 设备类型（如：个人，企业，共用等）
	 */
	private int type;
	/**
	 * 设备的生命周期（如：注册，激活，驳回，注销等）
	 */
	private int status;
	/**
	 * 设备详细信息
	 */
	private DeviceDetail detail;
	/**
	 * 设备和用户的绑定记录
	 */
	private List<UserDevice> userDevices = new ArrayList<UserDevice>();
	/**
	 * 应用的部署记录
	 */
	private List<ApplicationDeploy> appDeploys = new ArrayList<ApplicationDeploy>();

	public Device() {

	}

	public Device(String deviceID) {
		this.id = deviceID;
	}

	@Id
	@Column(name = "id", length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 255, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "os")
	public int getOs() {
		return os;
	}

	public void setOs(int os) {
		this.os = os;
	}

	/**
	 * @return the msgId
	 */
	@Column(name="msg_id",length=36)
	public String getMsgId() {
		return msgId;
	}

	/**
	 * @param msgId the msgId to set
	 */
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	@Column(name = "type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@OneToOne(mappedBy = "device", optional = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public DeviceDetail getDetail() {
		return detail;
	}

	public void setDetail(DeviceDetail detail) {
		this.detail = detail;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "device")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public List<UserDevice> getUserDevices() {
		return userDevices;
	}

	public void setUserDevices(List<UserDevice> userDevices) {
		this.userDevices = userDevices;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "device")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public List<ApplicationDeploy> getAppDeploys() {
		return appDeploys;
	}

	public void setAppDeploys(List<ApplicationDeploy> appDeploys) {
		this.appDeploys = appDeploys;
	}

	@Column(name = "deleted", nullable = false)
	@Override
	public Integer getDeleted() {
		return deleted;
	}
	
	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
