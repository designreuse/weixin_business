package com.cht.emm.model;

// default package

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;
import com.cht.emm.model.id.ConfigUser;
import com.cht.emm.model.id.StrategyUser;
import com.cht.emm.model.id.UserDevice;
import com.cht.emm.model.id.UserGroup;
import com.cht.emm.model.id.UserRole;


/**
 * User entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mip_sys_user")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class User extends AbstractModelUnDeletable {

	public static enum UserType{
		COMMON(4),DEPART_MASTER(3),ORG_MASTER(2),SUPER_USER(1);
		int i;
		private UserType(int i){
			this.i = i;
		}
		public int getType(){
			return i;
		}
	}
	
	
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8577958602838856429L;

	@Id
	private String id;
	private String username;
	private String password;
	private Integer status;
	private Integer userType;
	private UserDetail detail;
	private Set<UserGroup> userGroups = new HashSet<UserGroup>(0);
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);
	private Set<UserDevice> userDevices = new HashSet<UserDevice>();
	private List<ConfigUser> configUsers = new ArrayList<ConfigUser>();
	private List<StrategyUser> strategyUsers = new ArrayList<StrategyUser>();
	private Integer userSource = 0;
	private ThirdPartConfig thirdPartConfig;

	// Constructors

	/** default constructor */
	public User() {
		super();
	}

	public User(String id) {
		this.id = id;
	}

	/** minimal constructor */
	public User(String id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	/** full constructor */
	public User(String id, String username, String password, Integer status,
			Set<UserGroup> userGroups, Set<UserRole> userRoles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.status = status;
		this.userGroups = userGroups;
		this.userRoles = userRoles;
	}

	@Id
	@Column(name = "id", length = 36)
	@JoinColumn(name = "id")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @Name: getUsername
	 * @Decription: 当为第三方用户时采用 username||thirdpairtconfig_id的方式
	 * @Time: 2015-3-4 下午4:07:13
	 * @return String
	 */
	@Column(name = "username", nullable = false, length = 255)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 255)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer i) {
		this.status = i;
	}

	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	@OneToOne(mappedBy = "user", optional = false)
	/**
	 * @return   detail
	 */
	public UserDetail getDetail() {
		return detail;
	}

	/**
	 * @param detail
	 *            要设置的 detail
	 */
	public void setDetail(UserDetail detail) {
		this.detail = detail;
	}
	@Column(name = "user_type")
	/**
	 * @return the userType
	 */
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
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "user")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<UserGroup> getUserGroups() {
		return this.userGroups;
	}

	public void setUserGroups(Set<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "user")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "user")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	/**
	 * @return   userDevices
	 */
	public Set<UserDevice> getUserDevices() {
		return userDevices;
	}

	/**
	 * @param userDevices
	 *            要设置的 userDevices
	 */
	public void setUserDevices(Set<UserDevice> userDevices) {
		this.userDevices = userDevices;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "user")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public List<ConfigUser> getConfigUsers() {
		return configUsers;
	}

	public void setConfigUsers(List<ConfigUser> configUsers) {
		this.configUsers = configUsers;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "user")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public List<StrategyUser> getStrategyUsers() {
		return strategyUsers;
	}

	public void setStrategyUsers(List<StrategyUser> strategyUsers) {
		this.strategyUsers = strategyUsers;
	}

	/**
	 * @return the userSource
	 */
	@Column(name = "user_resource", nullable = false)
	public Integer getUserSource() {
		return userSource;
	}

	/**
	 * @param userSource the userSource to set
	 */
	public void setUserSource(Integer userSource) {
		this.userSource = userSource;
	}

	/**
	 * @return the thirdPartConfig
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "third_part_id", nullable = true)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public ThirdPartConfig getThirdPartConfig() {
		return thirdPartConfig;
	}

	/**
	 * @param thirdPartConfig the thirdPartConfig to set
	 */
	public void setThirdPartConfig(ThirdPartConfig thirdPartConfig) {
		this.thirdPartConfig = thirdPartConfig;
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