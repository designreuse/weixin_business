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
import com.cht.emm.model.Config;
import com.cht.emm.model.User;


/**
 * 用户和设备配置的关联记录
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_config_user")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ConfigUser extends AbstractModel {

	private static final long serialVersionUID = 6118807749145530834L;
	@Id
	private String id;
	/**
	 * 设备配置
	 */
	private Config config;
	/**
	 * 用户
	 */
	private User user;
	/**
	 * 配置状态（如：已生效，未生效等）
	 */
	private int status;

	public ConfigUser() {

	}

	public ConfigUser(String deviceID) {
		id = deviceID;
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
	@JoinColumn(name = "config_id", nullable = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
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
