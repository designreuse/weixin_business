/**
 * @Title: ThirdPartConfigVO.java
 * @Package: nari.mip.backstage.service.impl
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-3-23 下午4:13:11
 * @Version: 1.0
 */
package com.cht.emm.vo;

/**
 * @Class: ThirdPartConfigVO
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 第三方接入配置 vo
 */
public class ThirdPartConfigVO {
	
	/**
	 * id
	 */
	private String id;
	
	/**
	 * 第三方名称
	 */
	private String name;
	/**
	 * 远程验证地址
	 */
	private String remoteUrl;
	
	/**
	 * 验证类ID
	 */
	private String classId;
	/**
	 * 验证类的类名
	 */
	private String className;
	
	/**
	 * 组id
	 */
	private String groupId;
	
	/**
	 * 组名
	 */
	private String groupName;
	
	/**
	 * 其他参数
	 */
	private String other;
	
	
	/**

	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the configName
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param configName the configName to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the remoteUrl
	 */
	public String getRemoteUrl() {
		return remoteUrl;
	}
	/**
	 * @param remoteUrl the remoteUrl to set
	 */
	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}
	/**
	 * @return the classId
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * @param classId the classId to set
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * @return the className
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	/**
	 * @return the groupId
	 */
	public String getGroupId() {
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}
	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	/**
	 * @return the others
	 */
	public String getOther() {
		return other;
	}
	/**
	 * @param others the others to set
	 */
	public void setOthers(String other) {
		this.other = other;
	}
}
