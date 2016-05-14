package com.cht.emm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.id.StrategyUser;
import com.cht.emm.service.StrategyUserService;


@Service
public class StrategyUserServiceImpl extends BaseService<StrategyUser, String>
		implements StrategyUserService {

	@Resource(name = "strategyUserDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<StrategyUser, String> baseDao) {
		this.baseDao = baseDao;
	}

}
