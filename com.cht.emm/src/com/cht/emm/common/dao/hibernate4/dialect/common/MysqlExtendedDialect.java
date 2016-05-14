/**
 * @Title: BitAndDialect.java
 * @Package: nari.mip.backstage.common.dao.hibernate4.dialect.common
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2014-12-26 下午2:24:33
 * @Version: 1.0
 */
package com.cht.emm.common.dao.hibernate4.dialect.common;


import org.hibernate.dialect.MySQLDialect;

import com.cht.emm.common.dao.hibernate4.sqlFunction.BitAndFunction;
import com.cht.emm.common.dao.hibernate4.sqlFunction.PowFunction;

/**
 * @Class: BitAndDialect
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class MysqlExtendedDialect extends MySQLDialect {
	public MysqlExtendedDialect(){
		super();
		this.registerFunction("bitand", new BitAndFunction());
		this.registerFunction("pow", new PowFunction());
		
	}
}
