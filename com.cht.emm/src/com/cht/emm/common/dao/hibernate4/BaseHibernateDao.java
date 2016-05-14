/**
 * 
 */

package com.cht.emm.common.dao.hibernate4;


import org.hibernate.*;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.model.AbstractModel;
import com.cht.emm.common.pagination.PageUtil;
import com.cht.emm.vo.CounterVO;


import javax.persistence.Id;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * @ClassName: BaseHibernateDao
 * @Description: 基础的针对Entity的hibernate DAO操作
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-9-3 下午3:03:44
 * 
 * @param <M>
 * @param <PK>
 */
public abstract class BaseHibernateDao<M extends AbstractModel, PK extends java.io.Serializable>
		implements IBaseDao<M, PK> {


	/**
	* <p>Title: getCounters</p> 
	* <p>Description: </p> 
	* @param hql
	* @param query
	* @param orderBy
	* @param groupBy
	* @param maxNum
	* @return 
	* @see com.cht.emm.common.dao.IBaseDao#getCounters(java.lang.String, com.cht.emm.common.dao.util.ConditionQuery, com.cht.emm.common.dao.util.OrderBy, java.util.List, java.lang.Integer) 
	*/
	@Override
	public List<CounterVO> getCounters(String hql, String  whereClause,
			String orderBy, String groupBy, Integer maxNum) {
		// 统计的统一接口
		Session session = getSession();
		
		Query query = session.createQuery(hql+ (whereClause==null?"":whereClause) +(groupBy==null ? "":groupBy) +(orderBy==null?"":orderBy));
		query.setMaxResults(maxNum);
		query.setCacheable(true);
		
		List<CounterVO> counters = new ArrayList<CounterVO>();
		List<Object[]> counterVOs = query.list();
		for (Object[] obj : counterVOs) {
			counters.add(new CounterVO((String)obj[0],(String) obj[1],((Long)obj[2]).intValue()));
		}
		return counters;
				 
	}


	protected static final Logger LOGGER = LoggerFactory
			.getLogger(BaseHibernateDao.class);

	private final Class<M> entityClass;
	/**
	 * 获取所有Entity列表的语句
	 */
	protected final String HQL_LIST_ALL;

	/**
	 * 计数语句
	 */
	protected final String HQL_COUNT_ALL;
	/**
	 * 上一页
	 */
	protected final String HQL_OPTIMIZE_PRE_LIST_ALL;
	/**
	 * 下一页
	 */
	protected final String HQL_OPTIMIZE_NEXT_LIST_ALL;

	/**
	 * 根据多个id查询
	 */
	protected final String HQL_LIST_BY_IDS;

	/**
	 * 主键明
	 */
	private String pkName = null;

 

	/**
	 * 
	 * @Title: getPkName
	 * @Description: 取得主键名称
	 * @param @return 设定主键名称
	 * @return String 返回类型
	 * @throws
	 */
	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public Class<M> getEntityClass() {
		return entityClass;
	}

	@SuppressWarnings("unchecked")
	public BaseHibernateDao() {
		this.entityClass = (Class<M>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		Field[] fields = this.entityClass.getDeclaredFields();
		for (Field f : fields) {
			System.out.println(f.toGenericString());
			if (f.isAnnotationPresent(Id.class)) {
				this.pkName = f.getName();
			}
		}
		/**
		 * 继承于AbstractModelUnDeletable的子类都是不可删除的对象，对于不可删除的对象，删除时只是更改deleted=1
		 * 此外，查询时，对于实体类对象，需要指明查询条件中有 deleted =0 限制
		 */
		 
		// Assert.notNull(pkName);
		//
		HQL_LIST_ALL = "from " + this.entityClass.getSimpleName();
		HQL_LIST_BY_IDS = "from " + this.entityClass.getSimpleName()
				+ " where " + pkName + " in (:ids)";
		HQL_OPTIMIZE_PRE_LIST_ALL = "from " + this.entityClass.getSimpleName();
		HQL_OPTIMIZE_NEXT_LIST_ALL = "from " + this.entityClass.getSimpleName();
		HQL_COUNT_ALL = " select count(*) from "
				+ this.entityClass.getSimpleName();

	}


	/**
	 * <p>
	 * Title: next
	 * </p>
	 * <p>
	 * Description:按条件查询 下一页
	 * </p>
	 * 
	 * @param whereClause
	 * @param orderClause
	 * @param pn
	 * @param pageSize
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#next(java.lang.String,
	 *      java.lang.String, int, int)
	 */
	@Override
	public List<M> next(String whereClause, String orderClause, int pn,
			int pageSize) {
		String hql = HQL_OPTIMIZE_NEXT_LIST_ALL + " " + whereClause + " "
				+ orderClause;

		return list(hql, pn, pageSize);
	}

	/**
	 * <p>
	 * Title: pre
	 * </p>
	 * <p>
	 * Description:按条件查询 上一页
	 * </p>
	 * 
	 * @param whereClause
	 * @param orderClause
	 * @param pn
	 * @param pageSize
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#pre(java.lang.String,
	 *      java.lang.String, int, int)
	 */
	@Override
	public List<M> pre(String whereClause, String orderClause, int pn,
			int pageSize) {
		String hql = HQL_OPTIMIZE_NEXT_LIST_ALL + " " + whereClause + " "
				+ orderClause;

		return list(hql, pn, pageSize);
	}

	@Autowired
	@Qualifier("sessionFactory")
	protected SessionFactory sessionFactory;

	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	/**
	 * <p>
	 * Title: listAll
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param string
	 * @param pn
	 * @param length
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#listAll(java.lang.String,
	 *      int, java.lang.Integer)
	 */
	@Override
	public List<M> listAll(String string, int pn, Integer length) {
		return list(string, pn, length);
	}

	/**
	 * <p>
	 * Title: listAll
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param query
	 * @param orderby
	 * @param pn
	 * @param pageSize
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#listAll(com.cht.emm.common.dao.util.ConditionQuery,
	 *      com.cht.emm.common.dao.util.OrderBy, int, int)
	 */
	@Override
	public List<M> listAll(ConditionQuery query, OrderBy orderby, int pn,
			int pageSize) {
		return this.list(query, orderby, pn, pageSize);
	}
	/**
	* <p>Title: listAll</p> 
	* <p>Description: </p> 
	* @param whereClause
	* @param orderClause
	* @param pn
	* @param length
	* @return 
	* @see com.cht.emm.common.dao.IBaseDao#listAll(java.lang.String, java.lang.String, int, int) 
	*/
	@Override
	public List<M> listAll(String whereClause, String orderClause, int pn,
			int length) {
		return listAll(HQL_LIST_ALL+ whereClause + orderClause, pn, length);
	}

	/**
	 * <p>
	 * Title: listByIds
	 * </p>
	 * <p>
	 * Description:根据指定的ID获取
	 * </p>
	 * 
	 * @param ids
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#listByIds(PK[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<M> listByIds(PK[] ids) {
		Query query = getSession().createQuery(HQL_LIST_BY_IDS);
		query.setParameterList("ids", ids);
		return query.list();
	}

	/**
	 * s
	 * <p>
	 * Title: save
	 * </p>
	 * <p>
	 * Description: 新增时保存
	 * </p>
	 * 
	 * @param model
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#save(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PK save(M model) {
		PK pk = (PK) getSession().save(model);
		//sessionFactory.evict(entityClass);
		return pk;
	}

	/**
	 * <p>
	 * Title: saveOrUpdate
	 * </p>
	 * <p>
	 * Description: 新增或者更新
	 * </p>
	 * 
	 * @param model
	 * @see com.cht.emm.common.dao.IBaseDao#saveOrUpdate(java.io.Serializable)
	 */
	@Override
	public void saveOrUpdate(M model) {
		getSession().saveOrUpdate(model);
	}

	/**
	 * <p>
	 * Title: update
	 * </p>
	 * <p>
	 * Description: 更新
	 * </p>
	 * 
	 * @param model
	 * @see com.cht.emm.common.dao.IBaseDao#update(java.io.Serializable)
	 */
	@Override
	public void update(M model) {
		//sessionFactory.evict(entityClass);
		getSession().update(model);

	}

	/**
	 * <p>
	 * Title: merge
	 * </p>
	 * <p>
	 * Description: 合并更新
	 * </p>
	 * 
	 * @param model
	 * @see com.cht.emm.common.dao.IBaseDao#merge(java.io.Serializable)
	 */
	@Override
	public void merge(M model) {
		getSession().merge(model);
	}

	/**
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description: 根据ID 删除
	 * </p>
	 * 
	 * @param id
	 * @see com.cht.emm.common.dao.IBaseDao#delete(java.io.Serializable)
	 */
	@Override
	public void delete(PK id) {
		
			getSession().delete(this.get(id));
	}

	/**
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description: 根据IDs 批量删除
	 * </p>
	 * 
	 * @param ids
	 * @see com.cht.emm.common.dao.IBaseDao#delete(PK[])
	 */
	@Override
	public void delete(PK[] ids) {
		for (PK pk : ids) {
			delete(pk);
		}
		// deleteObjectList(this.listByIds(ids));

	}

	/**
	 * <p>
	 * Title: deleteObjectList
	 * </p>
	 * <p>
	 * Description: 批量删除
	 * </p>
	 * 
	 * @param model
	 * @see com.cht.emm.common.dao.IBaseDao#deleteObjectList(java.util.List)
	 */
	@Override
	public void deleteObjectList(List<M> model) {
		for (M m : model) {
			deleteObject(m);
		}

	}

	/**
	 * <p>
	 * Title: deleteObject
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param model
	 * @see com.cht.emm.common.dao.IBaseDao#deleteObject(java.io.Serializable)
	 */
	@Override
	public void deleteObject(M model) {
		
			getSession().delete(model);
		

	}

	/**
	 * <p>
	 * Title: exists
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#exists(java.io.Serializable)
	 */
	@Override
	public boolean exists(PK id) {
		return get(id) != null;
	}

	/**
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#get(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public M get(PK id) {
		return (M) getSession().get(this.entityClass, id);
	}

	/**
	 * <p>
	 * Title: countAll
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#countAll()
	 */
	@Override
	public int countAll() {
		
		Long total = aggregate(HQL_COUNT_ALL);
		return total.intValue();
	}

	/**
	 * <p>
	 * Title: countAll
	 * </p>
	 * <p>
	 * Description: 按照条件查询的统计
	 * </p>
	 * 
	 * @param query
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#countAll(com.cht.emm.common.dao.util.ConditionQuery)
	 */
	@Override
	public int countAll(String whereClause) {

		Query query = getSession().createQuery(
				HQL_COUNT_ALL + " " + whereClause);

		Long total = (Long) query.uniqueResult();
		return total.intValue();
	}

	/**
	 * <p>
	 * Title: listAll
	 * </p>
	 * <p>
	 * Description:返回所有对象实例
	 * </p>
	 * 
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#listAll()
	 */
	@Override
	public List<M> listAll() {
		return list(HQL_LIST_ALL, -1, -1);
	}

	/**
	 * <p>
	 * Title: listAll
	 * </p>
	 * <p>
	 * Description:分页显示
	 * </p>
	 * 
	 * @param pn
	 * @param pageSize
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#listAll(int, int)
	 */
	@Override
	public List<M> listAll(int pn, int pageSize) {
		return list(HQL_LIST_ALL, pn, pageSize);
	}

	/**
	 * <p>
	 * Title: pre
	 * </p>
	 * <p>
	 * Description: 上一页
	 * </p>
	 * 
	 * @param pk
	 * @param pn
	 * @param pageSize
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#pre(java.io.Serializable,
	 *      int, int)
	 */
	@Override
	public List<M> pre(PK pk, int pn, int pageSize) {
		if (pk == null) {
			return list(HQL_LIST_ALL, pn, pageSize);
		}
		// 倒序，重排
		List<M> result = list(HQL_OPTIMIZE_PRE_LIST_ALL, 1, pageSize, pk);
		Collections.reverse(result);
		return result;
	}

	/**
	 * <p>
	 * Title: next
	 * </p>
	 * <p>
	 * Description:下一页
	 * </p>
	 * 
	 * @param pk
	 * @param pn
	 * @param pageSize
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#next(java.io.Serializable,
	 *      int, int)
	 */
	@Override
	public List<M> next(PK pk, int pn, int pageSize) {
		if (pk == null) {
			return list(HQL_LIST_ALL, pn, pageSize);
		}
		return list(HQL_OPTIMIZE_NEXT_LIST_ALL, 1, pageSize, pk);
	}

	/**
	 * <p>
	 * Title: flush
	 * </p>
	 * <p>
	 * Description: 刷新回写
	 * </p>
	 * 
	 * @see com.cht.emm.common.dao.IBaseDao#flush()
	 */
	@Override
	public void flush() {
		getSession().flush();
	}

	/**
	 * <p>
	 * Title: clear
	 * </p>
	 * <p>
	 * Description:清空session缓存
	 * </p>
	 * 
	 * @see com.cht.emm.common.dao.IBaseDao#clear()
	 */
	@Override
	public void clear() {
		getSession().clear();
	}

	/**
	 * @Title: getIdResult
	 * @Description: ?
	 * @param hql
	 * @param paramlist
	 * @return
	 * @return long 返回类型
	 * @throws
	 */
	protected long getIdResult(String hql, Object... paramlist) {
		long result = -1;
		List<?> list = list(hql, -1, -1, paramlist);
		if (list != null && list.size() > 0) {
			return ((Number) list.get(0)).longValue();
		}
		return result;
	}

	/**
	 * 
	 * @Title: listSelf
	 * @Description: 返回当前页
	 * @param hql
	 * @param pn
	 * @param pageSize
	 * @param paramlist
	 * @return
	 * @return List<M> 返回类型
	 * @throws
	 */
	protected List<M> listSelf(final String hql, final int pn,
			final int pageSize, final Object... paramlist) {
		return this.<M> list(hql, pn, pageSize, paramlist);
	}

	/**
	 * 
	 * @Title: listWithIn
	 * @Description: 分页
	 * @param hql
	 * @param start
	 * @param length
	 * @param map
	 * @param paramlist
	 * @return List<T> 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> listWithIn(final String hql, final int start,
			final int length, final Map<String, Collection<?>> map,
			final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		for (Entry<String, Collection<?>> e : map.entrySet()) {
			query.setParameterList(e.getKey(), e.getValue());
		}
		if (start > -1 && length > -1) {
			query.setMaxResults(length);
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		List<T> results = query.list();
		return results;
	}

	/**
	 * @Title: list
	 * @Description: 分页
	 * @param hql
	 * @param pn
	 * @param pageSize
	 * @param paramlist
	 * @return
	 * @return List<T> 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> list(final String hql, final int pn,
			final int pageSize, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
//		query.setCacheable(true);
		setParameters(query, paramlist);
		if (pn > -1 && pageSize > -1) {
			query.setMaxResults(pageSize);

			int start = PageUtil.getPageStart(pn, pageSize);
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		if (pn < 0) {
			query.setFirstResult(0);
		}
		List<T> results = query.list();
		return results;
	}

	/**
	 * 根据查询条件返回唯一一条记录
	 */
	@SuppressWarnings("unchecked")
	protected <T> T unique(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		return (T) query.setMaxResults(1).uniqueResult();
	}

	/**
	 * 
	 * @Title: aggregate
	 * @Description: 集成参数
	 * @param hql
	 * @param map
	 * @param paramlist
	 * @return
	 * @return T 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	protected <T> T aggregate(final String hql,
			final Map<String, Collection<?>> map, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		if (paramlist != null) {
			setParameters(query, paramlist);
			for (Entry<String, Collection<?>> e : map.entrySet()) {
				query.setParameterList(e.getKey(), e.getValue());
			}
		}

		return (T) query.uniqueResult();
	}

	/**
	 * @Title: aggregate
	 * @Description: 集成参数
	 * @param hql
	 * @param paramlist
	 * @return
	 * @return T 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	protected <T> T aggregate(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);

		return (T) query.uniqueResult();

	}

	/**
	 * 执行批处理语句.如 之间insert, update, delete 等.
	 */
	/**
	 * @Title: execteBulk
	 * @Description: 批量
	 * @param hql
	 * @param paramlist
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	protected int execteBulk(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		Object result = query.executeUpdate();
		return result == null ? 0 : ((Integer) result).intValue();
	}

	/**
	 * @Title: execteNativeBulk
	 * @Description: 执行sql 语句
	 * @param natvieSQL
	 * @param paramlist
	 * @return
	 * @return int 返回类型
	 * @throws
	 */
	protected int execteNativeBulk(final String natvieSQL,
			final Object... paramlist) {
		Query query = getSession().createSQLQuery(natvieSQL);
		setParameters(query, paramlist);
		Object result = query.executeUpdate();
		return result == null ? 0 : ((Integer) result).intValue();
	}

	// protected <T> List<T> list(final String sql, final Object... paramlist) {
	// return list(sql, -1, -1, paramlist);
	// }

	/**
	 * @Title: listByNative
	 * @Description:
	 * @param nativeSQL
	 * @param pn
	 * @param pageSize
	 * @param entityList
	 * @param scalarList
	 * @param paramlist
	 * @return
	 * @return List<T> 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> listByNative(final String nativeSQL, final int pn,
			final int pageSize, final List<Entry<String, Class<?>>> entityList,
			final List<Entry<String, Type>> scalarList,
			final Object... paramlist) {

		SQLQuery query = getSession().createSQLQuery(nativeSQL);
		if (entityList != null) {
			for (Entry<String, Class<?>> entity : entityList) {
				query.addEntity(entity.getKey(), entity.getValue());
			}
		}
		if (scalarList != null) {
			for (Entry<String, Type> entity : scalarList) {
				query.addScalar(entity.getKey(), entity.getValue());
			}
		}

		setParameters(query, paramlist);

		if (pn > -1 && pageSize > -1) {
			query.setMaxResults(pageSize);
			int start = PageUtil.getPageStart(pn, pageSize);
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		List<T> result = query.list();
		return result;
	}

	/**
	 * @Title: aggregateByNative
	 * @Description: 聚合
	 * @param natvieSQL
	 * @param scalarList
	 * @param paramlist
	 * @return
	 * @return T 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	protected <T> T aggregateByNative(final String natvieSQL,
			final List<Entry<String, Type>> scalarList,
			final Object... paramlist) {
		SQLQuery query = getSession().createSQLQuery(natvieSQL);
		if (scalarList != null) {
			for (Entry<String, Type> entity : scalarList) {
				query.addScalar(entity.getKey(), entity.getValue());
			}
		}

		setParameters(query, paramlist);

		Object result = query.uniqueResult();
		return (T) result;
	}

	/**
	 * @Title: list
	 * @Description: 按照条件查询
	 * @param query
	 * @param orderBy
	 * @param pn
	 * @param pageSize
	 * @return
	 * @return List<T> 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> list(ConditionQuery query, OrderBy orderBy,
			final int pn, final int pageSize) {
		Criteria criteria = getSession().createCriteria(this.entityClass);
		criteria.setCacheable(true);
		if (query != null)
			query.build(criteria);
		if (orderBy != null)
			orderBy.build(criteria);
		if(pn >-1 && pageSize >-1){
			int start = PageUtil.getPageStart(pn, pageSize);
			if (start != 0) {
				criteria.setFirstResult(start);
			}
		}
		
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

	/**
	 * @Title: list
	 * @Description:条件查询
	 * @param criteria
	 * @return List<T> 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> list(Criteria criteria) {
		criteria.setCacheable(true);
		return criteria.list();
	}

	/**
	 * @Title: unique
	 * @Description: 取唯一值
	 * @param criteria
	 * @return
	 * @return T 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public <T> T unique(Criteria criteria) {
		return (T) criteria.uniqueResult();
	}

	/**
	 * @Title: list
	 * @Description: 查询列表
	 * @param criteria
	 * @return
	 * @return List<T> 返回类型
	 * @throws
	 */
	public <T> List<T> list(DetachedCriteria criteria) {
		return list(criteria.getExecutableCriteria(getSession()));
	}

	/**
	 * @Title: unique
	 * @Description: 取唯一值
	 * @param criteria
	 * @return
	 * @return T 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public <T> T unique(DetachedCriteria criteria) {
		return (T) unique(criteria.getExecutableCriteria(getSession()));
	}

	/**
	 * @Title: setParameters
	 * @Description: 根据条件查询
	 * @param query
	 * @param paramlist
	 * @return void 返回类型
	 * @throws
	 */
	protected void setParameters(Query query, Object[] paramlist) {
		if (paramlist != null) {
			for (int i = 0; i < paramlist.length; i++) {
				if (paramlist[i] instanceof Date) {
					// 难道这是bug 使用setParameter不行？？
					query.setTimestamp(i, (Date) paramlist[i]);
				} else {
					query.setParameter(i, paramlist[i]);
				}
			}
		}
	}

	/**
	 * <p>
	 * Title: countAll
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param hql
	 * @param whereClause
	 * @return
	 * @see com.cht.emm.common.dao.IBaseDao#countAll(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Integer countAll(String hql, String whereClause) {
		Long total = (Long) this.unique(hql + whereClause);
		return total.intValue();
	}

	/* (non-Javadoc)
	 * @see nari.mip.backstage.common.dao.IBaseDao#listHql(java.lang.String, int, java.lang.Integer)
	 */
	@Override
	public List<M> listHql(String string, int pn, Integer length) {
		// TODO Auto-generated method stub
		return listSelf(string, pn, length);
	}

	 
}
