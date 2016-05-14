/**   
 * @Title: User.java 
 * @Package nari.mip.backstage.rpc.model 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-9-29 上午11:05:45 
 * @version V1.0   
 */
package com.cht.emm.rpc.model;

/**
 * @ClassName: User
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-9-29 上午11:05:45
 * 
 */
public class RPCUser {
	private String user_id;
	private String password;
	private String org_id;
	private String real_name;
	private Integer status;

	/**
	 * @return user_id
	 */
	public String getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id
	 *            要设置的 user_id
	 */
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            要设置的 password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return org_id
	 */
	public String getOrg_id() {
		return org_id;
	}

	/**
	 * @param org_id
	 *            要设置的 org_id
	 */
	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	/**
	 * @return real_name
	 */
	public String getReal_name() {
		return real_name;
	}

	/**
	 * @param real_name
	 *            要设置的 real_name
	 */
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	/**
	 * @return status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            要设置的 status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
}
