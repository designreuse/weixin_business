/**
 * @Title: ThirdPartConfigDaoImpl.java
 * @Package: nari.mip.backstage.dao.impl
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-3-23 下午3:29:29
 * @Version: 1.0
 */
package com.cht.emm.dao.impl;



import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.ThirdPartConfigDao;
import com.cht.emm.model.ThirdPartConfig;


/**
 * @Class: ThirdPartConfigDaoImpl
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
@Repository("thirdPartConfigDao")
public class ThirdPartConfigDaoImpl extends BaseHibernateDaoUnDeletabke<ThirdPartConfig,String> implements
		ThirdPartConfigDao {
	
	 

}
