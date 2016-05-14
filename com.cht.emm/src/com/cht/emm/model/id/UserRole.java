package com.cht.emm.model.id;

// default package

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
import com.cht.emm.model.Role;
import com.cht.emm.model.User;


/**
 * UserRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mip_sys_user_role")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UserRole extends AbstractModel {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -4533125019062654373L;
	@Id
	private String id;
	private User user;
	private Role role;

	// Constructors

	/** default constructor */
	public UserRole() {
	}

	/** full constructor */
	public UserRole(String id, User user, Role role) {
		this.id = id;
		this.user = user;
		this.role = role;
	}

	// Property accessors
	@Id
	@Column(name = "id",length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}