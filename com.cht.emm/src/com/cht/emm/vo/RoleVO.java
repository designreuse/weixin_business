package com.cht.emm.vo;

import java.io.Serializable;
import java.util.List;

public class RoleVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4860417521622662519L;
	private String id;
	private String roleName;
	private String roleDesc;
	private int userNum;
	private Integer status = 1;
	private List<UserVO> users;
	private List<ResourceOpsVO> resources;
	
	private List<String> userTypes;
	private Integer userType;
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
	 * @param userNum
	 *            要设置的 userNum
	 */
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	/**
	 * @return userNum
	 */
	public int getUserNum() {
		return userNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<UserVO> getUsers() {
		return users;
	}

	public void setUsers(List<UserVO> users) {
		this.users = users;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<ResourceOpsVO> getResources() {
		return resources;
	}

	public void setResources(List<ResourceOpsVO> resources) {
		this.resources = resources;
	}

	/**
	 * @return the userTypes
	 */
	public List<String> getUserTypes() {
		return userTypes;
	}

	/**
	 * @param userTypes the userTypes to set
	 */
	public void setUserTypes(List<String> userTypes) {
		this.userTypes = userTypes;
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

}
