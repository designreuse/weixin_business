package com.cht.emm.common.service;
/**
* @ClassName: UnionEntityDeleteTrait 
* @Description: 针对联合主键对象的删除 
* @author 张凯  zhangkai3@sgepri.sgcc.com.cn 
* @date 2014-10-15 下午4:55:41 
* 
* @param <PK1>
* @param <Pk2>
 */
public interface UnionEntityDeleteTrait<PK1,Pk2> {
	/**
	* @Title: deleteUnionEntityA2B 
	* @Description: A->B,id 先左后右
	* @param pk1
	* @param pk2s  
	* @return void    返回类型 
	* @throws
	 */
	public void deleteUnionEntityA2B(PK1 pk1,Pk2[] pk2s);
	
	/**
	* @Title: deleteUnionEntityB2A 
	* @Description: id 先右后左
	* @param pk1
	* @param pk2s  
	* @return void    返回类型 
	* @throws
	 */
	public void deleteUnionEntityB2A(PK1 pk1,Pk2[] pk2s);
	
}
