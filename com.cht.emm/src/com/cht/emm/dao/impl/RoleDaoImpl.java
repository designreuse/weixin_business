package com.cht.emm.dao.impl;
// default package


import java.util.ArrayList;
import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.RoleDao;
import com.cht.emm.model.Resource;
import com.cht.emm.model.Role;
import com.cht.emm.model.id.RoleResourcePermission;

/**
 	* A data access object (DAO) providing persistence and search support for Role entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .Role
  * @author MyEclipse Persistence Tools 
 */
@Repository("roleDao")
public class RoleDaoImpl   extends BaseHibernateDaoUnDeletabke<Role,String> implements RoleDao  {
	     private static final Logger log = Logger.getLogger(RoleDaoImpl.class);
		//property constants
	public static final String NAME = "name";
	/* (non-Javadoc)
	 * @see nari.mip.backstage.dao.impl.RoleDao#findResourceByRoleId(java.lang.String)
	 */
	
	public List<Resource> findResourceByRoleId(String roleId) {
		log.debug("查询角色id为 "+roleId+"的资源列表");
		Role role = get(roleId);
		List<Resource> resources = new ArrayList<Resource>();
		for (RoleResourcePermission resource : role.getResourcePermissions()) {
			resources.add(resource.getResource());
		}
		return resources;
	}
	@SuppressWarnings("unchecked")
	
	public List<Role> excludedList(String excluded, String excludedId) {
		String exlcudesql = null;
		if("user".equals(excluded)){
			exlcudesql = "select u.role.id from UserRole u where u.user.id=?";
		}else if("resource".equals(excluded)){
			exlcudesql = "select rr.role.id from RoleResourcePermission rr where rr.resource.id=?";
		}else{
			exlcudesql = "";
		}
		Query query =getSession().createQuery("from Role r where r.id not in ("+exlcudesql+")");
		query.setString(0, excludedId);
		return query.list();
	}
}