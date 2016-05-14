/**
 * @Title: MenuItemCopier.java
 * @Package: nari.mip.backstage.util.objectcopier
 * @Description: 讲 数据对象MenuItem 转换成对应的试图对象MenuItemVO
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2014-12-18 上午11:22:21
 * @Version: 1.0
 */
package com.cht.emm.util.objectcopier;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cht.emm.model.MenuItem;
import com.cht.emm.util.StringUtil;
import com.cht.emm.vo.MenuItemVO;


/**
 * @Class: MenuItemCopier
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: MenuItem 数据库对象-> 试图对象工具
 */
public class MenuItemCopier {

	/**
	 * @Name: singleCopy
	 * @Decription: 转录数据库表信息
	 * @Time: 2014-12-18 下午2:33:56
	 * @param item
	 * @return MenuItemVO
	 */
	public static MenuItemVO singleCopy(MenuItem item) {
		if (item == null) {
			return null;
		}
		MenuItemVO copy = new MenuItemVO();
		if (!StringUtil.isNullOrBlank(item.getClassName()))
			copy.setClassName(item.getClassName());
		copy.setId(item.getId());
		copy.setLeaf(item.getIsLeaf() == 1);
		if (!StringUtil.isNullOrBlank(item.getTitleClass()))
			copy.setTitleClass(item.getTitleClass());
		if (!StringUtil.isNullOrBlank(item.getBeforeTitle())) {
			copy.setBeforeTitle(item.getBeforeTitle());
		}
		if (!StringUtil.isNullOrBlank(item.getAfterTitle())) {
			copy.setAfterTitle(item.getAfterTitle());
		}
		copy.setTitle(item.getTitle());
		if (!StringUtil.isNullOrBlank(item.getTrigger()))
			copy.setTrigger(item.getTrigger());
		if(!StringUtil.isNullOrBlank(item.getLinkClass())){
			copy.setLinkClass(item.getLinkClass());
		}
		copy.setRoot(false);
		copy.setIndex(item.getIndex());
		copy.setLeaf(item.getIsLeaf() == 1);
		if(StringUtil.isNullOrBlank(item.getUrl())){
			copy.setUrl("#");
		}else {
			copy.setUrl(item.getUrl());
		}
		copy.setIsSystem(item.getIsSystem());
		
		return copy;
	}

	/**
	 * @Name: copy
	 * @Decription: 转录表信息和关联关系
	 * @Time: 2014-12-18 下午2:34:27
	 * @param item
	 * @return MenuItemVO
	 */
	public static MenuItemVO copy(MenuItem item) {
		if (item == null) {
			return null;
		}
		MenuItemVO copy = singleCopy(item);
		copy.setResource(ResourceCopier.singleCopy(item.getResource()));
		if (item.getParentItem() != null) {
			copy.setParentItem(singleCopy(item.getParentItem()));
			copy.setParentId(item.getParentItem().getId());
		}

		if (item.getSubItems() != null) {
			copy.setSubItems(copySub(item.getSubItems()));
		}

		if (item.getResource() != null) {
			copy.setResource(ResourceCopier.singleCopy(item.getResource()));
		}
		return copy;
	}

	/**
	 * @Name: copy
	 * @Decription: 复制一批menuitem,包含从属关系
	 * @Time: 2014-12-18 下午2:35:18
	 * @param items
	 * @return List<MenuItemVO>
	 */
	public static List<MenuItemVO> copy(List<MenuItem> items) {

		if (items == null) {
			return null;
		}
		List<MenuItemVO> copies = new ArrayList<MenuItemVO>();
		for (MenuItem item : items) {
			MenuItemVO copy = singleCopy(item);
			copy.setParentItem(singleCopy(item.getParentItem()));
			copy.setSubItems(copySub(item.getSubItems()));
			copies.add(copy);
		}
		return copies;
	}

	/**
	 * @Name: copySub
	 * @Decription: 转录从属item的表内信息
	 * @Time: 2014-12-18 下午2:36:59
	 * @param items
	 * @return List<MenuItemVO>
	 */
	public static List<MenuItemVO> copySub(Set<MenuItem> items) {
		if (items == null) {
			return null;
		}
		List<MenuItemVO> copies = new ArrayList<MenuItemVO>();
		for (MenuItem item : items) {
			MenuItemVO copy = singleCopy(item);
			copy.setSubItems(MenuItemCopier.copySub(item.getSubItems()));
			copies.add(copy);
		}
		return copies;

	}

	public static MenuItem fromVO(MenuItemVO copy) {
		MenuItem item = new MenuItem();
		item.setClassName(copy.getClassName());
		item.setId(copy.getId());
		item.setIndex(copy.getIndex());
		item.setIsLeaf(copy.isLeaf() ? 1 : 0);
		item.setTitle(copy.getTitle());
		item.setTrigger(copy.getTrigger());
		item.setBeforeTitle(copy.getBeforeTitle());
		item.setAfterTitle(copy.getAfterTitle());
		item.setLinkClass(copy.getLinkClass());
		item.setLayer(copy.getLayer());
		item.setTitleClass(copy.getTitleClass());
		return item;
	}

}
