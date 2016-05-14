package com.cht.emm.dao.impl;



import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.ResourceAuthDao;
import com.cht.emm.model.id.ResourceAuth;

@Repository("resourceAuthDao")
public class ResourceAuthDaoImpl extends BaseHibernateDao<ResourceAuth, String> implements
		ResourceAuthDao {
	 private static final Logger log = Logger.getLogger(ResourceAuthDaoImpl.class);

	@SuppressWarnings("unchecked")
	
	public String getPK(String pk1, String pk2) {
		// TODO Auto-generated method stub
		log.debug("获取资源授权联合id，副id为："+pk1+","+pk2);
		Query query = getSession().createQuery("select m."+getPkName()+" from "+this.getEntityClass().getSimpleName()+" m where m.resource.id =? and m.auth.id = ? ");
		query.setString(0, pk1);
 		query.setString(1, pk2);
		List<String> res = query.list();
		if(res!=null && res.size()>0){
			return res.get(0);
		}
		return null;
	}
	

	@SuppressWarnings("unchecked")
	public List<String> getAuthUrlsByResourceId(String resId){
		
		Query query = getSession().createQuery("select m.subUri"+" from "+this.getEntityClass().getSimpleName()+" m where m.resource.id =? ");
		query.setString(0, resId);
		
		List<String> resAuthList = query.list();
		
		return resAuthList;
	}
	 

}
