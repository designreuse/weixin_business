package com.cht.emm.util.objectcopier;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cht.emm.model.User;
import com.cht.emm.model.UserDetail;
import com.cht.emm.model.id.UserRole;
import com.cht.emm.vo.GroupVO;
import com.cht.emm.vo.RoleVO;
import com.cht.emm.vo.UserVO;


public class UserCopier {
	public static  List<UserVO> copy(List<User> users){
		if(users ==null ){
			return null;
		}
		List<UserVO> copies = new ArrayList<UserVO>();
		for (User user : users) {
			UserVO copy = singleCopy(user);
			 copy.setDeviceNum(user.getUserDevices().size());
			 copy.setRoles(RoleCopier.copy(user.getUserRoles()));
			 copy.setGroups(GroupCopier.copy(user.getUserGroups()));
			 copies.add(copy);
		}
		return copies;
	}
	
	public static UserVO copy(User user){
		if(user ==null ){
			return null;
		}
		UserVO copy = singleCopy(user);
		copy.setRoles(RoleCopier.copy(user.getUserRoles()));
		if(copy.getRoles()!=null){
			List<RoleVO> newRoles = new ArrayList<RoleVO>();
			List<String> newRoleIds = new ArrayList<String>();
			for (RoleVO it : copy.getRoles()) {
				//过滤掉系统权限  /user  /admin
				if(it.getIsSystem()!=1){
					newRoles.add(it);
					newRoleIds.add(it.getId());
				}
			}
			copy.setRoles(newRoles);
			copy.setRoleIds(newRoleIds);
		}
		copy.setGroups(GroupCopier.copy(user.getUserGroups()));
		copy.setDevices(DeviceCopier.copy(user.getUserDevices()));
		 
		if(copy.getGroups()!=null){
			List<String> groupIds = new ArrayList<String>();
			for (GroupVO group : copy.getGroups()) {
				groupIds.add(group.getId());
			}
			copy.setGroupIds(groupIds);
		}
		
		 
		return copy;
	}
	
	public static UserVO singleCopy(User user){
		if(user ==null ){
			return null;
		}
		UserVO copy = new UserVO();
		copy.setId(user.getId());
		copy.setUsername(user.getUsername());
		copy.setPassword(user.getPassword());
		UserDetail detail = user.getDetail();
		copy.setEmail(detail.getEmail());
		copy.setMobile(detail.getMobile());
		copy.setUserAlias(detail.getUserAlias());
		copy.setGander(detail.getSex());
		copy.setUserType(user.getUserType());
		copy.setModifyTime(detail.getModifyTime());
		copy.setCreateTime(detail.getCreateTime());
		copy.setStatus(user.getStatus());
		copy.setIsSystem(user.getIsSystem());
		
		return copy;
	}

	public static List<UserVO> copy(Set<UserRole> userRoles) {
		// TODO Auto-generated method stub
		if(userRoles == null ){
			return null;
		}
		List<UserVO> users = new ArrayList<UserVO>();
		for (UserRole ur : userRoles) {
			users.add(singleCopy(ur.getUser()));
		}
		
		return users;
	}
}
