package com.cht.emm.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import nariis.pi3000.framework.utility.StringUtil;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.GroupDao;
import com.cht.emm.model.Group;
import com.cht.emm.util.PropertiesReader;

/**
 	* A data access object (DAO) providing persistence and search support for group entities.
 			* Transaction control of the save(), update() and delete() operations 
		can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
		Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
	 * @see .group
  * @author MyEclipse Persistence Tools 
 */
@Repository("groupDao")
public class GroupDaoImpl extends BaseHibernateDaoUnDeletabke<Group,String> implements GroupDao  {


	@Resource(name="propertiesReader")
	PropertiesReader propertiesReader;
	private static final Logger log = Logger.getLogger(GroupDaoImpl.class);
	//property constants
	public static final String GROUP_NAME = "groupName";
	public static final String GROUP_DESC = "groupDesc";
	public static final String STATUS = "status";
	
	@SuppressWarnings("unchecked")
	
	public List<Group> excludedList(String type, String id) {
		
		log.info("组列表排除条件: type/id: "+type+"/"+id);
		String exlcudesql = null;
		boolean moreArgues = false;
		if("user".equals(type)){
			exlcudesql = "select u.group.id from UserGroup u where u.user.id=?";
		}else if("group".equals(type)){
			
			exlcudesql = "select g.id from Group g where (g.id = ? or g.parentGroup.id= ? ) or g.parentGroup.id != null ";
			moreArgues =true;
		}else{
			exlcudesql = "";
		}
		Query query =getSession().createQuery("from Group g where g.id not in ("+exlcudesql+")");
		query.setString(0, id);
		if(moreArgues){
			query.setString(1, id);
		}
		return query.list();
	}

	@Override
	public Group getTopGroup() {
		String sql = "from Group where parentGroup.id = null and deleted=0 and groupType = " + Group.GROUP_TYPE.ORG.getType();
		Query query =getSession().createQuery(sql);
		Group group = (Group) query.uniqueResult();
		return group;
	}

	
	@Override
	public Group getThirdPartTopGroup() {
		String topThirdPartGroupId = propertiesReader.getString("thirdPartGroupId");
		if(StringUtil.isBlank(topThirdPartGroupId)){
			Session session = sessionFactory.openSession();
			Criteria criteria = session.createCriteria(Group.class);
			criteria.add(Restrictions.eq("thirdPartType", Group.GROUP_THIRDPART_TYPE.TOP.getType()));
			List<Group> groups = this.list(criteria);
			if(groups!=null  && groups.size() >0 ){
				Group group = groups.get(0);
				propertiesReader.setValue("thirdPartGroupId", group.getId());
				return groups.get(0);
			}
		}else {
			return get(topThirdPartGroupId);
		}
		return null;
	}
	
	 
}