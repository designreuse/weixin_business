/**
 * @Title: RemoteValidator.java
 * @Package: nari.mip.backstage.util
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-4-2 上午9:58:41
 * @Version: 1.0
 */
package com.cht.emm.util;

/**
 * @Class: RemoteValidator
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public interface RemoteValidator {

	public Response valid(String userName,String password,String url,String others);
}
