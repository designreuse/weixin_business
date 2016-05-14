/**
 * @Title: ThirdPartConfig.java
 * @Package: nari.mip.backstage.model
 * @Description: 第三方配置的模型
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-3-4 下午3:10:32
 * @Version: 1.0
 */
package com.cht.emm.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.cht.emm.common.model.AbstractModelUnDeletable;


/**
 * @Class: ThirdPartConfig
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 第三方接入的配置
 */
@Entity
@Table(name = "mip_sys_thirdpart_config")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class ThirdPartConfig extends AbstractModelUnDeletable{
  

	/**
	 * 
	 */
	private static final long serialVersionUID = 4509709800403127162L;
	@Id
	private String id;
	private String name;
	private String className;
	private String remoteUrl;
	//private String protocol;
	private Set<User> users;
	private String others;
	private Group group;
	
	
	


	/**
	 * @return the id
	 */
	@Id
	@Column(name = "id", length = 36)
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
	 * @return the name
	 */
	@Column(name = "name", length = 256)
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the accountName
	 */
	 
 
	/**
	 * @return the className
	 */
	@Column(name = "class_name", length = 256)
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
	 * @return the remoteUrl
	 */
	@Column(name = "remote_url", length = 256)
	public String getRemoteUrl() {
		return remoteUrl;
	}

	/**
	 * @param remoteUrl the remoteUrl to set
	 */
	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}

//	/**
//	 * @return the protocol
//	 */
//	public String getProtocol() {
//		return protocol;
//	}
//
//	/**
//	 * @param protocol the protocol to set
//	 */
//	public void setProtocol(String protocol) {
//		this.protocol = protocol;
//	}

	/**
	 * @return the users
	 */
	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST,
			CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "thirdPartConfig")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}

	/**
	 * @return the others
	 */
	@Column(name = "others", length = 512)
	public String getOthers() {
		return others;
	}

	/**
	 * @param others the others to set
	 */
	public void setOthers(String others) {
		this.others = others;
	}

	/**
	 * @return the group
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@JoinColumn(name = "group_id")
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	public Group getGroup() {
		return group;
	}

	/**
	 * @param group the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}
	@Column(name = "deleted", nullable = false)
	@Override
	public Integer getDeleted() {
		return deleted;
	}
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
	
}
