package com.cht.emm.model.id;

import java.sql.Timestamp;

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
import com.cht.emm.model.Application;
import com.cht.emm.model.Device;
import com.cht.emm.model.User;


/**
 * 应用的部署记录
 * 
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_app_deploy")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ApplicationDeploy extends AbstractModel {

	private static final long serialVersionUID = 6118807749145530834L;
	@Id
	private String id;
	/**
	 * 操作用户
	 */
	private User user;
	/**
	 * 操作设备
	 */
	private Device device;
	/**
	 * 操作应用
	 */
	private Application app;
	/**
	 * 应用状态（如：下载，安装，更新，卸载等）
	 */
	private int status;
	/**
	 * 操作时间
	 */
	private Timestamp time;

	public ApplicationDeploy() {

	}

	@Id
	@Column(name = "id", nullable = false, length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "device_id", nullable = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id", nullable = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	@Column(name = "status", nullable = false)
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "download_time")
	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}
	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
