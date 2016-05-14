package com.cht.emm.util.objectcopier;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cht.emm.model.Group;
import com.cht.emm.model.id.UserGroup;
import com.cht.emm.vo.GroupVO;
import com.cht.emm.vo.UserVO;


public class GroupCopier  {

	public static List<GroupVO> copy(Set<UserGroup> groups) {
		// TODO Auto-generated method stub
		if(groups ==null ){
			return null;
		}
		List<GroupVO> copies = new ArrayList<GroupVO>();
	    for (UserGroup userGroup : groups) {
	    	GroupVO copy = new GroupVO();
	    	Group group = userGroup.getGroup();
	    	copy.setId(group.getId());
	    	copy.setGroupDesc(group.getGroupDesc());
	    	copy.setGroupName(group.getGroupName());
			copies.add(copy);
		}
		return copies;
	}

	public static List<GroupVO> copy(List<Group> groups) {
		// TODO Auto-generated method stub
		if(groups ==null ){
			return null;
		}
		List<GroupVO> copies = new ArrayList<GroupVO>();
	    for (Group group : groups) {
	    	GroupVO copy = new GroupVO();
	    	copy.setId(group.getId());
	    	copy.setGroupDesc(group.getGroupDesc());
	    	copy.setGroupName(group.getGroupName());
	    	copy.setParentGroup(GroupCopier.singleCopy(group.getParentGroup()));
	    	copy.setSubGroups(GroupCopier.copySub(group.getSubGroups()));
			copies.add(copy);
		}
		return copies;
		
	}

	private static List<GroupVO> copySub(Set<Group> subGroups) {
		// TODO Auto-generated method stub
		if(subGroups ==null ){
			return null;
		}
		List<GroupVO> copies = new ArrayList<GroupVO>();
		for (Group group : subGroups) {
			copies.add(singleCopy(group));
		}
		return copies;
	}

	public static GroupVO copy(Group group) {
		if(group ==null ){
			return null;
		}
		// TODO Auto-generated method stub
		GroupVO copy =  singleCopy(group);
    	if(group.getParentGroup()!=null){
    		Group paGroup = group.getParentGroup();
    		GroupVO parentGroup = singleCopy(paGroup);
    		copy.setParentGroup(parentGroup);
    	}
    	
    	if(group.getSubGroups()!=null){
    		Set<Group> subGroups = group.getSubGroups();
    		List<GroupVO> sGroups = new ArrayList<GroupVO>();
    		for (Group group2 : subGroups) {
				sGroups.add(singleCopy(group2));
			}
    		copy.setSubGroups(sGroups);
    	}
    	if(group.getUsers()!= null ){
    		List<UserVO> cusers = new ArrayList<UserVO>();
    		Set<UserGroup> users = group.getUsers();
    		for (UserGroup userGroup : users) {
				cusers.add(UserCopier.singleCopy(userGroup.getUser()));
			}
    		copy.setUsers(cusers);
    		
    	}
		return copy;
	}

	public static GroupVO singleCopy(Group group){
		if(group ==null ){
			return null;
		}
		GroupVO copy = new GroupVO();
		copy.setId(group.getId());
		copy.setGroupName(group.getGroupName());
		copy.setGroupDesc(group.getGroupDesc());
		copy.setStatus(group.getStatus());
		copy.setIsSystem(group.getIsSystem());
		Integer type = group.getGroupType();
		if(type !=null ){
			switch (type) {
			case 1:
				copy.setGroupType("机构");
				break;
			case 2:
				copy.setGroupType("部门");
				break;
			case 3:
				copy.setGroupType("虚拟组");
				break;
			default:
				break;
			}
		}else {
			copy.setGroupType("默认");
		}
		
		/**
		 * add by zhangkai3@sgepri.sgcc.com.cn
		 * 对third_part_config_type的支持
		 */
		
		if(group.getThirdPartType() != null){
			copy.setThirdPartType(group.getThirdPartType());
		}
		 
		return copy;
	}
}
