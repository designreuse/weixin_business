/**
 * @Title: MenuItemDaoImp.java
 * @Package: nari.mip.backstage.dao.impl
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2014-12-18 下午3:11:01
 * @Version: 1.0
 */
package com.cht.emm.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.MenuItemDao;
import com.cht.emm.model.MenuItem;
import com.cht.emm.util.StringUtil;


/**
 * @Class: MenuItemDaoImp
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description:
 */
@Repository("menuItemDao")
public class MenuItemDaoImp extends BaseHibernateDao<MenuItem, String>
		implements MenuItemDao {
	/**
	 * @Name: updateIndex
	 * @Decription: 当新增或修改一个Item时，需要对应的修改index索引，使排序不出现问题
	 * @Time: 2014-12-18 下午3:21:36
	 * @param parentId
	 * @param currentIndex
	 * @param step
	 */
	protected static final Logger log = LoggerFactory
			.getLogger(MenuItemDaoImp.class);

	/**
	 * @Name: updateIndex
	 * @Decription: 当新增或修改一个Item时，需要对应的修改index索引，使排序不出现问题
	 * @Time: 2014-12-18 下午3:21:36
	 * @param parentId
	 * @param currentIndex
	 * @param preIndex
	 * @param step
	 */
	@Override
	public void updateIndex(String parentId, int currentIndex,int preIndex, int step) {
		// index 最小是0，即从0开始计数
		/*
		 * 基本逻辑，对于父项下的所有子项，
		 *  1. 当step =-1时，表示移除子项更新索引： 所有大于currentIndex的索引都-1
		 *  2. 当step = 1时，需考虑：a: preIndex > currentIndex, 即子项的前移，所有< preIndex 且 > currentIndex的索引+1;b:
		 *  preIndex < currentIndex, 即子项后移，此时 <currentIndex,且 > preIndex 所有子项的索引 -1；
		 *  3. 当preIndex <0，此时即为新增操作，所有 >=currentIndex的子项索引+1；
		 */
		String sql =null;
		int state =4;
		boolean parentIsNull =false;
		if(StringUtil.isNullOrBlank(parentId) || "-1".equals(parentId) || "#".equals(parentId)){
			parentIsNull =true;
		}
		if(step ==-1){
			if(parentIsNull){
				sql = "update MenuItem set index = index - 1  where parentItem.id = null and index >= ?";
			}else {
				sql = "update MenuItem set index = index - 1  where parentItem.id = ? and index >= ?";
			}
			 
			 state =0;
		}else if(step == 1){
			if(preIndex >= 0){
				if(preIndex > currentIndex){
					state =1;
					if(!parentIsNull){
						sql = "update MenuItem set index = index + 1  where parentItem.id = ? and index >= ? and index < ? ";
					}else{
						sql = "update MenuItem set index = index + 1  where parentItem.id = null and index >= ? and index < ? ";
					}
				}else if(preIndex < currentIndex ){
					if(!parentIsNull){
						sql = "update MenuItem set index = index - 1  where parentItem.id = ? and index >= ? and index < ? ";
					}else {
						sql = "update MenuItem set index = index - 1  where parentItem.id = null and index >= ? and index < ? ";
					}
					state = 2;
				}
			}else{
				if(!parentIsNull){
					sql = "update MenuItem set index = index + 1  where parentItem.id = ? and index >= ?";
				}else {
					sql = "update MenuItem set index = index + 1  where parentItem.id = null and index >= ?";
				}
				 state =3;
			}
		}
		if(state !=4){
			Query query = getSession().createQuery(sql);
			switch (state) {
			case 0:
				if(parentIsNull){
					query.setInteger(0, currentIndex);
				}else {
					query.setString(0, parentId);
					query.setInteger(1, currentIndex);
				}
				
				break;
			case 1:
				if(parentIsNull){
					query.setInteger(0, currentIndex);
					query.setInteger(1, preIndex);
				}else {
					query.setString(0, parentId);
					query.setInteger(1, currentIndex);
					query.setInteger(2, preIndex);
				}
				
				break;
			case 2:
				if(parentIsNull){
					query.setInteger(0, preIndex);
					query.setInteger(1, currentIndex);
				}else {
					query.setString(0, parentId);
					query.setInteger(1, preIndex);
					query.setInteger(2, currentIndex);
				}
				
				break;
			case 3:
				if(parentIsNull){
					query.setInteger(0, currentIndex);
				}else {
					query.setString(0, parentId);
					query.setInteger(1, currentIndex);
				}
				
				break;
			default:
				 
				break;
			}
			int size = query.executeUpdate();
			log.debug("menuItem.updateIndex.size", size);
		}else {
			log.error("MenuItemDaoImp.updateIndex","参数错误");
		}
		
		
	}

	@Override
	public int getMaxIndex(String parentId) {
		String sql= null;
		Query query = null;
		if(parentId != null && !"-1".equals(parentId)){
			sql = "select count(*) from MenuItem where parentItem.id = ?";
			query = getSession().createQuery(sql);
			query.setString(0, parentId);
		}else{
			sql = "select count(*) from MenuItem where parentItem =null";
			query = getSession().createQuery(sql);
		}
		
		Long result = (Long)query.uniqueResult();
		return result!=null?result.intValue():0;
	}
	
	
	/**
	 * @Name: getRoleMenus
	 * @Decription: 获得role具有的menu叶子节点
	 * @Time: 2014-12-26 上午10:27:11
	 * @param roleId
	 * @return List<MenuItem>
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<MenuItem> getRoleMenus(String roleIds){
		List<MenuItem> menus =  null;
		String sql= null;
		Query query = null;
		if(roleIds != null  ){
			String[] roleId = roleIds.split(",");
			
			
			
			sql = "select m from MenuItem m inner join fetch m.resource ra   "+
				  " inner join ra.resourcePermissions rr " +
				  " where rr.role.id in (:roleId) and ra.isItem=1 order by m.layer,m.index asc";
			
			query = getSession().createQuery(sql);
			query.setParameterList("roleId", roleId);
		} 
		
		menus  = (List<MenuItem>) query.list();
		
		return menus;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuItem> getUserMenus(String userId) {
		List<MenuItem> menus =  null;
		String sql= null;
		Query query = null;
		if(userId != null  ){
			sql = "select distinct m from MenuItem m " +
				  "inner join m.resource r  inner join  "+
				  " r.resourcePermissions rr inner join rr.role r1 inner join r1.userRoles ur " +
				  " where ur.user.id =:userid   and r.isItem=1 order by m.layer,m.index asc ";
			query = getSession().createQuery(sql);
			query.setString("userid", userId);
		} 
		menus  = (List<MenuItem>) query.list();
		return menus;
	}

}
