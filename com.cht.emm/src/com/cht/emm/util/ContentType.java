/**   
 * @Title: ContentType.java 
 * @Package nari.mip.backstage.util 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
 * @date 2014-9-28 下午4:18:11 
 * @version V1.0   
 */
package com.cht.emm.util;

/**
 * @ClassName: ContentType
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张凯 zhangkai3@sgepri.sgcc.com.cn
 * @date 2014-9-28 下午4:18:11
 * 
 */
public enum ContentType {
	XML("xml"), JSON("json");
	private String type;

	private ContentType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
