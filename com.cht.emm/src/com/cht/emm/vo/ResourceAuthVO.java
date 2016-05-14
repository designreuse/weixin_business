package com.cht.emm.vo;

public class ResourceAuthVO {
	private String id;
	private String subUri;
	private AuthVO auth;
	private boolean selected;
	private ResourceVO resource;
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
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubUri() {
		return subUri;
	}

	public void setSubUri(String subUri) {
		this.subUri = subUri;
	}

	public AuthVO getAuth() {
		return auth;
	}

	public void setAuth(AuthVO auth) {
		this.auth = auth;
	}

	public ResourceVO getResource() {
		return resource;
	}

	public void setResource(ResourceVO resource) {
		this.resource = resource;
	}

}
