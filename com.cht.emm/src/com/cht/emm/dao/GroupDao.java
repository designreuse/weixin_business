package com.cht.emm.dao;

import java.util.List;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.model.Group;


public interface GroupDao extends IBaseDao<Group, String> {
 
	/**
	 * @Name: excludedList
	 * @Decription: 根据查询条件 排除一些不相关的组
	 * @Time: 2015-3-24 上午10:15:26
	 * @param excluded
	 * @param excludedId
	 * @return List<Group>
	 */
	List<Group> excludedList(String excluded, String excludedId);
	/**
	 * @Name: getTopGroup
	 * @Decription: 查询顶级组，位于最高位置的组
	 * @Time: 2015-3-24 上午10:15:18
	 * @return Group
	 */
	Group getTopGroup();
	
	/**
	 * @Name: getThirdPartTopGroup
	 * @Decription: 查找第三方应用的顶级组
	 * @Time: 2015-3-24 上午10:17:33
	 * @return Group
	 */
	Group getThirdPartTopGroup();
}