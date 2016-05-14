package com.cht.emm.service;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.pagination.Page;
import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.Authority;
import com.cht.emm.vo.AuthVO;

public interface AuthorityService extends IBaseService<Authority, String> {

	public AuthVO getById(String id);

	/**
	 * @Title: queryForPage
	 * @Description: 分页查询
	 * @param count
	 * @param query
	 * @param orderBy
	 * @param pn
	 * @param pageSize
	 * @return
	 * @return Page<UserVO> 返回类型
	 * @throws
	 */
	public Page<AuthVO> queryForPage(Integer count, ConditionQuery query,
			OrderBy orderBy, int pn, int pageSize);

	/**
	 * @Title: queryForPage
	 * @Description: 分页查询
	 * @param queryAll
	 * @param whereClause
	 * @param orderby
	 * @param pn
	 * @param length
	 * @return
	 * @return Page<UserVO> 返回类型
	 * @throws
	 */
	public Page<AuthVO> queryForPage(int count, String whereClause,
			String orderby, int pn, Integer length);

	public Integer getMaxIndex();

	/**
	 * @Title: updateShowIndex
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param showIndex
	 * @return void 返回类型
	 * @throws
	 */
	public boolean updateShowIndex(Integer current, Integer pre);
}
