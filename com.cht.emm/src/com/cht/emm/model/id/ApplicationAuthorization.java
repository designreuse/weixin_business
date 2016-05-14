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
import com.cht.emm.model.Group;


/**
 * 应用的授权记录
 * 
 * 
 * @author luoyupan
 * 
 */
@Entity
@Table(name = "mip_sys_app_authorize")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ApplicationAuthorization extends AbstractModel {

	private static final long serialVersionUID = 6118807749145530834L;
	@Id
	private String id;
	/**
	 * 受权组织
	 */
	private Group group;
	/**
	 * 操作应用
	 */
	private Application app;
	/**
	 * 权限类型（如：允许安装、禁止安装等）
	 */
	private int type;

	public ApplicationAuthorization() {

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
	@JoinColumn(name = "group_id", nullable = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
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

	@Column(name = "type", nullable = false)
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
