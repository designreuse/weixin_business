package com.cht.emm.model;

// default package

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;
import com.cht.emm.model.id.UserGroup;


/**
 * group entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mip_sys_group")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Group extends AbstractModelUnDeletable {

	/** 
	 * 组织类型的枚举类
	 * 1   组织结构
	 * 2   部门
	 * 3  虚拟组
	 */
	public static enum GROUP_TYPE{
		ORG(1),DEP(2),VG(3);
		private int type;
		private GROUP_TYPE(int i){
			type = i;
		}
		
		public int getType() {
			return type;
		}
	};
	public static enum GROUP_THIRDPART_TYPE{
		NONE(0),TOP(1),RELATE(2);
		private int type;
		private GROUP_THIRDPART_TYPE(int i){
			type = i;
		}
		
		public int getType() {
			return type;
		}
	}
	
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3109542725901840252L;
	@Id
	private String id;
	private Group parentGroup;
	private String groupName;
	private String groupDesc;
	private Integer status;
	private Integer groupType;
	private Set<UserGroup> users = new HashSet<UserGroup>(0);
	private Set<Group> subGroups = new HashSet<Group>(0);
	private ThirdPartConfig config;
	/**
	 * 第三方相关选项，默认是0 不相关
	 * 		1. 第三方平台映射组的挂载点
	 * 		2. 第三方平台映射组 
	 */
	private Integer thirdPartType =0;
	// Constructors

	/** default constructor */
	public Group() {
	}

	/** minimal constructor */
	public Group(String id) {
		this.id = id;
	}

	/** full constructor */
	public Group(String id, Group parentGroup, String groupName,
			String groupDesc, Integer status, Set<UserGroup> users,
			Set<Group> subGroups) {
		this.id = id;
		this.parentGroup = parentGroup;
		this.groupName = groupName;
		this.groupDesc = groupDesc;
		this.status = status;
		this.users = users;
		this.subGroups = subGroups;
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

	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Group getParentGroup() {
		return this.parentGroup;
	}

	public void setParentGroup(Group parentGroup) {
		this.parentGroup = parentGroup;
	}

	@Column(name = "group_name", length = 255)
	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "group_desc", length = 255)
	public String getGroupDesc() {
		return this.groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name = "group_type")
	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "group")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<UserGroup> getUsers() {
		return this.users;
	}

	public void setUsers(Set<UserGroup> users) {
		this.users = users;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "parentGroup", orphanRemoval = false)
	//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<Group> getSubGroups() {
		return this.subGroups;
	}

	public void setSubGroups(Set<Group> subGroups) {
		this.subGroups = subGroups;
	}

	/**
	 * @return the config
	 */
	public ThirdPartConfig getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public void setConfig(ThirdPartConfig config) {
		this.config = config;
	}

	/**
	 * @return the thirdPartType
	 */
	@Column(name = "third_part_type")
	public Integer getThirdPartType() {
		return thirdPartType;
	}

	/**
	 * @param thirdPartType the thirdPartType to set
	 */
	public void setThirdPartType(Integer thirdPartType) {
		this.thirdPartType = thirdPartType;
	}

	
	/**
	 * <p>Title: getDeleted</p> 
	 * <p>Description: </p> 
	 * @return 
	 * @see com.cht.emm.common.model.AbstractModelUnDeletable#getDeleted() 
	 */
	@Override
	@Column(name = "deleted", nullable = false)
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