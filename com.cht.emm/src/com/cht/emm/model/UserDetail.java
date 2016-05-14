/**   
 * @Title: UserDetail.java 
 * @Package nari.mip.backstage.model 
 * @Description: 用户详情
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-9-22 下午1:24:13 
 * @version V1.0   
 */
package com.cht.emm.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.cht.emm.common.model.AbstractModelUnDeletable;

/**
 * @ClassName: UserDetail
 * @Description: 用户相关的详情信息
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-9-22 下午1:24:13
 * 
 */
@Entity
@Table(name = "mip_sys_user_detail")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class UserDetail extends AbstractModelUnDeletable {

	/**
	 * @Fields serialVersionUID : 自动生成的序列号
	 */
	private static final long serialVersionUID = 3222179846789322927L;
	@Id
	private String id;
	private String userAlias;
	private String mobile;
	private String email;
	private Integer sex;
	private Timestamp createTime;
	private Timestamp modifyTime;

	public UserDetail() {
		super();
	}

	private User user;

	@Id
	@Column(name = "id", length = 36)
	@GenericGenerator(strategy = "foreign", name = "user.id", parameters = @Parameter(name = "property", value = "user"))
	@GeneratedValue(generator = "user.id")
	/**
	 * @return   id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            要设置的 id
	 */
	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "useralias", nullable = false, length = 255)
	/**
	 * @return   userAlias
	 */
	public String getUserAlias() {
		return userAlias;
	}

	/**
	 * @param userAlias
	 *            要设置的 userAlias
	 */
	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	@Column(name = "mobile", nullable = false, length = 255)
	/**
	 * @return   mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            要设置的 mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "email", nullable = false, length = 255)
	/**
	 * @return   email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            要设置的 email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "sex", nullable = false)
	/**
	 * @return   sex
	 */
	public Integer getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            要设置的 sex
	 */
	public void setSex(Integer sex) {
		this.sex = sex;
	}

	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
	@PrimaryKeyJoinColumn
	@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
	/**
	 * @return   user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            要设置的 user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "create_time", nullable = false)
	/**
	 * @return   createTime
	 */
	public Timestamp getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime
	 *            要设置的 createTime
	 */
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name = "modify_time")
	/**
	 * @return   modifyTime
	 */
	public Timestamp getModifyTime() {
		return modifyTime;
	}

	/**
	 * @param modifyTime
	 *            要设置的 modifyTime
	 */
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
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

	
	@Column(name="is_system")
	@Override
	public Integer getIsSystem() {
		return isSystem;
	}
}
