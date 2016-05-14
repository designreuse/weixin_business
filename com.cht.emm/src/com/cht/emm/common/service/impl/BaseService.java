package com.cht.emm.common.service.impl;


import java.util.List;

import com.cht.emm.common.Constants;
import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.model.AbstractModel;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.pagination.PageUtil;
import com.cht.emm.common.service.IBaseService;
import com.cht.emm.vo.CounterVO;


public abstract class BaseService<M extends AbstractModel, PK extends java.io.Serializable> implements IBaseService<M, PK> {
    

	/**
	* <p>Title: getCounterVOs</p> 
	* <p>Description: </p> 
	* @param hsql
	* @param query
	* @param orderBy
	* @param groupBy
	* @param maxNum
	* @return 
	* @see com.cht.emm.common.service.IBaseService#getCounterVOs(java.lang.String, com.cht.emm.common.dao.util.ConditionQuery, com.cht.emm.common.dao.util.OrderBy, org.hibernate.criterion.ProjectionList, java.lang.Integer) 
	*/
	@Override
	public List<CounterVO> getCounterVOs(String hsql, String query,
			String orderBy, String groupBy, Integer maxNum) {
		return baseDao.getCounters(hsql, query, orderBy, groupBy, maxNum);
	}


	/**
	* <p>Title: listAll</p> 
	* <p>Description: </p> 
	* @param query
	* @param pn
	* @param pageSize
	* @return 
	* @see com.cht.emm.common.service.IBaseService#listAll(com.cht.emm.common.dao.util.ConditionQuery, int, int) 
	*/
	@Override
	public List<M> listAll(ConditionQuery query,OrderBy orderby, int pn, int pageSize) {
		
		return this.baseDao.listAll(query, orderby, pn, pageSize);
	}


	/**
	* <p>Title: countAll</p> 
	* <p>Description: </p> 
	* @param query
	* @param whereClause
	* @return 
	* @see com.cht.emm.common.service.IBaseService#countAll(java.lang.String, java.lang.String) 
	*/
	@Override
	public int countAll(String query, String whereClause) {
		return baseDao.countAll(query, whereClause);
	}


	protected IBaseDao<M, PK> baseDao;
    
    public abstract void setBaseDao(IBaseDao<M, PK> baseDao);
    

    @Override
    public M save(M model) {
    	
        baseDao.save(model);
        
        return model;
    }
    
    @Override
    public void merge(M model) {
        baseDao.merge(model);
    }

    @Override
    public void saveOrUpdate(M model) {
        baseDao.saveOrUpdate(model);
    }

    @Override
    public void update(M model) {
        baseDao.update(model);
    }
    
    @Override
    public void delete(PK id) {
        baseDao.delete(id);
    }
    @Override
    public void delete(PK[] ids){
    	 baseDao.delete(ids);
    }
    
    @Override
    public void deleteObject(M model) {
        baseDao.deleteObject(model);
    }

    @Override
    public M get(PK id) {
        return baseDao.get(id);
    }

   
    
    @Override
    public int countAll() {
        return baseDao.countAll();
    }

    @Override
    public List<M> listAll() {
        return baseDao.listAll();
    }
    @Override
    public Page<M> listAll(int pn) {

        return this.listAll(pn, Constants.DEFAULT_PAGE_SIZE);
    }
    
    public Page<M> listAllWithOptimize(int pn) {
        return this.listAllWithOptimize(pn, Constants.DEFAULT_PAGE_SIZE);
    }
    
    @Override
    public List<M> listByIds(PK[] ids){
    	List<M> terms =baseDao.listByIds(ids);
    	return terms;
    }
    
    @Override
    public Page<M> listAll(int pn, int pageSize) {
        Integer count = countAll();
        List<M> items = baseDao.listAll(pn, pageSize);
        return PageUtil.getPage(count, pn, items, pageSize);
    }
    public Page<M> listAllWithOptimize(int pn, int pageSize) {
        Integer count = countAll();
        List<M> items = baseDao.listAll(pn, pageSize);
        return PageUtil.getPage(count, pn, items, pageSize);
    }
    
    @Override
    public Page<M> pre(PK pk, int pn, int pageSize) {
        Integer count = countAll();
        List<M> items = baseDao.pre(pk, pn, pageSize);
        return PageUtil.getPage(count, pn, items, pageSize);
    }
    @Override
    public Page<M> next(PK pk, int pn, int pageSize) {
        Integer count = countAll();
        List<M> items = baseDao.next(pk, pn, pageSize);
        return PageUtil.getPage(count, pn, items, pageSize);
    }
    @Override
    public Page<M> pre(PK pk, int pn) {
        return pre(pk, pn, Constants.DEFAULT_PAGE_SIZE);
    }
    /**
     * 
    * <p>Title: next</p> 
    * <p>Description: 下一页 </p> 
    * @param pk
    * @param pn
    * @return  Page<M>
    * @see com.cht.emm.common.service.IBaseService#next(java.io.Serializable, int)
     */
    
    @Override
    public Page<M> next(PK pk, int pn) {
        return next(pk, pn, Constants.DEFAULT_PAGE_SIZE);
    }

 

	/**
	* <p>Title: countAll</p> 
	* <p>Description:增加查询条件 </p> 
	* @param whereClause
	* @return 
	* @see com.cht.emm.common.service.IBaseService#countAll(java.lang.String) 
	*/
	@Override
	public int countAll(String	whereClause) {
		return  baseDao.countAll( whereClause);
	}


}
