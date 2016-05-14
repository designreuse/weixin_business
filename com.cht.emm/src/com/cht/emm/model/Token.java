package com.cht.emm.model;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cht.emm.common.model.AbstractModel;


@Entity
@Table(name = "mip_sys_token")
public class Token extends AbstractModel {

	private static final long serialVersionUID = -8577958602838856420L;

	@Id
	@Column(name = "token_id", length = 36)
	private String tokenId;
	@Column(name = "create_time")
	private Timestamp createTime;
	@Column(name = "update_time")
	private Timestamp updateTime;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "device_id")
	private String deviceId;

	public String getTokenId() {

		return tokenId;
	}

	public void setTokenId(String id) {

		tokenId = id;
	}

	public Timestamp getCreateTime() {

		return createTime;
	}

	public void setCreateTime(Timestamp ctime) {

		createTime = ctime;

	}

	public Timestamp getUpdateTime() {

		return updateTime;
	}

	public void setUpdateTime(Timestamp utime) {

		updateTime = utime;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String uname) {

		userName = uname;
	}

	public String getDeviceId() {

		return deviceId;
	}

	public void setDeviceId(String devId) {

		deviceId = devId;
	}

	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
