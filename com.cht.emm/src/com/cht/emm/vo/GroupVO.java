package com.cht.emm.vo;

import java.io.Serializable;
import java.util.List;

public class GroupVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3333484225355526654L;
	private String id;
	private GroupVO parentGroup;
	private String groupName;
	private String groupDesc;
	private String groupType;
	/**
	 * 组与第三方相关的部分，
	 * 0表示不相关
	 * 1: 第三方接入的顶级组，不可删除
	 * 2: 第三方接入平台的映射组，删除之类的操作交由第三方接入配置管理处理，此处只能修改名称
	 * 
	 */
	private Integer thirdPartType;
	private Integer status;
	private List<GroupVO> subGroups;
	private List<UserVO> users;
	private Integer isSystem;

	/**
	 * @return the isSystem
	 */
	public Integer getIsSystem() {
		return isSystem;
	}

	/**
	 * @param isSystem the isSystem to set
	 */
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GroupVO getParentGroup() {
		return parentGroup;
	}

	public void setParentGroup(GroupVO parentGroup) {
		this.parentGroup = parentGroup;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}

	/**
	 * @return the groupType
	 */
	public String getGroupType() {
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	/**
	 * @return the thirdPartType
	 */
	public Integer getThirdPartType() {
		return thirdPartType;
	}

	/**
	 * @param thirdPartType the thirdPartType to set
	 */
	public void setThirdPartType(Integer thirdPartType) {
		this.thirdPartType = thirdPartType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<GroupVO> getSubGroups() {
		return subGroups;
	}

	public void setSubGroups(List<GroupVO> subGroups) {
		this.subGroups = subGroups;
	}

	public List<UserVO> getUsers() {
		return users;
	}

	public void setUsers(List<UserVO> users) {
		this.users = users;
	}
}
