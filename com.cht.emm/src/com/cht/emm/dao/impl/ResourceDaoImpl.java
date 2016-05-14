package com.cht.emm.dao.impl;

// default package

import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.ResourceDao;
import com.cht.emm.model.Resource;
import com.cht.emm.model.id.RoleResourcePermission;


/**
 * A data access object (DAO) providing persistence and search support for
 * Resource entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see .Resource
 * @author MyEclipse Persistence Tools
 */
@Repository("resourceDao")
public class ResourceDaoImpl extends BaseHibernateDaoUnDeletabke<Resource, String>
		implements ResourceDao {
	private static final Logger log = Logger.getLogger(ResourceDaoImpl.class);
	// property constants
	public static final String NAME = "name";
	public static final String URI = "uri";
	public static final String TYPE = "type";
	public static final String PERMISSION = "permission";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nari.mip.backstage.dao.impl.ResourceDAO#findResourceByRoleName(java.lang
	 * .String)
	 */

	
	@SuppressWarnings("unchecked")
	public RoleResourcePermission getRoleResource(String roleId,
			String resourceId) {
		// TODO Auto-generated method stub
		log.info("根据roleid和resourceId查找关联：roleId/resourceId"+roleId+"/"+resourceId);
		Query query = getSession()
				.createQuery(
						"from RoleResourcePermission rrp where rrp.role.id=? and rrp.resource.id=?");
		query.setString(0, roleId);
		query.setString(1, resourceId);
		List<RoleResourcePermission> rrp = query.list();
		if (rrp != null) {
			return rrp.get(0);
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	
	public List<Resource> selectResource(String type, String id) {
		// TODO Auto-generated method stub
		String exlcudesql = null;
		if("role".equals(type)){
			exlcudesql = "select rr.resource.id from RoleResourcePermission rr where rr.role.id=?";
		} else if("menu".equals(type)){
			exlcudesql = " select rr.id from MenuItem menu inner join menu.resource rr where  menu.id != ?";
		}else{
			exlcudesql = "''";
		}
		Query query =getSession().createQuery("from Resource r where r.id not in ("+exlcudesql+") and isSystem !=1");
		query.setString(0, id);
		return query.list();
	}

}