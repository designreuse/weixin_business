package com.cht.emm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.id.UserDevice;
import com.cht.emm.service.UserDeviceService;


@Service
public class UserDeviceServiceImpl extends BaseService<UserDevice, String>
		implements UserDeviceService {

	@Resource(name = "userDeviceDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<UserDevice, String> baseDao) {
		this.baseDao = baseDao;
	}

}
