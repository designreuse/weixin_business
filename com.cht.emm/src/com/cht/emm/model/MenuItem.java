/**   
 * @Title: MenuItem.java 
 * @Package nari.mip.backstage.model 
 * @Description: 用户详情
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-12-18 上午10:14:13    
 * @version V1.0   
 */
package com.cht.emm.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModel;


 /**
  * 
  * @Class: MenuItem
  * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
  * @Description: 导航栏菜单子项
  */
@Entity
@Table(name = "mip_sys_menu_item")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class MenuItem extends AbstractModel {
	 
	private static final long serialVersionUID = -827667933291371544L;
	@Id
	/* id	 */
	private String id;
	/*
	 * 标题
	 */
	private String title;
	
	private String beforeTitle;
	
	private String afterTitle;
	/* 位置索引 */
	private Integer index;
	
	/* 是否是叶子节点 */
	private Integer isLeaf =0;
	
	/* li类名 */
	private String className;
	
	/* a 类名 */
	private String linkClass;
	
	/* 样式 */
	private String titleClass;
	
	/* 触发器  */
	private String trigger; 
	
	/* 层级 */
	private Integer layer;
	
	/* 跳转的url地址  */
	private String url;
	
	/* 父项  */
	private MenuItem parentItem;
	
	/* 子项  */
	private Set<MenuItem> subItems = new HashSet<MenuItem>(0);
	
	/*
	 *菜单对应的资源 
	 */
	private Resource resource;
	
	 
	@Id
	@Column(name = "id", length = 36)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "title", length = 255)
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	@Column(name = "before_title", length = 255)
	public String getBeforeTitle() {
		return beforeTitle;
	}
	public void setBeforeTitle(String beforeTitle) {
		this.beforeTitle = beforeTitle;
	}
	@Column(name = "after_title", length = 255)
	public String getAfterTitle() {
		return afterTitle;
	}
	public void setAfterTitle(String afterTitle) {
		this.afterTitle = afterTitle;
	}
	@Column(name = "title_class", length = 255)
	public String getTitleClass() {
		return titleClass;
	}
	public void setTitleClass(String titleClass) {
		this.titleClass = titleClass;
	}
	@Column(name = "loc_index")
	public Integer getIndex() {
		return index;
	}
	
	public void setIndex(Integer index) {
		this.index = index;
	}
	
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	@Column(name = "is_leaf")
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	
	@Column(name = "class_name",length = 255)
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	@Column(name = "link_class",length = 255)
	public String getLinkClass() {
		return linkClass;
	}
	public void setLinkClass(String linkClass) {
		this.linkClass = linkClass;
	}
	@Column(name = "action_trigger",length = 255 )
	public String getTrigger() {
		return trigger;
	}
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	
	@Column(name = "url",length = 255 )
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@ManyToOne(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public MenuItem getParentItem() {
		return parentItem;
	}
	public void setParentItem(MenuItem parentItem) {
		this.parentItem = parentItem;
	}
	
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE,CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "parentItem", orphanRemoval = false)
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<MenuItem> getSubItems() {
		return subItems;
	}
	public void setSubItems(Set<MenuItem> subItems) {
		this.subItems = subItems;
	}
	@OneToOne(cascade={CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REFRESH}) 
	@JoinColumn(name="resource_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
