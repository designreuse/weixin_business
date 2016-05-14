package com.cht.emm.vo;

import java.util.List;

public class RoleOpsVO {
	private String id;
	private RoleVO role;
	private List<ResourceAuthVO> resourceAuth;
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
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RoleVO getRole() {
		return role;
	}

	public void setRole(RoleVO role) {
		this.role = role;
	}

	public List<ResourceAuthVO> getResourceAuth() {
		return resourceAuth;
	}

	public void setResourceAuth(List<ResourceAuthVO> resourceAuth) {
		this.resourceAuth = resourceAuth;
	}

}
