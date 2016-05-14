/**   
 * @Title: PageInfo.java 
 * @Package com.nari 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-9-12 下午4:52:54 
 * @version V1.0   
 */
package com.cht.emm.util;

import java.util.List;

/**
 * @ClassName: PageInfo
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-9-12 下午4:52:54
 * 
 */
public class PageInfo {

	private Integer start;
	private Integer length;
	private List<KeyValue<Integer, String>> orders;
	private Integer columnLength;
	private String search;

	/**
	 * @return start
	 */
	public Integer getStart() {
		return start;
	}

	/**
	 * @param start
	 *            要设置的 start
	 */
	public void setStart(Integer start) {
		this.start = start;
	}

	/**
	 * @return length
	 */
	public Integer getLength() {
		return length;
	}

	/**
	 * @param length
	 *            要设置的 length
	 */
	public void setLength(Integer length) {
		this.length = length;
	}

	/**
	 * @return orders
	 */
	public List<KeyValue<Integer, String>> getOrders() {
		return orders;
	}

	/**
	 * @param orders
	 *            要设置的 orders
	 */
	public void setOrders(List<KeyValue<Integer, String>> orders) {
		this.orders = orders;
	}

	/**
	 * @return columnLength
	 */
	public Integer getColumnLength() {
		return columnLength;
	}

	/**
	 * @param columnLength
	 *            要设置的 columnLength
	 */
	public void setColumnLength(Integer columnLength) {
		this.columnLength = columnLength;
	}

	/**
	 * @return search
	 */
	public String getSearch() {
		return search;
	}

	/**
	 * @param search
	 *            要设置的 search
	 */
	public void setSearch(String search) {
		this.search = search;
	}

}
