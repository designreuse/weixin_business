package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.DeviceDetailDao;
import com.cht.emm.model.DeviceDetail;

@Repository
public class DeviceDetailDaoImpl extends
		BaseHibernateDaoUnDeletabke<DeviceDetail, String> implements
		DeviceDetailDao {

}
