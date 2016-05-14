package com.cht.emm.common.service;


import java.util.List;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.vo.CounterVO;



/**
* @ClassName: IBaseService 
* @Description: 包含基本的service服务
* @author 张凯  zhangkai3@sgepri.sgcc.com.cn 
* @date 2014-10-15 下午4:58:59 
* 
* @param <M>
* @param <PK>
 */
public interface IBaseService<M extends java.io.Serializable, PK extends java.io.Serializable> {
    
    public M save(M model);

    public void saveOrUpdate(M model);
    
    public void update(M model);
    
    public void merge(M model);

    public void delete(PK id);

    public void deleteObject(M model);

    public M get(PK id);
    
    public int countAll();
    
    public int countAll(String whereClause  );
    
    public List<M> listAll();
    
    public Page<M> listAll(int pn);
    
    public Page<M> listAll(int pn, int pageSize);
    
    public List<M> listAll(ConditionQuery query, OrderBy orderby,int pn,int pageSize);
    
    public List<M> listByIds(PK[] ids);
    
    public Page<M> pre(PK pk, int pn, int pageSize);
    
    public Page<M> next(PK pk, int pn, int pageSize);
    
    public Page<M> pre(PK pk, int pn);
    
    public Page<M> next(PK pk, int pn);
    
    public int countAll(String query,String whereClause);
    
	void delete(PK[] ids);
	
	public List<CounterVO> getCounterVOs(String hsql,String query,String orderBy, String groupBy,Integer maxNum);

}
