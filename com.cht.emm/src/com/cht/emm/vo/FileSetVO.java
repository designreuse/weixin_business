/**
 * @Title: FileSetVO.java
 * @Package: nari.mip.backstage.vo
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-4-10 上午11:08:12
 * @Version: 1.0
 */
package com.cht.emm.vo;

import java.util.List;

/**
 * @Class: FileSetVO
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class FileSetVO {

	private String title;
	private List<Parameter> params;
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the params
	 */
	public List<Parameter> getParams() {
		return params;
	}
	/**
	 * @param params the params to set
	 */
	public void setParams(List<Parameter> params) {
		this.params = params;
	}
}


