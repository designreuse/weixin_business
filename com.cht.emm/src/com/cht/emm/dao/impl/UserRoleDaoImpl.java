package com.cht.emm.dao.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.UserRoleDao;
import com.cht.emm.model.id.UserRole;

@Repository("userRoleDao")
public class UserRoleDaoImpl extends BaseHibernateDao<UserRole, String> implements UserRoleDao {
	 private static final Logger log = Logger.getLogger(UserRole.class);

	 @SuppressWarnings("unchecked")
	 public String getPK(String pk1, String pk2) {
		 log.debug("get user role : userId"+pk1+" roleId:"+pk2);
	 		// TODO Auto-generated method stub
	 		Query query = getSession().createQuery("select m."+getPkName()+" from "+this.getEntityClass().getSimpleName()+" m where m.user.id =? and m.role.id = ? ");
	 		query.setString(0, pk1);
	 		query.setString(1, pk2);
	 		List<String> res = query.list();
	 		if(res!=null && res.size()>0){
	 			return res.get(0);
	 		}
	 		return null;
	 	}

	 
	 
	 
}
