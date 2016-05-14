/**
 * @Title: PlatFormAgent.java
 * @Package: nari.mip.backstage.model
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-2-25 下午5:01:37
 * @Version: 1.0
 */
package com.cht.emm.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;



/**
 * @Class: PlatFormAgent
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
@Entity
@Table(name = "mip_sys_platform_agent")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class PlatFormAgent extends AbstractModelUnDeletable{

	public static final int OS_ANDROID=0;
	public static final int OS_APPLE=1;
	public static final int OS_OTHER=2;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7605343280783792934L;
	/**
	 * id
	 */
	@Id
	private String id;
	/**
	 * 软件包名
	 */
	private String packageName;
	/**
	 * 版本名称
	 */
	private String versionName;
	/**
	 * 版本号
	 */
	private Integer versionCode;
	/**
	 * 安装包的路径
	 */
	private String url;

	/**
	 * 路径
	 */
	private String path;
	/**
	 * 图标路径
	 */
	private String iconUrl;
	/**
	 * 创建者
	 */
	private User creator;
	/**
	 * 系统类型
	 */
	private Integer os;
	
	/**
	 * 创建时间
	 */
	private Timestamp createTime;
	
	
	
	
	/**
	 * @return the id
	 */
	@Id
	@Column(name="id",length=36)
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
	 * @return the packageName
	 */
	@Column(name="package_name",nullable=false,length=255)
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
	 * @return the versionName
	 */
	@Column(name="version_name",nullable=false,length=64)
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
	 * @return the versionCode
	 */
	@Column(name="version_code" )
	public Integer getVersionCode() {
		return versionCode;
	}
	/**
	 * @param versionCode the versionCode to set
	 */
	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}
	/**
	 * @return the creator
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_publisher")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public User getCreator() {
		return creator;
	}
	/**
	 * @param creator the creator to set
	 */
	@Column(name="version",nullable=false)
	public void setCreator(User creator) {
		this.creator = creator;
	}
	/**
	 * @return the os
	 */
	@Column(name="os_type",nullable=false)
	public Integer getOs() {
		return os;
	}
	/**
	 * @param os the os to set
	 */
	public void setOs(Integer os) {
		this.os = os;
	}
	/**
	 * @return the url
	 */
	@Column(name="url",nullable=false,length=256)
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name="path",nullable=false,length=256)
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}
	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}
	/**
	 * @return the iconUrl
	 */
	@Column(name="icon_url",nullable=false,length=256)
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
	 * @return the createTime
	 */
	@Column(name = "create_time", nullable = false)
	public Timestamp getCreateTime() {
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "deleted", nullable = false)
	@Override
	public Integer getDeleted() {
		return deleted;
	}
	@Column(name = "is_system", nullable = false)
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
