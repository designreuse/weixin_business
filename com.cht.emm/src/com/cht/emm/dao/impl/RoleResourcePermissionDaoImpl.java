/**
 * 
 */
package com.cht.emm.dao.impl;


import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.RoleResourcePermissionDao;
import com.cht.emm.model.id.RoleResourcePermission;


/**
 * @author zhang-kai
 *
 */
@Repository("roleResourcePermissionDao")
public class RoleResourcePermissionDaoImpl extends BaseHibernateDao<RoleResourcePermission, String>
		implements RoleResourcePermissionDao {

	@SuppressWarnings("rawtypes")
	public String getPK(String pk1, String pk2) {
		// TODO Auto-generated method stub
		Query query = getSession().createQuery(" select m.id From "+this.getEntityClass().getSimpleName()+" m where m.role.id =:roleid and m.resource.id = :resourceid");
		query.setString("roleid", pk1);
 		query.setString("resourceid", pk2);
		List res = query.list();
		if(res!=null && res.size()>0){
			return (String) res.get(0);
		}
		return null;
	}


}
