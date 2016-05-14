/**
 * @Title: PlatFormAgentService.java
 * @Package: nari.mip.backstage.service
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-2-26 上午9:10:48
 * @Version: 1.0
 */
package com.cht.emm.service;

import java.util.List;

import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.service.IBaseService;
import com.cht.emm.model.PlatFormAgent;
import com.cht.emm.vo.ApplicationVO1;
import com.cht.emm.vo.PlatFormAgentVO;


/**
 * @Class: PlatFormAgentService
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
public interface PlatFormAgentService extends
		IBaseService<PlatFormAgent, String> {
	List<PlatFormAgentVO> queryForPage(int countall,ConditionQuery query,OrderBy orderBy,Integer pageNum,Integer pageSize);

	public abstract ApplicationVO1 getMaxVersion(Integer os);
}
