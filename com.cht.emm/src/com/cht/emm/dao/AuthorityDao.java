package com.cht.emm.dao;

import java.util.List;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.model.Authority;


public interface AuthorityDao extends IBaseDao<Authority, String> {

	List<Authority> listByIds(String[] ids);

	/**
	 * @Title: updateShowIndex
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param showIndex
	 * @return
	 * @return boolean 返回类型
	 * @throws
	 */
	boolean updateShowIndex(Integer current, Integer pre);
}