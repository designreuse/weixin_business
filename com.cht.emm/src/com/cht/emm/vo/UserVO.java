package com.cht.emm.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class UserVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2062576489181675962L;
	private String id;
	private String username;
	private String password;
	private Integer status;

	private Integer gander;

	private String email;
	private String mobile;
	private String userAlias;
	private Integer userType;
	private Timestamp createTime;
	private Timestamp modifyTime;

	private List<RoleVO> roles;
	private List<GroupVO> groups;
	private List<String> roleIds;
	private List<String> groupIds;
	private List<DeviceVO> devices;

	private int deviceNum;
	private Integer isSystem;

	/**
	 * @return the isSystem
	 */
	public Integer getIsSystem() {
		return isSystem;
	}

	/**
	 * @param isSystem the isSystem to set
	 */
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
	/**
	 * @return deviceNum
	 */
	public int getDeviceNum() {
		return deviceNum;
	}

	/**
	 * @param deviceNum
	 *            要设置的 deviceNum
	 */
	public void setDeviceNum(int deviceNum) {
		this.deviceNum = deviceNum;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @return gander
	 */
	public Integer getGander() {
		return gander;
	}

	/**
	 * @param gander
	 *            要设置的 gander
	 */
	public void setGander(Integer gander) {
		this.gander = gander;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            要设置的 email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            要设置的 mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return userAlias
	 */
	public String getUserAlias() {
		return userAlias;
	}

	/**
	 * @param userAlias
	 *            要设置的 userAlias
	 */
	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	/**
	 * @return the userType
	 */
	public Integer getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * @return createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            要设置的 createTime
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return modifyTime
	 */
	public Timestamp getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            要设置的 modifyTime
	 */
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}

	public List<RoleVO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleVO> roles) {
		this.roles = roles;
	}

	public List<GroupVO> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupVO> groups) {
		this.groups = groups;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public List<String> getGroupIds() {
		return groupIds;
	}

	public void setGroupIds(List<String> groupIds) {
		this.groupIds = groupIds;
	}

	/**
	 * @return devices
	 */
	public List<DeviceVO> getDevices() {
		return devices;
	}

	/**
	 * @param devices
	 *            要设置的 devices
	 */
	public void setDevices(List<DeviceVO> devices) {
		this.devices = devices;
	}

}
