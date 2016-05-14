package com.cht.emm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.pagination.PageUtil;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.AuthorityDao;
import com.cht.emm.model.Authority;
import com.cht.emm.service.AuthorityService;
import com.cht.emm.util.objectcopier.AuthorityCopier;
import com.cht.emm.vo.AuthVO;

@Service("authorityService")
public class AuthorityServiceImpl extends BaseService<Authority, String> implements AuthorityService {

	@Resource(name="authorityDao")
	@Override
	public void setBaseDao(IBaseDao<Authority, String> baseDao) {
		this.baseDao = baseDao;
	}

	/**
	* <p>Title: getById</p> 
	* <p>Description: 根据id获取Authority</p> 
	* @param id
	* @return 
	* @see com.cht.emm.service.AuthorityService#getById(java.lang.String) 
	*/
	@Override
	public AuthVO getById(String id) {
		return AuthorityCopier.copy(get(id));
	}

	/**
	* <p>Title: queryForPage</p> 
	* <p>Description:分页查询 </p> 
	* @param count
	* @param query
	* @param orderBy
	* @param pn
	* @param pageSize
	* @return 
	* @see com.cht.emm.service.AuthorityService#queryForPage(java.lang.Integer, com.cht.emm.common.dao.util.ConditionQuery, com.cht.emm.common.dao.util.OrderBy, int, int) 
	*/
	@Override
	public Page<AuthVO> queryForPage(Integer count, ConditionQuery query,
			OrderBy orderBy, int pn, int pageSize) {
		List<Authority> auths = this.listAll(query, orderBy, pn, pageSize);
		 
		Page<AuthVO> page = PageUtil.getPage(count, pn, AuthorityCopier.copy(auths), pageSize);
		return page;
	}

	/**
	* <p>Title: queryForPage</p> 
	* <p>Description: 分页查询</p> 
	* @param count
	* @param queryAll
	* @param whereClause
	* @param orderby
	* @param pn
	* @param length
	* @return 
	* @see com.cht.emm.service.AuthorityService#queryForPage(int, java.lang.String, java.lang.String, java.lang.String, int, java.lang.Integer) 
	*/
	@Override
	public Page<AuthVO> queryForPage(int count,String whereClause, String orderby, int pn, Integer length) {
		List<Authority> authorities = this.baseDao.listAll( whereClause,orderby , pn, length);
		Page<AuthVO> page = PageUtil.getPage(count, pn, AuthorityCopier.copy(authorities), length);
		return page;
	}

	/**
	* <p>Title: getMaxIndex</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.cht.emm.service.AuthorityService#getMaxIndex() 
	*/
	@Override
	public Integer getMaxIndex() {
		return this.baseDao.countAll();
	}

	/**
	* <p>Title: updateShowIndex</p> 
	* <p>Description: </p> 
	* @param showIndex
	* @return 
	* @see com.cht.emm.service.AuthorityService#updateShowIndex(java.lang.Integer) 
	*/
	@Override
	public boolean updateShowIndex(Integer current,Integer pre) {
		return ((AuthorityDao) baseDao).updateShowIndex(current,pre);
	}

}
