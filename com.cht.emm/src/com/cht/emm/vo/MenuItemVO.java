package com.cht.emm.vo;

import java.util.List;

public class MenuItemVO implements Comparable<MenuItemVO>{

	private String id;
	private String title;
	private boolean isLeaf;
	private String url;
	private String className;
	private String linkClass;
	private String titleClass;
	private String beforeTitle;
	private String afterTitle;
	private String trigger;
	private Integer layer;
	private Integer isSystem;

	/**
	 * @return the isSystem
	 */
	public Integer getIsSystem() {
		return isSystem;
	}

	/**
	 * @param isSystem the isSystem to set
	 */
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
	/*
	 * 如果preParentId 和现在的parentItem的id 不相等，
	 * 则原父项下的子项要-1，新增到的子项id相应的+1
	 */
	private String preParentId;
	private int preIndex;
	private int index;
	private String parentId;
	private MenuItemVO parentItem;
	private List<MenuItemVO> subItems;
	private ResourceVO resource;
	
	
	/* 父节点下子节点的个数 */
	private int max_index = 0;
	/**
	 * 对于root节点需要做特殊处理
	 */
	private boolean isRoot = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getLinkClass() {
		return linkClass;
	}

	public void setLinkClass(String linkClass) {
		this.linkClass = linkClass;
	}

	public String getTitleClass() {
		return titleClass;
	}

	public void setTitleClass(String titleClass) {
		this.titleClass = titleClass;
	}

	public String getBeforeTitle() {
		return beforeTitle;
	}

	public void setBeforeTitle(String beforeTitle) {
		this.beforeTitle = beforeTitle;
	}

	public String getAfterTitle() {
		return afterTitle;
	}

	public void setAfterTitle(String afterTitle) {
		this.afterTitle = afterTitle;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	public String getPreParentId() {
		return preParentId;
	}

	public void setPreParentId(String preParentId) {
		this.preParentId = preParentId;
	}

	public int getPreIndex() {
		return preIndex;
	}

	public void setPreIndex(int preIndex) {
		this.preIndex = preIndex;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public MenuItemVO getParentItem() {
		return parentItem;
	}

	public void setParentItem(MenuItemVO parentItem) {
		this.parentItem = parentItem;
	}

	public List<MenuItemVO> getSubItems() {
		return subItems;
	}

	public void setSubItems(List<MenuItemVO> subItems) {
		this.subItems = subItems;
	}

 

 

	public int getMax_index() {
		return max_index;
	}

	public void setMax_index(int max_index) {
		this.max_index = max_index;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public ResourceVO getResource() {
		return resource;
	}

	public void setResource(ResourceVO resource) {
		this.resource = resource;
	}

	@Override
	public int compareTo(MenuItemVO o) {
		// TODO Auto-generated method stub
		int result = 0;
		if(this.getLayer()== o.getLayer()){
			if(this.getIndex()!=o.getIndex()){
				result  =  this.getIndex()>o.getIndex()?1:-1;
			}
		}else{
			result = this.getLayer() > o.getLayer()?1:-1;
		}
		return result;
	}

	
}
