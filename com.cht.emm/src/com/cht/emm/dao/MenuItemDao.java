/**
 * @Title: MenuItemDao.java
 * @Package: nari.mip.backstage.dao
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2014-12-18上午11:11:40
 * @Version
 */
package com.cht.emm.dao;

import java.util.List;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.model.MenuItem;


/**
 * @Class: MenuItemDao
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description:
 */
public interface MenuItemDao extends IBaseDao<MenuItem, String> {

	/**
	 * @Name: updateIndex
	 * @Decription: 当新增或修改一个Item时，需要对应的修改index索引，使排序不出现问题
	 * @Time: 2014-12-18 下午3:21:36
	 * @param parentId
	 * @param currentIndex
	 * @param step
	 */
	public void updateIndex(String parentId, int currentIndex, int preIndex,
			int step);

	/**
	 * @Name: getMaxIndex
	 * @Decription:获得最大的索引
	 * @Time: 2014-12-19 上午9:35:21
	 * @param parentId
	 * @return int
	 */
	public int getMaxIndex(String parentId);
	
	/**
	 * @Name: getMaxIndex
	 * @Decription:获得最大的索引
	 * @Time: 2014-12-19 上午9:35:21
	 * @param parentId
	 * @return int
	 */
	public abstract List<MenuItem> getRoleMenus(String roleId);

	/**
	 * @Name: getUserMenus
	 * @Decription: 获得用户的menu列表
	 * @Time: 2014-12-29 下午2:49:54
	 * @param userId
	 * @return List<MenuItem>
	 */
	public List<MenuItem> getUserMenus(String userId);
}
