package com.cht.emm.service.impl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.GroupDao;
import com.cht.emm.dao.UserDao;
import com.cht.emm.dao.UserGroupDao;
import com.cht.emm.model.Group;
import com.cht.emm.model.User;
import com.cht.emm.model.id.UserGroup;
import com.cht.emm.service.UserGroupService;
import com.cht.emm.util.StringUtil;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.objectcopier.GroupCopier;
import com.cht.emm.util.objectcopier.UserCopier;
import com.cht.emm.vo.GroupVO;
import com.cht.emm.vo.UserVO;


@Service("userGroupService")
public class UserGroupServiceImpl extends BaseService<UserGroup, String> implements
		UserGroupService {
	@Resource
	UserDao userDao;
	
	@Resource
	GroupDao groupDao;
	
	
	@Resource (name="userGroupDao")
	UserGroupDao userGroupDao;
	@Override
	public void setBaseDao(IBaseDao<UserGroup, String> baseDao) {
		// TODO Auto-generated method stub
		this.baseDao =baseDao;
	}

	@Override
	public void deleteUnionEntityA2B(String pk1, String[] pk2s) {
		// TODO Auto-generated method stub
		for (String spk : pk2s) {
			String pk = ((UserGroupDao) baseDao).getPK(pk1,spk);
			if(pk!=null){
				delete(pk);
			}
		}
	}

	@Override
	public void deleteUnionEntityB2A(String pk1, String[] pk2s) {
		// TODO Auto-generated method stub
		for (String spk : pk2s) {
			String pk = ((UserGroupDao) baseDao).getPK(spk,pk1);
			if(pk!=null){
				delete(pk);
			}
		}
	}

	@Override
	public UserVO addUserGroup(String userId, String groupIds) {
		// TODO Auto-generated method stub
		User user = userDao.get(userId);
		Set<UserGroup> ugs =user.getUserGroups();
		List<String> existGroups = new ArrayList<String>();
		for (UserGroup userGroup : ugs) {
			existGroups.add(userGroup.getGroup().getId());
		}
		List<String> newGroups =StringUtil.getList(groupIds.split(","));
		List<String> keep = new ArrayList<String>();
		//新增的
		for (String string : newGroups) {
			if(!existGroups.contains(string)){
				UserGroup ug  = new UserGroup();
				ug.setId(UUIDGen.getUUID());
				ug.setUser(user);
				ug.setGroup(groupDao.get(string));
				ug.save();
				if(user.getUserGroups()== null){
					user.setUserGroups(new HashSet<UserGroup>());
				}
				user.getUserGroups().add(ug);
			}else{
				keep.add(string);
			}
		}
		// 删除的
		existGroups.removeAll(keep);
		if(existGroups.size()>0){
			for (String id : existGroups) {
				String pk = userGroupDao.getPK(userId, id);
				UserGroup deleteGroup  = userGroupDao.get(pk);
				user.getUserGroups().remove(deleteGroup);
				deleteGroup.delete();
			}
		}
		user.save();
//		 
		
		return UserCopier.copy(user);
	}

	@Override
	public GroupVO addGroupUser(String groupId, String userIds) {
		// TODO Auto-generated method stub
		Group group = groupDao.get(groupId);
		List<User> users = userDao.listByIds(userIds.split(","));
		for (User user : users) {
			UserGroup ug  = new UserGroup();
			ug.setId(UUIDGen.getUUID());
			ug.setUser(user);
			ug.setGroup(group);
			if(group.getUsers()== null){
				group.setUsers(new HashSet<UserGroup>());
			}
			group.getUsers().add(ug);
			ug.save();
		}
		return GroupCopier.copy(group);
	}

	@Override
	public GroupVO removeGroupUser(String groupId, String userIds) {
		// TODO Auto-generated method stub
		String[] userids =  userIds.split(",");
		this.deleteUnionEntityB2A(groupId, userids);
		return GroupCopier.copy(groupDao.get(groupId));
	}
 

}
