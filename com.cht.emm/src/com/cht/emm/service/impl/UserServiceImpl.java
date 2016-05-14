package com.cht.emm.service.impl;

import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.pagination.PageUtil;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.GroupDao;
import com.cht.emm.dao.RoleDao;
import com.cht.emm.dao.UserDao;
import com.cht.emm.dao.UserDetailDao;
import com.cht.emm.dao.impl.UserDeviceDaoImpl;
import com.cht.emm.model.Resource;
import com.cht.emm.model.Role;
import com.cht.emm.model.User;
import com.cht.emm.model.UserDetail;
import com.cht.emm.model.id.ConfigUser;
import com.cht.emm.model.id.StrategyUser;
import com.cht.emm.model.id.UserDevice;
import com.cht.emm.model.id.UserGroup;
import com.cht.emm.model.id.UserRole;
import com.cht.emm.rpc.OFService;
import com.cht.emm.service.UserService;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.objectcopier.RoleCopier;
import com.cht.emm.util.objectcopier.UserCopier;
import com.cht.emm.vo.RoleVO;
import com.cht.emm.vo.UserVO;

@Service("userService")
public class UserServiceImpl extends BaseService<User, String> implements
		UserService {
	/* (non-Javadoc)
	 * @see nari.mip.backstage.common.service.impl.BaseService#save(nari.mip.backstage.common.model.AbstractModel)
	 */
	@Override
	public User save(User model) {
		// TODO Auto-generated method stub
		try {
			oFService.addUser(model);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.save(model);
	}

	/* (non-Javadoc)
	 * @see nari.mip.backstage.common.service.impl.BaseService#update(nari.mip.backstage.common.model.AbstractModel)
	 */
	@Override
	public void update(User model) {
		// TODO Auto-generated method stub
		try {
			oFService.editUser(model);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.update(model);
	}

	/* (non-Javadoc)
	 * @see nari.mip.backstage.common.service.impl.BaseService#deleteObject(nari.mip.backstage.common.model.AbstractModel)
	 */
	@Override
	public void deleteObject(User model) {
		// TODO Auto-generated method stub
		try {
			oFService.delUser(model);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		super.deleteObject(model);
	}

	@javax.annotation.Resource
	private RoleDao roleDao;

	@javax.annotation.Resource
	UserDetailDao userDetailDao;

	@javax.annotation.Resource(name = "userDao")
	private UserDao userDao;

	@javax.annotation.Resource(name = "userDeviceDaoImpl")
	UserDeviceDaoImpl userDeviceDaoImpl;

	@javax.annotation.Resource
	private GroupDao groupDao;

	@javax.annotation.Resource
	private OFService oFService;
	
	public void setRoleDao(RoleDao roleDa0) {
		this.roleDao = roleDa0;
	}

	public List<RoleVO> findUserRolesByUsername(String username) {

		List<User> user = userDao.getUsersByName(username);
		if (user != null && user.size() > 0) {
			List<RoleVO> roles = new ArrayList<RoleVO>();
			Set<UserRole> urs = user.get(0).getUserRoles();
			if (urs != null && urs.size() > 0) {
				for (UserRole userRole : urs) {
					// userRole.setRole();
					Role role = userRole.getRole();
					System.out.println(role.getRoleName());
					roles.add(RoleCopier.singelCopy(userRole.getRole()));
				}
			}
			return roles;
		}

		return null;
	}

	public User loadUserByUserName(String username) {

		List<User> users = userDao.getUsersByName(username);
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	public List<Resource> findResourcesByRoleId(String roleId) {

		return roleDao.findResourceByRoleId(roleId);

	}

	@Override
	public List<UserVO> getAllUsers() {

		return UserCopier.copy(userDao.listAll());
	}

	@Override
	public UserVO getUserById(String id) {

		return UserCopier.copy(userDao.get(id));
	}

	public void saveUserRole(User user, String roleIds) {

		Set<UserRole> roles = user.getUserRoles();
		List<UserRole> removedUserRoles = new ArrayList<UserRole>();
		List<String> ids = new ArrayList<String>();
		String[] id_s = roleIds.split(",");
		for (String id : id_s) {
			ids.add(id);
		}
		if (roles == null) {
			roles = new HashSet<UserRole>();
		} else {
			for (UserRole ug : roles) {
				String id = ug.getRole().getId();
				if (roleIds.indexOf(id) < 0) {
					removedUserRoles.add(ug);
					ug.delete();
				} else {
					ids.remove(id);
				}
			}
		}

		if (ids.size() > 0) {
			for (String id : ids) {
				UserRole ur = new UserRole();
				ur.setId(UUIDGen.getUUID());
				ur.setRole(roleDao.get(id));
				ur.setUser(user);
				ur.save();
				roles.add(ur);
			}
		}
		System.out.println(roles.size());
		user.setUserRoles(roles);
		user.getUserRoles().removeAll(removedUserRoles);
		user.update();
	}

	public void saveUserGroup(User user, String groupIds) {

		Set<UserGroup> groups = user.getUserGroups();
		// 过滤出新增的UG
		List<String> ids = new ArrayList<String>();

		String[] groupId = groupIds.split(",");
		for (String id : groupId) {
			ids.add(id);
		}
		// 过滤出需要删除的UG
		List<UserGroup> removedUserGroups = new ArrayList<UserGroup>();
		if (groups == null) {
			groups = new HashSet<UserGroup>();
		} else {
			for (UserGroup userGroup : groups) {
				String gid = userGroup.getGroup().getId();
				if (groupIds.indexOf(gid) < 0) {
					removedUserGroups.add(userGroup);
					userGroup.delete();
				} else {
					ids.remove(gid);
				}
			}
		}

		if (ids.size() > 0) {
			for (String id : ids) {
				UserGroup ur = new UserGroup();
				ur.setId(UUIDGen.getUUID());
				ur.setGroup(groupDao.get(id));
				ur.setUser(user);
				ur.save();
				groups.add(ur);
			}
		}
		user.setUserGroups(groups);
		user.getUserGroups().removeAll(removedUserGroups);
		user.update();
	}

	@Override
	public UserVO removeUserGroup(String userId, String groupIds) {

		User user = userDao.get(userId);
		if (user.getUserGroups().size() > 0 && groupIds.length() > 0) {

			Iterator<UserGroup> it = user.getUserGroups().iterator();
			while (it.hasNext()) {
				UserGroup ug = it.next();

				if (groupIds.indexOf(ug.getGroup().getId()) >= 0) {
					ug.delete();
				}
			}
			// user.getUserGroups().clear();
			// user.getUserGroups().addAll(groups);
		}

		return UserCopier.copy(user);
	}

	@Override
	public UserVO removeUserRole(String userId, String roleIds) {
		User user = userDao.get(userId);
		if (user.getUserRoles().size() > 0 && roleIds.length() > 0) {

			Iterator<UserRole> it = user.getUserRoles().iterator();
			while (it.hasNext()) {
				UserRole ug = it.next();
				if (roleIds.indexOf(ug.getRole().getId()) >= 0) {
					ug.delete();
				}
			}
		}

		return UserCopier.copy(user);
	}

	@Override
	public UserVO saveUser(String id, String username, String password,
			String groups, String roles, String userAlias, String mobile,
			String email, Integer sex) {
		User user = null;
		if (id != null) {
			user = get(id);
		} else {
			user = new User();
			user.setId(UUIDGen.getUUID());
			// user.setId(username);
		}

		user.setStatus(1);
		user.setUsername(username);
		user.setPassword(password);
		baseDao.saveOrUpdate(user);

		UserDetail detail = null;
		if (id != null)
			try {
				detail = userDetailDao.get(id);
			} catch (Exception e) {
				e.printStackTrace();
			}

		// System.out.println(detail.getCreateTime());
		boolean created = false;
		if (detail == null || detail.getCreateTime() == null) {
			detail = new UserDetail();
			detail.setCreateTime(new Timestamp(new Date().getTime()));
			user.setDetail(detail);
			created = true;

		} else {
			detail.setModifyTime(new Timestamp(new Date().getTime()));
		}
		detail.setEmail(email);
		detail.setSex(sex);
		detail.setUserAlias(userAlias);
		detail.setMobile(mobile);
		user.setDetail(detail);
		detail.setUser(user);
		if (created) {
			detail.save();
		} else {
			detail.update();
		}
		saveUserGroup(user, groups);
		saveUserRole(user, roles);
		user.update();
		return UserCopier.copy(user);
	}

	@Override
	public List<UserVO> selectUser(String type, String id) {

		return UserCopier.copy(userDao.excludedList(type, id));
	}

	@javax.annotation.Resource(name = "userDao")
	@Override
	public void setBaseDao(IBaseDao<User, String> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public UserVO saveUserGroup(String userId, String groupIds) {
		User user = baseDao.get(userId);
		saveUserGroup(user, groupIds);
		return UserCopier.copy(user);
	}

	@Override
	public UserVO saveUserRole(String userId, String roleId) {
		User user = baseDao.get(userId);
		saveUserRole(user, roleId);
		return UserCopier.copy(user);
	}

	@Override
	public void delete(String[] ids) {
		List<User> users = this.listByIds(ids);
		for (User user : users) {
			List<StrategyUser> strategys = user.getStrategyUsers();
			if (strategys != null) {
				for (StrategyUser strategy : strategys) {
					strategy.delete();
				}
			}
			List<ConfigUser> configs = user.getConfigUsers();
			if (configs != null) {
				for (ConfigUser config : configs) {
					config.delete();
				}
			}
			Set<UserDevice> devices = user.getUserDevices();
			if (devices != null) {
				for (UserDevice device : devices) {
					device.delete();
				}
			}
			Set<UserGroup> groups = user.getUserGroups();
			if (groups != null) {
				for (UserGroup group : groups) {
					group.delete();
				}
			}
			Set<UserRole> roles = user.getUserRoles();
			for (UserRole role : roles) {
				role.delete();
			}
			UserDetail detail = user.getDetail();
			detail.delete();
		}
		super.delete(ids);
	}

	@SuppressWarnings("unchecked")
	public User checkUser(String username, String password) {
		List<User> users = userDao.getSession().createCriteria(User.class)
				.add(Restrictions.eq("username", username))
				.add(Restrictions.eq("password", password)).list();
		if (users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	/**
	 * <p>
	 * Title: queryForPage
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param query
	 * @param orderBy
	 * @param pn
	 * @param pageSize
	 * @return
	 * @see com.cht.emm.service.UserService#queryForPage(com.cht.emm.common.dao.util.ConditionQuery,
	 *      javax.persistence.OrderBy, int, int)
	 */
	@Override
	public Page<UserVO> queryForPage(Integer count, ConditionQuery query,
			OrderBy orderBy, int pn, int pageSize) {
		List<User> users = this.listAll(query, orderBy, pn, pageSize);

		Page<UserVO> page = PageUtil.getPage(count, pn, UserCopier.copy(users),
				pageSize);
		return page;
	}

	/**
	 * <p>
	 * Title: queryForPage
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param countFilter
	 * @param query
	 * @param orderBy
	 * @param pn
	 * @param length
	 * @return
	 * @see com.cht.emm.service.UserService#queryForPage(int,
	 *      com.cht.emm.common.dao.util.ConditionQuery,
	 *      java.lang.StringBuilder, int, java.lang.Integer)
	 */
	@Override
	public Page<UserVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length) {
		List<User> users = this.baseDao.listAll(whereClause, orderby, pn,
				length);
		Page<UserVO> page = PageUtil.getPage(count, pn, UserCopier.copy(users),
				length);
		return page;
	}

	/**
	 * <p>
	 * Title: getDevices
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 * @return
	 * @see com.cht.emm.service.UserService#getDevices(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserDevice> getDevices(String id) {
		// Criteria c =
		// userDeviceDaoImpl.getSession().createCriteria(UserDevice.class);
		// c.add(Restrictions.eq("user.id", id));
		// //c.add(Restrictions.eq("status", 1));
		// return userDeviceDaoImpl.list( c);
		return userDeviceDaoImpl.getSession().createCriteria(UserDevice.class)
				.add(Restrictions.eq("user.id", id)).list();
	}

	@Override
	public boolean isAdmin(String userId) {
		boolean isAdmin =false;
		
		return isAdmin;
	}

}
