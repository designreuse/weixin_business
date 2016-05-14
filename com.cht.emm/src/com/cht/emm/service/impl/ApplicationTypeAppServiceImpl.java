package com.cht.emm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.id.ApplicationTypeApp;
import com.cht.emm.service.ApplicationTypeAppService;


@Service
public class ApplicationTypeAppServiceImpl extends
		BaseService<ApplicationTypeApp, String> implements
		ApplicationTypeAppService {

	@Resource(name = "applicationTypeAppDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<ApplicationTypeApp, String> baseDao) {
		this.baseDao = baseDao;
	}

}
