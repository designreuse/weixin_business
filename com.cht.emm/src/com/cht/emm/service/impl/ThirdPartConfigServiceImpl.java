/**
 * @Title: ThirdPartConfigServiceImpl.java
 * @Package: nari.mip.backstage.service.impl
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-3-23 下午3:19:48
 * @Version: 1.0
 */
package com.cht.emm.service.impl;


import java.util.List;

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
import com.cht.emm.dao.ThirdPartConfigDao;
import com.cht.emm.dao.UserDao;
import com.cht.emm.model.Group;
import com.cht.emm.model.ThirdPartConfig;
import com.cht.emm.model.id.UserGroup;
import com.cht.emm.service.ThirdPartConfigService;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.objectcopier.ThirdPartConfigCopier;
import com.cht.emm.vo.ThirdPartConfigVO;


/**
 * @Class: ThirdPartConfigServiceImpl
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
@Service("thirdPartConfigService")
public class ThirdPartConfigServiceImpl extends BaseService<ThirdPartConfig, String> implements
		ThirdPartConfigService {
	
	
	@Resource(name = "groupDao")
	private GroupDao groupDao;
	
	@Resource(name = "userDao")
	private UserDao userDao;
	
	/* (non-Javadoc)
	 * @see nari.mip.backstage.common.service.impl.BaseService#setBaseDao(nari.mip.backstage.common.dao.IBaseDao)
	 */
	@Override
	@Resource(name="thirdPartConfigDao")
	public void setBaseDao(IBaseDao<ThirdPartConfig, String> baseDao) {
		this.baseDao = baseDao;
	}

	public Page<ThirdPartConfigVO> queryForPage(int count,String whereClause, String orderby, int pn, Integer length){
		List<ThirdPartConfig> lists =  baseDao.listAll(whereClause, orderby, pn, length);
		Page<ThirdPartConfigVO> page = PageUtil.getPage(count, pn, ThirdPartConfigCopier.copy(lists), length);
		return page;
	}

	/* (non-Javadoc)
	 * @see nari.mip.backstage.service.ThirdPartConfigService#checkThirdPartName(java.lang.String)
	 */
	@Override
	public Boolean checkThirdPartName(String name) {
		ConditionQuery query = new ConditionQuery();
		query.add(Restrictions.eq("name", name));
		List<ThirdPartConfig> list  = baseDao.listAll(query, null, -1, -1);
		if(list!= null && list.size() >0){
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see nari.mip.backstage.common.service.impl.BaseService#save(nari.mip.backstage.common.model.AbstractModel)
	 */
	@Override
	public ThirdPartConfig save(ThirdPartConfig model) {
		Group shadowGroup =model.getGroup();
		shadowGroup.setId(UUIDGen.getUUID());
		shadowGroup.setParentGroup(groupDao.getThirdPartTopGroup());
		shadowGroup.save();
		return super.save(model);
	}

	public Page<ThirdPartConfigVO> queryForPage(Integer count, ConditionQuery query, OrderBy orderBy, int pn, int pageSize){
		List<ThirdPartConfig> configs = this.baseDao.listAll(query,orderBy,  pn,   pageSize);
		Page<ThirdPartConfigVO> page = PageUtil.getPage(count, pn, ThirdPartConfigCopier.copy(configs), pageSize);
		return page;
	}

	@Override
	public List<ThirdPartConfigVO> getAllList() {
		List<ThirdPartConfig> configs = ((ThirdPartConfigDao) baseDao).listAll();
		List<ThirdPartConfigVO> results = ThirdPartConfigCopier.copy(configs);
		return results;
	}

	@Override
	public ThirdPartConfigVO getById(String id) {
		return ThirdPartConfigCopier.copy(baseDao.get(id));
	}

	@Override
	public void deleteThirdPart(String[] ids) {
		List<ThirdPartConfig> configs = baseDao.listByIds(ids);
		if(configs !=null  && configs.size()>0){
			for (ThirdPartConfig config : configs) {
				Group group  =  config.getGroup();
				releaseGroup(group);
				config.delete();
			}
		}
		
		
	}
	private void releaseGroup(Group group){
		if(group.getSubGroups()!=null){
			for (Group sub : group.getSubGroups()) {
				releaseGroup(sub);
			}
			
		}
		if(group.getUsers()!=null){
			for (UserGroup user : group.getUsers()) {
				
				userDao.deleteObject(user.getUser());
			}
		}
		groupDao.deleteObject(group);
		
	}
}
