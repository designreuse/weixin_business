/**
 * @Title: OpItem.java
 * @Package: nari.mip.backstage.security
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-2-4 下午4:09:24
 * @Version: 1.0
 */
package com.cht.emm.security;

import java.util.ArrayList;
import java.util.List;


/**
 * @Class: OpItem
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class OpItem {
	/**
	 * by role  还是  by url
	 */
	private int type;
	private String key;
	private List<String> values = new ArrayList<String>();
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/**
	 * @return the values
	 */
	public List<String> getValues() {
		return values;
	}
	/**
	 * @param values the values to set
	 */
	public void setValues(List<String> values) {
		this.values = values;
	}
	
	
}
