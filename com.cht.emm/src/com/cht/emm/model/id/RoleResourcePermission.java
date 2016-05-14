package com.cht.emm.model.id;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModel;
import com.cht.emm.model.Resource;
import com.cht.emm.model.Role;


/**
 * 
 * @author zhang-kai
 * 中间表  记录role对Resource的操作权限
 */
@Entity
@Table(name="mip_sys_role_resource")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class RoleResourcePermission extends AbstractModel{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = -2734912656910692536L;
	@Id
	private String id;
	private Role role;
	private Resource resource;
	private Integer permission;
	
	
	public RoleResourcePermission(){}
	
	@Id    
	@Column(name="id", length=36)

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="role_id",nullable=true)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@ManyToOne
	@JoinColumn(name="resource_id",nullable=true)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	@Column(name="permission" )
	public Integer getPermission() {
		return permission;
	}
	public void setPermission(Integer permission) {
		this.permission = permission;
	}
	

	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
	
}
