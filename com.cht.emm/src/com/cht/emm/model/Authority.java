package com.cht.emm.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;


/**
 * Authority entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "mip_sys_authority")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Authority extends AbstractModelUnDeletable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -3935946539510975300L;
	@Id
	private String id;
	private String name;
	private Integer showIndex;
	private Integer locIndex;
	private String descp;
	// Constructors

	/** default constructor */
	public Authority() {
		super();
	}

	/** minimal constructor */
	public Authority(String id, String name) {
		this.id = id;
		this.name = name;
	}

	/** full constructor */
	public Authority(String id, String name, Integer showIndex,
			Integer locIndex, String descp) {
		this.id = id;
		this.name = name;
		this.showIndex = showIndex;
		this.locIndex = locIndex;
		this.descp = descp;
	}

	// Property accessors
	@Id
	@Column(name = "id", length = 36)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false, length = 255)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "show_index")
	public Integer getShowIndex() {
		return this.showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}

	@Column(name = "loc_index")
	public Integer getLocIndex() {
		return this.locIndex;
	}

	public void setLocIndex(Integer locIndex) {
		this.locIndex = locIndex;
	}

	@Column(name = "descp",length =1024)
	public String getDescp() {
		return this.descp;
	}

	public void setDescp(String descp) {
		this.descp = descp;
	}

	@Column(name = "deleted", nullable = false)
	/**
	 * <p>Title: getDeleted</p> 
	 * <p>Description: </p> 
	 * @return 
	 * @see nari.mip.backstage.common.model.AbstractModelUnDeletable#getDeleted() 
	 */
	@Override
	public Integer getDeleted() {
		return deleted;
	}
	
	/**
	 * 是否是系统默认
	 */
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}

}