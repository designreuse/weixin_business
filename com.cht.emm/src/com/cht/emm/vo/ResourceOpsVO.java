package com.cht.emm.vo;

import java.util.List;

public class ResourceOpsVO {
	private String id;
	private ResourceVO resource;
	private List<ResourceAuthVO> resourceAuths;
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

	public ResourceVO getResource() {
		return resource;
	}

	public void setResource(ResourceVO resource) {
		this.resource = resource;
	}

	public List<ResourceAuthVO> getResourceAuths() {
		return resourceAuths;
	}

	public void setResourceAuths(List<ResourceAuthVO> resourceAuths) {
		this.resourceAuths = resourceAuths;
	}
}
