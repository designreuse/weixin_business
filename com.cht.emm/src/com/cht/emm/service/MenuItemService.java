package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.MenuItem;
import com.cht.emm.vo.MenuItemVO;


public interface MenuItemService extends IBaseService<MenuItem, String> {

	/**
	 * @Name: getMenuItems
	 * @Decription:根据parentId 获得该父项下的所有子项，当 
	 * @Time: 2014-12-18 下午2:41:18
	 * @param parentId
	 * @return List<MenuItemVO>
	 */
	public abstract List<MenuItemVO> getMenuItems(String parentId);
	
	
	/**
	 * @Name: updateOrSave
	 * @Decription: 保存或新增一个Item，需要更新index等
	 * @Time: 2014-12-18 下午3:01:44
	 * @param item
	 * @return boolean
	 */
	public MenuItem save(MenuItemVO item);
	
	/**
	 * @Name: getMaxIndex
	 * @Decription: 获得某个parentId下最大的index值，以方便排序
	 * @Time: 2014-12-18 下午3:02:47
	 * @param parentId
	 * @return int
	 */
	public abstract int getMaxIndex(String parentId);
	
	
	/**
	 * @Name: updateIndex
	 * @Decription: 更新index
	 * @Time: 2014-12-18 下午4:11:29
	 * @param parentId
	 * @param currentIndex
	 * @param step void
	 */
    public void updateIndex(String parentId,int currentIndex,int preIndex,int step);
    
    /**
     * @Name: getVO
     * @Decription: 根据id获得menuItemVO对象
     * @Time: 2014-12-19 上午9:17:09
     * @param id
     * @return MenuItemVO
     */
    public MenuItemVO getVO(String id);


	public abstract List<MenuItemVO> getRoleMenus(String roleId);


	/**
	 * @Name: getUserMenus
	 * @Decription: 获得用户所拥有的菜单，注意处理role_of_amin(即系统管理员权限时的菜单)
	 * @Time: 2014-12-29 下午2:11:26
	 * @param userId
	 * @return List<MenuItemVO>
	 */
	public abstract List<MenuItemVO> getUserMenus(String userId);

}