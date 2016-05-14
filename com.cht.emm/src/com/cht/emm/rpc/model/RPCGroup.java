/**   
 * @Title: RPCGroup.java 
 * @Package nari.mip.backstage.rpc.model 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-9-28 下午4:24:56 
 * @version V1.0   
 */
package com.cht.emm.rpc.model;

/**
 * @ClassName: RPCGroup
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-9-28 下午4:24:56
 * 
 */
public class RPCGroup {

	private String id;
	private String parent_id;
	private String org_name;
	private String org_desc;
	private int status;

	/**
	 * @return id
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

	/**
	 * @return parent_id
	 */
	public String getParent_id() {
		return parent_id;
	}

	/**
	 * @param parent_id
	 *            要设置的 parent_id
	 */
	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	/**
	 * @return org_name
	 */
	public String getOrg_name() {
		return org_name;
	}

	/**
	 * @param org_name
	 *            要设置的 org_name
	 */
	public void setOrg_name(String org_name) {
		this.org_name = org_name;
	}

	/**
	 * @return org_desc
	 */
	public String getOrg_desc() {
		return org_desc;
	}

	/**
	 * @param org_desc
	 *            要设置的 org_desc
	 */
	public void setOrg_desc(String org_desc) {
		this.org_desc = org_desc;
	}

	/**
	 * @return status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            要设置的 status
	 */
	public void setStatus(int status) {
		this.status = status;
	}
}
