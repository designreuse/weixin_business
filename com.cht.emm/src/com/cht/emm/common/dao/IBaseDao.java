package com.cht.emm.common.dao;


import java.util.List;

import org.hibernate.Session;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.model.AbstractModel;
import com.cht.emm.vo.CounterVO;


public interface IBaseDao<M extends AbstractModel, PK extends java.io.Serializable> {
    
	public Session getSession();
	/**
	 * 保存
	 * @param model
	 * @return
	 */
    public PK save(M model);

    /**
     * 保存或更新
     * @param model
     */
    public void saveOrUpdate(M model);
    
    /**
     * 更新
     * @param model
     */
    public void update(M model);
    
    /**
     * 替换更新
     * @param model
     */
    public void merge(M model);

    /**
     * 根据ID 删除
     * @param id
     */
    public void delete(PK id);
    
    /**
     * 根据ID 删除
     * @param id
     */
    public void delete(PK[] ids);

    /**
     * 删除对象
     * @param model
     */
    public void deleteObject(M model);

    /**
     * 根据对象id获取
     * @param id
     * @return
     */
    public M get(PK id);
    
    /**
     * 对象统计
     * @return
     */
    public int countAll();
    
    
    /**
     * 获取全部对象
     * @return
     */
    public List<M> listAll();

    /**
     * 分页获取对象
     * @param pn
     * @param pageSize
     * @return
     */
    public List<M> listAll(int pn, int pageSize);
    
    /**
     * 根据多个id获取对象
     * @param ids
     * @return
     */
    public List<M> listByIds(PK[] ids);
    
    /**
     * 分页上一页
     * @param pk
     * @param pn
     * @param pageSize
     * @return
     */
    public List<M> pre(PK pk, int pn, int pageSize);
    
    /**
     * 分页下一页
     * @param pk
     * @param pn
     * @param pageSize
     * @return
     */
    public List<M> next(PK pk, int pn, int pageSize);
    
    /**
     *  判断是否存在
     * @param id
     * @return
     */
    boolean exists(PK id);
    
    /**
     * 回写
     */
    public void flush();
    
    /**
     * 清空缓存
     */
    public void clear();

	void deleteObjectList(List<M> model);

	/** 
	* @Title: next 
	* @Description:按条件查询分页，下一页 
	* @param whereClause
	* @param orderClause
	* @param pn
	* @param pageSize
	* @return LIst<M>    返回类型 
	* @throws 
	*/
	public List<M> next(String whereClause, String orderClause, int pn,
			int pageSize);

	/** 
	* @Title: pre 
	* @Description: 按条件查询分页 
	* @param whereClause
	* @param orderClause
	* @param pn
	* @param pageSize
	* @return LIst<M>    返回类型 
	* @throws 
	*/
	public List<M> pre(String whereClause, String orderClause, int pn,
			int pageSize);

	/** 
	* @Title: countAll 
	* @Description: 增加查询条件
	* @param whereClause
	* @return int    返回类型 
	* @throws 
	*/
	public int countAll(String whereClause);

	
	
	/** 
	* @Title: listAll 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param query
	* @param orderby
	* @param pn
	* @param pageSize
	* @return  
	* @return List<M>    返回类型 
	* @throws 
	*/
	public List<M> listAll(ConditionQuery query, OrderBy orderby, int pn,
			int pageSize);

	/** 
	* @Title: listAll 
	* @Description: 直接根据hql语句查询，针对可能需要使用到连接的情况
	* @param string
	* @param pn
	* @param length
	* @return  
	* @return List<User>    返回类型 
	* @throws 
	*/
	public List<M> listAll(String string, int pn, Integer length);
	
	
	public List<M> listAll(String whereClause,String orderClause,int pn,int length);

	/**
	* @Title: countAll 
	* @Description: 用于连接查询时计数统计 
	* @param hql
	* @param whereClause
	* @return  
	* @return Integer    返回类型 
	* @throws
	 */
	public Integer countAll(String hql,String whereClause);
 
	public List<CounterVO> getCounters(String hql,String whereClause,String orderBy,String groupBy,Integer maxNum);
	
	
	 
	List<M> listHql(String string, int pn, Integer length);

	
 
}
