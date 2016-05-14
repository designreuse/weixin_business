/**
 * @Title: BitAndFunction.java
 * @Package: nari.mip.backstage.common.dao.hibernate4.sqlFunction
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2014-12-26 下午2:18:49
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
 * @Class: BitAndFunction
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public class BitAndFunction implements SQLFunction {

	/* (non-Javadoc)
	 * @see org.hibernate.dialect.function.SQLFunction#getReturnType(org.hibernate.type.Type, org.hibernate.engine.spi.Mapping)
	 */
	@Override
	public Type getReturnType(Type arg0, Mapping arg1) throws QueryException {
		// TODO Auto-generated method stub
		return org.hibernate.type.IntegerType.INSTANCE;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.dialect.function.SQLFunction#hasArguments()
	 */
	@Override
	public boolean hasArguments() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.dialect.function.SQLFunction#hasParenthesesIfNoArguments()
	 */
	@Override
	public boolean hasParenthesesIfNoArguments() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.hibernate.dialect.function.SQLFunction#render(org.hibernate.type.Type, java.util.List, org.hibernate.engine.spi.SessionFactoryImplementor)
	 */
	@Override
	public String render(Type arg0, List args, SessionFactoryImplementor factory)
			throws QueryException {
		// TODO Auto-generated method stub
		if (args.size() != 2) {

			throw new IllegalArgumentException(
					"BitAndFunction requires 2 arguments!");

		}

		return args.get(0) + " & " + args.get(1).toString();
	}

}
