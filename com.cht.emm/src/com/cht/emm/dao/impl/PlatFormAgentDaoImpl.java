/**
 * @Title: PlatFormAgentDaoImpl.java
 * @Package: nari.mip.backstage.dao.impl
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-2-26 上午9:19:18
 * @Version: 1.0
 */
package com.cht.emm.dao.impl;

import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDao;
import com.cht.emm.dao.PlatFormAgentDao;
import com.cht.emm.model.PlatFormAgent;
import com.cht.emm.vo.ApplicationVO1;


/**
 * @Class: PlatFormAgentDaoImpl
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
@Repository("platFormAgentDao")
public class PlatFormAgentDaoImpl extends BaseHibernateDao<PlatFormAgent, String> implements PlatFormAgentDao {

	@Override
	public ApplicationVO1 getMaxVersion(Integer os) {
		// TODO Auto-generated method stub
		Integer maxVersion = unique("select max(agent.versionCode) from PlatFormAgent agent where agent.os =?", os);
		
		PlatFormAgent pg = unique("select agent from PlatFormAgent agent where agent.versionCode =?", maxVersion);
		
		if(null==pg) return null;
		
		ApplicationVO1 app = new ApplicationVO1();
		
		app.setId(pg.getId());
		app.setAppId(pg.getPackageName());
		app.setIcon(pg.getIconUrl());
		app.setVersionCode(pg.getVersionCode());
		app.setVersionName(pg.getVersionName());
		
		return app;
	}

}
