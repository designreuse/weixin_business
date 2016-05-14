package com.cht.emm.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cht.emm.common.dao.IBaseDao;
import com.cht.emm.common.service.impl.BaseService;
import com.cht.emm.model.DeviceDetail;
import com.cht.emm.service.DeviceDetailService;


@Service
public class DeviceDetailServiceImpl extends BaseService<DeviceDetail, String>
		implements DeviceDetailService {

	@Resource(name = "deviceDetailDaoImpl")
	@Override
	public void setBaseDao(IBaseDao<DeviceDetail, String> baseDao) {
		this.baseDao = baseDao;
	}

}
