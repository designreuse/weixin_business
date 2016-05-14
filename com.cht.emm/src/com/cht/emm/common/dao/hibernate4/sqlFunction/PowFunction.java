/**
 * @Title: PowFunction.java
 * @Package: nari.mip.backstage.common.dao.hibernate4.sqlFunction
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2014-12-29 上午9:10:43
 * @Version: 1.0
 */
package com.cht.emm.common.dao.hibernate4.sqlFunction;

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

/**
 * @Class: PowFunction
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class PowFunction implements SQLFunction{

	@Override
	public Type getReturnType(Type arg0, Mapping arg1) throws QueryException {
		// TODO Auto-generated method stub
		return org.hibernate.type.IntegerType.INSTANCE;
	}

	@Override
	public boolean hasArguments() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean hasParenthesesIfNoArguments() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public String render(Type arg0, List args, SessionFactoryImplementor arg2)
			throws QueryException {
		// TODO Auto-generated method stub
		if (args.size() != 2) {

			throw new IllegalArgumentException(
					"BitAndFunction requires 2 arguments!");
		}
		return " pow("+args.get(0)+","+args.get(1)+")" ;
	}
	
}
