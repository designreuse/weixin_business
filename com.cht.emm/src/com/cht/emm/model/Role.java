package com.cht.emm.model;

// default package

import java.util.HashSet;
import java.util.Set;
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
import com.cht.emm.model.id.RoleResourcePermission;
import com.cht.emm.model.id.UserRole;


/**
 * Role entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mip_sys_role")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Role extends AbstractModelUnDeletable {

	private static final long serialVersionUID = -266056677904739036L;
	@Id
	private String id;
	private String roleName;
	private String roleDesc;
	private Integer userType=2;
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	
	private Set<RoleResourcePermission> resourcePermissions = new HashSet<RoleResourcePermission>(
			0);

	// private Set<ResourceAuth> resourceAuths = new HashSet<ResourceAuth>(0);

	// Constructors

	/** default constructor */
	public Role() {
		super();
	}

	/** minimal constructor */
	public Role(String id, String roleName) {
		this.id = id;
		this.roleName = roleName;
	}

	/** full constructor */
	public Role(String id, String roleName, Set<UserRole> userRoles,
			Set<RoleResourcePermission> resourcePermissions) {
		this.id = id;
		this.roleName = roleName;
		this.userRoles = userRoles;
		this.resourcePermissions = resourcePermissions;
	}

	// Property accessors
	@Id
	@Column(name = "id", length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 255)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "description", nullable = true, length = 255)
	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	/**
	 * @return the userType
	 */
	@Column(name="user_type", nullable=false)
	public Integer getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "role")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "role")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<RoleResourcePermission> getResourcePermissions() {
		return this.resourcePermissions;
	}

	public void setResourcePermissions(
			Set<RoleResourcePermission> resourcePermissions) {
		this.resourcePermissions = resourcePermissions;
	}

	@Column(name = "deleted", nullable = false)
	/**
	 * <p>Title: getDeleted</p> 
	 * <p>Description: </p> 
	 * @return 
	 * @see nari.mip.backstage.common.model.AbstractModelUnDeletable#getDeleted() 
	 */
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