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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;
import com.cht.emm.model.id.ResourceAuth;
import com.cht.emm.model.id.RoleResourcePermission;


/**
 * Resource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mip_sys_resource")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Resource extends AbstractModelUnDeletable {

	// Fields
	/**
	 * resource type
	 */
	public static enum ResourceType{
		FILE(2),URL
		
		(1);
		int i;
		private ResourceType(int i){
			this.i = i;
		}
		public int getType(){
			return i;
		}
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -3805850022517747919L;
	@Id
	private String id;
	private String name;
	private String uri;
	private Integer type;
	private Integer permission;
	private Integer isItem;
	private MenuItem item;
	private Set<RoleResourcePermission> resourcePermissions = new HashSet<RoleResourcePermission>(
			0);
	private Set<ResourceAuth> resourceAuths = new HashSet<ResourceAuth>(0);

	// Constructors

	/** default constructor */
	public Resource() {
		super();
	}

	/** minimal constructor */
	public Resource(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public Resource(String id, String name, String uri, Integer type,
			Integer permission, Set<RoleResourcePermission> resourcePermissions) {
		this.id = id;
		this.name = name;
		this.uri = uri;
		this.type = type;
		this.permission = permission;
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
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "uri", length = 255)
	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "permission")
	public Integer getPermission() {
		return this.permission;
	}

	public void setPermission(Integer permission) {
		this.permission = permission;
	}
	 
	@Column(name = "is_item")
	/**
	 * @return the isItem
	 */
	public Integer getIsItem() {
		return isItem;
	}

	/**
	 * @param isItem the isItem to set
	 */
	public void setIsItem(Integer isItem) {
		this.isItem = isItem;
	}

	@OneToOne(mappedBy="resource")   
	public MenuItem getItem() {
		return item;
	}

	public void setItem(MenuItem item) {
		this.item = item;
	}
	
	@OneToMany(mappedBy = "resource", cascade = { CascadeType.REFRESH,
			CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<ResourceAuth> getResourceAuths() {
		return resourceAuths;
	}

	public void setResourceAuths(Set<ResourceAuth> resourceAuths) {
		this.resourceAuths = resourceAuths;
	}

	@OneToMany(mappedBy = "resource", cascade = { CascadeType.REFRESH,
			CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<RoleResourcePermission> getResourcePermissions() {
		return this.resourcePermissions;
	}

	public void setResourcePermissions(
			Set<RoleResourcePermission> resourcePermissions) {
		this.resourcePermissions = resourcePermissions;
	}
	
	/**
	 * <p>Title: getDeleted</p> 
	 * <p>Description: </p> 
	 * @return 
	 * @see com.cht.emm.common.model.AbstractModelUnDeletable#getDeleted() 
	 */
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