/**   
* @Title: IntegerFetcher.java 
* @Package nari.mip.backstage.common.dao.util 
* @Description: TODO(用一句话描述该文件做什么) 
* @author 张凯  zhangkai3@sgepri.sgcc.com.cn   
* @date 2014-10-24 下午3:47:27 
* @version V1.0   
*/
package com.cht.emm.common.dao.util;

import java.math.BigDecimal;
import java.math.BigInteger;

/** 
 * @ClassName: IntegerFetcher 
 * @Description: 用于从数据库返回不同的数据类型中获得Integer
 * @author 张凯  zhangkai3@sgepri.sgcc.com.cn 
 * @date 2014-10-24 下午3:47:27 
 *  
 */
public class IntegerFetcher {
	
	/**
	 *  主要针对Mysql 和 oracle返回结果中不同的数值类型 需要转换成Integer的情况，提供一个转换工具，
	 *  主要是方便使用
	 */
	
	
	
	public  static  Integer getIntVal(Object val){
		/**
		 *  count函数  返回类型 通常为 Long 
		 */
		if(val != null){
			if(val instanceof Integer ) {
				return (Integer)val;
			}
			
			if(val instanceof Long){
				return ((Long) val).intValue();
			}
			
			if(val instanceof BigDecimal){
				return ((BigDecimal) val).intValue();
			}
			if(val instanceof BigInteger){
				
				return ((BigInteger) val).intValue(); 
			}
		}
		
		return 0;
	}

}
