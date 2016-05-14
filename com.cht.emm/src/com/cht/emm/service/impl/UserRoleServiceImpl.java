package com.cht.emm.service.impl;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.AuthorityDao;
import com.cht.emm.dao.RoleDao;
import com.cht.emm.dao.UserDao;
import com.cht.emm.dao.UserRoleDao;
import com.cht.emm.model.Role;
import com.cht.emm.model.User;
import com.cht.emm.model.id.UserRole;
import com.cht.emm.service.UserRoleService;
import com.cht.emm.util.StringUtil;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.objectcopier.RoleCopier;
import com.cht.emm.util.objectcopier.UserCopier;
import com.cht.emm.vo.RoleVO;
import com.cht.emm.vo.UserVO;



@Service("userRoleService")
public class UserRoleServiceImpl extends BaseService<UserRole, String> implements
		UserRoleService {

	@Resource(name="userDao")
	private UserDao userDao;
	
	@Resource(name="roleDao")
	private RoleDao roleDao;
	
	@Resource(name="authorityDao")
	private AuthorityDao authorityDao;
	
	
	@Resource (name="userRoleDao")	
	UserRoleDao userRoleDao;
	@Override
	public void setBaseDao(IBaseDao<UserRole, String> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void deleteUnionEntityA2B(String pk1, String[] pk2s) {
		for (String spk : pk2s) {
			String pk = ((UserRoleDao) baseDao).getPK(pk1, spk);
			if(pk!=null){
				delete(pk);
			}
		}
	}

	@Override
	public void deleteUnionEntityB2A(String pk1, String[] pk2s) {
		for (String spk : pk2s) {
			String pk = ((UserRoleDao) baseDao).getPK(spk,pk1);
			if(pk!=null){
				delete(pk);
			}
		}
	}
	@Override
	public UserVO addUserRole(String userId, String roleIds){
		User  user = userDao.get(userId);
		String[] roles = roleIds.split(",");
		for (String roleId : roles) {
			UserRole ur = new UserRole();
			ur.setId(UUIDGen.getUUID());
			ur.setRole(roleDao.get(roleId));
			ur.setUser(user);
			ur.save();
			user.getUserRoles().add(ur);
		}
		user.update();
		return UserCopier.copy(user);
	}
	
	@Override
	public UserVO updateUserRole(String userId, String roleIds) {
		User  user = userDao.get(userId);
		List<String> newRole = StringUtil.getList(roleIds.split(","));
		List<String> exists = new ArrayList<String>();
		Set<UserRole> urs = user.getUserRoles();
		for (UserRole userRole : urs) {
			exists.add(userRole.getRole().getId());
		}
		List<String> keeps = new ArrayList<String>();
		for (String	id : newRole) {
			if(!exists.contains(id)){
				UserRole ur = new UserRole();
				ur.setId(UUIDGen.getUUID());
				ur.setRole(roleDao.get(id));
				ur.setUser(user);
				ur.save();
				if(user.getUserRoles()==null){
					user.setUserRoles(new HashSet<UserRole>());
				}
				user.getUserRoles().add(ur);
			}else {
				keeps.add(id);
			}
		}
		//去掉存在的剩下的就是需要删除的
		exists.removeAll(keeps);
		
		// 删除
		if(exists.size() > 0){
			for (String id : exists) {
				String pk = userRoleDao.getPK(userId, id);
				UserRole ur = userRoleDao.get(pk);
				user.getUserRoles().remove(ur);
				ur.delete();
			}
		}
		
		user.update();
		return UserCopier.copy(user);
	}

	@Override
	public RoleVO removeRoleUser(String roleId, String userIds) {
		this.deleteUnionEntityB2A(roleId, userIds.split(","));
		return RoleCopier.copy(roleDao.get(roleId),authorityDao.listAll());
	}

	@Override
	public RoleVO addRoleUser(String roleId, String userIds) {
		Role  role = roleDao.get(roleId);
		List<User> users = userDao.listByIds(userIds.split(","));
		for (User user : users) {
			UserRole ur = new UserRole();
			ur.setId(UUIDGen.getUUID());
			ur.setRole(role);
			ur.setUser(user);
			ur.save();
			if(role.getUserRoles()==null){
				role.setUserRoles(new HashSet<UserRole>());
			}
			role.getUserRoles().add(ur);
		}
		return RoleCopier.copy(role,authorityDao.listAll());
	}

	@Override
	public String getPK(String userId, String roleId) {
		return userRoleDao.getPK(userId, roleId);
	}
}
