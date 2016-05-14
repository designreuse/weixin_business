package com.cht.emm.vo;

public class AuthVO {
	private String id;
	private String name;
	private Integer showIndex;
	private Integer locIndex;
	private String descp;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}

	public Integer getLocIndex() {
		return locIndex;
	}

	public void setLocIndex(Integer locIndex) {
		this.locIndex = locIndex;
	}

	public String getDescp() {
		return descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}
}
