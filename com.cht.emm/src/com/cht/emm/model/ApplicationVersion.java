package com.cht.emm.model;

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
 * 应用版本信息
 * 
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_app_version")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ApplicationVersion extends AbstractModelUnDeletable {

	private static final long serialVersionUID = 6118807749145530834L;
	@Id
	private String id;
	/**
	 * 应用基本信息
	 */
	private Application app;
	/**
	 * 版本名称
	 */
	private String version_name;
	/**
	 * 版本号
	 */
	private int version_code;
	/**
	 * 应用图标地址
	 */
	private String icon;
	/**
	 * 应用下载地址
	 */
	private String url;
	/**
	 * 版本变更说明
	 */
	private String updateDesc;

	public ApplicationVersion() {

	}

	@Id
	@Column(name = "id", length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "app_icon_url", length = 255, nullable = false)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "app_url", length = 255)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_id", nullable = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
	}

	@Column(name = "app_version_name", length = 255)
	public String getVersion_name() {
		return version_name;
	}

	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}

	@Column(name = "app_version_code")
	public int getVersion_code() {
		return version_code;
	}

	public void setVersion_code(int version_code) {
		this.version_code = version_code;
	}

	@Column(name = "app_update_desc", length = 255)
	public String getUpdateDesc() {
		return updateDesc;
	}

	public void setUpdateDesc(String updateDesc) {
		this.updateDesc = updateDesc;
	}

	@Column(name = "deleted", nullable = false)
	@Override
	public Integer getDeleted() {
		return deleted;
	}

	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		// TODO Auto-generated method stub
		return isSystem;
	}
}
