package com.cht.emm.common.dao;

import java.io.Serializable;
/**
 * 
* @ClassName: UnionEntityGetPK 
* @Description: 用于联合主键对象主键的获取 
* @author 张凯  zhangkai3@sgepri.sgcc.com.cn 
* @date 2014-10-15 下午4:52:01 
* 
* @param <PK>
* @param <PK1>
* @param <PK2>
 */
public interface UnionEntityGetPK<PK extends Serializable,PK1 extends Serializable,PK2 extends Serializable> {

	public PK getPK(PK1 pk1,PK2 pk2);
}
