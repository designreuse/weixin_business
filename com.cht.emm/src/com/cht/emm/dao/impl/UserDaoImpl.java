package com.cht.emm.dao.impl;
// default package

import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.UserDao;
import com.cht.emm.model.User;

/**
 	* A data access object (DAO) providing persistence and search support for User entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .User
  * @author MyEclipse Persistence Tools 
 */
@Repository("userDao")
public class UserDaoImpl extends BaseHibernateDaoUnDeletabke <User,String> implements UserDao   {
	private static final Logger log = Logger.getLogger(UserDaoImpl.class);
		//property constants
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String STATUS = "status";
	/* (non-Javadoc)
	 * @see nari.mip.backstage.dao.impl.User#getUsersByName(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see nari.mip.backstage.dao.impl.UserDAO#getUsersByName(java.lang.String)
	 */
	public List<User> getUsersByName(String username) {
		log.debug("查询用户："+username);
		Criteria criteria = getSession().createCriteria(User.class);
		criteria.add(Restrictions.and(Restrictions.eq(USERNAME, username),Restrictions.ne("deleted", 1)));
		 
		return list(criteria);
	}
	@SuppressWarnings("unchecked")
	public List<User> excludedList(String type, String id) {
		String exlcudesql = null;
		if("role".equals(type)){
			exlcudesql = "select ur.user.id from UserRole ur where ur.role.id=?";
		}else if("group".equals(type)){
			exlcudesql = "select ug.user.id from UserGroup ug where ug.group.id=?";
		}else{
			exlcudesql = "";
		}
		Query query =getSession().createQuery("from User u where u.id not in ("+exlcudesql+")");
		query.setString(0, id);
		return query.list();
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean isAdmin(String userId) {
		String sql = "from UserRole r where r.user.user.id=:userId and r.role.name='ROLE_ADMIN'" ;
		Query query = getSession().createQuery(sql);
		query.setString("userId", userId);
		List list = query.list();
		if(list!= null  && list.size() >0){
			return true;
		}else {
			return false;
		}
		
	}
	 
}