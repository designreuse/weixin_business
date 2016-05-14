/**
 * @Title: UsernamePasswordAuthenticationTokenExt.java
 * @Package: nari.mip.backstage.security.entity
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-4-1 下午4:16:23
 * @Version: 1.0
 */
package com.cht.emm.security.entity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @Class: UsernamePasswordAuthenticationTokenExt
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class UsernamePasswordAuthenticationTokenExt extends
		UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3329136845256176950L;
	
	private String userSource =null;
	
	/**
	 * @return the userSource
	 */
	public String getUserSource() {
		return userSource;
	}

	/**
	 * @param userSource the userSource to set
	 */
	public void setUserSource(String userSource) {
		this.userSource = userSource;
	}

	public UsernamePasswordAuthenticationTokenExt(Object principal,
			Object credentials) {
		super(principal, credentials);
		// TODO Auto-generated constructor stub
	}
	
	public UsernamePasswordAuthenticationTokenExt(Object principal,
			Object credentials,String userSource) {
		this(principal, credentials);
		this.userSource = userSource;
	}
	
	

}
