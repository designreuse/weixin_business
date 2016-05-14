package com.cht.emm.model.id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModel;
import com.cht.emm.model.Application;
import com.cht.emm.model.ApplicationType;


/**
 * 应用和应用分类的关联记录
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_apptype_app")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ApplicationTypeApp extends AbstractModel {

	private static final long serialVersionUID = 6118807749145530834L;
	@Id
	private String id;
	/**
	 * 应用
	 */
	private Application app;
	/**
	 * 所属应用分类
	 */
	private ApplicationType type;

	public ApplicationTypeApp() {

	}

	@Id
	@Column(name = "id", nullable = false, length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_type_id", nullable = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public ApplicationType getType() {
		return type;
	}

	public void setType(ApplicationType type) {
		this.type = type;
	}

	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
