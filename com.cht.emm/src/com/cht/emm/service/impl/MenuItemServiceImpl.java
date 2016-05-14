/**
 * @Title: MenuItemService.java
 * @Package: nari.mip.backstage.service.impl
 * @Description:  MenuItem 基础服务
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2014-12-18上午11:01:06
 * @Version
 */
package com.cht.emm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.MenuItemDao;
import com.cht.emm.model.MenuItem;
import com.cht.emm.service.MenuItemService;
import com.cht.emm.util.StringUtil;
import com.cht.emm.util.UUIDGen;
import com.cht.emm.util.objectcopier.MenuItemCopier;
import com.cht.emm.vo.MenuItemVO;


/**
 * @Class: MenuItemService
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description:
 */
@Service("menuItemService")
public class MenuItemServiceImpl extends BaseService<MenuItem, String>
		implements MenuItemService {

	@Override
	@Resource(name = "menuItemDao")
	public void setBaseDao(IBaseDao<MenuItem, String> baseDao) {
		this.baseDao = baseDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nari.mip.backstage.service.impl.MenuItemService#getMenuItems(java.lang
	 * .String)
	 */
	@Override
	public List<MenuItemVO> getMenuItems(String parentId) {
		List<MenuItemVO> items = null;
		String whereClause = null;
		if (StringUtil.isNullOrBlank(parentId)) {
			whereClause = " where parentItem.id = null ";
		} else {
			whereClause = " where parentItem.id = '" + parentId + "' ";
		}
		List<MenuItem> result = baseDao.listAll(whereClause,
				" order by index asc ", -1, -1);
		items = MenuItemCopier.copy(result);
		return items;
	}

	/**
	 * @Name: updateOrSave
	 * @Decription: 新增一个Item，需要更新index等
	 * @Time: 2014-12-18 下午3:01:44
	 * @param item
	 * @return boolean
	 */
	@Override
	public MenuItem save(MenuItemVO item) {
		MenuItem copy = MenuItemCopier.fromVO(item);

		String parentId = StringUtil.filterId(item.getParentId());
		copy.setIndex(getMaxIndex(parentId));
		if (!StringUtil.isNullOrBlank(parentId)) {
			
			copy.setParentItem(baseDao.get(parentId));
			copy.setLayer(copy.getParentItem().getLayer()+1);
		}else {
			copy.setLayer(0);
		}
		((MenuItemDao) baseDao).updateIndex(parentId, copy.getIndex(), -1, 1);
		copy.setId(UUIDGen.getUUID());
		baseDao.save(copy);
		return copy;
	}

	/**
	 * @Name: getMaxIndex
	 * @Decription: 获得某个parentId下最大的index值，以方便排序
	 * @Time: 2014-12-18 下午3:02:47
	 * @param parentId
	 * @return int
	 */
	@Override
	public int getMaxIndex(String parentId) {
		return ((MenuItemDao) baseDao).getMaxIndex(parentId);
	}

	/**
	 * @Name: updateIndex
	 * @Decription: 更新index
	 * @Time: 2014-12-18 下午4:11:29
	 * @param parentId
	 * @param currentIndex
	 * @param step
	 *            void
	 */
	@Override
	public void updateIndex(String parentId, int currentIndex, int preIndex,
			int step) {
		((MenuItemDao) baseDao).updateIndex(parentId, currentIndex, preIndex,
				step);
	}

	/**
	 * @Name: getVO
	 * @Decription: 根据id获得menuItemVO对象
	 * @Time: 2014-12-19 上午9:17:09
	 * @param id
	 * @return MenuItemVO
	 */
	@Override
	public MenuItemVO getVO(String id) {
		return MenuItemCopier.copy(get(id));
	}

	@Override
	public List<MenuItemVO> getRoleMenus(String roleIds) {
		List<MenuItemVO> result = null;
		List<MenuItem> menus = ((MenuItemDao) baseDao).getRoleMenus(roleIds);
		if (menus != null && menus.size() > 0) {

			result = new ArrayList<MenuItemVO>();
			Map<String, MenuItemVO> map = new HashMap<String, MenuItemVO>();
			for (MenuItem menuItem : menus) {
				copyParent(menuItem, map, result);
			}
			map.clear();
		}
		return result;
	}

	public void copyParent(MenuItem item, Map<String, MenuItemVO> map,
			List<MenuItemVO> result) {
		MenuItemVO vo = map.get(item.getId());
		if (vo == null) {
			vo = MenuItemCopier.singleCopy(item);
			map.put(item.getId(), vo);
		}
		if (item.getParentItem() != null) {
			System.out.println("有parent: " + item.getTitle());
			MenuItem parent = item.getParentItem();
			MenuItemVO parentVo = map.get(parent.getId());
			if (parentVo == null) {
				parentVo = MenuItemCopier.singleCopy(parent);
				map.put(parent.getId(), parentVo);
			}
			synchronized (parentVo) {
				List<MenuItemVO> subs = parentVo.getSubItems();
				if (subs == null) {
					subs = new ArrayList<MenuItemVO>();
					parentVo.setSubItems(subs);
				}
				subs.add(vo);
				Collections.sort(subs);
			}
			copyParent(parent, map, result);
		} else {
			System.out.println("没有parent: " + item.getTitle());
			if (!result.contains(vo)) {
				result.add(vo);
				Collections.sort(result);
			}

		}
		// System.out.println(vo.getTitle());
		// System.out.println(result.size());
	}

	/**
	 * @Name: getUserMenus
	 * @Decription: 获得用户所拥有的菜单，注意处理role_of_amin(即系统管理员权限时的菜单)
	 * @Time: 2014-12-29 下午2:11:26
	 * @param userId
	 * @return List<MenuItemVO>
	 */
	@Override
	public List<MenuItemVO> getUserMenus(String userId) {
		List<MenuItem> menus = ((MenuItemDao) baseDao).getUserMenus(userId);
		List<MenuItemVO> result = null;
		if (menus != null && menus.size() > 0) {

			result = new ArrayList<MenuItemVO>();
			Map<String, MenuItemVO> map = new HashMap<String, MenuItemVO>();
			for (MenuItem menuItem : menus) {
				copyParent(menuItem, map, result);
			}
			map.clear();
		}
		return result;
	}
}
