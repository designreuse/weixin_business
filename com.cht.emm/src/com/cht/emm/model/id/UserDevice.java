package com.cht.emm.model.id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModel;
import com.cht.emm.model.Device;
import com.cht.emm.model.User;


/**
 * 设备和用户的绑定记录
 * 
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_user_device")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UserDevice extends AbstractModel {
	/**
	 * 用户与设备的绑定状态（如：未审核，已审核等）
	 * 
	 * @author luoyupan
	 * 
	 */
	public enum UserDeviceStatus {
		// 未审核、已审核状态
		UNCHECK(0), CHECK(1);

		private final int value;

		// 构造器默认也只能是private, 从而保证构造函数只能在内部使用
		UserDeviceStatus(int value) {
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
	 * 绑定用户
	 */
	private User user;
	/**
	 * 绑定设备
	 */
	private Device device;
	/**
	 * 绑定状态（如：未审核，已审核等）
	 */
	private int status;

	public UserDevice() {

	}

	@Id
	@Column(name = "id", length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
