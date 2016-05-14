/**
 * @Title: PlatFormAgentServiceImpl.java
 * @Package: nari.mip.backstage.service.impl
 * @Description: 
 * @Author: 张凯(zhangkai3@sgepri.sgcc.com.cn)
 * @Date 2015-2-26 上午9:22:42
 * @Version: 1.0
 */
package com.cht.emm.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.dao.util.ConditionQuery;
import com.cht.emm.common.dao.util.OrderBy;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.dao.PlatFormAgentDao;
import com.cht.emm.model.PlatFormAgent;
import com.cht.emm.service.PlatFormAgentService;
import com.cht.emm.util.objectcopier.PlatFormCopier;
import com.cht.emm.vo.ApplicationVO1;
import com.cht.emm.vo.PlatFormAgentVO;


/**
 * @Class: PlatFormAgentServiceImpl
 * @Author: 张凯 (zhangkai3@sgepri.sgcc.com.cn)
 * @Description: 
 */
@Service("platFormAgentService")
public class PlatFormAgentServiceImpl extends
		BaseService<PlatFormAgent, String> implements PlatFormAgentService {
	
	@Resource(name = "platFormAgentDao")
	@Override
	public void setBaseDao(IBaseDao<PlatFormAgent, String> baseDao) {
		// TODO Auto-generated method stub
		this.baseDao =baseDao;
	}

	@Override
	public List<PlatFormAgentVO> queryForPage(int countall,
			ConditionQuery query, OrderBy orderBy, Integer pageNum,
			Integer pageSize) {
		// TODO Auto-generated method stub
		List<PlatFormAgent> list = baseDao.listAll(query, orderBy, pageNum, pageSize);
		return PlatFormCopier.copy(list);
	}

	@Override
	public ApplicationVO1 getMaxVersion(Integer os){
		
		return ((PlatFormAgentDao) baseDao).getMaxVersion(os);
	
	}

}
