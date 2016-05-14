package com.cht.emm.dao.impl;

import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.AuthorityDao;
import com.cht.emm.model.Authority;


/**
 	* A data access object (DAO) providing persistence and search support for Authority entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .Authority
  * @author MyEclipse Persistence Tools 
 */
@Repository("authorityDao")
public class AuthorityDaoImpl extends BaseHibernateDaoUnDeletabke<Authority,String> implements AuthorityDao  {
	private static final Logger log = Logger.getLogger(AuthorityDaoImpl.class);
		//property constants
	public static final String NAME = "name";
	public static final String SHOW_INDEX = "showIndex";
	public static final String LOC_INDEX = "locIndex";
	public static final String DESCP = "descp";
	@SuppressWarnings("unchecked")
	@Override
	public List<Authority> listByIds(String[] ids) {
		log.info("根据ids查找所有的auth");
		Query query =getSession().createQuery("select au from Authority au where au.id in (:ids) ");
		 
		query.setParameterList("ids", ids);
		return query.list();
	}
	/**
	* <p>Title: updateShowIndex</p> 
	* <p>Description: </p> 
	* @param showIndex
	* @return 
	* @see com.cht.emm.dao.AuthorityDao#updateShowIndex(java.lang.Integer) 
	*/
	@Override
	public boolean updateShowIndex(Integer current,Integer pre) {
		Session session = null;
		try {
			session = getSession();
			if(current > pre){
				Query query2 = session.createQuery("update Authority auth set auth.showIndex = auth.showIndex -1 where auth.showIndex <= ? and auth.showIndex >?");
				query2.setInteger(0, current);
				query2.setInteger(1, pre);
				query2.executeUpdate();
			}else {
				Query query = session.createQuery("update Authority auth set auth.showIndex = auth.showIndex + 1 where auth.showIndex < ? and auth.showIndex >=?");
				query.setInteger(0, pre);
				query.setInteger(1, current);
				query.executeUpdate();
			}
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
		}finally{
		}
		return false;
	}
}