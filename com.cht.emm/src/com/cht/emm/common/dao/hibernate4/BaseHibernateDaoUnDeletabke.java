/**   
 * @Title: BaseHibernateDaoUnDeletabke.java 
 * @Package nari.mip.backstage.common.dao.hibernate4 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-10-11 上午10:26:40 
 * @version V1.0   
 */
package com.cht.emm.common.dao.hibernate4;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.model.AbstractModelUnDeletable;
import com.cht.emm.util.StringUtil;


/**
 * @ClassName: BaseHibernateDaoUnDeletabke
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-10-11 上午10:26:40
 * 
 */
public class BaseHibernateDaoUnDeletabke<M extends AbstractModelUnDeletable, PK extends java.io.Serializable>
		extends BaseHibernateDao<M, PK> {

	/**
	 * <p>
	 * Title: delete
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 * @see com.cht.emm.common.dao.hibernate4.BaseHibernateDao#delete(java.io.Serializable)
	 */
	@Override
	public void delete(PK id) {
		// TODO Auto-generated method stub
		M model = get(id);
		deleteObject(model);
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
	 * @see com.cht.emm.common.dao.hibernate4.BaseHibernateDao#deleteObject(com.cht.emm.common.model.AbstractModel)
	 */
	@Override
	public void deleteObject(M model) {
		// TODO Auto-generated method stub
		model.setDeleted(1);
		System.out.println(model.getDeleted());
		saveOrUpdate(model);

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
	 * @see com.cht.emm.common.dao.hibernate4.BaseHibernateDao#countAll()
	 */
	@Override
	public int countAll() {
		// TODO Auto-generated method stub

		return super.countAll(" where deleted = 0 ");
	}

	/**
	 * <p>
	 * Title: countAll
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param whereClause
	 * @return
	 * @see com.cht.emm.common.dao.hibernate4.BaseHibernateDao#countAll(java.lang.String)
	 */
	@Override
	public int countAll(String whereClause) {
		// TODO Auto-generated method stub
		return super.countAll(WrapWhereClause(whereClause));
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
	 * @see com.cht.emm.common.dao.hibernate4.BaseHibernateDao#countAll(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Integer countAll(String hql, String whereClause) {
		// TODO Auto-generated method stub
		return super.countAll(hql, WrapWhereClause(whereClause));
	}

	private String WrapWhereClause(String whereClause) {
		if (StringUtil.isNullOrBlank(whereClause)) {
			whereClause = " where deleted = 0 ";
		} else {
			whereClause += " and deleted = 0 ";
		}

		return whereClause;
	}

	private Criterion wrapCriterion() {
		return Restrictions.eq("deleted", 0);
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
	 * @see com.cht.emm.common.dao.hibernate4.BaseHibernateDao#listAll(com.cht.emm.common.dao.util.ConditionQuery,
	 *      com.cht.emm.common.dao.util.OrderBy, int, int)
	 */
	@Override
	public List<M> listAll(ConditionQuery query, OrderBy orderby, int pn,
			int pageSize) {
		// TODO Auto-generated method stub
		query.add(wrapCriterion());
		// TODO Auto-generated method stub
		return super.listAll(query, orderby, pn, pageSize);
	}

	/**
	 * <p>
	 * Title: save
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param model
	 * @return
	 * @see com.cht.emm.common.dao.hibernate4.BaseHibernateDao#save(com.cht.emm.common.model.AbstractModel)
	 */
	@Override
	public PK save(M model) {
		// TODO Auto-generated method stub
		if (model.getDeleted() == null) {
			model.setDeleted(0);
		}
		return super.save(model);
	}

	/**
	 * <p>
	 * Title: saveOrUpdate
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param model
	 * @see com.cht.emm.common.dao.hibernate4.BaseHibernateDao#saveOrUpdate(com.cht.emm.common.model.AbstractModel)
	 */
	@Override
	public void saveOrUpdate(M model) {
		// TODO Auto-generated method stub
		if (model.getDeleted() == null) {
			model.setDeleted(0);
		}
		super.saveOrUpdate(model);
	}

	/**
	 * <p>
	 * Title: listAll
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param whereClause
	 * @param orderClause
	 * @param pn
	 * @param length
	 * @return
	 * @see com.cht.emm.common.dao.hibernate4.BaseHibernateDao#listAll(java.lang.String,
	 *      java.lang.String, int, int)
	 */
	@Override
	public List<M> listAll(String whereClause, String orderClause, int pn,
			int length) {
		// TODO Auto-generated method stub
		return super.listAll(WrapWhereClause(whereClause), orderClause, pn,
				length);
	}

	/**
	 * <p>
	 * Title: listAll
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 * @see com.cht.emm.common.dao.hibernate4.BaseHibernateDao#listAll()
	 */
	@Override
	public List<M> listAll() {
		// TODO Auto-generated method stub

		return super.listAll(HQL_LIST_ALL + WrapWhereClause(""), -1, -1);
	}

	/**
	 * <p>
	 * Title: listAll
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param pn
	 * @param pageSize
	 * @return
	 * @see com.cht.emm.common.dao.hibernate4.BaseHibernateDao#listAll(int,
	 *      int)
	 */
	@Override
	public List<M> listAll(int pn, int pageSize) {
		// TODO Auto-generated method stub
		return super.listAll(HQL_LIST_ALL + WrapWhereClause(""), pn, pageSize);
	}

}
