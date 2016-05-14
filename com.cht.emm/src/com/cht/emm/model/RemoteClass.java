/**
 * @Title: RemoteClass.java
 * @Package: nari.mip.backstage.model
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-3-23 上午10:28:05
 * @Version: 1.0
 */
package com.cht.emm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModel;


/**
 * @Class: RemoteClass
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
@Entity
@Table(name = "mip_sys_remote_class")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class RemoteClass extends AbstractModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8228187883559622385L;
	
	@Id
	private String id;
	private String className;
	private String packageName;
	private String desc;
	private String content;
	private Integer enabled =0;
	
	/**
	 * @return the enabled
	 */
	@Column(name="enabled",nullable=false)
	public Integer getEnabled() {
		return enabled;
	}



	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}



	/**
	 * @return the id
	 */
	@Id
	@Column(name="id",length=36)
	public String getId() {
		return id;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



	/**
	 * @return the className
	 */
	@Column(name="class_name",length=255,unique=true,nullable=false)
	public String getClassName() {
		return className;
	}



	/**
	 * @param className the className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}



	/**
	 * @return the packageName
	 */
	@Column(name="package_name",length=255,unique=false,nullable=false)
	public String getPackageName() {
		return packageName;
	}



	/**
	 * @param packageName the packageName to set
	 */
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}



	/**
	 * @return the desc
	 */
	@Column(name="class_desc",length=255,unique=true,nullable=false)
	public String getDesc() {
		return desc;
	}



	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}



	/**
	 * @return the content
	 */
	
	@Lob
	@Column(name = "content")
	public String getContent() {
		return content;
	}


	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}



	
	/* (non-Javadoc)
	 * @see nari.mip.backstage.common.model.AbstractModel#getIsSystem()
	 */
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}

}
