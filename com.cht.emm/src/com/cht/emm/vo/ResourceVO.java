package com.cht.emm.vo;

import java.util.List;

public class ResourceVO {
	private String id;
	private String name;
	private String uri;
	private Integer type;
	private Integer isItem;
	private Integer permission;
	private List<RoleOpsVO> roles;
	private List<ResourceAuthVO> resourceAuths;
	private Integer status;
	private List<String> authIds;
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
	public List<String> getAuthIds() {
		return authIds;
	}

	public void setAuthIds(List<String> authIds) {
		this.authIds = authIds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @return the isItem
	 */
	public Integer getIsItem() {
		return isItem;
	}

	/**
	 * @param isItem the isItem to set
	 */
	public void setIsItem(Integer isItem) {
		this.isItem = isItem;
	}

	public Integer getPermission() {
		return permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}

	public List<RoleOpsVO> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleOpsVO> roles) {
		this.roles = roles;
	}

	public List<ResourceAuthVO> getResourceAuths() {
		return resourceAuths;
	}

	public void setResourceAuths(List<ResourceAuthVO> resourceAuths) {
		this.resourceAuths = resourceAuths;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
