package com.cht.emm.dao.impl;
// default package

import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.UserGroupDao;
import com.cht.emm.model.id.UserGroup;

/**
 * 
* @ClassName: UserGroupDaoImpl 
* @Description: 用户组操作 
* @author 张凯  zhangkai3@sgepri.sgcc.com.cn 
* @date 2014-10-11 上午9:51:27 
*
 */
@Repository("userGroupDao")
public class UserGroupDaoImpl extends BaseHibernateDao<UserGroup,String> implements UserGroupDao  {
	     private static final Logger log = Logger.getLogger(UserGroupDaoImpl.class);

	     @SuppressWarnings("unchecked")
	 	public String getPK(String pk1, String pk2) {
	 		// TODO Auto-generated method stub
	    	log.debug("get user group: userId:"+ pk1+" groupId:"+pk2);
	 		Query query = getSession().createQuery("select m."+getPkName()+" from "+this.getEntityClass().getSimpleName()+" m where m.user.id =? and m.group.id = ? ");
	 		query.setString(0, pk1);
	 		query.setString(1, pk2);
	 		List<String> res = query.list();
	 		if(res!=null && res.size()>0){
	 			return res.get(0);
	 		}
	 		return null;
	 	}

	 
	 
}