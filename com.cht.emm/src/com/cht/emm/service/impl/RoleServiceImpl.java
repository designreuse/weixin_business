package com.cht.emm.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.pagination.PageUtil;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.AuthorityDao;
import com.cht.emm.dao.RoleDao;
import com.cht.emm.dao.UserDao;
import com.cht.emm.model.Role;
import com.cht.emm.model.User;
import com.cht.emm.model.id.RoleResourcePermission;
import com.cht.emm.model.id.UserRole;
import com.cht.emm.service.RoleService;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.objectcopier.RoleCopier;
import com.cht.emm.vo.RoleVO;


@Service("roleService")
public class RoleServiceImpl extends BaseService<Role, String> implements RoleService {
	
	@Resource
	RoleDao roleDao;
	
	@Resource
	AuthorityDao authorityDAO;
	
	@Resource
	UserDao userDAO;
	
	
	@Override
	public void delete(String[] ids) {
		List<Role> roles = this.listByIds(ids);
		for (Role role : roles) {
			Set<UserRole> users = role.getUserRoles();
			if( users !=null ){
				for (UserRole userRole : users) {
					userRole.delete();
				}
			}
			
			Set<RoleResourcePermission> resources = role.getResourcePermissions();
			if(resources != null){
				for (RoleResourcePermission resource : resources) {
					resource.delete();
				}
			}
		}
		super.delete(ids);
	}
	
	@Override
	public List<RoleVO> selectRoles(String excluded,String excludedId){
		if(excluded == null){
			return RoleCopier.copy(roleDao.listAll());
		}else{
			return RoleCopier.copy(roleDao.excludedList(excluded,excludedId));
		}
	}
	@Override
	public List<RoleVO> getAllRoles() {
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.ne("isSystem", 1));
		return RoleCopier.copy(roleDao.listAll(query,null,-1,-1));
	}

	@Override
	public RoleVO getById(String id) {
		return RoleCopier.copy(roleDao.get(id),authorityDAO.listAll());
	}
	@Override
	public RoleVO addRole(String roleName, String roleDesc) {
		Role role =new Role();
		role.setId(UUIDGen.getUUID());
		role.setRoleName(roleName);
		role.setRoleDesc(roleDesc);
		role.save();
		return RoleCopier.copy(role,authorityDAO.listAll());
	}
	@Override
	public RoleVO saveUserRole(String roleId, String userIds) {
		Role role = roleDao.get(roleId);
		List<User> users = userDAO.listByIds(userIds.split(","));
		for (User user : users) {
			UserRole ur = new UserRole();
			ur.setId(UUIDGen.getUUID());
			ur.setUser(user);
			ur.setRole(role);
			ur.save();
		}
		return RoleCopier.copy(roleDao.get(roleId), authorityDAO.listAll());
	}
	@Resource (name="roleDao")
	@Override
	public void setBaseDao(IBaseDao<Role, String> baseDao) {
		this.baseDao =baseDao;
	}

	/**
	* <p>Title: queryForPage</p> 
	* <p>Description: </p> 
	* @param countFilter
	* @param conditionQuery
	* @param orderBy
	* @param i
	* @param length
	* @return 
	* @see com.cht.emm.service.RoleService#queryForPage(int, com.cht.emm.common.dao.util.ConditionQuery, com.cht.emm.common.dao.util.OrderBy, int, java.lang.Integer) 
	*/
	@Override
	public Page<RoleVO> queryForPage(int count,
			ConditionQuery query, OrderBy orderBy, int pn,
			Integer pageSize) {
		List<Role> roles = this.baseDao.listAll(query,orderBy,  pn,   pageSize);
		Page<RoleVO> page = PageUtil.getPage(count, pn, RoleCopier.copy(roles), pageSize);
		return page;
	}

	/**
	* <p>Title: queryForPage</p> 
	* <p>Description: </p> 
	* @param count
	* @param whereClause
	* @param orderby
	* @param pn
	* @param length
	* @return 
	* @see com.cht.emm.service.RoleService#queryForPage(int, java.lang.String, java.lang.String, int, java.lang.Integer) 
	*/
	@Override
	public Page<RoleVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length) {
		List<Role> users = this.baseDao.listAll( whereClause,orderby , pn, length);
		Page<RoleVO> page = PageUtil.getPage(count, pn, RoleCopier.copy(users), length);
		return page;
	}

	@Override
	public List<RoleVO> getAVRoles() {
		return RoleCopier.copy(roleDao.listAll());
	}

	@Override
	public List<RoleVO> selectRole(Integer userType ) {
		List<Role> roles = baseDao.listAll(" where bitand(userType,"+(1<<(userType -1))+")>0 and isSystem !=1","",-1,-1);
		return RoleCopier.copy(roles);
	}
}
