package com.cht.emm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.id.ConfigUser;
import com.cht.emm.service.ConfigUserService;


@Service
public class ConfigUserServiceImpl extends BaseService<ConfigUser, String>
		implements ConfigUserService {

	@Resource(name = "configUserDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<ConfigUser, String> baseDao) {
		this.baseDao = baseDao;
	}

}
