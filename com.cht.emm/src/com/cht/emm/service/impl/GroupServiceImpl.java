package com.cht.emm.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
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
import com.cht.emm.dao.GroupDao;
import com.cht.emm.dao.UserDao;
import com.cht.emm.model.Group;
import com.cht.emm.model.User;
import com.cht.emm.model.id.UserGroup;
import com.cht.emm.service.GroupService;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.objectcopier.GroupCopier;
import com.cht.emm.util.objectcopier.UserCopier;
import com.cht.emm.vo.GroupVO;
import com.cht.emm.vo.UserVO;

@Service("groupService")
public class GroupServiceImpl extends BaseService<Group,String> implements GroupService {
	 

	@Resource
	UserDao userDAO;
	
	
	
	
	
	/* (non-Javadoc)
	 * @see nari.mip.backstage.common.service.impl.BaseService#save(nari.mip.backstage.common.model.AbstractModel)
	 */
	@Override
	public Group save(Group model) {
		baseDao.save(model);
		return model;
	}

	@Override
	public List<GroupVO> getAllGroups(){
		return GroupCopier.copy(baseDao.listAll());
	}
	
	@Override
	public GroupVO getById(String id){
		
		return GroupCopier.copy(baseDao.get(id));
	}
	
	@Override
	public List<GroupVO> selectGroups(String type, String id) {
		
		return GroupCopier.copy(((GroupDao)baseDao).excludedList(type, id));
	}

	@Override
	public GroupVO saveGroupUser(String groupId, String userIds) {
		
		Group group = baseDao.get(groupId);
		List<User> users = userDAO.listByIds(userIds.split(","));
		for (User user : users) {
			UserGroup userGroup = new UserGroup();
			userGroup.setGroup(group);
			userGroup.setUser(user);
			userGroup.setId(UUIDGen.getUUID());
			userGroup.save();
		}
		return GroupCopier.copy(baseDao.get(groupId));
	}

	@Override
	public GroupVO saveSubGroup(String groupId, String subIds) {

		Group group =baseDao.get(groupId);
		List<Group> subs = ((GroupDao)baseDao).listByIds(subIds.split(","));
		for (Group sub : subs) {
			sub.setParentGroup(group);
			sub.update();
			if(group.getSubGroups()==null){
				group.setSubGroups(new HashSet<Group>());
			}
			group.getSubGroups().add(sub);
		}
		return GroupCopier.copy(group);
	}

	@Override
	@Resource (name="groupDao")
	public void setBaseDao(IBaseDao<Group, String> baseDao) {

		this.baseDao  = baseDao;
		
	}
	
	@Override
	public void delete(String[] ids) {
		
		List<Group> groups = listByIds(ids);
		for (Group group : groups) {
			
			Set<Group> subs = group.getSubGroups();
			if(subs!=null){
				for (Group group2 : subs) {
					group2.setParentGroup(group.getParentGroup());
				}
			}
			Set<UserGroup> users = group.getUsers();
			if(users != null){
				for (UserGroup user : users) {
					user.delete();
				}
			}
			
		}
		
		super.delete(ids);
	}

	/**
	* <p>Title: queryForPage</p> 
	* <p>Description: </p> 
	* @param count
	* @param queryAll
	* @param whereClause
	* @param orderby
	* @param pn
	* @param length
	* @return 
	* @see com.cht.emm.service.GroupService#queryForPage(int, java.lang.String, java.lang.String, java.lang.String, int, java.lang.Integer) 
	*/
	@Override
	public Page<GroupVO> queryForPage(int count,String whereClause, String orderby, int pn, Integer length) {
		List<Group> users = this.baseDao.listAll( whereClause,orderby , pn, length);
		Page<GroupVO> page = PageUtil.getPage(count, pn, GroupCopier.copy(users), length);
		return page;
	}

	/**
	* <p>Title: queryForPage</p> 
	* <p>Description: </p> 
	* @param count
	* @param query
	* @param orderBy
	* @param pn
	* @param pageSize
	* @return 
	* @see com.cht.emm.service.GroupService#queryForPage(java.lang.Integer, com.cht.emm.common.dao.util.ConditionQuery, com.cht.emm.common.dao.util.OrderBy, int, int) 
	*/
	@Override
	public Page<GroupVO> queryForPage(Integer count, ConditionQuery query,
			OrderBy orderBy, int pn, int pageSize) {

		List<Group> users = this.baseDao.listAll(query,orderBy,  pn,   pageSize);
		Page<GroupVO> page = PageUtil.getPage(count, pn, GroupCopier.copy(users), pageSize);
		return page;
	}

	/**
	 * 
	 */
	@Override
	public GroupVO getTopGroup() {
		Group group = ((GroupDao) baseDao).getTopGroup();
		GroupVO vo = null;
		if(group !=null){
			vo = copySub(group);
		}
		return vo;
	}
	
	private GroupVO copySub(Group group){
		if(group!=null){
			GroupVO vo = GroupCopier.singleCopy(group);
			if(group.getSubGroups()!=null && group.getSubGroups().size() > 0){
				vo.setSubGroups(new ArrayList<GroupVO>());
				for (Group gp : group.getSubGroups()) {
					if(gp.getDeleted()==0)
					  vo.getSubGroups().add(copySub(gp));
				}
			}
			
			return vo;
		}
		return null;
		
		
	}
 
	@Override
	public List<String> getSubGroupListIDs(String topId) {
		List<String> groupids = new ArrayList<String>();
		groupids.add(topId);
		Group group = baseDao.get(topId);
		if(group.getSubGroups()!=null){
			for (Group sub : group.getSubGroups()) {
				getId(sub, groupids);
			}
		}
		return groupids;
	}
	
	private void getId(Group group,List<String> ids){
		if(group.getDeleted()==0){
			ids.add(group.getId());
			if(group.getSubGroups()!=null){
				for (Group sub : group.getSubGroups()) {
					getId(sub, ids);
				}
			}
		}
		
	}
	@Override
	public boolean checkSubName(String id,String parentId,String name){
		boolean result =false;
		ConditionQuery query = new ConditionQuery();
		if(id == null){
			query.add(Restrictions.and(Restrictions.eq("parentGroup.id", parentId),Restrictions.eq("groupName", name)));
			
		}else{
			query.add(Restrictions.and(Restrictions.eq("parentGroup.id", parentId),Restrictions.eq("groupName", name),Restrictions.ne("id", id)));
			
		}
			List<Group> groups =baseDao.listAll(query,null, -1, -1);
		if(groups ==null || groups.size() == 0){
			result =true;
		}
		return result;
	}
	
	
	
	@Override
	public GroupVO getDepartStruct(String groupId) {
		Group group = baseDao.get(groupId);
		GroupVO vo = copySubWithUser(group);
		return vo;
	}
	
	private GroupVO copySubWithUser(Group group){
		if(group!=null){
			GroupVO vo = GroupCopier.singleCopy(group);
			if(group.getSubGroups()!=null && group.getSubGroups().size() > 0){
				vo.setSubGroups(new ArrayList<GroupVO>());
				for (Group gp : group.getSubGroups()) {
					if(gp.getDeleted()==0)
					  vo.getSubGroups().add(copySubWithUser(gp));
				}
			}
			if(group.getUsers()!=null){
				vo.setUsers(new ArrayList<UserVO>());
				for (UserGroup user : group.getUsers()) {
					vo.getUsers().add(UserCopier.singleCopy(user.getUser()));
				}
			}
			
			return vo;
		}
		return null;
	}

	@Override
	public Group getThirdpartTopGroup() {
		return ((GroupDao)baseDao).getThirdPartTopGroup();
	}
	
}
