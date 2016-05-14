package com.cht.emm.dao.impl;


import org.springframework.stereotype.Repository;

import com.cht.emm.common.dao.hibernate4.BaseHibernateDaoUnDeletabke;
import com.cht.emm.dao.DeviceDao;
import com.cht.emm.model.Device;

@Repository
public class DeviceDaoImpl extends BaseHibernateDaoUnDeletabke<Device, String>
		implements DeviceDao {

}
