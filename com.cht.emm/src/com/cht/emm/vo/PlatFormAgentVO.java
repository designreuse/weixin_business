/**
 * @Title: PlatFormAgentVO.java
 * @Package: nari.mip.backstage.service
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-2-26 上午10:09:17
 * @Version: 1.0
 */
package com.cht.emm.vo;



/**
 * @Class: PlatFormAgentVO
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class PlatFormAgentVO {
	/**
	 * id
	 */
	private String id;
	/**
	 * 操作系统类型
	 */
	private String os;
	/**
	 * 本版号
	 */
	private String versionName;
	/**
	 * 数字化的版本号
	 */
	private int versionNum;
	/**
	 * 安装包存放地址
	 */
	private String url;
	/**
	 * 图标的存放地址
	 */
	private String iconUrl;
	/**
	 * 创建者名称
	 */
	private String createUser;
	
	/**
	 * 创建时间
	 */
	private String createTime;
	
	
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
	 * @return the os
	 */
	public String getOs() {
		return os;
	}
	/**
	 * @param os the os to set
	 */
	public void setOs(String os) {
		this.os = os;
	}
	/**
	 * @return the versionName
	 */
	public String getVersionName() {
		return versionName;
	}
	/**
	 * @param versionName the versionName to set
	 */
	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
	/**
	 * @return the versionNum
	 */
	public int getVersionNum() {
		return versionNum;
	}
	/**
	 * @param versionNum the versionNum to set
	 */
	public void setVersionNum(int versionNum) {
		this.versionNum = versionNum;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the iconUrl
	 */
	public String getIconUrl() {
		return iconUrl;
	}
	/**
	 * @param iconUrl the iconUrl to set
	 */
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	/**
	 * @return the createUser
	 */
	public String getCreateUser() {
		return createUser;
	}
	/**
	 * @param createUser the createUser to set
	 */
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	/**
	 * @return the create_time
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * @param create_time the create_time to set
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
