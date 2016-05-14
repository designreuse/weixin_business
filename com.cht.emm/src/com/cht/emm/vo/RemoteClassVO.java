/**
 * @Title: RemoteClassVO.java
 * @Package: nari.mip.backstage.vo
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-4-2 下午4:20:49
 * @Version: 1.0
 */
package com.cht.emm.vo;

/**
 * @Class: RemoteClassVO
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class RemoteClassVO {

	private String id;
	private String className;
	private String packageName;
	private String classDesc;
	private String content;
	private Integer enabled;
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
	 * @return the packageName
	 */
	public String getPackageName() {
		return packageName;
	}
	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	/**
	 * @return the classDesc
	 */
	public String getClassDesc() {
		return classDesc;
	}
	/**
	 * @param classDesc the classDesc to set
	 */
	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return the enabled
	 */
	public Integer getEnabled() {
		return enabled;
	}
	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
}
