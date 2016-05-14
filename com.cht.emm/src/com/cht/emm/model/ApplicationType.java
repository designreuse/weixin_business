package com.cht.emm.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;
import com.cht.emm.model.id.ApplicationTypeApp;


/**
 * 应用分类信息
 * 
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_app_type")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ApplicationType extends AbstractModelUnDeletable {

	private static final long serialVersionUID = 6118807749145530834L;
	@Id
	private String id;
	/**
	 * 应用分类名称
	 */
	private String name;
	/**
	 * 应用分类描述
	 */
	private String description;
	/**
	 * 应用和应用分类的关联记录
	 */
	private List<ApplicationTypeApp> appTypeApps = new ArrayList<ApplicationTypeApp>();

	public ApplicationType() {

	}

	@Id
	@Column(name = "id", length = 36)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", length = 255, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "description", length = 255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "type")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public List<ApplicationTypeApp> getAppTypeApps() {
		return appTypeApps;
	}

	public void setAppTypeApps(List<ApplicationTypeApp> appTypeApps) {
		this.appTypeApps = appTypeApps;
	}

	@Column(name = "deleted", nullable = false)
	@Override
	public Integer getDeleted() {
		return deleted;
	}
	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}

}
