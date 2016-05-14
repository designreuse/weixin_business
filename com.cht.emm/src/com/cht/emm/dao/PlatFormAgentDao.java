/**
 * @Title: PlatFormAgentDao.java
 * @Package: nari.mip.backstage.dao
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-2-26 上午9:08:43
 * @Version: 1.0
 */
package com.cht.emm.dao;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.model.PlatFormAgent;
import com.cht.emm.vo.ApplicationVO1;

/**
 * @Class: PlatFormAgentDao
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public interface PlatFormAgentDao extends IBaseDao<PlatFormAgent, String> {
	ApplicationVO1 getMaxVersion(Integer os);
}
