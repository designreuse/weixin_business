/**   
 * @Title: ColumnProperty.java 
 * @Package com.nari 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-9-12 下午4:33:11 
 * @version V1.0   
 */
package com.cht.emm.util;

/**
 * @ClassName: ColumnProperty
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-9-12 下午4:33:11
 * 
 */
public class ColumnProperty {

	private String colName;
	private boolean searchable;

	private boolean sortable;
	private String searchValue;
	private boolean useRegex;

	/**
	 * @return colName
	 */
	public String getColName() {
		return colName;
	}

	/**
	 * @param colName
	 *            要设置的 colName
	 */
	public void setColName(String colName) {
		this.colName = colName;
	}

	/**
	 * @return searchable
	 */
	public boolean isSearchable() {
		return searchable;
	}

	/**
	 * @param searchable
	 *            要设置的 searchable
	 */
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	/**
	 * @return sortable
	 */
	public boolean isSortable() {
		return sortable;
	}

	/**
	 * @param sortable
	 *            要设置的 sortable
	 */
	public void setSortable(boolean sortable) {
		this.sortable = sortable;
	}

	/**
	 * @return searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}

	/**
	 * @param searchValue
	 *            要设置的 searchValue
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	/**
	 * @return useRegex
	 */
	public boolean isUseRegex() {
		return useRegex;
	}

	/**
	 * @param useRegex
	 *            要设置的 useRegex
	 */
	public void setUseRegex(boolean useRegex) {
		this.useRegex = useRegex;
	}

}
